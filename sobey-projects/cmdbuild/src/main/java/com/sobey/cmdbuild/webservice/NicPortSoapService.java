package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.NicPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "NicPortService", targetNamespace = WsConstants.NS)
public interface NicPortSoapService {
	DTOResult<NicPortDTO> findNicPort(@WebParam(name = "id") Integer id);

	DTOResult<NicPortDTO> findNicPortByCode(@WebParam(name = "code") String code);

	IdResult createNicPort(@WebParam(name = "nicPortDTO") NicPortDTO nicPortDTO);

	IdResult updateNicPort(@WebParam(name = "id") Integer id, @WebParam(name = "nicPortDTO") NicPortDTO nicPortDTO);

	IdResult deleteNicPort(@WebParam(name = "id") Integer id);

	DTOListResult<NicPortDTO> getNicPortList();

	PaginationResult<NicPortDTO> getNicPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}