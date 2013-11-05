package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.NetappControllerHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "NetappControllerHistoryService", targetNamespace = WsConstants.NS)
public interface NetappControllerHistorySoapService {
	DTOResult<NetappControllerHistoryDTO> findNetappControllerHistory(@WebParam(name = "id") Integer id);

	DTOResult<NetappControllerHistoryDTO> findNetappControllerHistoryByCode(@WebParam(name = "code") String code);

	IdResult createNetappControllerHistory(
			@WebParam(name = "netappControllerHistoryDTO") NetappControllerHistoryDTO netappControllerHistoryDTO);

	IdResult updateNetappControllerHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappControllerHistoryDTO") NetappControllerHistoryDTO netappControllerHistoryDTO);

	IdResult deleteNetappControllerHistory(@WebParam(name = "id") Integer id);

	DTOListResult<NetappControllerHistoryDTO> getNetappControllerHistoryList();

	PaginationResult<NetappControllerHistoryDTO> getNetappControllerHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}