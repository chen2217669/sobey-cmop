package com.sobey.cmdbuild.webservice;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.sobey.cmdbuild.webservice.response.GetCompanyResult;
import com.sobey.cmdbuild.webservice.response.base.IdResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;

@WebService(name = "CmdbuildService", targetNamespace = WsConstants.NS)
public interface CmdbuildSoapService {

	GetCompanyResult getCompany(@WebParam(name = "id") Integer id);

	IdResult createCompany(@WebParam(name = "company") CompanyDTO company);

	IdResult updateCompany(@WebParam(name = "id") Integer id, @WebParam(name = "company") CompanyDTO company);

	IdResult deleteCompany(@WebParam(name = "id") Integer id);

}
