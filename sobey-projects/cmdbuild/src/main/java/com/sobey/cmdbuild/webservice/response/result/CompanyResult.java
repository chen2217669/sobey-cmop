package com.sobey.cmdbuild.webservice.response.result;

import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.base.WSResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;

@XmlType(name = "CompanyResult", namespace = WsConstants.NS)
public class CompanyResult extends WSResult {

	private CompanyDTO companyDTO;

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}

}
