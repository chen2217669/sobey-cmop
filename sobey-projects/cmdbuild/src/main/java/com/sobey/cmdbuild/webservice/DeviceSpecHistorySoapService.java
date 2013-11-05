package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.DeviceSpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "DeviceSpecHistoryService", targetNamespace = WsConstants.NS)
public interface DeviceSpecHistorySoapService {
	DTOResult<DeviceSpecHistoryDTO> findDeviceSpecHistory(@WebParam(name = "id") Integer id);

	DTOResult<DeviceSpecHistoryDTO> findDeviceSpecHistoryByCode(@WebParam(name = "code") String code);

	IdResult createDeviceSpecHistory(@WebParam(name = "deviceSpecHistoryDTO") DeviceSpecHistoryDTO deviceSpecHistoryDTO);

	IdResult updateDeviceSpecHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "deviceSpecHistoryDTO") DeviceSpecHistoryDTO deviceSpecHistoryDTO);

	IdResult deleteDeviceSpecHistory(@WebParam(name = "id") Integer id);

	DTOListResult<DeviceSpecHistoryDTO> getDeviceSpecHistoryList();

	PaginationResult<DeviceSpecHistoryDTO> getDeviceSpecHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}