package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EsgPolicyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EsgPolicyHistoryService", targetNamespace = WsConstants.NS)
public interface EsgPolicyHistorySoapService {
	DTOResult<EsgPolicyHistoryDTO> findEsgPolicyHistory(@WebParam(name = "id") Integer id);

	DTOResult<EsgPolicyHistoryDTO> findEsgPolicyHistoryByCode(@WebParam(name = "code") String code);

	IdResult createEsgPolicyHistory(@WebParam(name = "esgPolicyHistoryDTO") EsgPolicyHistoryDTO esgPolicyHistoryDTO);

	IdResult updateEsgPolicyHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "esgPolicyHistoryDTO") EsgPolicyHistoryDTO esgPolicyHistoryDTO);

	IdResult deleteEsgPolicyHistory(@WebParam(name = "id") Integer id);

	DTOListResult<EsgPolicyHistoryDTO> getEsgPolicyHistoryList();

	PaginationResult<EsgPolicyHistoryDTO> getEsgPolicyHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}