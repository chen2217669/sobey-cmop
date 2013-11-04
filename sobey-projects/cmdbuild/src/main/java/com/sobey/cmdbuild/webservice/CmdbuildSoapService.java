package com.sobey.cmdbuild.webservice;

import java.util.Map;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.base.IdResult;
import com.sobey.cmdbuild.webservice.response.base.PaginationResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;
import com.sobey.cmdbuild.webservice.response.result.CompanyResult;
import com.sobey.cmdbuild.webservice.response.result.plural.CompaniesResult;

/**
 * CMDBuild模块对外暴露的webservice接口.
 * 
 * @author Administrator
 * 
 */
@WebService(name = "CmdbuildService", targetNamespace = WsConstants.NS)
public interface CmdbuildSoapService {

	// ==============================//
	// =========== Comany ===========//
	// ==============================//

	CompanyResult findCompany(@WebParam(name = "id") Integer id);

	IdResult createCompany(@WebParam(name = "companyDTO") CompanyDTO companyDTO);

	IdResult updateCompany(@WebParam(name = "id") Integer id, @WebParam(name = "companyDTO") CompanyDTO companyDTO);

	IdResult deleteCompany(@WebParam(name = "id") Integer id);

	CompaniesResult getCompanies();

	PaginationResult<CompanyDTO> getCompanyPagination(
			@WebParam(name = "searchParams") Map<String, Object> searchParams,
			@WebParam(name = "pageNumber") Integer pageNumber, @WebParam(name = "pageSize") Integer pageSize);

}
