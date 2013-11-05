package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "IpaddressHistoryService", targetNamespace = WsConstants.NS)
public interface IpaddressHistorySoapService {
	DTOResult<IpaddressHistoryDTO> findIpaddressHistory(@WebParam(name = "id") Integer id);

	DTOResult<IpaddressHistoryDTO> findIpaddressHistoryByCode(@WebParam(name = "code") String code);

	IdResult createIpaddressHistory(@WebParam(name = "ipaddressHistoryDTO") IpaddressHistoryDTO ipaddressHistoryDTO);

	IdResult updateIpaddressHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "ipaddressHistoryDTO") IpaddressHistoryDTO ipaddressHistoryDTO);

	IdResult deleteIpaddressHistory(@WebParam(name = "id") Integer id);

	DTOListResult<IpaddressHistoryDTO> getIpaddressHistoryList();

	PaginationResult<IpaddressHistoryDTO> getIpaddressHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}