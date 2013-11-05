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
import com.sobey.cmdbuild.entity.GroupPolicy;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.GroupPolicySoapService;
import com.sobey.cmdbuild.webservice.response.dto.GroupPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "GroupPolicyService", endpointInterface = "com.sobey.cmdbuild.webservice.GroupPolicySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class GroupPolicySoapServiceImpl extends BasicSoapSevcie implements GroupPolicySoapService {
	@Override
	public DTOResult<GroupPolicyDTO> findGroupPolicy(@WebParam(name = "id") Integer id) {
		DTOResult<GroupPolicyDTO> result = new DTOResult<GroupPolicyDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			GroupPolicy groupPolicy = comm.groupPolicyService.findGroupPolicy(id);
			Validate.notNull(groupPolicy, ERROR.OBJECT_NULL);
			GroupPolicyDTO groupPolicyDTO = BeanMapper.map(groupPolicy, GroupPolicyDTO.class);
			result.setDto(groupPolicyDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<GroupPolicyDTO> findGroupPolicyByCode(@WebParam(name = "code") String code) {
		DTOResult<GroupPolicyDTO> result = new DTOResult<GroupPolicyDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			GroupPolicy groupPolicy = comm.groupPolicyService.findByCode(code);
			Validate.notNull(groupPolicy, ERROR.OBJECT_NULL);
			GroupPolicyDTO groupPolicyDTO = BeanMapper.map(groupPolicy, GroupPolicyDTO.class);
			result.setDto(groupPolicyDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createGroupPolicy(@WebParam(name = "groupPolicyDTO") GroupPolicyDTO groupPolicyDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(groupPolicyDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.groupPolicyService.findByCode(groupPolicyDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			GroupPolicy groupPolicy = BeanMapper.map(groupPolicyDTO, GroupPolicy.class);
			BeanValidators.validateWithException(validator, groupPolicy);
			comm.groupPolicyService.saveOrUpdate(groupPolicy);
			return new IdResult(groupPolicy.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateGroupPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "groupPolicyDTO") GroupPolicyDTO groupPolicyDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(groupPolicyDTO, ERROR.INPUT_NULL);
			GroupPolicy groupPolicy = comm.groupPolicyService.findGroupPolicy(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.groupPolicyService.findByCode(groupPolicyDTO.getCode()) == null
					|| groupPolicy.getCode().equals(groupPolicyDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			GroupPolicy groupPolicyEntity = BeanMapper.map(groupPolicyDTO, GroupPolicy.class);
			BeanMapper.copy(groupPolicyEntity, groupPolicy);
			groupPolicyEntity.setIdClass(GroupPolicy.class.getSimpleName());
			groupPolicyEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.groupPolicyService.saveOrUpdate(groupPolicyEntity);
			return new IdResult(groupPolicy.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteGroupPolicy(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			GroupPolicy groupPolicy = comm.groupPolicyService.findGroupPolicy(id);
			GroupPolicy groupPolicyEntity = BeanMapper.map(findGroupPolicy(id).getDto(), GroupPolicy.class);
			BeanMapper.copy(groupPolicyEntity, groupPolicy);
			groupPolicyEntity.setIdClass(GroupPolicy.class.getSimpleName());
			groupPolicyEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.groupPolicyService.saveOrUpdate(groupPolicyEntity);
			return new IdResult(groupPolicy.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<GroupPolicyDTO> getGroupPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<GroupPolicyDTO> result = new PaginationResult<GroupPolicyDTO>();
		try {
			result = comm.groupPolicyService.getGroupPolicyDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<GroupPolicyDTO> getGroupPolicyList() {
		DTOListResult<GroupPolicyDTO> result = new DTOListResult<GroupPolicyDTO>();
		try {
			List<GroupPolicy> companies = comm.groupPolicyService.getCompanies();
			List<GroupPolicyDTO> list = BeanMapper.mapList(companies, GroupPolicyDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}