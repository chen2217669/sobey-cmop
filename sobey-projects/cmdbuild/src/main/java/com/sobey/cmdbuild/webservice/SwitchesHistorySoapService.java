package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "SwitchesHistoryService", targetNamespace = WsConstants.NS)
public interface SwitchesHistorySoapService {
	DTOResult<SwitchesHistoryDTO> findSwitchesHistory(@WebParam(name = "id") Integer id);

	DTOResult<SwitchesHistoryDTO> findSwitchesHistoryByCode(@WebParam(name = "code") String code);

	IdResult createSwitchesHistory(@WebParam(name = "switchesHistoryDTO") SwitchesHistoryDTO switchesHistoryDTO);

	IdResult updateSwitchesHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "switchesHistoryDTO") SwitchesHistoryDTO switchesHistoryDTO);

	IdResult deleteSwitchesHistory(@WebParam(name = "id") Integer id);

	DTOListResult<SwitchesHistoryDTO> getSwitchesHistoryList();

	PaginationResult<SwitchesHistoryDTO> getSwitchesHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}