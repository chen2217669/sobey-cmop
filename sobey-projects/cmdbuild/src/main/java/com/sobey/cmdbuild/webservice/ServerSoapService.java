package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.ServerDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "ServerService", targetNamespace = WsConstants.NS)
public interface ServerSoapService {
	DTOResult<ServerDTO> findServer(@WebParam(name = "id") Integer id);

	DTOResult<ServerDTO> findServerByCode(@WebParam(name = "code") String code);

	IdResult createServer(@WebParam(name = "serverDTO") ServerDTO serverDTO);

	IdResult updateServer(@WebParam(name = "id") Integer id, @WebParam(name = "serverDTO") ServerDTO serverDTO);

	IdResult deleteServer(@WebParam(name = "id") Integer id);

	DTOListResult<ServerDTO> getServerList();

	PaginationResult<ServerDTO> getServerPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}