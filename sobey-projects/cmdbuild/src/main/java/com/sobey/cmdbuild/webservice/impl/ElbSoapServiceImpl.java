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
import com.sobey.cmdbuild.entity.Elb;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.ElbSoapService;
import com.sobey.cmdbuild.webservice.response.dto.ElbDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "ElbService", endpointInterface = "com.sobey.cmdbuild.webservice.ElbSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class ElbSoapServiceImpl extends BasicSoapSevcie implements ElbSoapService {
	@Override
	public DTOResult<ElbDTO> findElb(@WebParam(name = "id") Integer id) {
		DTOResult<ElbDTO> result = new DTOResult<ElbDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Elb elb = comm.elbService.findElb(id);
			Validate.notNull(elb, ERROR.OBJECT_NULL);
			ElbDTO elbDTO = BeanMapper.map(elb, ElbDTO.class);
			result.setDto(elbDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ElbDTO> findElbByCode(@WebParam(name = "code") String code) {
		DTOResult<ElbDTO> result = new DTOResult<ElbDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Elb elb = comm.elbService.findByCode(code);
			Validate.notNull(elb, ERROR.OBJECT_NULL);
			ElbDTO elbDTO = BeanMapper.map(elb, ElbDTO.class);
			result.setDto(elbDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createElb(@WebParam(name = "elbDTO") ElbDTO elbDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(elbDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.elbService.findByCode(elbDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Elb elb = BeanMapper.map(elbDTO, Elb.class);
			BeanValidators.validateWithException(validator, elb);
			comm.elbService.saveOrUpdate(elb);
			return new IdResult(elb.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateElb(@WebParam(name = "id") Integer id, @WebParam(name = "elbDTO") ElbDTO elbDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(elbDTO, ERROR.INPUT_NULL);
			Elb elb = comm.elbService.findElb(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.elbService.findByCode(elbDTO.getCode()) == null || elb.getCode().equals(elbDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Elb elbEntity = BeanMapper.map(elbDTO, Elb.class);
			BeanMapper.copy(elbEntity, elb);
			elbEntity.setIdClass(Elb.class.getSimpleName());
			elbEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.elbService.saveOrUpdate(elbEntity);
			return new IdResult(elb.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteElb(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Elb elb = comm.elbService.findElb(id);
			Elb elbEntity = BeanMapper.map(findElb(id).getDto(), Elb.class);
			BeanMapper.copy(elbEntity, elb);
			elbEntity.setIdClass(Elb.class.getSimpleName());
			elbEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.elbService.saveOrUpdate(elbEntity);
			return new IdResult(elb.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ElbDTO> getElbPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ElbDTO> result = new PaginationResult<ElbDTO>();
		try {
			result = comm.elbService.getElbDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ElbDTO> getElbList() {
		DTOListResult<ElbDTO> result = new DTOListResult<ElbDTO>();
		try {
			List<Elb> companies = comm.elbService.getCompanies();
			List<ElbDTO> list = BeanMapper.mapList(companies, ElbDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}