package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.ElbDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "ElbService", targetNamespace = WsConstants.NS)
public interface ElbSoapService {
	DTOResult<ElbDTO> findElb(@WebParam(name = "id") Integer id);

	DTOResult<ElbDTO> findElbByCode(@WebParam(name = "code") String code);

	IdResult createElb(@WebParam(name = "elbDTO") ElbDTO elbDTO);

	IdResult updateElb(@WebParam(name = "id") Integer id, @WebParam(name = "elbDTO") ElbDTO elbDTO);

	IdResult deleteElb(@WebParam(name = "id") Integer id);

	DTOListResult<ElbDTO> getElbList();

	PaginationResult<ElbDTO> getElbPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}