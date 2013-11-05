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
import com.sobey.cmdbuild.entity.LoadBalancerHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.LoadBalancerHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "LoadBalancerHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.LoadBalancerHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class LoadBalancerHistorySoapServiceImpl extends BasicSoapSevcie implements LoadBalancerHistorySoapService {
	@Override
	public DTOResult<LoadBalancerHistoryDTO> findLoadBalancerHistory(@WebParam(name = "id") Integer id) {
		DTOResult<LoadBalancerHistoryDTO> result = new DTOResult<LoadBalancerHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			LoadBalancerHistory loadBalancerHistory = comm.loadBalancerHistoryService.findLoadBalancerHistory(id);
			Validate.notNull(loadBalancerHistory, ERROR.OBJECT_NULL);
			LoadBalancerHistoryDTO loadBalancerHistoryDTO = BeanMapper.map(loadBalancerHistory,
					LoadBalancerHistoryDTO.class);
			result.setDto(loadBalancerHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LoadBalancerHistoryDTO> findLoadBalancerHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<LoadBalancerHistoryDTO> result = new DTOResult<LoadBalancerHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			LoadBalancerHistory loadBalancerHistory = comm.loadBalancerHistoryService.findByCode(code);
			Validate.notNull(loadBalancerHistory, ERROR.OBJECT_NULL);
			LoadBalancerHistoryDTO loadBalancerHistoryDTO = BeanMapper.map(loadBalancerHistory,
					LoadBalancerHistoryDTO.class);
			result.setDto(loadBalancerHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createLoadBalancerHistory(
			@WebParam(name = "loadBalancerHistoryDTO") LoadBalancerHistoryDTO loadBalancerHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(loadBalancerHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerHistoryService.findByCode(loadBalancerHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			LoadBalancerHistory loadBalancerHistory = BeanMapper.map(loadBalancerHistoryDTO, LoadBalancerHistory.class);
			BeanValidators.validateWithException(validator, loadBalancerHistory);
			comm.loadBalancerHistoryService.saveOrUpdate(loadBalancerHistory);
			return new IdResult(loadBalancerHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateLoadBalancerHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerHistoryDTO") LoadBalancerHistoryDTO loadBalancerHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(loadBalancerHistoryDTO, ERROR.INPUT_NULL);
			LoadBalancerHistory loadBalancerHistory = comm.loadBalancerHistoryService.findLoadBalancerHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerHistoryService.findByCode(loadBalancerHistoryDTO.getCode()) == null
					|| loadBalancerHistory.getCode().equals(loadBalancerHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			LoadBalancerHistory loadBalancerHistoryEntity = BeanMapper.map(loadBalancerHistoryDTO,
					LoadBalancerHistory.class);
			BeanMapper.copy(loadBalancerHistoryEntity, loadBalancerHistory);
			loadBalancerHistoryEntity.setIdClass(LoadBalancerHistory.class.getSimpleName());
			loadBalancerHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.loadBalancerHistoryService.saveOrUpdate(loadBalancerHistoryEntity);
			return new IdResult(loadBalancerHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteLoadBalancerHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			LoadBalancerHistory loadBalancerHistory = comm.loadBalancerHistoryService.findLoadBalancerHistory(id);
			LoadBalancerHistory loadBalancerHistoryEntity = BeanMapper.map(findLoadBalancerHistory(id).getDto(),
					LoadBalancerHistory.class);
			BeanMapper.copy(loadBalancerHistoryEntity, loadBalancerHistory);
			loadBalancerHistoryEntity.setIdClass(LoadBalancerHistory.class.getSimpleName());
			loadBalancerHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.loadBalancerHistoryService.saveOrUpdate(loadBalancerHistoryEntity);
			return new IdResult(loadBalancerHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LoadBalancerHistoryDTO> getLoadBalancerHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<LoadBalancerHistoryDTO> result = new PaginationResult<LoadBalancerHistoryDTO>();
		try {
			result = comm.loadBalancerHistoryService.getLoadBalancerHistoryDTOPagination(searchParams, pageNumber,
					pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<LoadBalancerHistoryDTO> getLoadBalancerHistoryList() {
		DTOListResult<LoadBalancerHistoryDTO> result = new DTOListResult<LoadBalancerHistoryDTO>();
		try {
			List<LoadBalancerHistory> companies = comm.loadBalancerHistoryService.getCompanies();
			List<LoadBalancerHistoryDTO> list = BeanMapper.mapList(companies, LoadBalancerHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}