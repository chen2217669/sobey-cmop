package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.GroupPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "GroupPolicyService", targetNamespace = WsConstants.NS)
public interface GroupPolicySoapService {
	DTOResult<GroupPolicyDTO> findGroupPolicy(@WebParam(name = "id") Integer id);

	DTOResult<GroupPolicyDTO> findGroupPolicyByCode(@WebParam(name = "code") String code);

	IdResult createGroupPolicy(@WebParam(name = "groupPolicyDTO") GroupPolicyDTO groupPolicyDTO);

	IdResult updateGroupPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "groupPolicyDTO") GroupPolicyDTO groupPolicyDTO);

	IdResult deleteGroupPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<GroupPolicyDTO> getGroupPolicyList();

	PaginationResult<GroupPolicyDTO> getGroupPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}