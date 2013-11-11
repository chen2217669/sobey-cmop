package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "EcsSpecDTO", namespace = WsConstants.NS)
public class EcsSpecDTO {

	private Date beginDate;
	private Integer brand;
	private String code;
	private Integer cpuNumber;
	private String description;
	private String diskSize;
	private Integer id;
	private Integer memory;
	private Double price;
	private String remark;

	public Date getBeginDate() {
		return beginDate;
	}

	public Integer getBrand() {
		return brand;
	}

	public String getCode() {
		return code;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public String getDescription() {
		return description;
	}

	public String getDiskSize() {
		return diskSize;
	}

	public Integer getId() {
		return id;
	}

	public Integer getMemory() {
		return memory;
	}

	public Double getPrice() {
		return price;
	}

	public String getRemark() {
		return remark;
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

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDiskSize(String diskSize) {
		this.diskSize = diskSize;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}