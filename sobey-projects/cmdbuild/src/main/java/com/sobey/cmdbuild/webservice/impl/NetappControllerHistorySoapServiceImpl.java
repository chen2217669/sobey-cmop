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
import com.sobey.cmdbuild.entity.NetappControllerHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.NetappControllerHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.NetappControllerHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "NetappControllerHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.NetappControllerHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NetappControllerHistorySoapServiceImpl extends BasicSoapSevcie implements
		NetappControllerHistorySoapService {
	@Override
	public DTOResult<NetappControllerHistoryDTO> findNetappControllerHistory(@WebParam(name = "id") Integer id) {
		DTOResult<NetappControllerHistoryDTO> result = new DTOResult<NetappControllerHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NetappControllerHistory netappControllerHistory = comm.netappControllerHistoryService
					.findNetappControllerHistory(id);
			Validate.notNull(netappControllerHistory, ERROR.OBJECT_NULL);
			NetappControllerHistoryDTO netappControllerHistoryDTO = BeanMapper.map(netappControllerHistory,
					NetappControllerHistoryDTO.class);
			result.setDto(netappControllerHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NetappControllerHistoryDTO> findNetappControllerHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<NetappControllerHistoryDTO> result = new DTOResult<NetappControllerHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			NetappControllerHistory netappControllerHistory = comm.netappControllerHistoryService.findByCode(code);
			Validate.notNull(netappControllerHistory, ERROR.OBJECT_NULL);
			NetappControllerHistoryDTO netappControllerHistoryDTO = BeanMapper.map(netappControllerHistory,
					NetappControllerHistoryDTO.class);
			result.setDto(netappControllerHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createNetappControllerHistory(
			@WebParam(name = "netappControllerHistoryDTO") NetappControllerHistoryDTO netappControllerHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(netappControllerHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.netappControllerHistoryService.findByCode(netappControllerHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			NetappControllerHistory netappControllerHistory = BeanMapper.map(netappControllerHistoryDTO,
					NetappControllerHistory.class);
			BeanValidators.validateWithException(validator, netappControllerHistory);
			comm.netappControllerHistoryService.saveOrUpdate(netappControllerHistory);
			return new IdResult(netappControllerHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNetappControllerHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappControllerHistoryDTO") NetappControllerHistoryDTO netappControllerHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(netappControllerHistoryDTO, ERROR.INPUT_NULL);
			NetappControllerHistory netappControllerHistory = comm.netappControllerHistoryService
					.findNetappControllerHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.netappControllerHistoryService.findByCode(netappControllerHistoryDTO.getCode()) == null
							|| netappControllerHistory.getCode().equals(netappControllerHistoryDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			NetappControllerHistory netappControllerHistoryEntity = BeanMapper.map(netappControllerHistoryDTO,
					NetappControllerHistory.class);
			BeanMapper.copy(netappControllerHistoryEntity, netappControllerHistory);
			netappControllerHistoryEntity.setIdClass(NetappControllerHistory.class.getSimpleName());
			netappControllerHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.netappControllerHistoryService.saveOrUpdate(netappControllerHistoryEntity);
			return new IdResult(netappControllerHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNetappControllerHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NetappControllerHistory netappControllerHistory = comm.netappControllerHistoryService
					.findNetappControllerHistory(id);
			NetappControllerHistory netappControllerHistoryEntity = BeanMapper.map(findNetappControllerHistory(id)
					.getDto(), NetappControllerHistory.class);
			BeanMapper.copy(netappControllerHistoryEntity, netappControllerHistory);
			netappControllerHistoryEntity.setIdClass(NetappControllerHistory.class.getSimpleName());
			netappControllerHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.netappControllerHistoryService.saveOrUpdate(netappControllerHistoryEntity);
			return new IdResult(netappControllerHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NetappControllerHistoryDTO> getNetappControllerHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<NetappControllerHistoryDTO> result = new PaginationResult<NetappControllerHistoryDTO>();
		try {
			result = comm.netappControllerHistoryService.getNetappControllerHistoryDTOPagination(searchParams,
					pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NetappControllerHistoryDTO> getNetappControllerHistoryList() {
		DTOListResult<NetappControllerHistoryDTO> result = new DTOListResult<NetappControllerHistoryDTO>();
		try {
			List<NetappControllerHistory> companies = comm.netappControllerHistoryService.getCompanies();
			List<NetappControllerHistoryDTO> list = BeanMapper.mapList(companies, NetappControllerHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}