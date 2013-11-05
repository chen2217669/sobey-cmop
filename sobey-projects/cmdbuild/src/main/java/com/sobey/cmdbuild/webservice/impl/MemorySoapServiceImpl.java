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
import com.sobey.cmdbuild.entity.Memory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.MemorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.MemoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "MemoryService", endpointInterface = "com.sobey.cmdbuild.webservice.MemorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class MemorySoapServiceImpl extends BasicSoapSevcie implements MemorySoapService {
	@Override
	public DTOResult<MemoryDTO> findMemory(@WebParam(name = "id") Integer id) {
		DTOResult<MemoryDTO> result = new DTOResult<MemoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Memory memory = comm.memoryService.findMemory(id);
			Validate.notNull(memory, ERROR.OBJECT_NULL);
			MemoryDTO memoryDTO = BeanMapper.map(memory, MemoryDTO.class);
			result.setDto(memoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MemoryDTO> findMemoryByCode(@WebParam(name = "code") String code) {
		DTOResult<MemoryDTO> result = new DTOResult<MemoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Memory memory = comm.memoryService.findByCode(code);
			Validate.notNull(memory, ERROR.OBJECT_NULL);
			MemoryDTO memoryDTO = BeanMapper.map(memory, MemoryDTO.class);
			result.setDto(memoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createMemory(@WebParam(name = "memoryDTO") MemoryDTO memoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(memoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.memoryService.findByCode(memoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Memory memory = BeanMapper.map(memoryDTO, Memory.class);
			BeanValidators.validateWithException(validator, memory);
			comm.memoryService.saveOrUpdate(memory);
			return new IdResult(memory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateMemory(@WebParam(name = "id") Integer id, @WebParam(name = "memoryDTO") MemoryDTO memoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(memoryDTO, ERROR.INPUT_NULL);
			Memory memory = comm.memoryService.findMemory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.memoryService.findByCode(memoryDTO.getCode()) == null
							|| memory.getCode().equals(memoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Memory memoryEntity = BeanMapper.map(memoryDTO, Memory.class);
			BeanMapper.copy(memoryEntity, memory);
			memoryEntity.setIdClass(Memory.class.getSimpleName());
			memoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.memoryService.saveOrUpdate(memoryEntity);
			return new IdResult(memory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMemory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Memory memory = comm.memoryService.findMemory(id);
			Memory memoryEntity = BeanMapper.map(findMemory(id).getDto(), Memory.class);
			BeanMapper.copy(memoryEntity, memory);
			memoryEntity.setIdClass(Memory.class.getSimpleName());
			memoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.memoryService.saveOrUpdate(memoryEntity);
			return new IdResult(memory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<MemoryDTO> getMemoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<MemoryDTO> result = new PaginationResult<MemoryDTO>();
		try {
			result = comm.memoryService.getMemoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<MemoryDTO> getMemoryList() {
		DTOListResult<MemoryDTO> result = new DTOListResult<MemoryDTO>();
		try {
			List<Memory> companies = comm.memoryService.getCompanies();
			List<MemoryDTO> list = BeanMapper.mapList(companies, MemoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}