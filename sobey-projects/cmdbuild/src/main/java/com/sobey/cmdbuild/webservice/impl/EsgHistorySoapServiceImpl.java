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
import com.sobey.cmdbuild.entity.EsgHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EsgHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.EsgHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EsgHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.EsgHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EsgHistorySoapServiceImpl extends BasicSoapSevcie implements EsgHistorySoapService {
	@Override
	public DTOResult<EsgHistoryDTO> findEsgHistory(@WebParam(name = "id") Integer id) {
		DTOResult<EsgHistoryDTO> result = new DTOResult<EsgHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EsgHistory esgHistory = comm.esgHistoryService.findEsgHistory(id);
			Validate.notNull(esgHistory, ERROR.OBJECT_NULL);
			EsgHistoryDTO esgHistoryDTO = BeanMapper.map(esgHistory, EsgHistoryDTO.class);
			result.setDto(esgHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EsgHistoryDTO> findEsgHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<EsgHistoryDTO> result = new DTOResult<EsgHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			EsgHistory esgHistory = comm.esgHistoryService.findByCode(code);
			Validate.notNull(esgHistory, ERROR.OBJECT_NULL);
			EsgHistoryDTO esgHistoryDTO = BeanMapper.map(esgHistory, EsgHistoryDTO.class);
			result.setDto(esgHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEsgHistory(@WebParam(name = "esgHistoryDTO") EsgHistoryDTO esgHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(esgHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.esgHistoryService.findByCode(esgHistoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			EsgHistory esgHistory = BeanMapper.map(esgHistoryDTO, EsgHistory.class);
			BeanValidators.validateWithException(validator, esgHistory);
			comm.esgHistoryService.saveOrUpdate(esgHistory);
			return new IdResult(esgHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEsgHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "esgHistoryDTO") EsgHistoryDTO esgHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(esgHistoryDTO, ERROR.INPUT_NULL);
			EsgHistory esgHistory = comm.esgHistoryService.findEsgHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.esgHistoryService.findByCode(esgHistoryDTO.getCode()) == null
					|| esgHistory.getCode().equals(esgHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EsgHistory esgHistoryEntity = BeanMapper.map(esgHistoryDTO, EsgHistory.class);
			BeanMapper.copy(esgHistoryEntity, esgHistory);
			esgHistoryEntity.setIdClass(EsgHistory.class.getSimpleName());
			esgHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.esgHistoryService.saveOrUpdate(esgHistoryEntity);
			return new IdResult(esgHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEsgHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EsgHistory esgHistory = comm.esgHistoryService.findEsgHistory(id);
			EsgHistory esgHistoryEntity = BeanMapper.map(findEsgHistory(id).getDto(), EsgHistory.class);
			BeanMapper.copy(esgHistoryEntity, esgHistory);
			esgHistoryEntity.setIdClass(EsgHistory.class.getSimpleName());
			esgHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.esgHistoryService.saveOrUpdate(esgHistoryEntity);
			return new IdResult(esgHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EsgHistoryDTO> getEsgHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EsgHistoryDTO> result = new PaginationResult<EsgHistoryDTO>();
		try {
			result = comm.esgHistoryService.getEsgHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EsgHistoryDTO> getEsgHistoryList() {
		DTOListResult<EsgHistoryDTO> result = new DTOListResult<EsgHistoryDTO>();
		try {
			List<EsgHistory> companies = comm.esgHistoryService.getCompanies();
			List<EsgHistoryDTO> list = BeanMapper.mapList(companies, EsgHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}