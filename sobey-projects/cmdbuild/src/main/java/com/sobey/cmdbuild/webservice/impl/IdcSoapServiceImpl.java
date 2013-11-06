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
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.IdcSoapService;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "IdcService", endpointInterface = "com.sobey.cmdbuild.webservice.IdcSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class IdcSoapServiceImpl extends BasicSoapSevcie implements IdcSoapService {
	@Override
	public DTOResult<IdcDTO> findIdc(@WebParam(name = "id") Integer id) {
		DTOResult<IdcDTO> result = new DTOResult<IdcDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Idc idc = comm.idcService.findIdc(id);
			Validate.notNull(idc, ERROR.OBJECT_NULL);
			IdcDTO idcDTO = BeanMapper.map(idc, IdcDTO.class);
			result.setDto(idcDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<IdcDTO> findIdcByCode(@WebParam(name = "code") String code) {
		DTOResult<IdcDTO> result = new DTOResult<IdcDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Idc idc = comm.idcService.findByCode(code);
			Validate.notNull(idc, ERROR.OBJECT_NULL);
			IdcDTO idcDTO = BeanMapper.map(idc, IdcDTO.class);
			result.setDto(idcDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createIdc(@WebParam(name = "idcDTO") IdcDTO idcDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(idcDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.idcService.findByCode(idcDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Idc idc = BeanMapper.map(idcDTO, Idc.class);
			BeanValidators.validateWithException(validator, idc);
			comm.idcService.saveOrUpdate(idc);
			return new IdResult(idc.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateIdc(@WebParam(name = "id") Integer id, @WebParam(name = "idcDTO") IdcDTO idcDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(idcDTO, ERROR.INPUT_NULL);
			Idc idc = comm.idcService.findIdc(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.idcService.findByCode(idcDTO.getCode()) == null || idc.getCode().equals(idcDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Idc idcEntity = BeanMapper.map(idcDTO, Idc.class);
			BeanMapper.copy(idcEntity, idc);
			idcEntity.setIdClass(Idc.class.getSimpleName());
			idcEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.idcService.saveOrUpdate(idcEntity);
			return new IdResult(idc.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteIdc(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Idc idc = comm.idcService.findIdc(id);
			Idc idcEntity = BeanMapper.map(findIdc(id).getDto(), Idc.class);
			BeanMapper.copy(idcEntity, idc);
			idcEntity.setIdClass(Idc.class.getSimpleName());
			idcEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.idcService.saveOrUpdate(idcEntity);
			return new IdResult(idc.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<IdcDTO> getIdcPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<IdcDTO> result = new PaginationResult<IdcDTO>();
		try {
			result = comm.idcService.getIdcDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<IdcDTO> getIdcList() {
		DTOListResult<IdcDTO> result = new DTOListResult<IdcDTO>();
		try {
			List<Idc> companies = comm.idcService.getCompanies();
			List<IdcDTO> list = BeanMapper.mapList(companies, IdcDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}