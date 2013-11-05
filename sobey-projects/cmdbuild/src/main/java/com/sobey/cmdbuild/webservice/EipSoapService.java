package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EipDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EipService", targetNamespace = WsConstants.NS)
public interface EipSoapService {
	DTOResult<EipDTO> findEip(@WebParam(name = "id") Integer id);

	DTOResult<EipDTO> findEipByCode(@WebParam(name = "code") String code);

	IdResult createEip(@WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult updateEip(@WebParam(name = "id") Integer id, @WebParam(name = "eipDTO") EipDTO eipDTO);

	IdResult deleteEip(@WebParam(name = "id") Integer id);

	DTOListResult<EipDTO> getEipList();

	PaginationResult<EipDTO> getEipPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}