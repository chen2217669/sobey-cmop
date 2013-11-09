package com.sobey.cmdbuild.webservice.response.result;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.constants.WsConstants;

/**
 * 针对DTO List的返回
 * 
 * @author Administrator
 * 
 * @param <T>
 */
@XmlType(name = "DTOListResult", namespace = WsConstants.NS)
public class DTOListResult<T> extends WSResult {

	private List<T> dtos;

	public DTOListResult() {
	}

	public DTOListResult(List<T> dtos) {
		this.dtos = dtos;
	}

	@XmlElementWrapper(name = "dtoList")
	@XmlElement(name = "dto")
	public List<T> getDtos() {
		return dtos;
	}

	public void setDtos(List<T> dtos) {
		this.dtos = dtos;
	}

}
