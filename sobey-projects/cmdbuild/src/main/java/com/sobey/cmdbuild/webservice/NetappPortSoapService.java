package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.NetappPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "NetappPortService", targetNamespace = WsConstants.NS)
public interface NetappPortSoapService {
	DTOResult<NetappPortDTO> findNetappPort(@WebParam(name = "id") Integer id);

	DTOResult<NetappPortDTO> findNetappPortByCode(@WebParam(name = "code") String code);

	IdResult createNetappPort(@WebParam(name = "netappPortDTO") NetappPortDTO netappPortDTO);

	IdResult updateNetappPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "netappPortDTO") NetappPortDTO netappPortDTO);

	IdResult deleteNetappPort(@WebParam(name = "id") Integer id);

	DTOListResult<NetappPortDTO> getNetappPortList();

	PaginationResult<NetappPortDTO> getNetappPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}