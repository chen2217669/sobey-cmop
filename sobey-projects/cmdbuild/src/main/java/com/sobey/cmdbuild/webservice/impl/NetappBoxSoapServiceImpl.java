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
import com.sobey.cmdbuild.entity.NetappBox;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.NetappBoxSoapService;
import com.sobey.cmdbuild.webservice.response.dto.NetappBoxDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "NetappBoxService", endpointInterface = "com.sobey.cmdbuild.webservice.NetappBoxSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NetappBoxSoapServiceImpl extends BasicSoapSevcie implements NetappBoxSoapService {
	@Override
	public DTOResult<NetappBoxDTO> findNetappBox(@WebParam(name = "id") Integer id) {
		DTOResult<NetappBoxDTO> result = new DTOResult<NetappBoxDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NetappBox netappBox = comm.netappBoxService.findNetappBox(id);
			Validate.notNull(netappBox, ERROR.OBJECT_NULL);
			NetappBoxDTO netappBoxDTO = BeanMapper.map(netappBox, NetappBoxDTO.class);
			result.setDto(netappBoxDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NetappBoxDTO> findNetappBoxByCode(@WebParam(name = "code") String code) {
		DTOResult<NetappBoxDTO> result = new DTOResult<NetappBoxDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			NetappBox netappBox = comm.netappBoxService.findByCode(code);
			Validate.notNull(netappBox, ERROR.OBJECT_NULL);
			NetappBoxDTO netappBoxDTO = BeanMapper.map(netappBox, NetappBoxDTO.class);
			result.setDto(netappBoxDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createNetappBox(@WebParam(name = "netappBoxDTO") NetappBoxDTO netappBoxDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(netappBoxDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappBoxService.findByCode(netappBoxDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			NetappBox netappBox = BeanMapper.map(netappBoxDTO, NetappBox.class);
			BeanValidators.validateWithException(validator, netappBox);
			comm.netappBoxService.saveOrUpdate(netappBox);
			return new IdResult(netappBox.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNetappBox(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappBoxDTO") NetappBoxDTO netappBoxDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(netappBoxDTO, ERROR.INPUT_NULL);
			NetappBox netappBox = comm.netappBoxService.findNetappBox(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappBoxService.findByCode(netappBoxDTO.getCode()) == null
					|| netappBox.getCode().equals(netappBoxDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			NetappBox netappBoxEntity = BeanMapper.map(netappBoxDTO, NetappBox.class);
			BeanMapper.copy(netappBoxEntity, netappBox);
			netappBoxEntity.setIdClass(NetappBox.class.getSimpleName());
			netappBoxEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.netappBoxService.saveOrUpdate(netappBoxEntity);
			return new IdResult(netappBox.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNetappBox(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NetappBox netappBox = comm.netappBoxService.findNetappBox(id);
			NetappBox netappBoxEntity = BeanMapper.map(findNetappBox(id).getDto(), NetappBox.class);
			BeanMapper.copy(netappBoxEntity, netappBox);
			netappBoxEntity.setIdClass(NetappBox.class.getSimpleName());
			netappBoxEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.netappBoxService.saveOrUpdate(netappBoxEntity);
			return new IdResult(netappBox.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NetappBoxDTO> getNetappBoxPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<NetappBoxDTO> result = new PaginationResult<NetappBoxDTO>();
		try {
			result = comm.netappBoxService.getNetappBoxDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NetappBoxDTO> getNetappBoxList() {
		DTOListResult<NetappBoxDTO> result = new DTOListResult<NetappBoxDTO>();
		try {
			List<NetappBox> companies = comm.netappBoxService.getCompanies();
			List<NetappBoxDTO> list = BeanMapper.mapList(companies, NetappBoxDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}