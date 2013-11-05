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
import com.sobey.cmdbuild.entity.TenantsHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.TenantsHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.TenantsHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "TenantsHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.TenantsHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class TenantsHistorySoapServiceImpl extends BasicSoapSevcie implements TenantsHistorySoapService {
	@Override
	public DTOResult<TenantsHistoryDTO> findTenantsHistory(@WebParam(name = "id") Integer id) {
		DTOResult<TenantsHistoryDTO> result = new DTOResult<TenantsHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			TenantsHistory tenantsHistory = comm.tenantsHistoryService.findTenantsHistory(id);
			Validate.notNull(tenantsHistory, ERROR.OBJECT_NULL);
			TenantsHistoryDTO tenantsHistoryDTO = BeanMapper.map(tenantsHistory, TenantsHistoryDTO.class);
			result.setDto(tenantsHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TenantsHistoryDTO> findTenantsHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<TenantsHistoryDTO> result = new DTOResult<TenantsHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			TenantsHistory tenantsHistory = comm.tenantsHistoryService.findByCode(code);
			Validate.notNull(tenantsHistory, ERROR.OBJECT_NULL);
			TenantsHistoryDTO tenantsHistoryDTO = BeanMapper.map(tenantsHistory, TenantsHistoryDTO.class);
			result.setDto(tenantsHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createTenantsHistory(@WebParam(name = "tenantsHistoryDTO") TenantsHistoryDTO tenantsHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tenantsHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.tenantsHistoryService.findByCode(tenantsHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			TenantsHistory tenantsHistory = BeanMapper.map(tenantsHistoryDTO, TenantsHistory.class);
			BeanValidators.validateWithException(validator, tenantsHistory);
			comm.tenantsHistoryService.saveOrUpdate(tenantsHistory);
			return new IdResult(tenantsHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateTenantsHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "tenantsHistoryDTO") TenantsHistoryDTO tenantsHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tenantsHistoryDTO, ERROR.INPUT_NULL);
			TenantsHistory tenantsHistory = comm.tenantsHistoryService.findTenantsHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.tenantsHistoryService.findByCode(tenantsHistoryDTO.getCode()) == null
					|| tenantsHistory.getCode().equals(tenantsHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			TenantsHistory tenantsHistoryEntity = BeanMapper.map(tenantsHistoryDTO, TenantsHistory.class);
			BeanMapper.copy(tenantsHistoryEntity, tenantsHistory);
			tenantsHistoryEntity.setIdClass(TenantsHistory.class.getSimpleName());
			tenantsHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.tenantsHistoryService.saveOrUpdate(tenantsHistoryEntity);
			return new IdResult(tenantsHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteTenantsHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			TenantsHistory tenantsHistory = comm.tenantsHistoryService.findTenantsHistory(id);
			TenantsHistory tenantsHistoryEntity = BeanMapper.map(findTenantsHistory(id).getDto(), TenantsHistory.class);
			BeanMapper.copy(tenantsHistoryEntity, tenantsHistory);
			tenantsHistoryEntity.setIdClass(TenantsHistory.class.getSimpleName());
			tenantsHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.tenantsHistoryService.saveOrUpdate(tenantsHistoryEntity);
			return new IdResult(tenantsHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<TenantsHistoryDTO> getTenantsHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<TenantsHistoryDTO> result = new PaginationResult<TenantsHistoryDTO>();
		try {
			result = comm.tenantsHistoryService.getTenantsHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<TenantsHistoryDTO> getTenantsHistoryList() {
		DTOListResult<TenantsHistoryDTO> result = new DTOListResult<TenantsHistoryDTO>();
		try {
			List<TenantsHistory> companies = comm.tenantsHistoryService.getCompanies();
			List<TenantsHistoryDTO> list = BeanMapper.mapList(companies, TenantsHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}