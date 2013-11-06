package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "dns", schema = "public")
public class Dns extends BasicEntity {

	private String cnameDomain;
	private Set<DnsHistory> dnsHistories = new HashSet<DnsHistory>(0);
	private String domainName;
	private Integer domainType;
	private String remark;
	private Integer tag;
	private Integer tenants;

	public Dns() {
	}

	@Column(name = "cname_domain", length = 100)
	public String getCnameDomain() {
		return this.cnameDomain;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "dns")
	public Set<DnsHistory> getDnsHistories() {
		return this.dnsHistories;
	}

	@Column(name = "domain_name", length = 100)
	public String getDomainName() {
		return this.domainName;
	}

	@Column(name = "domain_type")
	public Integer getDomainType() {
		return this.domainType;
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

	public void setDnsHistories(Set<DnsHistory> dnsHistories) {
		this.dnsHistories = dnsHistories;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public void setDomainType(Integer domainType) {
		this.domainType = domainType;
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
