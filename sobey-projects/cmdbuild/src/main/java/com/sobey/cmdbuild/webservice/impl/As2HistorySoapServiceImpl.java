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
import com.sobey.cmdbuild.entity.As2History;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.As2HistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.As2HistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "As2HistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.As2HistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class As2HistorySoapServiceImpl extends BasicSoapSevcie implements As2HistorySoapService {
	@Override
	public DTOResult<As2HistoryDTO> findAs2History(@WebParam(name = "id") Integer id) {
		DTOResult<As2HistoryDTO> result = new DTOResult<As2HistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			As2History as2History = comm.as2HistoryService.findAs2History(id);
			Validate.notNull(as2History, ERROR.OBJECT_NULL);
			As2HistoryDTO as2HistoryDTO = BeanMapper.map(as2History, As2HistoryDTO.class);
			result.setDto(as2HistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<As2HistoryDTO> findAs2HistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<As2HistoryDTO> result = new DTOResult<As2HistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			As2History as2History = comm.as2HistoryService.findByCode(code);
			Validate.notNull(as2History, ERROR.OBJECT_NULL);
			As2HistoryDTO as2HistoryDTO = BeanMapper.map(as2History, As2HistoryDTO.class);
			result.setDto(as2HistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createAs2History(@WebParam(name = "as2HistoryDTO") As2HistoryDTO as2HistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(as2HistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.as2HistoryService.findByCode(as2HistoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			As2History as2History = BeanMapper.map(as2HistoryDTO, As2History.class);
			BeanValidators.validateWithException(validator, as2History);
			comm.as2HistoryService.saveOrUpdate(as2History);
			return new IdResult(as2History.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateAs2History(@WebParam(name = "id") Integer id,
			@WebParam(name = "as2HistoryDTO") As2HistoryDTO as2HistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(as2HistoryDTO, ERROR.INPUT_NULL);
			As2History as2History = comm.as2HistoryService.findAs2History(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.as2HistoryService.findByCode(as2HistoryDTO.getCode()) == null
					|| as2History.getCode().equals(as2HistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			As2History as2HistoryEntity = BeanMapper.map(as2HistoryDTO, As2History.class);
			BeanMapper.copy(as2HistoryEntity, as2History);
			as2HistoryEntity.setIdClass(As2History.class.getSimpleName());
			as2HistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.as2HistoryService.saveOrUpdate(as2HistoryEntity);
			return new IdResult(as2History.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteAs2History(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			As2History as2History = comm.as2HistoryService.findAs2History(id);
			As2History as2HistoryEntity = BeanMapper.map(findAs2History(id).getDto(), As2History.class);
			BeanMapper.copy(as2HistoryEntity, as2History);
			as2HistoryEntity.setIdClass(As2History.class.getSimpleName());
			as2HistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.as2HistoryService.saveOrUpdate(as2HistoryEntity);
			return new IdResult(as2History.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<As2HistoryDTO> getAs2HistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<As2HistoryDTO> result = new PaginationResult<As2HistoryDTO>();
		try {
			result = comm.as2HistoryService.getAs2HistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<As2HistoryDTO> getAs2HistoryList() {
		DTOListResult<As2HistoryDTO> result = new DTOListResult<As2HistoryDTO>();
		try {
			List<As2History> companies = comm.as2HistoryService.getCompanies();
			List<As2HistoryDTO> list = BeanMapper.mapList(companies, As2HistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}