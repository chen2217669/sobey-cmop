package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.google.common.collect.Lists;
import com.sobey.cmdbuild.webservice.WsConstants;

@XmlRootElement
@XmlType(name = "Company", namespace = WsConstants.NS)
public class CompanyDTO {

	private Integer id;
	private String code;
	private String description;
	private Date beginDate;
	private String zip;
	private String phone;
	private String address;
	private String remark;
	private List<CompanyHistoryDTO> historyList = Lists.newArrayList();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@XmlElementWrapper(name = "historyList")
	@XmlElement(name = "companyHistory")
	public List<CompanyHistoryDTO> getHistoryList() {
		return historyList;
	}

	public void setHistoryList(List<CompanyHistoryDTO> historyList) {
		this.historyList = historyList;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
