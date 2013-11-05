package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.RackHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "RackHistoryService", targetNamespace = WsConstants.NS)
public interface RackHistorySoapService {
	DTOResult<RackHistoryDTO> findRackHistory(@WebParam(name = "id") Integer id);

	DTOResult<RackHistoryDTO> findRackHistoryByCode(@WebParam(name = "code") String code);

	IdResult createRackHistory(@WebParam(name = "rackHistoryDTO") RackHistoryDTO rackHistoryDTO);

	IdResult updateRackHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "rackHistoryDTO") RackHistoryDTO rackHistoryDTO);

	IdResult deleteRackHistory(@WebParam(name = "id") Integer id);

	DTOListResult<RackHistoryDTO> getRackHistoryList();

	PaginationResult<RackHistoryDTO> getRackHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}