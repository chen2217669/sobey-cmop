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
import com.sobey.cmdbuild.entity.ServerHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.ServerHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.ServerHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "ServerHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.ServerHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class ServerHistorySoapServiceImpl extends BasicSoapSevcie implements ServerHistorySoapService {
	@Override
	public DTOResult<ServerHistoryDTO> findServerHistory(@WebParam(name = "id") Integer id) {
		DTOResult<ServerHistoryDTO> result = new DTOResult<ServerHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ServerHistory serverHistory = comm.serverHistoryService.findServerHistory(id);
			Validate.notNull(serverHistory, ERROR.OBJECT_NULL);
			ServerHistoryDTO serverHistoryDTO = BeanMapper.map(serverHistory, ServerHistoryDTO.class);
			result.setDto(serverHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerHistoryDTO> findServerHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<ServerHistoryDTO> result = new DTOResult<ServerHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			ServerHistory serverHistory = comm.serverHistoryService.findByCode(code);
			Validate.notNull(serverHistory, ERROR.OBJECT_NULL);
			ServerHistoryDTO serverHistoryDTO = BeanMapper.map(serverHistory, ServerHistoryDTO.class);
			result.setDto(serverHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createServerHistory(@WebParam(name = "serverHistoryDTO") ServerHistoryDTO serverHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(serverHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.serverHistoryService.findByCode(serverHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			ServerHistory serverHistory = BeanMapper.map(serverHistoryDTO, ServerHistory.class);
			BeanValidators.validateWithException(validator, serverHistory);
			comm.serverHistoryService.saveOrUpdate(serverHistory);
			return new IdResult(serverHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateServerHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "serverHistoryDTO") ServerHistoryDTO serverHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(serverHistoryDTO, ERROR.INPUT_NULL);
			ServerHistory serverHistory = comm.serverHistoryService.findServerHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.serverHistoryService.findByCode(serverHistoryDTO.getCode()) == null
					|| serverHistory.getCode().equals(serverHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			ServerHistory serverHistoryEntity = BeanMapper.map(serverHistoryDTO, ServerHistory.class);
			BeanMapper.copy(serverHistoryEntity, serverHistory);
			serverHistoryEntity.setIdClass(ServerHistory.class.getSimpleName());
			serverHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.serverHistoryService.saveOrUpdate(serverHistoryEntity);
			return new IdResult(serverHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteServerHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ServerHistory serverHistory = comm.serverHistoryService.findServerHistory(id);
			ServerHistory serverHistoryEntity = BeanMapper.map(findServerHistory(id).getDto(), ServerHistory.class);
			BeanMapper.copy(serverHistoryEntity, serverHistory);
			serverHistoryEntity.setIdClass(ServerHistory.class.getSimpleName());
			serverHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.serverHistoryService.saveOrUpdate(serverHistoryEntity);
			return new IdResult(serverHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ServerHistoryDTO> getServerHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ServerHistoryDTO> result = new PaginationResult<ServerHistoryDTO>();
		try {
			result = comm.serverHistoryService.getServerHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ServerHistoryDTO> getServerHistoryList() {
		DTOListResult<ServerHistoryDTO> result = new DTOListResult<ServerHistoryDTO>();
		try {
			List<ServerHistory> companies = comm.serverHistoryService.getCompanies();
			List<ServerHistoryDTO> list = BeanMapper.mapList(companies, ServerHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}