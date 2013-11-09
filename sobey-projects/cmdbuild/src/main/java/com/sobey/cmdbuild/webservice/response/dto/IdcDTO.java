package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "IdcDTO", namespace = WsConstants.NS)
public class IdcDTO {

	private String address;
	private Date beginDate;
	private String city;
	private String code;
	private String description;
	private Integer id;
	private String phone;
	private String remark;
	private String zip;

	public String getAddress() {
		return address;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCity() {
		return city;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return id;
	}

	public String getPhone() {
		return phone;
	}

	public String getRemark() {
		return remark;
	}

	public String getZip() {
		return zip;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}