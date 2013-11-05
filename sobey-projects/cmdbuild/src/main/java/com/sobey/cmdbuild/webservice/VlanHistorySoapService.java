package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.VlanHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "VlanHistoryService", targetNamespace = WsConstants.NS)
public interface VlanHistorySoapService {
	DTOResult<VlanHistoryDTO> findVlanHistory(@WebParam(name = "id") Integer id);

	DTOResult<VlanHistoryDTO> findVlanHistoryByCode(@WebParam(name = "code") String code);

	IdResult createVlanHistory(@WebParam(name = "vlanHistoryDTO") VlanHistoryDTO vlanHistoryDTO);

	IdResult updateVlanHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "vlanHistoryDTO") VlanHistoryDTO vlanHistoryDTO);

	IdResult deleteVlanHistory(@WebParam(name = "id") Integer id);

	DTOListResult<VlanHistoryDTO> getVlanHistoryList();

	PaginationResult<VlanHistoryDTO> getVlanHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}