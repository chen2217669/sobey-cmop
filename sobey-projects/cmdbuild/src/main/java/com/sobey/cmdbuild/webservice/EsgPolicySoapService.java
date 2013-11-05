package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EsgPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EsgPolicyService", targetNamespace = WsConstants.NS)
public interface EsgPolicySoapService {
	DTOResult<EsgPolicyDTO> findEsgPolicy(@WebParam(name = "id") Integer id);

	DTOResult<EsgPolicyDTO> findEsgPolicyByCode(@WebParam(name = "code") String code);

	IdResult createEsgPolicy(@WebParam(name = "esgPolicyDTO") EsgPolicyDTO esgPolicyDTO);

	IdResult updateEsgPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "esgPolicyDTO") EsgPolicyDTO esgPolicyDTO);

	IdResult deleteEsgPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<EsgPolicyDTO> getEsgPolicyList();

	PaginationResult<EsgPolicyDTO> getEsgPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}