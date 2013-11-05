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
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.SwitchesSoapService;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "SwitchesService", endpointInterface = "com.sobey.cmdbuild.webservice.SwitchesSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class SwitchesSoapServiceImpl extends BasicSoapSevcie implements SwitchesSoapService {
	@Override
	public DTOResult<SwitchesDTO> findSwitches(@WebParam(name = "id") Integer id) {
		DTOResult<SwitchesDTO> result = new DTOResult<SwitchesDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Switches switches = comm.switchesService.findSwitches(id);
			Validate.notNull(switches, ERROR.OBJECT_NULL);
			SwitchesDTO switchesDTO = BeanMapper.map(switches, SwitchesDTO.class);
			result.setDto(switchesDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<SwitchesDTO> findSwitchesByCode(@WebParam(name = "code") String code) {
		DTOResult<SwitchesDTO> result = new DTOResult<SwitchesDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Switches switches = comm.switchesService.findByCode(code);
			Validate.notNull(switches, ERROR.OBJECT_NULL);
			SwitchesDTO switchesDTO = BeanMapper.map(switches, SwitchesDTO.class);
			result.setDto(switchesDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createSwitches(@WebParam(name = "switchesDTO") SwitchesDTO switchesDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(switchesDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.switchesService.findByCode(switchesDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Switches switches = BeanMapper.map(switchesDTO, Switches.class);
			BeanValidators.validateWithException(validator, switches);
			comm.switchesService.saveOrUpdate(switches);
			return new IdResult(switches.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateSwitches(@WebParam(name = "id") Integer id,
			@WebParam(name = "switchesDTO") SwitchesDTO switchesDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(switchesDTO, ERROR.INPUT_NULL);
			Switches switches = comm.switchesService.findSwitches(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.switchesService.findByCode(switchesDTO.getCode()) == null
					|| switches.getCode().equals(switchesDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Switches switchesEntity = BeanMapper.map(switchesDTO, Switches.class);
			BeanMapper.copy(switchesEntity, switches);
			switchesEntity.setIdClass(Switches.class.getSimpleName());
			switchesEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.switchesService.saveOrUpdate(switchesEntity);
			return new IdResult(switches.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteSwitches(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Switches switches = comm.switchesService.findSwitches(id);
			Switches switchesEntity = BeanMapper.map(findSwitches(id).getDto(), Switches.class);
			BeanMapper.copy(switchesEntity, switches);
			switchesEntity.setIdClass(Switches.class.getSimpleName());
			switchesEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.switchesService.saveOrUpdate(switchesEntity);
			return new IdResult(switches.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<SwitchesDTO> getSwitchesPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<SwitchesDTO> result = new PaginationResult<SwitchesDTO>();
		try {
			result = comm.switchesService.getSwitchesDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<SwitchesDTO> getSwitchesList() {
		DTOListResult<SwitchesDTO> result = new DTOListResult<SwitchesDTO>();
		try {
			List<Switches> companies = comm.switchesService.getCompanies();
			List<SwitchesDTO> list = BeanMapper.mapList(companies, SwitchesDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}