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
 * ElbPolicy generated by hbm2java
 */
@Entity
@Table(name = "elb_policy", schema = "public")
public class ElbPolicy extends BasicEntity {

	private Integer elb;
	private Set<ElbPolicyHistory> elbPolicyHistories = new HashSet<ElbPolicyHistory>(0);
	private Integer protocol;
	private Integer sourcePort;
	private Integer targetPort;

	public ElbPolicy() {
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

	@Column(name = "elb")
	public Integer getElb() {
		return this.elb;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "elbPolicy")
	public Set<ElbPolicyHistory> getElbPolicyHistories() {
		return this.elbPolicyHistories;
	}

	@Column(name = "\"Notes\"")
	public String getNotes() {
		return this.notes;
	}

	@Column(name = "protocol")
	public Integer getProtocol() {
		return this.protocol;
	}

	@Column(name = "source_port")
	public Integer getSourcePort() {
		return this.sourcePort;
	}

	@Column(name = "\"Status\"", length = 1)
	public Character getStatus() {
		return this.status;
	}

	@Column(name = "target_port")
	public Integer getTargetPort() {
		return this.targetPort;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public void setElb(Integer elb) {
		this.elb = elb;
	}

	public void setElbPolicyHistories(Set<ElbPolicyHistory> elbPolicyHistories) {
		this.elbPolicyHistories = elbPolicyHistories;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setProtocol(Integer protocol) {
		this.protocol = protocol;
	}

	public void setSourcePort(Integer sourcePort) {
		this.sourcePort = sourcePort;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	public void setTargetPort(Integer targetPort) {
		this.targetPort = targetPort;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
