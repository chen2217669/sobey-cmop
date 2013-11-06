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
import com.sobey.cmdbuild.entity.Es3Spec;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.Es3SpecSoapService;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "Es3SpecService", endpointInterface = "com.sobey.cmdbuild.webservice.Es3SpecSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class Es3SpecSoapServiceImpl extends BasicSoapSevcie implements Es3SpecSoapService {
	@Override
	public DTOResult<Es3SpecDTO> findEs3Spec(@WebParam(name = "id") Integer id) {
		DTOResult<Es3SpecDTO> result = new DTOResult<Es3SpecDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id);
			Validate.notNull(es3Spec, ERROR.OBJECT_NULL);
			Es3SpecDTO es3SpecDTO = BeanMapper.map(es3Spec, Es3SpecDTO.class);
			result.setDto(es3SpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3SpecDTO> findEs3SpecByCode(@WebParam(name = "code") String code) {
		DTOResult<Es3SpecDTO> result = new DTOResult<Es3SpecDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Es3Spec es3Spec = comm.es3SpecService.findByCode(code);
			Validate.notNull(es3Spec, ERROR.OBJECT_NULL);
			Es3SpecDTO es3SpecDTO = BeanMapper.map(es3Spec, Es3SpecDTO.class);
			result.setDto(es3SpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEs3Spec(@WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(es3SpecDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.es3SpecService.findByCode(es3SpecDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Es3Spec es3Spec = BeanMapper.map(es3SpecDTO, Es3Spec.class);
			BeanValidators.validateWithException(validator, es3Spec);
			comm.es3SpecService.saveOrUpdate(es3Spec);
			return new IdResult(es3Spec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEs3Spec(@WebParam(name = "id") Integer id,
			@WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(es3SpecDTO, ERROR.INPUT_NULL);
			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.es3SpecService.findByCode(es3SpecDTO.getCode()) == null
							|| es3Spec.getCode().equals(es3SpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Es3Spec es3SpecEntity = BeanMapper.map(es3SpecDTO, Es3Spec.class);
			BeanMapper.copy(es3SpecEntity, es3Spec);
			es3SpecEntity.setIdClass(Es3Spec.class.getSimpleName());
			es3SpecEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.es3SpecService.saveOrUpdate(es3SpecEntity);
			return new IdResult(es3Spec.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEs3Spec(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id);
			Es3Spec es3SpecEntity = BeanMapper.map(findEs3Spec(id).getDto(), Es3Spec.class);
			BeanMapper.copy(es3SpecEntity, es3Spec);
			es3SpecEntity.setIdClass(Es3Spec.class.getSimpleName());
			es3SpecEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.es3SpecService.saveOrUpdate(es3SpecEntity);
			return new IdResult(es3Spec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<Es3SpecDTO> getEs3SpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<Es3SpecDTO> result = new PaginationResult<Es3SpecDTO>();
		try {
			result = comm.es3SpecService.getEs3SpecDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<Es3SpecDTO> getEs3SpecList() {
		DTOListResult<Es3SpecDTO> result = new DTOListResult<Es3SpecDTO>();
		try {
			List<Es3Spec> companies = comm.es3SpecService.getCompanies();
			List<Es3SpecDTO> list = BeanMapper.mapList(companies, Es3SpecDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}