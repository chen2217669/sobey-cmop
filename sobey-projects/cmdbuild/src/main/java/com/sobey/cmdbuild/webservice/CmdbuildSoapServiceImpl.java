package com.sobey.cmdbuild.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.entity.Idc;
import com.sobey.cmdbuild.entity.LookUp;
import com.sobey.cmdbuild.entity.Rack;
import com.sobey.cmdbuild.entity.Tag;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.dto.IdcDTO;
import com.sobey.cmdbuild.webservice.response.dto.LookUpDTO;
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

	/**
	 * CMDBuild的默认超级用户名
	 */
	private static final String DEFAULT_USER = "admin";

	@Override
	public DTOResult<LookUpDTO> findLookUp(@WebParam(name = "id") Integer id) {

		DTOResult<LookUpDTO> result = new DTOResult<LookUpDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LookUp lookUp = comm.lookUpService.findLookUp(id);

			Validate.notNull(lookUp, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(lookUp, LookUpDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LookUpDTO> findLookUpByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<LookUpDTO> result = new DTOResult<LookUpDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			LookUp lookUp = comm.lookUpService.findLookUp(searchParams);

			Validate.notNull(lookUp, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(lookUp, LookUpDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public DTOListResult<LookUpDTO> getLookUpList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<LookUpDTO> result = new DTOListResult<LookUpDTO>();
		try {
			result.setDtos(BeanMapper.mapList(comm.lookUpService.getLookUpList(searchParams), LookUpDTO.class));
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LookUpDTO> getLookUpPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<LookUpDTO> result = new PaginationResult<LookUpDTO>();
		try {
			return comm.lookUpService.getLookUpDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<CompanyDTO> findCompany(@WebParam(name = "id") Integer id) {

		DTOResult<CompanyDTO> result = new DTOResult<CompanyDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Company company = comm.companyService.findCompany(id);

			Validate.notNull(company, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(company, CompanyDTO.class));

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

			result.setDto(BeanMapper.map(company, CompanyDTO.class));

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

			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", companyDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.companyService.findCompany(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Company company = BeanMapper.map(companyDTO, Company.class);
			company.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, company);

			comm.companyService.saveOrUpdate(company);

			return new IdResult(company.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
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
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", companyDTO.getCode());

			Validate.isTrue(
					comm.companyService.findCompany(searchParams) == null
							|| company.getCode().equals(companyDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(companyDTO, Company.class), company);

			company.setUser(DEFAULT_USER);
			company.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			company.setIdClass(Company.class.getSimpleName());

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, company);

			comm.companyService.saveOrUpdate(company);

			return new IdResult(company.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
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
			company.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.companyService.saveOrUpdate(company);

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
			result.setDtos(BeanMapper.mapList(comm.companyService.getCompanyList(searchParams), CompanyDTO.class));
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

			result.setDto(BeanMapper.map(tenants, TenantsDTO.class));

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

			result.setDto(BeanMapper.map(tenants, TenantsDTO.class));

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

			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", tenantsDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.tenantsService.findTenants(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Tenants tenants = BeanMapper.map(tenantsDTO, Tenants.class);
			tenants.setUser(DEFAULT_USER);

			BeanValidators.validateWithException(validator, tenants);

			comm.tenantsService.saveOrUpdate(tenants);

			return new IdResult(tenants.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
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

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(tenantsDTO, Tenants.class), tenants);

			tenants.setUser(DEFAULT_USER);
			tenants.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			tenants.setIdClass(Tenants.class.getSimpleName());

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, tenants);

			comm.tenantsService.saveOrUpdate(tenants);

			return new IdResult(tenants.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
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
			tenants.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.tenantsService.saveOrUpdate(tenants);

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
			List<TenantsDTO> list = BeanMapper.mapList(comm.tenantsService.getTenantsList(searchParams),
					TenantsDTO.class);
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

			result.setDto(BeanMapper.map(tag, TagDTO.class));

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

			result.setDto(BeanMapper.map(tag, TagDTO.class));

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
			// 此处先判断同一Tenants下是否有相同的code,如果有相同的code名称，则不能创建.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", tagDTO.getCode());
			searchParams.put("EQ_tenants", tagDTO.getTenants());

			Validate.isTrue(comm.tagService.findTag(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Tag tag = BeanMapper.map(tagDTO, Tag.class);
			tag.setUser(DEFAULT_USER);

			BeanValidators.validateWithException(validator, tag);

			comm.tagService.saveOrUpdate(tag);

			return new IdResult(tag.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
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

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(tagDTO, Tag.class), tag);

			tag.setUser(DEFAULT_USER);
			tag.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			tag.setIdClass(Tag.class.getSimpleName());

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, tag);

			comm.tagService.saveOrUpdate(tag);

			return new IdResult(tag.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
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
			tag.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.tagService.saveOrUpdate(tag);

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
			result.setDtos(BeanMapper.mapList(comm.tagService.getTagList(searchParams), TagDTO.class));
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

			result.setDto(BeanMapper.map(idc, IdcDTO.class));

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

			result.setDto(BeanMapper.map(idc, IdcDTO.class));

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

			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", idcDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.idcService.findIdc(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Idc idc = BeanMapper.map(idcDTO, Idc.class);
			idc.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, idc);

			comm.idcService.saveOrUpdate(idc);

			return new IdResult(idc.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
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

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(idcDTO, Idc.class), idc);

			idc.setUser(DEFAULT_USER);
			idc.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			idc.setIdClass(Idc.class.getSimpleName());

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, idc);

			comm.idcService.saveOrUpdate(idc);

			return new IdResult(idc.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
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
			idc.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.idcService.saveOrUpdate(idc);

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
			result.setDtos(BeanMapper.mapList(comm.idcService.getIdcList(searchParams), IdcDTO.class));
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

			result.setDto(BeanMapper.map(rack, RackDTO.class));

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

			result.setDto(BeanMapper.map(rack, RackDTO.class));

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

			// 将DTO对象转换至Entity对象
			Rack rack = BeanMapper.map(rackDTO, Rack.class);
			rack.setUser(DEFAULT_USER);

			BeanValidators.validateWithException(validator, rack);

			comm.rackService.saveOrUpdate(rack);

			return new IdResult(rack.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
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

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(rackDTO, Rack.class), rack);

			rack.setUser(DEFAULT_USER);
			rack.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			rack.setIdClass(Rack.class.getSimpleName());

			BeanValidators.validateWithException(validator, rack);

			comm.rackService.saveOrUpdate(rack);

			return new IdResult(rack.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
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
			rack.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.rackService.saveOrUpdate(rack);

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
			result.setDtos(BeanMapper.mapList(comm.rackService.getRackList(searchParams), RackDTO.class));
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

}
