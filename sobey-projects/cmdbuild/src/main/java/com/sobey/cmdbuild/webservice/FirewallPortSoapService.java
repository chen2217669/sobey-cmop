package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.FirewallPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "FirewallPortService", targetNamespace = WsConstants.NS)
public interface FirewallPortSoapService {
	DTOResult<FirewallPortDTO> findFirewallPort(@WebParam(name = "id") Integer id);

	DTOResult<FirewallPortDTO> findFirewallPortByCode(@WebParam(name = "code") String code);

	IdResult createFirewallPort(@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO);

	IdResult updateFirewallPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "firewallPortDTO") FirewallPortDTO firewallPortDTO);

	IdResult deleteFirewallPort(@WebParam(name = "id") Integer id);

	DTOListResult<FirewallPortDTO> getFirewallPortList();

	PaginationResult<FirewallPortDTO> getFirewallPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}