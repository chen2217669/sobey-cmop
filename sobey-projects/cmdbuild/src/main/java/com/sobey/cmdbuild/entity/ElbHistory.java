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
 * ElbHistory generated by hbm2java
 */
@Entity
@Table(name = "elb_history", schema = "public")
public class ElbHistory extends BasicEntity {

	private Elb elb;
	private Date endDate;
	private Integer ipaddress;
	private Boolean isSession;
	private String remark;
	private Integer tag;
	private Integer tenants;

	public ElbHistory() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Elb getElb() {
		return this.elb;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return this.endDate;
	}

	@Column(name = "ipaddress")
	public Integer getIpaddress() {
		return this.ipaddress;
	}

	@Column(name = "is_session")
	public Boolean getIsSession() {
		return this.isSession;
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

	public void setElb(Elb elb) {
		this.elb = elb;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setIsSession(Boolean isSession) {
		this.isSession = isSession;
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
