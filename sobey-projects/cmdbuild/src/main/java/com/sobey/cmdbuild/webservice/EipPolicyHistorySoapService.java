package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EipPolicyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EipPolicyHistoryService", targetNamespace = WsConstants.NS)
public interface EipPolicyHistorySoapService {
	DTOResult<EipPolicyHistoryDTO> findEipPolicyHistory(@WebParam(name = "id") Integer id);

	DTOResult<EipPolicyHistoryDTO> findEipPolicyHistoryByCode(@WebParam(name = "code") String code);

	IdResult createEipPolicyHistory(@WebParam(name = "eipPolicyHistoryDTO") EipPolicyHistoryDTO eipPolicyHistoryDTO);

	IdResult updateEipPolicyHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipPolicyHistoryDTO") EipPolicyHistoryDTO eipPolicyHistoryDTO);

	IdResult deleteEipPolicyHistory(@WebParam(name = "id") Integer id);

	DTOListResult<EipPolicyHistoryDTO> getEipPolicyHistoryList();

	PaginationResult<EipPolicyHistoryDTO> getEipPolicyHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}