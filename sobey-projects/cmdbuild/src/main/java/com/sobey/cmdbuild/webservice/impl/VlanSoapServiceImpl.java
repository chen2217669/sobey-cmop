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
import com.sobey.cmdbuild.entity.Vlan;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.VlanSoapService;
import com.sobey.cmdbuild.webservice.response.dto.VlanDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "VlanService", endpointInterface = "com.sobey.cmdbuild.webservice.VlanSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class VlanSoapServiceImpl extends BasicSoapSevcie implements VlanSoapService {
	@Override
	public DTOResult<VlanDTO> findVlan(@WebParam(name = "id") Integer id) {
		DTOResult<VlanDTO> result = new DTOResult<VlanDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Vlan vlan = comm.vlanService.findVlan(id);
			Validate.notNull(vlan, ERROR.OBJECT_NULL);
			VlanDTO vlanDTO = BeanMapper.map(vlan, VlanDTO.class);
			result.setDto(vlanDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<VlanDTO> findVlanByCode(@WebParam(name = "code") String code) {
		DTOResult<VlanDTO> result = new DTOResult<VlanDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Vlan vlan = comm.vlanService.findByCode(code);
			Validate.notNull(vlan, ERROR.OBJECT_NULL);
			VlanDTO vlanDTO = BeanMapper.map(vlan, VlanDTO.class);
			result.setDto(vlanDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createVlan(@WebParam(name = "vlanDTO") VlanDTO vlanDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(vlanDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.vlanService.findByCode(vlanDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Vlan vlan = BeanMapper.map(vlanDTO, Vlan.class);
			BeanValidators.validateWithException(validator, vlan);
			comm.vlanService.saveOrUpdate(vlan);
			return new IdResult(vlan.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateVlan(@WebParam(name = "id") Integer id, @WebParam(name = "vlanDTO") VlanDTO vlanDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(vlanDTO, ERROR.INPUT_NULL);
			Vlan vlan = comm.vlanService.findVlan(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.vlanService.findByCode(vlanDTO.getCode()) == null || vlan.getCode().equals(vlanDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Vlan vlanEntity = BeanMapper.map(vlanDTO, Vlan.class);
			BeanMapper.copy(vlanEntity, vlan);
			vlanEntity.setIdClass(Vlan.class.getSimpleName());
			vlanEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.vlanService.saveOrUpdate(vlanEntity);
			return new IdResult(vlan.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteVlan(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Vlan vlan = comm.vlanService.findVlan(id);
			Vlan vlanEntity = BeanMapper.map(findVlan(id).getDto(), Vlan.class);
			BeanMapper.copy(vlanEntity, vlan);
			vlanEntity.setIdClass(Vlan.class.getSimpleName());
			vlanEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.vlanService.saveOrUpdate(vlanEntity);
			return new IdResult(vlan.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<VlanDTO> getVlanPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<VlanDTO> result = new PaginationResult<VlanDTO>();
		try {
			result = comm.vlanService.getVlanDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<VlanDTO> getVlanList() {
		DTOListResult<VlanDTO> result = new DTOListResult<VlanDTO>();
		try {
			List<Vlan> companies = comm.vlanService.getCompanies();
			List<VlanDTO> list = BeanMapper.mapList(companies, VlanDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}