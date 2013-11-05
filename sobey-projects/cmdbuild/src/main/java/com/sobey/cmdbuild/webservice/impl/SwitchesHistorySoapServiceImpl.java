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
import com.sobey.cmdbuild.entity.SwitchesHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.SwitchesHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "SwitchesHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.SwitchesHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class SwitchesHistorySoapServiceImpl extends BasicSoapSevcie implements SwitchesHistorySoapService {
	@Override
	public DTOResult<SwitchesHistoryDTO> findSwitchesHistory(@WebParam(name = "id") Integer id) {
		DTOResult<SwitchesHistoryDTO> result = new DTOResult<SwitchesHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			SwitchesHistory switchesHistory = comm.switchesHistoryService.findSwitchesHistory(id);
			Validate.notNull(switchesHistory, ERROR.OBJECT_NULL);
			SwitchesHistoryDTO switchesHistoryDTO = BeanMapper.map(switchesHistory, SwitchesHistoryDTO.class);
			result.setDto(switchesHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<SwitchesHistoryDTO> findSwitchesHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<SwitchesHistoryDTO> result = new DTOResult<SwitchesHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			SwitchesHistory switchesHistory = comm.switchesHistoryService.findByCode(code);
			Validate.notNull(switchesHistory, ERROR.OBJECT_NULL);
			SwitchesHistoryDTO switchesHistoryDTO = BeanMapper.map(switchesHistory, SwitchesHistoryDTO.class);
			result.setDto(switchesHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createSwitchesHistory(@WebParam(name = "switchesHistoryDTO") SwitchesHistoryDTO switchesHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(switchesHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.switchesHistoryService.findByCode(switchesHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			SwitchesHistory switchesHistory = BeanMapper.map(switchesHistoryDTO, SwitchesHistory.class);
			BeanValidators.validateWithException(validator, switchesHistory);
			comm.switchesHistoryService.saveOrUpdate(switchesHistory);
			return new IdResult(switchesHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateSwitchesHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "switchesHistoryDTO") SwitchesHistoryDTO switchesHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(switchesHistoryDTO, ERROR.INPUT_NULL);
			SwitchesHistory switchesHistory = comm.switchesHistoryService.findSwitchesHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.switchesHistoryService.findByCode(switchesHistoryDTO.getCode()) == null
					|| switchesHistory.getCode().equals(switchesHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			SwitchesHistory switchesHistoryEntity = BeanMapper.map(switchesHistoryDTO, SwitchesHistory.class);
			BeanMapper.copy(switchesHistoryEntity, switchesHistory);
			switchesHistoryEntity.setIdClass(SwitchesHistory.class.getSimpleName());
			switchesHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.switchesHistoryService.saveOrUpdate(switchesHistoryEntity);
			return new IdResult(switchesHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteSwitchesHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			SwitchesHistory switchesHistory = comm.switchesHistoryService.findSwitchesHistory(id);
			SwitchesHistory switchesHistoryEntity = BeanMapper.map(findSwitchesHistory(id).getDto(),
					SwitchesHistory.class);
			BeanMapper.copy(switchesHistoryEntity, switchesHistory);
			switchesHistoryEntity.setIdClass(SwitchesHistory.class.getSimpleName());
			switchesHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.switchesHistoryService.saveOrUpdate(switchesHistoryEntity);
			return new IdResult(switchesHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<SwitchesHistoryDTO> getSwitchesHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<SwitchesHistoryDTO> result = new PaginationResult<SwitchesHistoryDTO>();
		try {
			result = comm.switchesHistoryService.getSwitchesHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<SwitchesHistoryDTO> getSwitchesHistoryList() {
		DTOListResult<SwitchesHistoryDTO> result = new DTOListResult<SwitchesHistoryDTO>();
		try {
			List<SwitchesHistory> companies = comm.switchesHistoryService.getCompanies();
			List<SwitchesHistoryDTO> list = BeanMapper.mapList(companies, SwitchesHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}