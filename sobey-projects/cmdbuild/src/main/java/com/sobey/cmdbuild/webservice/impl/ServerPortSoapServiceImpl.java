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
import com.sobey.cmdbuild.entity.ServerPort;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.ServerPortSoapService;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "ServerPortService", endpointInterface = "com.sobey.cmdbuild.webservice.ServerPortSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class ServerPortSoapServiceImpl extends BasicSoapSevcie implements ServerPortSoapService {
	@Override
	public DTOResult<ServerPortDTO> findServerPort(@WebParam(name = "id") Integer id) {
		DTOResult<ServerPortDTO> result = new DTOResult<ServerPortDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ServerPort serverPort = comm.serverPortService.findServerPort(id);
			Validate.notNull(serverPort, ERROR.OBJECT_NULL);
			ServerPortDTO serverPortDTO = BeanMapper.map(serverPort, ServerPortDTO.class);
			result.setDto(serverPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerPortDTO> findServerPortByCode(@WebParam(name = "code") String code) {
		DTOResult<ServerPortDTO> result = new DTOResult<ServerPortDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			ServerPort serverPort = comm.serverPortService.findByCode(code);
			Validate.notNull(serverPort, ERROR.OBJECT_NULL);
			ServerPortDTO serverPortDTO = BeanMapper.map(serverPort, ServerPortDTO.class);
			result.setDto(serverPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createServerPort(@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(serverPortDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.serverPortService.findByCode(serverPortDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			ServerPort serverPort = BeanMapper.map(serverPortDTO, ServerPort.class);
			BeanValidators.validateWithException(validator, serverPort);
			comm.serverPortService.saveOrUpdate(serverPort);
			return new IdResult(serverPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateServerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(serverPortDTO, ERROR.INPUT_NULL);
			ServerPort serverPort = comm.serverPortService.findServerPort(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.serverPortService.findByCode(serverPortDTO.getCode()) == null
					|| serverPort.getCode().equals(serverPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			ServerPort serverPortEntity = BeanMapper.map(serverPortDTO, ServerPort.class);
			BeanMapper.copy(serverPortEntity, serverPort);
			serverPortEntity.setIdClass(ServerPort.class.getSimpleName());
			serverPortEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.serverPortService.saveOrUpdate(serverPortEntity);
			return new IdResult(serverPort.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteServerPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ServerPort serverPort = comm.serverPortService.findServerPort(id);
			ServerPort serverPortEntity = BeanMapper.map(findServerPort(id).getDto(), ServerPort.class);
			BeanMapper.copy(serverPortEntity, serverPort);
			serverPortEntity.setIdClass(ServerPort.class.getSimpleName());
			serverPortEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.serverPortService.saveOrUpdate(serverPortEntity);
			return new IdResult(serverPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ServerPortDTO> getServerPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ServerPortDTO> result = new PaginationResult<ServerPortDTO>();
		try {
			result = comm.serverPortService.getServerPortDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ServerPortDTO> getServerPortList() {
		DTOListResult<ServerPortDTO> result = new DTOListResult<ServerPortDTO>();
		try {
			List<ServerPort> companies = comm.serverPortService.getCompanies();
			List<ServerPortDTO> list = BeanMapper.mapList(companies, ServerPortDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}