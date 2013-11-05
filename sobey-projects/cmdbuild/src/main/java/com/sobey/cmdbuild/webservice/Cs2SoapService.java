package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.Cs2DTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "Cs2Service", targetNamespace = WsConstants.NS)
public interface Cs2SoapService {
	DTOResult<Cs2DTO> findCs2(@WebParam(name = "id") Integer id);

	DTOResult<Cs2DTO> findCs2ByCode(@WebParam(name = "code") String code);

	IdResult createCs2(@WebParam(name = "cs2DTO") Cs2DTO cs2DTO);

	IdResult updateCs2(@WebParam(name = "id") Integer id, @WebParam(name = "cs2DTO") Cs2DTO cs2DTO);

	IdResult deleteCs2(@WebParam(name = "id") Integer id);

	DTOListResult<Cs2DTO> getCs2List();

	PaginationResult<Cs2DTO> getCs2Pagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}