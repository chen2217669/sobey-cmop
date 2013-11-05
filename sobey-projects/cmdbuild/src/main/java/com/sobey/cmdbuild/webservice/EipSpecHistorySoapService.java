package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EipSpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EipSpecHistoryService", targetNamespace = WsConstants.NS)
public interface EipSpecHistorySoapService {
	DTOResult<EipSpecHistoryDTO> findEipSpecHistory(@WebParam(name = "id") Integer id);

	DTOResult<EipSpecHistoryDTO> findEipSpecHistoryByCode(@WebParam(name = "code") String code);

	IdResult createEipSpecHistory(@WebParam(name = "eipSpecHistoryDTO") EipSpecHistoryDTO eipSpecHistoryDTO);

	IdResult updateEipSpecHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "eipSpecHistoryDTO") EipSpecHistoryDTO eipSpecHistoryDTO);

	IdResult deleteEipSpecHistory(@WebParam(name = "id") Integer id);

	DTOListResult<EipSpecHistoryDTO> getEipSpecHistoryList();

	PaginationResult<EipSpecHistoryDTO> getEipSpecHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}