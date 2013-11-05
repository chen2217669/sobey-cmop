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
import com.sobey.cmdbuild.entity.FirewallHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.FirewallHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.FirewallHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "FirewallHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.FirewallHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FirewallHistorySoapServiceImpl extends BasicSoapSevcie implements FirewallHistorySoapService {
	@Override
	public DTOResult<FirewallHistoryDTO> findFirewallHistory(@WebParam(name = "id") Integer id) {
		DTOResult<FirewallHistoryDTO> result = new DTOResult<FirewallHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FirewallHistory firewallHistory = comm.firewallHistoryService.findFirewallHistory(id);
			Validate.notNull(firewallHistory, ERROR.OBJECT_NULL);
			FirewallHistoryDTO firewallHistoryDTO = BeanMapper.map(firewallHistory, FirewallHistoryDTO.class);
			result.setDto(firewallHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallHistoryDTO> findFirewallHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<FirewallHistoryDTO> result = new DTOResult<FirewallHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			FirewallHistory firewallHistory = comm.firewallHistoryService.findByCode(code);
			Validate.notNull(firewallHistory, ERROR.OBJECT_NULL);
			FirewallHistoryDTO firewallHistoryDTO = BeanMapper.map(firewallHistory, FirewallHistoryDTO.class);
			result.setDto(firewallHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createFirewallHistory(@WebParam(name = "firewallHistoryDTO") FirewallHistoryDTO firewallHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(firewallHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallHistoryService.findByCode(firewallHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			FirewallHistory firewallHistory = BeanMapper.map(firewallHistoryDTO, FirewallHistory.class);
			BeanValidators.validateWithException(validator, firewallHistory);
			comm.firewallHistoryService.saveOrUpdate(firewallHistory);
			return new IdResult(firewallHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFirewallHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallHistoryDTO") FirewallHistoryDTO firewallHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(firewallHistoryDTO, ERROR.INPUT_NULL);
			FirewallHistory firewallHistory = comm.firewallHistoryService.findFirewallHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallHistoryService.findByCode(firewallHistoryDTO.getCode()) == null
					|| firewallHistory.getCode().equals(firewallHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			FirewallHistory firewallHistoryEntity = BeanMapper.map(firewallHistoryDTO, FirewallHistory.class);
			BeanMapper.copy(firewallHistoryEntity, firewallHistory);
			firewallHistoryEntity.setIdClass(FirewallHistory.class.getSimpleName());
			firewallHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.firewallHistoryService.saveOrUpdate(firewallHistoryEntity);
			return new IdResult(firewallHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFirewallHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FirewallHistory firewallHistory = comm.firewallHistoryService.findFirewallHistory(id);
			FirewallHistory firewallHistoryEntity = BeanMapper.map(findFirewallHistory(id).getDto(),
					FirewallHistory.class);
			BeanMapper.copy(firewallHistoryEntity, firewallHistory);
			firewallHistoryEntity.setIdClass(FirewallHistory.class.getSimpleName());
			firewallHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.firewallHistoryService.saveOrUpdate(firewallHistoryEntity);
			return new IdResult(firewallHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FirewallHistoryDTO> getFirewallHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FirewallHistoryDTO> result = new PaginationResult<FirewallHistoryDTO>();
		try {
			result = comm.firewallHistoryService.getFirewallHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FirewallHistoryDTO> getFirewallHistoryList() {
		DTOListResult<FirewallHistoryDTO> result = new DTOListResult<FirewallHistoryDTO>();
		try {
			List<FirewallHistory> companies = comm.firewallHistoryService.getCompanies();
			List<FirewallHistoryDTO> list = BeanMapper.mapList(companies, FirewallHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}