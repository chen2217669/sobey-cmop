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
import com.sobey.cmdbuild.entity.DeviceSpecHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.DeviceSpecHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "DeviceSpecHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.DeviceSpecHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class DeviceSpecHistorySoapServiceImpl extends BasicSoapSevcie implements DeviceSpecHistorySoapService {
	@Override
	public DTOResult<DeviceSpecHistoryDTO> findDeviceSpecHistory(@WebParam(name = "id") Integer id) {
		DTOResult<DeviceSpecHistoryDTO> result = new DTOResult<DeviceSpecHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			DeviceSpecHistory deviceSpecHistory = comm.deviceSpecHistoryService.findDeviceSpecHistory(id);
			Validate.notNull(deviceSpecHistory, ERROR.OBJECT_NULL);
			DeviceSpecHistoryDTO deviceSpecHistoryDTO = BeanMapper.map(deviceSpecHistory, DeviceSpecHistoryDTO.class);
			result.setDto(deviceSpecHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DeviceSpecHistoryDTO> findDeviceSpecHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<DeviceSpecHistoryDTO> result = new DTOResult<DeviceSpecHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			DeviceSpecHistory deviceSpecHistory = comm.deviceSpecHistoryService.findByCode(code);
			Validate.notNull(deviceSpecHistory, ERROR.OBJECT_NULL);
			DeviceSpecHistoryDTO deviceSpecHistoryDTO = BeanMapper.map(deviceSpecHistory, DeviceSpecHistoryDTO.class);
			result.setDto(deviceSpecHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createDeviceSpecHistory(
			@WebParam(name = "deviceSpecHistoryDTO") DeviceSpecHistoryDTO deviceSpecHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(deviceSpecHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.deviceSpecHistoryService.findByCode(deviceSpecHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			DeviceSpecHistory deviceSpecHistory = BeanMapper.map(deviceSpecHistoryDTO, DeviceSpecHistory.class);
			BeanValidators.validateWithException(validator, deviceSpecHistory);
			comm.deviceSpecHistoryService.saveOrUpdate(deviceSpecHistory);
			return new IdResult(deviceSpecHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateDeviceSpecHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "deviceSpecHistoryDTO") DeviceSpecHistoryDTO deviceSpecHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(deviceSpecHistoryDTO, ERROR.INPUT_NULL);
			DeviceSpecHistory deviceSpecHistory = comm.deviceSpecHistoryService.findDeviceSpecHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.deviceSpecHistoryService.findByCode(deviceSpecHistoryDTO.getCode()) == null
					|| deviceSpecHistory.getCode().equals(deviceSpecHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			DeviceSpecHistory deviceSpecHistoryEntity = BeanMapper.map(deviceSpecHistoryDTO, DeviceSpecHistory.class);
			BeanMapper.copy(deviceSpecHistoryEntity, deviceSpecHistory);
			deviceSpecHistoryEntity.setIdClass(DeviceSpecHistory.class.getSimpleName());
			deviceSpecHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.deviceSpecHistoryService.saveOrUpdate(deviceSpecHistoryEntity);
			return new IdResult(deviceSpecHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteDeviceSpecHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			DeviceSpecHistory deviceSpecHistory = comm.deviceSpecHistoryService.findDeviceSpecHistory(id);
			DeviceSpecHistory deviceSpecHistoryEntity = BeanMapper.map(findDeviceSpecHistory(id).getDto(),
					DeviceSpecHistory.class);
			BeanMapper.copy(deviceSpecHistoryEntity, deviceSpecHistory);
			deviceSpecHistoryEntity.setIdClass(DeviceSpecHistory.class.getSimpleName());
			deviceSpecHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.deviceSpecHistoryService.saveOrUpdate(deviceSpecHistoryEntity);
			return new IdResult(deviceSpecHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<DeviceSpecHistoryDTO> getDeviceSpecHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<DeviceSpecHistoryDTO> result = new PaginationResult<DeviceSpecHistoryDTO>();
		try {
			result = comm.deviceSpecHistoryService
					.getDeviceSpecHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<DeviceSpecHistoryDTO> getDeviceSpecHistoryList() {
		DTOListResult<DeviceSpecHistoryDTO> result = new DTOListResult<DeviceSpecHistoryDTO>();
		try {
			List<DeviceSpecHistory> companies = comm.deviceSpecHistoryService.getCompanies();
			List<DeviceSpecHistoryDTO> list = BeanMapper.mapList(companies, DeviceSpecHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}