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
import com.sobey.cmdbuild.entity.NicHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.NicHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.NicHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "NicHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.NicHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NicHistorySoapServiceImpl extends BasicSoapSevcie implements NicHistorySoapService {
	@Override
	public DTOResult<NicHistoryDTO> findNicHistory(@WebParam(name = "id") Integer id) {
		DTOResult<NicHistoryDTO> result = new DTOResult<NicHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NicHistory nicHistory = comm.nicHistoryService.findNicHistory(id);
			Validate.notNull(nicHistory, ERROR.OBJECT_NULL);
			NicHistoryDTO nicHistoryDTO = BeanMapper.map(nicHistory, NicHistoryDTO.class);
			result.setDto(nicHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NicHistoryDTO> findNicHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<NicHistoryDTO> result = new DTOResult<NicHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			NicHistory nicHistory = comm.nicHistoryService.findByCode(code);
			Validate.notNull(nicHistory, ERROR.OBJECT_NULL);
			NicHistoryDTO nicHistoryDTO = BeanMapper.map(nicHistory, NicHistoryDTO.class);
			result.setDto(nicHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createNicHistory(@WebParam(name = "nicHistoryDTO") NicHistoryDTO nicHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(nicHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.nicHistoryService.findByCode(nicHistoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			NicHistory nicHistory = BeanMapper.map(nicHistoryDTO, NicHistory.class);
			BeanValidators.validateWithException(validator, nicHistory);
			comm.nicHistoryService.saveOrUpdate(nicHistory);
			return new IdResult(nicHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNicHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "nicHistoryDTO") NicHistoryDTO nicHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(nicHistoryDTO, ERROR.INPUT_NULL);
			NicHistory nicHistory = comm.nicHistoryService.findNicHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.nicHistoryService.findByCode(nicHistoryDTO.getCode()) == null
					|| nicHistory.getCode().equals(nicHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			NicHistory nicHistoryEntity = BeanMapper.map(nicHistoryDTO, NicHistory.class);
			BeanMapper.copy(nicHistoryEntity, nicHistory);
			nicHistoryEntity.setIdClass(NicHistory.class.getSimpleName());
			nicHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.nicHistoryService.saveOrUpdate(nicHistoryEntity);
			return new IdResult(nicHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNicHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NicHistory nicHistory = comm.nicHistoryService.findNicHistory(id);
			NicHistory nicHistoryEntity = BeanMapper.map(findNicHistory(id).getDto(), NicHistory.class);
			BeanMapper.copy(nicHistoryEntity, nicHistory);
			nicHistoryEntity.setIdClass(NicHistory.class.getSimpleName());
			nicHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.nicHistoryService.saveOrUpdate(nicHistoryEntity);
			return new IdResult(nicHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NicHistoryDTO> getNicHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<NicHistoryDTO> result = new PaginationResult<NicHistoryDTO>();
		try {
			result = comm.nicHistoryService.getNicHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NicHistoryDTO> getNicHistoryList() {
		DTOListResult<NicHistoryDTO> result = new DTOListResult<NicHistoryDTO>();
		try {
			List<NicHistory> companies = comm.nicHistoryService.getCompanies();
			List<NicHistoryDTO> list = BeanMapper.mapList(companies, NicHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}