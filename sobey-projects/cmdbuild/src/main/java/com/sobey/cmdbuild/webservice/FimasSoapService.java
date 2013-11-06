package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.FimasDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "FimasService", targetNamespace = WsConstants.NS)
public interface FimasSoapService {
	DTOResult<FimasDTO> findFimas(@WebParam(name = "id") Integer id);

	DTOResult<FimasDTO> findFimasByCode(@WebParam(name = "code") String code);

	IdResult createFimas(@WebParam(name = "fimasDTO") FimasDTO fimasDTO);

	IdResult updateFimas(@WebParam(name = "id") Integer id, @WebParam(name = "fimasDTO") FimasDTO fimasDTO);

	IdResult deleteFimas(@WebParam(name = "id") Integer id);

	DTOListResult<FimasDTO> getFimasList();

	PaginationResult<FimasDTO> getFimasPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}