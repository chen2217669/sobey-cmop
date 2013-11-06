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
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.CompanySoapService;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "CompanyService", endpointInterface = "com.sobey.cmdbuild.webservice.CompanySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class CompanySoapServiceImpl extends BasicSoapSevcie implements CompanySoapService {
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
			result = comm.companyService.getCompanyDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<CompanyDTO> getCompanyList() {
		DTOListResult<CompanyDTO> result = new DTOListResult<CompanyDTO>();
		try {
			List<Company> companies = comm.companyService.getCompanies();
			List<CompanyDTO> list = BeanMapper.mapList(companies, CompanyDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}