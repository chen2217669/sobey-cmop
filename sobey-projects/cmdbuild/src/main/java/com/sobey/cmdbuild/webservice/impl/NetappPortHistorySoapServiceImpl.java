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
import com.sobey.cmdbuild.entity.NetappPortHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.NetappPortHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.NetappPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "NetappPortHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.NetappPortHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NetappPortHistorySoapServiceImpl extends BasicSoapSevcie implements NetappPortHistorySoapService {
	@Override
	public DTOResult<NetappPortHistoryDTO> findNetappPortHistory(@WebParam(name = "id") Integer id) {
		DTOResult<NetappPortHistoryDTO> result = new DTOResult<NetappPortHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NetappPortHistory netappPortHistory = comm.netappPortHistoryService.findNetappPortHistory(id);
			Validate.notNull(netappPortHistory, ERROR.OBJECT_NULL);
			NetappPortHistoryDTO netappPortHistoryDTO = BeanMapper.map(netappPortHistory, NetappPortHistoryDTO.class);
			result.setDto(netappPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NetappPortHistoryDTO> findNetappPortHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<NetappPortHistoryDTO> result = new DTOResult<NetappPortHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			NetappPortHistory netappPortHistory = comm.netappPortHistoryService.findByCode(code);
			Validate.notNull(netappPortHistory, ERROR.OBJECT_NULL);
			NetappPortHistoryDTO netappPortHistoryDTO = BeanMapper.map(netappPortHistory, NetappPortHistoryDTO.class);
			result.setDto(netappPortHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createNetappPortHistory(
			@WebParam(name = "netappPortHistoryDTO") NetappPortHistoryDTO netappPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(netappPortHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappPortHistoryService.findByCode(netappPortHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			NetappPortHistory netappPortHistory = BeanMapper.map(netappPortHistoryDTO, NetappPortHistory.class);
			BeanValidators.validateWithException(validator, netappPortHistory);
			comm.netappPortHistoryService.saveOrUpdate(netappPortHistory);
			return new IdResult(netappPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNetappPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappPortHistoryDTO") NetappPortHistoryDTO netappPortHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(netappPortHistoryDTO, ERROR.INPUT_NULL);
			NetappPortHistory netappPortHistory = comm.netappPortHistoryService.findNetappPortHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappPortHistoryService.findByCode(netappPortHistoryDTO.getCode()) == null
					|| netappPortHistory.getCode().equals(netappPortHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			NetappPortHistory netappPortHistoryEntity = BeanMapper.map(netappPortHistoryDTO, NetappPortHistory.class);
			BeanMapper.copy(netappPortHistoryEntity, netappPortHistory);
			netappPortHistoryEntity.setIdClass(NetappPortHistory.class.getSimpleName());
			netappPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.netappPortHistoryService.saveOrUpdate(netappPortHistoryEntity);
			return new IdResult(netappPortHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNetappPortHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NetappPortHistory netappPortHistory = comm.netappPortHistoryService.findNetappPortHistory(id);
			NetappPortHistory netappPortHistoryEntity = BeanMapper.map(findNetappPortHistory(id).getDto(),
					NetappPortHistory.class);
			BeanMapper.copy(netappPortHistoryEntity, netappPortHistory);
			netappPortHistoryEntity.setIdClass(NetappPortHistory.class.getSimpleName());
			netappPortHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.netappPortHistoryService.saveOrUpdate(netappPortHistoryEntity);
			return new IdResult(netappPortHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NetappPortHistoryDTO> getNetappPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<NetappPortHistoryDTO> result = new PaginationResult<NetappPortHistoryDTO>();
		try {
			result = comm.netappPortHistoryService
					.getNetappPortHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NetappPortHistoryDTO> getNetappPortHistoryList() {
		DTOListResult<NetappPortHistoryDTO> result = new DTOListResult<NetappPortHistoryDTO>();
		try {
			List<NetappPortHistory> companies = comm.netappPortHistoryService.getCompanies();
			List<NetappPortHistoryDTO> list = BeanMapper.mapList(companies, NetappPortHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}