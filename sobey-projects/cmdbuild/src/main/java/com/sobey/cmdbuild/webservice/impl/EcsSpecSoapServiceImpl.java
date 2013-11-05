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
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EcsSpecSoapService;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EcsSpecService", endpointInterface = "com.sobey.cmdbuild.webservice.EcsSpecSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EcsSpecSoapServiceImpl extends BasicSoapSevcie implements EcsSpecSoapService {
	@Override
	public DTOResult<EcsSpecDTO> findEcsSpec(@WebParam(name = "id") Integer id) {
		DTOResult<EcsSpecDTO> result = new DTOResult<EcsSpecDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);
			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);
			EcsSpecDTO ecsSpecDTO = BeanMapper.map(ecsSpec, EcsSpecDTO.class);
			result.setDto(ecsSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsSpecDTO> findEcsSpecByCode(@WebParam(name = "code") String code) {
		DTOResult<EcsSpecDTO> result = new DTOResult<EcsSpecDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			EcsSpec ecsSpec = comm.ecsSpecService.findByCode(code);
			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);
			EcsSpecDTO ecsSpecDTO = BeanMapper.map(ecsSpec, EcsSpecDTO.class);
			result.setDto(ecsSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEcsSpec(@WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ecsSpecDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ecsSpecService.findByCode(ecsSpecDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			EcsSpec ecsSpec = BeanMapper.map(ecsSpecDTO, EcsSpec.class);
			BeanValidators.validateWithException(validator, ecsSpec);
			comm.ecsSpecService.saveOrUpdate(ecsSpec);
			return new IdResult(ecsSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEcsSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ecsSpecDTO, ERROR.INPUT_NULL);
			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.ecsSpecService.findByCode(ecsSpecDTO.getCode()) == null
							|| ecsSpec.getCode().equals(ecsSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EcsSpec ecsSpecEntity = BeanMapper.map(ecsSpecDTO, EcsSpec.class);
			BeanMapper.copy(ecsSpecEntity, ecsSpec);
			ecsSpecEntity.setIdClass(EcsSpec.class.getSimpleName());
			ecsSpecEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.ecsSpecService.saveOrUpdate(ecsSpecEntity);
			return new IdResult(ecsSpec.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEcsSpec(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);
			EcsSpec ecsSpecEntity = BeanMapper.map(findEcsSpec(id).getDto(), EcsSpec.class);
			BeanMapper.copy(ecsSpecEntity, ecsSpec);
			ecsSpecEntity.setIdClass(EcsSpec.class.getSimpleName());
			ecsSpecEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.ecsSpecService.saveOrUpdate(ecsSpecEntity);
			return new IdResult(ecsSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EcsSpecDTO> getEcsSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EcsSpecDTO> result = new PaginationResult<EcsSpecDTO>();
		try {
			result = comm.ecsSpecService.getEcsSpecDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EcsSpecDTO> getEcsSpecList() {
		DTOListResult<EcsSpecDTO> result = new DTOListResult<EcsSpecDTO>();
		try {
			List<EcsSpec> companies = comm.ecsSpecService.getCompanies();
			List<EcsSpecDTO> list = BeanMapper.mapList(companies, EcsSpecDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}