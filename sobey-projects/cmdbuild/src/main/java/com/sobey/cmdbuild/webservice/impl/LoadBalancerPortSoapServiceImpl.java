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
import com.sobey.cmdbuild.entity.LoadBalancerPort;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.LoadBalancerPortSoapService;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "LoadBalancerPortService", endpointInterface = "com.sobey.cmdbuild.webservice.LoadBalancerPortSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class LoadBalancerPortSoapServiceImpl extends BasicSoapSevcie implements LoadBalancerPortSoapService {
	@Override
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPort(@WebParam(name = "id") Integer id) {
		DTOResult<LoadBalancerPortDTO> result = new DTOResult<LoadBalancerPortDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);
			Validate.notNull(loadBalancerPort, ERROR.OBJECT_NULL);
			LoadBalancerPortDTO loadBalancerPortDTO = BeanMapper.map(loadBalancerPort, LoadBalancerPortDTO.class);
			result.setDto(loadBalancerPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPortByCode(@WebParam(name = "code") String code) {
		DTOResult<LoadBalancerPortDTO> result = new DTOResult<LoadBalancerPortDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findByCode(code);
			Validate.notNull(loadBalancerPort, ERROR.OBJECT_NULL);
			LoadBalancerPortDTO loadBalancerPortDTO = BeanMapper.map(loadBalancerPort, LoadBalancerPortDTO.class);
			result.setDto(loadBalancerPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createLoadBalancerPort(
			@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(loadBalancerPortDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerPortService.findByCode(loadBalancerPortDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			LoadBalancerPort loadBalancerPort = BeanMapper.map(loadBalancerPortDTO, LoadBalancerPort.class);
			BeanValidators.validateWithException(validator, loadBalancerPort);
			comm.loadBalancerPortService.saveOrUpdate(loadBalancerPort);
			return new IdResult(loadBalancerPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateLoadBalancerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(loadBalancerPortDTO, ERROR.INPUT_NULL);
			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerPortService.findByCode(loadBalancerPortDTO.getCode()) == null
					|| loadBalancerPort.getCode().equals(loadBalancerPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			LoadBalancerPort loadBalancerPortEntity = BeanMapper.map(loadBalancerPortDTO, LoadBalancerPort.class);
			BeanMapper.copy(loadBalancerPortEntity, loadBalancerPort);
			loadBalancerPortEntity.setIdClass(LoadBalancerPort.class.getSimpleName());
			loadBalancerPortEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.loadBalancerPortService.saveOrUpdate(loadBalancerPortEntity);
			return new IdResult(loadBalancerPort.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteLoadBalancerPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);
			LoadBalancerPort loadBalancerPortEntity = BeanMapper.map(findLoadBalancerPort(id).getDto(),
					LoadBalancerPort.class);
			BeanMapper.copy(loadBalancerPortEntity, loadBalancerPort);
			loadBalancerPortEntity.setIdClass(LoadBalancerPort.class.getSimpleName());
			loadBalancerPortEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.loadBalancerPortService.saveOrUpdate(loadBalancerPortEntity);
			return new IdResult(loadBalancerPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LoadBalancerPortDTO> getLoadBalancerPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<LoadBalancerPortDTO> result = new PaginationResult<LoadBalancerPortDTO>();
		try {
			result = comm.loadBalancerPortService.getLoadBalancerPortDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<LoadBalancerPortDTO> getLoadBalancerPortList() {
		DTOListResult<LoadBalancerPortDTO> result = new DTOListResult<LoadBalancerPortDTO>();
		try {
			List<LoadBalancerPort> companies = comm.loadBalancerPortService.getCompanies();
			List<LoadBalancerPortDTO> list = BeanMapper.mapList(companies, LoadBalancerPortDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}