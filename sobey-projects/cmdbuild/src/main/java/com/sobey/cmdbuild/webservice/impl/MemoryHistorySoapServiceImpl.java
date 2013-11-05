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
import com.sobey.cmdbuild.entity.MemoryHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.MemoryHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.MemoryHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "MemoryHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.MemoryHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class MemoryHistorySoapServiceImpl extends BasicSoapSevcie implements MemoryHistorySoapService {
	@Override
	public DTOResult<MemoryHistoryDTO> findMemoryHistory(@WebParam(name = "id") Integer id) {
		DTOResult<MemoryHistoryDTO> result = new DTOResult<MemoryHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			MemoryHistory memoryHistory = comm.memoryHistoryService.findMemoryHistory(id);
			Validate.notNull(memoryHistory, ERROR.OBJECT_NULL);
			MemoryHistoryDTO memoryHistoryDTO = BeanMapper.map(memoryHistory, MemoryHistoryDTO.class);
			result.setDto(memoryHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MemoryHistoryDTO> findMemoryHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<MemoryHistoryDTO> result = new DTOResult<MemoryHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			MemoryHistory memoryHistory = comm.memoryHistoryService.findByCode(code);
			Validate.notNull(memoryHistory, ERROR.OBJECT_NULL);
			MemoryHistoryDTO memoryHistoryDTO = BeanMapper.map(memoryHistory, MemoryHistoryDTO.class);
			result.setDto(memoryHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMemoryHistory(@WebParam(name = "memoryHistoryDTO") MemoryHistoryDTO memoryHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(memoryHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.memoryHistoryService.findByCode(memoryHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			MemoryHistory memoryHistory = BeanMapper.map(memoryHistoryDTO, MemoryHistory.class);
			BeanValidators.validateWithException(validator, memoryHistory);
			comm.memoryHistoryService.saveOrUpdate(memoryHistory);
			return new IdResult(memoryHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateMemoryHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "memoryHistoryDTO") MemoryHistoryDTO memoryHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(memoryHistoryDTO, ERROR.INPUT_NULL);
			MemoryHistory memoryHistory = comm.memoryHistoryService.findMemoryHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.memoryHistoryService.findByCode(memoryHistoryDTO.getCode()) == null
					|| memoryHistory.getCode().equals(memoryHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			MemoryHistory memoryHistoryEntity = BeanMapper.map(memoryHistoryDTO, MemoryHistory.class);
			BeanMapper.copy(memoryHistoryEntity, memoryHistory);
			memoryHistoryEntity.setIdClass(MemoryHistory.class.getSimpleName());
			memoryHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.memoryHistoryService.saveOrUpdate(memoryHistoryEntity);
			return new IdResult(memoryHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMemoryHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			MemoryHistory memoryHistory = comm.memoryHistoryService.findMemoryHistory(id);
			MemoryHistory memoryHistoryEntity = BeanMapper.map(findMemoryHistory(id).getDto(), MemoryHistory.class);
			BeanMapper.copy(memoryHistoryEntity, memoryHistory);
			memoryHistoryEntity.setIdClass(MemoryHistory.class.getSimpleName());
			memoryHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.memoryHistoryService.saveOrUpdate(memoryHistoryEntity);
			return new IdResult(memoryHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<MemoryHistoryDTO> getMemoryHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<MemoryHistoryDTO> result = new PaginationResult<MemoryHistoryDTO>();
		try {
			result = comm.memoryHistoryService.getMemoryHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<MemoryHistoryDTO> getMemoryHistoryList() {
		DTOListResult<MemoryHistoryDTO> result = new DTOListResult<MemoryHistoryDTO>();
		try {
			List<MemoryHistory> companies = comm.memoryHistoryService.getCompanies();
			List<MemoryHistoryDTO> list = BeanMapper.mapList(companies, MemoryHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}