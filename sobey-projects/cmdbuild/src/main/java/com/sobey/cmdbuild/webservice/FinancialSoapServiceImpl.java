package com.sobey.cmdbuild.webservice;

import java.util.Map;

import javax.jws.WebParam;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.cxf.feature.Features;

import com.google.common.collect.Maps;
import com.sobey.cmdbuild.constants.CMDBuildConstants;
import com.sobey.cmdbuild.constants.ERROR;
import com.sobey.cmdbuild.entity.Consumptions;
import com.sobey.cmdbuild.entity.DeviceSpec;
import com.sobey.cmdbuild.entity.EcsSpec;
import com.sobey.cmdbuild.entity.EipSpec;
import com.sobey.cmdbuild.entity.Es3Spec;
import com.sobey.cmdbuild.entity.Tenants;
import com.sobey.cmdbuild.webservice.response.dto.ConsumptionsDTO;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;
import com.sobey.core.utils.MathsUtil;

//@WebService(serviceName = "CmdbuildService", endpointInterface = "com.sobey.cmdbuild.webservice.CmdbuildSoapService", targetNamespace = WsConstants.NS)
//查看webservice的日志.
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class FinancialSoapServiceImpl extends BasicSoapSevcie implements FinancialSoapService {

	/**
	 * CMDBuild的默认超级用户名
	 */
	private static final String DEFAULT_USER = "admin";

	@Override
	public DTOResult<ConsumptionsDTO> findConsumptions(@WebParam(name = "id") Integer id) {

		DTOResult<ConsumptionsDTO> result = new DTOResult<ConsumptionsDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Consumptions consumptions = comm.consumptionsService.findConsumptions(id);

			Validate.notNull(consumptions, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(consumptions, ConsumptionsDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<ConsumptionsDTO> findConsumptionsByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<ConsumptionsDTO> result = new DTOResult<ConsumptionsDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Consumptions consumptions = comm.consumptionsService.findConsumptions(searchParams);

			Validate.notNull(consumptions, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(consumptions, ConsumptionsDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createConsumptions(@WebParam(name = "consumptionsDTO") ConsumptionsDTO consumptionsDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(consumptionsDTO, ERROR.INPUT_NULL);

			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", consumptionsDTO.getCode());

			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.consumptionsService.findConsumptions(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Consumptions consumptions = BeanMapper.map(consumptionsDTO, Consumptions.class);
			consumptions.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, consumptions);

			comm.consumptionsService.saveOrUpdate(consumptions);

			return new IdResult(consumptions.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
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
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", consumptionsDTO.getCode());

			Validate.isTrue(comm.consumptionsService.findConsumptions(searchParams) == null
					|| consumptions.getCode().equals(consumptionsDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(consumptionsDTO, Consumptions.class), consumptions);

			consumptions.setUser(DEFAULT_USER);
			consumptions.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			consumptions.setIdClass(Consumptions.class.getSimpleName());

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, consumptions);

			comm.consumptionsService.saveOrUpdate(consumptions);

			return new IdResult(consumptions.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
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
			consumptions.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.consumptionsService.saveOrUpdate(consumptions);

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
			return comm.consumptionsService.getConsumptionsDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<ConsumptionsDTO> getConsumptionsList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<ConsumptionsDTO> result = new DTOListResult<ConsumptionsDTO>();
		try {
			result.setDtos(BeanMapper.mapList(comm.consumptionsService.getConsumptionsList(searchParams),
					ConsumptionsDTO.class));
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult settleConsumptions(Integer cid, Integer tid) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(cid, ERROR.INPUT_NULL);
			Validate.notNull(tid, ERROR.INPUT_NULL);

			Tenants tenants = comm.tenantsService.findTenants(tid);
			Validate.notNull(tenants, ERROR.OBJECT_NULL);

			Consumptions consumptions = comm.consumptionsService.findConsumptions(cid);

			Validate.notNull(consumptions, ERROR.OBJECT_NULL);

			// 更新租户的余额.
			tenants.setAccontBalance(MathsUtil.sub(tenants.getAccontBalance(), consumptions.getSpending()));

			// TODO更新订单状态为"完成",从lookup中读取"完成"状态的ID.
			consumptions.setConsumptionsStatus(42);

			comm.tenantsService.saveOrUpdate(tenants);

			comm.consumptionsService.saveOrUpdate(consumptions);

			// 租户消费历史

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
		return result;
	}

	@Override
	public DTOListResult<ConsumptionsDTO> reportConsumptions(Map<String, Object> searchParams) {
		// TODO 待完成
		return null;
	}

	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpec(@WebParam(name = "id") Integer id) {

		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(deviceSpec, DeviceSpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<DeviceSpecDTO> findDeviceSpecByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<DeviceSpecDTO> result = new DTOResult<DeviceSpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(searchParams);

			Validate.notNull(deviceSpec, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(deviceSpec, DeviceSpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createDeviceSpec(@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(deviceSpecDTO, ERROR.INPUT_NULL);

			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", deviceSpecDTO.getCode());
			searchParams.put("EQ_deviceType", deviceSpecDTO.getDeviceType());

			// 判断同一个 DeviceType 下是否有相同的 code，如果有相同的 code则不能创建。,则弹出错误.
			Validate.isTrue(comm.deviceSpecService.findDeviceSpec(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			DeviceSpec deviceSpec = BeanMapper.map(deviceSpecDTO, DeviceSpec.class);
			deviceSpec.setUser(DEFAULT_USER);

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, deviceSpec);

			comm.deviceSpecService.saveOrUpdate(deviceSpec);

			return new IdResult(deviceSpec.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateDeviceSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "deviceSpecDTO") DeviceSpecDTO deviceSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(deviceSpecDTO, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);

			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", deviceSpecDTO.getCode());
			searchParams.put("EQ_deviceType", deviceSpecDTO.getDeviceType());

			// 判断同一个 DeviceType 下是否有相同的 code，如果有相同的 code则不能创建。,则弹出错误.
			Validate.isTrue(
					comm.deviceSpecService.findDeviceSpec(searchParams) == null
							|| deviceSpec.getCode().equals(deviceSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(deviceSpecDTO, DeviceSpec.class), deviceSpec);

			deviceSpec.setUser(DEFAULT_USER);
			deviceSpec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			deviceSpec.setIdClass(DeviceSpec.class.getSimpleName());

			// 调用JSR303的validate方法, 验证失败时抛出ConstraintViolationException.
			BeanValidators.validateWithException(validator, deviceSpec);

			comm.deviceSpecService.saveOrUpdate(deviceSpec);

			return new IdResult(deviceSpec.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteDeviceSpec(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			DeviceSpec deviceSpec = comm.deviceSpecService.findDeviceSpec(id);
			deviceSpec.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.deviceSpecService.saveOrUpdate(deviceSpec);

			return new IdResult(deviceSpec.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<DeviceSpecDTO> getDeviceSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<DeviceSpecDTO> result = new PaginationResult<DeviceSpecDTO>();
		try {
			return comm.deviceSpecService.getDeviceSpecDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<DeviceSpecDTO> getDeviceSpecList(
			@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<DeviceSpecDTO> result = new DTOListResult<DeviceSpecDTO>();
		try {
			result.setDtos(BeanMapper.mapList(comm.deviceSpecService.getDeviceSpecList(searchParams),
					DeviceSpecDTO.class));
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsSpecDTO> findEcsSpec(@WebParam(name = "id") Integer id) {

		DTOResult<EcsSpecDTO> result = new DTOResult<EcsSpecDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);

			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(ecsSpec, EcsSpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EcsSpecDTO> findEcsSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<EcsSpecDTO> result = new DTOResult<EcsSpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(searchParams);

			Validate.notNull(ecsSpec, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(ecsSpec, EcsSpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEcsSpec(@WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsSpecDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", ecsSpecDTO.getCode());

			Validate.isTrue(comm.ecsSpecService.findEcsSpec(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			EcsSpec ecsSpec = BeanMapper.map(ecsSpecDTO, EcsSpec.class);
			ecsSpec.setUser(DEFAULT_USER);

			BeanValidators.validateWithException(validator, ecsSpec);

			comm.ecsSpecService.saveOrUpdate(ecsSpec);

			return new IdResult(ecsSpec.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEcsSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(ecsSpecDTO, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", ecsSpecDTO.getCode());

			Validate.isTrue(
					comm.ecsSpecService.findEcsSpec(searchParams) == null
							|| ecsSpec.getCode().equals(ecsSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(ecsSpecDTO, EcsSpec.class), ecsSpec);

			ecsSpec.setUser(DEFAULT_USER);
			ecsSpec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			ecsSpec.setIdClass(EcsSpec.class.getSimpleName());

			BeanValidators.validateWithException(validator, ecsSpec);

			comm.ecsSpecService.saveOrUpdate(ecsSpec);

			return new IdResult(ecsSpec.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEcsSpec(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EcsSpec ecsSpec = comm.ecsSpecService.findEcsSpec(id);
			ecsSpec.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.ecsSpecService.saveOrUpdate(ecsSpec);

			return new IdResult(ecsSpec.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EcsSpecDTO> getEcsSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EcsSpecDTO> result = new PaginationResult<EcsSpecDTO>();
		try {
			return comm.ecsSpecService.getEcsSpecDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EcsSpecDTO> getEcsSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<EcsSpecDTO> result = new DTOListResult<EcsSpecDTO>();
		try {
			result.setDtos(BeanMapper.mapList(comm.ecsSpecService.getEcsSpecList(searchParams), EcsSpecDTO.class));
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipSpecDTO> findEipSpec(@WebParam(name = "id") Integer id) {

		DTOResult<EipSpecDTO> result = new DTOResult<EipSpecDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id);

			Validate.notNull(eipSpec, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(eipSpec, EipSpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<EipSpecDTO> findEipSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<EipSpecDTO> result = new DTOResult<EipSpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			EipSpec eipSpec = comm.eipSpecService.findEipSpec(searchParams);

			Validate.notNull(eipSpec, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(eipSpec, EipSpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEipSpec(@WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipSpecDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", eipSpecDTO.getCode());

			Validate.isTrue(comm.eipSpecService.findEipSpec(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			EipSpec eipSpec = BeanMapper.map(eipSpecDTO, EipSpec.class);
			eipSpec.setUser(DEFAULT_USER);

			BeanValidators.validateWithException(validator, eipSpec);

			comm.eipSpecService.saveOrUpdate(eipSpec);

			return new IdResult(eipSpec.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEipSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(eipSpecDTO, ERROR.INPUT_NULL);

			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", eipSpecDTO.getCode());

			Validate.isTrue(
					comm.eipSpecService.findEipSpec(searchParams) == null
							|| eipSpec.getCode().equals(eipSpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(eipSpecDTO, EipSpec.class), eipSpec);

			eipSpec.setUser(DEFAULT_USER);
			eipSpec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			eipSpec.setIdClass(EipSpec.class.getSimpleName());

			BeanValidators.validateWithException(validator, eipSpec);

			comm.eipSpecService.saveOrUpdate(eipSpec);

			return new IdResult(eipSpec.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEipSpec(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			EipSpec eipSpec = comm.eipSpecService.findEipSpec(id);
			eipSpec.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.eipSpecService.saveOrUpdate(eipSpec);

			return new IdResult(eipSpec.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<EipSpecDTO> getEipSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {
		PaginationResult<EipSpecDTO> result = new PaginationResult<EipSpecDTO>();
		try {
			return comm.eipSpecService.getEipSpecDTOPagination(searchParams, pageNumber, pageSize);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<EipSpecDTO> getEipSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {
		DTOListResult<EipSpecDTO> result = new DTOListResult<EipSpecDTO>();
		try {
			result.setDtos(BeanMapper.mapList(comm.eipSpecService.getEipSpecList(searchParams), EipSpecDTO.class));
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3SpecDTO> findEs3Spec(@WebParam(name = "id") Integer id) {

		DTOResult<Es3SpecDTO> result = new DTOResult<Es3SpecDTO>();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id);

			Validate.notNull(es3Spec, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(es3Spec, Es3SpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOResult<Es3SpecDTO> findEs3SpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOResult<Es3SpecDTO> result = new DTOResult<Es3SpecDTO>();

		try {

			Validate.notNull(searchParams, ERROR.INPUT_NULL);

			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(searchParams);

			Validate.notNull(es3Spec, ERROR.OBJECT_NULL);

			result.setDto(BeanMapper.map(es3Spec, Es3SpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e, ERROR.MORE_RESULT);
		}
	}

	@Override
	public IdResult createEs3Spec(@WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(es3SpecDTO, ERROR.INPUT_NULL);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", es3SpecDTO.getCode());

			Validate.isTrue(comm.es3SpecService.findEs3Spec(searchParams) == null, ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象
			Es3Spec es3Spec = BeanMapper.map(es3SpecDTO, Es3Spec.class);
			es3Spec.setUser(DEFAULT_USER);

			BeanValidators.validateWithException(validator, es3Spec);

			comm.es3SpecService.saveOrUpdate(es3Spec);

			return new IdResult(es3Spec.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult updateEs3Spec(@WebParam(name = "id") Integer id,
			@WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(es3SpecDTO, ERROR.INPUT_NULL);

			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id);

			// 验证code是否唯一.如果不为null,则弹出错误.
			Map<String, Object> searchParams = Maps.newHashMap();
			searchParams.put("EQ_code", es3SpecDTO.getCode());

			Validate.isTrue(
					comm.es3SpecService.findEs3Spec(searchParams) == null
							|| es3Spec.getCode().equals(es3SpecDTO.getCode()), ERROR.OBJECT_DUPLICATE);

			// 将DTO对象转换至Entity对象,并将Entity拷贝至根据ID查询得到的Entity对象中
			BeanMapper.copy(BeanMapper.map(es3SpecDTO, Es3Spec.class), es3Spec);

			es3Spec.setUser(DEFAULT_USER);
			es3Spec.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			es3Spec.setIdClass(Es3Spec.class.getSimpleName());

			BeanValidators.validateWithException(validator, es3Spec);

			comm.es3SpecService.saveOrUpdate(es3Spec);

			return new IdResult(es3Spec.getId());

		} catch (ConstraintViolationException e) {
			String message = StringUtils.join(BeanValidators.extractPropertyAndMessageAsList(e, " "), "\n");
			return handleParameterError(result, e, message);
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public IdResult deleteEs3Spec(@WebParam(name = "id") Integer id) {

		IdResult result = new IdResult();

		try {

			Validate.notNull(id, ERROR.INPUT_NULL);

			Es3Spec es3Spec = comm.es3SpecService.findEs3Spec(id);
			es3Spec.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);

			comm.es3SpecService.saveOrUpdate(es3Spec);

			return new IdResult(es3Spec.getId());

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public PaginationResult<Es3SpecDTO> getEs3SpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize) {

		PaginationResult<Es3SpecDTO> result = new PaginationResult<Es3SpecDTO>();
		try {

			return comm.es3SpecService.getEs3SpecDTOPagination(searchParams, pageNumber, pageSize);

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<Es3SpecDTO> getEs3SpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams) {

		DTOListResult<Es3SpecDTO> result = new DTOListResult<Es3SpecDTO>();

		try {

			result.setDtos(BeanMapper.mapList(comm.es3SpecService.getEs3SpecList(searchParams), Es3SpecDTO.class));

			return result;

		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

}
