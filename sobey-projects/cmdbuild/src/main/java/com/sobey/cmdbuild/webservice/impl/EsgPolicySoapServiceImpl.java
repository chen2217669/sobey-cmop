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
import com.sobey.cmdbuild.entity.EsgPolicy;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EsgPolicySoapService;
import com.sobey.cmdbuild.webservice.response.dto.EsgPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EsgPolicyService", endpointInterface = "com.sobey.cmdbuild.webservice.EsgPolicySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EsgPolicySoapServiceImpl extends BasicSoapSevcie implements EsgPolicySoapService {
	@Override
	public DTOResult<EsgPolicyDTO> findEsgPolicy(@WebParam(name = "id") Integer id) {
		DTOResult<EsgPolicyDTO> result = new DTOResult<EsgPolicyDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EsgPolicy esgPolicy = comm.esgPolicyService.findEsgPolicy(id);
			Validate.notNull(esgPolicy, ERROR.OBJECT_NULL);
			EsgPolicyDTO esgPolicyDTO = BeanMapper.map(esgPolicy, EsgPolicyDTO.class);
			result.setDto(esgPolicyDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EsgPolicyDTO> findEsgPolicyByCode(@WebParam(name = "code") String code) {
		DTOResult<EsgPolicyDTO> result = new DTOResult<EsgPolicyDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			EsgPolicy esgPolicy = comm.esgPolicyService.findByCode(code);
			Validate.notNull(esgPolicy, ERROR.OBJECT_NULL);
			EsgPolicyDTO esgPolicyDTO = BeanMapper.map(esgPolicy, EsgPolicyDTO.class);
			result.setDto(esgPolicyDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEsgPolicy(@WebParam(name = "esgPolicyDTO") EsgPolicyDTO esgPolicyDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(esgPolicyDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.esgPolicyService.findByCode(esgPolicyDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			EsgPolicy esgPolicy = BeanMapper.map(esgPolicyDTO, EsgPolicy.class);
			BeanValidators.validateWithException(validator, esgPolicy);
			comm.esgPolicyService.saveOrUpdate(esgPolicy);
			return new IdResult(esgPolicy.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEsgPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "esgPolicyDTO") EsgPolicyDTO esgPolicyDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(esgPolicyDTO, ERROR.INPUT_NULL);
			EsgPolicy esgPolicy = comm.esgPolicyService.findEsgPolicy(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.esgPolicyService.findByCode(esgPolicyDTO.getCode()) == null
					|| esgPolicy.getCode().equals(esgPolicyDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EsgPolicy esgPolicyEntity = BeanMapper.map(esgPolicyDTO, EsgPolicy.class);
			BeanMapper.copy(esgPolicyEntity, esgPolicy);
			esgPolicyEntity.setIdClass(EsgPolicy.class.getSimpleName());
			esgPolicyEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.esgPolicyService.saveOrUpdate(esgPolicyEntity);
			return new IdResult(esgPolicy.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEsgPolicy(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EsgPolicy esgPolicy = comm.esgPolicyService.findEsgPolicy(id);
			EsgPolicy esgPolicyEntity = BeanMapper.map(findEsgPolicy(id).getDto(), EsgPolicy.class);
			BeanMapper.copy(esgPolicyEntity, esgPolicy);
			esgPolicyEntity.setIdClass(EsgPolicy.class.getSimpleName());
			esgPolicyEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.esgPolicyService.saveOrUpdate(esgPolicyEntity);
			return new IdResult(esgPolicy.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EsgPolicyDTO> getEsgPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EsgPolicyDTO> result = new PaginationResult<EsgPolicyDTO>();
		try {
			result = comm.esgPolicyService.getEsgPolicyDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EsgPolicyDTO> getEsgPolicyList() {
		DTOListResult<EsgPolicyDTO> result = new DTOListResult<EsgPolicyDTO>();
		try {
			List<EsgPolicy> companies = comm.esgPolicyService.getCompanies();
			List<EsgPolicyDTO> list = BeanMapper.mapList(companies, EsgPolicyDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}