package com.sobey.cmdbuild.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Memory generated by hbm2java
 */
@Entity
@Table(name = "memory", schema = "public")
public class Memory extends BasicEntity {

	private Integer brand;
	private Integer fimas;
	private Integer frequency;
	private Integer idc;
	private Set<MemoryHistory> memoryHistories = new HashSet<MemoryHistory>(0);
	private Integer ram;
	private Integer server;

	public Memory() {
	}

	@Column(name = "\"Brand\"")
	public Integer getBrand() {
		return this.brand;
	}

	@Column(name = "fimas")
	public Integer getFimas() {
		return this.fimas;
	}

	@Column(name = "\"Frequency\"")
	public Integer getFrequency() {
		return this.frequency;
	}

	@Column(name = "\"IDC\"")
	public Integer getIdc() {
		return this.idc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "memory")
	public Set<MemoryHistory> getMemoryHistories() {
		return this.memoryHistories;
	}

	@Column(name = "ram")
	public Integer getRam() {
		return this.ram;
	}

	@Column(name = "server")
	public Integer getServer() {
		return this.server;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public void setFimas(Integer fimas) {
		this.fimas = fimas;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setMemoryHistories(Set<MemoryHistory> memoryHistories) {
		this.memoryHistories = memoryHistories;
	}

	public void setRam(Integer ram) {
		this.ram = ram;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

}
