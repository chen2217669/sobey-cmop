package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.As2DTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "As2Service", targetNamespace = WsConstants.NS)
public interface As2SoapService {
	DTOResult<As2DTO> findAs2(@WebParam(name = "id") Integer id);

	DTOResult<As2DTO> findAs2ByCode(@WebParam(name = "code") String code);

	IdResult createAs2(@WebParam(name = "as2DTO") As2DTO as2DTO);

	IdResult updateAs2(@WebParam(name = "id") Integer id, @WebParam(name = "as2DTO") As2DTO as2DTO);

	IdResult deleteAs2(@WebParam(name = "id") Integer id);

	DTOListResult<As2DTO> getAs2List();

	PaginationResult<As2DTO> getAs2Pagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}