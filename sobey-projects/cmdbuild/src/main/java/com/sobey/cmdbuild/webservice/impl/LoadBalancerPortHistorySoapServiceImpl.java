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
import com.sobey.cmdbuild.entity.LoadBalancerPortHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.LoadBalancerPortHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "LoadBalancerPortHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.LoadBalancerPortHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class LoadBalancerPortHistorySoapServiceImpl extends BasicSoapSevcie implements
		LoadBalancerPortHistorySoapService {
	@Override
	public DTOResult<LoadBalancerPortHistoryDTO> findLoadBalancerPortHistory(@WebParam(name = "id") Integer id) {
		DTOResult<LoadBalancerPortHistoryDTO> result = new DTOResult<LoadBalancerPortHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			LoadBalancerPortHistory loadBalancerPortHistory = comm.loadBalancerPortHistoryService
					.findLoadBalancerPortHistory(id);
			Validate.notNull(loadBalancerPortHistory, ERROR.OBJECT_NULL);
			LoadBalancerPortHistoryDTO loadBalancerPortHistoryDTO = BeanMapper.map(loadBalancerPortHistory,
					LoadBalancerPortHistoryDTO.class);
			result.setDto(loadBalancerPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LoadBalancerPortHistoryDTO> findLoadBalancerPortHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<LoadBalancerPortHistoryDTO> result = new DTOResult<LoadBalancerPortHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			LoadBalancerPortHistory loadBalancerPortHistory = comm.loadBalancerPortHistoryService.findByCode(code);
			Validate.notNull(loadBalancerPortHistory, ERROR.OBJECT_NULL);
			LoadBalancerPortHistoryDTO loadBalancerPortHistoryDTO = BeanMapper.map(loadBalancerPortHistory,
					LoadBalancerPortHistoryDTO.class);
			result.setDto(loadBalancerPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createLoadBalancerPortHistory(
			@WebParam(name = "loadBalancerPortHistoryDTO") LoadBalancerPortHistoryDTO loadBalancerPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(loadBalancerPortHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.loadBalancerPortHistoryService.findByCode(loadBalancerPortHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			LoadBalancerPortHistory loadBalancerPortHistory = BeanMapper.map(loadBalancerPortHistoryDTO,
					LoadBalancerPortHistory.class);
			BeanValidators.validateWithException(validator, loadBalancerPortHistory);
			comm.loadBalancerPortHistoryService.saveOrUpdate(loadBalancerPortHistory);
			return new IdResult(loadBalancerPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateLoadBalancerPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerPortHistoryDTO") LoadBalancerPortHistoryDTO loadBalancerPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(loadBalancerPortHistoryDTO, ERROR.INPUT_NULL);
			LoadBalancerPortHistory loadBalancerPortHistory = comm.loadBalancerPortHistoryService
					.findLoadBalancerPortHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.loadBalancerPortHistoryService.findByCode(loadBalancerPortHistoryDTO.getCode()) == null
							|| loadBalancerPortHistory.getCode().equals(loadBalancerPortHistoryDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			LoadBalancerPortHistory loadBalancerPortHistoryEntity = BeanMapper.map(loadBalancerPortHistoryDTO,
					LoadBalancerPortHistory.class);
			BeanMapper.copy(loadBalancerPortHistoryEntity, loadBalancerPortHistory);
			loadBalancerPortHistoryEntity.setIdClass(LoadBalancerPortHistory.class.getSimpleName());
			loadBalancerPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.loadBalancerPortHistoryService.saveOrUpdate(loadBalancerPortHistoryEntity);
			return new IdResult(loadBalancerPortHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteLoadBalancerPortHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			LoadBalancerPortHistory loadBalancerPortHistory = comm.loadBalancerPortHistoryService
					.findLoadBalancerPortHistory(id);
			LoadBalancerPortHistory loadBalancerPortHistoryEntity = BeanMapper.map(findLoadBalancerPortHistory(id)
					.getDto(), LoadBalancerPortHistory.class);
			BeanMapper.copy(loadBalancerPortHistoryEntity, loadBalancerPortHistory);
			loadBalancerPortHistoryEntity.setIdClass(LoadBalancerPortHistory.class.getSimpleName());
			loadBalancerPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.loadBalancerPortHistoryService.saveOrUpdate(loadBalancerPortHistoryEntity);
			return new IdResult(loadBalancerPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LoadBalancerPortHistoryDTO> getLoadBalancerPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<LoadBalancerPortHistoryDTO> result = new PaginationResult<LoadBalancerPortHistoryDTO>();
		try {
			result = comm.loadBalancerPortHistoryService.getLoadBalancerPortHistoryDTOPagination(searchParams,
					pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<LoadBalancerPortHistoryDTO> getLoadBalancerPortHistoryList() {
		DTOListResult<LoadBalancerPortHistoryDTO> result = new DTOListResult<LoadBalancerPortHistoryDTO>();
		try {
			List<LoadBalancerPortHistory> companies = comm.loadBalancerPortHistoryService.getCompanies();
			List<LoadBalancerPortHistoryDTO> list = BeanMapper.mapList(companies, LoadBalancerPortHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}