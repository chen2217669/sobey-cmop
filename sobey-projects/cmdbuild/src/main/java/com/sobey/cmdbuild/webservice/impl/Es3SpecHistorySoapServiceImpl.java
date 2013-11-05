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
import com.sobey.cmdbuild.entity.Es3SpecHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.Es3SpecHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "Es3SpecHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.Es3SpecHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class Es3SpecHistorySoapServiceImpl extends BasicSoapSevcie implements Es3SpecHistorySoapService {
	@Override
	public DTOResult<Es3SpecHistoryDTO> findEs3SpecHistory(@WebParam(name = "id") Integer id) {
		DTOResult<Es3SpecHistoryDTO> result = new DTOResult<Es3SpecHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Es3SpecHistory es3SpecHistory = comm.es3SpecHistoryService.findEs3SpecHistory(id);
			Validate.notNull(es3SpecHistory, ERROR.OBJECT_NULL);
			Es3SpecHistoryDTO es3SpecHistoryDTO = BeanMapper.map(es3SpecHistory, Es3SpecHistoryDTO.class);
			result.setDto(es3SpecHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3SpecHistoryDTO> findEs3SpecHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<Es3SpecHistoryDTO> result = new DTOResult<Es3SpecHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Es3SpecHistory es3SpecHistory = comm.es3SpecHistoryService.findByCode(code);
			Validate.notNull(es3SpecHistory, ERROR.OBJECT_NULL);
			Es3SpecHistoryDTO es3SpecHistoryDTO = BeanMapper.map(es3SpecHistory, Es3SpecHistoryDTO.class);
			result.setDto(es3SpecHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEs3SpecHistory(@WebParam(name = "es3SpecHistoryDTO") Es3SpecHistoryDTO es3SpecHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(es3SpecHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.es3SpecHistoryService.findByCode(es3SpecHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			Es3SpecHistory es3SpecHistory = BeanMapper.map(es3SpecHistoryDTO, Es3SpecHistory.class);
			BeanValidators.validateWithException(validator, es3SpecHistory);
			comm.es3SpecHistoryService.saveOrUpdate(es3SpecHistory);
			return new IdResult(es3SpecHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEs3SpecHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "es3SpecHistoryDTO") Es3SpecHistoryDTO es3SpecHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(es3SpecHistoryDTO, ERROR.INPUT_NULL);
			Es3SpecHistory es3SpecHistory = comm.es3SpecHistoryService.findEs3SpecHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.es3SpecHistoryService.findByCode(es3SpecHistoryDTO.getCode()) == null
					|| es3SpecHistory.getCode().equals(es3SpecHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Es3SpecHistory es3SpecHistoryEntity = BeanMapper.map(es3SpecHistoryDTO, Es3SpecHistory.class);
			BeanMapper.copy(es3SpecHistoryEntity, es3SpecHistory);
			es3SpecHistoryEntity.setIdClass(Es3SpecHistory.class.getSimpleName());
			es3SpecHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.es3SpecHistoryService.saveOrUpdate(es3SpecHistoryEntity);
			return new IdResult(es3SpecHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEs3SpecHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Es3SpecHistory es3SpecHistory = comm.es3SpecHistoryService.findEs3SpecHistory(id);
			Es3SpecHistory es3SpecHistoryEntity = BeanMapper.map(findEs3SpecHistory(id).getDto(), Es3SpecHistory.class);
			BeanMapper.copy(es3SpecHistoryEntity, es3SpecHistory);
			es3SpecHistoryEntity.setIdClass(Es3SpecHistory.class.getSimpleName());
			es3SpecHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.es3SpecHistoryService.saveOrUpdate(es3SpecHistoryEntity);
			return new IdResult(es3SpecHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<Es3SpecHistoryDTO> getEs3SpecHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<Es3SpecHistoryDTO> result = new PaginationResult<Es3SpecHistoryDTO>();
		try {
			result = comm.es3SpecHistoryService.getEs3SpecHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<Es3SpecHistoryDTO> getEs3SpecHistoryList() {
		DTOListResult<Es3SpecHistoryDTO> result = new DTOListResult<Es3SpecHistoryDTO>();
		try {
			List<Es3SpecHistory> companies = comm.es3SpecHistoryService.getCompanies();
			List<Es3SpecHistoryDTO> list = BeanMapper.mapList(companies, Es3SpecHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}