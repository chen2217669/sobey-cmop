package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "FirewallPortHistoryService", targetNamespace = WsConstants.NS)
public interface FirewallPortHistorySoapService {
	DTOResult<FirewallPortHistoryDTO> findFirewallPortHistory(@WebParam(name = "id") Integer id);

	DTOResult<FirewallPortHistoryDTO> findFirewallPortHistoryByCode(@WebParam(name = "code") String code);

	IdResult createFirewallPortHistory(
			@WebParam(name = "firewallPortHistoryDTO") FirewallPortHistoryDTO firewallPortHistoryDTO);

	IdResult updateFirewallPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallPortHistoryDTO") FirewallPortHistoryDTO firewallPortHistoryDTO);

	IdResult deleteFirewallPortHistory(@WebParam(name = "id") Integer id);

	DTOListResult<FirewallPortHistoryDTO> getFirewallPortHistoryList();

	PaginationResult<FirewallPortHistoryDTO> getFirewallPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}