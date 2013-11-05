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
import com.sobey.cmdbuild.entity.TagHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.TagHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.TagHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "TagHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.TagHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class TagHistorySoapServiceImpl extends BasicSoapSevcie implements TagHistorySoapService {
	@Override
	public DTOResult<TagHistoryDTO> findTagHistory(@WebParam(name = "id") Integer id) {
		DTOResult<TagHistoryDTO> result = new DTOResult<TagHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			TagHistory tagHistory = comm.tagHistoryService.findTagHistory(id);
			Validate.notNull(tagHistory, ERROR.OBJECT_NULL);
			TagHistoryDTO tagHistoryDTO = BeanMapper.map(tagHistory, TagHistoryDTO.class);
			result.setDto(tagHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TagHistoryDTO> findTagHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<TagHistoryDTO> result = new DTOResult<TagHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			TagHistory tagHistory = comm.tagHistoryService.findByCode(code);
			Validate.notNull(tagHistory, ERROR.OBJECT_NULL);
			TagHistoryDTO tagHistoryDTO = BeanMapper.map(tagHistory, TagHistoryDTO.class);
			result.setDto(tagHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createTagHistory(@WebParam(name = "tagHistoryDTO") TagHistoryDTO tagHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tagHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.tagHistoryService.findByCode(tagHistoryDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			TagHistory tagHistory = BeanMapper.map(tagHistoryDTO, TagHistory.class);
			BeanValidators.validateWithException(validator, tagHistory);
			comm.tagHistoryService.saveOrUpdate(tagHistory);
			return new IdResult(tagHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateTagHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "tagHistoryDTO") TagHistoryDTO tagHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tagHistoryDTO, ERROR.INPUT_NULL);
			TagHistory tagHistory = comm.tagHistoryService.findTagHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.tagHistoryService.findByCode(tagHistoryDTO.getCode()) == null
					|| tagHistory.getCode().equals(tagHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			TagHistory tagHistoryEntity = BeanMapper.map(tagHistoryDTO, TagHistory.class);
			BeanMapper.copy(tagHistoryEntity, tagHistory);
			tagHistoryEntity.setIdClass(TagHistory.class.getSimpleName());
			tagHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.tagHistoryService.saveOrUpdate(tagHistoryEntity);
			return new IdResult(tagHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteTagHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			TagHistory tagHistory = comm.tagHistoryService.findTagHistory(id);
			TagHistory tagHistoryEntity = BeanMapper.map(findTagHistory(id).getDto(), TagHistory.class);
			BeanMapper.copy(tagHistoryEntity, tagHistory);
			tagHistoryEntity.setIdClass(TagHistory.class.getSimpleName());
			tagHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.tagHistoryService.saveOrUpdate(tagHistoryEntity);
			return new IdResult(tagHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<TagHistoryDTO> getTagHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<TagHistoryDTO> result = new PaginationResult<TagHistoryDTO>();
		try {
			result = comm.tagHistoryService.getTagHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<TagHistoryDTO> getTagHistoryList() {
		DTOListResult<TagHistoryDTO> result = new DTOListResult<TagHistoryDTO>();
		try {
			List<TagHistory> companies = comm.tagHistoryService.getCompanies();
			List<TagHistoryDTO> list = BeanMapper.mapList(companies, TagHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}