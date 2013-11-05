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
import com.sobey.cmdbuild.entity.IpaddressHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.IpaddressHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "IpaddressHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.IpaddressHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class IpaddressHistorySoapServiceImpl extends BasicSoapSevcie implements IpaddressHistorySoapService {
	@Override
	public DTOResult<IpaddressHistoryDTO> findIpaddressHistory(@WebParam(name = "id") Integer id) {
		DTOResult<IpaddressHistoryDTO> result = new DTOResult<IpaddressHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			IpaddressHistory ipaddressHistory = comm.ipaddressHistoryService.findIpaddressHistory(id);
			Validate.notNull(ipaddressHistory, ERROR.OBJECT_NULL);
			IpaddressHistoryDTO ipaddressHistoryDTO = BeanMapper.map(ipaddressHistory, IpaddressHistoryDTO.class);
			result.setDto(ipaddressHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<IpaddressHistoryDTO> findIpaddressHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<IpaddressHistoryDTO> result = new DTOResult<IpaddressHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			IpaddressHistory ipaddressHistory = comm.ipaddressHistoryService.findByCode(code);
			Validate.notNull(ipaddressHistory, ERROR.OBJECT_NULL);
			IpaddressHistoryDTO ipaddressHistoryDTO = BeanMapper.map(ipaddressHistory, IpaddressHistoryDTO.class);
			result.setDto(ipaddressHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createIpaddressHistory(
			@WebParam(name = "ipaddressHistoryDTO") IpaddressHistoryDTO ipaddressHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ipaddressHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ipaddressHistoryService.findByCode(ipaddressHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			IpaddressHistory ipaddressHistory = BeanMapper.map(ipaddressHistoryDTO, IpaddressHistory.class);
			BeanValidators.validateWithException(validator, ipaddressHistory);
			comm.ipaddressHistoryService.saveOrUpdate(ipaddressHistory);
			return new IdResult(ipaddressHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateIpaddressHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "ipaddressHistoryDTO") IpaddressHistoryDTO ipaddressHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ipaddressHistoryDTO, ERROR.INPUT_NULL);
			IpaddressHistory ipaddressHistory = comm.ipaddressHistoryService.findIpaddressHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ipaddressHistoryService.findByCode(ipaddressHistoryDTO.getCode()) == null
					|| ipaddressHistory.getCode().equals(ipaddressHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			IpaddressHistory ipaddressHistoryEntity = BeanMapper.map(ipaddressHistoryDTO, IpaddressHistory.class);
			BeanMapper.copy(ipaddressHistoryEntity, ipaddressHistory);
			ipaddressHistoryEntity.setIdClass(IpaddressHistory.class.getSimpleName());
			ipaddressHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.ipaddressHistoryService.saveOrUpdate(ipaddressHistoryEntity);
			return new IdResult(ipaddressHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteIpaddressHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			IpaddressHistory ipaddressHistory = comm.ipaddressHistoryService.findIpaddressHistory(id);
			IpaddressHistory ipaddressHistoryEntity = BeanMapper.map(findIpaddressHistory(id).getDto(),
					IpaddressHistory.class);
			BeanMapper.copy(ipaddressHistoryEntity, ipaddressHistory);
			ipaddressHistoryEntity.setIdClass(IpaddressHistory.class.getSimpleName());
			ipaddressHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.ipaddressHistoryService.saveOrUpdate(ipaddressHistoryEntity);
			return new IdResult(ipaddressHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<IpaddressHistoryDTO> getIpaddressHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<IpaddressHistoryDTO> result = new PaginationResult<IpaddressHistoryDTO>();
		try {
			result = comm.ipaddressHistoryService.getIpaddressHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<IpaddressHistoryDTO> getIpaddressHistoryList() {
		DTOListResult<IpaddressHistoryDTO> result = new DTOListResult<IpaddressHistoryDTO>();
		try {
			List<IpaddressHistory> companies = comm.ipaddressHistoryService.getCompanies();
			List<IpaddressHistoryDTO> list = BeanMapper.mapList(companies, IpaddressHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}