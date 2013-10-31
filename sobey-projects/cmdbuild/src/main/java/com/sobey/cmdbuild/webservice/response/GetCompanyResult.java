package com.sobey.cmdbuild.webservice.response;

import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.webservice.WsConstants;
import com.sobey.cmdbuild.webservice.response.base.WSResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;

@XmlType(name = "GetCompanyResult", namespace = WsConstants.NS)
public class GetCompanyResult extends WSResult {
	private CompanyDTO companyDTO;

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}

}
