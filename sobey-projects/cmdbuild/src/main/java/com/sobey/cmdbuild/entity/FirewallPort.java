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
 * FirewallPort generated by hbm2java
 */
@Entity
@Table(name = "firewall_port", schema = "public")
public class FirewallPort extends BasicEntity {

	private Integer connectedTo;
	private Integer firewall;
	private Set<FirewallPortHistory> firewallPortHistories = new HashSet<FirewallPortHistory>(0);
	private Integer ipaddress;
	private String macAddress;
	private String remark;
	private String site;

	public FirewallPort() {
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

	@Column(name = "connected_to")
	public Integer getConnectedTo() {
		return this.connectedTo;
	}

	@Column(name = "\"Description\"", length = 250)
	public String getDescription() {
		return this.description;
	}

	@Column(name = "firewall")
	public Integer getFirewall() {
		return this.firewall;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "firewallPort")
	public Set<FirewallPortHistory> getFirewallPortHistories() {
		return this.firewallPortHistories;
	}

	@Column(name = "ipaddress")
	public Integer getIpaddress() {
		return this.ipaddress;
	}

	@Column(name = "mac_address", length = 50)
	public String getMacAddress() {
		return this.macAddress;
	}

	@Column(name = "\"Notes\"")
	public String getNotes() {
		return this.notes;
	}

	@Column(name = "\"Remark\"", length = 50)
	public String getRemark() {
		return this.remark;
	}

	@Column(name = "\"Site\"", length = 50)
	public String getSite() {
		return this.site;
	}

	@Column(name = "\"Status\"", length = 1)
	public Character getStatus() {
		return this.status;
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

	public void setConnectedTo(Integer connectedTo) {
		this.connectedTo = connectedTo;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFirewall(Integer firewall) {
		this.firewall = firewall;
	}

	public void setFirewallPortHistories(Set<FirewallPortHistory> firewallPortHistories) {
		this.firewallPortHistories = firewallPortHistories;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
