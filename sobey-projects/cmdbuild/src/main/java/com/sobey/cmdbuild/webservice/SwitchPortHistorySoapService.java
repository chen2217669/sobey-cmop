package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "SwitchPortHistoryService", targetNamespace = WsConstants.NS)
public interface SwitchPortHistorySoapService {
	DTOResult<SwitchPortHistoryDTO> findSwitchPortHistory(@WebParam(name = "id") Integer id);

	DTOResult<SwitchPortHistoryDTO> findSwitchPortHistoryByCode(@WebParam(name = "code") String code);

	IdResult createSwitchPortHistory(@WebParam(name = "switchPortHistoryDTO") SwitchPortHistoryDTO switchPortHistoryDTO);

	IdResult updateSwitchPortHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "switchPortHistoryDTO") SwitchPortHistoryDTO switchPortHistoryDTO);

	IdResult deleteSwitchPortHistory(@WebParam(name = "id") Integer id);

	DTOListResult<SwitchPortHistoryDTO> getSwitchPortHistoryList();

	PaginationResult<SwitchPortHistoryDTO> getSwitchPortHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}