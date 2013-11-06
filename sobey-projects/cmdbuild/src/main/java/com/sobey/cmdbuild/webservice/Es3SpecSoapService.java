package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.Es3SpecDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "Es3SpecService", targetNamespace = WsConstants.NS)
public interface Es3SpecSoapService {
	DTOResult<Es3SpecDTO> findEs3Spec(@WebParam(name = "id") Integer id);

	DTOResult<Es3SpecDTO> findEs3SpecByCode(@WebParam(name = "code") String code);

	IdResult createEs3Spec(@WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO);

	IdResult updateEs3Spec(@WebParam(name = "id") Integer id, @WebParam(name = "es3SpecDTO") Es3SpecDTO es3SpecDTO);

	IdResult deleteEs3Spec(@WebParam(name = "id") Integer id);

	DTOListResult<Es3SpecDTO> getEs3SpecList();

	PaginationResult<Es3SpecDTO> getEs3SpecPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}