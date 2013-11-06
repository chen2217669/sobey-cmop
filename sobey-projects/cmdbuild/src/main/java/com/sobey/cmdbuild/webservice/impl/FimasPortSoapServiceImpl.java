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
import com.sobey.cmdbuild.entity.FimasPort;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.FimasPortSoapService;
import com.sobey.cmdbuild.webservice.response.dto.FimasPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "FimasPortService", endpointInterface = "com.sobey.cmdbuild.webservice.FimasPortSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FimasPortSoapServiceImpl extends BasicSoapSevcie implements FimasPortSoapService {
	@Override
	public DTOResult<FimasPortDTO> findFimasPort(@WebParam(name = "id") Integer id) {
		DTOResult<FimasPortDTO> result = new DTOResult<FimasPortDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FimasPort fimasPort = comm.fimasPortService.findFimasPort(id);
			Validate.notNull(fimasPort, ERROR.OBJECT_NULL);
			FimasPortDTO fimasPortDTO = BeanMapper.map(fimasPort, FimasPortDTO.class);
			result.setDto(fimasPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasPortDTO> findFimasPortByCode(@WebParam(name = "code") String code) {
		DTOResult<FimasPortDTO> result = new DTOResult<FimasPortDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			FimasPort fimasPort = comm.fimasPortService.findByCode(code);
			Validate.notNull(fimasPort, ERROR.OBJECT_NULL);
			FimasPortDTO fimasPortDTO = BeanMapper.map(fimasPort, FimasPortDTO.class);
			result.setDto(fimasPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createFimasPort(@WebParam(name = "fimasPortDTO") FimasPortDTO fimasPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasPortDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasPortService.findByCode(fimasPortDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			FimasPort fimasPort = BeanMapper.map(fimasPortDTO, FimasPort.class);
			BeanValidators.validateWithException(validator, fimasPort);
			comm.fimasPortService.saveOrUpdate(fimasPort);
			return new IdResult(fimasPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimasPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasPortDTO") FimasPortDTO fimasPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasPortDTO, ERROR.INPUT_NULL);
			FimasPort fimasPort = comm.fimasPortService.findFimasPort(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasPortService.findByCode(fimasPortDTO.getCode()) == null
					|| fimasPort.getCode().equals(fimasPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			FimasPort fimasPortEntity = BeanMapper.map(fimasPortDTO, FimasPort.class);
			BeanMapper.copy(fimasPortEntity, fimasPort);
			fimasPortEntity.setIdClass(FimasPort.class.getSimpleName());
			fimasPortEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.fimasPortService.saveOrUpdate(fimasPortEntity);
			return new IdResult(fimasPort.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimasPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FimasPort fimasPort = comm.fimasPortService.findFimasPort(id);
			FimasPort fimasPortEntity = BeanMapper.map(findFimasPort(id).getDto(), FimasPort.class);
			BeanMapper.copy(fimasPortEntity, fimasPort);
			fimasPortEntity.setIdClass(FimasPort.class.getSimpleName());
			fimasPortEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.fimasPortService.saveOrUpdate(fimasPortEntity);
			return new IdResult(fimasPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasPortDTO> getFimasPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FimasPortDTO> result = new PaginationResult<FimasPortDTO>();
		try {
			result = comm.fimasPortService.getFimasPortDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasPortDTO> getFimasPortList() {
		DTOListResult<FimasPortDTO> result = new DTOListResult<FimasPortDTO>();
		try {
			List<FimasPort> companies = comm.fimasPortService.getCompanies();
			List<FimasPortDTO> list = BeanMapper.mapList(companies, FimasPortDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}