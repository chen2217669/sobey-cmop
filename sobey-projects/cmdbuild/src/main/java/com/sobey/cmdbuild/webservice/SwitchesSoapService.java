package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.SwitchesDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "SwitchesService", targetNamespace = WsConstants.NS)
public interface SwitchesSoapService {
	DTOResult<SwitchesDTO> findSwitches(@WebParam(name = "id") Integer id);

	DTOResult<SwitchesDTO> findSwitchesByCode(@WebParam(name = "code") String code);

	IdResult createSwitches(@WebParam(name = "switchesDTO") SwitchesDTO switchesDTO);

	IdResult updateSwitches(@WebParam(name = "id") Integer id, @WebParam(name = "switchesDTO") SwitchesDTO switchesDTO);

	IdResult deleteSwitches(@WebParam(name = "id") Integer id);

	DTOListResult<SwitchesDTO> getSwitchesList();

	PaginationResult<SwitchesDTO> getSwitchesPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}