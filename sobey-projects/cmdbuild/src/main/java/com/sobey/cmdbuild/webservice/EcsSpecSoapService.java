package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EcsSpecService", targetNamespace = WsConstants.NS)
public interface EcsSpecSoapService {
	DTOResult<EcsSpecDTO> findEcsSpec(@WebParam(name = "id") Integer id);

	DTOResult<EcsSpecDTO> findEcsSpecByCode(@WebParam(name = "code") String code);

	IdResult createEcsSpec(@WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO);

	IdResult updateEcsSpec(@WebParam(name = "id") Integer id, @WebParam(name = "ecsSpecDTO") EcsSpecDTO ecsSpecDTO);

	IdResult deleteEcsSpec(@WebParam(name = "id") Integer id);

	DTOListResult<EcsSpecDTO> getEcsSpecList();

	PaginationResult<EcsSpecDTO> getEcsSpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}