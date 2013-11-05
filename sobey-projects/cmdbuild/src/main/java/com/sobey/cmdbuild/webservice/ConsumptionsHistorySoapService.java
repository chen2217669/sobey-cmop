package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.ConsumptionsHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "ConsumptionsHistoryService", targetNamespace = WsConstants.NS)
public interface ConsumptionsHistorySoapService {
	DTOResult<ConsumptionsHistoryDTO> findConsumptionsHistory(@WebParam(name = "id") Integer id);

	DTOResult<ConsumptionsHistoryDTO> findConsumptionsHistoryByCode(@WebParam(name = "code") String code);

	IdResult createConsumptionsHistory(
			@WebParam(name = "consumptionsHistoryDTO") ConsumptionsHistoryDTO consumptionsHistoryDTO);

	IdResult updateConsumptionsHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "consumptionsHistoryDTO") ConsumptionsHistoryDTO consumptionsHistoryDTO);

	IdResult deleteConsumptionsHistory(@WebParam(name = "id") Integer id);

	DTOListResult<ConsumptionsHistoryDTO> getConsumptionsHistoryList();

	PaginationResult<ConsumptionsHistoryDTO> getConsumptionsHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}