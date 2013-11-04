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
@Table(name = "consumptions", schema = "public")
public class Consumptions extends BasicEntity {

	private Set<ConsumptionsHistory> consumptionsHistories = new HashSet<ConsumptionsHistory>(0);
	private Integer consumptionsStatus;
	private String identifier;
	private String remark;
	private Date serviceEnd;
	private Date serviceStart;
	private Integer serviceType;
	private Double spending;
	private Integer tenants;

	public Consumptions() {
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consumptions")
	public Set<ConsumptionsHistory> getConsumptionsHistories() {
		return this.consumptionsHistories;
	}

	@Column(name = "consumptions_status")
	public Integer getConsumptionsStatus() {
		return this.consumptionsStatus;
	}

	@Column(name = "\"Description\"", length = 250)
	public String getDescription() {
		return this.description;
	}

	@Column(name = "identifier", length = 100)
	public String getIdentifier() {
		return this.identifier;
	}

	@Column(name = "\"Notes\"")
	public String getNotes() {
		return this.notes;
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "service_end", length = 13)
	public Date getServiceEnd() {
		return this.serviceEnd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "service_start", length = 13)
	public Date getServiceStart() {
		return this.serviceStart;
	}

	@Column(name = "\"ServiceType\"")
	public Integer getServiceType() {
		return this.serviceType;
	}

	@Column(name = "\"Spending\"", precision = 17, scale = 17)
	public Double getSpending() {
		return this.spending;
	}

	@Column(name = "\"Status\"", length = 1)
	public Character getStatus() {
		return this.status;
	}

	@Column(name = "tenants")
	public Integer getTenants() {
		return this.tenants;
	}

	@Column(name = "\"User\"", length = 40)
	public String getUser() {
		return this.user;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setConsumptionsHistories(Set<ConsumptionsHistory> consumptionsHistories) {
		this.consumptionsHistories = consumptionsHistories;
	}

	public void setConsumptionsStatus(Integer consumptionsStatus) {
		this.consumptionsStatus = consumptionsStatus;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setServiceEnd(Date serviceEnd) {
		this.serviceEnd = serviceEnd;
	}

	public void setServiceStart(Date serviceStart) {
		this.serviceStart = serviceStart;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public void setSpending(Double spending) {
		this.spending = spending;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
