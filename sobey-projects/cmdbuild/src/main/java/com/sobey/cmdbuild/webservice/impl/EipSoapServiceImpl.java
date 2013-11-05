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
import com.sobey.cmdbuild.entity.Eip;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EipSoapService;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EipService", endpointInterface = "com.sobey.cmdbuild.webservice.EipSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EipSoapServiceImpl extends BasicSoapSevcie implements EipSoapService {
	@Override
	public DTOResult<EipDTO> findEip(@WebParam(name = "id") Integer id) {
		DTOResult<EipDTO> result = new DTOResult<EipDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Eip eip = comm.eipService.findEip(id);
			Validate.notNull(eip, ERROR.OBJECT_NULL);
			EipDTO eipDTO = BeanMapper.map(eip, EipDTO.class);
			result.setDto(eipDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipDTO> findEipByCode(@WebParam(name = "code") String code) {
		DTOResult<EipDTO> result = new DTOResult<EipDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Eip eip = comm.eipService.findByCode(code);
			Validate.notNull(eip, ERROR.OBJECT_NULL);
			EipDTO eipDTO = BeanMapper.map(eip, EipDTO.class);
			result.setDto(eipDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEip(@WebParam(name = "eipDTO") EipDTO eipDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipService.findByCode(eipDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Eip eip = BeanMapper.map(eipDTO, Eip.class);
			BeanValidators.validateWithException(validator, eip);
			comm.eipService.saveOrUpdate(eip);
			return new IdResult(eip.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEip(@WebParam(name = "id") Integer id, @WebParam(name = "eipDTO") EipDTO eipDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipDTO, ERROR.INPUT_NULL);
			Eip eip = comm.eipService.findEip(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.eipService.findByCode(eipDTO.getCode()) == null || eip.getCode().equals(eipDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Eip eipEntity = BeanMapper.map(eipDTO, Eip.class);
			BeanMapper.copy(eipEntity, eip);
			eipEntity.setIdClass(Eip.class.getSimpleName());
			eipEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.eipService.saveOrUpdate(eipEntity);
			return new IdResult(eip.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEip(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Eip eip = comm.eipService.findEip(id);
			Eip eipEntity = BeanMapper.map(findEip(id).getDto(), Eip.class);
			BeanMapper.copy(eipEntity, eip);
			eipEntity.setIdClass(Eip.class.getSimpleName());
			eipEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.eipService.saveOrUpdate(eipEntity);
			return new IdResult(eip.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipDTO> getEipPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EipDTO> result = new PaginationResult<EipDTO>();
		try {
			result = comm.eipService.getEipDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipDTO> getEipList() {
		DTOListResult<EipDTO> result = new DTOListResult<EipDTO>();
		try {
			List<Eip> companies = comm.eipService.getCompanies();
			List<EipDTO> list = BeanMapper.mapList(companies, EipDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}