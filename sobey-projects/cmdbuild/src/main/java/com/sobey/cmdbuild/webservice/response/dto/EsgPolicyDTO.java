package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "EsgPolicyDTO", namespace = WsConstants.NS)
public class EsgPolicyDTO {

	private Date beginDate;
	private String code;
	private String description;
	private Integer esg;
	private Integer id;

	// ESG Integer ESG ID
	// SourceIP String 源IP
	// TargetIP String 目标IP
	// Port String 端口
	// Protocol Integer 协议ID

	private String port;
	private String protocol;
	private String remark;
	private String sourceIP;
	private String targetIP;

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getEsg() {
		return esg;
	}

	public Integer getId() {
		return id;
	}

	public String getPort() {
		return port;
	}

	public String getProtocol() {
		return protocol;
	}

	public String getRemark() {
		return remark;
	}

	public String getSourceIP() {
		return sourceIP;
	}

	public String getTargetIP() {
		return targetIP;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEsg(Integer esg) {
		this.esg = esg;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}

	public void setTargetIP(String targetIP) {
		this.targetIP = targetIP;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}