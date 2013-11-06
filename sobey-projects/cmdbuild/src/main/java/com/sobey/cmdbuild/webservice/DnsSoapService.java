package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.DnsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "DnsService", targetNamespace = WsConstants.NS)
public interface DnsSoapService {
	DTOResult<DnsDTO> findDns(@WebParam(name = "id") Integer id);

	DTOResult<DnsDTO> findDnsByCode(@WebParam(name = "code") String code);

	IdResult createDns(@WebParam(name = "dnsDTO") DnsDTO dnsDTO);

	IdResult updateDns(@WebParam(name = "id") Integer id, @WebParam(name = "dnsDTO") DnsDTO dnsDTO);

	IdResult deleteDns(@WebParam(name = "id") Integer id);

	DTOListResult<DnsDTO> getDnsList();

	PaginationResult<DnsDTO> getDnsPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}