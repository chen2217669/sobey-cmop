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
import com.sobey.cmdbuild.entity.Firewall;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.FirewallSoapService;
import com.sobey.cmdbuild.webservice.response.dto.FirewallDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "FirewallService", endpointInterface = "com.sobey.cmdbuild.webservice.FirewallSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FirewallSoapServiceImpl extends BasicSoapSevcie implements FirewallSoapService {
	@Override
	public DTOResult<FirewallDTO> findFirewall(@WebParam(name = "id") Integer id) {
		DTOResult<FirewallDTO> result = new DTOResult<FirewallDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Firewall firewall = comm.firewallService.findFirewall(id);
			Validate.notNull(firewall, ERROR.OBJECT_NULL);
			FirewallDTO firewallDTO = BeanMapper.map(firewall, FirewallDTO.class);
			result.setDto(firewallDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallDTO> findFirewallByCode(@WebParam(name = "code") String code) {
		DTOResult<FirewallDTO> result = new DTOResult<FirewallDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Firewall firewall = comm.firewallService.findByCode(code);
			Validate.notNull(firewall, ERROR.OBJECT_NULL);
			FirewallDTO firewallDTO = BeanMapper.map(firewall, FirewallDTO.class);
			result.setDto(firewallDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createFirewall(@WebParam(name = "firewallDTO") FirewallDTO firewallDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(firewallDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallService.findByCode(firewallDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Firewall firewall = BeanMapper.map(firewallDTO, Firewall.class);
			BeanValidators.validateWithException(validator, firewall);
			comm.firewallService.saveOrUpdate(firewall);
			return new IdResult(firewall.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFirewall(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallDTO") FirewallDTO firewallDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(firewallDTO, ERROR.INPUT_NULL);
			Firewall firewall = comm.firewallService.findFirewall(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallService.findByCode(firewallDTO.getCode()) == null
					|| firewall.getCode().equals(firewallDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Firewall firewallEntity = BeanMapper.map(firewallDTO, Firewall.class);
			BeanMapper.copy(firewallEntity, firewall);
			firewallEntity.setIdClass(Firewall.class.getSimpleName());
			firewallEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.firewallService.saveOrUpdate(firewallEntity);
			return new IdResult(firewall.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFirewall(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Firewall firewall = comm.firewallService.findFirewall(id);
			Firewall firewallEntity = BeanMapper.map(findFirewall(id).getDto(), Firewall.class);
			BeanMapper.copy(firewallEntity, firewall);
			firewallEntity.setIdClass(Firewall.class.getSimpleName());
			firewallEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.firewallService.saveOrUpdate(firewallEntity);
			return new IdResult(firewall.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FirewallDTO> getFirewallPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FirewallDTO> result = new PaginationResult<FirewallDTO>();
		try {
			result = comm.firewallService.getFirewallDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FirewallDTO> getFirewallList() {
		DTOListResult<FirewallDTO> result = new DTOListResult<FirewallDTO>();
		try {
			List<Firewall> companies = comm.firewallService.getCompanies();
			List<FirewallDTO> list = BeanMapper.mapList(companies, FirewallDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}