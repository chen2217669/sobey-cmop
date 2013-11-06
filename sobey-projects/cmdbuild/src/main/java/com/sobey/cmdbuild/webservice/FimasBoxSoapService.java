package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.FimasBoxDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "FimasBoxService", targetNamespace = WsConstants.NS)
public interface FimasBoxSoapService {
	DTOResult<FimasBoxDTO> findFimasBox(@WebParam(name = "id") Integer id);

	DTOResult<FimasBoxDTO> findFimasBoxByCode(@WebParam(name = "code") String code);

	IdResult createFimasBox(@WebParam(name = "fimasBoxDTO") FimasBoxDTO fimasBoxDTO);

	IdResult updateFimasBox(@WebParam(name = "id") Integer id, @WebParam(name = "fimasBoxDTO") FimasBoxDTO fimasBoxDTO);

	IdResult deleteFimasBox(@WebParam(name = "id") Integer id);

	DTOListResult<FimasBoxDTO> getFimasBoxList();

	PaginationResult<FimasBoxDTO> getFimasBoxPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}