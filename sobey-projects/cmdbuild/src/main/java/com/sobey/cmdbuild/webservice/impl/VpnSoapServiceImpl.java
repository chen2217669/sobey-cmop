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
import com.sobey.cmdbuild.entity.Vpn;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.VpnSoapService;
import com.sobey.cmdbuild.webservice.response.dto.VpnDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "VpnService", endpointInterface = "com.sobey.cmdbuild.webservice.VpnSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class VpnSoapServiceImpl extends BasicSoapSevcie implements VpnSoapService {
	@Override
	public DTOResult<VpnDTO> findVpn(@WebParam(name = "id") Integer id) {
		DTOResult<VpnDTO> result = new DTOResult<VpnDTO>();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Vpn vpn = comm.vpnService.findVpn(id);
			Validate.notNull(vpn, ERROR.OBJECT_NULL);
			VpnDTO vpnDTO = BeanMapper.map(vpn, VpnDTO.class);
			result.setDto(vpnDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<VpnDTO> findVpnByCode(@WebParam(name = "code") String code) {
		DTOResult<VpnDTO> result = new DTOResult<VpnDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			Vpn vpn = comm.vpnService.findByCode(code);
			Validate.notNull(vpn, ERROR.OBJECT_NULL);
			VpnDTO vpnDTO = BeanMapper.map(vpn, VpnDTO.class);
			result.setDto(vpnDTO);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult createVpn(@WebParam(name = "vpnDTO") VpnDTO vpnDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(vpnDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.vpnService.findByCode(vpnDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
			Vpn vpn = BeanMapper.map(vpnDTO, Vpn.class);
			BeanValidators.validateWithException(validator, vpn);
			comm.vpnService.saveOrUpdate(vpn);
			return new IdResult(vpn.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateVpn(@WebParam(name = "id") Integer id, @WebParam(name = "vpnDTO") VpnDTO vpnDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(vpnDTO, ERROR.INPUT_NULL);
			Vpn vpn = comm.vpnService.findVpn(id);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(
					comm.vpnService.findByCode(vpnDTO.getCode()) == null || vpn.getCode().equals(vpnDTO.getCode()),
					ERROR.OBJECT_DUPLICATE);
			Vpn vpnEntity = BeanMapper.map(vpnDTO, Vpn.class);
			BeanMapper.copy(vpnEntity, vpn);
			vpnEntity.setIdClass(Vpn.class.getSimpleName());
			vpnEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.vpnService.saveOrUpdate(vpnEntity);
			return new IdResult(vpn.getId());
		} catch (ConstraintViolationException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteVpn(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(id, ERROR.INPUT_NULL);
			Vpn vpn = comm.vpnService.findVpn(id);
			Vpn vpnEntity = BeanMapper.map(findVpn(id).getDto(), Vpn.class);
			BeanMapper.copy(vpnEntity, vpn);
			vpnEntity.setIdClass(Vpn.class.getSimpleName());
			vpnEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.vpnService.saveOrUpdate(vpnEntity);
			return new IdResult(vpn.getId());
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<VpnDTO> getVpnPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<VpnDTO> result = new PaginationResult<VpnDTO>();
		try {
			result = comm.vpnService.getVpnDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<VpnDTO> getVpnList() {
		DTOListResult<VpnDTO> result = new DTOListResult<VpnDTO>();
		try {
			List<Vpn> companies = comm.vpnService.getCompanies();
			List<VpnDTO> list = BeanMapper.mapList(companies, VpnDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}