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
import com.sobey.cmdbuild.entity.HardDisk;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.HardDiskSoapService;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "HardDiskService", endpointInterface = "com.sobey.cmdbuild.webservice.HardDiskSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class HardDiskSoapServiceImpl extends BasicSoapSevcie implements HardDiskSoapService {
	@Override
	public DTOResult<HardDiskDTO> findHardDisk(@WebParam(name = "id") Integer id) {
		DTOResult<HardDiskDTO> result = new DTOResult<HardDiskDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			HardDisk hardDisk = comm.hardDiskService.findHardDisk(id);
			Validate.notNull(hardDisk, ERROR.OBJECT_NULL);
			HardDiskDTO hardDiskDTO = BeanMapper.map(hardDisk, HardDiskDTO.class);
			result.setDto(hardDiskDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<HardDiskDTO> findHardDiskByCode(@WebParam(name = "code") String code) {
		DTOResult<HardDiskDTO> result = new DTOResult<HardDiskDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			HardDisk hardDisk = comm.hardDiskService.findByCode(code);
			Validate.notNull(hardDisk, ERROR.OBJECT_NULL);
			HardDiskDTO hardDiskDTO = BeanMapper.map(hardDisk, HardDiskDTO.class);
			result.setDto(hardDiskDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createHardDisk(@WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(hardDiskDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.hardDiskService.findByCode(hardDiskDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			HardDisk hardDisk = BeanMapper.map(hardDiskDTO, HardDisk.class);
			BeanValidators.validateWithException(validator, hardDisk);
			comm.hardDiskService.saveOrUpdate(hardDisk);
			return new IdResult(hardDisk.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateHardDisk(@WebParam(name = "id") Integer id,
			@WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(hardDiskDTO, ERROR.INPUT_NULL);
			HardDisk hardDisk = comm.hardDiskService.findHardDisk(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.hardDiskService.findByCode(hardDiskDTO.getCode()) == null
					|| hardDisk.getCode().equals(hardDiskDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			HardDisk hardDiskEntity = BeanMapper.map(hardDiskDTO, HardDisk.class);
			BeanMapper.copy(hardDiskEntity, hardDisk);
			hardDiskEntity.setIdClass(HardDisk.class.getSimpleName());
			hardDiskEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.hardDiskService.saveOrUpdate(hardDiskEntity);
			return new IdResult(hardDisk.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteHardDisk(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			HardDisk hardDisk = comm.hardDiskService.findHardDisk(id);
			HardDisk hardDiskEntity = BeanMapper.map(findHardDisk(id).getDto(), HardDisk.class);
			BeanMapper.copy(hardDiskEntity, hardDisk);
			hardDiskEntity.setIdClass(HardDisk.class.getSimpleName());
			hardDiskEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.hardDiskService.saveOrUpdate(hardDiskEntity);
			return new IdResult(hardDisk.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<HardDiskDTO> getHardDiskPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<HardDiskDTO> result = new PaginationResult<HardDiskDTO>();
		try {
			result = comm.hardDiskService.getHardDiskDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<HardDiskDTO> getHardDiskList() {
		DTOListResult<HardDiskDTO> result = new DTOListResult<HardDiskDTO>();
		try {
			List<HardDisk> companies = comm.hardDiskService.getCompanies();
			List<HardDiskDTO> list = BeanMapper.mapList(companies, HardDiskDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}