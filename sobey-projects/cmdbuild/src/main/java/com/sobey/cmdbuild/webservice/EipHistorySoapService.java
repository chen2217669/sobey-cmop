package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EipHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EipHistoryService", targetNamespace = WsConstants.NS)
public interface EipHistorySoapService {
	DTOResult<EipHistoryDTO> findEipHistory(@WebParam(name = "id") Integer id);

	DTOResult<EipHistoryDTO> findEipHistoryByCode(@WebParam(name = "code") String code);

	IdResult createEipHistory(@WebParam(name = "eipHistoryDTO") EipHistoryDTO eipHistoryDTO);

	IdResult updateEipHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipHistoryDTO") EipHistoryDTO eipHistoryDTO);

	IdResult deleteEipHistory(@WebParam(name = "id") Integer id);

	DTOListResult<EipHistoryDTO> getEipHistoryList();

	PaginationResult<EipHistoryDTO> getEipHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}