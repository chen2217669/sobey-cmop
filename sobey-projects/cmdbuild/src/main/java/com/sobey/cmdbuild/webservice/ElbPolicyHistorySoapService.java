package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.ElbPolicyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "ElbPolicyHistoryService", targetNamespace = WsConstants.NS)
public interface ElbPolicyHistorySoapService {
	DTOResult<ElbPolicyHistoryDTO> findElbPolicyHistory(@WebParam(name = "id") Integer id);

	DTOResult<ElbPolicyHistoryDTO> findElbPolicyHistoryByCode(@WebParam(name = "code") String code);

	IdResult createElbPolicyHistory(@WebParam(name = "elbPolicyHistoryDTO") ElbPolicyHistoryDTO elbPolicyHistoryDTO);

	IdResult updateElbPolicyHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "elbPolicyHistoryDTO") ElbPolicyHistoryDTO elbPolicyHistoryDTO);

	IdResult deleteElbPolicyHistory(@WebParam(name = "id") Integer id);

	DTOListResult<ElbPolicyHistoryDTO> getElbPolicyHistoryList();

	PaginationResult<ElbPolicyHistoryDTO> getElbPolicyHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}