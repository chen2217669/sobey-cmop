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
import com.sobey.cmdbuild.entity.EsgPolicyHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EsgPolicyHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.EsgPolicyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EsgPolicyHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.EsgPolicyHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EsgPolicyHistorySoapServiceImpl extends BasicSoapSevcie implements EsgPolicyHistorySoapService {
	@Override
	public DTOResult<EsgPolicyHistoryDTO> findEsgPolicyHistory(@WebParam(name = "id") Integer id) {
		DTOResult<EsgPolicyHistoryDTO> result = new DTOResult<EsgPolicyHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EsgPolicyHistory esgPolicyHistory = comm.esgPolicyHistoryService.findEsgPolicyHistory(id);
			Validate.notNull(esgPolicyHistory, ERROR.OBJECT_NULL);
			EsgPolicyHistoryDTO esgPolicyHistoryDTO = BeanMapper.map(esgPolicyHistory, EsgPolicyHistoryDTO.class);
			result.setDto(esgPolicyHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EsgPolicyHistoryDTO> findEsgPolicyHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<EsgPolicyHistoryDTO> result = new DTOResult<EsgPolicyHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			EsgPolicyHistory esgPolicyHistory = comm.esgPolicyHistoryService.findByCode(code);
			Validate.notNull(esgPolicyHistory, ERROR.OBJECT_NULL);
			EsgPolicyHistoryDTO esgPolicyHistoryDTO = BeanMapper.map(esgPolicyHistory, EsgPolicyHistoryDTO.class);
			result.setDto(esgPolicyHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEsgPolicyHistory(
			@WebParam(name = "esgPolicyHistoryDTO") EsgPolicyHistoryDTO esgPolicyHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(esgPolicyHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.esgPolicyHistoryService.findByCode(esgPolicyHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			EsgPolicyHistory esgPolicyHistory = BeanMapper.map(esgPolicyHistoryDTO, EsgPolicyHistory.class);
			BeanValidators.validateWithException(validator, esgPolicyHistory);
			comm.esgPolicyHistoryService.saveOrUpdate(esgPolicyHistory);
			return new IdResult(esgPolicyHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEsgPolicyHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "esgPolicyHistoryDTO") EsgPolicyHistoryDTO esgPolicyHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(esgPolicyHistoryDTO, ERROR.INPUT_NULL);
			EsgPolicyHistory esgPolicyHistory = comm.esgPolicyHistoryService.findEsgPolicyHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.esgPolicyHistoryService.findByCode(esgPolicyHistoryDTO.getCode()) == null
					|| esgPolicyHistory.getCode().equals(esgPolicyHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EsgPolicyHistory esgPolicyHistoryEntity = BeanMapper.map(esgPolicyHistoryDTO, EsgPolicyHistory.class);
			BeanMapper.copy(esgPolicyHistoryEntity, esgPolicyHistory);
			esgPolicyHistoryEntity.setIdClass(EsgPolicyHistory.class.getSimpleName());
			esgPolicyHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.esgPolicyHistoryService.saveOrUpdate(esgPolicyHistoryEntity);
			return new IdResult(esgPolicyHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEsgPolicyHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EsgPolicyHistory esgPolicyHistory = comm.esgPolicyHistoryService.findEsgPolicyHistory(id);
			EsgPolicyHistory esgPolicyHistoryEntity = BeanMapper.map(findEsgPolicyHistory(id).getDto(),
					EsgPolicyHistory.class);
			BeanMapper.copy(esgPolicyHistoryEntity, esgPolicyHistory);
			esgPolicyHistoryEntity.setIdClass(EsgPolicyHistory.class.getSimpleName());
			esgPolicyHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.esgPolicyHistoryService.saveOrUpdate(esgPolicyHistoryEntity);
			return new IdResult(esgPolicyHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EsgPolicyHistoryDTO> getEsgPolicyHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EsgPolicyHistoryDTO> result = new PaginationResult<EsgPolicyHistoryDTO>();
		try {
			result = comm.esgPolicyHistoryService.getEsgPolicyHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EsgPolicyHistoryDTO> getEsgPolicyHistoryList() {
		DTOListResult<EsgPolicyHistoryDTO> result = new DTOListResult<EsgPolicyHistoryDTO>();
		try {
			List<EsgPolicyHistory> companies = comm.esgPolicyHistoryService.getCompanies();
			List<EsgPolicyHistoryDTO> list = BeanMapper.mapList(companies, EsgPolicyHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}