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
import com.sobey.cmdbuild.entity.Nic;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.NicSoapService;
import com.sobey.cmdbuild.webservice.response.dto.NicDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "NicService", endpointInterface = "com.sobey.cmdbuild.webservice.NicSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NicSoapServiceImpl extends BasicSoapSevcie implements NicSoapService {
	@Override
	public DTOResult<NicDTO> findNic(@WebParam(name = "id") Integer id) {
		DTOResult<NicDTO> result = new DTOResult<NicDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Nic nic = comm.nicService.findNic(id);
			Validate.notNull(nic, ERROR.OBJECT_NULL);
			NicDTO nicDTO = BeanMapper.map(nic, NicDTO.class);
			result.setDto(nicDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NicDTO> findNicByCode(@WebParam(name = "code") String code) {
		DTOResult<NicDTO> result = new DTOResult<NicDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Nic nic = comm.nicService.findByCode(code);
			Validate.notNull(nic, ERROR.OBJECT_NULL);
			NicDTO nicDTO = BeanMapper.map(nic, NicDTO.class);
			result.setDto(nicDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createNic(@WebParam(name = "nicDTO") NicDTO nicDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(nicDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.nicService.findByCode(nicDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Nic nic = BeanMapper.map(nicDTO, Nic.class);
			BeanValidators.validateWithException(validator, nic);
			comm.nicService.saveOrUpdate(nic);
			return new IdResult(nic.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNic(@WebParam(name = "id") Integer id, @WebParam(name = "nicDTO") NicDTO nicDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(nicDTO, ERROR.INPUT_NULL);
			Nic nic = comm.nicService.findNic(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.nicService.findByCode(nicDTO.getCode()) == null || nic.getCode().equals(nicDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Nic nicEntity = BeanMapper.map(nicDTO, Nic.class);
			BeanMapper.copy(nicEntity, nic);
			nicEntity.setIdClass(Nic.class.getSimpleName());
			nicEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.nicService.saveOrUpdate(nicEntity);
			return new IdResult(nic.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNic(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Nic nic = comm.nicService.findNic(id);
			Nic nicEntity = BeanMapper.map(findNic(id).getDto(), Nic.class);
			BeanMapper.copy(nicEntity, nic);
			nicEntity.setIdClass(Nic.class.getSimpleName());
			nicEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.nicService.saveOrUpdate(nicEntity);
			return new IdResult(nic.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NicDTO> getNicPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<NicDTO> result = new PaginationResult<NicDTO>();
		try {
			result = comm.nicService.getNicDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NicDTO> getNicList() {
		DTOListResult<NicDTO> result = new DTOListResult<NicDTO>();
		try {
			List<Nic> companies = comm.nicService.getCompanies();
			List<NicDTO> list = BeanMapper.mapList(companies, NicDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}