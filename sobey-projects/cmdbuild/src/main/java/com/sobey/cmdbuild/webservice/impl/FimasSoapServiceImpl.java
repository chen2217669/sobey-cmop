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
import com.sobey.cmdbuild.entity.Fimas;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.FimasSoapService;
import com.sobey.cmdbuild.webservice.response.dto.FimasDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "FimasService", endpointInterface = "com.sobey.cmdbuild.webservice.FimasSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FimasSoapServiceImpl extends BasicSoapSevcie implements FimasSoapService {
	@Override
	public DTOResult<FimasDTO> findFimas(@WebParam(name = "id") Integer id) {
		DTOResult<FimasDTO> result = new DTOResult<FimasDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Fimas fimas = comm.fimasService.findFimas(id);
			Validate.notNull(fimas, ERROR.OBJECT_NULL);
			FimasDTO fimasDTO = BeanMapper.map(fimas, FimasDTO.class);
			result.setDto(fimasDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasDTO> findFimasByCode(@WebParam(name = "code") String code) {
		DTOResult<FimasDTO> result = new DTOResult<FimasDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Fimas fimas = comm.fimasService.findByCode(code);
			Validate.notNull(fimas, ERROR.OBJECT_NULL);
			FimasDTO fimasDTO = BeanMapper.map(fimas, FimasDTO.class);
			result.setDto(fimasDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createFimas(@WebParam(name = "fimasDTO") FimasDTO fimasDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasService.findByCode(fimasDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Fimas fimas = BeanMapper.map(fimasDTO, Fimas.class);
			BeanValidators.validateWithException(validator, fimas);
			comm.fimasService.saveOrUpdate(fimas);
			return new IdResult(fimas.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimas(@WebParam(name = "id") Integer id, @WebParam(name = "fimasDTO") FimasDTO fimasDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasDTO, ERROR.INPUT_NULL);
			Fimas fimas = comm.fimasService.findFimas(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.fimasService.findByCode(fimasDTO.getCode()) == null
							|| fimas.getCode().equals(fimasDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Fimas fimasEntity = BeanMapper.map(fimasDTO, Fimas.class);
			BeanMapper.copy(fimasEntity, fimas);
			fimasEntity.setIdClass(Fimas.class.getSimpleName());
			fimasEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.fimasService.saveOrUpdate(fimasEntity);
			return new IdResult(fimas.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimas(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Fimas fimas = comm.fimasService.findFimas(id);
			Fimas fimasEntity = BeanMapper.map(findFimas(id).getDto(), Fimas.class);
			BeanMapper.copy(fimasEntity, fimas);
			fimasEntity.setIdClass(Fimas.class.getSimpleName());
			fimasEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.fimasService.saveOrUpdate(fimasEntity);
			return new IdResult(fimas.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasDTO> getFimasPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FimasDTO> result = new PaginationResult<FimasDTO>();
		try {
			result = comm.fimasService.getFimasDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasDTO> getFimasList() {
		DTOListResult<FimasDTO> result = new DTOListResult<FimasDTO>();
		try {
			List<Fimas> companies = comm.fimasService.getCompanies();
			List<FimasDTO> list = BeanMapper.mapList(companies, FimasDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}