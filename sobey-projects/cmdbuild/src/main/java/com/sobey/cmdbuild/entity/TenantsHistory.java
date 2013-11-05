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
 * TenantsHistory generated by hbm2java
 */
@Entity
@Table(name = "tenants_history", schema = "public")
public class TenantsHistory extends BasicEntity {

	private Double accontBalance;
	private Integer company;
	private String email;
	private Date endDate;
	private String password;
	private String phone;
	private String remark;
	private Tenants tenants;

	public TenantsHistory() {
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

	@Column(name = "\"Company\"")
	public Integer getCompany() {
		return this.company;
	}

	@Column(name = "\"Email\"", length = 100)
	public String getEmail() {
		return this.email;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return this.endDate;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Tenants getTenants() {
		return this.tenants;
	}

	public void setAccontBalance(Double accontBalance) {
		this.accontBalance = accontBalance;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public void setTenants(Tenants tenants) {
		this.tenants = tenants;
	}

}
