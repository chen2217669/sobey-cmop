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
import com.sobey.cmdbuild.entity.ServerPortHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.ServerPortHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "ServerPortHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.ServerPortHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class ServerPortHistorySoapServiceImpl extends BasicSoapSevcie implements ServerPortHistorySoapService {
	@Override
	public DTOResult<ServerPortHistoryDTO> findServerPortHistory(@WebParam(name = "id") Integer id) {
		DTOResult<ServerPortHistoryDTO> result = new DTOResult<ServerPortHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ServerPortHistory serverPortHistory = comm.serverPortHistoryService.findServerPortHistory(id);
			Validate.notNull(serverPortHistory, ERROR.OBJECT_NULL);
			ServerPortHistoryDTO serverPortHistoryDTO = BeanMapper.map(serverPortHistory, ServerPortHistoryDTO.class);
			result.setDto(serverPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerPortHistoryDTO> findServerPortHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<ServerPortHistoryDTO> result = new DTOResult<ServerPortHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			ServerPortHistory serverPortHistory = comm.serverPortHistoryService.findByCode(code);
			Validate.notNull(serverPortHistory, ERROR.OBJECT_NULL);
			ServerPortHistoryDTO serverPortHistoryDTO = BeanMapper.map(serverPortHistory, ServerPortHistoryDTO.class);
			result.setDto(serverPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createServerPortHistory(
			@WebParam(name = "serverPortHistoryDTO") ServerPortHistoryDTO serverPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(serverPortHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.serverPortHistoryService.findByCode(serverPortHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			ServerPortHistory serverPortHistory = BeanMapper.map(serverPortHistoryDTO, ServerPortHistory.class);
			BeanValidators.validateWithException(validator, serverPortHistory);
			comm.serverPortHistoryService.saveOrUpdate(serverPortHistory);
			return new IdResult(serverPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateServerPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "serverPortHistoryDTO") ServerPortHistoryDTO serverPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(serverPortHistoryDTO, ERROR.INPUT_NULL);
			ServerPortHistory serverPortHistory = comm.serverPortHistoryService.findServerPortHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.serverPortHistoryService.findByCode(serverPortHistoryDTO.getCode()) == null
					|| serverPortHistory.getCode().equals(serverPortHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			ServerPortHistory serverPortHistoryEntity = BeanMapper.map(serverPortHistoryDTO, ServerPortHistory.class);
			BeanMapper.copy(serverPortHistoryEntity, serverPortHistory);
			serverPortHistoryEntity.setIdClass(ServerPortHistory.class.getSimpleName());
			serverPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.serverPortHistoryService.saveOrUpdate(serverPortHistoryEntity);
			return new IdResult(serverPortHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteServerPortHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ServerPortHistory serverPortHistory = comm.serverPortHistoryService.findServerPortHistory(id);
			ServerPortHistory serverPortHistoryEntity = BeanMapper.map(findServerPortHistory(id).getDto(),
					ServerPortHistory.class);
			BeanMapper.copy(serverPortHistoryEntity, serverPortHistory);
			serverPortHistoryEntity.setIdClass(ServerPortHistory.class.getSimpleName());
			serverPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.serverPortHistoryService.saveOrUpdate(serverPortHistoryEntity);
			return new IdResult(serverPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ServerPortHistoryDTO> getServerPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ServerPortHistoryDTO> result = new PaginationResult<ServerPortHistoryDTO>();
		try {
			result = comm.serverPortHistoryService
					.getServerPortHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ServerPortHistoryDTO> getServerPortHistoryList() {
		DTOListResult<ServerPortHistoryDTO> result = new DTOListResult<ServerPortHistoryDTO>();
		try {
			List<ServerPortHistory> companies = comm.serverPortHistoryService.getCompanies();
			List<ServerPortHistoryDTO> list = BeanMapper.mapList(companies, ServerPortHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}