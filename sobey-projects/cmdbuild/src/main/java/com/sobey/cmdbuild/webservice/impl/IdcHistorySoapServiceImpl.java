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
import com.sobey.cmdbuild.entity.IdcHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.IdcHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.IdcHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "IdcHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.IdcHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class IdcHistorySoapServiceImpl extends BasicSoapSevcie implements IdcHistorySoapService {
	@Override
	public DTOResult<IdcHistoryDTO> findIdcHistory(@WebParam(name = "id") Integer id) {
		DTOResult<IdcHistoryDTO> result = new DTOResult<IdcHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			IdcHistory idcHistory = comm.idcHistoryService.findIdcHistory(id);
			Validate.notNull(idcHistory, ERROR.OBJECT_NULL);
			IdcHistoryDTO idcHistoryDTO = BeanMapper.map(idcHistory, IdcHistoryDTO.class);
			result.setDto(idcHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<IdcHistoryDTO> findIdcHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<IdcHistoryDTO> result = new DTOResult<IdcHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			IdcHistory idcHistory = comm.idcHistoryService.findByCode(code);
			Validate.notNull(idcHistory, ERROR.OBJECT_NULL);
			IdcHistoryDTO idcHistoryDTO = BeanMapper.map(idcHistory, IdcHistoryDTO.class);
			result.setDto(idcHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createIdcHistory(@WebParam(name = "idcHistoryDTO") IdcHistoryDTO idcHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(idcHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.idcHistoryService.findByCode(idcHistoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			IdcHistory idcHistory = BeanMapper.map(idcHistoryDTO, IdcHistory.class);
			BeanValidators.validateWithException(validator, idcHistory);
			comm.idcHistoryService.saveOrUpdate(idcHistory);
			return new IdResult(idcHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateIdcHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "idcHistoryDTO") IdcHistoryDTO idcHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(idcHistoryDTO, ERROR.INPUT_NULL);
			IdcHistory idcHistory = comm.idcHistoryService.findIdcHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.idcHistoryService.findByCode(idcHistoryDTO.getCode()) == null
					|| idcHistory.getCode().equals(idcHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			IdcHistory idcHistoryEntity = BeanMapper.map(idcHistoryDTO, IdcHistory.class);
			BeanMapper.copy(idcHistoryEntity, idcHistory);
			idcHistoryEntity.setIdClass(IdcHistory.class.getSimpleName());
			idcHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.idcHistoryService.saveOrUpdate(idcHistoryEntity);
			return new IdResult(idcHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteIdcHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			IdcHistory idcHistory = comm.idcHistoryService.findIdcHistory(id);
			IdcHistory idcHistoryEntity = BeanMapper.map(findIdcHistory(id).getDto(), IdcHistory.class);
			BeanMapper.copy(idcHistoryEntity, idcHistory);
			idcHistoryEntity.setIdClass(IdcHistory.class.getSimpleName());
			idcHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.idcHistoryService.saveOrUpdate(idcHistoryEntity);
			return new IdResult(idcHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<IdcHistoryDTO> getIdcHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<IdcHistoryDTO> result = new PaginationResult<IdcHistoryDTO>();
		try {
			result = comm.idcHistoryService.getIdcHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<IdcHistoryDTO> getIdcHistoryList() {
		DTOListResult<IdcHistoryDTO> result = new DTOListResult<IdcHistoryDTO>();
		try {
			List<IdcHistory> companies = comm.idcHistoryService.getCompanies();
			List<IdcHistoryDTO> list = BeanMapper.mapList(companies, IdcHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}