package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.Cs2HistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "Cs2HistoryService", targetNamespace = WsConstants.NS)
public interface Cs2HistorySoapService {
	DTOResult<Cs2HistoryDTO> findCs2History(@WebParam(name = "id") Integer id);

	DTOResult<Cs2HistoryDTO> findCs2HistoryByCode(@WebParam(name = "code") String code);

	IdResult createCs2History(@WebParam(name = "cs2HistoryDTO") Cs2HistoryDTO cs2HistoryDTO);

	IdResult updateCs2History(@WebParam(name = "id") Integer id,
			@WebParam(name = "cs2HistoryDTO") Cs2HistoryDTO cs2HistoryDTO);

	IdResult deleteCs2History(@WebParam(name = "id") Integer id);

	DTOListResult<Cs2HistoryDTO> getCs2HistoryList();

	PaginationResult<Cs2HistoryDTO> getCs2HistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}