package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.FirewallHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "FirewallHistoryService", targetNamespace = WsConstants.NS)
public interface FirewallHistorySoapService {
	DTOResult<FirewallHistoryDTO> findFirewallHistory(@WebParam(name = "id") Integer id);

	DTOResult<FirewallHistoryDTO> findFirewallHistoryByCode(@WebParam(name = "code") String code);

	IdResult createFirewallHistory(@WebParam(name = "firewallHistoryDTO") FirewallHistoryDTO firewallHistoryDTO);

	IdResult updateFirewallHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallHistoryDTO") FirewallHistoryDTO firewallHistoryDTO);

	IdResult deleteFirewallHistory(@WebParam(name = "id") Integer id);

	DTOListResult<FirewallHistoryDTO> getFirewallHistoryList();

	PaginationResult<FirewallHistoryDTO> getFirewallHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}