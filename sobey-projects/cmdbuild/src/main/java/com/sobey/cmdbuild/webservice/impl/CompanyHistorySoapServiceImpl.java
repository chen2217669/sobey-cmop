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
import com.sobey.cmdbuild.entity.CompanyHistory;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.CompanyHistorySoapService;
import com.sobey.cmdbuild.webservice.response.dto.CompanyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "CompanyHistoryService", endpointInterface = "com.sobey.cmdbuild.webservice.CompanyHistorySoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class CompanyHistorySoapServiceImpl extends BasicSoapSevcie implements CompanyHistorySoapService {
	@Override
	public DTOResult<CompanyHistoryDTO> findCompanyHistory(@WebParam(name = "id") Integer id) {
		DTOResult<CompanyHistoryDTO> result = new DTOResult<CompanyHistoryDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			CompanyHistory companyHistory = comm.companyHistoryService.findCompanyHistory(id);
			Validate.notNull(companyHistory, ERROR.OBJECT_NULL);
			CompanyHistoryDTO companyHistoryDTO = BeanMapper.map(companyHistory, CompanyHistoryDTO.class);
			result.setDto(companyHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<CompanyHistoryDTO> findCompanyHistoryByCode(@WebParam(name = "code") String code) {
		DTOResult<CompanyHistoryDTO> result = new DTOResult<CompanyHistoryDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			CompanyHistory companyHistory = comm.companyHistoryService.findByCode(code);
			Validate.notNull(companyHistory, ERROR.OBJECT_NULL);
			CompanyHistoryDTO companyHistoryDTO = BeanMapper.map(companyHistory, CompanyHistoryDTO.class);
			result.setDto(companyHistoryDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createCompanyHistory(@WebParam(name = "companyHistoryDTO") CompanyHistoryDTO companyHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(companyHistoryDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.companyHistoryService.findByCode(companyHistoryDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
			CompanyHistory companyHistory = BeanMapper.map(companyHistoryDTO, CompanyHistory.class);
			BeanValidators.validateWithException(validator, companyHistory);
			comm.companyHistoryService.saveOrUpdate(companyHistory);
			return new IdResult(companyHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateCompanyHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "companyHistoryDTO") CompanyHistoryDTO companyHistoryDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(companyHistoryDTO, ERROR.INPUT_NULL);
			CompanyHistory companyHistory = comm.companyHistoryService.findCompanyHistory(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.companyHistoryService.findByCode(companyHistoryDTO.getCode()) == null
					|| companyHistory.getCode().equals(companyHistoryDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			CompanyHistory companyHistoryEntity = BeanMapper.map(companyHistoryDTO, CompanyHistory.class);
			BeanMapper.copy(companyHistoryEntity, companyHistory);
			companyHistoryEntity.setIdClass(CompanyHistory.class.getSimpleName());
			companyHistoryEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.companyHistoryService.saveOrUpdate(companyHistoryEntity);
			return new IdResult(companyHistory.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteCompanyHistory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			CompanyHistory companyHistory = comm.companyHistoryService.findCompanyHistory(id);
			CompanyHistory companyHistoryEntity = BeanMapper.map(findCompanyHistory(id).getDto(), CompanyHistory.class);
			BeanMapper.copy(companyHistoryEntity, companyHistory);
			companyHistoryEntity.setIdClass(CompanyHistory.class.getSimpleName());
			companyHistoryEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.companyHistoryService.saveOrUpdate(companyHistoryEntity);
			return new IdResult(companyHistory.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<CompanyHistoryDTO> getCompanyHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<CompanyHistoryDTO> result = new PaginationResult<CompanyHistoryDTO>();
		try {
			result = comm.companyHistoryService.getCompanyHistoryDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<CompanyHistoryDTO> getCompanyHistoryList() {
		DTOListResult<CompanyHistoryDTO> result = new DTOListResult<CompanyHistoryDTO>();
		try {
			List<CompanyHistory> companies = comm.companyHistoryService.getCompanies();
			List<CompanyHistoryDTO> list = BeanMapper.mapList(companies, CompanyHistoryDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}