package com.sobey.cmdbuild.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;

import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.entity.Company;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
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

	private static final String DUPLICATE_ERROR = "对象存在唯一性冲突";
	private static final String NULL_ERROR = "对象不存在";
	private static final String INPUT_ERROR = "输入参数为空";

	@Override
	public DTOResult<CompanyDTO> findCompany(@WebParam(name = "id") Integer id) {

		DTOResult<CompanyDTO> result = new DTOResult<CompanyDTO>();

		try {

			Validate.notNull(id, INPUT_ERROR);

			Company company = comm.companyService.findCompany(id);

			Validate.notNull(company, NULL_ERROR);

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

			Validate.notNull(code, INPUT_ERROR);

			Company company = comm.companyService.findByCode(code);

			Validate.notNull(company, NULL_ERROR);

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

			Validate.notNull(companyDTO, INPUT_ERROR);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.companyService.findByCode(companyDTO.getCode()) == null, DUPLICATE_ERROR);

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

			Validate.notNull(companyDTO, INPUT_ERROR);

			Company company = comm.companyService.findCompany(id);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.companyService.findByCode(companyDTO.getCode()) == null
							|| company.getCode().equals(companyDTO.getCode()), DUPLICATE_ERROR);

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

			Validate.notNull(id, INPUT_ERROR);

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

}
