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
import com.sobey.cmdbuild.entity.EcsSpecHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EcsSpecHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EcsSpecHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.EcsSpecHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EcsSpecHistorySoapServiceImpl extends BasicSoapSevcie implements EcsSpecHistorySoapService {
	@Override
	public DTOResult<EcsSpecHistoryDTO> findEcsSpecHistory(@WebParam(name = "id") Integer id) {
		DTOResult<EcsSpecHistoryDTO> result = new DTOResult<EcsSpecHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EcsSpecHistory ecsSpecHistory = comm.ecsSpecHistoryService.findEcsSpecHistory(id);
			Validate.notNull(ecsSpecHistory, ERROR.OBJECT_NULL);
			EcsSpecHistoryDTO ecsSpecHistoryDTO = BeanMapper.map(ecsSpecHistory, EcsSpecHistoryDTO.class);
			result.setDto(ecsSpecHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsSpecHistoryDTO> findEcsSpecHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<EcsSpecHistoryDTO> result = new DTOResult<EcsSpecHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			EcsSpecHistory ecsSpecHistory = comm.ecsSpecHistoryService.findByCode(code);
			Validate.notNull(ecsSpecHistory, ERROR.OBJECT_NULL);
			EcsSpecHistoryDTO ecsSpecHistoryDTO = BeanMapper.map(ecsSpecHistory, EcsSpecHistoryDTO.class);
			result.setDto(ecsSpecHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEcsSpecHistory(@WebParam(name = "ecsSpecHistoryDTO") EcsSpecHistoryDTO ecsSpecHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ecsSpecHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ecsSpecHistoryService.findByCode(ecsSpecHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			EcsSpecHistory ecsSpecHistory = BeanMapper.map(ecsSpecHistoryDTO, EcsSpecHistory.class);
			BeanValidators.validateWithException(validator, ecsSpecHistory);
			comm.ecsSpecHistoryService.saveOrUpdate(ecsSpecHistory);
			return new IdResult(ecsSpecHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEcsSpecHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "ecsSpecHistoryDTO") EcsSpecHistoryDTO ecsSpecHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ecsSpecHistoryDTO, ERROR.INPUT_NULL);
			EcsSpecHistory ecsSpecHistory = comm.ecsSpecHistoryService.findEcsSpecHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ecsSpecHistoryService.findByCode(ecsSpecHistoryDTO.getCode()) == null
					|| ecsSpecHistory.getCode().equals(ecsSpecHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EcsSpecHistory ecsSpecHistoryEntity = BeanMapper.map(ecsSpecHistoryDTO, EcsSpecHistory.class);
			BeanMapper.copy(ecsSpecHistoryEntity, ecsSpecHistory);
			ecsSpecHistoryEntity.setIdClass(EcsSpecHistory.class.getSimpleName());
			ecsSpecHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.ecsSpecHistoryService.saveOrUpdate(ecsSpecHistoryEntity);
			return new IdResult(ecsSpecHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEcsSpecHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EcsSpecHistory ecsSpecHistory = comm.ecsSpecHistoryService.findEcsSpecHistory(id);
			EcsSpecHistory ecsSpecHistoryEntity = BeanMapper.map(findEcsSpecHistory(id).getDto(), EcsSpecHistory.class);
			BeanMapper.copy(ecsSpecHistoryEntity, ecsSpecHistory);
			ecsSpecHistoryEntity.setIdClass(EcsSpecHistory.class.getSimpleName());
			ecsSpecHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.ecsSpecHistoryService.saveOrUpdate(ecsSpecHistoryEntity);
			return new IdResult(ecsSpecHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EcsSpecHistoryDTO> getEcsSpecHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EcsSpecHistoryDTO> result = new PaginationResult<EcsSpecHistoryDTO>();
		try {
			result = comm.ecsSpecHistoryService.getEcsSpecHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EcsSpecHistoryDTO> getEcsSpecHistoryList() {
		DTOListResult<EcsSpecHistoryDTO> result = new DTOListResult<EcsSpecHistoryDTO>();
		try {
			List<EcsSpecHistory> companies = comm.ecsSpecHistoryService.getCompanies();
			List<EcsSpecHistoryDTO> list = BeanMapper.mapList(companies, EcsSpecHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}