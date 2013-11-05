package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.ServerPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "ServerPortService", targetNamespace = WsConstants.NS)
public interface ServerPortSoapService {
	DTOResult<ServerPortDTO> findServerPort(@WebParam(name = "id") Integer id);

	DTOResult<ServerPortDTO> findServerPortByCode(@WebParam(name = "code") String code);

	IdResult createServerPort(@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO);

	IdResult updateServerPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "serverPortDTO") ServerPortDTO serverPortDTO);

	IdResult deleteServerPort(@WebParam(name = "id") Integer id);

	DTOListResult<ServerPortDTO> getServerPortList();

	PaginationResult<ServerPortDTO> getServerPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}