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
import com.sobey.cmdbuild.entity.NicPort;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.NicPortSoapService;
import com.sobey.cmdbuild.webservice.response.dto.NicPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "NicPortService", endpointInterface = "com.sobey.cmdbuild.webservice.NicPortSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NicPortSoapServiceImpl extends BasicSoapSevcie implements NicPortSoapService {
	@Override
	public DTOResult<NicPortDTO> findNicPort(@WebParam(name = "id") Integer id) {
		DTOResult<NicPortDTO> result = new DTOResult<NicPortDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NicPort nicPort = comm.nicPortService.findNicPort(id);
			Validate.notNull(nicPort, ERROR.OBJECT_NULL);
			NicPortDTO nicPortDTO = BeanMapper.map(nicPort, NicPortDTO.class);
			result.setDto(nicPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NicPortDTO> findNicPortByCode(@WebParam(name = "code") String code) {
		DTOResult<NicPortDTO> result = new DTOResult<NicPortDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			NicPort nicPort = comm.nicPortService.findByCode(code);
			Validate.notNull(nicPort, ERROR.OBJECT_NULL);
			NicPortDTO nicPortDTO = BeanMapper.map(nicPort, NicPortDTO.class);
			result.setDto(nicPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createNicPort(@WebParam(name = "nicPortDTO") NicPortDTO nicPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(nicPortDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.nicPortService.findByCode(nicPortDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			NicPort nicPort = BeanMapper.map(nicPortDTO, NicPort.class);
			BeanValidators.validateWithException(validator, nicPort);
			comm.nicPortService.saveOrUpdate(nicPort);
			return new IdResult(nicPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNicPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "nicPortDTO") NicPortDTO nicPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(nicPortDTO, ERROR.INPUT_NULL);
			NicPort nicPort = comm.nicPortService.findNicPort(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.nicPortService.findByCode(nicPortDTO.getCode()) == null
							|| nicPort.getCode().equals(nicPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			NicPort nicPortEntity = BeanMapper.map(nicPortDTO, NicPort.class);
			BeanMapper.copy(nicPortEntity, nicPort);
			nicPortEntity.setIdClass(NicPort.class.getSimpleName());
			nicPortEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.nicPortService.saveOrUpdate(nicPortEntity);
			return new IdResult(nicPort.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNicPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NicPort nicPort = comm.nicPortService.findNicPort(id);
			NicPort nicPortEntity = BeanMapper.map(findNicPort(id).getDto(), NicPort.class);
			BeanMapper.copy(nicPortEntity, nicPort);
			nicPortEntity.setIdClass(NicPort.class.getSimpleName());
			nicPortEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.nicPortService.saveOrUpdate(nicPortEntity);
			return new IdResult(nicPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NicPortDTO> getNicPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<NicPortDTO> result = new PaginationResult<NicPortDTO>();
		try {
			result = comm.nicPortService.getNicPortDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NicPortDTO> getNicPortList() {
		DTOListResult<NicPortDTO> result = new DTOListResult<NicPortDTO>();
		try {
			List<NicPort> companies = comm.nicPortService.getCompanies();
			List<NicPortDTO> list = BeanMapper.mapList(companies, NicPortDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}