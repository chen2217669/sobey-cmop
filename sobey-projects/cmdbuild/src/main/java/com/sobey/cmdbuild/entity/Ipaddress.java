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
 * Ipaddress generated by hbm2java
 */
@Entity
@Table(name = "ipaddress", schema = "public")
public class Ipaddress extends BasicEntity {

	private String gateway;
	private Set<IpaddressHistory> ipaddressHistories = new HashSet<IpaddressHistory>(0);
	private Integer ipaddressPool;
	private Integer ipaddressStatus;
	private Integer isp;
	private String netMask;
	private Integer vlan;

	public Ipaddress() {
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

	@Column(name = "\"Description\"", length = 250)
	public String getDescription() {
		return this.description;
	}

	@Column(name = "gateway", length = 50)
	public String getGateway() {
		return this.gateway;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ipaddress")
	public Set<IpaddressHistory> getIpaddressHistories() {
		return this.ipaddressHistories;
	}

	@Column(name = "ipaddress_pool")
	public Integer getIpaddressPool() {
		return this.ipaddressPool;
	}

	@Column(name = "ipaddress_status")
	public Integer getIpaddressStatus() {
		return this.ipaddressStatus;
	}

	@Column(name = "\"ISP\"")
	public Integer getIsp() {
		return this.isp;
	}

	@Column(name = "net_mask", length = 50)
	public String getNetMask() {
		return this.netMask;
	}

	@Column(name = "\"Notes\"")
	public String getNotes() {
		return this.notes;
	}

	@Column(name = "\"Status\"", length = 1)
	public Character getStatus() {
		return this.status;
	}

	@Column(name = "\"User\"", length = 40)
	public String getUser() {
		return this.user;
	}

	@Column(name = "vlan")
	public Integer getVlan() {
		return this.vlan;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setIpaddressHistories(Set<IpaddressHistory> ipaddressHistories) {
		this.ipaddressHistories = ipaddressHistories;
	}

	public void setIpaddressPool(Integer ipaddressPool) {
		this.ipaddressPool = ipaddressPool;
	}

	public void setIpaddressStatus(Integer ipaddressStatus) {
		this.ipaddressStatus = ipaddressStatus;
	}

	public void setIsp(Integer isp) {
		this.isp = isp;
	}

	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setVlan(Integer vlan) {
		this.vlan = vlan;
	}

}
