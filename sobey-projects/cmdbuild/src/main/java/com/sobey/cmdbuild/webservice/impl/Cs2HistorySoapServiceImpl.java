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
import com.sobey.cmdbuild.entity.Cs2History;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.Cs2HistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.Cs2HistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "Cs2HistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.Cs2HistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class Cs2HistorySoapServiceImpl extends BasicSoapSevcie implements Cs2HistorySoapService {
	@Override
	public DTOResult<Cs2HistoryDTO> findCs2History(@WebParam(name = "id") Integer id) {
		DTOResult<Cs2HistoryDTO> result = new DTOResult<Cs2HistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Cs2History cs2History = comm.cs2HistoryService.findCs2History(id);
			Validate.notNull(cs2History, ERROR.OBJECT_NULL);
			Cs2HistoryDTO cs2HistoryDTO = BeanMapper.map(cs2History, Cs2HistoryDTO.class);
			result.setDto(cs2HistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Cs2HistoryDTO> findCs2HistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<Cs2HistoryDTO> result = new DTOResult<Cs2HistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Cs2History cs2History = comm.cs2HistoryService.findByCode(code);
			Validate.notNull(cs2History, ERROR.OBJECT_NULL);
			Cs2HistoryDTO cs2HistoryDTO = BeanMapper.map(cs2History, Cs2HistoryDTO.class);
			result.setDto(cs2HistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createCs2History(@WebParam(name = "cs2HistoryDTO") Cs2HistoryDTO cs2HistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(cs2HistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.cs2HistoryService.findByCode(cs2HistoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Cs2History cs2History = BeanMapper.map(cs2HistoryDTO, Cs2History.class);
			BeanValidators.validateWithException(validator, cs2History);
			comm.cs2HistoryService.saveOrUpdate(cs2History);
			return new IdResult(cs2History.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateCs2History(@WebParam(name = "id") Integer id,
			@WebParam(name = "cs2HistoryDTO") Cs2HistoryDTO cs2HistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(cs2HistoryDTO, ERROR.INPUT_NULL);
			Cs2History cs2History = comm.cs2HistoryService.findCs2History(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.cs2HistoryService.findByCode(cs2HistoryDTO.getCode()) == null
					|| cs2History.getCode().equals(cs2HistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Cs2History cs2HistoryEntity = BeanMapper.map(cs2HistoryDTO, Cs2History.class);
			BeanMapper.copy(cs2HistoryEntity, cs2History);
			cs2HistoryEntity.setIdClass(Cs2History.class.getSimpleName());
			cs2HistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.cs2HistoryService.saveOrUpdate(cs2HistoryEntity);
			return new IdResult(cs2History.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteCs2History(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Cs2History cs2History = comm.cs2HistoryService.findCs2History(id);
			Cs2History cs2HistoryEntity = BeanMapper.map(findCs2History(id).getDto(), Cs2History.class);
			BeanMapper.copy(cs2HistoryEntity, cs2History);
			cs2HistoryEntity.setIdClass(Cs2History.class.getSimpleName());
			cs2HistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.cs2HistoryService.saveOrUpdate(cs2HistoryEntity);
			return new IdResult(cs2History.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<Cs2HistoryDTO> getCs2HistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<Cs2HistoryDTO> result = new PaginationResult<Cs2HistoryDTO>();
		try {
			result = comm.cs2HistoryService.getCs2HistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<Cs2HistoryDTO> getCs2HistoryList() {
		DTOListResult<Cs2HistoryDTO> result = new DTOListResult<Cs2HistoryDTO>();
		try {
			List<Cs2History> companies = comm.cs2HistoryService.getCompanies();
			List<Cs2HistoryDTO> list = BeanMapper.mapList(companies, Cs2HistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}