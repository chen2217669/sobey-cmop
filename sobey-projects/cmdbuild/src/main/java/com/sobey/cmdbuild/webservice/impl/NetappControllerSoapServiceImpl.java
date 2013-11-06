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
import com.sobey.cmdbuild.entity.NetappController;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.NetappControllerSoapService;
import com.sobey.cmdbuild.webservice.response.dto.NetappControllerDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "NetappControllerService", endpointInterface = "com.sobey.cmdbuild.webservice.NetappControllerSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class NetappControllerSoapServiceImpl extends BasicSoapSevcie implements NetappControllerSoapService {
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
	public DTOResult<NetappControllerDTO> findNetappControllerByCode(@WebParam(name = "code") String code) {
		DTOResult<NetappControllerDTO> result = new DTOResult<NetappControllerDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			NetappController netappController = comm.netappControllerService.findByCode(code);
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
	public IdResult createNetappController(
			@WebParam(name = "netappControllerDTO") NetappControllerDTO netappControllerDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(netappControllerDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappControllerService.findByCode(netappControllerDTO.getCode()) == null,
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
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.netappControllerService.findByCode(netappControllerDTO.getCode()) == null
					|| netappController.getCode().equals(netappControllerDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			NetappController netappControllerEntity = BeanMapper.map(netappControllerDTO, NetappController.class);
			BeanMapper.copy(netappControllerEntity, netappController);
			netappControllerEntity.setIdClass(NetappController.class.getSimpleName());
			netappControllerEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.netappControllerService.saveOrUpdate(netappControllerEntity);
			return new IdResult(netappController.getId());
		} catch (ConstraintViolationException e) {
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
			NetappController netappControllerEntity = BeanMapper.map(findNetappController(id).getDto(),
					NetappController.class);
			BeanMapper.copy(netappControllerEntity, netappController);
			netappControllerEntity.setIdClass(NetappController.class.getSimpleName());
			netappControllerEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.netappControllerService.saveOrUpdate(netappControllerEntity);
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
			result = comm.netappControllerService.getNetappControllerDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<NetappControllerDTO> getNetappControllerList() {
		DTOListResult<NetappControllerDTO> result = new DTOListResult<NetappControllerDTO>();
		try {
			List<NetappController> companies = comm.netappControllerService.getCompanies();
			List<NetappControllerDTO> list = BeanMapper.mapList(companies, NetappControllerDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}