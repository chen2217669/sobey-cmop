package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.EcsSpecHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "EcsSpecHistoryService", targetNamespace = WsConstants.NS)
public interface EcsSpecHistorySoapService {
	DTOResult<EcsSpecHistoryDTO> findEcsSpecHistory(@WebParam(name = "id") Integer id);

	DTOResult<EcsSpecHistoryDTO> findEcsSpecHistoryByCode(@WebParam(name = "code") String code);

	IdResult createEcsSpecHistory(@WebParam(name = "ecsSpecHistoryDTO") EcsSpecHistoryDTO ecsSpecHistoryDTO);

	IdResult updateEcsSpecHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "ecsSpecHistoryDTO") EcsSpecHistoryDTO ecsSpecHistoryDTO);

	IdResult deleteEcsSpecHistory(@WebParam(name = "id") Integer id);

	DTOListResult<EcsSpecHistoryDTO> getEcsSpecHistoryList();

	PaginationResult<EcsSpecHistoryDTO> getEcsSpecHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}