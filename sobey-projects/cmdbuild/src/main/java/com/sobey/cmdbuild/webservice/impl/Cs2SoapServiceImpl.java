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
import com.sobey.cmdbuild.entity.Cs2;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.Cs2SoapService;
import com.sobey.cmdbuild.webservice.response.dto.Cs2DTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "Cs2Service", endpointInterface = "com.sobey.cmdbuild.webservice.Cs2SoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class Cs2SoapServiceImpl extends BasicSoapSevcie implements Cs2SoapService {
	@Override
	public DTOResult<Cs2DTO> findCs2(@WebParam(name = "id") Integer id) {
		DTOResult<Cs2DTO> result = new DTOResult<Cs2DTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Cs2 cs2 = comm.cs2Service.findCs2(id);
			Validate.notNull(cs2, ERROR.OBJECT_NULL);
			Cs2DTO cs2DTO = BeanMapper.map(cs2, Cs2DTO.class);
			result.setDto(cs2DTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Cs2DTO> findCs2ByCode(@WebParam(name = "code") String code) {
		DTOResult<Cs2DTO> result = new DTOResult<Cs2DTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Cs2 cs2 = comm.cs2Service.findByCode(code);
			Validate.notNull(cs2, ERROR.OBJECT_NULL);
			Cs2DTO cs2DTO = BeanMapper.map(cs2, Cs2DTO.class);
			result.setDto(cs2DTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createCs2(@WebParam(name = "cs2DTO") Cs2DTO cs2DTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(cs2DTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.cs2Service.findByCode(cs2DTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Cs2 cs2 = BeanMapper.map(cs2DTO, Cs2.class);
			BeanValidators.validateWithException(validator, cs2);
			comm.cs2Service.saveOrUpdate(cs2);
			return new IdResult(cs2.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateCs2(@WebParam(name = "id") Integer id, @WebParam(name = "cs2DTO") Cs2DTO cs2DTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(cs2DTO, ERROR.INPUT_NULL);
			Cs2 cs2 = comm.cs2Service.findCs2(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.cs2Service.findByCode(cs2DTO.getCode()) == null || cs2.getCode().equals(cs2DTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Cs2 cs2Entity = BeanMapper.map(cs2DTO, Cs2.class);
			BeanMapper.copy(cs2Entity, cs2);
			cs2Entity.setIdClass(Cs2.class.getSimpleName());
			cs2Entity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.cs2Service.saveOrUpdate(cs2Entity);
			return new IdResult(cs2.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteCs2(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Cs2 cs2 = comm.cs2Service.findCs2(id);
			Cs2 cs2Entity = BeanMapper.map(findCs2(id).getDto(), Cs2.class);
			BeanMapper.copy(cs2Entity, cs2);
			cs2Entity.setIdClass(Cs2.class.getSimpleName());
			cs2Entity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.cs2Service.saveOrUpdate(cs2Entity);
			return new IdResult(cs2.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<Cs2DTO> getCs2Pagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<Cs2DTO> result = new PaginationResult<Cs2DTO>();
		try {
			result = comm.cs2Service.getCs2DTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<Cs2DTO> getCs2List() {
		DTOListResult<Cs2DTO> result = new DTOListResult<Cs2DTO>();
		try {
			List<Cs2> companies = comm.cs2Service.getCompanies();
			List<Cs2DTO> list = BeanMapper.mapList(companies, Cs2DTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}