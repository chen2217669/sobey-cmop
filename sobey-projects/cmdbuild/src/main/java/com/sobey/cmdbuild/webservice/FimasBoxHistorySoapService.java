package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.FimasBoxHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "FimasBoxHistoryService", targetNamespace = WsConstants.NS)
public interface FimasBoxHistorySoapService {
	DTOResult<FimasBoxHistoryDTO> findFimasBoxHistory(@WebParam(name = "id") Integer id);

	DTOResult<FimasBoxHistoryDTO> findFimasBoxHistoryByCode(@WebParam(name = "code") String code);

	IdResult createFimasBoxHistory(@WebParam(name = "fimasBoxHistoryDTO") FimasBoxHistoryDTO fimasBoxHistoryDTO);

	IdResult updateFimasBoxHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasBoxHistoryDTO") FimasBoxHistoryDTO fimasBoxHistoryDTO);

	IdResult deleteFimasBoxHistory(@WebParam(name = "id") Integer id);

	DTOListResult<FimasBoxHistoryDTO> getFimasBoxHistoryList();

	PaginationResult<FimasBoxHistoryDTO> getFimasBoxHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}