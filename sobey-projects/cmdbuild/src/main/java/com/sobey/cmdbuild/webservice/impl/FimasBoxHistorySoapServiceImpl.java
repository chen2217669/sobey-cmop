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
import com.sobey.cmdbuild.entity.FimasBoxHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.FimasBoxHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.FimasBoxHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "FimasBoxHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.FimasBoxHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FimasBoxHistorySoapServiceImpl extends BasicSoapSevcie implements FimasBoxHistorySoapService {
	@Override
	public DTOResult<FimasBoxHistoryDTO> findFimasBoxHistory(@WebParam(name = "id") Integer id) {
		DTOResult<FimasBoxHistoryDTO> result = new DTOResult<FimasBoxHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FimasBoxHistory fimasBoxHistory = comm.fimasBoxHistoryService.findFimasBoxHistory(id);
			Validate.notNull(fimasBoxHistory, ERROR.OBJECT_NULL);
			FimasBoxHistoryDTO fimasBoxHistoryDTO = BeanMapper.map(fimasBoxHistory, FimasBoxHistoryDTO.class);
			result.setDto(fimasBoxHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasBoxHistoryDTO> findFimasBoxHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<FimasBoxHistoryDTO> result = new DTOResult<FimasBoxHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			FimasBoxHistory fimasBoxHistory = comm.fimasBoxHistoryService.findByCode(code);
			Validate.notNull(fimasBoxHistory, ERROR.OBJECT_NULL);
			FimasBoxHistoryDTO fimasBoxHistoryDTO = BeanMapper.map(fimasBoxHistory, FimasBoxHistoryDTO.class);
			result.setDto(fimasBoxHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createFimasBoxHistory(@WebParam(name = "fimasBoxHistoryDTO") FimasBoxHistoryDTO fimasBoxHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasBoxHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasBoxHistoryService.findByCode(fimasBoxHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			FimasBoxHistory fimasBoxHistory = BeanMapper.map(fimasBoxHistoryDTO, FimasBoxHistory.class);
			BeanValidators.validateWithException(validator, fimasBoxHistory);
			comm.fimasBoxHistoryService.saveOrUpdate(fimasBoxHistory);
			return new IdResult(fimasBoxHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimasBoxHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasBoxHistoryDTO") FimasBoxHistoryDTO fimasBoxHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasBoxHistoryDTO, ERROR.INPUT_NULL);
			FimasBoxHistory fimasBoxHistory = comm.fimasBoxHistoryService.findFimasBoxHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasBoxHistoryService.findByCode(fimasBoxHistoryDTO.getCode()) == null
					|| fimasBoxHistory.getCode().equals(fimasBoxHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			FimasBoxHistory fimasBoxHistoryEntity = BeanMapper.map(fimasBoxHistoryDTO, FimasBoxHistory.class);
			BeanMapper.copy(fimasBoxHistoryEntity, fimasBoxHistory);
			fimasBoxHistoryEntity.setIdClass(FimasBoxHistory.class.getSimpleName());
			fimasBoxHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.fimasBoxHistoryService.saveOrUpdate(fimasBoxHistoryEntity);
			return new IdResult(fimasBoxHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimasBoxHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FimasBoxHistory fimasBoxHistory = comm.fimasBoxHistoryService.findFimasBoxHistory(id);
			FimasBoxHistory fimasBoxHistoryEntity = BeanMapper.map(findFimasBoxHistory(id).getDto(),
					FimasBoxHistory.class);
			BeanMapper.copy(fimasBoxHistoryEntity, fimasBoxHistory);
			fimasBoxHistoryEntity.setIdClass(FimasBoxHistory.class.getSimpleName());
			fimasBoxHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.fimasBoxHistoryService.saveOrUpdate(fimasBoxHistoryEntity);
			return new IdResult(fimasBoxHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasBoxHistoryDTO> getFimasBoxHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FimasBoxHistoryDTO> result = new PaginationResult<FimasBoxHistoryDTO>();
		try {
			result = comm.fimasBoxHistoryService.getFimasBoxHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasBoxHistoryDTO> getFimasBoxHistoryList() {
		DTOListResult<FimasBoxHistoryDTO> result = new DTOListResult<FimasBoxHistoryDTO>();
		try {
			List<FimasBoxHistory> companies = comm.fimasBoxHistoryService.getCompanies();
			List<FimasBoxHistoryDTO> list = BeanMapper.mapList(companies, FimasBoxHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}