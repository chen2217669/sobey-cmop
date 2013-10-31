package com.sobey.cmdbuild.webservice.response;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.webservice.WsConstants;
import com.sobey.cmdbuild.webservice.response.base.WSResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;

@XmlType(name = "SearchCompanyResult", namespace = WsConstants.NS)
public class SearchCompanyResult extends WSResult {

	private List<CompanyDTO> companyList;

	public SearchCompanyResult() {
	}

	public SearchCompanyResult(List<CompanyDTO> companyList) {
		this.companyList = companyList;
	}

	@XmlElementWrapper(name = "companyList")
	@XmlElement(name = "company")
	public List<CompanyDTO> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<CompanyDTO> companyList) {
		this.companyList = companyList;
	}

}
