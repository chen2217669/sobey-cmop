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
 * ServerPortHistory generated by hbm2java
 */
@Entity
@Table(name = "server_port_history", schema = "public")
public class ServerPortHistory extends BasicEntity {

	private Integer connectedTo;
	private Date endDate;
	private Integer ipaddress;
	private String macAddress;
	private String remark;
	private Integer server;
	private ServerPort serverPort;
	private String site;

	public ServerPortHistory() {
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

	@Column(name = "mac_address", length = 50)
	public String getMacAddress() {
		return this.macAddress;
	}

	@Column(name = "\"Remark\"", length = 50)
	public String getRemark() {
		return this.remark;
	}

	@Column(name = "server")
	public Integer getServer() {
		return this.server;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public ServerPort getServerPort() {
		return this.serverPort;
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

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

	public void setServerPort(ServerPort serverPort) {
		this.serverPort = serverPort;
	}

	public void setSite(String site) {
		this.site = site;
	}

}
