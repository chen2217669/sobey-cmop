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
import com.sobey.cmdbuild.entity.EipSpec;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EipSpecSoapService;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EipSpecService", endpointInterface = "com.sobey.cmdbuild.webservice.EipSpecSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EipSpecSoapServiceImpl extends BasicSoapSevcie implements EipSpecSoapService {
	@Override
	public DTOResult<EipSpecDTO> findEipSpec(@WebParam(name = "id") Integer id) {
		DTOResult<EipSpecDTO> result = new DTOResult<EipSpecDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id);
			Validate.notNull(eipSpec, ERROR.OBJECT_NULL);
			EipSpecDTO eipSpecDTO = BeanMapper.map(eipSpec, EipSpecDTO.class);
			result.setDto(eipSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipSpecDTO> findEipSpecByCode(@WebParam(name = "code") String code) {
		DTOResult<EipSpecDTO> result = new DTOResult<EipSpecDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			EipSpec eipSpec = comm.eipSpecService.findByCode(code);
			Validate.notNull(eipSpec, ERROR.OBJECT_NULL);
			EipSpecDTO eipSpecDTO = BeanMapper.map(eipSpec, EipSpecDTO.class);
			result.setDto(eipSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEipSpec(@WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipSpecDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipSpecService.findByCode(eipSpecDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			EipSpec eipSpec = BeanMapper.map(eipSpecDTO, EipSpec.class);
			BeanValidators.validateWithException(validator, eipSpec);
			comm.eipSpecService.saveOrUpdate(eipSpec);
			return new IdResult(eipSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEipSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipSpecDTO, ERROR.INPUT_NULL);
			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.eipSpecService.findByCode(eipSpecDTO.getCode()) == null
							|| eipSpec.getCode().equals(eipSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EipSpec eipSpecEntity = BeanMapper.map(eipSpecDTO, EipSpec.class);
			BeanMapper.copy(eipSpecEntity, eipSpec);
			eipSpecEntity.setIdClass(EipSpec.class.getSimpleName());
			eipSpecEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.eipSpecService.saveOrUpdate(eipSpecEntity);
			return new IdResult(eipSpec.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEipSpec(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id);
			EipSpec eipSpecEntity = BeanMapper.map(findEipSpec(id).getDto(), EipSpec.class);
			BeanMapper.copy(eipSpecEntity, eipSpec);
			eipSpecEntity.setIdClass(EipSpec.class.getSimpleName());
			eipSpecEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.eipSpecService.saveOrUpdate(eipSpecEntity);
			return new IdResult(eipSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipSpecDTO> getEipSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EipSpecDTO> result = new PaginationResult<EipSpecDTO>();
		try {
			result = comm.eipSpecService.getEipSpecDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipSpecDTO> getEipSpecList() {
		DTOListResult<EipSpecDTO> result = new DTOListResult<EipSpecDTO>();
		try {
			List<EipSpec> companies = comm.eipSpecService.getCompanies();
			List<EipSpecDTO> list = BeanMapper.mapList(companies, EipSpecDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}