package com.sobey.cmdbuild.webservice;

import java.util.List;
import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.entity.Fimas;
import com.sobey.cmdbuild.entity.FimasBox;
import com.sobey.cmdbuild.entity.FimasPort;
import com.sobey.cmdbuild.entity.Firewall;
import com.sobey.cmdbuild.entity.FirewallPort;
import com.sobey.cmdbuild.entity.HardDisk;
import com.sobey.cmdbuild.entity.Ipaddress;
import com.sobey.cmdbuild.entity.LoadBalancer;
import com.sobey.cmdbuild.entity.LoadBalancerPort;
import com.sobey.cmdbuild.entity.Memory;
import com.sobey.cmdbuild.entity.NetappBox;
import com.sobey.cmdbuild.entity.NetappController;
import com.sobey.cmdbuild.entity.NetappPort;
import com.sobey.cmdbuild.entity.Nic;
import com.sobey.cmdbuild.entity.NicPort;
import com.sobey.cmdbuild.entity.Server;
import com.sobey.cmdbuild.entity.ServerPort;
import com.sobey.cmdbuild.entity.SwitchPort;
import com.sobey.cmdbuild.entity.Switches;
import com.sobey.cmdbuild.entity.Vlan;
import com.sobey.cmdbuild.webservice.response.dto.FimasBoxDTO;
import com.sobey.cmdbuild.webservice.response.dto.FimasDTO;
import com.sobey.cmdbuild.webservice.response.dto.FimasPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallDTO;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskDTO;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerDTO;
import com.sobey.cmdbuild.webservice.response.dto.LoadBalancerPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.MemoryDTO;
import com.sobey.cmdbuild.webservice.response.dto.NetappBoxDTO;
import com.sobey.cmdbuild.webservice.response.dto.NetappControllerDTO;
import com.sobey.cmdbuild.webservice.response.dto.NetappPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicDTO;
import com.sobey.cmdbuild.webservice.response.dto.NicPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerDTO;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortDTO;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesDTO;
import com.sobey.cmdbuild.webservice.response.dto.VlanDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.utils.TableNameUtil;

@WebService(serviceName = "InfrastructureService", endpointInterface = "com.sobey.cmdbuild.webservice.InfrastructureSoapService", targetNamespace = WsConstants.NS)
// 查看webservice的日志.
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class InfrastructureSoapServiceImpl extends BasicSoapSevcie implements InfrastructureSoapService {

	/**
	 * CMDBuild的默认超级用户名
	 */
	private static final String DEFAULT_USER = "admin";
	
	@Override
	public DTOResult<FimasDTO> findFimas(@WebParam(name = "id") Integer id) {
		DTOResult<FimasDTO> result = new DTOResult<FimasDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Fimas fimas = comm.fimasService.findFimas(id);

			Validate.notNull(fimas, ERROR.OBJECT_NULL);

			FimasDTO fimasDTO = BeanMapper.map(fimas, FimasDTO.class);

			result.setDto(fimasDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasDTO> findFimasByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<FimasDTO> result = new DTOResult<FimasDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Fimas fimas = comm.fimasService.findFimas(searchParams);

			Validate.notNull(fimas, ERROR.OBJECT_NULL);

			FimasDTO fimasDTO = BeanMapper.map(fimas, FimasDTO.class);

			result.setDto(fimasDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createFimas(@WebParam(name = "fimasDTO") FimasDTO fimasDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", fimasDTO.getCode());

			Validate.isTrue(comm.fimasService.findFimas(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			Fimas fimas = BeanMapper.map(fimasDTO, Fimas.class);

			BeanValidators.validateWithException(validator, fimas);

			comm.fimasService.saveOrUpdate(fimas);

			return new IdResult(fimas.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimas(@WebParam(name = "id") Integer id, @WebParam(name = "fimasDTO") FimasDTO fimasDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasDTO, ERROR.INPUT_NULL);

			Fimas fimas = comm.fimasService.findFimas(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", fimasDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasService.findFimas(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(fimasDTO, Fimas.class), fimas);

			fimas.setIdClass(TableNameUtil.getTableName(Fimas.class));

			fimas.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, fimas);

			comm.fimasService.saveOrUpdate(fimas);

			return new IdResult(fimas.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimas(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Fimas fimas = comm.fimasService.findFimas(id);

			Validate.isTrue(fimas != null, ERROR.OBJECT_NULL);

			fimas.setIdClass(TableNameUtil.getTableName(Fimas.class));

			fimas.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.fimasService.saveOrUpdate(fimas);

			return new IdResult(fimas.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasDTO> getFimasPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FimasDTO> result = new PaginationResult<FimasDTO>();

		try {

			return comm.fimasService.getFimasDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasDTO> getFimasList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<FimasDTO> result = new DTOListResult<FimasDTO>();

		try {

			List<Fimas> fimas = comm.fimasService.getFimasList(searchParams);

			List<FimasDTO> list = BeanMapper.mapList(fimas, FimasDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasBoxDTO> findFimasBox(@WebParam(name = "id") Integer id) {
		DTOResult<FimasBoxDTO> result = new DTOResult<FimasBoxDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FimasBox fimasBox = comm.fimasBoxService.findFimasBox(id);

			Validate.notNull(fimasBox, ERROR.OBJECT_NULL);

			FimasBoxDTO fimasBoxDTO = BeanMapper.map(fimasBox, FimasBoxDTO.class);

			result.setDto(fimasBoxDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasBoxDTO> findFimasBoxByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<FimasBoxDTO> result = new DTOResult<FimasBoxDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			FimasBox fimasBox = comm.fimasBoxService.findFimasBox(searchParams);

			Validate.notNull(fimasBox, ERROR.OBJECT_NULL);

			FimasBoxDTO fimasBoxDTO = BeanMapper.map(fimasBox, FimasBoxDTO.class);

			result.setDto(fimasBoxDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createFimasBox(@WebParam(name = "fimasBoxDTO") FimasBoxDTO fimasBoxDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasBoxDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", fimasBoxDTO.getCode());

			Validate.isTrue(comm.fimasBoxService.findFimasBox(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			FimasBox fimasBox = BeanMapper.map(fimasBoxDTO, FimasBox.class);

			fimasBox.setUser(DEFAULT_USER);
			BeanValidators.validateWithException(validator, fimasBox);

			comm.fimasBoxService.saveOrUpdate(fimasBox);

			return new IdResult(fimasBox.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimasBox(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasBoxDTO") FimasBoxDTO fimasBoxDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasBoxDTO, ERROR.INPUT_NULL);

			FimasBox fimasBox = comm.fimasBoxService.findFimasBox(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", fimasBoxDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasBoxService.findFimasBox(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(fimasBoxDTO, FimasBox.class), fimasBox);

			fimasBox.setIdClass(TableNameUtil.getTableName(FimasBox.class));

			fimasBox.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			fimasBox.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, fimasBox);

			comm.fimasBoxService.saveOrUpdate(fimasBox);

			return new IdResult(fimasBox.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimasBox(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FimasBox fimasBox = comm.fimasBoxService.findFimasBox(id);

			Validate.isTrue(fimasBox != null, ERROR.OBJECT_NULL);

			fimasBox.setIdClass(TableNameUtil.getTableName(FimasBox.class));

			fimasBox.setUser(DEFAULT_USER);
			fimasBox.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.fimasBoxService.saveOrUpdate(fimasBox);

			return new IdResult(fimasBox.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasBoxDTO> getFimasBoxPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FimasBoxDTO> result = new PaginationResult<FimasBoxDTO>();

		try {

			return comm.fimasBoxService.getFimasBoxDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasBoxDTO> getFimasBoxList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<FimasBoxDTO> result = new DTOListResult<FimasBoxDTO>();

		try {

			List<FimasBox> fimasBox = comm.fimasBoxService.getFimasBoxList(searchParams);

			List<FimasBoxDTO> list = BeanMapper.mapList(fimasBox, FimasBoxDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasPortDTO> findFimasPort(@WebParam(name = "id") Integer id) {
		DTOResult<FimasPortDTO> result = new DTOResult<FimasPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FimasPort fimasPort = comm.fimasPortService.findFimasPort(id);

			Validate.notNull(fimasPort, ERROR.OBJECT_NULL);

			FimasPortDTO fimasPortDTO = BeanMapper.map(fimasPort, FimasPortDTO.class);

			result.setDto(fimasPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FimasPortDTO> findFimasPortByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<FimasPortDTO> result = new DTOResult<FimasPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			FimasPort fimasPort = comm.fimasPortService.findFimasPort(searchParams);

			Validate.notNull(fimasPort, ERROR.OBJECT_NULL);

			FimasPortDTO fimasPortDTO = BeanMapper.map(fimasPort, FimasPortDTO.class);

			result.setDto(fimasPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createFimasPort(@WebParam(name = "fimasPortDTO") FimasPortDTO fimasPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", fimasPortDTO.getCode());

			Validate.isTrue(comm.fimasPortService.findFimasPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			FimasPort fimasPort = BeanMapper.map(fimasPortDTO, FimasPort.class);

			BeanValidators.validateWithException(validator, fimasPort);

			comm.fimasPortService.saveOrUpdate(fimasPort);

			return new IdResult(fimasPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFimasPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasPortDTO") FimasPortDTO fimasPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(fimasPortDTO, ERROR.INPUT_NULL);

			FimasPort fimasPort = comm.fimasPortService.findFimasPort(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", fimasPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.fimasPortService.findFimasPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(fimasPortDTO, FimasPort.class), fimasPort);

			fimasPort.setIdClass(TableNameUtil.getTableName(FimasPort.class));

			fimasPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, fimasPort);

			comm.fimasPortService.saveOrUpdate(fimasPort);

			return new IdResult(fimasPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFimasPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FimasPort fimasPort = comm.fimasPortService.findFimasPort(id);

			Validate.isTrue(fimasPort != null, ERROR.OBJECT_NULL);

			fimasPort.setIdClass(TableNameUtil.getTableName(FimasPort.class));

			fimasPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.fimasPortService.saveOrUpdate(fimasPort);

			return new IdResult(fimasPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FimasPortDTO> getFimasPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FimasPortDTO> result = new PaginationResult<FimasPortDTO>();

		try {

			return comm.fimasPortService.getFimasPortDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FimasPortDTO> getFimasPortList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<FimasPortDTO> result = new DTOListResult<FimasPortDTO>();

		try {

			List<FimasPort> fimasPort = comm.fimasPortService.getFimasPortList(searchParams);

			List<FimasPortDTO> list = BeanMapper.mapList(fimasPort, FimasPortDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallDTO> findFirewall(@WebParam(name = "id") Integer id) {
		DTOResult<FirewallDTO> result = new DTOResult<FirewallDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Firewall firewall = comm.firewallService.findFirewall(id);

			Validate.notNull(firewall, ERROR.OBJECT_NULL);

			FirewallDTO firewallDTO = BeanMapper.map(firewall, FirewallDTO.class);

			result.setDto(firewallDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallDTO> findFirewallByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<FirewallDTO> result = new DTOResult<FirewallDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Firewall firewall = comm.firewallService.findFirewall(searchParams);

			Validate.notNull(firewall, ERROR.OBJECT_NULL);

			FirewallDTO firewallDTO = BeanMapper.map(firewall, FirewallDTO.class);

			result.setDto(firewallDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createFirewall(@WebParam(name = "firewallDTO") FirewallDTO firewallDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", firewallDTO.getCode());

			Validate.isTrue(comm.firewallService.findFirewall(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			Firewall firewall = BeanMapper.map(firewallDTO, Firewall.class);

			BeanValidators.validateWithException(validator, firewall);

			comm.firewallService.saveOrUpdate(firewall);

			return new IdResult(firewall.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFirewall(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallDTO") FirewallDTO firewallDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallDTO, ERROR.INPUT_NULL);

			Firewall firewall = comm.firewallService.findFirewall(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", firewallDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallService.findFirewall(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(firewallDTO, Firewall.class), firewall);

			firewall.setIdClass(TableNameUtil.getTableName(Firewall.class));

			firewall.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, firewall);

			comm.firewallService.saveOrUpdate(firewall);

			return new IdResult(firewall.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFirewall(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Firewall firewall = comm.firewallService.findFirewall(id);

			Validate.isTrue(firewall != null, ERROR.OBJECT_NULL);

			firewall.setIdClass(TableNameUtil.getTableName(Firewall.class));

			firewall.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.firewallService.saveOrUpdate(firewall);

			return new IdResult(firewall.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FirewallDTO> getFirewallPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FirewallDTO> result = new PaginationResult<FirewallDTO>();

		try {

			return comm.firewallService.getFirewallDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FirewallDTO> getFirewallList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<FirewallDTO> result = new DTOListResult<FirewallDTO>();

		try {

			List<Firewall> firewall = comm.firewallService.getFirewallList(searchParams);

			List<FirewallDTO> list = BeanMapper.mapList(firewall, FirewallDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallPortDTO> findFirewallPort(@WebParam(name = "id") Integer id) {
		DTOResult<FirewallPortDTO> result = new DTOResult<FirewallPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);

			Validate.notNull(firewallPort, ERROR.OBJECT_NULL);

			FirewallPortDTO firewallPortDTO = BeanMapper.map(firewallPort, FirewallPortDTO.class);

			result.setDto(firewallPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<FirewallPortDTO> findFirewallPortByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<FirewallPortDTO> result = new DTOResult<FirewallPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(searchParams);

			Validate.notNull(firewallPort, ERROR.OBJECT_NULL);

			FirewallPortDTO firewallPortDTO = BeanMapper.map(firewallPort, FirewallPortDTO.class);

			result.setDto(firewallPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createFirewallPort(@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", firewallPortDTO.getCode());

			Validate.isTrue(comm.firewallPortService.findFirewallPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			FirewallPort firewallPort = BeanMapper.map(firewallPortDTO, FirewallPort.class);

			BeanValidators.validateWithException(validator, firewallPort);

			comm.firewallPortService.saveOrUpdate(firewallPort);

			return new IdResult(firewallPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateFirewallPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(firewallPortDTO, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", firewallPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.firewallPortService.findFirewallPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(firewallPortDTO, FirewallPort.class), firewallPort);

			firewallPort.setIdClass(TableNameUtil.getTableName(FirewallPort.class));

			firewallPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, firewallPort);

			comm.firewallPortService.saveOrUpdate(firewallPort);

			return new IdResult(firewallPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteFirewallPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			FirewallPort firewallPort = comm.firewallPortService.findFirewallPort(id);

			Validate.isTrue(firewallPort != null, ERROR.OBJECT_NULL);

			firewallPort.setIdClass(TableNameUtil.getTableName(FirewallPort.class));

			firewallPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.firewallPortService.saveOrUpdate(firewallPort);

			return new IdResult(firewallPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<FirewallPortDTO> getFirewallPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<FirewallPortDTO> result = new PaginationResult<FirewallPortDTO>();

		try {

			return comm.firewallPortService.getFirewallPortDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<FirewallPortDTO> getFirewallPortList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<FirewallPortDTO> result = new DTOListResult<FirewallPortDTO>();

		try {

			List<FirewallPort> firewallPort = comm.firewallPortService.getFirewallPortList(searchParams);

			List<FirewallPortDTO> list = BeanMapper.mapList(firewallPort, FirewallPortDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<HardDiskDTO> findHardDisk(@WebParam(name = "id") Integer id) {
		DTOResult<HardDiskDTO> result = new DTOResult<HardDiskDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			HardDisk hardDisk = comm.hardDiskService.findHardDisk(id);

			Validate.notNull(hardDisk, ERROR.OBJECT_NULL);

			HardDiskDTO hardDiskDTO = BeanMapper.map(hardDisk, HardDiskDTO.class);

			result.setDto(hardDiskDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<HardDiskDTO> findHardDiskByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<HardDiskDTO> result = new DTOResult<HardDiskDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			HardDisk hardDisk = comm.hardDiskService.findHardDisk(searchParams);

			Validate.notNull(hardDisk, ERROR.OBJECT_NULL);

			HardDiskDTO hardDiskDTO = BeanMapper.map(hardDisk, HardDiskDTO.class);

			result.setDto(hardDiskDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createHardDisk(@WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(hardDiskDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", hardDiskDTO.getCode());

			Validate.isTrue(comm.hardDiskService.findHardDisk(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			HardDisk hardDisk = BeanMapper.map(hardDiskDTO, HardDisk.class);

			BeanValidators.validateWithException(validator, hardDisk);

			comm.hardDiskService.saveOrUpdate(hardDisk);

			return new IdResult(hardDisk.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateHardDisk(@WebParam(name = "id") Integer id,
			@WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(hardDiskDTO, ERROR.INPUT_NULL);

			HardDisk hardDisk = comm.hardDiskService.findHardDisk(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", hardDiskDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.hardDiskService.findHardDisk(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(hardDiskDTO, HardDisk.class), hardDisk);

			hardDisk.setIdClass(TableNameUtil.getTableName(HardDisk.class));

			hardDisk.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, hardDisk);

			comm.hardDiskService.saveOrUpdate(hardDisk);

			return new IdResult(hardDisk.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteHardDisk(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			HardDisk hardDisk = comm.hardDiskService.findHardDisk(id);

			Validate.isTrue(hardDisk != null, ERROR.OBJECT_NULL);

			hardDisk.setIdClass(TableNameUtil.getTableName(HardDisk.class));

			hardDisk.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.hardDiskService.saveOrUpdate(hardDisk);

			return new IdResult(hardDisk.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<HardDiskDTO> getHardDiskPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<HardDiskDTO> result = new PaginationResult<HardDiskDTO>();

		try {

			return comm.hardDiskService.getHardDiskDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<HardDiskDTO> getHardDiskList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<HardDiskDTO> result = new DTOListResult<HardDiskDTO>();

		try {

			List<HardDisk> hardDisk = comm.hardDiskService.getHardDiskList(searchParams);

			List<HardDiskDTO> list = BeanMapper.mapList(hardDisk, HardDiskDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

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
	public DTOResult<IpaddressDTO> findIpaddressByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<IpaddressDTO> result = new DTOResult<IpaddressDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(searchParams);

			Validate.notNull(ipaddress, ERROR.OBJECT_NULL);

			IpaddressDTO ipaddressDTO = BeanMapper.map(ipaddress, IpaddressDTO.class);

			result.setDto(ipaddressDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createIpaddress(@WebParam(name = "ipaddressDTO") IpaddressDTO ipaddressDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(ipaddressDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", ipaddressDTO.getCode());

			Validate.isTrue(comm.ipaddressService.findIpaddress(searchParams) == null, ERROR.OBJECT_DUPLICATE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", ipaddressDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.ipaddressService.findIpaddress(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(ipaddressDTO, Ipaddress.class), ipaddress);

			ipaddress.setIdClass(TableNameUtil.getTableName(Ipaddress.class));

			ipaddress.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
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
	public IdResult deleteIpaddress(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Ipaddress ipaddress = comm.ipaddressService.findIpaddress(id);

			Validate.isTrue(ipaddress != null, ERROR.OBJECT_NULL);

			ipaddress.setIdClass(TableNameUtil.getTableName(Ipaddress.class));

			ipaddress.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.ipaddressService.saveOrUpdate(ipaddress);

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

			return comm.ipaddressService.getIpaddressDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<IpaddressDTO> getIpaddressList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<IpaddressDTO> result = new DTOListResult<IpaddressDTO>();

		try {

			List<Ipaddress> ipaddress = comm.ipaddressService.getIpaddressList(searchParams);

			List<IpaddressDTO> list = BeanMapper.mapList(ipaddress, IpaddressDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LoadBalancerDTO> findLoadBalancer(@WebParam(name = "id") Integer id) {
		DTOResult<LoadBalancerDTO> result = new DTOResult<LoadBalancerDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(id);

			Validate.notNull(loadBalancer, ERROR.OBJECT_NULL);

			LoadBalancerDTO loadBalancerDTO = BeanMapper.map(loadBalancer, LoadBalancerDTO.class);

			result.setDto(loadBalancerDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LoadBalancerDTO> findLoadBalancerByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<LoadBalancerDTO> result = new DTOResult<LoadBalancerDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(searchParams);

			Validate.notNull(loadBalancer, ERROR.OBJECT_NULL);

			LoadBalancerDTO loadBalancerDTO = BeanMapper.map(loadBalancer, LoadBalancerDTO.class);

			result.setDto(loadBalancerDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createLoadBalancer(@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", loadBalancerDTO.getCode());

			Validate.isTrue(comm.loadBalancerService.findLoadBalancer(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			LoadBalancer loadBalancer = BeanMapper.map(loadBalancerDTO, LoadBalancer.class);

			BeanValidators.validateWithException(validator, loadBalancer);

			comm.loadBalancerService.saveOrUpdate(loadBalancer);

			return new IdResult(loadBalancer.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateLoadBalancer(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerDTO") LoadBalancerDTO loadBalancerDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerDTO, ERROR.INPUT_NULL);

			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", loadBalancerDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerService.findLoadBalancer(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(loadBalancerDTO, LoadBalancer.class), loadBalancer);

			loadBalancer.setIdClass(TableNameUtil.getTableName(LoadBalancer.class));

			loadBalancer.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, loadBalancer);

			comm.loadBalancerService.saveOrUpdate(loadBalancer);

			return new IdResult(loadBalancer.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteLoadBalancer(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LoadBalancer loadBalancer = comm.loadBalancerService.findLoadBalancer(id);

			Validate.isTrue(loadBalancer != null, ERROR.OBJECT_NULL);

			loadBalancer.setIdClass(TableNameUtil.getTableName(LoadBalancer.class));

			loadBalancer.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.loadBalancerService.saveOrUpdate(loadBalancer);

			return new IdResult(loadBalancer.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LoadBalancerDTO> getLoadBalancerPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<LoadBalancerDTO> result = new PaginationResult<LoadBalancerDTO>();

		try {

			return comm.loadBalancerService.getLoadBalancerDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<LoadBalancerDTO> getLoadBalancerList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<LoadBalancerDTO> result = new DTOListResult<LoadBalancerDTO>();

		try {

			List<LoadBalancer> loadBalancer = comm.loadBalancerService.getLoadBalancerList(searchParams);

			List<LoadBalancerDTO> list = BeanMapper.mapList(loadBalancer, LoadBalancerDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPort(@WebParam(name = "id") Integer id) {
		DTOResult<LoadBalancerPortDTO> result = new DTOResult<LoadBalancerPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);

			Validate.notNull(loadBalancerPort, ERROR.OBJECT_NULL);

			LoadBalancerPortDTO loadBalancerPortDTO = BeanMapper.map(loadBalancerPort, LoadBalancerPortDTO.class);

			result.setDto(loadBalancerPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<LoadBalancerPortDTO> findLoadBalancerPortByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<LoadBalancerPortDTO> result = new DTOResult<LoadBalancerPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(searchParams);

			Validate.notNull(loadBalancerPort, ERROR.OBJECT_NULL);

			LoadBalancerPortDTO loadBalancerPortDTO = BeanMapper.map(loadBalancerPort, LoadBalancerPortDTO.class);

			result.setDto(loadBalancerPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createLoadBalancerPort(
			@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", loadBalancerPortDTO.getCode());

			Validate.isTrue(comm.loadBalancerPortService.findLoadBalancerPort(searchParams) == null,
					ERROR.OBJECT_DUPLICATE);

			LoadBalancerPort loadBalancerPort = BeanMapper.map(loadBalancerPortDTO, LoadBalancerPort.class);

			BeanValidators.validateWithException(validator, loadBalancerPort);

			comm.loadBalancerPortService.saveOrUpdate(loadBalancerPort);

			return new IdResult(loadBalancerPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateLoadBalancerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "loadBalancerPortDTO") LoadBalancerPortDTO loadBalancerPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(loadBalancerPortDTO, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", loadBalancerPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.loadBalancerPortService.findLoadBalancerPort(searchParams) == null,
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(loadBalancerPortDTO, LoadBalancerPort.class), loadBalancerPort);

			loadBalancerPort.setIdClass(TableNameUtil.getTableName(LoadBalancerPort.class));

			loadBalancerPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, loadBalancerPort);

			comm.loadBalancerPortService.saveOrUpdate(loadBalancerPort);

			return new IdResult(loadBalancerPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteLoadBalancerPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			LoadBalancerPort loadBalancerPort = comm.loadBalancerPortService.findLoadBalancerPort(id);

			Validate.isTrue(loadBalancerPort != null, ERROR.OBJECT_NULL);

			loadBalancerPort.setIdClass(TableNameUtil.getTableName(LoadBalancerPort.class));

			loadBalancerPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.loadBalancerPortService.saveOrUpdate(loadBalancerPort);

			return new IdResult(loadBalancerPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<LoadBalancerPortDTO> getLoadBalancerPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<LoadBalancerPortDTO> result = new PaginationResult<LoadBalancerPortDTO>();

		try {

			return comm.loadBalancerPortService.getLoadBalancerPortDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<LoadBalancerPortDTO> getLoadBalancerPortList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<LoadBalancerPortDTO> result = new DTOListResult<LoadBalancerPortDTO>();

		try {

			List<LoadBalancerPort> loadBalancerPort = comm.loadBalancerPortService
					.getLoadBalancerPortList(searchParams);

			List<LoadBalancerPortDTO> list = BeanMapper.mapList(loadBalancerPort, LoadBalancerPortDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MemoryDTO> findMemory(@WebParam(name = "id") Integer id) {
		DTOResult<MemoryDTO> result = new DTOResult<MemoryDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(id);

			Validate.notNull(memory, ERROR.OBJECT_NULL);

			MemoryDTO memoryDTO = BeanMapper.map(memory, MemoryDTO.class);

			result.setDto(memoryDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<MemoryDTO> findMemoryByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<MemoryDTO> result = new DTOResult<MemoryDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(searchParams);

			Validate.notNull(memory, ERROR.OBJECT_NULL);

			MemoryDTO memoryDTO = BeanMapper.map(memory, MemoryDTO.class);

			result.setDto(memoryDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createMemory(@WebParam(name = "memoryDTO") MemoryDTO memoryDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(memoryDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", memoryDTO.getCode());

			Validate.isTrue(comm.memoryService.findMemory(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			Memory memory = BeanMapper.map(memoryDTO, Memory.class);

			BeanValidators.validateWithException(validator, memory);

			comm.memoryService.saveOrUpdate(memory);

			return new IdResult(memory.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateMemory(@WebParam(name = "id") Integer id, @WebParam(name = "memoryDTO") MemoryDTO memoryDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(memoryDTO, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", memoryDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.memoryService.findMemory(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(memoryDTO, Memory.class), memory);

			memory.setIdClass(TableNameUtil.getTableName(Memory.class));

			memory.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, memory);

			comm.memoryService.saveOrUpdate(memory);

			return new IdResult(memory.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteMemory(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Memory memory = comm.memoryService.findMemory(id);

			Validate.isTrue(memory != null, ERROR.OBJECT_NULL);

			memory.setIdClass(TableNameUtil.getTableName(Memory.class));

			memory.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.memoryService.saveOrUpdate(memory);

			return new IdResult(memory.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<MemoryDTO> getMemoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<MemoryDTO> result = new PaginationResult<MemoryDTO>();

		try {

			return comm.memoryService.getMemoryDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<MemoryDTO> getMemoryList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<MemoryDTO> result = new DTOListResult<MemoryDTO>();

		try {

			List<Memory> memory = comm.memoryService.getMemoryList(searchParams);

			List<MemoryDTO> list = BeanMapper.mapList(memory, MemoryDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

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
	public DTOResult<NetappBoxDTO> findNetappBoxByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<NetappBoxDTO> result = new DTOResult<NetappBoxDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			NetappBox netappBox = comm.netappBoxService.findNetappBox(searchParams);

			Validate.notNull(netappBox, ERROR.OBJECT_NULL);

			NetappBoxDTO netappBoxDTO = BeanMapper.map(netappBox, NetappBoxDTO.class);

			result.setDto(netappBoxDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createNetappBox(@WebParam(name = "netappBoxDTO") NetappBoxDTO netappBoxDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(netappBoxDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", netappBoxDTO.getCode());

			Validate.isTrue(comm.netappBoxService.findNetappBox(searchParams) == null, ERROR.OBJECT_DUPLICATE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", netappBoxDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappBoxService.findNetappBox(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(netappBoxDTO, NetappBox.class), netappBox);

			netappBox.setIdClass(TableNameUtil.getTableName(NetappBox.class));

			netappBox.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
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
	public IdResult deleteNetappBox(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			NetappBox netappBox = comm.netappBoxService.findNetappBox(id);

			Validate.isTrue(netappBox != null, ERROR.OBJECT_NULL);

			netappBox.setIdClass(TableNameUtil.getTableName(NetappBox.class));

			netappBox.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.netappBoxService.saveOrUpdate(netappBox);

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

			return comm.netappBoxService.getNetappBoxDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NetappBoxDTO> getNetappBoxList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<NetappBoxDTO> result = new DTOListResult<NetappBoxDTO>();

		try {

			List<NetappBox> netappBox = comm.netappBoxService.getNetappBoxList(searchParams);

			List<NetappBoxDTO> list = BeanMapper.mapList(netappBox, NetappBoxDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NetappControllerDTO> findNetappController(@WebParam(name = "id") Integer id) {
		DTOResult<NetappControllerDTO> result = new DTOResult<NetappControllerDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			NetappController netappController = comm.netappControllerService.findNetappController(id);

			Validate.notNull(netappController, ERROR.OBJECT_NULL);

			NetappControllerDTO netappControllerDTO = BeanMapper.map(netappController, NetappControllerDTO.class);

			result.setDto(netappControllerDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<NetappControllerDTO> findNetappControllerByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<NetappControllerDTO> result = new DTOResult<NetappControllerDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			NetappController netappController = comm.netappControllerService.findNetappController(searchParams);

			Validate.notNull(netappController, ERROR.OBJECT_NULL);

			NetappControllerDTO netappControllerDTO = BeanMapper.map(netappController, NetappControllerDTO.class);

			result.setDto(netappControllerDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createNetappController(
			@WebParam(name = "netappControllerDTO") NetappControllerDTO netappControllerDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(netappControllerDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", netappControllerDTO.getCode());

			Validate.isTrue(comm.netappControllerService.findNetappController(searchParams) == null,
					ERROR.OBJECT_DUPLICATE);

			NetappController netappController = BeanMapper.map(netappControllerDTO, NetappController.class);

			BeanValidators.validateWithException(validator, netappController);

			comm.netappControllerService.saveOrUpdate(netappController);

			return new IdResult(netappController.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateNetappController(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappControllerDTO") NetappControllerDTO netappControllerDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(netappControllerDTO, ERROR.INPUT_NULL);

			NetappController netappController = comm.netappControllerService.findNetappController(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", netappControllerDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappControllerService.findNetappController(searchParams) == null,
					ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(netappControllerDTO, NetappController.class), netappController);

			netappController.setIdClass(TableNameUtil.getTableName(NetappController.class));

			netappController.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, netappController);

			comm.netappControllerService.saveOrUpdate(netappController);

			return new IdResult(netappController.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteNetappController(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			NetappController netappController = comm.netappControllerService.findNetappController(id);

			Validate.isTrue(netappController != null, ERROR.OBJECT_NULL);

			netappController.setIdClass(TableNameUtil.getTableName(NetappController.class));

			netappController.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.netappControllerService.saveOrUpdate(netappController);

			return new IdResult(netappController.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<NetappControllerDTO> getNetappControllerPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<NetappControllerDTO> result = new PaginationResult<NetappControllerDTO>();

		try {

			return comm.netappControllerService.getNetappControllerDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NetappControllerDTO> getNetappControllerList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<NetappControllerDTO> result = new DTOListResult<NetappControllerDTO>();

		try {

			List<NetappController> netappController = comm.netappControllerService
					.getNetappControllerList(searchParams);

			List<NetappControllerDTO> list = BeanMapper.mapList(netappController, NetappControllerDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

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
	public DTOResult<NetappPortDTO> findNetappPortByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<NetappPortDTO> result = new DTOResult<NetappPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			NetappPort netappPort = comm.netappPortService.findNetappPort(searchParams);

			Validate.notNull(netappPort, ERROR.OBJECT_NULL);

			NetappPortDTO netappPortDTO = BeanMapper.map(netappPort, NetappPortDTO.class);

			result.setDto(netappPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createNetappPort(@WebParam(name = "netappPortDTO") NetappPortDTO netappPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(netappPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", netappPortDTO.getCode());

			Validate.isTrue(comm.netappPortService.findNetappPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", netappPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappPortService.findNetappPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(netappPortDTO, NetappPort.class), netappPort);

			netappPort.setIdClass(TableNameUtil.getTableName(NetappPort.class));

			netappPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
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
	public IdResult deleteNetappPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			NetappPort netappPort = comm.netappPortService.findNetappPort(id);

			Validate.isTrue(netappPort != null, ERROR.OBJECT_NULL);

			netappPort.setIdClass(TableNameUtil.getTableName(NetappPort.class));

			netappPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.netappPortService.saveOrUpdate(netappPort);

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

			return comm.netappPortService.getNetappPortDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NetappPortDTO> getNetappPortList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<NetappPortDTO> result = new DTOListResult<NetappPortDTO>();

		try {

			List<NetappPort> netappPort = comm.netappPortService.getNetappPortList(searchParams);

			List<NetappPortDTO> list = BeanMapper.mapList(netappPort, NetappPortDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

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
	public DTOResult<NicDTO> findNicByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<NicDTO> result = new DTOResult<NicDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Nic nic = comm.nicService.findNic(searchParams);

			Validate.notNull(nic, ERROR.OBJECT_NULL);

			NicDTO nicDTO = BeanMapper.map(nic, NicDTO.class);

			result.setDto(nicDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createNic(@WebParam(name = "nicDTO") NicDTO nicDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(nicDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", nicDTO.getCode());

			Validate.isTrue(comm.nicService.findNic(searchParams) == null, ERROR.OBJECT_DUPLICATE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", nicDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.nicService.findNic(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(nicDTO, Nic.class), nic);

			nic.setIdClass(TableNameUtil.getTableName(Nic.class));

			nic.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
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
	public IdResult deleteNic(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Nic nic = comm.nicService.findNic(id);

			Validate.isTrue(nic != null, ERROR.OBJECT_NULL);

			nic.setIdClass(TableNameUtil.getTableName(Nic.class));

			nic.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.nicService.saveOrUpdate(nic);

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

			return comm.nicService.getNicDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NicDTO> getNicList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<NicDTO> result = new DTOListResult<NicDTO>();

		try {

			List<Nic> nic = comm.nicService.getNicList(searchParams);

			List<NicDTO> list = BeanMapper.mapList(nic, NicDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

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
	public DTOResult<NicPortDTO> findNicPortByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<NicPortDTO> result = new DTOResult<NicPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			NicPort nicPort = comm.nicPortService.findNicPort(searchParams);

			Validate.notNull(nicPort, ERROR.OBJECT_NULL);

			NicPortDTO nicPortDTO = BeanMapper.map(nicPort, NicPortDTO.class);

			result.setDto(nicPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createNicPort(@WebParam(name = "nicPortDTO") NicPortDTO nicPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(nicPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", nicPortDTO.getCode());

			Validate.isTrue(comm.nicPortService.findNicPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", nicPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.nicPortService.findNicPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(nicPortDTO, NicPort.class), nicPort);

			nicPort.setIdClass(TableNameUtil.getTableName(NicPort.class));

			nicPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
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
	public IdResult deleteNicPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			NicPort nicPort = comm.nicPortService.findNicPort(id);

			Validate.isTrue(nicPort != null, ERROR.OBJECT_NULL);

			nicPort.setIdClass(TableNameUtil.getTableName(NicPort.class));

			nicPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.nicPortService.saveOrUpdate(nicPort);

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

			return comm.nicPortService.getNicPortDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NicPortDTO> getNicPortList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<NicPortDTO> result = new DTOListResult<NicPortDTO>();

		try {

			List<NicPort> nicPort = comm.nicPortService.getNicPortList(searchParams);

			List<NicPortDTO> list = BeanMapper.mapList(nicPort, NicPortDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerDTO> findServer(@WebParam(name = "id") Integer id) {
		DTOResult<ServerDTO> result = new DTOResult<ServerDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Server server = comm.serverService.findServer(id);

			Validate.notNull(server, ERROR.OBJECT_NULL);

			ServerDTO serverDTO = BeanMapper.map(server, ServerDTO.class);

			result.setDto(serverDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerDTO> findServerByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<ServerDTO> result = new DTOResult<ServerDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Server server = comm.serverService.findServer(searchParams);

			Validate.notNull(server, ERROR.OBJECT_NULL);

			ServerDTO serverDTO = BeanMapper.map(server, ServerDTO.class);

			result.setDto(serverDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createServer(@WebParam(name = "serverDTO") ServerDTO serverDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(serverDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", serverDTO.getCode());

			Validate.isTrue(comm.serverService.findServer(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			Server server = BeanMapper.map(serverDTO, Server.class);

			BeanValidators.validateWithException(validator, server);

			comm.serverService.saveOrUpdate(server);

			return new IdResult(server.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateServer(@WebParam(name = "id") Integer id, @WebParam(name = "serverDTO") ServerDTO serverDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(serverDTO, ERROR.INPUT_NULL);

			Server server = comm.serverService.findServer(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", serverDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.serverService.findServer(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(serverDTO, Server.class), server);

			server.setIdClass(TableNameUtil.getTableName(Server.class));

			server.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, server);

			comm.serverService.saveOrUpdate(server);

			return new IdResult(server.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteServer(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Server server = comm.serverService.findServer(id);

			Validate.isTrue(server != null, ERROR.OBJECT_NULL);

			server.setIdClass(TableNameUtil.getTableName(Server.class));

			server.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.serverService.saveOrUpdate(server);

			return new IdResult(server.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ServerDTO> getServerPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ServerDTO> result = new PaginationResult<ServerDTO>();

		try {

			return comm.serverService.getServerDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ServerDTO> getServerList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<ServerDTO> result = new DTOListResult<ServerDTO>();

		try {

			List<Server> server = comm.serverService.getServerList(searchParams);

			List<ServerDTO> list = BeanMapper.mapList(server, ServerDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerPortDTO> findServerPort(@WebParam(name = "id") Integer id) {
		DTOResult<ServerPortDTO> result = new DTOResult<ServerPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(id);

			Validate.notNull(serverPort, ERROR.OBJECT_NULL);

			ServerPortDTO serverPortDTO = BeanMapper.map(serverPort, ServerPortDTO.class);

			result.setDto(serverPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ServerPortDTO> findServerPortByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<ServerPortDTO> result = new DTOResult<ServerPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(searchParams);

			Validate.notNull(serverPort, ERROR.OBJECT_NULL);

			ServerPortDTO serverPortDTO = BeanMapper.map(serverPort, ServerPortDTO.class);

			result.setDto(serverPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createServerPort(@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(serverPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", serverPortDTO.getCode());

			Validate.isTrue(comm.serverPortService.findServerPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			ServerPort serverPort = BeanMapper.map(serverPortDTO, ServerPort.class);

			BeanValidators.validateWithException(validator, serverPort);

			comm.serverPortService.saveOrUpdate(serverPort);

			return new IdResult(serverPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateServerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(serverPortDTO, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", serverPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.serverPortService.findServerPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(serverPortDTO, ServerPort.class), serverPort);

			serverPort.setIdClass(TableNameUtil.getTableName(ServerPort.class));

			serverPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, serverPort);

			comm.serverPortService.saveOrUpdate(serverPort);

			return new IdResult(serverPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteServerPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			ServerPort serverPort = comm.serverPortService.findServerPort(id);

			Validate.isTrue(serverPort != null, ERROR.OBJECT_NULL);

			serverPort.setIdClass(TableNameUtil.getTableName(ServerPort.class));

			serverPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.serverPortService.saveOrUpdate(serverPort);

			return new IdResult(serverPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<ServerPortDTO> getServerPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<ServerPortDTO> result = new PaginationResult<ServerPortDTO>();

		try {

			return comm.serverPortService.getServerPortDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ServerPortDTO> getServerPortList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<ServerPortDTO> result = new DTOListResult<ServerPortDTO>();

		try {

			List<ServerPort> serverPort = comm.serverPortService.getServerPortList(searchParams);

			List<ServerPortDTO> list = BeanMapper.mapList(serverPort, ServerPortDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

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
	public DTOResult<SwitchesDTO> findSwitchesByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<SwitchesDTO> result = new DTOResult<SwitchesDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Switches switches = comm.switchesService.findSwitches(searchParams);

			Validate.notNull(switches, ERROR.OBJECT_NULL);

			SwitchesDTO switchesDTO = BeanMapper.map(switches, SwitchesDTO.class);

			result.setDto(switchesDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createSwitches(@WebParam(name = "switchesDTO") SwitchesDTO switchesDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(switchesDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", switchesDTO.getCode());

			Validate.isTrue(comm.switchesService.findSwitches(searchParams) == null, ERROR.OBJECT_DUPLICATE);

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

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", switchesDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.switchesService.findSwitches(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(switchesDTO, Switches.class), switches);

			switches.setIdClass(TableNameUtil.getTableName(Switches.class));

			switches.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
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
	public IdResult deleteSwitches(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Switches switches = comm.switchesService.findSwitches(id);

			Validate.isTrue(switches != null, ERROR.OBJECT_NULL);

			switches.setIdClass(TableNameUtil.getTableName(Switches.class));

			switches.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.switchesService.saveOrUpdate(switches);

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

			return comm.switchesService.getSwitchesDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<SwitchesDTO> getSwitchesList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<SwitchesDTO> result = new DTOListResult<SwitchesDTO>();

		try {

			List<Switches> switches = comm.switchesService.getSwitchesList(searchParams);

			List<SwitchesDTO> list = BeanMapper.mapList(switches, SwitchesDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<SwitchPortDTO> findSwitchPort(@WebParam(name = "id") Integer id) {
		DTOResult<SwitchPortDTO> result = new DTOResult<SwitchPortDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(id);

			Validate.notNull(switchPort, ERROR.OBJECT_NULL);

			SwitchPortDTO switchPortDTO = BeanMapper.map(switchPort, SwitchPortDTO.class);

			result.setDto(switchPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<SwitchPortDTO> findSwitchPortByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<SwitchPortDTO> result = new DTOResult<SwitchPortDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(searchParams);

			Validate.notNull(switchPort, ERROR.OBJECT_NULL);

			SwitchPortDTO switchPortDTO = BeanMapper.map(switchPort, SwitchPortDTO.class);

			result.setDto(switchPortDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createSwitchPort(@WebParam(name = "switchPortDTO") SwitchPortDTO switchPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(switchPortDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", switchPortDTO.getCode());

			Validate.isTrue(comm.switchPortService.findSwitchPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			SwitchPort switchPort = BeanMapper.map(switchPortDTO, SwitchPort.class);

			BeanValidators.validateWithException(validator, switchPort);

			comm.switchPortService.saveOrUpdate(switchPort);

			return new IdResult(switchPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateSwitchPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "switchPortDTO") SwitchPortDTO switchPortDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(switchPortDTO, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", switchPortDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.switchPortService.findSwitchPort(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(switchPortDTO, SwitchPort.class), switchPort);

			switchPort.setIdClass(TableNameUtil.getTableName(SwitchPort.class));

			switchPort.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, switchPort);

			comm.switchPortService.saveOrUpdate(switchPort);

			return new IdResult(switchPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteSwitchPort(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			SwitchPort switchPort = comm.switchPortService.findSwitchPort(id);

			Validate.isTrue(switchPort != null, ERROR.OBJECT_NULL);

			switchPort.setIdClass(TableNameUtil.getTableName(SwitchPort.class));

			switchPort.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.switchPortService.saveOrUpdate(switchPort);

			return new IdResult(switchPort.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<SwitchPortDTO> getSwitchPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<SwitchPortDTO> result = new PaginationResult<SwitchPortDTO>();

		try {

			return comm.switchPortService.getSwitchPortDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<SwitchPortDTO> getSwitchPortList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<SwitchPortDTO> result = new DTOListResult<SwitchPortDTO>();

		try {

			List<SwitchPort> switchPort = comm.switchPortService.getSwitchPortList(searchParams);

			List<SwitchPortDTO> list = BeanMapper.mapList(switchPort, SwitchPortDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<VlanDTO> findVlan(@WebParam(name = "id") Integer id) {
		DTOResult<VlanDTO> result = new DTOResult<VlanDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Vlan vlan = comm.vlanService.findVlan(id);

			Validate.notNull(vlan, ERROR.OBJECT_NULL);

			VlanDTO vlanDTO = BeanMapper.map(vlan, VlanDTO.class);

			result.setDto(vlanDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<VlanDTO> findVlanByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOResult<VlanDTO> result = new DTOResult<VlanDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Vlan vlan = comm.vlanService.findVlan(searchParams);

			Validate.notNull(vlan, ERROR.OBJECT_NULL);

			VlanDTO vlanDTO = BeanMapper.map(vlan, VlanDTO.class);

			result.setDto(vlanDTO);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createVlan(@WebParam(name = "vlanDTO") VlanDTO vlanDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(vlanDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", vlanDTO.getCode());

			Validate.isTrue(comm.vlanService.findVlan(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			Vlan vlan = BeanMapper.map(vlanDTO, Vlan.class);

			BeanValidators.validateWithException(validator, vlan);

			comm.vlanService.saveOrUpdate(vlan);

			return new IdResult(vlan.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateVlan(@WebParam(name = "id") Integer id, @WebParam(name = "vlanDTO") VlanDTO vlanDTO) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(vlanDTO, ERROR.INPUT_NULL);

			Vlan vlan = comm.vlanService.findVlan(id);

			Map<String, Object> searchParams = Maps.newHashMap();

			searchParams.put("EQ_code", vlanDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.vlanService.findVlan(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(vlanDTO, Vlan.class), vlan);

			vlan.setIdClass(TableNameUtil.getTableName(Vlan.class));

			vlan.setStatus(CMDBuildConstants.STATUS_ACTIVE);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, vlan);

			comm.vlanService.saveOrUpdate(vlan);

			return new IdResult(vlan.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteVlan(@WebParam(name = "id") Integer id) {
		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Vlan vlan = comm.vlanService.findVlan(id);

			Validate.isTrue(vlan != null, ERROR.OBJECT_NULL);

			vlan.setIdClass(TableNameUtil.getTableName(Vlan.class));

			vlan.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.vlanService.saveOrUpdate(vlan);

			return new IdResult(vlan.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<VlanDTO> getVlanPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<VlanDTO> result = new PaginationResult<VlanDTO>();

		try {

			return comm.vlanService.getVlanDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<VlanDTO> getVlanList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<VlanDTO> result = new DTOListResult<VlanDTO>();

		try {

			List<Vlan> vlan = comm.vlanService.getVlanList(searchParams);

			List<VlanDTO> list = BeanMapper.mapList(vlan, VlanDTO.class);

			result.setDtos(list);

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult allocateIPAddress(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdResult> insertIPAddress(List<IpaddressDTO> ipaddressDTOList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IdResult initIPAddress(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IdResult> insertVlan(List<VlanDTO> vlanDTOList) {
		// TODO Auto-generated method stub
		return null;
	}
}
