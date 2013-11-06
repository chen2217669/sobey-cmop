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
import com.sobey.cmdbuild.entity.Dns;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.DnsSoapService;
import com.sobey.cmdbuild.webservice.response.dto.DnsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "DnsService", endpointInterface = "com.sobey.cmdbuild.webservice.DnsSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class DnsSoapServiceImpl extends BasicSoapSevcie implements DnsSoapService {
	@Override
	public DTOResult<DnsDTO> findDns(@WebParam(name = "id") Integer id) {
		DTOResult<DnsDTO> result = new DTOResult<DnsDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Dns dns = comm.dnsService.findDns(id);
			Validate.notNull(dns, ERROR.OBJECT_NULL);
			DnsDTO dnsDTO = BeanMapper.map(dns, DnsDTO.class);
			result.setDto(dnsDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DnsDTO> findDnsByCode(@WebParam(name = "code") String code) {
		DTOResult<DnsDTO> result = new DTOResult<DnsDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Dns dns = comm.dnsService.findByCode(code);
			Validate.notNull(dns, ERROR.OBJECT_NULL);
			DnsDTO dnsDTO = BeanMapper.map(dns, DnsDTO.class);
			result.setDto(dnsDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createDns(@WebParam(name = "dnsDTO") DnsDTO dnsDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(dnsDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.dnsService.findByCode(dnsDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Dns dns = BeanMapper.map(dnsDTO, Dns.class);
			BeanValidators.validateWithException(validator, dns);
			comm.dnsService.saveOrUpdate(dns);
			return new IdResult(dns.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateDns(@WebParam(name = "id") Integer id, @WebParam(name = "dnsDTO") DnsDTO dnsDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(dnsDTO, ERROR.INPUT_NULL);
			Dns dns = comm.dnsService.findDns(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.dnsService.findByCode(dnsDTO.getCode()) == null || dns.getCode().equals(dnsDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Dns dnsEntity = BeanMapper.map(dnsDTO, Dns.class);
			BeanMapper.copy(dnsEntity, dns);
			dnsEntity.setIdClass(Dns.class.getSimpleName());
			dnsEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.dnsService.saveOrUpdate(dnsEntity);
			return new IdResult(dns.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteDns(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Dns dns = comm.dnsService.findDns(id);
			Dns dnsEntity = BeanMapper.map(findDns(id).getDto(), Dns.class);
			BeanMapper.copy(dnsEntity, dns);
			dnsEntity.setIdClass(Dns.class.getSimpleName());
			dnsEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.dnsService.saveOrUpdate(dnsEntity);
			return new IdResult(dns.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<DnsDTO> getDnsPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<DnsDTO> result = new PaginationResult<DnsDTO>();
		try {
			result = comm.dnsService.getDnsDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<DnsDTO> getDnsList() {
		DTOListResult<DnsDTO> result = new DTOListResult<DnsDTO>();
		try {
			List<Dns> companies = comm.dnsService.getCompanies();
			List<DnsDTO> list = BeanMapper.mapList(companies, DnsDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}