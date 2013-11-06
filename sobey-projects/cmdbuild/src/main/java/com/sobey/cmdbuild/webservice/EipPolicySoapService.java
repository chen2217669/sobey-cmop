package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EipPolicyDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EipPolicyService", targetNamespace = WsConstants.NS)
public interface EipPolicySoapService {
	DTOResult<EipPolicyDTO> findEipPolicy(@WebParam(name = "id") Integer id);

	DTOResult<EipPolicyDTO> findEipPolicyByCode(@WebParam(name = "code") String code);

	IdResult createEipPolicy(@WebParam(name = "eipPolicyDTO") EipPolicyDTO eipPolicyDTO);

	IdResult updateEipPolicy(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipPolicyDTO") EipPolicyDTO eipPolicyDTO);

	IdResult deleteEipPolicy(@WebParam(name = "id") Integer id);

	DTOListResult<EipPolicyDTO> getEipPolicyList();

	PaginationResult<EipPolicyDTO> getEipPolicyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}