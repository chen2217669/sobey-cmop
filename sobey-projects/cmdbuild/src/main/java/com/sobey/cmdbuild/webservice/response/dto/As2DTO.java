package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "As2DTO", namespace = WsConstants.NS)
public class As2DTO {

	private Date beginDate;
	private String code;
	private String description;
	private Double diskSize;
	private Integer id;
	private Integer ipaddress;
	private Integer netAppController;
	private String remark;
	private Integer tag;
	private Integer tenants;
	private String volumePath;
	private Integer volumeType;

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Double getDiskSize() {
		return diskSize;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public Integer getNetAppController() {
		return netAppController;
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

	public String getVolumePath() {
		return volumePath;
	}

	public Integer getVolumeType() {
		return volumeType;
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

	public void setDiskSize(Double diskSize) {
		this.diskSize = diskSize;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setNetAppController(Integer netAppController) {
		this.netAppController = netAppController;
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

	public void setVolumePath(String volumePath) {
		this.volumePath = volumePath;
	}

	public void setVolumeType(Integer volumeType) {
		this.volumeType = volumeType;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}