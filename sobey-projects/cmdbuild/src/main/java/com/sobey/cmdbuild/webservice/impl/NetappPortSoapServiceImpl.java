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
import com.sobey.cmdbuild.entity.NetappPort;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.NetappPortSoapService;
import com.sobey.cmdbuild.webservice.response.dto.NetappPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "NetappPortService", endpointInterface = "com.sobey.cmdbuild.webservice.NetappPortSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NetappPortSoapServiceImpl extends BasicSoapSevcie implements NetappPortSoapService {
	@Override
	public DTOResult<NetappPortDTO> findNetappPort(@WebParam(name = "id") Integer id) {
		DTOResult<NetappPortDTO> result = new DTOResult<NetappPortDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NetappPort netappPort = comm.netappPortService.findNetappPort(id);
			Validate.notNull(netappPort, ERROR.OBJECT_NULL);
			NetappPortDTO netappPortDTO = BeanMapper.map(netappPort, NetappPortDTO.class);
			result.setDto(netappPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NetappPortDTO> findNetappPortByCode(@WebParam(name = "code") String code) {
		DTOResult<NetappPortDTO> result = new DTOResult<NetappPortDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			NetappPort netappPort = comm.netappPortService.findByCode(code);
			Validate.notNull(netappPort, ERROR.OBJECT_NULL);
			NetappPortDTO netappPortDTO = BeanMapper.map(netappPort, NetappPortDTO.class);
			result.setDto(netappPortDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createNetappPort(@WebParam(name = "netappPortDTO") NetappPortDTO netappPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(netappPortDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappPortService.findByCode(netappPortDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			NetappPort netappPort = BeanMapper.map(netappPortDTO, NetappPort.class);
			BeanValidators.validateWithException(validator, netappPort);
			comm.netappPortService.saveOrUpdate(netappPort);
			return new IdResult(netappPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNetappPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappPortDTO") NetappPortDTO netappPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(netappPortDTO, ERROR.INPUT_NULL);
			NetappPort netappPort = comm.netappPortService.findNetappPort(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappPortService.findByCode(netappPortDTO.getCode()) == null
					|| netappPort.getCode().equals(netappPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			NetappPort netappPortEntity = BeanMapper.map(netappPortDTO, NetappPort.class);
			BeanMapper.copy(netappPortEntity, netappPort);
			netappPortEntity.setIdClass(NetappPort.class.getSimpleName());
			netappPortEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.netappPortService.saveOrUpdate(netappPortEntity);
			return new IdResult(netappPort.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNetappPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			NetappPort netappPort = comm.netappPortService.findNetappPort(id);
			NetappPort netappPortEntity = BeanMapper.map(findNetappPort(id).getDto(), NetappPort.class);
			BeanMapper.copy(netappPortEntity, netappPort);
			netappPortEntity.setIdClass(NetappPort.class.getSimpleName());
			netappPortEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.netappPortService.saveOrUpdate(netappPortEntity);
			return new IdResult(netappPort.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NetappPortDTO> getNetappPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<NetappPortDTO> result = new PaginationResult<NetappPortDTO>();
		try {
			result = comm.netappPortService.getNetappPortDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NetappPortDTO> getNetappPortList() {
		DTOListResult<NetappPortDTO> result = new DTOListResult<NetappPortDTO>();
		try {
			List<NetappPort> companies = comm.netappPortService.getCompanies();
			List<NetappPortDTO> list = BeanMapper.mapList(companies, NetappPortDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}