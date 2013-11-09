package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "EipPolicyDTO", namespace = WsConstants.NS)
public class EipPolicyDTO {

	private Date beginDate;
	private String code;
	private String description;
	private Integer eip;
	private Integer id;

	// EIP Integer EIP ID
	// SourcePort Integer 源端口
	// TargetPort Integer 目标端口
	// Protocol Integer 协议ID

	private Integer protocol;
	private String remark;
	private Integer sourcePort;
	private Integer targetPort;

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getEip() {
		return eip;
	}

	public Integer getId() {
		return id;
	}

	public Integer getProtocol() {
		return protocol;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getSourcePort() {
		return sourcePort;
	}

	public Integer getTargetPort() {
		return targetPort;
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

	public void setEip(Integer eip) {
		this.eip = eip;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProtocol(Integer protocol) {
		this.protocol = protocol;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSourcePort(Integer sourcePort) {
		this.sourcePort = sourcePort;
	}

	public void setTargetPort(Integer targetPort) {
		this.targetPort = targetPort;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}