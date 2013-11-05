package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.FimasPortDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "FimasPortService", targetNamespace = WsConstants.NS)
public interface FimasPortSoapService {
	DTOResult<FimasPortDTO> findFimasPort(@WebParam(name = "id") Integer id);

	DTOResult<FimasPortDTO> findFimasPortByCode(@WebParam(name = "code") String code);

	IdResult createFimasPort(@WebParam(name = "fimasPortDTO") FimasPortDTO fimasPortDTO);

	IdResult updateFimasPort(@WebParam(name = "id") Integer id,
			@WebParam(name = "fimasPortDTO") FimasPortDTO fimasPortDTO);

	IdResult deleteFimasPort(@WebParam(name = "id") Integer id);

	DTOListResult<FimasPortDTO> getFimasPortList();

	PaginationResult<FimasPortDTO> getFimasPortPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}