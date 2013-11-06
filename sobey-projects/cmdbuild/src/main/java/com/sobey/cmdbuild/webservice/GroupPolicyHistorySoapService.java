package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.GroupPolicyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "GroupPolicyHistoryService", targetNamespace = WsConstants.NS)
public interface GroupPolicyHistorySoapService {
	DTOResult<GroupPolicyHistoryDTO> findGroupPolicyHistory(@WebParam(name = "id") Integer id);

	DTOResult<GroupPolicyHistoryDTO> findGroupPolicyHistoryByCode(@WebParam(name = "code") String code);

	IdResult createGroupPolicyHistory(
			@WebParam(name = "groupPolicyHistoryDTO") GroupPolicyHistoryDTO groupPolicyHistoryDTO);

	IdResult updateGroupPolicyHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "groupPolicyHistoryDTO") GroupPolicyHistoryDTO groupPolicyHistoryDTO);

	IdResult deleteGroupPolicyHistory(@WebParam(name = "id") Integer id);

	DTOListResult<GroupPolicyHistoryDTO> getGroupPolicyHistoryList();

	PaginationResult<GroupPolicyHistoryDTO> getGroupPolicyHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}