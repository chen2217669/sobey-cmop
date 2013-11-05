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
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.RackSoapService;
import com.sobey.cmdbuild.webservice.response.dto.RackDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "RackService", endpointInterface = "com.sobey.cmdbuild.webservice.RackSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class RackSoapServiceImpl extends BasicSoapSevcie implements RackSoapService {
	@Override
	public DTOResult<RackDTO> findRack(@WebParam(name = "id") Integer id) {
		DTOResult<RackDTO> result = new DTOResult<RackDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Rack rack = comm.rackService.findRack(id);
			Validate.notNull(rack, ERROR.OBJECT_NULL);
			RackDTO rackDTO = BeanMapper.map(rack, RackDTO.class);
			result.setDto(rackDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<RackDTO> findRackByCode(@WebParam(name = "code") String code) {
		DTOResult<RackDTO> result = new DTOResult<RackDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Rack rack = comm.rackService.findByCode(code);
			Validate.notNull(rack, ERROR.OBJECT_NULL);
			RackDTO rackDTO = BeanMapper.map(rack, RackDTO.class);
			result.setDto(rackDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createRack(@WebParam(name = "rackDTO") RackDTO rackDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(rackDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.rackService.findByCode(rackDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Rack rack = BeanMapper.map(rackDTO, Rack.class);
			BeanValidators.validateWithException(validator, rack);
			comm.rackService.saveOrUpdate(rack);
			return new IdResult(rack.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateRack(@WebParam(name = "id") Integer id, @WebParam(name = "rackDTO") RackDTO rackDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(rackDTO, ERROR.INPUT_NULL);
			Rack rack = comm.rackService.findRack(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.rackService.findByCode(rackDTO.getCode()) == null || rack.getCode().equals(rackDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Rack rackEntity = BeanMapper.map(rackDTO, Rack.class);
			BeanMapper.copy(rackEntity, rack);
			rackEntity.setIdClass(Rack.class.getSimpleName());
			rackEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.rackService.saveOrUpdate(rackEntity);
			return new IdResult(rack.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteRack(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Rack rack = comm.rackService.findRack(id);
			Rack rackEntity = BeanMapper.map(findRack(id).getDto(), Rack.class);
			BeanMapper.copy(rackEntity, rack);
			rackEntity.setIdClass(Rack.class.getSimpleName());
			rackEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.rackService.saveOrUpdate(rackEntity);
			return new IdResult(rack.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<RackDTO> getRackPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<RackDTO> result = new PaginationResult<RackDTO>();
		try {
			result = comm.rackService.getRackDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<RackDTO> getRackList() {
		DTOListResult<RackDTO> result = new DTOListResult<RackDTO>();
		try {
			List<Rack> companies = comm.rackService.getCompanies();
			List<RackDTO> list = BeanMapper.mapList(companies, RackDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}