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
import com.sobey.cmdbuild.entity.FimasBox;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.FimasBoxSoapService;
import com.sobey.cmdbuild.webservice.response.dto.FimasBoxDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "FimasBoxService", endpointInterface = "com.sobey.cmdbuild.webservice.FimasBoxSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FimasBoxSoapServiceImpl extends BasicSoapSevcie implements FimasBoxSoapService {
	@Override
	public DTOResult<FimasBoxDTO> findFimasBox(@WebParam(name = "id") Integer id) {
		DTOResult<FimasBoxDTO> result = new DTOResult<FimasBoxDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FimasBox fimasBox = comm.fimasBoxService.findFimasBox(id);
			Validate.notNull(fimasBox, ERROR.OBJECT_NULL);
			FimasBoxDTO fimasBoxDTO = BeanMapper.map(fimasBox, FimasBoxDTO.class);
			result.setDto(fimasBoxDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasBoxDTO> findFimasBoxByCode(@WebParam(name = "code") String code) {
		DTOResult<FimasBoxDTO> result = new DTOResult<FimasBoxDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			FimasBox fimasBox = comm.fimasBoxService.findByCode(code);
			Validate.notNull(fimasBox, ERROR.OBJECT_NULL);
			FimasBoxDTO fimasBoxDTO = BeanMapper.map(fimasBox, FimasBoxDTO.class);
			result.setDto(fimasBoxDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createFimasBox(@WebParam(name = "fimasBoxDTO") FimasBoxDTO fimasBoxDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasBoxDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasBoxService.findByCode(fimasBoxDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			FimasBox fimasBox = BeanMapper.map(fimasBoxDTO, FimasBox.class);
			BeanValidators.validateWithException(validator, fimasBox);
			comm.fimasBoxService.saveOrUpdate(fimasBox);
			return new IdResult(fimasBox.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimasBox(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasBoxDTO") FimasBoxDTO fimasBoxDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(fimasBoxDTO, ERROR.INPUT_NULL);
			FimasBox fimasBox = comm.fimasBoxService.findFimasBox(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasBoxService.findByCode(fimasBoxDTO.getCode()) == null
					|| fimasBox.getCode().equals(fimasBoxDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			FimasBox fimasBoxEntity = BeanMapper.map(fimasBoxDTO, FimasBox.class);
			BeanMapper.copy(fimasBoxEntity, fimasBox);
			fimasBoxEntity.setIdClass(FimasBox.class.getSimpleName());
			fimasBoxEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.fimasBoxService.saveOrUpdate(fimasBoxEntity);
			return new IdResult(fimasBox.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimasBox(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			FimasBox fimasBox = comm.fimasBoxService.findFimasBox(id);
			FimasBox fimasBoxEntity = BeanMapper.map(findFimasBox(id).getDto(), FimasBox.class);
			BeanMapper.copy(fimasBoxEntity, fimasBox);
			fimasBoxEntity.setIdClass(FimasBox.class.getSimpleName());
			fimasBoxEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.fimasBoxService.saveOrUpdate(fimasBoxEntity);
			return new IdResult(fimasBox.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasBoxDTO> getFimasBoxPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FimasBoxDTO> result = new PaginationResult<FimasBoxDTO>();
		try {
			result = comm.fimasBoxService.getFimasBoxDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasBoxDTO> getFimasBoxList() {
		DTOListResult<FimasBoxDTO> result = new DTOListResult<FimasBoxDTO>();
		try {
			List<FimasBox> companies = comm.fimasBoxService.getCompanies();
			List<FimasBoxDTO> list = BeanMapper.mapList(companies, FimasBoxDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}