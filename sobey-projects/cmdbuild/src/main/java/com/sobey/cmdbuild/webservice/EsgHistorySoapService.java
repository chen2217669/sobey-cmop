package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EsgHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EsgHistoryService", targetNamespace = WsConstants.NS)
public interface EsgHistorySoapService {
	DTOResult<EsgHistoryDTO> findEsgHistory(@WebParam(name = "id") Integer id);

	DTOResult<EsgHistoryDTO> findEsgHistoryByCode(@WebParam(name = "code") String code);

	IdResult createEsgHistory(@WebParam(name = "esgHistoryDTO") EsgHistoryDTO esgHistoryDTO);

	IdResult updateEsgHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "esgHistoryDTO") EsgHistoryDTO esgHistoryDTO);

	IdResult deleteEsgHistory(@WebParam(name = "id") Integer id);

	DTOListResult<EsgHistoryDTO> getEsgHistoryList();

	PaginationResult<EsgHistoryDTO> getEsgHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}