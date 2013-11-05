package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.ServerHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "ServerHistoryService", targetNamespace = WsConstants.NS)
public interface ServerHistorySoapService {
	DTOResult<ServerHistoryDTO> findServerHistory(@WebParam(name = "id") Integer id);

	DTOResult<ServerHistoryDTO> findServerHistoryByCode(@WebParam(name = "code") String code);

	IdResult createServerHistory(@WebParam(name = "serverHistoryDTO") ServerHistoryDTO serverHistoryDTO);

	IdResult updateServerHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "serverHistoryDTO") ServerHistoryDTO serverHistoryDTO);

	IdResult deleteServerHistory(@WebParam(name = "id") Integer id);

	DTOListResult<ServerHistoryDTO> getServerHistoryList();

	PaginationResult<ServerHistoryDTO> getServerHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}