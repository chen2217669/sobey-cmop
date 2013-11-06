package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.NetappControllerDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "NetappControllerService", targetNamespace = WsConstants.NS)
public interface NetappControllerSoapService {
	DTOResult<NetappControllerDTO> findNetappController(@WebParam(name = "id") Integer id);

	DTOResult<NetappControllerDTO> findNetappControllerByCode(@WebParam(name = "code") String code);

	IdResult createNetappController(@WebParam(name = "netappControllerDTO") NetappControllerDTO netappControllerDTO);

	IdResult updateNetappController(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappControllerDTO") NetappControllerDTO netappControllerDTO);

	IdResult deleteNetappController(@WebParam(name = "id") Integer id);

	DTOListResult<NetappControllerDTO> getNetappControllerList();

	PaginationResult<NetappControllerDTO> getNetappControllerPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}