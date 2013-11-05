package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "Es3SpecHistoryService", targetNamespace = WsConstants.NS)
public interface Es3SpecHistorySoapService {
	DTOResult<Es3SpecHistoryDTO> findEs3SpecHistory(@WebParam(name = "id") Integer id);

	DTOResult<Es3SpecHistoryDTO> findEs3SpecHistoryByCode(@WebParam(name = "code") String code);

	IdResult createEs3SpecHistory(@WebParam(name = "es3SpecHistoryDTO") Es3SpecHistoryDTO es3SpecHistoryDTO);

	IdResult updateEs3SpecHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "es3SpecHistoryDTO") Es3SpecHistoryDTO es3SpecHistoryDTO);

	IdResult deleteEs3SpecHistory(@WebParam(name = "id") Integer id);

	DTOListResult<Es3SpecHistoryDTO> getEs3SpecHistoryList();

	PaginationResult<Es3SpecHistoryDTO> getEs3SpecHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}