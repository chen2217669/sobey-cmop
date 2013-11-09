package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "EsgDTO", namespace = WsConstants.NS)
public class EsgDTO {

	private Integer aclNumber;
	private Date beginDate;
	private String code;
	private String description;
	private Integer id;

	// Tag Integer 标签ID
	// Tenants Integer 租户ID
	// ACLNumber Integer 用于安全组脚本的参数
	// IsPublic Boolean 是否公用

	private Integer isPublic;
	private String remark;
	private Integer tag;
	private Integer tenants;

	public Integer getAclNumber() {
		return aclNumber;
	}

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

	public Integer getIsPublic() {
		return isPublic;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getTag() {
		return tag;
	}

	public Integer getTenants() {
		return tenants;
	}

	public void setAclNumber(Integer aclNumber) {
		this.aclNumber = aclNumber;
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

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
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