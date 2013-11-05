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
import com.sobey.cmdbuild.entity.EipPolicy;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EipPolicySoapService;
import com.sobey.cmdbuild.webservice.response.dto.EipPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EipPolicyService", endpointInterface = "com.sobey.cmdbuild.webservice.EipPolicySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EipPolicySoapServiceImpl extends BasicSoapSevcie implements EipPolicySoapService {
	@Override
	public DTOResult<EipPolicyDTO> findEipPolicy(@WebParam(name = "id") Integer id) {
		DTOResult<EipPolicyDTO> result = new DTOResult<EipPolicyDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipPolicy eipPolicy = comm.eipPolicyService.findEipPolicy(id);
			Validate.notNull(eipPolicy, ERROR.OBJECT_NULL);
			EipPolicyDTO eipPolicyDTO = BeanMapper.map(eipPolicy, EipPolicyDTO.class);
			result.setDto(eipPolicyDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipPolicyDTO> findEipPolicyByCode(@WebParam(name = "code") String code) {
		DTOResult<EipPolicyDTO> result = new DTOResult<EipPolicyDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			EipPolicy eipPolicy = comm.eipPolicyService.findByCode(code);
			Validate.notNull(eipPolicy, ERROR.OBJECT_NULL);
			EipPolicyDTO eipPolicyDTO = BeanMapper.map(eipPolicy, EipPolicyDTO.class);
			result.setDto(eipPolicyDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEipPolicy(@WebParam(name = "eipPolicyDTO") EipPolicyDTO eipPolicyDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipPolicyDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipPolicyService.findByCode(eipPolicyDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			EipPolicy eipPolicy = BeanMapper.map(eipPolicyDTO, EipPolicy.class);
			BeanValidators.validateWithException(validator, eipPolicy);
			comm.eipPolicyService.saveOrUpdate(eipPolicy);
			return new IdResult(eipPolicy.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEipPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipPolicyDTO") EipPolicyDTO eipPolicyDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipPolicyDTO, ERROR.INPUT_NULL);
			EipPolicy eipPolicy = comm.eipPolicyService.findEipPolicy(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipPolicyService.findByCode(eipPolicyDTO.getCode()) == null
					|| eipPolicy.getCode().equals(eipPolicyDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EipPolicy eipPolicyEntity = BeanMapper.map(eipPolicyDTO, EipPolicy.class);
			BeanMapper.copy(eipPolicyEntity, eipPolicy);
			eipPolicyEntity.setIdClass(EipPolicy.class.getSimpleName());
			eipPolicyEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.eipPolicyService.saveOrUpdate(eipPolicyEntity);
			return new IdResult(eipPolicy.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEipPolicy(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipPolicy eipPolicy = comm.eipPolicyService.findEipPolicy(id);
			EipPolicy eipPolicyEntity = BeanMapper.map(findEipPolicy(id).getDto(), EipPolicy.class);
			BeanMapper.copy(eipPolicyEntity, eipPolicy);
			eipPolicyEntity.setIdClass(EipPolicy.class.getSimpleName());
			eipPolicyEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.eipPolicyService.saveOrUpdate(eipPolicyEntity);
			return new IdResult(eipPolicy.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipPolicyDTO> getEipPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EipPolicyDTO> result = new PaginationResult<EipPolicyDTO>();
		try {
			result = comm.eipPolicyService.getEipPolicyDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipPolicyDTO> getEipPolicyList() {
		DTOListResult<EipPolicyDTO> result = new DTOListResult<EipPolicyDTO>();
		try {
			List<EipPolicy> companies = comm.eipPolicyService.getCompanies();
			List<EipPolicyDTO> list = BeanMapper.mapList(companies, EipPolicyDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}