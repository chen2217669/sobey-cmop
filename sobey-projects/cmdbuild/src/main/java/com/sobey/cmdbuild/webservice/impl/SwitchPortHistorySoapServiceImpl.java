package com.sobey.cmdbuild.webservice.impl;

import java.util.List;
import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.ConstraintViolationException;
import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.entity.SwitchPortHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.SwitchPortHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "SwitchPortHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.SwitchPortHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class SwitchPortHistorySoapServiceImpl extends BasicSoapSevcie implements SwitchPortHistorySoapService {
	@Override
	public DTOResult<SwitchPortHistoryDTO> findSwitchPortHistory(@WebParam(name = "id") Integer id) {
		DTOResult<SwitchPortHistoryDTO> result = new DTOResult<SwitchPortHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			SwitchPortHistory switchPortHistory = comm.switchPortHistoryService.findSwitchPortHistory(id);
			Validate.notNull(switchPortHistory, ERROR.OBJECT_NULL);
			SwitchPortHistoryDTO switchPortHistoryDTO = BeanMapper.map(switchPortHistory, SwitchPortHistoryDTO.class);
			result.setDto(switchPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<SwitchPortHistoryDTO> findSwitchPortHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<SwitchPortHistoryDTO> result = new DTOResult<SwitchPortHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			SwitchPortHistory switchPortHistory = comm.switchPortHistoryService.findByCode(code);
			Validate.notNull(switchPortHistory, ERROR.OBJECT_NULL);
			SwitchPortHistoryDTO switchPortHistoryDTO = BeanMapper.map(switchPortHistory, SwitchPortHistoryDTO.class);
			result.setDto(switchPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createSwitchPortHistory(
			@WebParam(name = "switchPortHistoryDTO") SwitchPortHistoryDTO switchPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(switchPortHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.switchPortHistoryService.findByCode(switchPortHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			SwitchPortHistory switchPortHistory = BeanMapper.map(switchPortHistoryDTO, SwitchPortHistory.class);
			BeanValidators.validateWithException(validator, switchPortHistory);
			comm.switchPortHistoryService.saveOrUpdate(switchPortHistory);
			return new IdResult(switchPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateSwitchPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "switchPortHistoryDTO") SwitchPortHistoryDTO switchPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(switchPortHistoryDTO, ERROR.INPUT_NULL);
			SwitchPortHistory switchPortHistory = comm.switchPortHistoryService.findSwitchPortHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.switchPortHistoryService.findByCode(switchPortHistoryDTO.getCode()) == null
					|| switchPortHistory.getCode().equals(switchPortHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			SwitchPortHistory switchPortHistoryEntity = BeanMapper.map(switchPortHistoryDTO, SwitchPortHistory.class);
			BeanMapper.copy(switchPortHistoryEntity, switchPortHistory);
			switchPortHistoryEntity.setIdClass(SwitchPortHistory.class.getSimpleName());
			switchPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.switchPortHistoryService.saveOrUpdate(switchPortHistoryEntity);
			return new IdResult(switchPortHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteSwitchPortHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			SwitchPortHistory switchPortHistory = comm.switchPortHistoryService.findSwitchPortHistory(id);
			SwitchPortHistory switchPortHistoryEntity = BeanMapper.map(findSwitchPortHistory(id).getDto(),
					SwitchPortHistory.class);
			BeanMapper.copy(switchPortHistoryEntity, switchPortHistory);
			switchPortHistoryEntity.setIdClass(SwitchPortHistory.class.getSimpleName());
			switchPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.switchPortHistoryService.saveOrUpdate(switchPortHistoryEntity);
			return new IdResult(switchPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<SwitchPortHistoryDTO> getSwitchPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<SwitchPortHistoryDTO> result = new PaginationResult<SwitchPortHistoryDTO>();
		try {
			result = comm.switchPortHistoryService
					.getSwitchPortHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<SwitchPortHistoryDTO> getSwitchPortHistoryList() {
		DTOListResult<SwitchPortHistoryDTO> result = new DTOListResult<SwitchPortHistoryDTO>();
		try {
			List<SwitchPortHistory> companies = comm.switchPortHistoryService.getCompanies();
			List<SwitchPortHistoryDTO> list = BeanMapper.mapList(companies, SwitchPortHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}