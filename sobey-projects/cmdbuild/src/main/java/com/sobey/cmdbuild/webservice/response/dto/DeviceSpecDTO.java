package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "DeviceSpecDTO", namespace = WsConstants.NS)
public class DeviceSpecDTO {

	private Date beginDate;
	private Integer brand;
	private String code;
	private String cpuModel;
	private Integer cpuNumber;
	private String description;
	private Integer deviceType;
	private Integer hdnumber;
	private Integer hight;
	private Integer id;
	private Integer maintenance;
	private Integer nicNumber;
	private Integer power;
	private Double price;

	private Integer ramNumber;

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

	public String getCpuModel() {
		return cpuModel;
	}

	public Integer getCpuNumber() {
		return cpuNumber;
	}

	public String getDescription() {
		return description;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public Integer getHdnumber() {
		return hdnumber;
	}

	public Integer getHight() {
		return hight;
	}

	public Integer getId() {
		return id;
	}

	public Integer getMaintenance() {
		return maintenance;
	}

	public Integer getNicNumber() {
		return nicNumber;
	}

	public Integer getPower() {
		return power;
	}

	public Double getPrice() {
		return price;
	}

	public Integer getRamNumber() {
		return ramNumber;
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

	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}

	public void setCpuNumber(Integer cpuNumber) {
		this.cpuNumber = cpuNumber;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public void setHdnumber(Integer hdnumber) {
		this.hdnumber = hdnumber;
	}

	public void setHight(Integer hight) {
		this.hight = hight;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMaintenance(Integer maintenance) {
		this.maintenance = maintenance;
	}

	public void setNicNumber(Integer nicNumber) {
		this.nicNumber = nicNumber;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setRamNumber(Integer ramNumber) {
		this.ramNumber = ramNumber;
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