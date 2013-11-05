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
import com.sobey.cmdbuild.entity.ElbPolicyHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.ElbPolicyHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.ElbPolicyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "ElbPolicyHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.ElbPolicyHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class ElbPolicyHistorySoapServiceImpl extends BasicSoapSevcie implements ElbPolicyHistorySoapService {
	@Override
	public DTOResult<ElbPolicyHistoryDTO> findElbPolicyHistory(@WebParam(name = "id") Integer id) {
		DTOResult<ElbPolicyHistoryDTO> result = new DTOResult<ElbPolicyHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ElbPolicyHistory elbPolicyHistory = comm.elbPolicyHistoryService.findElbPolicyHistory(id);
			Validate.notNull(elbPolicyHistory, ERROR.OBJECT_NULL);
			ElbPolicyHistoryDTO elbPolicyHistoryDTO = BeanMapper.map(elbPolicyHistory, ElbPolicyHistoryDTO.class);
			result.setDto(elbPolicyHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ElbPolicyHistoryDTO> findElbPolicyHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<ElbPolicyHistoryDTO> result = new DTOResult<ElbPolicyHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			ElbPolicyHistory elbPolicyHistory = comm.elbPolicyHistoryService.findByCode(code);
			Validate.notNull(elbPolicyHistory, ERROR.OBJECT_NULL);
			ElbPolicyHistoryDTO elbPolicyHistoryDTO = BeanMapper.map(elbPolicyHistory, ElbPolicyHistoryDTO.class);
			result.setDto(elbPolicyHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createElbPolicyHistory(
			@WebParam(name = "elbPolicyHistoryDTO") ElbPolicyHistoryDTO elbPolicyHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(elbPolicyHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.elbPolicyHistoryService.findByCode(elbPolicyHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			ElbPolicyHistory elbPolicyHistory = BeanMapper.map(elbPolicyHistoryDTO, ElbPolicyHistory.class);
			BeanValidators.validateWithException(validator, elbPolicyHistory);
			comm.elbPolicyHistoryService.saveOrUpdate(elbPolicyHistory);
			return new IdResult(elbPolicyHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateElbPolicyHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "elbPolicyHistoryDTO") ElbPolicyHistoryDTO elbPolicyHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(elbPolicyHistoryDTO, ERROR.INPUT_NULL);
			ElbPolicyHistory elbPolicyHistory = comm.elbPolicyHistoryService.findElbPolicyHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.elbPolicyHistoryService.findByCode(elbPolicyHistoryDTO.getCode()) == null
					|| elbPolicyHistory.getCode().equals(elbPolicyHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			ElbPolicyHistory elbPolicyHistoryEntity = BeanMapper.map(elbPolicyHistoryDTO, ElbPolicyHistory.class);
			BeanMapper.copy(elbPolicyHistoryEntity, elbPolicyHistory);
			elbPolicyHistoryEntity.setIdClass(ElbPolicyHistory.class.getSimpleName());
			elbPolicyHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.elbPolicyHistoryService.saveOrUpdate(elbPolicyHistoryEntity);
			return new IdResult(elbPolicyHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteElbPolicyHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ElbPolicyHistory elbPolicyHistory = comm.elbPolicyHistoryService.findElbPolicyHistory(id);
			ElbPolicyHistory elbPolicyHistoryEntity = BeanMapper.map(findElbPolicyHistory(id).getDto(),
					ElbPolicyHistory.class);
			BeanMapper.copy(elbPolicyHistoryEntity, elbPolicyHistory);
			elbPolicyHistoryEntity.setIdClass(ElbPolicyHistory.class.getSimpleName());
			elbPolicyHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.elbPolicyHistoryService.saveOrUpdate(elbPolicyHistoryEntity);
			return new IdResult(elbPolicyHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ElbPolicyHistoryDTO> getElbPolicyHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ElbPolicyHistoryDTO> result = new PaginationResult<ElbPolicyHistoryDTO>();
		try {
			result = comm.elbPolicyHistoryService.getElbPolicyHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ElbPolicyHistoryDTO> getElbPolicyHistoryList() {
		DTOListResult<ElbPolicyHistoryDTO> result = new DTOListResult<ElbPolicyHistoryDTO>();
		try {
			List<ElbPolicyHistory> companies = comm.elbPolicyHistoryService.getCompanies();
			List<ElbPolicyHistoryDTO> list = BeanMapper.mapList(companies, ElbPolicyHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}