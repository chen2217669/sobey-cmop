package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.TagHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "TagHistoryService", targetNamespace = WsConstants.NS)
public interface TagHistorySoapService {
	DTOResult<TagHistoryDTO> findTagHistory(@WebParam(name = "id") Integer id);

	DTOResult<TagHistoryDTO> findTagHistoryByCode(@WebParam(name = "code") String code);

	IdResult createTagHistory(@WebParam(name = "tagHistoryDTO") TagHistoryDTO tagHistoryDTO);

	IdResult updateTagHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "tagHistoryDTO") TagHistoryDTO tagHistoryDTO);

	IdResult deleteTagHistory(@WebParam(name = "id") Integer id);

	DTOListResult<TagHistoryDTO> getTagHistoryList();

	PaginationResult<TagHistoryDTO> getTagHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}