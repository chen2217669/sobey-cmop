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
import com.sobey.cmdbuild.entity.NicPortHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.NicPortHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.NicPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "NicPortHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.NicPortHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NicPortHistorySoapServiceImpl extends BasicSoapSevcie implements NicPortHistorySoapService {
	@Override
	public DTOResult<NicPortHistoryDTO> findNicPortHistory(@WebParam(name = "id") Integer id) {
		DTOResult<NicPortHistoryDTO> result = new DTOResult<NicPortHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NicPortHistory nicPortHistory = comm.nicPortHistoryService.findNicPortHistory(id);
			Validate.notNull(nicPortHistory, ERROR.OBJECT_NULL);
			NicPortHistoryDTO nicPortHistoryDTO = BeanMapper.map(nicPortHistory, NicPortHistoryDTO.class);
			result.setDto(nicPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NicPortHistoryDTO> findNicPortHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<NicPortHistoryDTO> result = new DTOResult<NicPortHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			NicPortHistory nicPortHistory = comm.nicPortHistoryService.findByCode(code);
			Validate.notNull(nicPortHistory, ERROR.OBJECT_NULL);
			NicPortHistoryDTO nicPortHistoryDTO = BeanMapper.map(nicPortHistory, NicPortHistoryDTO.class);
			result.setDto(nicPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createNicPortHistory(@WebParam(name = "nicPortHistoryDTO") NicPortHistoryDTO nicPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(nicPortHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.nicPortHistoryService.findByCode(nicPortHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			NicPortHistory nicPortHistory = BeanMapper.map(nicPortHistoryDTO, NicPortHistory.class);
			BeanValidators.validateWithException(validator, nicPortHistory);
			comm.nicPortHistoryService.saveOrUpdate(nicPortHistory);
			return new IdResult(nicPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNicPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "nicPortHistoryDTO") NicPortHistoryDTO nicPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(nicPortHistoryDTO, ERROR.INPUT_NULL);
			NicPortHistory nicPortHistory = comm.nicPortHistoryService.findNicPortHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.nicPortHistoryService.findByCode(nicPortHistoryDTO.getCode()) == null
					|| nicPortHistory.getCode().equals(nicPortHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			NicPortHistory nicPortHistoryEntity = BeanMapper.map(nicPortHistoryDTO, NicPortHistory.class);
			BeanMapper.copy(nicPortHistoryEntity, nicPortHistory);
			nicPortHistoryEntity.setIdClass(NicPortHistory.class.getSimpleName());
			nicPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.nicPortHistoryService.saveOrUpdate(nicPortHistoryEntity);
			return new IdResult(nicPortHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNicPortHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NicPortHistory nicPortHistory = comm.nicPortHistoryService.findNicPortHistory(id);
			NicPortHistory nicPortHistoryEntity = BeanMapper.map(findNicPortHistory(id).getDto(), NicPortHistory.class);
			BeanMapper.copy(nicPortHistoryEntity, nicPortHistory);
			nicPortHistoryEntity.setIdClass(NicPortHistory.class.getSimpleName());
			nicPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.nicPortHistoryService.saveOrUpdate(nicPortHistoryEntity);
			return new IdResult(nicPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NicPortHistoryDTO> getNicPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<NicPortHistoryDTO> result = new PaginationResult<NicPortHistoryDTO>();
		try {
			result = comm.nicPortHistoryService.getNicPortHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NicPortHistoryDTO> getNicPortHistoryList() {
		DTOListResult<NicPortHistoryDTO> result = new DTOListResult<NicPortHistoryDTO>();
		try {
			List<NicPortHistory> companies = comm.nicPortHistoryService.getCompanies();
			List<NicPortHistoryDTO> list = BeanMapper.mapList(companies, NicPortHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}