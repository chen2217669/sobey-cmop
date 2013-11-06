package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.IdcHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "IdcHistoryService", targetNamespace = WsConstants.NS)
public interface IdcHistorySoapService {
	DTOResult<IdcHistoryDTO> findIdcHistory(@WebParam(name = "id") Integer id);

	DTOResult<IdcHistoryDTO> findIdcHistoryByCode(@WebParam(name = "code") String code);

	IdResult createIdcHistory(@WebParam(name = "idcHistoryDTO") IdcHistoryDTO idcHistoryDTO);

	IdResult updateIdcHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "idcHistoryDTO") IdcHistoryDTO idcHistoryDTO);

	IdResult deleteIdcHistory(@WebParam(name = "id") Integer id);

	DTOListResult<IdcHistoryDTO> getIdcHistoryList();

	PaginationResult<IdcHistoryDTO> getIdcHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}