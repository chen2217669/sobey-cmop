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
@Table(name = "as2", schema = "public")
public class As2 extends BasicEntity {

	private Set<As2History> as2Histories = new HashSet<As2History>(0);
	private Integer diskSize;
	private Integer es3Spec;
	private Integer ipaddress;
	private Integer netAppController;
	private String remark;
	private Integer tag;
	private Integer tenants;
	private String volumePath;
	private Integer volumeType;

	public As2() {
	}

	public As2(Integer id, String idClass, Date beginDate) {
		this.id = id;
		this.idClass = idClass;
		this.beginDate = beginDate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "as2")
	public Set<As2History> getAs2Histories() {
		return this.as2Histories;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"BeginDate\"", nullable = false, length = 29)
	public Date getBeginDate() {
		return this.beginDate;
	}

	@Column(name = "disk_size")
	public Integer getDiskSize() {
		return this.diskSize;
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

	@Column(name = "volume_path", length = 100)
	public String getVolumePath() {
		return this.volumePath;
	}

	@Column(name = "volume_type")
	public Integer getVolumeType() {
		return this.volumeType;
	}

	public void setAs2Histories(Set<As2History> as2Histories) {
		this.as2Histories = as2Histories;
	}

	public void setDiskSize(Integer diskSize) {
		this.diskSize = diskSize;
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public void setVolumePath(String volumePath) {
		this.volumePath = volumePath;
	}

	public void setVolumeType(Integer volumeType) {
		this.volumeType = volumeType;
	}

}
