package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.NicHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "NicHistoryService", targetNamespace = WsConstants.NS)
public interface NicHistorySoapService {
	DTOResult<NicHistoryDTO> findNicHistory(@WebParam(name = "id") Integer id);

	DTOResult<NicHistoryDTO> findNicHistoryByCode(@WebParam(name = "code") String code);

	IdResult createNicHistory(@WebParam(name = "nicHistoryDTO") NicHistoryDTO nicHistoryDTO);

	IdResult updateNicHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "nicHistoryDTO") NicHistoryDTO nicHistoryDTO);

	IdResult deleteNicHistory(@WebParam(name = "id") Integer id);

	DTOListResult<NicHistoryDTO> getNicHistoryList();

	PaginationResult<NicHistoryDTO> getNicHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}