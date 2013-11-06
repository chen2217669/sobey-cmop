package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.FimasHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "FimasHistoryService", targetNamespace = WsConstants.NS)
public interface FimasHistorySoapService {
	DTOResult<FimasHistoryDTO> findFimasHistory(@WebParam(name = "id") Integer id);

	DTOResult<FimasHistoryDTO> findFimasHistoryByCode(@WebParam(name = "code") String code);

	IdResult createFimasHistory(@WebParam(name = "fimasHistoryDTO") FimasHistoryDTO fimasHistoryDTO);

	IdResult updateFimasHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasHistoryDTO") FimasHistoryDTO fimasHistoryDTO);

	IdResult deleteFimasHistory(@WebParam(name = "id") Integer id);

	DTOListResult<FimasHistoryDTO> getFimasHistoryList();

	PaginationResult<FimasHistoryDTO> getFimasHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}