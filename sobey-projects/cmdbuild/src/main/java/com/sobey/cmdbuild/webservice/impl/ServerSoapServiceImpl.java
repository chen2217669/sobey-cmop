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
import com.sobey.cmdbuild.entity.Server;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.ServerSoapService;
import com.sobey.cmdbuild.webservice.response.dto.ServerDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "ServerService", endpointInterface = "com.sobey.cmdbuild.webservice.ServerSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class ServerSoapServiceImpl extends BasicSoapSevcie implements ServerSoapService {
	@Override
	public DTOResult<ServerDTO> findServer(@WebParam(name = "id") Integer id) {
		DTOResult<ServerDTO> result = new DTOResult<ServerDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Server server = comm.serverService.findServer(id);
			Validate.notNull(server, ERROR.OBJECT_NULL);
			ServerDTO serverDTO = BeanMapper.map(server, ServerDTO.class);
			result.setDto(serverDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerDTO> findServerByCode(@WebParam(name = "code") String code) {
		DTOResult<ServerDTO> result = new DTOResult<ServerDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Server server = comm.serverService.findByCode(code);
			Validate.notNull(server, ERROR.OBJECT_NULL);
			ServerDTO serverDTO = BeanMapper.map(server, ServerDTO.class);
			result.setDto(serverDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createServer(@WebParam(name = "serverDTO") ServerDTO serverDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(serverDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.serverService.findByCode(serverDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Server server = BeanMapper.map(serverDTO, Server.class);
			BeanValidators.validateWithException(validator, server);
			comm.serverService.saveOrUpdate(server);
			return new IdResult(server.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateServer(@WebParam(name = "id") Integer id, @WebParam(name = "serverDTO") ServerDTO serverDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(serverDTO, ERROR.INPUT_NULL);
			Server server = comm.serverService.findServer(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.serverService.findByCode(serverDTO.getCode()) == null
							|| server.getCode().equals(serverDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Server serverEntity = BeanMapper.map(serverDTO, Server.class);
			BeanMapper.copy(serverEntity, server);
			serverEntity.setIdClass(Server.class.getSimpleName());
			serverEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.serverService.saveOrUpdate(serverEntity);
			return new IdResult(server.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteServer(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Server server = comm.serverService.findServer(id);
			Server serverEntity = BeanMapper.map(findServer(id).getDto(), Server.class);
			BeanMapper.copy(serverEntity, server);
			serverEntity.setIdClass(Server.class.getSimpleName());
			serverEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.serverService.saveOrUpdate(serverEntity);
			return new IdResult(server.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ServerDTO> getServerPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ServerDTO> result = new PaginationResult<ServerDTO>();
		try {
			result = comm.serverService.getServerDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ServerDTO> getServerList() {
		DTOListResult<ServerDTO> result = new DTOListResult<ServerDTO>();
		try {
			List<Server> companies = comm.serverService.getCompanies();
			List<ServerDTO> list = BeanMapper.mapList(companies, ServerDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}