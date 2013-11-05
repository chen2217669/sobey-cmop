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
import com.sobey.cmdbuild.entity.Consumptions;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.ConsumptionsSoapService;
import com.sobey.cmdbuild.webservice.response.dto.ConsumptionsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "ConsumptionsService", endpointInterface = "com.sobey.cmdbuild.webservice.ConsumptionsSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class ConsumptionsSoapServiceImpl extends BasicSoapSevcie implements ConsumptionsSoapService {
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
	public DTOResult<ConsumptionsDTO> findConsumptionsByCode(@WebParam(name = "code") String code) {
		DTOResult<ConsumptionsDTO> result = new DTOResult<ConsumptionsDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Consumptions consumptions = comm.consumptionsService.findByCode(code);
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
	public IdResult createConsumptions(@WebParam(name = "consumptionsDTO") ConsumptionsDTO consumptionsDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(consumptionsDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.consumptionsService.findByCode(consumptionsDTO.getCode()) == null,
					ERROR.OBJECT_DUPLICATE);
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
			Consumptions consumptions = comm.consumptionsService.findConsumptions(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.consumptionsService.findByCode(consumptionsDTO.getCode()) == null
					|| consumptions.getCode().equals(consumptionsDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Consumptions consumptionsEntity = BeanMapper.map(consumptionsDTO, Consumptions.class);
			BeanMapper.copy(consumptionsEntity, consumptions);
			consumptionsEntity.setIdClass(Consumptions.class.getSimpleName());
			consumptionsEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.consumptionsService.saveOrUpdate(consumptionsEntity);
			return new IdResult(consumptions.getId());
		} catch (ConstraintViolationException e) {
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
			result = comm.consumptionsService.getConsumptionsDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ConsumptionsDTO> getConsumptionsList() {
		DTOListResult<ConsumptionsDTO> result = new DTOListResult<ConsumptionsDTO>();
		try {
			List<Consumptions> companies = comm.consumptionsService.getCompanies();
			List<ConsumptionsDTO> list = BeanMapper.mapList(companies, ConsumptionsDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}