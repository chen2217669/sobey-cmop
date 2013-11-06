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
import com.sobey.cmdbuild.entity.ElbPolicy;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.ElbPolicySoapService;
import com.sobey.cmdbuild.webservice.response.dto.ElbPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "ElbPolicyService", endpointInterface = "com.sobey.cmdbuild.webservice.ElbPolicySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class ElbPolicySoapServiceImpl extends BasicSoapSevcie implements ElbPolicySoapService {
	@Override
	public DTOResult<ElbPolicyDTO> findElbPolicy(@WebParam(name = "id") Integer id) {
		DTOResult<ElbPolicyDTO> result = new DTOResult<ElbPolicyDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ElbPolicy elbPolicy = comm.elbPolicyService.findElbPolicy(id);
			Validate.notNull(elbPolicy, ERROR.OBJECT_NULL);
			ElbPolicyDTO elbPolicyDTO = BeanMapper.map(elbPolicy, ElbPolicyDTO.class);
			result.setDto(elbPolicyDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ElbPolicyDTO> findElbPolicyByCode(@WebParam(name = "code") String code) {
		DTOResult<ElbPolicyDTO> result = new DTOResult<ElbPolicyDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			ElbPolicy elbPolicy = comm.elbPolicyService.findByCode(code);
			Validate.notNull(elbPolicy, ERROR.OBJECT_NULL);
			ElbPolicyDTO elbPolicyDTO = BeanMapper.map(elbPolicy, ElbPolicyDTO.class);
			result.setDto(elbPolicyDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createElbPolicy(@WebParam(name = "elbPolicyDTO") ElbPolicyDTO elbPolicyDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(elbPolicyDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.elbPolicyService.findByCode(elbPolicyDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			ElbPolicy elbPolicy = BeanMapper.map(elbPolicyDTO, ElbPolicy.class);
			BeanValidators.validateWithException(validator, elbPolicy);
			comm.elbPolicyService.saveOrUpdate(elbPolicy);
			return new IdResult(elbPolicy.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateElbPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "elbPolicyDTO") ElbPolicyDTO elbPolicyDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(elbPolicyDTO, ERROR.INPUT_NULL);
			ElbPolicy elbPolicy = comm.elbPolicyService.findElbPolicy(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.elbPolicyService.findByCode(elbPolicyDTO.getCode()) == null
					|| elbPolicy.getCode().equals(elbPolicyDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			ElbPolicy elbPolicyEntity = BeanMapper.map(elbPolicyDTO, ElbPolicy.class);
			BeanMapper.copy(elbPolicyEntity, elbPolicy);
			elbPolicyEntity.setIdClass(ElbPolicy.class.getSimpleName());
			elbPolicyEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.elbPolicyService.saveOrUpdate(elbPolicyEntity);
			return new IdResult(elbPolicy.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteElbPolicy(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ElbPolicy elbPolicy = comm.elbPolicyService.findElbPolicy(id);
			ElbPolicy elbPolicyEntity = BeanMapper.map(findElbPolicy(id).getDto(), ElbPolicy.class);
			BeanMapper.copy(elbPolicyEntity, elbPolicy);
			elbPolicyEntity.setIdClass(ElbPolicy.class.getSimpleName());
			elbPolicyEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.elbPolicyService.saveOrUpdate(elbPolicyEntity);
			return new IdResult(elbPolicy.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ElbPolicyDTO> getElbPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ElbPolicyDTO> result = new PaginationResult<ElbPolicyDTO>();
		try {
			result = comm.elbPolicyService.getElbPolicyDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ElbPolicyDTO> getElbPolicyList() {
		DTOListResult<ElbPolicyDTO> result = new DTOListResult<ElbPolicyDTO>();
		try {
			List<ElbPolicy> companies = comm.elbPolicyService.getCompanies();
			List<ElbPolicyDTO> list = BeanMapper.mapList(companies, ElbPolicyDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}