package com.sobey.cmdbuild.webservice.response.base;

import javax.xml.bind.annotation.XmlType;

import com.sobey.cmdbuild.webservice.WsConstants;

/**
 * 创建某个对象返回的 通用IdResult.
 * 
 * @author badqiu
 */
@XmlType(name = "IdResult", namespace = WsConstants.NS)
public class IdResult extends WSResult {
	private Integer id;

	public IdResult() {
	}

	public IdResult(Integer id) {
		super();
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
