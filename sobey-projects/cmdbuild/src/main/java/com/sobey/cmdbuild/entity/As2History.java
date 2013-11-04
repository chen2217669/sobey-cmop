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

@Entity
@Table(name = "as2_history", schema = "public")
public class As2History extends BasicEntity {

	private As2 as2;
	private Integer diskSize;
	private Date endDate;
	private Integer es3Spec;
	private Integer ipaddress;
	private Integer netAppController;
	private String remark;
	private Integer tag;
	private Integer tenants;
	private String volumePath;
	private Integer volumeType;

	public As2History() {
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "\"CurrentId\"", nullable = false)
	public As2 getAs2() {
		return this.as2;
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

	@Column(name = "disk_size")
	public Integer getDiskSize() {
		return this.diskSize;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"EndDate\"", nullable = false, length = 29)
	public Date getEndDate() {
		return this.endDate;
	}

	@Column(name = "es3_spec")
	public Integer getEs3Spec() {
		return this.es3Spec;
	}

	@Column(name = "ipaddress")
	public Integer getIpaddress() {
		return this.ipaddress;
	}

	@Column(name = "netApp_controller")
	public Integer getNetAppController() {
		return this.netAppController;
	}

	@Column(name = "\"Notes\"")
	public String getNotes() {
		return this.notes;
	}

	@Column(name = "\"Remark\"", length = 250)
	public String getRemark() {
		return this.remark;
	}

	@Column(name = "\"Status\"", length = 1)
	public Character getStatus() {
		return this.status;
	}

	@Column(name = "tag")
	public Integer getTag() {
		return this.tag;
	}

	@Column(name = "tenants")
	public Integer getTenants() {
		return this.tenants;
	}

	@Column(name = "\"User\"", length = 40)
	public String getUser() {
		return this.user;
	}

	@Column(name = "volume_path", length = 100)
	public String getVolumePath() {
		return this.volumePath;
	}

	@Column(name = "volume_type")
	public Integer getVolumeType() {
		return this.volumeType;
	}

	public void setAs2(As2 as2) {
		this.as2 = as2;
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

	public void setDiskSize(Integer diskSize) {
		this.diskSize = diskSize;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setEs3Spec(Integer es3Spec) {
		this.es3Spec = es3Spec;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setNetAppController(Integer netAppController) {
		this.netAppController = netAppController;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setVolumePath(String volumePath) {
		this.volumePath = volumePath;
	}

	public void setVolumeType(Integer volumeType) {
		this.volumeType = volumeType;
	}

}
