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
import com.sobey.cmdbuild.entity.VlanHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.VlanHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.VlanHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "VlanHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.VlanHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class VlanHistorySoapServiceImpl extends BasicSoapSevcie implements VlanHistorySoapService {
	@Override
	public DTOResult<VlanHistoryDTO> findVlanHistory(@WebParam(name = "id") Integer id) {
		DTOResult<VlanHistoryDTO> result = new DTOResult<VlanHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			VlanHistory vlanHistory = comm.vlanHistoryService.findVlanHistory(id);
			Validate.notNull(vlanHistory, ERROR.OBJECT_NULL);
			VlanHistoryDTO vlanHistoryDTO = BeanMapper.map(vlanHistory, VlanHistoryDTO.class);
			result.setDto(vlanHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<VlanHistoryDTO> findVlanHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<VlanHistoryDTO> result = new DTOResult<VlanHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			VlanHistory vlanHistory = comm.vlanHistoryService.findByCode(code);
			Validate.notNull(vlanHistory, ERROR.OBJECT_NULL);
			VlanHistoryDTO vlanHistoryDTO = BeanMapper.map(vlanHistory, VlanHistoryDTO.class);
			result.setDto(vlanHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createVlanHistory(@WebParam(name = "vlanHistoryDTO") VlanHistoryDTO vlanHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(vlanHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.vlanHistoryService.findByCode(vlanHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			VlanHistory vlanHistory = BeanMapper.map(vlanHistoryDTO, VlanHistory.class);
			BeanValidators.validateWithException(validator, vlanHistory);
			comm.vlanHistoryService.saveOrUpdate(vlanHistory);
			return new IdResult(vlanHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateVlanHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "vlanHistoryDTO") VlanHistoryDTO vlanHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(vlanHistoryDTO, ERROR.INPUT_NULL);
			VlanHistory vlanHistory = comm.vlanHistoryService.findVlanHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.vlanHistoryService.findByCode(vlanHistoryDTO.getCode()) == null
					|| vlanHistory.getCode().equals(vlanHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			VlanHistory vlanHistoryEntity = BeanMapper.map(vlanHistoryDTO, VlanHistory.class);
			BeanMapper.copy(vlanHistoryEntity, vlanHistory);
			vlanHistoryEntity.setIdClass(VlanHistory.class.getSimpleName());
			vlanHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.vlanHistoryService.saveOrUpdate(vlanHistoryEntity);
			return new IdResult(vlanHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteVlanHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			VlanHistory vlanHistory = comm.vlanHistoryService.findVlanHistory(id);
			VlanHistory vlanHistoryEntity = BeanMapper.map(findVlanHistory(id).getDto(), VlanHistory.class);
			BeanMapper.copy(vlanHistoryEntity, vlanHistory);
			vlanHistoryEntity.setIdClass(VlanHistory.class.getSimpleName());
			vlanHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.vlanHistoryService.saveOrUpdate(vlanHistoryEntity);
			return new IdResult(vlanHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<VlanHistoryDTO> getVlanHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<VlanHistoryDTO> result = new PaginationResult<VlanHistoryDTO>();
		try {
			result = comm.vlanHistoryService.getVlanHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<VlanHistoryDTO> getVlanHistoryList() {
		DTOListResult<VlanHistoryDTO> result = new DTOListResult<VlanHistoryDTO>();
		try {
			List<VlanHistory> companies = comm.vlanHistoryService.getCompanies();
			List<VlanHistoryDTO> list = BeanMapper.mapList(companies, VlanHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}