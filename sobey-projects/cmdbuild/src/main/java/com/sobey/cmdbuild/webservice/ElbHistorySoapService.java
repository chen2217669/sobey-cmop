package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.ElbHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "ElbHistoryService", targetNamespace = WsConstants.NS)
public interface ElbHistorySoapService {
	DTOResult<ElbHistoryDTO> findElbHistory(@WebParam(name = "id") Integer id);

	DTOResult<ElbHistoryDTO> findElbHistoryByCode(@WebParam(name = "code") String code);

	IdResult createElbHistory(@WebParam(name = "elbHistoryDTO") ElbHistoryDTO elbHistoryDTO);

	IdResult updateElbHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "elbHistoryDTO") ElbHistoryDTO elbHistoryDTO);

	IdResult deleteElbHistory(@WebParam(name = "id") Integer id);

	DTOListResult<ElbHistoryDTO> getElbHistoryList();

	PaginationResult<ElbHistoryDTO> getElbHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}