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
import com.sobey.cmdbuild.entity.ConsumptionsHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.ConsumptionsHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.ConsumptionsHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "ConsumptionsHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.ConsumptionsHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class ConsumptionsHistorySoapServiceImpl extends BasicSoapSevcie implements ConsumptionsHistorySoapService {
	@Override
	public DTOResult<ConsumptionsHistoryDTO> findConsumptionsHistory(@WebParam(name = "id") Integer id) {
		DTOResult<ConsumptionsHistoryDTO> result = new DTOResult<ConsumptionsHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ConsumptionsHistory consumptionsHistory = comm.consumptionsHistoryService.findConsumptionsHistory(id);
			Validate.notNull(consumptionsHistory, ERROR.OBJECT_NULL);
			ConsumptionsHistoryDTO consumptionsHistoryDTO = BeanMapper.map(consumptionsHistory,
					ConsumptionsHistoryDTO.class);
			result.setDto(consumptionsHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConsumptionsHistoryDTO> findConsumptionsHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<ConsumptionsHistoryDTO> result = new DTOResult<ConsumptionsHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			ConsumptionsHistory consumptionsHistory = comm.consumptionsHistoryService.findByCode(code);
			Validate.notNull(consumptionsHistory, ERROR.OBJECT_NULL);
			ConsumptionsHistoryDTO consumptionsHistoryDTO = BeanMapper.map(consumptionsHistory,
					ConsumptionsHistoryDTO.class);
			result.setDto(consumptionsHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createConsumptionsHistory(
			@WebParam(name = "consumptionsHistoryDTO") ConsumptionsHistoryDTO consumptionsHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(consumptionsHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.consumptionsHistoryService.findByCode(consumptionsHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			ConsumptionsHistory consumptionsHistory = BeanMapper.map(consumptionsHistoryDTO, ConsumptionsHistory.class);
			BeanValidators.validateWithException(validator, consumptionsHistory);
			comm.consumptionsHistoryService.saveOrUpdate(consumptionsHistory);
			return new IdResult(consumptionsHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateConsumptionsHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "consumptionsHistoryDTO") ConsumptionsHistoryDTO consumptionsHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(consumptionsHistoryDTO, ERROR.INPUT_NULL);
			ConsumptionsHistory consumptionsHistory = comm.consumptionsHistoryService.findConsumptionsHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.consumptionsHistoryService.findByCode(consumptionsHistoryDTO.getCode()) == null
					|| consumptionsHistory.getCode().equals(consumptionsHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			ConsumptionsHistory consumptionsHistoryEntity = BeanMapper.map(consumptionsHistoryDTO,
					ConsumptionsHistory.class);
			BeanMapper.copy(consumptionsHistoryEntity, consumptionsHistory);
			consumptionsHistoryEntity.setIdClass(ConsumptionsHistory.class.getSimpleName());
			consumptionsHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.consumptionsHistoryService.saveOrUpdate(consumptionsHistoryEntity);
			return new IdResult(consumptionsHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteConsumptionsHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ConsumptionsHistory consumptionsHistory = comm.consumptionsHistoryService.findConsumptionsHistory(id);
			ConsumptionsHistory consumptionsHistoryEntity = BeanMapper.map(findConsumptionsHistory(id).getDto(),
					ConsumptionsHistory.class);
			BeanMapper.copy(consumptionsHistoryEntity, consumptionsHistory);
			consumptionsHistoryEntity.setIdClass(ConsumptionsHistory.class.getSimpleName());
			consumptionsHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.consumptionsHistoryService.saveOrUpdate(consumptionsHistoryEntity);
			return new IdResult(consumptionsHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ConsumptionsHistoryDTO> getConsumptionsHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ConsumptionsHistoryDTO> result = new PaginationResult<ConsumptionsHistoryDTO>();
		try {
			result = comm.consumptionsHistoryService.getConsumptionsHistoryDTOPagination(searchParams, pageNumber,
					pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ConsumptionsHistoryDTO> getConsumptionsHistoryList() {
		DTOListResult<ConsumptionsHistoryDTO> result = new DTOListResult<ConsumptionsHistoryDTO>();
		try {
			List<ConsumptionsHistory> companies = comm.consumptionsHistoryService.getCompanies();
			List<ConsumptionsHistoryDTO> list = BeanMapper.mapList(companies, ConsumptionsHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}