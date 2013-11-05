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
import com.sobey.cmdbuild.entity.VpnHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.VpnHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.VpnHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "VpnHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.VpnHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class VpnHistorySoapServiceImpl extends BasicSoapSevcie implements VpnHistorySoapService {
	@Override
	public DTOResult<VpnHistoryDTO> findVpnHistory(@WebParam(name = "id") Integer id) {
		DTOResult<VpnHistoryDTO> result = new DTOResult<VpnHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			VpnHistory vpnHistory = comm.vpnHistoryService.findVpnHistory(id);
			Validate.notNull(vpnHistory, ERROR.OBJECT_NULL);
			VpnHistoryDTO vpnHistoryDTO = BeanMapper.map(vpnHistory, VpnHistoryDTO.class);
			result.setDto(vpnHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<VpnHistoryDTO> findVpnHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<VpnHistoryDTO> result = new DTOResult<VpnHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			VpnHistory vpnHistory = comm.vpnHistoryService.findByCode(code);
			Validate.notNull(vpnHistory, ERROR.OBJECT_NULL);
			VpnHistoryDTO vpnHistoryDTO = BeanMapper.map(vpnHistory, VpnHistoryDTO.class);
			result.setDto(vpnHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createVpnHistory(@WebParam(name = "vpnHistoryDTO") VpnHistoryDTO vpnHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(vpnHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.vpnHistoryService.findByCode(vpnHistoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			VpnHistory vpnHistory = BeanMapper.map(vpnHistoryDTO, VpnHistory.class);
			BeanValidators.validateWithException(validator, vpnHistory);
			comm.vpnHistoryService.saveOrUpdate(vpnHistory);
			return new IdResult(vpnHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateVpnHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "vpnHistoryDTO") VpnHistoryDTO vpnHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(vpnHistoryDTO, ERROR.INPUT_NULL);
			VpnHistory vpnHistory = comm.vpnHistoryService.findVpnHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.vpnHistoryService.findByCode(vpnHistoryDTO.getCode()) == null
					|| vpnHistory.getCode().equals(vpnHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			VpnHistory vpnHistoryEntity = BeanMapper.map(vpnHistoryDTO, VpnHistory.class);
			BeanMapper.copy(vpnHistoryEntity, vpnHistory);
			vpnHistoryEntity.setIdClass(VpnHistory.class.getSimpleName());
			vpnHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.vpnHistoryService.saveOrUpdate(vpnHistoryEntity);
			return new IdResult(vpnHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteVpnHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			VpnHistory vpnHistory = comm.vpnHistoryService.findVpnHistory(id);
			VpnHistory vpnHistoryEntity = BeanMapper.map(findVpnHistory(id).getDto(), VpnHistory.class);
			BeanMapper.copy(vpnHistoryEntity, vpnHistory);
			vpnHistoryEntity.setIdClass(VpnHistory.class.getSimpleName());
			vpnHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.vpnHistoryService.saveOrUpdate(vpnHistoryEntity);
			return new IdResult(vpnHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<VpnHistoryDTO> getVpnHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<VpnHistoryDTO> result = new PaginationResult<VpnHistoryDTO>();
		try {
			result = comm.vpnHistoryService.getVpnHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<VpnHistoryDTO> getVpnHistoryList() {
		DTOListResult<VpnHistoryDTO> result = new DTOListResult<VpnHistoryDTO>();
		try {
			List<VpnHistory> companies = comm.vpnHistoryService.getCompanies();
			List<VpnHistoryDTO> list = BeanMapper.mapList(companies, VpnHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}