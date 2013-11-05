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
 * LoadBalancerPortHistory generated by hbm2java
 */
@Entity
@Table(name = "load_balancer_port_history", schema = "public")
public class LoadBalancerPortHistory extends BasicEntity {

	private Integer connectedTo;

	private Date endDate;
	private Integer ipaddress;
	private Integer loadBalancer;
	private LoadBalancerPort loadBalancerPort;
	private String macAddress;
	private String remark;
	private String site;

	public LoadBalancerPortHistory() {
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"BeginDate\"", nullable = false, length = 29)
	public Date getBeginDate() {
		return this.beginDate;
	}

	@Column(name = "connected_to")
	public Integer getConnectedTo() {
		return this.connectedTo;
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

	@Column(name = "load_balancer")
	public Integer getLoadBalancer() {
		return this.loadBalancer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public LoadBalancerPort getLoadBalancerPort() {
		return this.loadBalancerPort;
	}

	@Column(name = "mac_address", length = 50)
	public String getMacAddress() {
		return this.macAddress;
	}

	@Column(name = "\"Remark\"", length = 50)
	public String getRemark() {
		return this.remark;
	}

	@Column(name = "\"Site\"", length = 50)
	public String getSite() {
		return this.site;
	}

	public void setConnectedTo(Integer connectedTo) {
		this.connectedTo = connectedTo;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setLoadBalancer(Integer loadBalancer) {
		this.loadBalancer = loadBalancer;
	}

	public void setLoadBalancerPort(LoadBalancerPort loadBalancerPort) {
		this.loadBalancerPort = loadBalancerPort;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSite(String site) {
		this.site = site;
	}

}
