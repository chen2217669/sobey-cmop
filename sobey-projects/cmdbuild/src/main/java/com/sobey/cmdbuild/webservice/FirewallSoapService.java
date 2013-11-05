package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.FirewallDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "FirewallService", targetNamespace = WsConstants.NS)
public interface FirewallSoapService {
	DTOResult<FirewallDTO> findFirewall(@WebParam(name = "id") Integer id);

	DTOResult<FirewallDTO> findFirewallByCode(@WebParam(name = "code") String code);

	IdResult createFirewall(@WebParam(name = "firewallDTO") FirewallDTO firewallDTO);

	IdResult updateFirewall(@WebParam(name = "id") Integer id, @WebParam(name = "firewallDTO") FirewallDTO firewallDTO);

	IdResult deleteFirewall(@WebParam(name = "id") Integer id);

	DTOListResult<FirewallDTO> getFirewallList();

	PaginationResult<FirewallDTO> getFirewallPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}