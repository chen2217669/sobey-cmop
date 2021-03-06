package com.sobey.cmdbuild.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * DnsHistory generated by hbm2java
 */
@Entity
@Table(name = "dns_history", schema = "public")
public class DnsHistory extends BasicEntity {

	private String cnameDomain;
	private Dns dns;
	private String domainName;
	private Integer domainType;
	private Date endDate;
	private String remark;
	private Integer tag;
	private Integer tenants;

	public DnsHistory() {
	}

	@Column(name = "cname_domain", length = 100)
	public String getCnameDomain() {
		return this.cnameDomain;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Dns getDns() {
		return this.dns;
	}

	@Column(name = "domain_name", length = 100)
	public String getDomainName() {
		return this.domainName;
	}

	@Column(name = "domain_type")
	public Integer getDomainType() {
		return this.domainType;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return this.endDate;
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
	}

	@Column(name = "tag")
	public Integer getTag() {
		return this.tag;
	}

	@Column(name = "tenants")
	public Integer getTenants() {
		return this.tenants;
	}

	public void setCnameDomain(String cnameDomain) {
		this.cnameDomain = cnameDomain;
	}

	public void setDns(Dns dns) {
		this.dns = dns;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public void setDomainType(Integer domainType) {
		this.domainType = domainType;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

}
