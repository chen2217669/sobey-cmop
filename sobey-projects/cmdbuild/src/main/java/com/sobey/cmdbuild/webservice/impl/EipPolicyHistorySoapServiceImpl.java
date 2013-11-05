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
import com.sobey.cmdbuild.entity.EipPolicyHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EipPolicyHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.EipPolicyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EipPolicyHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.EipPolicyHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EipPolicyHistorySoapServiceImpl extends BasicSoapSevcie implements EipPolicyHistorySoapService {
	@Override
	public DTOResult<EipPolicyHistoryDTO> findEipPolicyHistory(@WebParam(name = "id") Integer id) {
		DTOResult<EipPolicyHistoryDTO> result = new DTOResult<EipPolicyHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipPolicyHistory eipPolicyHistory = comm.eipPolicyHistoryService.findEipPolicyHistory(id);
			Validate.notNull(eipPolicyHistory, ERROR.OBJECT_NULL);
			EipPolicyHistoryDTO eipPolicyHistoryDTO = BeanMapper.map(eipPolicyHistory, EipPolicyHistoryDTO.class);
			result.setDto(eipPolicyHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipPolicyHistoryDTO> findEipPolicyHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<EipPolicyHistoryDTO> result = new DTOResult<EipPolicyHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			EipPolicyHistory eipPolicyHistory = comm.eipPolicyHistoryService.findByCode(code);
			Validate.notNull(eipPolicyHistory, ERROR.OBJECT_NULL);
			EipPolicyHistoryDTO eipPolicyHistoryDTO = BeanMapper.map(eipPolicyHistory, EipPolicyHistoryDTO.class);
			result.setDto(eipPolicyHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEipPolicyHistory(
			@WebParam(name = "eipPolicyHistoryDTO") EipPolicyHistoryDTO eipPolicyHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipPolicyHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipPolicyHistoryService.findByCode(eipPolicyHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			EipPolicyHistory eipPolicyHistory = BeanMapper.map(eipPolicyHistoryDTO, EipPolicyHistory.class);
			BeanValidators.validateWithException(validator, eipPolicyHistory);
			comm.eipPolicyHistoryService.saveOrUpdate(eipPolicyHistory);
			return new IdResult(eipPolicyHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEipPolicyHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipPolicyHistoryDTO") EipPolicyHistoryDTO eipPolicyHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipPolicyHistoryDTO, ERROR.INPUT_NULL);
			EipPolicyHistory eipPolicyHistory = comm.eipPolicyHistoryService.findEipPolicyHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipPolicyHistoryService.findByCode(eipPolicyHistoryDTO.getCode()) == null
					|| eipPolicyHistory.getCode().equals(eipPolicyHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EipPolicyHistory eipPolicyHistoryEntity = BeanMapper.map(eipPolicyHistoryDTO, EipPolicyHistory.class);
			BeanMapper.copy(eipPolicyHistoryEntity, eipPolicyHistory);
			eipPolicyHistoryEntity.setIdClass(EipPolicyHistory.class.getSimpleName());
			eipPolicyHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.eipPolicyHistoryService.saveOrUpdate(eipPolicyHistoryEntity);
			return new IdResult(eipPolicyHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEipPolicyHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipPolicyHistory eipPolicyHistory = comm.eipPolicyHistoryService.findEipPolicyHistory(id);
			EipPolicyHistory eipPolicyHistoryEntity = BeanMapper.map(findEipPolicyHistory(id).getDto(),
					EipPolicyHistory.class);
			BeanMapper.copy(eipPolicyHistoryEntity, eipPolicyHistory);
			eipPolicyHistoryEntity.setIdClass(EipPolicyHistory.class.getSimpleName());
			eipPolicyHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.eipPolicyHistoryService.saveOrUpdate(eipPolicyHistoryEntity);
			return new IdResult(eipPolicyHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipPolicyHistoryDTO> getEipPolicyHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EipPolicyHistoryDTO> result = new PaginationResult<EipPolicyHistoryDTO>();
		try {
			result = comm.eipPolicyHistoryService.getEipPolicyHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipPolicyHistoryDTO> getEipPolicyHistoryList() {
		DTOListResult<EipPolicyHistoryDTO> result = new DTOListResult<EipPolicyHistoryDTO>();
		try {
			List<EipPolicyHistory> companies = comm.eipPolicyHistoryService.getCompanies();
			List<EipPolicyHistoryDTO> list = BeanMapper.mapList(companies, EipPolicyHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}