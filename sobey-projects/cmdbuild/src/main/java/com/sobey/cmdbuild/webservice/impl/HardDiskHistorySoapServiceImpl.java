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
import com.sobey.cmdbuild.entity.HardDiskHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.HardDiskHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "HardDiskHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.HardDiskHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class HardDiskHistorySoapServiceImpl extends BasicSoapSevcie implements HardDiskHistorySoapService {
	@Override
	public DTOResult<HardDiskHistoryDTO> findHardDiskHistory(@WebParam(name = "id") Integer id) {
		DTOResult<HardDiskHistoryDTO> result = new DTOResult<HardDiskHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			HardDiskHistory hardDiskHistory = comm.hardDiskHistoryService.findHardDiskHistory(id);
			Validate.notNull(hardDiskHistory, ERROR.OBJECT_NULL);
			HardDiskHistoryDTO hardDiskHistoryDTO = BeanMapper.map(hardDiskHistory, HardDiskHistoryDTO.class);
			result.setDto(hardDiskHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<HardDiskHistoryDTO> findHardDiskHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<HardDiskHistoryDTO> result = new DTOResult<HardDiskHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			HardDiskHistory hardDiskHistory = comm.hardDiskHistoryService.findByCode(code);
			Validate.notNull(hardDiskHistory, ERROR.OBJECT_NULL);
			HardDiskHistoryDTO hardDiskHistoryDTO = BeanMapper.map(hardDiskHistory, HardDiskHistoryDTO.class);
			result.setDto(hardDiskHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createHardDiskHistory(@WebParam(name = "hardDiskHistoryDTO") HardDiskHistoryDTO hardDiskHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(hardDiskHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.hardDiskHistoryService.findByCode(hardDiskHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			HardDiskHistory hardDiskHistory = BeanMapper.map(hardDiskHistoryDTO, HardDiskHistory.class);
			BeanValidators.validateWithException(validator, hardDiskHistory);
			comm.hardDiskHistoryService.saveOrUpdate(hardDiskHistory);
			return new IdResult(hardDiskHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateHardDiskHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "hardDiskHistoryDTO") HardDiskHistoryDTO hardDiskHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(hardDiskHistoryDTO, ERROR.INPUT_NULL);
			HardDiskHistory hardDiskHistory = comm.hardDiskHistoryService.findHardDiskHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.hardDiskHistoryService.findByCode(hardDiskHistoryDTO.getCode()) == null
					|| hardDiskHistory.getCode().equals(hardDiskHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			HardDiskHistory hardDiskHistoryEntity = BeanMapper.map(hardDiskHistoryDTO, HardDiskHistory.class);
			BeanMapper.copy(hardDiskHistoryEntity, hardDiskHistory);
			hardDiskHistoryEntity.setIdClass(HardDiskHistory.class.getSimpleName());
			hardDiskHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.hardDiskHistoryService.saveOrUpdate(hardDiskHistoryEntity);
			return new IdResult(hardDiskHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteHardDiskHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			HardDiskHistory hardDiskHistory = comm.hardDiskHistoryService.findHardDiskHistory(id);
			HardDiskHistory hardDiskHistoryEntity = BeanMapper.map(findHardDiskHistory(id).getDto(),
					HardDiskHistory.class);
			BeanMapper.copy(hardDiskHistoryEntity, hardDiskHistory);
			hardDiskHistoryEntity.setIdClass(HardDiskHistory.class.getSimpleName());
			hardDiskHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.hardDiskHistoryService.saveOrUpdate(hardDiskHistoryEntity);
			return new IdResult(hardDiskHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<HardDiskHistoryDTO> getHardDiskHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<HardDiskHistoryDTO> result = new PaginationResult<HardDiskHistoryDTO>();
		try {
			result = comm.hardDiskHistoryService.getHardDiskHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<HardDiskHistoryDTO> getHardDiskHistoryList() {
		DTOListResult<HardDiskHistoryDTO> result = new DTOListResult<HardDiskHistoryDTO>();
		try {
			List<HardDiskHistory> companies = comm.hardDiskHistoryService.getCompanies();
			List<HardDiskHistoryDTO> list = BeanMapper.mapList(companies, HardDiskHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}