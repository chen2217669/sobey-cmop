package com.sobey.cmdbuild.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.entity.Consumptions;
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.entity.EipSpec;
import com.sobey.cmdbuild.entity.Es3Spec;
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.dto.ConsumptionsDTO;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.RackDTO;
import com.sobey.cmdbuild.webservice.response.dto.TagDTO;
import com.sobey.cmdbuild.webservice.response.dto.TenantsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "CmdbuildService", endpointInterface = "com.sobey.cmdbuild.webservice.CmdbuildSoapService", targetNamespace = WsConstants.NS)
// 查看webservice的日志.
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class CmdbuildSoapServiceImpl extends BasicSoapSevcie implements CmdbuildSoapService {

	@Override
	public DTOResult<CompanyDTO> findCompany(@WebParam(name = "id") Integer id) {
		DTOResult<CompanyDTO> result = new DTOResult<CompanyDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Company company = comm.companyService.findCompany(id);
			Validate.notNull(company, ERROR.OBJECT_NULL);
			CompanyDTO companyDTO = BeanMapper.map(company, CompanyDTO.class);
			result.setDto(companyDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<CompanyDTO> findCompanyByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<CompanyDTO> result = new DTOResult<CompanyDTO>();
		try {
			Validate.notNull(searchParams, ERROR.INPUT_NULL);
			Company company = comm.companyService.findCompany(searchParams);
			Validate.notNull(company, ERROR.OBJECT_NULL);
			CompanyDTO companyDTO = BeanMapper.map(company, CompanyDTO.class);
			result.setDto(companyDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createCompany(@WebParam(name = "companyDTO") CompanyDTO companyDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(companyDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", companyDTO.getCode());
			Validate.isTrue(comm.companyService.findCompany(searchParams) == null, ERROR.OBJECT_DUPLICATE);
			Company company = BeanMapper.map(companyDTO, Company.class);
			BeanValidators.validateWithException(validator, company);
			comm.companyService.saveOrUpdate(company);
			return new IdResult(company.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public IdResult updateCompany(@WebParam(name = "id") Integer id,
			@WebParam(name = "companyDTO") CompanyDTO companyDTO) {
		IdResult result = new IdResult();
		try {
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", companyDTO.getCode());
			Validate.notNull(companyDTO, ERROR.INPUT_NULL);
			Company company = comm.companyService.findCompany(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.companyService.findCompany(searchParams) == null
							|| company.getCode().equals(companyDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Company companyEntity = BeanMapper.map(companyDTO, Company.class);
			BeanMapper.copy(companyEntity, company);
			companyEntity.setIdClass(Company.class.getSimpleName());
			companyEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.companyService.saveOrUpdate(companyEntity);
			return new IdResult(company.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteCompany(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Company company = comm.companyService.findCompany(id);
			Company companyEntity = BeanMapper.map(findCompany(id).getDto(), Company.class);
			BeanMapper.copy(companyEntity, company);
			companyEntity.setIdClass(Company.class.getSimpleName());
			companyEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.companyService.saveOrUpdate(companyEntity);
			return new IdResult(company.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<CompanyDTO> getCompanyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<CompanyDTO> result = new PaginationResult<CompanyDTO>();
		try {
			return comm.companyService.getCompanyDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public DTOListResult<CompanyDTO> getCompanyList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<CompanyDTO> result = new DTOListResult<CompanyDTO>();
		try {
			List<Company> companies = comm.companyService.getCompanyList(searchParams);
			List<CompanyDTO> dtos = BeanMapper.mapList(companies, CompanyDTO.class);
			result.setDtos(dtos);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

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
	public DTOResult<TenantsDTO> findTenantsByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<TenantsDTO> result = new DTOResult<TenantsDTO>();
		try {
			Validate.notNull(searchParams, ERROR.INPUT_NULL);
			Tenants tenants = comm.tenantsService.findTenants(searchParams);
			Validate.notNull(tenants, ERROR.OBJECT_NULL);
			TenantsDTO tenantsDTO = BeanMapper.map(tenants, TenantsDTO.class);
			result.setDto(tenantsDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createTenants(@WebParam(name = "tenantsDTO") TenantsDTO tenantsDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tenantsDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", tenantsDTO.getCode());
			Validate.isTrue(comm.tenantsService.findTenants(searchParams) == null, ERROR.OBJECT_DUPLICATE);
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
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", tenantsDTO.getCode());
			Validate.isTrue(
					comm.tenantsService.findTenants(searchParams) == null
							|| tenants.getCode().equals(tenantsDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Tenants tenantsEntity = BeanMapper.map(tenantsDTO, Tenants.class);
			BeanMapper.copy(tenantsEntity, tenants);
			tenantsEntity.setIdClass(Tenants.class.getSimpleName());
			tenantsEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.tenantsService.saveOrUpdate(tenantsEntity);
			return new IdResult(tenants.getId());
		} catch (IllegalArgumentException e) {
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
			return comm.tenantsService.getTenantsDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<TenantsDTO> getTenantsList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<TenantsDTO> result = new DTOListResult<TenantsDTO>();
		try {
			List<Tenants> tenants = comm.tenantsService.getTenantsList(searchParams);
			List<TenantsDTO> list = BeanMapper.mapList(tenants, TenantsDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TagDTO> findTag(@WebParam(name = "id") Integer id) {
		DTOResult<TagDTO> result = new DTOResult<TagDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Tag tag = comm.tagService.findTag(id);
			Validate.notNull(tag, ERROR.OBJECT_NULL);
			TagDTO tagDTO = BeanMapper.map(tag, TagDTO.class);
			result.setDto(tagDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<TagDTO> findTagByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<TagDTO> result = new DTOResult<TagDTO>();
		try {
			Validate.notNull(searchParams, ERROR.INPUT_NULL);
			Tag tag = comm.tagService.findTag(searchParams);
			Validate.notNull(tag, ERROR.OBJECT_NULL);
			TagDTO tagDTO = BeanMapper.map(tag, TagDTO.class);
			result.setDto(tagDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createTag(@WebParam(name = "tagDTO") TagDTO tagDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tagDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			// 此处先判断同一Tenants下是否有相同的code如果有相同的code名称，则不能创建.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", tagDTO.getCode());
			searchParams.put("EQ_tenants", tagDTO.getTenants());
			Validate.isTrue(comm.tagService.findTag(searchParams) == null, ERROR.OBJECT_DUPLICATE);
			Tag tag = BeanMapper.map(tagDTO, Tag.class);
			BeanValidators.validateWithException(validator, tag);
			comm.tagService.saveOrUpdate(tag);
			return new IdResult(tag.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateTag(@WebParam(name = "id") Integer id, @WebParam(name = "tagDTO") TagDTO tagDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tagDTO, ERROR.INPUT_NULL);
			Tag tag = comm.tagService.findTag(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			// 此处先判断同一Tenants下是否有相同的code如果有相同的code名称，则不能创建.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", tagDTO.getCode());
			searchParams.put("EQ_tenants", tagDTO.getTenants());
			Validate.isTrue(comm.tagService.findTag(searchParams) == null || tag.getCode().equals(tagDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Tag tagEntity = BeanMapper.map(tagDTO, Tag.class);
			BeanMapper.copy(tagEntity, tag);
			tagEntity.setIdClass(Tag.class.getSimpleName());
			tagEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.tagService.saveOrUpdate(tagEntity);
			return new IdResult(tag.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteTag(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Tag tag = comm.tagService.findTag(id);
			Tag tagEntity = BeanMapper.map(findTag(id).getDto(), Tag.class);
			BeanMapper.copy(tagEntity, tag);
			tagEntity.setIdClass(Tag.class.getSimpleName());
			tagEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.tagService.saveOrUpdate(tagEntity);
			return new IdResult(tag.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<TagDTO> getTagPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<TagDTO> result = new PaginationResult<TagDTO>();
		try {
			return comm.tagService.getTagDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<TagDTO> getTagList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<TagDTO> result = new DTOListResult<TagDTO>();
		try {
			List<Tag> tags = comm.tagService.getTagList(searchParams);
			List<TagDTO> list = BeanMapper.mapList(tags, TagDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

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
	public DTOResult<IdcDTO> findIdcByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<IdcDTO> result = new DTOResult<IdcDTO>();
		try {
			Validate.notNull(searchParams, ERROR.INPUT_NULL);
			Idc idc = comm.idcService.findIdc(searchParams);
			Validate.notNull(idc, ERROR.OBJECT_NULL);
			IdcDTO idcDTO = BeanMapper.map(idc, IdcDTO.class);
			result.setDto(idcDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createIdc(@WebParam(name = "idcDTO") IdcDTO idcDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(idcDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", idcDTO.getCode());
			Validate.isTrue(comm.idcService.findIdc(searchParams) == null, ERROR.OBJECT_DUPLICATE);
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
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", idcDTO.getCode());
			Validate.isTrue(comm.idcService.findIdc(searchParams) == null || idc.getCode().equals(idcDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Idc idcEntity = BeanMapper.map(idcDTO, Idc.class);
			BeanMapper.copy(idcEntity, idc);
			idcEntity.setIdClass(Idc.class.getSimpleName());
			idcEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.idcService.saveOrUpdate(idcEntity);
			return new IdResult(idc.getId());
		} catch (IllegalArgumentException e) {
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
			return comm.idcService.getIdcDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<IdcDTO> getIdcList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<IdcDTO> result = new DTOListResult<IdcDTO>();
		try {
			List<Idc> idcs = comm.idcService.getIdcList(searchParams);
			List<IdcDTO> list = BeanMapper.mapList(idcs, IdcDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<RackDTO> findRack(@WebParam(name = "id") Integer id) {
		DTOResult<RackDTO> result = new DTOResult<RackDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Rack rack = comm.rackService.findRack(id);
			Validate.notNull(rack, ERROR.OBJECT_NULL);
			RackDTO rackDTO = BeanMapper.map(rack, RackDTO.class);
			result.setDto(rackDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<RackDTO> findRackByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<RackDTO> result = new DTOResult<RackDTO>();
		try {
			Validate.notNull(searchParams, ERROR.INPUT_NULL);
			Rack rack = comm.rackService.findRack(searchParams);
			Validate.notNull(rack, ERROR.OBJECT_NULL);
			RackDTO rackDTO = BeanMapper.map(rack, RackDTO.class);
			result.setDto(rackDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createRack(@WebParam(name = "rackDTO") RackDTO rackDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(rackDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", rackDTO.getCode());
			Validate.isTrue(comm.rackService.findRack(searchParams) == null, ERROR.OBJECT_DUPLICATE);
			Rack rack = BeanMapper.map(rackDTO, Rack.class);
			BeanValidators.validateWithException(validator, rack);
			comm.rackService.saveOrUpdate(rack);
			return new IdResult(rack.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateRack(@WebParam(name = "id") Integer id, @WebParam(name = "rackDTO") RackDTO rackDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(rackDTO, ERROR.INPUT_NULL);
			Rack rack = comm.rackService.findRack(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", rackDTO.getCode());
			Validate.isTrue(
					comm.rackService.findRack(searchParams) == null || rack.getCode().equals(rackDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Rack rackEntity = BeanMapper.map(rackDTO, Rack.class);
			BeanMapper.copy(rackEntity, rack);
			rackEntity.setIdClass(Rack.class.getSimpleName());
			rackEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.rackService.saveOrUpdate(rackEntity);
			return new IdResult(rack.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteRack(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Rack rack = comm.rackService.findRack(id);
			Rack rackEntity = BeanMapper.map(findRack(id).getDto(), Rack.class);
			BeanMapper.copy(rackEntity, rack);
			rackEntity.setIdClass(Rack.class.getSimpleName());
			rackEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.rackService.saveOrUpdate(rackEntity);
			return new IdResult(rack.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<RackDTO> getRackPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<RackDTO> result = new PaginationResult<RackDTO>();
		try {
			return comm.rackService.getRackDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<RackDTO> getRackList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<RackDTO> result = new DTOListResult<RackDTO>();
		try {
			List<Rack> racks = comm.rackService.getRackList(searchParams);
			List<RackDTO> list = BeanMapper.mapList(racks, RackDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConsumptionsDTO> findConsumptions(@WebParam(name = "id") Integer id) {
		DTOResult<ConsumptionsDTO> result = new DTOResult<ConsumptionsDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Consumptions consumptions = comm.consumptionsService.findConsumptions(id);
			Validate.notNull(consumptions, ERROR.OBJECT_NULL);
			ConsumptionsDTO consumptionsDTO = BeanMapper.map(consumptions, ConsumptionsDTO.class);
			result.setDto(consumptionsDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConsumptionsDTO> findConsumptionsByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<ConsumptionsDTO> result = new DTOResult<ConsumptionsDTO>();
		try {
			Validate.notNull(searchParams, ERROR.INPUT_NULL);
			Consumptions consumptions = comm.consumptionsService.findConsumptions(searchParams);
			Validate.notNull(consumptions, ERROR.OBJECT_NULL);
			ConsumptionsDTO consumptionsDTO = BeanMapper.map(consumptions, ConsumptionsDTO.class);
			result.setDto(consumptionsDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createConsumptions(@WebParam(name = "consumptionsDTO") ConsumptionsDTO consumptionsDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(consumptionsDTO, ERROR.INPUT_NULL); // 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", consumptionsDTO.getCode());
			Validate.isTrue(comm.consumptionsService.findConsumptions(searchParams) == null, ERROR.OBJECT_DUPLICATE);
			Consumptions consumptions = BeanMapper.map(consumptionsDTO, Consumptions.class);
			BeanValidators.validateWithException(validator, consumptions);
			comm.consumptionsService.saveOrUpdate(consumptions);
			return new IdResult(consumptions.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateConsumptions(@WebParam(name = "id") Integer id,
			@WebParam(name = "consumptionsDTO") ConsumptionsDTO consumptionsDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(consumptionsDTO, ERROR.INPUT_NULL);
			Consumptions consumptions = comm.consumptionsService.findConsumptions(id); // 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", consumptionsDTO.getCode());
			Validate.isTrue(comm.consumptionsService.findConsumptions(searchParams) == null
					|| consumptions.getCode().equals(consumptionsDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Consumptions consumptionsEntity = BeanMapper.map(consumptionsDTO, Consumptions.class);
			BeanMapper.copy(consumptionsEntity, consumptions);
			consumptionsEntity.setIdClass(Consumptions.class.getSimpleName());
			consumptionsEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.consumptionsService.saveOrUpdate(consumptionsEntity);
			return new IdResult(consumptions.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteConsumptions(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Consumptions consumptions = comm.consumptionsService.findConsumptions(id);
			Consumptions consumptionsEntity = BeanMapper.map(findConsumptions(id).getDto(), Consumptions.class);
			BeanMapper.copy(consumptionsEntity, consumptions);
			consumptionsEntity.setIdClass(Consumptions.class.getSimpleName());
			consumptionsEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.consumptionsService.saveOrUpdate(consumptionsEntity);
			return new IdResult(consumptions.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ConsumptionsDTO> getConsumptionsPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ConsumptionsDTO> result = new PaginationResult<ConsumptionsDTO>();
		try {
			return comm.consumptionsService.getConsumptionsDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ConsumptionsDTO> getConsumptionsList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<ConsumptionsDTO> result = new DTOListResult<ConsumptionsDTO>();
		try {
			List<Consumptions> consumptions = comm.consumptionsService.getConsumptionsList(searchParams);
			List<ConsumptionsDTO> list = BeanMapper.mapList(consumptions, ConsumptionsDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult settleConsumptions(Integer cid, Integer tid) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(cid, ERROR.INPUT_NULL);
			Validate.notNull(tid, ERROR.INPUT_NULL);

			Tenants tenants = comm.tenantsService.findTenants(tid);
			Validate.notNull(tenants, ERROR.OBJECT_NULL);

			Consumptions consumptions = comm.consumptionsService.findConsumptions(cid);
			Validate.notNull(consumptions, ERROR.OBJECT_NULL);

			// 租户计费,判断租户还有钱没有
			if (tenants.getAccontBalance() == null || tenants.getAccontBalance() < consumptions.getSpending()) {
				return null;
			}

			tenants.setAccontBalance(tenants.getAccontBalance() - consumptions.getSpending());

			// 保存信息
			comm.tenantsService.saveOrUpdate(tenants);

			// 租户消费历史

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
		return result;
	}

	@Override
	public DTOListResult<ConsumptionsDTO> reportConsumptions(Map<String, Object> searchParams) {
		return null;
	}

	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpec(@WebParam(name = "id") Integer id) {
		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);
			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);
			DeviceSpecDTO deviceSpecDTO = BeanMapper.map(deviceSpec, DeviceSpecDTO.class);
			result.setDto(deviceSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpecByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();
		try {
			Validate.notNull(searchParams, ERROR.INPUT_NULL);
			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(searchParams);
			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);
			DeviceSpecDTO deviceSpecDTO = BeanMapper.map(deviceSpec, DeviceSpecDTO.class);
			result.setDto(deviceSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createDeviceSpec(@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(deviceSpecDTO, ERROR.INPUT_NULL); // 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", deviceSpecDTO.getCode());
			Validate.isTrue(comm.deviceSpecService.findDeviceSpec(searchParams) == null, ERROR.OBJECT_DUPLICATE);
			DeviceSpec deviceSpec = BeanMapper.map(deviceSpecDTO, DeviceSpec.class);
			BeanValidators.validateWithException(validator, deviceSpec);
			comm.deviceSpecService.saveOrUpdate(deviceSpec);
			return new IdResult(deviceSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateDeviceSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(deviceSpecDTO, ERROR.INPUT_NULL);
			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id); // 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", deviceSpecDTO.getCode());
			Validate.isTrue(
					comm.deviceSpecService.findDeviceSpec(searchParams) == null
							|| deviceSpec.getCode().equals(deviceSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			DeviceSpec deviceSpecEntity = BeanMapper.map(deviceSpecDTO, DeviceSpec.class);
			BeanMapper.copy(deviceSpecEntity, deviceSpec);
			deviceSpecEntity.setIdClass(DeviceSpec.class.getSimpleName());
			deviceSpecEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.deviceSpecService.saveOrUpdate(deviceSpecEntity);
			return new IdResult(deviceSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteDeviceSpec(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);
			DeviceSpec deviceSpecEntity = BeanMapper.map(findDeviceSpec(id).getDto(), DeviceSpec.class);
			BeanMapper.copy(deviceSpecEntity, deviceSpec);
			deviceSpecEntity.setIdClass(DeviceSpec.class.getSimpleName());
			deviceSpecEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.deviceSpecService.saveOrUpdate(deviceSpecEntity);
			return new IdResult(deviceSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<DeviceSpecDTO> getDeviceSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<DeviceSpecDTO> result = new PaginationResult<DeviceSpecDTO>();
		try {
			return comm.deviceSpecService.getDeviceSpecDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<DeviceSpecDTO> getDeviceSpecList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<DeviceSpecDTO> result = new DTOListResult<DeviceSpecDTO>();
		try {
			List<DeviceSpec> deviceSpec = comm.deviceSpecService.getDeviceSpecList(searchParams);
			List<DeviceSpecDTO> list = BeanMapper.mapList(deviceSpec, DeviceSpecDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsSpecDTO> findEcsSpec(@WebParam(name = "id") Integer id) {
		DTOResult<EcsSpecDTO> result = new DTOResult<EcsSpecDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);
			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);
			EcsSpecDTO ecsSpecDTO = BeanMapper.map(ecsSpec, EcsSpecDTO.class);
			result.setDto(ecsSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsSpecDTO> findEcsSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<EcsSpecDTO> result = new DTOResult<EcsSpecDTO>();
		try {
			Validate.notNull(searchParams, ERROR.INPUT_NULL);
			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(searchParams);
			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);
			EcsSpecDTO ecsSpecDTO = BeanMapper.map(ecsSpec, EcsSpecDTO.class);
			result.setDto(ecsSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEcsSpec(@WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ecsSpecDTO, ERROR.INPUT_NULL); // 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", ecsSpecDTO.getCode());
			Validate.isTrue(comm.ecsSpecService.findEcsSpec(searchParams) == null, ERROR.OBJECT_DUPLICATE);
			EcsSpec ecsSpec = BeanMapper.map(ecsSpecDTO, EcsSpec.class);
			BeanValidators.validateWithException(validator, ecsSpec);
			comm.ecsSpecService.saveOrUpdate(ecsSpec);
			return new IdResult(ecsSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEcsSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ecsSpecDTO, ERROR.INPUT_NULL);
			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id); // 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", ecsSpecDTO.getCode());
			Validate.isTrue(
					comm.ecsSpecService.findEcsSpec(searchParams) == null
							|| ecsSpec.getCode().equals(ecsSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EcsSpec ecsSpecEntity = BeanMapper.map(ecsSpecDTO, EcsSpec.class);
			BeanMapper.copy(ecsSpecEntity, ecsSpec);
			ecsSpecEntity.setIdClass(EcsSpec.class.getSimpleName());
			ecsSpecEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.ecsSpecService.saveOrUpdate(ecsSpecEntity);
			return new IdResult(ecsSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEcsSpec(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);
			EcsSpec ecsSpecEntity = BeanMapper.map(findEcsSpec(id).getDto(), EcsSpec.class);
			BeanMapper.copy(ecsSpecEntity, ecsSpec);
			ecsSpecEntity.setIdClass(EcsSpec.class.getSimpleName());
			ecsSpecEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.ecsSpecService.saveOrUpdate(ecsSpecEntity);
			return new IdResult(ecsSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EcsSpecDTO> getEcsSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EcsSpecDTO> result = new PaginationResult<EcsSpecDTO>();
		try {
			return comm.ecsSpecService.getEcsSpecDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EcsSpecDTO> getEcsSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<EcsSpecDTO> result = new DTOListResult<EcsSpecDTO>();
		try {
			List<EcsSpec> ecsSpec = comm.ecsSpecService.getEcsSpecList(searchParams);
			List<EcsSpecDTO> list = BeanMapper.mapList(ecsSpec, EcsSpecDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipSpecDTO> findEipSpec(@WebParam(name = "id") Integer id) {
		DTOResult<EipSpecDTO> result = new DTOResult<EipSpecDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id);
			Validate.notNull(eipSpec, ERROR.OBJECT_NULL);
			EipSpecDTO eipSpecDTO = BeanMapper.map(eipSpec, EipSpecDTO.class);
			result.setDto(eipSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipSpecDTO> findEipSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<EipSpecDTO> result = new DTOResult<EipSpecDTO>();
		try {
			Validate.notNull(searchParams, ERROR.INPUT_NULL);
			EipSpec eipSpec = comm.eipSpecService.findEipSpec(searchParams);
			Validate.notNull(eipSpec, ERROR.OBJECT_NULL);
			EipSpecDTO eipSpecDTO = BeanMapper.map(eipSpec, EipSpecDTO.class);
			result.setDto(eipSpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEipSpec(@WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipSpecDTO, ERROR.INPUT_NULL); // 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", eipSpecDTO.getCode());
			Validate.isTrue(comm.eipSpecService.findEipSpec(searchParams) == null, ERROR.OBJECT_DUPLICATE);
			EipSpec eipSpec = BeanMapper.map(eipSpecDTO, EipSpec.class);
			BeanValidators.validateWithException(validator, eipSpec);
			comm.eipSpecService.saveOrUpdate(eipSpec);
			return new IdResult(eipSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEipSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(eipSpecDTO, ERROR.INPUT_NULL);
			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id); // 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", eipSpecDTO.getCode());
			Validate.isTrue(
					comm.eipSpecService.findEipSpec(searchParams) == null
							|| eipSpec.getCode().equals(eipSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			EipSpec eipSpecEntity = BeanMapper.map(eipSpecDTO, EipSpec.class);
			BeanMapper.copy(eipSpecEntity, eipSpec);
			eipSpecEntity.setIdClass(EipSpec.class.getSimpleName());
			eipSpecEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.eipSpecService.saveOrUpdate(eipSpecEntity);
			return new IdResult(eipSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEipSpec(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id);
			EipSpec eipSpecEntity = BeanMapper.map(findEipSpec(id).getDto(), EipSpec.class);
			BeanMapper.copy(eipSpecEntity, eipSpec);
			eipSpecEntity.setIdClass(EipSpec.class.getSimpleName());
			eipSpecEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.eipSpecService.saveOrUpdate(eipSpecEntity);
			return new IdResult(eipSpec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipSpecDTO> getEipSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EipSpecDTO> result = new PaginationResult<EipSpecDTO>();
		try {
			return comm.eipSpecService.getEipSpecDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipSpecDTO> getEipSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<EipSpecDTO> result = new DTOListResult<EipSpecDTO>();
		try {
			List<EipSpec> eipSpec = comm.eipSpecService.getEipSpecList(searchParams);
			List<EipSpecDTO> list = BeanMapper.mapList(eipSpec, EipSpecDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3SpecDTO> findEs3Spec(@WebParam(name = "id") Integer id) {
		DTOResult<Es3SpecDTO> result = new DTOResult<Es3SpecDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id);
			Validate.notNull(es3Spec, ERROR.OBJECT_NULL);
			Es3SpecDTO es3SpecDTO = BeanMapper.map(es3Spec, Es3SpecDTO.class);
			result.setDto(es3SpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3SpecDTO> findEs3SpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<Es3SpecDTO> result = new DTOResult<Es3SpecDTO>();
		try {
			Validate.notNull(searchParams, ERROR.INPUT_NULL);
			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(searchParams);
			Validate.notNull(es3Spec, ERROR.OBJECT_NULL);
			Es3SpecDTO es3SpecDTO = BeanMapper.map(es3Spec, Es3SpecDTO.class);
			result.setDto(es3SpecDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEs3Spec(@WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(es3SpecDTO, ERROR.INPUT_NULL); // 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", es3SpecDTO.getCode());
			Validate.isTrue(comm.es3SpecService.findEs3Spec(searchParams) == null, ERROR.OBJECT_DUPLICATE);
			Es3Spec es3Spec = BeanMapper.map(es3SpecDTO, Es3Spec.class);
			BeanValidators.validateWithException(validator, es3Spec);
			comm.es3SpecService.saveOrUpdate(es3Spec);
			return new IdResult(es3Spec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEs3Spec(@WebParam(name = "id") Integer id,
			@WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(es3SpecDTO, ERROR.INPUT_NULL);
			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id); // 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", es3SpecDTO.getCode());
			Validate.isTrue(
					comm.es3SpecService.findEs3Spec(searchParams) == null
							|| es3Spec.getCode().equals(es3SpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Es3Spec es3SpecEntity = BeanMapper.map(es3SpecDTO, Es3Spec.class);
			BeanMapper.copy(es3SpecEntity, es3Spec);
			es3SpecEntity.setIdClass(Es3Spec.class.getSimpleName());
			es3SpecEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.es3SpecService.saveOrUpdate(es3SpecEntity);
			return new IdResult(es3Spec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEs3Spec(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id);
			Es3Spec es3SpecEntity = BeanMapper.map(findEs3Spec(id).getDto(), Es3Spec.class);
			BeanMapper.copy(es3SpecEntity, es3Spec);
			es3SpecEntity.setIdClass(Es3Spec.class.getSimpleName());
			es3SpecEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.es3SpecService.saveOrUpdate(es3SpecEntity);
			return new IdResult(es3Spec.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<Es3SpecDTO> getEs3SpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<Es3SpecDTO> result = new PaginationResult<Es3SpecDTO>();
		try {
			return comm.es3SpecService.getEs3SpecDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<Es3SpecDTO> getEs3SpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<Es3SpecDTO> result = new DTOListResult<Es3SpecDTO>();
		try {
			List<Es3Spec> es3Spec = comm.es3SpecService.getEs3SpecList(searchParams);
			List<Es3SpecDTO> list = BeanMapper.mapList(es3Spec, Es3SpecDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

}
