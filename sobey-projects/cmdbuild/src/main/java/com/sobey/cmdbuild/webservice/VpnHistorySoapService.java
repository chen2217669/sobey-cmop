package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.VpnHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "VpnHistoryService", targetNamespace = WsConstants.NS)
public interface VpnHistorySoapService {
	DTOResult<VpnHistoryDTO> findVpnHistory(@WebParam(name = "id") Integer id);

	DTOResult<VpnHistoryDTO> findVpnHistoryByCode(@WebParam(name = "code") String code);

	IdResult createVpnHistory(@WebParam(name = "vpnHistoryDTO") VpnHistoryDTO vpnHistoryDTO);

	IdResult updateVpnHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "vpnHistoryDTO") VpnHistoryDTO vpnHistoryDTO);

	IdResult deleteVpnHistory(@WebParam(name = "id") Integer id);

	DTOListResult<VpnHistoryDTO> getVpnHistoryList();

	PaginationResult<VpnHistoryDTO> getVpnHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}