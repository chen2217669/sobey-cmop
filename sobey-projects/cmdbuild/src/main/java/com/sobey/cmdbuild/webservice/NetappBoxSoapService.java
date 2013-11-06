package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.NetappBoxDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "NetappBoxService", targetNamespace = WsConstants.NS)
public interface NetappBoxSoapService {
	DTOResult<NetappBoxDTO> findNetappBox(@WebParam(name = "id") Integer id);

	DTOResult<NetappBoxDTO> findNetappBoxByCode(@WebParam(name = "code") String code);

	IdResult createNetappBox(@WebParam(name = "netappBoxDTO") NetappBoxDTO netappBoxDTO);

	IdResult updateNetappBox(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappBoxDTO") NetappBoxDTO netappBoxDTO);

	IdResult deleteNetappBox(@WebParam(name = "id") Integer id);

	DTOListResult<NetappBoxDTO> getNetappBoxList();

	PaginationResult<NetappBoxDTO> getNetappBoxPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}