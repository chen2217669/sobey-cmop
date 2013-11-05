package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.TenantsHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "TenantsHistoryService", targetNamespace = WsConstants.NS)
public interface TenantsHistorySoapService {
	DTOResult<TenantsHistoryDTO> findTenantsHistory(@WebParam(name = "id") Integer id);

	DTOResult<TenantsHistoryDTO> findTenantsHistoryByCode(@WebParam(name = "code") String code);

	IdResult createTenantsHistory(@WebParam(name = "tenantsHistoryDTO") TenantsHistoryDTO tenantsHistoryDTO);

	IdResult updateTenantsHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "tenantsHistoryDTO") TenantsHistoryDTO tenantsHistoryDTO);

	IdResult deleteTenantsHistory(@WebParam(name = "id") Integer id);

	DTOListResult<TenantsHistoryDTO> getTenantsHistoryList();

	PaginationResult<TenantsHistoryDTO> getTenantsHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}