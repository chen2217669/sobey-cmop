package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "ServerPortHistoryService", targetNamespace = WsConstants.NS)
public interface ServerPortHistorySoapService {
	DTOResult<ServerPortHistoryDTO> findServerPortHistory(@WebParam(name = "id") Integer id);

	DTOResult<ServerPortHistoryDTO> findServerPortHistoryByCode(@WebParam(name = "code") String code);

	IdResult createServerPortHistory(@WebParam(name = "serverPortHistoryDTO") ServerPortHistoryDTO serverPortHistoryDTO);

	IdResult updateServerPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "serverPortHistoryDTO") ServerPortHistoryDTO serverPortHistoryDTO);

	IdResult deleteServerPortHistory(@WebParam(name = "id") Integer id);

	DTOListResult<ServerPortHistoryDTO> getServerPortHistoryList();

	PaginationResult<ServerPortHistoryDTO> getServerPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}