package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.NicDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "NicService", targetNamespace = WsConstants.NS)
public interface NicSoapService {
	DTOResult<NicDTO> findNic(@WebParam(name = "id") Integer id);

	DTOResult<NicDTO> findNicByCode(@WebParam(name = "code") String code);

	IdResult createNic(@WebParam(name = "nicDTO") NicDTO nicDTO);

	IdResult updateNic(@WebParam(name = "id") Integer id, @WebParam(name = "nicDTO") NicDTO nicDTO);

	IdResult deleteNic(@WebParam(name = "id") Integer id);

	DTOListResult<NicDTO> getNicList();

	PaginationResult<NicDTO> getNicPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}