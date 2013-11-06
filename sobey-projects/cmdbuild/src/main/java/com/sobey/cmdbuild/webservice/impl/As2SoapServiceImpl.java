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
import com.sobey.cmdbuild.entity.As2;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.As2SoapService;
import com.sobey.cmdbuild.webservice.response.dto.As2DTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "As2Service", endpointInterface = "com.sobey.cmdbuild.webservice.As2SoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class As2SoapServiceImpl extends BasicSoapSevcie implements As2SoapService {
	@Override
	public DTOResult<As2DTO> findAs2(@WebParam(name = "id") Integer id) {
		DTOResult<As2DTO> result = new DTOResult<As2DTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			As2 as2 = comm.as2Service.findAs2(id);
			Validate.notNull(as2, ERROR.OBJECT_NULL);
			As2DTO as2DTO = BeanMapper.map(as2, As2DTO.class);
			result.setDto(as2DTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<As2DTO> findAs2ByCode(@WebParam(name = "code") String code) {
		DTOResult<As2DTO> result = new DTOResult<As2DTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			As2 as2 = comm.as2Service.findByCode(code);
			Validate.notNull(as2, ERROR.OBJECT_NULL);
			As2DTO as2DTO = BeanMapper.map(as2, As2DTO.class);
			result.setDto(as2DTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createAs2(@WebParam(name = "as2DTO") As2DTO as2DTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(as2DTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.as2Service.findByCode(as2DTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			As2 as2 = BeanMapper.map(as2DTO, As2.class);
			BeanValidators.validateWithException(validator, as2);
			comm.as2Service.saveOrUpdate(as2);
			return new IdResult(as2.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateAs2(@WebParam(name = "id") Integer id, @WebParam(name = "as2DTO") As2DTO as2DTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(as2DTO, ERROR.INPUT_NULL);
			As2 as2 = comm.as2Service.findAs2(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.as2Service.findByCode(as2DTO.getCode()) == null || as2.getCode().equals(as2DTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			As2 as2Entity = BeanMapper.map(as2DTO, As2.class);
			BeanMapper.copy(as2Entity, as2);
			as2Entity.setIdClass(As2.class.getSimpleName());
			as2Entity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.as2Service.saveOrUpdate(as2Entity);
			return new IdResult(as2.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteAs2(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			As2 as2 = comm.as2Service.findAs2(id);
			As2 as2Entity = BeanMapper.map(findAs2(id).getDto(), As2.class);
			BeanMapper.copy(as2Entity, as2);
			as2Entity.setIdClass(As2.class.getSimpleName());
			as2Entity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.as2Service.saveOrUpdate(as2Entity);
			return new IdResult(as2.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<As2DTO> getAs2Pagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<As2DTO> result = new PaginationResult<As2DTO>();
		try {
			result = comm.as2Service.getAs2DTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<As2DTO> getAs2List() {
		DTOListResult<As2DTO> result = new DTOListResult<As2DTO>();
		try {
			List<As2> companies = comm.as2Service.getCompanies();
			List<As2DTO> list = BeanMapper.mapList(companies, As2DTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}