package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "RackDTO", namespace = WsConstants.NS)
public class RackDTO {

	private Date beginDate;
	private Integer brand;
	private String code;
	private String description;
	private String gdzcSn;
	private Integer height;
	private Integer id;
	private Integer idc;
	private String model;
	private Integer power;
	private Double price;
	private String remark;
	private String sn;
	private Integer unitNumber;
	private IdcDTO idcDTO;

	public IdcDTO getIdcDTO() {
		return idcDTO;
	}

	public void setIdcDTO(IdcDTO idcDTO) {
		this.idcDTO = idcDTO;
	}

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

	public String getGdzcSn() {
		return gdzcSn;
	}

	public Integer getHeight() {
		return height;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIdc() {
		return idc;
	}

	public String getModel() {
		return model;
	}

	public Integer getPower() {
		return power;
	}

	public Double getPrice() {
		return price;
	}

	public String getRemark() {
		return remark;
	}

	public String getSn() {
		return sn;
	}

	public Integer getUnitNumber() {
		return unitNumber;
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

	public void setGdzcSn(String gdzcSn) {
		this.gdzcSn = gdzcSn;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setPower(Integer power) {
		this.power = power;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public void setUnitNumber(Integer unitNumber) {
		this.unitNumber = unitNumber;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}