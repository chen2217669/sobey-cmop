package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.ConsumptionsDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "ConsumptionsService", targetNamespace = WsConstants.NS)
public interface ConsumptionsSoapService {
	DTOResult<ConsumptionsDTO> findConsumptions(@WebParam(name = "id") Integer id);

	DTOResult<ConsumptionsDTO> findConsumptionsByCode(@WebParam(name = "code") String code);

	IdResult createConsumptions(@WebParam(name = "consumptionsDTO") ConsumptionsDTO consumptionsDTO);

	IdResult updateConsumptions(@WebParam(name = "id") Integer id,
			@WebParam(name = "consumptionsDTO") ConsumptionsDTO consumptionsDTO);

	IdResult deleteConsumptions(@WebParam(name = "id") Integer id);

	DTOListResult<ConsumptionsDTO> getConsumptionsList();

	PaginationResult<ConsumptionsDTO> getConsumptionsPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}