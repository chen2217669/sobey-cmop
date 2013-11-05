package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.FimasPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "FimasPortHistoryService", targetNamespace = WsConstants.NS)
public interface FimasPortHistorySoapService {
	DTOResult<FimasPortHistoryDTO> findFimasPortHistory(@WebParam(name = "id") Integer id);

	DTOResult<FimasPortHistoryDTO> findFimasPortHistoryByCode(@WebParam(name = "code") String code);

	IdResult createFimasPortHistory(@WebParam(name = "fimasPortHistoryDTO") FimasPortHistoryDTO fimasPortHistoryDTO);

	IdResult updateFimasPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasPortHistoryDTO") FimasPortHistoryDTO fimasPortHistoryDTO);

	IdResult deleteFimasPortHistory(@WebParam(name = "id") Integer id);

	DTOListResult<FimasPortHistoryDTO> getFimasPortHistoryList();

	PaginationResult<FimasPortHistoryDTO> getFimasPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}