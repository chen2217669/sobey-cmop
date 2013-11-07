package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "ConsumptionsDTO", namespace = WsConstants.NS)
public class ConsumptionsDTO {

	private Date beginDate;
	private String code;
	private String description;
	private Integer id;
	private String remark;

	private Date serviceEnd;
	private Date serviceStart;
	// Tenants Integer 租户Id
	// ServiceStart Date 服务开始时间
	// ServiceEnd Date 服务结束时间
	private Integer tenants;

	public Date getBeginDate() {
		return beginDate;
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

	public String getRemark() {
		return remark;
	}

	public Date getServiceEnd() {
		return serviceEnd;
	}

	public Date getServiceStart() {
		return serviceStart;
	}

	public Integer getTenants() {
		return tenants;
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

	public void setId(Integer id) {
		this.id = id;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setServiceEnd(Date serviceEnd) {
		this.serviceEnd = serviceEnd;
	}

	public void setServiceStart(Date serviceStart) {
		this.serviceStart = serviceStart;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}