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
import com.sobey.cmdbuild.entity.Esg;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EsgSoapService;
import com.sobey.cmdbuild.webservice.response.dto.EsgDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EsgService", endpointInterface = "com.sobey.cmdbuild.webservice.EsgSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EsgSoapServiceImpl extends BasicSoapSevcie implements EsgSoapService {
	@Override
	public DTOResult<EsgDTO> findEsg(@WebParam(name = "id") Integer id) {
		DTOResult<EsgDTO> result = new DTOResult<EsgDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Esg esg = comm.esgService.findEsg(id);
			Validate.notNull(esg, ERROR.OBJECT_NULL);
			EsgDTO esgDTO = BeanMapper.map(esg, EsgDTO.class);
			result.setDto(esgDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EsgDTO> findEsgByCode(@WebParam(name = "code") String code) {
		DTOResult<EsgDTO> result = new DTOResult<EsgDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Esg esg = comm.esgService.findByCode(code);
			Validate.notNull(esg, ERROR.OBJECT_NULL);
			EsgDTO esgDTO = BeanMapper.map(esg, EsgDTO.class);
			result.setDto(esgDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEsg(@WebParam(name = "esgDTO") EsgDTO esgDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(esgDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.esgService.findByCode(esgDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Esg esg = BeanMapper.map(esgDTO, Esg.class);
			BeanValidators.validateWithException(validator, esg);
			comm.esgService.saveOrUpdate(esg);
			return new IdResult(esg.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEsg(@WebParam(name = "id") Integer id, @WebParam(name = "esgDTO") EsgDTO esgDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(esgDTO, ERROR.INPUT_NULL);
			Esg esg = comm.esgService.findEsg(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.esgService.findByCode(esgDTO.getCode()) == null || esg.getCode().equals(esgDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Esg esgEntity = BeanMapper.map(esgDTO, Esg.class);
			BeanMapper.copy(esgEntity, esg);
			esgEntity.setIdClass(Esg.class.getSimpleName());
			esgEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.esgService.saveOrUpdate(esgEntity);
			return new IdResult(esg.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEsg(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Esg esg = comm.esgService.findEsg(id);
			Esg esgEntity = BeanMapper.map(findEsg(id).getDto(), Esg.class);
			BeanMapper.copy(esgEntity, esg);
			esgEntity.setIdClass(Esg.class.getSimpleName());
			esgEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.esgService.saveOrUpdate(esgEntity);
			return new IdResult(esg.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EsgDTO> getEsgPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EsgDTO> result = new PaginationResult<EsgDTO>();
		try {
			result = comm.esgService.getEsgDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EsgDTO> getEsgList() {
		DTOListResult<EsgDTO> result = new DTOListResult<EsgDTO>();
		try {
			List<Esg> companies = comm.esgService.getCompanies();
			List<EsgDTO> list = BeanMapper.mapList(companies, EsgDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}