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
import com.sobey.cmdbuild.entity.FirewallPortHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.FirewallPortHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "FirewallPortHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.FirewallPortHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FirewallPortHistorySoapServiceImpl extends BasicSoapSevcie implements FirewallPortHistorySoapService {
	@Override
	public DTOResult<FirewallPortHistoryDTO> findFirewallPortHistory(@WebParam(name = "id") Integer id) {
		DTOResult<FirewallPortHistoryDTO> result = new DTOResult<FirewallPortHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FirewallPortHistory firewallPortHistory = comm.firewallPortHistoryService.findFirewallPortHistory(id);
			Validate.notNull(firewallPortHistory, ERROR.OBJECT_NULL);
			FirewallPortHistoryDTO firewallPortHistoryDTO = BeanMapper.map(firewallPortHistory,
					FirewallPortHistoryDTO.class);
			result.setDto(firewallPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallPortHistoryDTO> findFirewallPortHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<FirewallPortHistoryDTO> result = new DTOResult<FirewallPortHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			FirewallPortHistory firewallPortHistory = comm.firewallPortHistoryService.findByCode(code);
			Validate.notNull(firewallPortHistory, ERROR.OBJECT_NULL);
			FirewallPortHistoryDTO firewallPortHistoryDTO = BeanMapper.map(firewallPortHistory,
					FirewallPortHistoryDTO.class);
			result.setDto(firewallPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createFirewallPortHistory(
			@WebParam(name = "firewallPortHistoryDTO") FirewallPortHistoryDTO firewallPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(firewallPortHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallPortHistoryService.findByCode(firewallPortHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			FirewallPortHistory firewallPortHistory = BeanMapper.map(firewallPortHistoryDTO, FirewallPortHistory.class);
			BeanValidators.validateWithException(validator, firewallPortHistory);
			comm.firewallPortHistoryService.saveOrUpdate(firewallPortHistory);
			return new IdResult(firewallPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFirewallPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallPortHistoryDTO") FirewallPortHistoryDTO firewallPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(firewallPortHistoryDTO, ERROR.INPUT_NULL);
			FirewallPortHistory firewallPortHistory = comm.firewallPortHistoryService.findFirewallPortHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallPortHistoryService.findByCode(firewallPortHistoryDTO.getCode()) == null
					|| firewallPortHistory.getCode().equals(firewallPortHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			FirewallPortHistory firewallPortHistoryEntity = BeanMapper.map(firewallPortHistoryDTO,
					FirewallPortHistory.class);
			BeanMapper.copy(firewallPortHistoryEntity, firewallPortHistory);
			firewallPortHistoryEntity.setIdClass(FirewallPortHistory.class.getSimpleName());
			firewallPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.firewallPortHistoryService.saveOrUpdate(firewallPortHistoryEntity);
			return new IdResult(firewallPortHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFirewallPortHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FirewallPortHistory firewallPortHistory = comm.firewallPortHistoryService.findFirewallPortHistory(id);
			FirewallPortHistory firewallPortHistoryEntity = BeanMapper.map(findFirewallPortHistory(id).getDto(),
					FirewallPortHistory.class);
			BeanMapper.copy(firewallPortHistoryEntity, firewallPortHistory);
			firewallPortHistoryEntity.setIdClass(FirewallPortHistory.class.getSimpleName());
			firewallPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.firewallPortHistoryService.saveOrUpdate(firewallPortHistoryEntity);
			return new IdResult(firewallPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FirewallPortHistoryDTO> getFirewallPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FirewallPortHistoryDTO> result = new PaginationResult<FirewallPortHistoryDTO>();
		try {
			result = comm.firewallPortHistoryService.getFirewallPortHistoryDTOPagination(searchParams, pageNumber,
					pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FirewallPortHistoryDTO> getFirewallPortHistoryList() {
		DTOListResult<FirewallPortHistoryDTO> result = new DTOListResult<FirewallPortHistoryDTO>();
		try {
			List<FirewallPortHistory> companies = comm.firewallPortHistoryService.getCompanies();
			List<FirewallPortHistoryDTO> list = BeanMapper.mapList(companies, FirewallPortHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}