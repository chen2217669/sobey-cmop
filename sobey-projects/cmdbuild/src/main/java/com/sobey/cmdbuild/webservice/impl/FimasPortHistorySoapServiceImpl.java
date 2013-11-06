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
import com.sobey.cmdbuild.entity.FimasPortHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.FimasPortHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.FimasPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "FimasPortHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.FimasPortHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FimasPortHistorySoapServiceImpl extends BasicSoapSevcie implements FimasPortHistorySoapService {
	@Override
	public DTOResult<FimasPortHistoryDTO> findFimasPortHistory(@WebParam(name = "id") Integer id) {
		DTOResult<FimasPortHistoryDTO> result = new DTOResult<FimasPortHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FimasPortHistory fimasPortHistory = comm.fimasPortHistoryService.findFimasPortHistory(id);
			Validate.notNull(fimasPortHistory, ERROR.OBJECT_NULL);
			FimasPortHistoryDTO fimasPortHistoryDTO = BeanMapper.map(fimasPortHistory, FimasPortHistoryDTO.class);
			result.setDto(fimasPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasPortHistoryDTO> findFimasPortHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<FimasPortHistoryDTO> result = new DTOResult<FimasPortHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			FimasPortHistory fimasPortHistory = comm.fimasPortHistoryService.findByCode(code);
			Validate.notNull(fimasPortHistory, ERROR.OBJECT_NULL);
			FimasPortHistoryDTO fimasPortHistoryDTO = BeanMapper.map(fimasPortHistory, FimasPortHistoryDTO.class);
			result.setDto(fimasPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createFimasPortHistory(
			@WebParam(name = "fimasPortHistoryDTO") FimasPortHistoryDTO fimasPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasPortHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasPortHistoryService.findByCode(fimasPortHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			FimasPortHistory fimasPortHistory = BeanMapper.map(fimasPortHistoryDTO, FimasPortHistory.class);
			BeanValidators.validateWithException(validator, fimasPortHistory);
			comm.fimasPortHistoryService.saveOrUpdate(fimasPortHistory);
			return new IdResult(fimasPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimasPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasPortHistoryDTO") FimasPortHistoryDTO fimasPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasPortHistoryDTO, ERROR.INPUT_NULL);
			FimasPortHistory fimasPortHistory = comm.fimasPortHistoryService.findFimasPortHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasPortHistoryService.findByCode(fimasPortHistoryDTO.getCode()) == null
					|| fimasPortHistory.getCode().equals(fimasPortHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			FimasPortHistory fimasPortHistoryEntity = BeanMapper.map(fimasPortHistoryDTO, FimasPortHistory.class);
			BeanMapper.copy(fimasPortHistoryEntity, fimasPortHistory);
			fimasPortHistoryEntity.setIdClass(FimasPortHistory.class.getSimpleName());
			fimasPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.fimasPortHistoryService.saveOrUpdate(fimasPortHistoryEntity);
			return new IdResult(fimasPortHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimasPortHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FimasPortHistory fimasPortHistory = comm.fimasPortHistoryService.findFimasPortHistory(id);
			FimasPortHistory fimasPortHistoryEntity = BeanMapper.map(findFimasPortHistory(id).getDto(),
					FimasPortHistory.class);
			BeanMapper.copy(fimasPortHistoryEntity, fimasPortHistory);
			fimasPortHistoryEntity.setIdClass(FimasPortHistory.class.getSimpleName());
			fimasPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.fimasPortHistoryService.saveOrUpdate(fimasPortHistoryEntity);
			return new IdResult(fimasPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasPortHistoryDTO> getFimasPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FimasPortHistoryDTO> result = new PaginationResult<FimasPortHistoryDTO>();
		try {
			result = comm.fimasPortHistoryService.getFimasPortHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasPortHistoryDTO> getFimasPortHistoryList() {
		DTOListResult<FimasPortHistoryDTO> result = new DTOListResult<FimasPortHistoryDTO>();
		try {
			List<FimasPortHistory> companies = comm.fimasPortHistoryService.getCompanies();
			List<FimasPortHistoryDTO> list = BeanMapper.mapList(companies, FimasPortHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}