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

/**
 * Tenants generated by hbm2java
 */
@Entity
@Table(name = "tenants", schema = "public")
public class Tenants extends BasicEntity {

	private Double accontBalance;
	private Integer company;
	private String email;
	private String password;
	private String phone;
	private String remark;
	private Set<TenantsHistory> tenantsHistories = new HashSet<TenantsHistory>(0);

	public Tenants() {
	}

	@Column(name = "accont_balance", precision = 17, scale = 17)
	public Double getAccontBalance() {
		return this.accontBalance;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"BeginDate\"", nullable = false, length = 29)
	public Date getBeginDate() {
		return this.beginDate;
	}

	@Column(name = "\"Code\"", length = 100)
	public String getCode() {
		return this.code;
	}

	@Column(name = "\"Company\"")
	public Integer getCompany() {
		return this.company;
	}

	@Column(name = "\"Description\"", length = 250)
	public String getDescription() {
		return this.description;
	}

	@Column(name = "\"Email\"", length = 100)
	public String getEmail() {
		return this.email;
	}

	@Column(name = "\"Notes\"")
	public String getNotes() {
		return this.notes;
	}

	@Column(name = "\"Password\"", length = 100)
	public String getPassword() {
		return this.password;
	}

	@Column(name = "\"Phone\"", length = 100)
	public String getPhone() {
		return this.phone;
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
	}

	@Column(name = "\"Status\"", length = 1)
	public Character getStatus() {
		return this.status;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tenants")
	public Set<TenantsHistory> getTenantsHistories() {
		return this.tenantsHistories;
	}

	@Column(name = "\"User\"", length = 40)
	public String getUser() {
		return this.user;
	}

	public void setAccontBalance(Double accontBalance) {
		this.accontBalance = accontBalance;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public void setTenantsHistories(Set<TenantsHistory> tenantsHistories) {
		this.tenantsHistories = tenantsHistories;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
