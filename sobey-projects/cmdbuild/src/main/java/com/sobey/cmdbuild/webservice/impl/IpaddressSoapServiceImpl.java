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
import com.sobey.cmdbuild.entity.Ipaddress;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.IpaddressSoapService;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "IpaddressService", endpointInterface = "com.sobey.cmdbuild.webservice.IpaddressSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class IpaddressSoapServiceImpl extends BasicSoapSevcie implements IpaddressSoapService {
	@Override
	public DTOResult<IpaddressDTO> findIpaddress(@WebParam(name = "id") Integer id) {
		DTOResult<IpaddressDTO> result = new DTOResult<IpaddressDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(id);
			Validate.notNull(ipaddress, ERROR.OBJECT_NULL);
			IpaddressDTO ipaddressDTO = BeanMapper.map(ipaddress, IpaddressDTO.class);
			result.setDto(ipaddressDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<IpaddressDTO> findIpaddressByCode(@WebParam(name = "code") String code) {
		DTOResult<IpaddressDTO> result = new DTOResult<IpaddressDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Ipaddress ipaddress = comm.ipaddressService.findByCode(code);
			Validate.notNull(ipaddress, ERROR.OBJECT_NULL);
			IpaddressDTO ipaddressDTO = BeanMapper.map(ipaddress, IpaddressDTO.class);
			result.setDto(ipaddressDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createIpaddress(@WebParam(name = "ipaddressDTO") IpaddressDTO ipaddressDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ipaddressDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ipaddressService.findByCode(ipaddressDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Ipaddress ipaddress = BeanMapper.map(ipaddressDTO, Ipaddress.class);
			BeanValidators.validateWithException(validator, ipaddress);
			comm.ipaddressService.saveOrUpdate(ipaddress);
			return new IdResult(ipaddress.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateIpaddress(@WebParam(name = "id") Integer id,
			@WebParam(name = "ipaddressDTO") IpaddressDTO ipaddressDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(ipaddressDTO, ERROR.INPUT_NULL);
			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ipaddressService.findByCode(ipaddressDTO.getCode()) == null
					|| ipaddress.getCode().equals(ipaddressDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			Ipaddress ipaddressEntity = BeanMapper.map(ipaddressDTO, Ipaddress.class);
			BeanMapper.copy(ipaddressEntity, ipaddress);
			ipaddressEntity.setIdClass(Ipaddress.class.getSimpleName());
			ipaddressEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.ipaddressService.saveOrUpdate(ipaddressEntity);
			return new IdResult(ipaddress.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteIpaddress(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(id);
			Ipaddress ipaddressEntity = BeanMapper.map(findIpaddress(id).getDto(), Ipaddress.class);
			BeanMapper.copy(ipaddressEntity, ipaddress);
			ipaddressEntity.setIdClass(Ipaddress.class.getSimpleName());
			ipaddressEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.ipaddressService.saveOrUpdate(ipaddressEntity);
			return new IdResult(ipaddress.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<IpaddressDTO> getIpaddressPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<IpaddressDTO> result = new PaginationResult<IpaddressDTO>();
		try {
			result = comm.ipaddressService.getIpaddressDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<IpaddressDTO> getIpaddressList() {
		DTOListResult<IpaddressDTO> result = new DTOListResult<IpaddressDTO>();
		try {
			List<Ipaddress> companies = comm.ipaddressService.getCompanies();
			List<IpaddressDTO> list = BeanMapper.mapList(companies, IpaddressDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}