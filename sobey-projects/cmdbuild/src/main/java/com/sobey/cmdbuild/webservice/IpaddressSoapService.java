package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.IpaddressDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "IpaddressService", targetNamespace = WsConstants.NS)
public interface IpaddressSoapService {
	DTOResult<IpaddressDTO> findIpaddress(@WebParam(name = "id") Integer id);

	DTOResult<IpaddressDTO> findIpaddressByCode(@WebParam(name = "code") String code);

	IdResult createIpaddress(@WebParam(name = "ipaddressDTO") IpaddressDTO ipaddressDTO);

	IdResult updateIpaddress(@WebParam(name = "id") Integer id,
			@WebParam(name = "ipaddressDTO") IpaddressDTO ipaddressDTO);

	IdResult deleteIpaddress(@WebParam(name = "id") Integer id);

	DTOListResult<IpaddressDTO> getIpaddressList();

	PaginationResult<IpaddressDTO> getIpaddressPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}