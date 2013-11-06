package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.NicPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "NicPortHistoryService", targetNamespace = WsConstants.NS)
public interface NicPortHistorySoapService {
	DTOResult<NicPortHistoryDTO> findNicPortHistory(@WebParam(name = "id") Integer id);

	DTOResult<NicPortHistoryDTO> findNicPortHistoryByCode(@WebParam(name = "code") String code);

	IdResult createNicPortHistory(@WebParam(name = "nicPortHistoryDTO") NicPortHistoryDTO nicPortHistoryDTO);

	IdResult updateNicPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "nicPortHistoryDTO") NicPortHistoryDTO nicPortHistoryDTO);

	IdResult deleteNicPortHistory(@WebParam(name = "id") Integer id);

	DTOListResult<NicPortHistoryDTO> getNicPortHistoryList();

	PaginationResult<NicPortHistoryDTO> getNicPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}