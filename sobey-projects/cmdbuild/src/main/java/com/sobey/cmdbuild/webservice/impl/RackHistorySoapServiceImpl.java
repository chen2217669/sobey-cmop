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
import com.sobey.cmdbuild.entity.RackHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.RackHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.RackHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "RackHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.RackHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class RackHistorySoapServiceImpl extends BasicSoapSevcie implements RackHistorySoapService {
	@Override
	public DTOResult<RackHistoryDTO> findRackHistory(@WebParam(name = "id") Integer id) {
		DTOResult<RackHistoryDTO> result = new DTOResult<RackHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			RackHistory rackHistory = comm.rackHistoryService.findRackHistory(id);
			Validate.notNull(rackHistory, ERROR.OBJECT_NULL);
			RackHistoryDTO rackHistoryDTO = BeanMapper.map(rackHistory, RackHistoryDTO.class);
			result.setDto(rackHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<RackHistoryDTO> findRackHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<RackHistoryDTO> result = new DTOResult<RackHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			RackHistory rackHistory = comm.rackHistoryService.findByCode(code);
			Validate.notNull(rackHistory, ERROR.OBJECT_NULL);
			RackHistoryDTO rackHistoryDTO = BeanMapper.map(rackHistory, RackHistoryDTO.class);
			result.setDto(rackHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createRackHistory(@WebParam(name = "rackHistoryDTO") RackHistoryDTO rackHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(rackHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.rackHistoryService.findByCode(rackHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			RackHistory rackHistory = BeanMapper.map(rackHistoryDTO, RackHistory.class);
			BeanValidators.validateWithException(validator, rackHistory);
			comm.rackHistoryService.saveOrUpdate(rackHistory);
			return new IdResult(rackHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateRackHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "rackHistoryDTO") RackHistoryDTO rackHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(rackHistoryDTO, ERROR.INPUT_NULL);
			RackHistory rackHistory = comm.rackHistoryService.findRackHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.rackHistoryService.findByCode(rackHistoryDTO.getCode()) == null
					|| rackHistory.getCode().equals(rackHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			RackHistory rackHistoryEntity = BeanMapper.map(rackHistoryDTO, RackHistory.class);
			BeanMapper.copy(rackHistoryEntity, rackHistory);
			rackHistoryEntity.setIdClass(RackHistory.class.getSimpleName());
			rackHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.rackHistoryService.saveOrUpdate(rackHistoryEntity);
			return new IdResult(rackHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteRackHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			RackHistory rackHistory = comm.rackHistoryService.findRackHistory(id);
			RackHistory rackHistoryEntity = BeanMapper.map(findRackHistory(id).getDto(), RackHistory.class);
			BeanMapper.copy(rackHistoryEntity, rackHistory);
			rackHistoryEntity.setIdClass(RackHistory.class.getSimpleName());
			rackHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.rackHistoryService.saveOrUpdate(rackHistoryEntity);
			return new IdResult(rackHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<RackHistoryDTO> getRackHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<RackHistoryDTO> result = new PaginationResult<RackHistoryDTO>();
		try {
			result = comm.rackHistoryService.getRackHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<RackHistoryDTO> getRackHistoryList() {
		DTOListResult<RackHistoryDTO> result = new DTOListResult<RackHistoryDTO>();
		try {
			List<RackHistory> companies = comm.rackHistoryService.getCompanies();
			List<RackHistoryDTO> list = BeanMapper.mapList(companies, RackHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}