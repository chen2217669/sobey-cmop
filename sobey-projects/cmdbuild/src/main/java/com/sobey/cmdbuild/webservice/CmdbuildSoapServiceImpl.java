package com.sobey.cmdbuild.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;
import org.springframework.dao.DuplicateKeyException;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.webservice.response.base.IdResult;
import com.sobey.cmdbuild.webservice.response.base.PaginationResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.result.CompanyResult;
import com.sobey.cmdbuild.webservice.response.result.plural.CompaniesResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.utils.Exceptions;

@WebService(serviceName = "CmdbuildService", endpointInterface = "com.sobey.cmdbuild.webservice.CmdbuildSoapService", targetNamespace = WsConstants.NS)
// 查看webservice的日志.
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class CmdbuildSoapServiceImpl extends BasicSoapSevcie implements CmdbuildSoapService {

	@Override
	public CompanyResult findCompany(@WebParam(name = "id") Integer id) {

		CompanyResult result = new CompanyResult();

		try {

			Validate.notNull(id, "id参数为空");

			Company company = comm.companyService.findCompany(id);

			Validate.notNull(company, "对象不存在");

			CompanyDTO companyDTO = BeanMapper.map(company, CompanyDTO.class);

			result.setCompanyDTO(companyDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public IdResult createCompany(@WebParam(name = "companyDTO") CompanyDTO companyDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(companyDTO, "输入参数为空");

			Company company = BeanMapper.map(companyDTO, Company.class);

			BeanValidators.validateWithException(validator, company);

			comm.companyService.saveOrUpdate(company);

			return new IdResult(company.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (RuntimeException e) {
			if (Exceptions.isCausedBy(e, DuplicateKeyException.class)) {
				String message = "对象存在唯一性冲突!";
				return handleParameterError(result, e, message);
			} else {
				return handleGeneralError(result, e);
			}
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public IdResult updateCompany(@WebParam(name = "id") Integer id,
			@WebParam(name = "companyDTO") CompanyDTO companyDTO) {

		IdResult result = new IdResult();

		try {

			Company company = comm.companyService.findCompany(id);

			Company companyEntity = BeanMapper.map(companyDTO, Company.class);

			BeanMapper.copy(companyEntity, company);

			companyEntity.setIdClass(Company.class.getSimpleName());
			companyEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			comm.companyService.saveOrUpdate(companyEntity);

			return new IdResult(company.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (RuntimeException e) {
			if (Exceptions.isCausedBy(e, DuplicateKeyException.class)) {
				String message = "对象存在唯一性冲突!";
				return handleParameterError(result, e, message);
			} else {
				return handleGeneralError(result, e);
			}
		}
	}

	@Override
	public IdResult deleteCompany(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Company company = comm.companyService.findCompany(id);

			Company companyEntity = BeanMapper.map(findCompany(id).getCompanyDTO(), Company.class);

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

			Validate.notNull(result.getGetContent(), "对象不存在");

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}

	}

	@Override
	public CompaniesResult getCompanies() {

		CompaniesResult result = new CompaniesResult();

		try {

			List<Company> companies = comm.companyService.getCompanies();

			Validate.notNull(companies, "对象不存在");

			List<CompanyDTO> dtos = BeanMapper.mapList(companies, CompanyDTO.class);

			result.setDtos(dtos);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

}
