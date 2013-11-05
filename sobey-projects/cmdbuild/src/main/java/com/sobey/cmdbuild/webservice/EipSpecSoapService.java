package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EipSpecService", targetNamespace = WsConstants.NS)
public interface EipSpecSoapService {
	DTOResult<EipSpecDTO> findEipSpec(@WebParam(name = "id") Integer id);

	DTOResult<EipSpecDTO> findEipSpecByCode(@WebParam(name = "code") String code);

	IdResult createEipSpec(@WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO);

	IdResult updateEipSpec(@WebParam(name = "id") Integer id, @WebParam(name = "eipSpecDTO") EipSpecDTO eipSpecDTO);

	IdResult deleteEipSpec(@WebParam(name = "id") Integer id);

	DTOListResult<EipSpecDTO> getEipSpecList();

	PaginationResult<EipSpecDTO> getEipSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}