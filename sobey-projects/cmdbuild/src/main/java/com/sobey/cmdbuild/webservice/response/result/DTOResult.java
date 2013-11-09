package com.sobey.cmdbuild.webservice.response.result;

import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.constants.WsConstants;

/**
 * 针对单个DTO的返回
 * 
 * @author Administrator
 * 
 * @param <T>
 */
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
