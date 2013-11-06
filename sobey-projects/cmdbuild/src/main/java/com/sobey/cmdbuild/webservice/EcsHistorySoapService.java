package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EcsHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EcsHistoryService", targetNamespace = WsConstants.NS)
public interface EcsHistorySoapService {
	DTOResult<EcsHistoryDTO> findEcsHistory(@WebParam(name = "id") Integer id);

	DTOResult<EcsHistoryDTO> findEcsHistoryByCode(@WebParam(name = "code") String code);

	IdResult createEcsHistory(@WebParam(name = "ecsHistoryDTO") EcsHistoryDTO ecsHistoryDTO);

	IdResult updateEcsHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "ecsHistoryDTO") EcsHistoryDTO ecsHistoryDTO);

	IdResult deleteEcsHistory(@WebParam(name = "id") Integer id);

	DTOListResult<EcsHistoryDTO> getEcsHistoryList();

	PaginationResult<EcsHistoryDTO> getEcsHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}