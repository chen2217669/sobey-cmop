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
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.TenantsSoapService;
import com.sobey.cmdbuild.webservice.response.dto.TenantsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "TenantsService", endpointInterface = "com.sobey.cmdbuild.webservice.TenantsSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class TenantsSoapServiceImpl extends BasicSoapSevcie implements TenantsSoapService {
	@Override
	public DTOResult<TenantsDTO> findTenants(@WebParam(name = "id") Integer id) {
		DTOResult<TenantsDTO> result = new DTOResult<TenantsDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Tenants tenants = comm.tenantsService.findTenants(id);
			Validate.notNull(tenants, ERROR.OBJECT_NULL);
			TenantsDTO tenantsDTO = BeanMapper.map(tenants, TenantsDTO.class);
			result.setDto(tenantsDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TenantsDTO> findTenantsByCode(@WebParam(name = "code") String code) {
		DTOResult<TenantsDTO> result = new DTOResult<TenantsDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Tenants tenants = comm.tenantsService.findByCode(code);
			Validate.notNull(tenants, ERROR.OBJECT_NULL);
			TenantsDTO tenantsDTO = BeanMapper.map(tenants, TenantsDTO.class);
			result.setDto(tenantsDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createTenants(@WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tenantsDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.tenantsService.findByCode(tenantsDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Tenants tenants = BeanMapper.map(tenantsDTO, Tenants.class);
			BeanValidators.validateWithException(validator, tenants);
			comm.tenantsService.saveOrUpdate(tenants);
			return new IdResult(tenants.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateTenants(@WebParam(name = "id") Integer id,
			@WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tenantsDTO, ERROR.INPUT_NULL);
			Tenants tenants = comm.tenantsService.findTenants(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.tenantsService.findByCode(tenantsDTO.getCode()) == null
							|| tenants.getCode().equals(tenantsDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Tenants tenantsEntity = BeanMapper.map(tenantsDTO, Tenants.class);
			BeanMapper.copy(tenantsEntity, tenants);
			tenantsEntity.setIdClass(Tenants.class.getSimpleName());
			tenantsEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.tenantsService.saveOrUpdate(tenantsEntity);
			return new IdResult(tenants.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteTenants(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Tenants tenants = comm.tenantsService.findTenants(id);
			Tenants tenantsEntity = BeanMapper.map(findTenants(id).getDto(), Tenants.class);
			BeanMapper.copy(tenantsEntity, tenants);
			tenantsEntity.setIdClass(Tenants.class.getSimpleName());
			tenantsEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.tenantsService.saveOrUpdate(tenantsEntity);
			return new IdResult(tenants.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<TenantsDTO> getTenantsPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<TenantsDTO> result = new PaginationResult<TenantsDTO>();
		try {
			result = comm.tenantsService.getTenantsDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<TenantsDTO> getTenantsList() {
		DTOListResult<TenantsDTO> result = new DTOListResult<TenantsDTO>();
		try {
			List<Tenants> companies = comm.tenantsService.getCompanies();
			List<TenantsDTO> list = BeanMapper.mapList(companies, TenantsDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}