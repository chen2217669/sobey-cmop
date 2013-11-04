package com.sobey.cmdbuild.webservice;

import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.cmdbuild.webservice.response.GetCompanyResult;
import com.sobey.cmdbuild.webservice.response.base.IdResult;
import com.sobey.cmdbuild.webservice.response.base.PaginationResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;

@WebService(name = "CmdbuildService", targetNamespace = WsConstants.NS)
public interface CmdbuildSoapService {

	GetCompanyResult getCompanies(@WebParam(name = "id") Integer id);

	IdResult createCompany(@WebParam(name = "company") CompanyDTO company);

	IdResult updateCompany(@WebParam(name = "id") Integer id, @WebParam(name = "company") CompanyDTO company);

	IdResult deleteCompany(@WebParam(name = "id") Integer id);

	PaginationResult<CompanyDTO> getCompanyDaoPageable(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

}
