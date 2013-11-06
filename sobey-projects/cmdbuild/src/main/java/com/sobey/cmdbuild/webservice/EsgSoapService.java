package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EsgDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EsgService", targetNamespace = WsConstants.NS)
public interface EsgSoapService {
	DTOResult<EsgDTO> findEsg(@WebParam(name = "id") Integer id);

	DTOResult<EsgDTO> findEsgByCode(@WebParam(name = "code") String code);

	IdResult createEsg(@WebParam(name = "esgDTO") EsgDTO esgDTO);

	IdResult updateEsg(@WebParam(name = "id") Integer id, @WebParam(name = "esgDTO") EsgDTO esgDTO);

	IdResult deleteEsg(@WebParam(name = "id") Integer id);

	DTOListResult<EsgDTO> getEsgList();

	PaginationResult<EsgDTO> getEsgPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}