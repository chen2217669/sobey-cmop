package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.TagDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "TagService", targetNamespace = WsConstants.NS)
public interface TagSoapService {
	DTOResult<TagDTO> findTag(@WebParam(name = "id") Integer id);

	DTOResult<TagDTO> findTagByCode(@WebParam(name = "code") String code);

	IdResult createTag(@WebParam(name = "tagDTO") TagDTO tagDTO);

	IdResult updateTag(@WebParam(name = "id") Integer id, @WebParam(name = "tagDTO") TagDTO tagDTO);

	IdResult deleteTag(@WebParam(name = "id") Integer id);

	DTOListResult<TagDTO> getTagList();

	PaginationResult<TagDTO> getTagPagination(@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}