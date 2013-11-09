package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "DnsDTO", namespace = WsConstants.NS)
public class DnsDTO {

	private Date beginDate;
	private String cnameDomain;
	private String code;
	private String description;
	private String domainName;

	private Integer domainType;
	private Integer id;
	private String remark;
	// Tag Integer 标签ID
	// Tenants Integer 租户ID
	// DomainType Integer 域名类型 (GSLB,A,CNAME)
	// DomainName String 域名
	// CnameDomain String CNAME 域名
	private Integer tag;
	private Integer tenants;

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCnameDomain() {
		return cnameDomain;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public String getDomainName() {
		return domainName;
	}

	public Integer getDomainType() {
		return domainType;
	}

	public Integer getId() {
		return id;
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

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCnameDomain(String cnameDomain) {
		this.cnameDomain = cnameDomain;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public void setDomainType(Integer domainType) {
		this.domainType = domainType;
	}

	public void setId(Integer id) {
		this.id = id;
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