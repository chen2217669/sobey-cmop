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
import com.sobey.cmdbuild.entity.LoadBalancer;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.LoadBalancerSoapService;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "LoadBalancerService", endpointInterface = "com.sobey.cmdbuild.webservice.LoadBalancerSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class LoadBalancerSoapServiceImpl extends BasicSoapSevcie implements LoadBalancerSoapService {
	@Override
	public DTOResult<LoadBalancerDTO> findLoadBalancer(@WebParam(name = "id") Integer id) {
		DTOResult<LoadBalancerDTO> result = new DTOResult<LoadBalancerDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(id);
			Validate.notNull(loadBalancer, ERROR.OBJECT_NULL);
			LoadBalancerDTO loadBalancerDTO = BeanMapper.map(loadBalancer, LoadBalancerDTO.class);
			result.setDto(loadBalancerDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LoadBalancerDTO> findLoadBalancerByCode(@WebParam(name = "code") String code) {
		DTOResult<LoadBalancerDTO> result = new DTOResult<LoadBalancerDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			LoadBalancer loadBalancer = comm.loadBalancerService.findByCode(code);
			Validate.notNull(loadBalancer, ERROR.OBJECT_NULL);
			LoadBalancerDTO loadBalancerDTO = BeanMapper.map(loadBalancer, LoadBalancerDTO.class);
			result.setDto(loadBalancerDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createLoadBalancer(@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(loadBalancerDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerService.findByCode(loadBalancerDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			LoadBalancer loadBalancer = BeanMapper.map(loadBalancerDTO, LoadBalancer.class);
			BeanValidators.validateWithException(validator, loadBalancer);
			comm.loadBalancerService.saveOrUpdate(loadBalancer);
			return new IdResult(loadBalancer.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateLoadBalancer(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(loadBalancerDTO, ERROR.INPUT_NULL);
			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerService.findByCode(loadBalancerDTO.getCode()) == null
					|| loadBalancer.getCode().equals(loadBalancerDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			LoadBalancer loadBalancerEntity = BeanMapper.map(loadBalancerDTO, LoadBalancer.class);
			BeanMapper.copy(loadBalancerEntity, loadBalancer);
			loadBalancerEntity.setIdClass(LoadBalancer.class.getSimpleName());
			loadBalancerEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.loadBalancerService.saveOrUpdate(loadBalancerEntity);
			return new IdResult(loadBalancer.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteLoadBalancer(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(id);
			LoadBalancer loadBalancerEntity = BeanMapper.map(findLoadBalancer(id).getDto(), LoadBalancer.class);
			BeanMapper.copy(loadBalancerEntity, loadBalancer);
			loadBalancerEntity.setIdClass(LoadBalancer.class.getSimpleName());
			loadBalancerEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.loadBalancerService.saveOrUpdate(loadBalancerEntity);
			return new IdResult(loadBalancer.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LoadBalancerDTO> getLoadBalancerPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<LoadBalancerDTO> result = new PaginationResult<LoadBalancerDTO>();
		try {
			result = comm.loadBalancerService.getLoadBalancerDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<LoadBalancerDTO> getLoadBalancerList() {
		DTOListResult<LoadBalancerDTO> result = new DTOListResult<LoadBalancerDTO>();
		try {
			List<LoadBalancer> companies = comm.loadBalancerService.getCompanies();
			List<LoadBalancerDTO> list = BeanMapper.mapList(companies, LoadBalancerDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}