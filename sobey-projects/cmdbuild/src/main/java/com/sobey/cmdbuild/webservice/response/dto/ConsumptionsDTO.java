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
	private Integer consumptionsStatus;
	private String description;
	private Integer id;
	private String identifier;
	private String remark;
	private Date serviceEnd;
	private Date serviceStart;
	private Integer serviceType;
	private Double spending;
	private Integer tenants;
	private TenantsDTO tenantsDTO;

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public Integer getConsumptionsStatus() {
		return consumptionsStatus;
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return id;
	}

	public String getIdentifier() {
		return identifier;
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

	public Integer getServiceType() {
		return serviceType;
	}

	public Double getSpending() {
		return spending;
	}

	public Integer getTenants() {
		return tenants;
	}

	public TenantsDTO getTenantsDTO() {
		return tenantsDTO;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setConsumptionsStatus(Integer consumptionsStatus) {
		this.consumptionsStatus = consumptionsStatus;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
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

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public void setSpending(Double spending) {
		this.spending = spending;
	}

	public void setTenants(Integer tenants) {
		this.tenants = tenants;
	}

	public void setTenantsDTO(TenantsDTO tenantsDTO) {
		this.tenantsDTO = tenantsDTO;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}