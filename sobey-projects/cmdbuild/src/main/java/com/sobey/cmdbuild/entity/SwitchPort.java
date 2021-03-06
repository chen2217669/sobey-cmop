package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * SwitchPort generated by hbm2java
 */
@Entity
@Table(name = "switch_port", schema = "public")
public class SwitchPort extends BasicEntity {

	private Integer connectedTo;
	private Integer ipaddress;
	private String macAddress;
	private String remark;
	private String site;
	private Integer switches;
	private Set<SwitchPortHistory> switchPortHistories = new HashSet<SwitchPortHistory>(0);

	public SwitchPort() {
	}

	@Column(name = "connected_to")
	public Integer getConnectedTo() {
		return this.connectedTo;
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

	@Column(name = "\"Site\"", length = 50)
	public String getSite() {
		return this.site;
	}

	@Column(name = "switches")
	public Integer getSwitches() {
		return this.switches;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "switchPort")
	public Set<SwitchPortHistory> getSwitchPortHistories() {
		return this.switchPortHistories;
	}

	public void setConnectedTo(Integer connectedTo) {
		this.connectedTo = connectedTo;
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

	public void setSite(String site) {
		this.site = site;
	}

	public void setSwitches(Integer switches) {
		this.switches = switches;
	}

	public void setSwitchPortHistories(Set<SwitchPortHistory> switchPortHistories) {
		this.switchPortHistories = switchPortHistories;
	}

}
