package com.sobey.cmdbuild.webservice.response.result;

import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlType(name = "DTOResult", namespace = WsConstants.NS)
public class DTOResult<T> extends WSResult {

	private T dto;

	public T getDto() {
		return dto;
	}

	public void setDto(T dto) {
		this.dto = dto;
	}

}
