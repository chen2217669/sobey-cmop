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
import com.sobey.cmdbuild.entity.SwitchPort;
import com.sobey.cmdbuild.webservice.BasicSoapSevcie;
import com.sobey.cmdbuild.webservice.SwitchPortSoapService;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;
import com.sobey.core.beanvalidator.BeanValidators;
import com.sobey.core.mapper.BeanMapper;

@WebService(serviceName = "SwitchPortService", endpointInterface = "com.sobey.cmdbuild.webservice.SwitchPortSoapService", targetNamespace = WsConstants.NS)
@Features(features = "org.apache.cxf.feature.LoggingFeature")
public class SwitchPortSoapServiceImpl extends BasicSoapSevcie implements SwitchPortSoapService {
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
	public DTOResult<SwitchPortDTO> findSwitchPortByCode(@WebParam(name = "code") String code) {
		DTOResult<SwitchPortDTO> result = new DTOResult<SwitchPortDTO>();
		try {
			Validate.notNull(code, ERROR.INPUT_NULL);
			SwitchPort switchPort = comm.switchPortService.findByCode(code);
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
	public IdResult createSwitchPort(@WebParam(name = "switchPortDTO") SwitchPortDTO switchPortDTO) {
		IdResult result = new IdResult();
		try {
			Validate.notNull(switchPortDTO, ERROR.INPUT_NULL);
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.switchPortService.findByCode(switchPortDTO.getCode()) == null, ERROR.OBJECT_DUPLICATE);
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
			// 验证code是否唯一.如果不为null,则弹出错误.
			Validate.isTrue(comm.switchPortService.findByCode(switchPortDTO.getCode()) == null
					|| switchPort.getCode().equals(switchPortDTO.getCode()), ERROR.OBJECT_DUPLICATE);
			SwitchPort switchPortEntity = BeanMapper.map(switchPortDTO, SwitchPort.class);
			BeanMapper.copy(switchPortEntity, switchPort);
			switchPortEntity.setIdClass(SwitchPort.class.getSimpleName());
			switchPortEntity.setStatus(CMDBuildConstants.STATUS_ACTIVE);
			comm.switchPortService.saveOrUpdate(switchPortEntity);
			return new IdResult(switchPort.getId());
		} catch (ConstraintViolationException e) {
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
			SwitchPort switchPortEntity = BeanMapper.map(findSwitchPort(id).getDto(), SwitchPort.class);
			BeanMapper.copy(switchPortEntity, switchPort);
			switchPortEntity.setIdClass(SwitchPort.class.getSimpleName());
			switchPortEntity.setStatus(CMDBuildConstants.STATUS_NON_ACTIVE);
			comm.switchPortService.saveOrUpdate(switchPortEntity);
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
			result = comm.switchPortService.getSwitchPortDTOPagination(searchParams, pageNumber, pageSize);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}

	@Override
	public DTOListResult<SwitchPortDTO> getSwitchPortList() {
		DTOListResult<SwitchPortDTO> result = new DTOListResult<SwitchPortDTO>();
		try {
			List<SwitchPort> companies = comm.switchPortService.getCompanies();
			List<SwitchPortDTO> list = BeanMapper.mapList(companies, SwitchPortDTO.class);
			result.setDtos(list);
			return result;
		} catch (IllegalArgumentException e) {
			return handleParameterError(result, e);
		} catch (RuntimeException e) {
			return handleGeneralError(result, e);
		}
	}
}