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
import com.sobey.cmdbuild.entity.EipSpecHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EipSpecHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EipSpecHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.EipSpecHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EipSpecHistorySoapServiceImpl extends BasicSoapSevcie implements EipSpecHistorySoapService {
	@Override
	public DTOResult<EipSpecHistoryDTO> findEipSpecHistory(@WebParam(name = "id") Integer id) {
		DTOResult<EipSpecHistoryDTO> result = new DTOResult<EipSpecHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipSpecHistory eipSpecHistory = comm.eipSpecHistoryService.findEipSpecHistory(id);
			Validate.notNull(eipSpecHistory, ERROR.OBJECT_NULL);
			EipSpecHistoryDTO eipSpecHistoryDTO = BeanMapper.map(eipSpecHistory, EipSpecHistoryDTO.class);
			result.setDto(eipSpecHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipSpecHistoryDTO> findEipSpecHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<EipSpecHistoryDTO> result = new DTOResult<EipSpecHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			EipSpecHistory eipSpecHistory = comm.eipSpecHistoryService.findByCode(code);
			Validate.notNull(eipSpecHistory, ERROR.OBJECT_NULL);
			EipSpecHistoryDTO eipSpecHistoryDTO = BeanMapper.map(eipSpecHistory, EipSpecHistoryDTO.class);
			result.setDto(eipSpecHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEipSpecHistory(@WebParam(name = "eipSpecHistoryDTO") EipSpecHistoryDTO eipSpecHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipSpecHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipSpecHistoryService.findByCode(eipSpecHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			EipSpecHistory eipSpecHistory = BeanMapper.map(eipSpecHistoryDTO, EipSpecHistory.class);
			BeanValidators.validateWithException(validator, eipSpecHistory);
			comm.eipSpecHistoryService.saveOrUpdate(eipSpecHistory);
			return new IdResult(eipSpecHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEipSpecHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipSpecHistoryDTO") EipSpecHistoryDTO eipSpecHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipSpecHistoryDTO, ERROR.INPUT_NULL);
			EipSpecHistory eipSpecHistory = comm.eipSpecHistoryService.findEipSpecHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.eipSpecHistoryService.findByCode(eipSpecHistoryDTO.getCode()) == null
					|| eipSpecHistory.getCode().equals(eipSpecHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EipSpecHistory eipSpecHistoryEntity = BeanMapper.map(eipSpecHistoryDTO, EipSpecHistory.class);
			BeanMapper.copy(eipSpecHistoryEntity, eipSpecHistory);
			eipSpecHistoryEntity.setIdClass(EipSpecHistory.class.getSimpleName());
			eipSpecHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.eipSpecHistoryService.saveOrUpdate(eipSpecHistoryEntity);
			return new IdResult(eipSpecHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEipSpecHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipSpecHistory eipSpecHistory = comm.eipSpecHistoryService.findEipSpecHistory(id);
			EipSpecHistory eipSpecHistoryEntity = BeanMapper.map(findEipSpecHistory(id).getDto(), EipSpecHistory.class);
			BeanMapper.copy(eipSpecHistoryEntity, eipSpecHistory);
			eipSpecHistoryEntity.setIdClass(EipSpecHistory.class.getSimpleName());
			eipSpecHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.eipSpecHistoryService.saveOrUpdate(eipSpecHistoryEntity);
			return new IdResult(eipSpecHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipSpecHistoryDTO> getEipSpecHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EipSpecHistoryDTO> result = new PaginationResult<EipSpecHistoryDTO>();
		try {
			result = comm.eipSpecHistoryService.getEipSpecHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipSpecHistoryDTO> getEipSpecHistoryList() {
		DTOListResult<EipSpecHistoryDTO> result = new DTOListResult<EipSpecHistoryDTO>();
		try {
			List<EipSpecHistory> companies = comm.eipSpecHistoryService.getCompanies();
			List<EipSpecHistoryDTO> list = BeanMapper.mapList(companies, EipSpecHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}