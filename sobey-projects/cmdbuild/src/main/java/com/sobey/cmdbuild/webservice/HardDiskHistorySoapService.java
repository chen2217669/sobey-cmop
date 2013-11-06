package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.HardDiskHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "HardDiskHistoryService", targetNamespace = WsConstants.NS)
public interface HardDiskHistorySoapService {
	DTOResult<HardDiskHistoryDTO> findHardDiskHistory(@WebParam(name = "id") Integer id);

	DTOResult<HardDiskHistoryDTO> findHardDiskHistoryByCode(@WebParam(name = "code") String code);

	IdResult createHardDiskHistory(@WebParam(name = "hardDiskHistoryDTO") HardDiskHistoryDTO hardDiskHistoryDTO);

	IdResult updateHardDiskHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "hardDiskHistoryDTO") HardDiskHistoryDTO hardDiskHistoryDTO);

	IdResult deleteHardDiskHistory(@WebParam(name = "id") Integer id);

	DTOListResult<HardDiskHistoryDTO> getHardDiskHistoryList();

	PaginationResult<HardDiskHistoryDTO> getHardDiskHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}