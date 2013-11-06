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
import com.sobey.cmdbuild.entity.EcsHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EcsHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.EcsHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EcsHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.EcsHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EcsHistorySoapServiceImpl extends BasicSoapSevcie implements EcsHistorySoapService {
	@Override
	public DTOResult<EcsHistoryDTO> findEcsHistory(@WebParam(name = "id") Integer id) {
		DTOResult<EcsHistoryDTO> result = new DTOResult<EcsHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EcsHistory ecsHistory = comm.ecsHistoryService.findEcsHistory(id);
			Validate.notNull(ecsHistory, ERROR.OBJECT_NULL);
			EcsHistoryDTO ecsHistoryDTO = BeanMapper.map(ecsHistory, EcsHistoryDTO.class);
			result.setDto(ecsHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsHistoryDTO> findEcsHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<EcsHistoryDTO> result = new DTOResult<EcsHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			EcsHistory ecsHistory = comm.ecsHistoryService.findByCode(code);
			Validate.notNull(ecsHistory, ERROR.OBJECT_NULL);
			EcsHistoryDTO ecsHistoryDTO = BeanMapper.map(ecsHistory, EcsHistoryDTO.class);
			result.setDto(ecsHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEcsHistory(@WebParam(name = "ecsHistoryDTO") EcsHistoryDTO ecsHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ecsHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ecsHistoryService.findByCode(ecsHistoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			EcsHistory ecsHistory = BeanMapper.map(ecsHistoryDTO, EcsHistory.class);
			BeanValidators.validateWithException(validator, ecsHistory);
			comm.ecsHistoryService.saveOrUpdate(ecsHistory);
			return new IdResult(ecsHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEcsHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "ecsHistoryDTO") EcsHistoryDTO ecsHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ecsHistoryDTO, ERROR.INPUT_NULL);
			EcsHistory ecsHistory = comm.ecsHistoryService.findEcsHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ecsHistoryService.findByCode(ecsHistoryDTO.getCode()) == null
					|| ecsHistory.getCode().equals(ecsHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EcsHistory ecsHistoryEntity = BeanMapper.map(ecsHistoryDTO, EcsHistory.class);
			BeanMapper.copy(ecsHistoryEntity, ecsHistory);
			ecsHistoryEntity.setIdClass(EcsHistory.class.getSimpleName());
			ecsHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.ecsHistoryService.saveOrUpdate(ecsHistoryEntity);
			return new IdResult(ecsHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEcsHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EcsHistory ecsHistory = comm.ecsHistoryService.findEcsHistory(id);
			EcsHistory ecsHistoryEntity = BeanMapper.map(findEcsHistory(id).getDto(), EcsHistory.class);
			BeanMapper.copy(ecsHistoryEntity, ecsHistory);
			ecsHistoryEntity.setIdClass(EcsHistory.class.getSimpleName());
			ecsHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.ecsHistoryService.saveOrUpdate(ecsHistoryEntity);
			return new IdResult(ecsHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EcsHistoryDTO> getEcsHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EcsHistoryDTO> result = new PaginationResult<EcsHistoryDTO>();
		try {
			result = comm.ecsHistoryService.getEcsHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EcsHistoryDTO> getEcsHistoryList() {
		DTOListResult<EcsHistoryDTO> result = new DTOListResult<EcsHistoryDTO>();
		try {
			List<EcsHistory> companies = comm.ecsHistoryService.getCompanies();
			List<EcsHistoryDTO> list = BeanMapper.mapList(companies, EcsHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}