package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.As2HistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "As2HistoryService", targetNamespace = WsConstants.NS)
public interface As2HistorySoapService {
	DTOResult<As2HistoryDTO> findAs2History(@WebParam(name = "id") Integer id);

	DTOResult<As2HistoryDTO> findAs2HistoryByCode(@WebParam(name = "code") String code);

	IdResult createAs2History(@WebParam(name = "as2HistoryDTO") As2HistoryDTO as2HistoryDTO);

	IdResult updateAs2History(@WebParam(name = "id") Integer id,
			@WebParam(name = "as2HistoryDTO") As2HistoryDTO as2HistoryDTO);

	IdResult deleteAs2History(@WebParam(name = "id") Integer id);

	DTOListResult<As2HistoryDTO> getAs2HistoryList();

	PaginationResult<As2HistoryDTO> getAs2HistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}