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
import com.sobey.cmdbuild.entity.Ecs;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.EcsSoapService;
import com.sobey.cmdbuild.webservice.response.dto.EcsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "EcsService", endpointInterface = "com.sobey.cmdbuild.webservice.EcsSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class EcsSoapServiceImpl extends BasicSoapSevcie implements EcsSoapService {
	@Override
	public DTOResult<EcsDTO> findEcs(@WebParam(name = "id") Integer id) {
		DTOResult<EcsDTO> result = new DTOResult<EcsDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Ecs ecs = comm.ecsService.findEcs(id);
			Validate.notNull(ecs, ERROR.OBJECT_NULL);
			EcsDTO ecsDTO = BeanMapper.map(ecs, EcsDTO.class);
			result.setDto(ecsDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsDTO> findEcsByCode(@WebParam(name = "code") String code) {
		DTOResult<EcsDTO> result = new DTOResult<EcsDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Ecs ecs = comm.ecsService.findByCode(code);
			Validate.notNull(ecs, ERROR.OBJECT_NULL);
			EcsDTO ecsDTO = BeanMapper.map(ecs, EcsDTO.class);
			result.setDto(ecsDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createEcs(@WebParam(name = "ecsDTO") EcsDTO ecsDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ecsDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ecsService.findByCode(ecsDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Ecs ecs = BeanMapper.map(ecsDTO, Ecs.class);
			BeanValidators.validateWithException(validator, ecs);
			comm.ecsService.saveOrUpdate(ecs);
			return new IdResult(ecs.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEcs(@WebParam(name = "id") Integer id, @WebParam(name = "ecsDTO") EcsDTO ecsDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ecsDTO, ERROR.INPUT_NULL);
			Ecs ecs = comm.ecsService.findEcs(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.ecsService.findByCode(ecsDTO.getCode()) == null || ecs.getCode().equals(ecsDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Ecs ecsEntity = BeanMapper.map(ecsDTO, Ecs.class);
			BeanMapper.copy(ecsEntity, ecs);
			ecsEntity.setIdClass(Ecs.class.getSimpleName());
			ecsEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.ecsService.saveOrUpdate(ecsEntity);
			return new IdResult(ecs.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEcs(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Ecs ecs = comm.ecsService.findEcs(id);
			Ecs ecsEntity = BeanMapper.map(findEcs(id).getDto(), Ecs.class);
			BeanMapper.copy(ecsEntity, ecs);
			ecsEntity.setIdClass(Ecs.class.getSimpleName());
			ecsEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.ecsService.saveOrUpdate(ecsEntity);
			return new IdResult(ecs.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EcsDTO> getEcsPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EcsDTO> result = new PaginationResult<EcsDTO>();
		try {
			result = comm.ecsService.getEcsDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EcsDTO> getEcsList() {
		DTOListResult<EcsDTO> result = new DTOListResult<EcsDTO>();
		try {
			List<Ecs> companies = comm.ecsService.getCompanies();
			List<EcsDTO> list = BeanMapper.mapList(companies, EcsDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}