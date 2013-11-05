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
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.DeviceSpecSoapService;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "DeviceSpecService", endpointInterface = "com.sobey.cmdbuild.webservice.DeviceSpecSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class DeviceSpecSoapServiceImpl extends BasicSoapSevcie implements DeviceSpecSoapService {
	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpec(@WebParam(name = "id") Integer id) {
		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);
			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);
			DeviceSpecDTO deviceSpecDTO = BeanMapper.map(deviceSpec, DeviceSpecDTO.class);
			result.setDto(deviceSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpecByCode(@WebParam(name = "code") String code) {
		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			DeviceSpec deviceSpec = comm.deviceSpecService.findByCode(code);
			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);
			DeviceSpecDTO deviceSpecDTO = BeanMapper.map(deviceSpec, DeviceSpecDTO.class);
			result.setDto(deviceSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createDeviceSpec(@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(deviceSpecDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.deviceSpecService.findByCode(deviceSpecDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			DeviceSpec deviceSpec = BeanMapper.map(deviceSpecDTO, DeviceSpec.class);
			BeanValidators.validateWithException(validator, deviceSpec);
			comm.deviceSpecService.saveOrUpdate(deviceSpec);
			return new IdResult(deviceSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateDeviceSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(deviceSpecDTO, ERROR.INPUT_NULL);
			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.deviceSpecService.findByCode(deviceSpecDTO.getCode()) == null
					|| deviceSpec.getCode().equals(deviceSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			DeviceSpec deviceSpecEntity = BeanMapper.map(deviceSpecDTO, DeviceSpec.class);
			BeanMapper.copy(deviceSpecEntity, deviceSpec);
			deviceSpecEntity.setIdClass(DeviceSpec.class.getSimpleName());
			deviceSpecEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.deviceSpecService.saveOrUpdate(deviceSpecEntity);
			return new IdResult(deviceSpec.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteDeviceSpec(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);
			DeviceSpec deviceSpecEntity = BeanMapper.map(findDeviceSpec(id).getDto(), DeviceSpec.class);
			BeanMapper.copy(deviceSpecEntity, deviceSpec);
			deviceSpecEntity.setIdClass(DeviceSpec.class.getSimpleName());
			deviceSpecEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.deviceSpecService.saveOrUpdate(deviceSpecEntity);
			return new IdResult(deviceSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<DeviceSpecDTO> getDeviceSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<DeviceSpecDTO> result = new PaginationResult<DeviceSpecDTO>();
		try {
			result = comm.deviceSpecService.getDeviceSpecDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<DeviceSpecDTO> getDeviceSpecList() {
		DTOListResult<DeviceSpecDTO> result = new DTOListResult<DeviceSpecDTO>();
		try {
			List<DeviceSpec> companies = comm.deviceSpecService.getCompanies();
			List<DeviceSpecDTO> list = BeanMapper.mapList(companies, DeviceSpecDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}