package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.VpnDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "VpnService", targetNamespace = WsConstants.NS)
public interface VpnSoapService {
	DTOResult<VpnDTO> findVpn(@WebParam(name = "id") Integer id);

	DTOResult<VpnDTO> findVpnByCode(@WebParam(name = "code") String code);

	IdResult createVpn(@WebParam(name = "vpnDTO") VpnDTO vpnDTO);

	IdResult updateVpn(@WebParam(name = "id") Integer id, @WebParam(name = "vpnDTO") VpnDTO vpnDTO);

	IdResult deleteVpn(@WebParam(name = "id") Integer id);

	DTOListResult<VpnDTO> getVpnList();

	PaginationResult<VpnDTO> getVpnPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}