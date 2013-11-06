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
import com.sobey.cmdbuild.entity.FimasHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.FimasHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.FimasHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "FimasHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.FimasHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FimasHistorySoapServiceImpl extends BasicSoapSevcie implements FimasHistorySoapService {
	@Override
	public DTOResult<FimasHistoryDTO> findFimasHistory(@WebParam(name = "id") Integer id) {
		DTOResult<FimasHistoryDTO> result = new DTOResult<FimasHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FimasHistory fimasHistory = comm.fimasHistoryService.findFimasHistory(id);
			Validate.notNull(fimasHistory, ERROR.OBJECT_NULL);
			FimasHistoryDTO fimasHistoryDTO = BeanMapper.map(fimasHistory, FimasHistoryDTO.class);
			result.setDto(fimasHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasHistoryDTO> findFimasHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<FimasHistoryDTO> result = new DTOResult<FimasHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			FimasHistory fimasHistory = comm.fimasHistoryService.findByCode(code);
			Validate.notNull(fimasHistory, ERROR.OBJECT_NULL);
			FimasHistoryDTO fimasHistoryDTO = BeanMapper.map(fimasHistory, FimasHistoryDTO.class);
			result.setDto(fimasHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createFimasHistory(@WebParam(name = "fimasHistoryDTO") FimasHistoryDTO fimasHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasHistoryService.findByCode(fimasHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			FimasHistory fimasHistory = BeanMapper.map(fimasHistoryDTO, FimasHistory.class);
			BeanValidators.validateWithException(validator, fimasHistory);
			comm.fimasHistoryService.saveOrUpdate(fimasHistory);
			return new IdResult(fimasHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimasHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasHistoryDTO") FimasHistoryDTO fimasHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasHistoryDTO, ERROR.INPUT_NULL);
			FimasHistory fimasHistory = comm.fimasHistoryService.findFimasHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasHistoryService.findByCode(fimasHistoryDTO.getCode()) == null
					|| fimasHistory.getCode().equals(fimasHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			FimasHistory fimasHistoryEntity = BeanMapper.map(fimasHistoryDTO, FimasHistory.class);
			BeanMapper.copy(fimasHistoryEntity, fimasHistory);
			fimasHistoryEntity.setIdClass(FimasHistory.class.getSimpleName());
			fimasHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.fimasHistoryService.saveOrUpdate(fimasHistoryEntity);
			return new IdResult(fimasHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimasHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FimasHistory fimasHistory = comm.fimasHistoryService.findFimasHistory(id);
			FimasHistory fimasHistoryEntity = BeanMapper.map(findFimasHistory(id).getDto(), FimasHistory.class);
			BeanMapper.copy(fimasHistoryEntity, fimasHistory);
			fimasHistoryEntity.setIdClass(FimasHistory.class.getSimpleName());
			fimasHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.fimasHistoryService.saveOrUpdate(fimasHistoryEntity);
			return new IdResult(fimasHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasHistoryDTO> getFimasHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FimasHistoryDTO> result = new PaginationResult<FimasHistoryDTO>();
		try {
			result = comm.fimasHistoryService.getFimasHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasHistoryDTO> getFimasHistoryList() {
		DTOListResult<FimasHistoryDTO> result = new DTOListResult<FimasHistoryDTO>();
		try {
			List<FimasHistory> companies = comm.fimasHistoryService.getCompanies();
			List<FimasHistoryDTO> list = BeanMapper.mapList(companies, FimasHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}