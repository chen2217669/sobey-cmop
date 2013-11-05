package com.sobey.cmdbuild.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cs2", schema = "public")
public class Cs2 extends BasicEntity {

	private Set<Cs2History> cs2Histories = new HashSet<Cs2History>(0);
	private Integer diskSize;
	private Integer es3Spec;
	private Integer fimas;
	private Integer ipaddress;
	private String remark;
	private Integer tag;
	private Integer tenants;

	public Cs2() {
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"BeginDate\"", nullable = false, length = 29)
	public Date getBeginDate() {
		return this.beginDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "cs2")
	public Set<Cs2History> getCs2Histories() {
		return this.cs2Histories;
	}

	@Column(name = "disk_size")
	public Integer getDiskSize() {
		return this.diskSize;
	}

	@Column(name = "es3_spec")
	public Integer getEs3Spec() {
		return this.es3Spec;
	}

	@Column(name = "fimas")
	public Integer getFimas() {
		return this.fimas;
	}

	@Column(name = "ipaddress")
	public Integer getIpaddress() {
		return this.ipaddress;
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

	public void setCs2Histories(Set<Cs2History> cs2Histories) {
		this.cs2Histories = cs2Histories;
	}

	public void setDiskSize(Integer diskSize) {
		this.diskSize = diskSize;
	}

	public void setEs3Spec(Integer es3Spec) {
		this.es3Spec = es3Spec;
	}

	public void setFimas(Integer fimas) {
		this.fimas = fimas;
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

}
