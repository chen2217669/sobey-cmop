package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.MemoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "MemoryService", targetNamespace = WsConstants.NS)
public interface MemorySoapService {
	DTOResult<MemoryDTO> findMemory(@WebParam(name = "id") Integer id);

	DTOResult<MemoryDTO> findMemoryByCode(@WebParam(name = "code") String code);

	IdResult createMemory(@WebParam(name = "memoryDTO") MemoryDTO memoryDTO);

	IdResult updateMemory(@WebParam(name = "id") Integer id, @WebParam(name = "memoryDTO") MemoryDTO memoryDTO);

	IdResult deleteMemory(@WebParam(name = "id") Integer id);

	DTOListResult<MemoryDTO> getMemoryList();

	PaginationResult<MemoryDTO> getMemoryPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}