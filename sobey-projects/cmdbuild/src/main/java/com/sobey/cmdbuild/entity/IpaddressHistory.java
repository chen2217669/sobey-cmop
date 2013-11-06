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
 * IpaddressHistory generated by hbm2java
 */
@Entity
@Table(name = "ipaddress_history", schema = "public")
public class IpaddressHistory extends BasicEntity {

	private Date endDate;

	private String gateway;
	private Ipaddress ipaddress;
	private Integer ipaddressPool;
	private Integer ipaddressStatus;
	private Integer isp;
	private String netMask;
	private Integer vlan;

	public IpaddressHistory() {
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return this.endDate;
	}

	@Column(name = "gateway", length = 50)
	public String getGateway() {
		return this.gateway;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public Ipaddress getIpaddress() {
		return this.ipaddress;
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

	@Column(name = "vlan")
	public Integer getVlan() {
		return this.vlan;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setIpaddress(Ipaddress ipaddress) {
		this.ipaddress = ipaddress;
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

	public void setVlan(Integer vlan) {
		this.vlan = vlan;
	}

}