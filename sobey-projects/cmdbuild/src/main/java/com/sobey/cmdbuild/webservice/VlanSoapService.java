package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.VlanDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "VlanService", targetNamespace = WsConstants.NS)
public interface VlanSoapService {
	DTOResult<VlanDTO> findVlan(@WebParam(name = "id") Integer id);

	DTOResult<VlanDTO> findVlanByCode(@WebParam(name = "code") String code);

	IdResult createVlan(@WebParam(name = "vlanDTO") VlanDTO vlanDTO);

	IdResult updateVlan(@WebParam(name = "id") Integer id, @WebParam(name = "vlanDTO") VlanDTO vlanDTO);

	IdResult deleteVlan(@WebParam(name = "id") Integer id);

	DTOListResult<VlanDTO> getVlanList();

	PaginationResult<VlanDTO> getVlanPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}