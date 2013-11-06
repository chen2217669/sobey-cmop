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
import com.sobey.cmdbuild.entity.DnsHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.DnsHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.DnsHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "DnsHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.DnsHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class DnsHistorySoapServiceImpl extends BasicSoapSevcie implements DnsHistorySoapService {
	@Override
	public DTOResult<DnsHistoryDTO> findDnsHistory(@WebParam(name = "id") Integer id) {
		DTOResult<DnsHistoryDTO> result = new DTOResult<DnsHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			DnsHistory dnsHistory = comm.dnsHistoryService.findDnsHistory(id);
			Validate.notNull(dnsHistory, ERROR.OBJECT_NULL);
			DnsHistoryDTO dnsHistoryDTO = BeanMapper.map(dnsHistory, DnsHistoryDTO.class);
			result.setDto(dnsHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DnsHistoryDTO> findDnsHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<DnsHistoryDTO> result = new DTOResult<DnsHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			DnsHistory dnsHistory = comm.dnsHistoryService.findByCode(code);
			Validate.notNull(dnsHistory, ERROR.OBJECT_NULL);
			DnsHistoryDTO dnsHistoryDTO = BeanMapper.map(dnsHistory, DnsHistoryDTO.class);
			result.setDto(dnsHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createDnsHistory(@WebParam(name = "dnsHistoryDTO") DnsHistoryDTO dnsHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(dnsHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.dnsHistoryService.findByCode(dnsHistoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			DnsHistory dnsHistory = BeanMapper.map(dnsHistoryDTO, DnsHistory.class);
			BeanValidators.validateWithException(validator, dnsHistory);
			comm.dnsHistoryService.saveOrUpdate(dnsHistory);
			return new IdResult(dnsHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateDnsHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "dnsHistoryDTO") DnsHistoryDTO dnsHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(dnsHistoryDTO, ERROR.INPUT_NULL);
			DnsHistory dnsHistory = comm.dnsHistoryService.findDnsHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.dnsHistoryService.findByCode(dnsHistoryDTO.getCode()) == null
					|| dnsHistory.getCode().equals(dnsHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			DnsHistory dnsHistoryEntity = BeanMapper.map(dnsHistoryDTO, DnsHistory.class);
			BeanMapper.copy(dnsHistoryEntity, dnsHistory);
			dnsHistoryEntity.setIdClass(DnsHistory.class.getSimpleName());
			dnsHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.dnsHistoryService.saveOrUpdate(dnsHistoryEntity);
			return new IdResult(dnsHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteDnsHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			DnsHistory dnsHistory = comm.dnsHistoryService.findDnsHistory(id);
			DnsHistory dnsHistoryEntity = BeanMapper.map(findDnsHistory(id).getDto(), DnsHistory.class);
			BeanMapper.copy(dnsHistoryEntity, dnsHistory);
			dnsHistoryEntity.setIdClass(DnsHistory.class.getSimpleName());
			dnsHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.dnsHistoryService.saveOrUpdate(dnsHistoryEntity);
			return new IdResult(dnsHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<DnsHistoryDTO> getDnsHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<DnsHistoryDTO> result = new PaginationResult<DnsHistoryDTO>();
		try {
			result = comm.dnsHistoryService.getDnsHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<DnsHistoryDTO> getDnsHistoryList() {
		DTOListResult<DnsHistoryDTO> result = new DTOListResult<DnsHistoryDTO>();
		try {
			List<DnsHistory> companies = comm.dnsHistoryService.getCompanies();
			List<DnsHistoryDTO> list = BeanMapper.mapList(companies, DnsHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}