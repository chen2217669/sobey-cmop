package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.SwitchPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "SwitchPortService", targetNamespace = WsConstants.NS)
public interface SwitchPortSoapService {
	DTOResult<SwitchPortDTO> findSwitchPort(@WebParam(name = "id") Integer id);

	DTOResult<SwitchPortDTO> findSwitchPortByCode(@WebParam(name = "code") String code);

	IdResult createSwitchPort(@WebParam(name = "switchPortDTO") SwitchPortDTO switchPortDTO);

	IdResult updateSwitchPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "switchPortDTO") SwitchPortDTO switchPortDTO);

	IdResult deleteSwitchPort(@WebParam(name = "id") Integer id);

	DTOListResult<SwitchPortDTO> getSwitchPortList();

	PaginationResult<SwitchPortDTO> getSwitchPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}