package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.MemoryHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "MemoryHistoryService", targetNamespace = WsConstants.NS)
public interface MemoryHistorySoapService {
	DTOResult<MemoryHistoryDTO> findMemoryHistory(@WebParam(name = "id") Integer id);

	DTOResult<MemoryHistoryDTO> findMemoryHistoryByCode(@WebParam(name = "code") String code);

	IdResult createMemoryHistory(@WebParam(name = "memoryHistoryDTO") MemoryHistoryDTO memoryHistoryDTO);

	IdResult updateMemoryHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "memoryHistoryDTO") MemoryHistoryDTO memoryHistoryDTO);

	IdResult deleteMemoryHistory(@WebParam(name = "id") Integer id);

	DTOListResult<MemoryHistoryDTO> getMemoryHistoryList();

	PaginationResult<MemoryHistoryDTO> getMemoryHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}