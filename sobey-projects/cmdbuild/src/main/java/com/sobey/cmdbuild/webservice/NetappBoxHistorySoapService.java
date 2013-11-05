package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.NetappBoxHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "NetappBoxHistoryService", targetNamespace = WsConstants.NS)
public interface NetappBoxHistorySoapService {
	DTOResult<NetappBoxHistoryDTO> findNetappBoxHistory(@WebParam(name = "id") Integer id);

	DTOResult<NetappBoxHistoryDTO> findNetappBoxHistoryByCode(@WebParam(name = "code") String code);

	IdResult createNetappBoxHistory(@WebParam(name = "netappBoxHistoryDTO") NetappBoxHistoryDTO netappBoxHistoryDTO);

	IdResult updateNetappBoxHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappBoxHistoryDTO") NetappBoxHistoryDTO netappBoxHistoryDTO);

	IdResult deleteNetappBoxHistory(@WebParam(name = "id") Integer id);

	DTOListResult<NetappBoxHistoryDTO> getNetappBoxHistoryList();

	PaginationResult<NetappBoxHistoryDTO> getNetappBoxHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}