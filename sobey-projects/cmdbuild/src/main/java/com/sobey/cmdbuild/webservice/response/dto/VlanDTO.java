package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "VlanDTO", namespace = WsConstants.NS)
public class VlanDTO {

	private Date beginDate;
	private String code;
	private String description;
	private String gateway;
	private Integer id;

	// IDC Integer IDC Id
	// Segment String 网段
	// Netmask String 子网掩码
	// Gateway String 网关

	private Integer idc;
	private String netmask;
	private String remark;
	private String segment;

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getGateway() {
		return gateway;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIdc() {
		return idc;
	}

	public String getNetmask() {
		return netmask;
	}

	public String getRemark() {
		return remark;
	}

	public String getSegment() {
		return segment;
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

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSegment(String segment) {
		this.segment = segment;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}