package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.DnsHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "DnsHistoryService", targetNamespace = WsConstants.NS)
public interface DnsHistorySoapService {
	DTOResult<DnsHistoryDTO> findDnsHistory(@WebParam(name = "id") Integer id);

	DTOResult<DnsHistoryDTO> findDnsHistoryByCode(@WebParam(name = "code") String code);

	IdResult createDnsHistory(@WebParam(name = "dnsHistoryDTO") DnsHistoryDTO dnsHistoryDTO);

	IdResult updateDnsHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "dnsHistoryDTO") DnsHistoryDTO dnsHistoryDTO);

	IdResult deleteDnsHistory(@WebParam(name = "id") Integer id);

	DTOListResult<DnsHistoryDTO> getDnsHistoryList();

	PaginationResult<DnsHistoryDTO> getDnsHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}