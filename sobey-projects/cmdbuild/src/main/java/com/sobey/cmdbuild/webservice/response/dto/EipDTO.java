package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "EipDTO", namespace = WsConstants.NS)
public class EipDTO {

	private Integer bandwidth;
	private Date beginDate;
	private String code;
	private String description;
	private Integer eipSpec;

	private Integer eipStatus;
	private Integer id;
	private Integer ipaddress;
	private String remark;
	// Tag Integer 标签ID
	// Tenants Integer 租户ID
	// Bandwidth Integer 公网IP带宽，单位为Mpbs(默认1M)
	// EIPSpec Integer EIP规格
	// Ipaddress Integer IP池中ipaddress的ID
	// EIPStatus Integer EIP状态（可用、绑定中）
	private Integer tag;
	private Integer tenants;

	public Integer getBandwidth() {
		return bandwidth;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getEipSpec() {
		return eipSpec;
	}

	public Integer getEipStatus() {
		return eipStatus;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getTag() {
		return tag;
	}

	public Integer getTenants() {
		return tenants;
	}

	public void setBandwidth(Integer bandwidth) {
		this.bandwidth = bandwidth;
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

	public void setEipSpec(Integer eipSpec) {
		this.eipSpec = eipSpec;
	}

	public void setEipStatus(Integer eipStatus) {
		this.eipStatus = eipStatus;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}