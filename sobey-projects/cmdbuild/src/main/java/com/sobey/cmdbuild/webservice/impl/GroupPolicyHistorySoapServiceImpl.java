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
import com.sobey.cmdbuild.entity.GroupPolicyHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.GroupPolicyHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.GroupPolicyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "GroupPolicyHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.GroupPolicyHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class GroupPolicyHistorySoapServiceImpl extends BasicSoapSevcie implements GroupPolicyHistorySoapService {
	@Override
	public DTOResult<GroupPolicyHistoryDTO> findGroupPolicyHistory(@WebParam(name = "id") Integer id) {
		DTOResult<GroupPolicyHistoryDTO> result = new DTOResult<GroupPolicyHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			GroupPolicyHistory groupPolicyHistory = comm.groupPolicyHistoryService.findGroupPolicyHistory(id);
			Validate.notNull(groupPolicyHistory, ERROR.OBJECT_NULL);
			GroupPolicyHistoryDTO groupPolicyHistoryDTO = BeanMapper.map(groupPolicyHistory,
					GroupPolicyHistoryDTO.class);
			result.setDto(groupPolicyHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<GroupPolicyHistoryDTO> findGroupPolicyHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<GroupPolicyHistoryDTO> result = new DTOResult<GroupPolicyHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			GroupPolicyHistory groupPolicyHistory = comm.groupPolicyHistoryService.findByCode(code);
			Validate.notNull(groupPolicyHistory, ERROR.OBJECT_NULL);
			GroupPolicyHistoryDTO groupPolicyHistoryDTO = BeanMapper.map(groupPolicyHistory,
					GroupPolicyHistoryDTO.class);
			result.setDto(groupPolicyHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createGroupPolicyHistory(
			@WebParam(name = "groupPolicyHistoryDTO") GroupPolicyHistoryDTO groupPolicyHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(groupPolicyHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.groupPolicyHistoryService.findByCode(groupPolicyHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			GroupPolicyHistory groupPolicyHistory = BeanMapper.map(groupPolicyHistoryDTO, GroupPolicyHistory.class);
			BeanValidators.validateWithException(validator, groupPolicyHistory);
			comm.groupPolicyHistoryService.saveOrUpdate(groupPolicyHistory);
			return new IdResult(groupPolicyHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateGroupPolicyHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "groupPolicyHistoryDTO") GroupPolicyHistoryDTO groupPolicyHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(groupPolicyHistoryDTO, ERROR.INPUT_NULL);
			GroupPolicyHistory groupPolicyHistory = comm.groupPolicyHistoryService.findGroupPolicyHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.groupPolicyHistoryService.findByCode(groupPolicyHistoryDTO.getCode()) == null
					|| groupPolicyHistory.getCode().equals(groupPolicyHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			GroupPolicyHistory groupPolicyHistoryEntity = BeanMapper.map(groupPolicyHistoryDTO,
					GroupPolicyHistory.class);
			BeanMapper.copy(groupPolicyHistoryEntity, groupPolicyHistory);
			groupPolicyHistoryEntity.setIdClass(GroupPolicyHistory.class.getSimpleName());
			groupPolicyHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.groupPolicyHistoryService.saveOrUpdate(groupPolicyHistoryEntity);
			return new IdResult(groupPolicyHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteGroupPolicyHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			GroupPolicyHistory groupPolicyHistory = comm.groupPolicyHistoryService.findGroupPolicyHistory(id);
			GroupPolicyHistory groupPolicyHistoryEntity = BeanMapper.map(findGroupPolicyHistory(id).getDto(),
					GroupPolicyHistory.class);
			BeanMapper.copy(groupPolicyHistoryEntity, groupPolicyHistory);
			groupPolicyHistoryEntity.setIdClass(GroupPolicyHistory.class.getSimpleName());
			groupPolicyHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.groupPolicyHistoryService.saveOrUpdate(groupPolicyHistoryEntity);
			return new IdResult(groupPolicyHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<GroupPolicyHistoryDTO> getGroupPolicyHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<GroupPolicyHistoryDTO> result = new PaginationResult<GroupPolicyHistoryDTO>();
		try {
			result = comm.groupPolicyHistoryService.getGroupPolicyHistoryDTOPagination(searchParams, pageNumber,
					pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<GroupPolicyHistoryDTO> getGroupPolicyHistoryList() {
		DTOListResult<GroupPolicyHistoryDTO> result = new DTOListResult<GroupPolicyHistoryDTO>();
		try {
			List<GroupPolicyHistory> companies = comm.groupPolicyHistoryService.getCompanies();
			List<GroupPolicyHistoryDTO> list = BeanMapper.mapList(companies, GroupPolicyHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}