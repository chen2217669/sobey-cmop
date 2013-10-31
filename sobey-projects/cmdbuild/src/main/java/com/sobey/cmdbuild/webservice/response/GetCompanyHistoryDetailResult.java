package com.sobey.cmdbuild.webservice.response;

import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.webservice.WsConstants;
import com.sobey.cmdbuild.webservice.response.base.WSResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyHistoryDTO;

@XmlType(name = "GetCompanyHistoryDetailResult", namespace = WsConstants.NS)
public class GetCompanyHistoryDetailResult extends WSResult {

	private CompanyHistoryDTO companyHistoryDTO;

	public GetCompanyHistoryDetailResult() {
	}

	public GetCompanyHistoryDetailResult(CompanyHistoryDTO companyHistoryDTO) {
		this.companyHistoryDTO = companyHistoryDTO;
	}

	public CompanyHistoryDTO getCompanyHistoryDTO() {
		return companyHistoryDTO;
	}

	public void setCompanyHistoryDTO(CompanyHistoryDTO companyHistoryDTO) {
		this.companyHistoryDTO = companyHistoryDTO;
	}

}
