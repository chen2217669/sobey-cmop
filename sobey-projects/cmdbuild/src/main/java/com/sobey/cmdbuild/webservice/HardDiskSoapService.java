package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "HardDiskService", targetNamespace = WsConstants.NS)
public interface HardDiskSoapService {
	DTOResult<HardDiskDTO> findHardDisk(@WebParam(name = "id") Integer id);

	DTOResult<HardDiskDTO> findHardDiskByCode(@WebParam(name = "code") String code);

	IdResult createHardDisk(@WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO);

	IdResult updateHardDisk(@WebParam(name = "id") Integer id, @WebParam(name = "hardDiskDTO") HardDiskDTO hardDiskDTO);

	IdResult deleteHardDisk(@WebParam(name = "id") Integer id);

	DTOListResult<HardDiskDTO> getHardDiskList();

	PaginationResult<HardDiskDTO> getHardDiskPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}