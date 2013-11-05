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
import com.sobey.cmdbuild.entity.NetappBoxHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.NetappBoxHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.NetappBoxHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "NetappBoxHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.NetappBoxHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NetappBoxHistorySoapServiceImpl extends BasicSoapSevcie implements NetappBoxHistorySoapService {
	@Override
	public DTOResult<NetappBoxHistoryDTO> findNetappBoxHistory(@WebParam(name = "id") Integer id) {
		DTOResult<NetappBoxHistoryDTO> result = new DTOResult<NetappBoxHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NetappBoxHistory netappBoxHistory = comm.netappBoxHistoryService.findNetappBoxHistory(id);
			Validate.notNull(netappBoxHistory, ERROR.OBJECT_NULL);
			NetappBoxHistoryDTO netappBoxHistoryDTO = BeanMapper.map(netappBoxHistory, NetappBoxHistoryDTO.class);
			result.setDto(netappBoxHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NetappBoxHistoryDTO> findNetappBoxHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<NetappBoxHistoryDTO> result = new DTOResult<NetappBoxHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			NetappBoxHistory netappBoxHistory = comm.netappBoxHistoryService.findByCode(code);
			Validate.notNull(netappBoxHistory, ERROR.OBJECT_NULL);
			NetappBoxHistoryDTO netappBoxHistoryDTO = BeanMapper.map(netappBoxHistory, NetappBoxHistoryDTO.class);
			result.setDto(netappBoxHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createNetappBoxHistory(
			@WebParam(name = "netappBoxHistoryDTO") NetappBoxHistoryDTO netappBoxHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(netappBoxHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappBoxHistoryService.findByCode(netappBoxHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			NetappBoxHistory netappBoxHistory = BeanMapper.map(netappBoxHistoryDTO, NetappBoxHistory.class);
			BeanValidators.validateWithException(validator, netappBoxHistory);
			comm.netappBoxHistoryService.saveOrUpdate(netappBoxHistory);
			return new IdResult(netappBoxHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNetappBoxHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappBoxHistoryDTO") NetappBoxHistoryDTO netappBoxHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(netappBoxHistoryDTO, ERROR.INPUT_NULL);
			NetappBoxHistory netappBoxHistory = comm.netappBoxHistoryService.findNetappBoxHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappBoxHistoryService.findByCode(netappBoxHistoryDTO.getCode()) == null
					|| netappBoxHistory.getCode().equals(netappBoxHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			NetappBoxHistory netappBoxHistoryEntity = BeanMapper.map(netappBoxHistoryDTO, NetappBoxHistory.class);
			BeanMapper.copy(netappBoxHistoryEntity, netappBoxHistory);
			netappBoxHistoryEntity.setIdClass(NetappBoxHistory.class.getSimpleName());
			netappBoxHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.netappBoxHistoryService.saveOrUpdate(netappBoxHistoryEntity);
			return new IdResult(netappBoxHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNetappBoxHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NetappBoxHistory netappBoxHistory = comm.netappBoxHistoryService.findNetappBoxHistory(id);
			NetappBoxHistory netappBoxHistoryEntity = BeanMapper.map(findNetappBoxHistory(id).getDto(),
					NetappBoxHistory.class);
			BeanMapper.copy(netappBoxHistoryEntity, netappBoxHistory);
			netappBoxHistoryEntity.setIdClass(NetappBoxHistory.class.getSimpleName());
			netappBoxHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.netappBoxHistoryService.saveOrUpdate(netappBoxHistoryEntity);
			return new IdResult(netappBoxHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NetappBoxHistoryDTO> getNetappBoxHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<NetappBoxHistoryDTO> result = new PaginationResult<NetappBoxHistoryDTO>();
		try {
			result = comm.netappBoxHistoryService.getNetappBoxHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NetappBoxHistoryDTO> getNetappBoxHistoryList() {
		DTOListResult<NetappBoxHistoryDTO> result = new DTOListResult<NetappBoxHistoryDTO>();
		try {
			List<NetappBoxHistory> companies = comm.netappBoxHistoryService.getCompanies();
			List<NetappBoxHistoryDTO> list = BeanMapper.mapList(companies, NetappBoxHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}