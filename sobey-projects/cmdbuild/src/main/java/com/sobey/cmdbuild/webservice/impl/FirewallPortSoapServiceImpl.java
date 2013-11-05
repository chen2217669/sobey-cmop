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
import com.sobey.cmdbuild.entity.FirewallPort;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.FirewallPortSoapService;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "FirewallPortService", endpointInterface = "com.sobey.cmdbuild.webservice.FirewallPortSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FirewallPortSoapServiceImpl extends BasicSoapSevcie implements FirewallPortSoapService {
	@Override
	public DTOResult<FirewallPortDTO> findFirewallPort(@WebParam(name = "id") Integer id) {
		DTOResult<FirewallPortDTO> result = new DTOResult<FirewallPortDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);
			Validate.notNull(firewallPort, ERROR.OBJECT_NULL);
			FirewallPortDTO firewallPortDTO = BeanMapper.map(firewallPort, FirewallPortDTO.class);
			result.setDto(firewallPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallPortDTO> findFirewallPortByCode(@WebParam(name = "code") String code) {
		DTOResult<FirewallPortDTO> result = new DTOResult<FirewallPortDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			FirewallPort firewallPort = comm.firewallPortService.findByCode(code);
			Validate.notNull(firewallPort, ERROR.OBJECT_NULL);
			FirewallPortDTO firewallPortDTO = BeanMapper.map(firewallPort, FirewallPortDTO.class);
			result.setDto(firewallPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createFirewallPort(@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(firewallPortDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallPortService.findByCode(firewallPortDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			FirewallPort firewallPort = BeanMapper.map(firewallPortDTO, FirewallPort.class);
			BeanValidators.validateWithException(validator, firewallPort);
			comm.firewallPortService.saveOrUpdate(firewallPort);
			return new IdResult(firewallPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFirewallPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(firewallPortDTO, ERROR.INPUT_NULL);
			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallPortService.findByCode(firewallPortDTO.getCode()) == null
					|| firewallPort.getCode().equals(firewallPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			FirewallPort firewallPortEntity = BeanMapper.map(firewallPortDTO, FirewallPort.class);
			BeanMapper.copy(firewallPortEntity, firewallPort);
			firewallPortEntity.setIdClass(FirewallPort.class.getSimpleName());
			firewallPortEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.firewallPortService.saveOrUpdate(firewallPortEntity);
			return new IdResult(firewallPort.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFirewallPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);
			FirewallPort firewallPortEntity = BeanMapper.map(findFirewallPort(id).getDto(), FirewallPort.class);
			BeanMapper.copy(firewallPortEntity, firewallPort);
			firewallPortEntity.setIdClass(FirewallPort.class.getSimpleName());
			firewallPortEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.firewallPortService.saveOrUpdate(firewallPortEntity);
			return new IdResult(firewallPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FirewallPortDTO> getFirewallPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FirewallPortDTO> result = new PaginationResult<FirewallPortDTO>();
		try {
			result = comm.firewallPortService.getFirewallPortDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FirewallPortDTO> getFirewallPortList() {
		DTOListResult<FirewallPortDTO> result = new DTOListResult<FirewallPortDTO>();
		try {
			List<FirewallPort> companies = comm.firewallPortService.getCompanies();
			List<FirewallPortDTO> list = BeanMapper.mapList(companies, FirewallPortDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}