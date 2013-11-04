package com.sobey.cmdbuild.webservice.response.result.plural;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.constants.WsConstants;
import com.sobey.cmdbuild.webservice.response.base.WSResult;
import com.sobey.cmdbuild.webservice.response.dto.CompanyDTO;

@XmlType(name = "CompaniesResult", namespace = WsConstants.NS)
public class CompaniesResult extends WSResult {

	private List<CompanyDTO> dtos;

	public CompaniesResult() {
	}

	public CompaniesResult(List<CompanyDTO> dtos) {
		this.dtos = dtos;
	}

	@XmlElementWrapper(name = "companyDTOList")
	@XmlElement(name = "companyDTO")
	public List<CompanyDTO> getDtos() {
		return dtos;
	}

	public void setDtos(List<CompanyDTO> dtos) {
		this.dtos = dtos;
	}

}
