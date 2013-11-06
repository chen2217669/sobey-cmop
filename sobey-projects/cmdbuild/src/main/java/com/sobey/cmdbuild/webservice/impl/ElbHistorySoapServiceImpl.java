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
import com.sobey.cmdbuild.entity.ElbHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.ElbHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.ElbHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "ElbHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.ElbHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class ElbHistorySoapServiceImpl extends BasicSoapSevcie implements ElbHistorySoapService {
	@Override
	public DTOResult<ElbHistoryDTO> findElbHistory(@WebParam(name = "id") Integer id) {
		DTOResult<ElbHistoryDTO> result = new DTOResult<ElbHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ElbHistory elbHistory = comm.elbHistoryService.findElbHistory(id);
			Validate.notNull(elbHistory, ERROR.OBJECT_NULL);
			ElbHistoryDTO elbHistoryDTO = BeanMapper.map(elbHistory, ElbHistoryDTO.class);
			result.setDto(elbHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ElbHistoryDTO> findElbHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<ElbHistoryDTO> result = new DTOResult<ElbHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			ElbHistory elbHistory = comm.elbHistoryService.findByCode(code);
			Validate.notNull(elbHistory, ERROR.OBJECT_NULL);
			ElbHistoryDTO elbHistoryDTO = BeanMapper.map(elbHistory, ElbHistoryDTO.class);
			result.setDto(elbHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createElbHistory(@WebParam(name = "elbHistoryDTO") ElbHistoryDTO elbHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(elbHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.elbHistoryService.findByCode(elbHistoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			ElbHistory elbHistory = BeanMapper.map(elbHistoryDTO, ElbHistory.class);
			BeanValidators.validateWithException(validator, elbHistory);
			comm.elbHistoryService.saveOrUpdate(elbHistory);
			return new IdResult(elbHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateElbHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "elbHistoryDTO") ElbHistoryDTO elbHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(elbHistoryDTO, ERROR.INPUT_NULL);
			ElbHistory elbHistory = comm.elbHistoryService.findElbHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.elbHistoryService.findByCode(elbHistoryDTO.getCode()) == null
					|| elbHistory.getCode().equals(elbHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			ElbHistory elbHistoryEntity = BeanMapper.map(elbHistoryDTO, ElbHistory.class);
			BeanMapper.copy(elbHistoryEntity, elbHistory);
			elbHistoryEntity.setIdClass(ElbHistory.class.getSimpleName());
			elbHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.elbHistoryService.saveOrUpdate(elbHistoryEntity);
			return new IdResult(elbHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteElbHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			ElbHistory elbHistory = comm.elbHistoryService.findElbHistory(id);
			ElbHistory elbHistoryEntity = BeanMapper.map(findElbHistory(id).getDto(), ElbHistory.class);
			BeanMapper.copy(elbHistoryEntity, elbHistory);
			elbHistoryEntity.setIdClass(ElbHistory.class.getSimpleName());
			elbHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.elbHistoryService.saveOrUpdate(elbHistoryEntity);
			return new IdResult(elbHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ElbHistoryDTO> getElbHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ElbHistoryDTO> result = new PaginationResult<ElbHistoryDTO>();
		try {
			result = comm.elbHistoryService.getElbHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ElbHistoryDTO> getElbHistoryList() {
		DTOListResult<ElbHistoryDTO> result = new DTOListResult<ElbHistoryDTO>();
		try {
			List<ElbHistory> companies = comm.elbHistoryService.getCompanies();
			List<ElbHistoryDTO> list = BeanMapper.mapList(companies, ElbHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}