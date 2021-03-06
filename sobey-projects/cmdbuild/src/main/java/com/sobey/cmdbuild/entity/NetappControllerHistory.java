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
 * NetappControllerHistory generated by hbm2java
 */
@Entity
@Table(name = "netapp_controller_history", schema = "public")
public class NetappControllerHistory extends BasicEntity {

	private Integer deviceSpec;

	private Date endDate;
	private String gdzcSn;
	private Integer idc;
	private Integer ipaddress;
	private Integer netAppBox;
	private NetappController netappController;
	private Integer rack;
	private String remark;
	private String site;
	private String sn;

	public NetappControllerHistory() {
	}

	@Column(name = "device_spec")
	public Integer getDeviceSpec() {
		return this.deviceSpec;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return this.endDate;
	}

	@Column(name = "\"GdzcSn\"", length = 50)
	public String getGdzcSn() {
		return this.gdzcSn;
	}

	@Column(name = "\"IDC\"")
	public Integer getIdc() {
		return this.idc;
	}

	@Column(name = "\"IPAddress\"")
	public Integer getIpaddress() {
		return this.ipaddress;
	}

	@Column(name = "\"netApp_box\"")
	public Integer getNetAppBox() {
		return this.netAppBox;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public NetappController getNetappController() {
		return this.netappController;
	}

	@Column(name = "\"Rack\"")
	public Integer getRack() {
		return this.rack;
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
	}

	@Column(name = "\"Site\"", length = 50)
	public String getSite() {
		return this.site;
	}

	@Column(name = "\"SN\"", length = 50)
	public String getSn() {
		return this.sn;
	}

	public void setDeviceSpec(Integer deviceSpec) {
		this.deviceSpec = deviceSpec;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setGdzcSn(String gdzcSn) {
		this.gdzcSn = gdzcSn;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setNetAppBox(Integer netAppBox) {
		this.netAppBox = netAppBox;
	}

	public void setNetappController(NetappController netappController) {
		this.netappController = netappController;
	}

	public void setRack(Integer rack) {
		this.rack = rack;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

}
