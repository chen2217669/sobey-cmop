package com.sobey.cmdbuild.webservice;

import java.util.Map;
import javax.jws.WebParam;
import javax.jws.WebService;
import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.dto.CompanyHistoryDTO;
import com.sobey.cmdbuild.webservice.response.result.DTOListResult;
import com.sobey.cmdbuild.webservice.response.result.DTOResult;
import com.sobey.cmdbuild.webservice.response.result.IdResult;
import com.sobey.cmdbuild.webservice.response.result.PaginationResult;

@WebService(name = "CompanyHistoryService", targetNamespace = WsConstants.NS)
public interface CompanyHistorySoapService {
	DTOResult<CompanyHistoryDTO> findCompanyHistory(@WebParam(name = "id") Integer id);

	DTOResult<CompanyHistoryDTO> findCompanyHistoryByCode(@WebParam(name = "code") String code);

	IdResult createCompanyHistory(@WebParam(name = "companyHistoryDTO") CompanyHistoryDTO companyHistoryDTO);

	IdResult updateCompanyHistory(@WebParam(name = "id") Integer id,
			@WebParam(name = "companyHistoryDTO") CompanyHistoryDTO companyHistoryDTO);

	IdResult deleteCompanyHistory(@WebParam(name = "id") Integer id);

	DTOListResult<CompanyHistoryDTO> getCompanyHistoryList();

	PaginationResult<CompanyHistoryDTO> getCompanyHistoryPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);
}