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
import com.sobey.cmdbuild.entity.EipHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EipHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.EipHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EipHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.EipHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EipHistorySoapServiceImpl extends BasicSoapSevcie implements EipHistorySoapService {
	@Override
	public DTOResult<EipHistoryDTO> findEipHistory(@WebParam(name = "id") Integer id) {
		DTOResult<EipHistoryDTO> result = new DTOResult<EipHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipHistory eipHistory = comm.eipHistoryService.findEipHistory(id);
			Validate.notNull(eipHistory, ERROR.OBJECT_NULL);
			EipHistoryDTO eipHistoryDTO = BeanMapper.map(eipHistory, EipHistoryDTO.class);
			result.setDto(eipHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipHistoryDTO> findEipHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<EipHistoryDTO> result = new DTOResult<EipHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			EipHistory eipHistory = comm.eipHistoryService.findByCode(code);
			Validate.notNull(eipHistory, ERROR.OBJECT_NULL);
			EipHistoryDTO eipHistoryDTO = BeanMapper.map(eipHistory, EipHistoryDTO.class);
			result.setDto(eipHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEipHistory(@WebParam(name = "eipHistoryDTO") EipHistoryDTO eipHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipHistoryService.findByCode(eipHistoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			EipHistory eipHistory = BeanMapper.map(eipHistoryDTO, EipHistory.class);
			BeanValidators.validateWithException(validator, eipHistory);
			comm.eipHistoryService.saveOrUpdate(eipHistory);
			return new IdResult(eipHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEipHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipHistoryDTO") EipHistoryDTO eipHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipHistoryDTO, ERROR.INPUT_NULL);
			EipHistory eipHistory = comm.eipHistoryService.findEipHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipHistoryService.findByCode(eipHistoryDTO.getCode()) == null
					|| eipHistory.getCode().equals(eipHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EipHistory eipHistoryEntity = BeanMapper.map(eipHistoryDTO, EipHistory.class);
			BeanMapper.copy(eipHistoryEntity, eipHistory);
			eipHistoryEntity.setIdClass(EipHistory.class.getSimpleName());
			eipHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.eipHistoryService.saveOrUpdate(eipHistoryEntity);
			return new IdResult(eipHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEipHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipHistory eipHistory = comm.eipHistoryService.findEipHistory(id);
			EipHistory eipHistoryEntity = BeanMapper.map(findEipHistory(id).getDto(), EipHistory.class);
			BeanMapper.copy(eipHistoryEntity, eipHistory);
			eipHistoryEntity.setIdClass(EipHistory.class.getSimpleName());
			eipHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.eipHistoryService.saveOrUpdate(eipHistoryEntity);
			return new IdResult(eipHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipHistoryDTO> getEipHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EipHistoryDTO> result = new PaginationResult<EipHistoryDTO>();
		try {
			result = comm.eipHistoryService.getEipHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipHistoryDTO> getEipHistoryList() {
		DTOListResult<EipHistoryDTO> result = new DTOListResult<EipHistoryDTO>();
		try {
			List<EipHistory> companies = comm.eipHistoryService.getCompanies();
			List<EipHistoryDTO> list = BeanMapper.mapList(companies, EipHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}