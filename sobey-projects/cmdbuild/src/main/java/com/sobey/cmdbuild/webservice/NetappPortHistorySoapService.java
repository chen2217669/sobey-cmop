package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.NetappPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "NetappPortHistoryService", targetNamespace = WsConstants.NS)
public interface NetappPortHistorySoapService {
	DTOResult<NetappPortHistoryDTO> findNetappPortHistory(@WebParam(name = "id") Integer id);

	DTOResult<NetappPortHistoryDTO> findNetappPortHistoryByCode(@WebParam(name = "code") String code);

	IdResult createNetappPortHistory(@WebParam(name = "netappPortHistoryDTO") NetappPortHistoryDTO netappPortHistoryDTO);

	IdResult updateNetappPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappPortHistoryDTO") NetappPortHistoryDTO netappPortHistoryDTO);

	IdResult deleteNetappPortHistory(@WebParam(name = "id") Integer id);

	DTOListResult<NetappPortHistoryDTO> getNetappPortHistoryList();

	PaginationResult<NetappPortHistoryDTO> getNetappPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}