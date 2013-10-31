package com.sobey.cmdbuild.entity;

// Generated 2013-10-28 14:02:09 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Company generated by hbm2java
 */
@Entity
@Table(name = "company", schema = "public")
public class Company extends BasicEntity {

	private String zip;
	private String phone;
	private String address;
	private String remark;
	private Set<CompanyHistory> companyHistories = new HashSet<CompanyHistory>(0);

	public Company() {
	}

	public Company(Integer id, String idClass, Date beginDate) {
		this.id = id;
		this.idClass = idClass;
		this.beginDate = beginDate;
	}

	public Company(Integer id, String idClass, String code, String description, Character status, String user,
			Date beginDate, String notes, String zip, String phone, String address, String remark,
			Set<CompanyHistory> companyHistories) {
		this.id = id;
		this.idClass = idClass;
		this.code = code;
		this.description = description;
		this.status = status;
		this.user = user;
		this.beginDate = beginDate;
		this.notes = notes;
		this.zip = zip;
		this.phone = phone;
		this.address = address;
		this.remark = remark;
		this.companyHistories = companyHistories;
	}

	@Column(name = "\"Zip\"", length = 100)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "\"Phone\"", length = 100)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "\"Address\"", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	public Set<CompanyHistory> getCompanyHistories() {
		return this.companyHistories;
	}

	public void setCompanyHistories(Set<CompanyHistory> companyHistories) {
		this.companyHistories = companyHistories;
	}

}
