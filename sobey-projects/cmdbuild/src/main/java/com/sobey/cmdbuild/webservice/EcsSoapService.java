package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EcsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EcsService", targetNamespace = WsConstants.NS)
public interface EcsSoapService {
	DTOResult<EcsDTO> findEcs(@WebParam(name = "id") Integer id);

	DTOResult<EcsDTO> findEcsByCode(@WebParam(name = "code") String code);

	IdResult createEcs(@WebParam(name = "ecsDTO") EcsDTO ecsDTO);

	IdResult updateEcs(@WebParam(name = "id") Integer id, @WebParam(name = "ecsDTO") EcsDTO ecsDTO);

	IdResult deleteEcs(@WebParam(name = "id") Integer id);

	DTOListResult<EcsDTO> getEcsList();

	PaginationResult<EcsDTO> getEcsPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}