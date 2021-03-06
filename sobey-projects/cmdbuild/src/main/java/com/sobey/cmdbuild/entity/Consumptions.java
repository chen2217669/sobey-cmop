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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "consumptions")
	public Set<ConsumptionsHistory> getConsumptionsHistories() {
		return this.consumptionsHistories;
	}

	@Column(name = "consumptions_status")
	public Integer getConsumptionsStatus() {
		return this.consumptionsStatus;
	}

	@Column(name = "identifier", length = 100)
	public String getIdentifier() {
		return this.identifier;
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

	@Column(name = "tenants")
	public Integer getTenants() {
		return this.tenants;
	}

	public void setConsumptionsHistories(Set<ConsumptionsHistory> consumptionsHistories) {
		this.consumptionsHistories = consumptionsHistories;
	}

	public void setConsumptionsStatus(Integer consumptionsStatus) {
		this.consumptionsStatus = consumptionsStatus;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

}
