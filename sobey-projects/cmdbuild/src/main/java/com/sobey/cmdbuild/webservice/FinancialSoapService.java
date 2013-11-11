package com.sobey.cmdbuild.webservice;

import java.util.Map;

import javax.jws.WebParam;

import com.sobey.cmdbuild.webservice.response.dto.ConsumptionsDTO;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecDTO;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

//@Component("CmdbuildService")
//@WebService(name = "CmdbuildService", targetNamespace = WsConstants.NS)
public interface FinancialSoapService {

	/*************************************************
	 ****************** Financial ********************
	 *************************************************/

	// ==============================//
	// ========= Consumptions =======//
	// ==============================//

	DTOResult<ConsumptionsDTO> findConsumptions(@WebParam(name = "id") Integer id);

	DTOResult<ConsumptionsDTO> findConsumptionsByParams(
			@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createConsumptions(@WebParam(name = "ConsumptionsDTO") ConsumptionsDTO consumptionsDTO);

	IdResult updateConsumptions(@WebParam(name = "id") Integer id,
			@WebParam(name = "ConsumptionsDTO") ConsumptionsDTO consumptionsDTO);

	IdResult deleteConsumptions(@WebParam(name = "id") Integer id);

	DTOListResult<ConsumptionsDTO> getConsumptionsList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<ConsumptionsDTO> getConsumptionsPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	/**
	 * Consumptions 结算。<br/>
	 * 在租户用户的账号余额基础上扣除 Consumptions 的 Spending(消费金额),<br/>
	 * 并将 Consumptions 锁定。<br/>
	 * 
	 * @param cid
	 *            消费id
	 * @param tid
	 *            租户id
	 * @param ConsumptionsDTO
	 * @return IdResult
	 */
	IdResult settleConsumptions(@WebParam(name = "cid") Integer cid, @WebParam(name = "tid") Integer tid);

	/**
	 * 多条件获取对象集合，并导出列表的信息为 Excel 文件。
	 * 
	 * @param searchParams
	 * @return DTOListResult<ConsumptionsDTO>
	 */
	DTOListResult<ConsumptionsDTO> reportConsumptions(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	// ==============================//
	// ========= DeviceSpec =========//
	// ==============================//

	DTOResult<DeviceSpecDTO> findDeviceSpec(@WebParam(name = "id") Integer id);

	DTOResult<DeviceSpecDTO> findDeviceSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createDeviceSpec(@WebParam(name = "DeviceSpecDTO") DeviceSpecDTO deviceSpecDTO);

	IdResult updateDeviceSpec(@WebParam(name = "id") Integer id,
			@WebParam(name = "DeviceSpecDTO") DeviceSpecDTO deviceSpecDTO);

	IdResult deleteDeviceSpec(@WebParam(name = "id") Integer id);

	DTOListResult<DeviceSpecDTO> getDeviceSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<DeviceSpecDTO> getDeviceSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== EcsSpec ==========//
	// ==============================//

	DTOResult<EcsSpecDTO> findEcsSpec(@WebParam(name = "id") Integer id);

	DTOResult<EcsSpecDTO> findEcsSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEcsSpec(@WebParam(name = "EcsSpecDTO") EcsSpecDTO ecsSpecDTO);

	IdResult updateEcsSpec(@WebParam(name = "id") Integer id, @WebParam(name = "EcsSpecDTO") EcsSpecDTO ecsSpecDTO);

	IdResult deleteEcsSpec(@WebParam(name = "id") Integer id);

	DTOListResult<EcsSpecDTO> getEcsSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EcsSpecDTO> getEcsSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// =========== EipSpec ==========//
	// ==============================//

	DTOResult<EipSpecDTO> findEipSpec(@WebParam(name = "id") Integer id);

	DTOResult<EipSpecDTO> findEipSpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEipSpec(@WebParam(name = "EipSpecDTO") EipSpecDTO eipSpecDTO);

	IdResult updateEipSpec(@WebParam(name = "id") Integer id, @WebParam(name = "EipSpecDTO") EipSpecDTO eipSpecDTO);

	IdResult deleteEipSpec(@WebParam(name = "id") Integer id);

	DTOListResult<EipSpecDTO> getEipSpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<EipSpecDTO> getEipSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

	// ==============================//
	// ============ Es3Spec =========//
	// ==============================//

	DTOResult<Es3SpecDTO> findEs3Spec(@WebParam(name = "id") Integer id);

	DTOResult<Es3SpecDTO> findEs3SpecByParams(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	IdResult createEs3Spec(@WebParam(name = "Es3SpecDTO") Es3SpecDTO es3SpecDTO);

	IdResult updateEs3Spec(@WebParam(name = "id") Integer id, @WebParam(name = "Es3SpecDTO") Es3SpecDTO es3SpecDTO);

	IdResult deleteEs3Spec(@WebParam(name = "id") Integer id);

	DTOListResult<Es3SpecDTO> getEs3SpecList(@WebParam(name = "searchParams") Map<String, Object> searchParams);

	PaginationResult<Es3SpecDTO> getEs3SpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

}
