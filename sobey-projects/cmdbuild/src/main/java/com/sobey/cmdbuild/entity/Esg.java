package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Esg generated by hbm2java
 */
@Entity
@Table(name = "esg", schema = "public")
public class Esg extends BasicEntity {

	private Integer aclNumber;
	private Set<EsgHistory> esgHistories = new HashSet<EsgHistory>(0);
	private Boolean isPublic;
	private String remark;
	private Integer tag;
	private Integer tenants;

	public Esg() {
	}

	@Column(name = "acl_number")
	public Integer getAclNumber() {
		return this.aclNumber;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "esg")
	public Set<EsgHistory> getEsgHistories() {
		return this.esgHistories;
	}

	@Column(name = "is_public")
	public Boolean getIsPublic() {
		return this.isPublic;
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

	public void setAclNumber(Integer aclNumber) {
		this.aclNumber = aclNumber;
	}

	public void setEsgHistories(Set<EsgHistory> esgHistories) {
		this.esgHistories = esgHistories;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
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
