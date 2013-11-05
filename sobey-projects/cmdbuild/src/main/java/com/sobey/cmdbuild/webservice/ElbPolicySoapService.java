package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.ElbPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "ElbPolicyService", targetNamespace = WsConstants.NS)
public interface ElbPolicySoapService {
	DTOResult<ElbPolicyDTO> findElbPolicy(@WebParam(name = "id") Integer id);

	DTOResult<ElbPolicyDTO> findElbPolicyByCode(@WebParam(name = "code") String code);

	IdResult createElbPolicy(@WebParam(name = "elbPolicyDTO") ElbPolicyDTO elbPolicyDTO);

	IdResult updateElbPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "elbPolicyDTO") ElbPolicyDTO elbPolicyDTO);

	IdResult deleteElbPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<ElbPolicyDTO> getElbPolicyList();

	PaginationResult<ElbPolicyDTO> getElbPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}