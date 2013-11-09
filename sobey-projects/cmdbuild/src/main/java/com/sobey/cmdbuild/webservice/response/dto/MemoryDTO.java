package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "MemoryDTO", namespace = WsConstants.NS)
public class MemoryDTO {

	private Date beginDate;
	private Integer brand;
	private String code;
	private String description;
	private Integer fimas;

	// IDC Integer IDC ID
	// Server Integer Server ID
	// Fimas Integer Fimas ID
	// Brand Integer Brand ID
	// Frequency Integer Frequency ID
	// ram Integer 内存大小(G)

	private Integer frequency;
	private Integer id;
	private Integer idc;
	private Integer ram;
	private String remark;
	private Integer server;

	public Date getBeginDate() {
		return beginDate;
	}

	public Integer getBrand() {
		return brand;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getFimas() {
		return fimas;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIdc() {
		return idc;
	}

	public Integer getRam() {
		return ram;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getServer() {
		return server;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setBrand(Integer brand) {
		this.brand = brand;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFimas(Integer fimas) {
		this.fimas = fimas;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setRam(Integer ram) {
		this.ram = ram;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}