package com.sobey.cmdbuild.webservice;

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
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
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
	public DTOResult<CompanyDTO> findCompanyByCode(@WebParam(name = "code") String code) {
		DTOResult<CompanyDTO> result = new DTOResult<CompanyDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Company company = comm.companyService.findByCode(code);
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
	public IdResult createCompany(@WebParam(name = "companyDTO") CompanyDTO companyDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(companyDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.companyService.findByCode(companyDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
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
			Validate.notNull(companyDTO, ERROR.INPUT_NULL);
			Company company = comm.companyService.findCompany(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.companyService.findByCode(companyDTO.getCode()) == null
							|| company.getCode().equals(companyDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Company companyEntity = BeanMapper.map(companyDTO, Company.class);
			BeanMapper.copy(companyEntity, company);
			companyEntity.setIdClass(Company.class.getSimpleName());
			companyEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.companyService.saveOrUpdate(companyEntity);
			return new IdResult(company.getId());
		} catch (ConstraintViolationException e) {
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
	public DTOListResult<CompanyDTO> getCompanies() {
		DTOListResult<CompanyDTO> result = new DTOListResult<CompanyDTO>();
		try {
			List<Company> companies = comm.companyService.getCompanies();
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
			return comm.tenantsService.getTenantsDTOPagination(searchParams, pageNumber, pageSize);
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
			List<Tenants> companies = comm.tenantsService.getTenants();
			List<TenantsDTO> list = BeanMapper.mapList(companies, TenantsDTO.class);
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
	public DTOResult<TagDTO> findTagByCode(@WebParam(name = "code") String code) {
		DTOResult<TagDTO> result = new DTOResult<TagDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Tag tag = comm.tagService.findByCode(code);
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
	public IdResult createTag(@WebParam(name = "tagDTO") TagDTO tagDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(tagDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.tagService.findByCode(tagDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
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
			Validate.isTrue(
					comm.tagService.findByCode(tagDTO.getCode()) == null || tag.getCode().equals(tagDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Tag tagEntity = BeanMapper.map(tagDTO, Tag.class);
			BeanMapper.copy(tagEntity, tag);
			tagEntity.setIdClass(Tag.class.getSimpleName());
			tagEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.tagService.saveOrUpdate(tagEntity);
			return new IdResult(tag.getId());
		} catch (ConstraintViolationException e) {
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
	public DTOListResult<TagDTO> getTagList() {
		DTOListResult<TagDTO> result = new DTOListResult<TagDTO>();
		try {
			List<Tag> companies = comm.tagService.getTags();
			List<TagDTO> list = BeanMapper.mapList(companies, TagDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<TagDTO> getTagListByTenants(@WebParam(name = "tenantsId") Integer tenantsId) {
		DTOListResult<TagDTO> result = new DTOListResult<TagDTO>();
		try {
			List<Tag> companies = comm.tagService.getTagsByTenants(tenantsId);
			List<TagDTO> list = BeanMapper.mapList(companies, TagDTO.class);
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
			return comm.idcService.getIdcDTOPagination(searchParams, pageNumber, pageSize);
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
			List<Idc> companies = comm.idcService.getIdcs();
			List<IdcDTO> list = BeanMapper.mapList(companies, IdcDTO.class);
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
	public DTOResult<RackDTO> findRackByCode(@WebParam(name = "code") String code) {
		DTOResult<RackDTO> result = new DTOResult<RackDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Rack rack = comm.rackService.findByCode(code);
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
	public IdResult createRack(@WebParam(name = "rackDTO") RackDTO rackDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(rackDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.rackService.findByCode(rackDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
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
			Validate.isTrue(
					comm.rackService.findByCode(rackDTO.getCode()) == null || rack.getCode().equals(rackDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Rack rackEntity = BeanMapper.map(rackDTO, Rack.class);
			BeanMapper.copy(rackEntity, rack);
			rackEntity.setIdClass(Rack.class.getSimpleName());
			rackEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.rackService.saveOrUpdate(rackEntity);
			return new IdResult(rack.getId());
		} catch (ConstraintViolationException e) {
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
	public DTOListResult<RackDTO> getRackList() {
		DTOListResult<RackDTO> result = new DTOListResult<RackDTO>();
		try {
			List<Rack> companies = comm.rackService.getRacks();
			List<RackDTO> list = BeanMapper.mapList(companies, RackDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}
