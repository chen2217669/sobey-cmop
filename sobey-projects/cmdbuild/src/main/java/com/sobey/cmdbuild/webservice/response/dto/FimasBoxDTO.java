package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "FimasBoxDTO", namespace = WsConstants.NS)
public class FimasBoxDTO {

	private Date beginDate;
	private String code;
	private String description;
	private Integer deviceSpec;
	private Integer diskNumber;

	// IDC Integer IDC ID
	// Rack Integer Rack ID
	// DeviceSpec Integer 规格ID
	// Ipaddress Integer Ipaddress
	// DiskType Integer 磁盘类型
	// Site String 位置
	// DiskNumber Integer 磁盘数量
	// SN String SN
	// GdzcSn String Sobey SN编号

	private Integer diskType;
	private String gdzcSn;
	private Integer id;
	private Integer idc;
	private Integer ipaddress;
	private Integer rack;
	private String remark;
	private String site;
	private String sn;

	public Date getBeginDate() {
		return beginDate;
	}

	public String getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}

	public Integer getDeviceSpec() {
		return deviceSpec;
	}

	public Integer getDiskNumber() {
		return diskNumber;
	}

	public Integer getDiskType() {
		return diskType;
	}

	public String getGdzcSn() {
		return gdzcSn;
	}

	public Integer getId() {
		return id;
	}

	public Integer getIdc() {
		return idc;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public Integer getRack() {
		return rack;
	}

	public String getRemark() {
		return remark;
	}

	public String getSite() {
		return site;
	}

	public String getSn() {
		return sn;
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

	public void setDeviceSpec(Integer deviceSpec) {
		this.deviceSpec = deviceSpec;
	}

	public void setDiskNumber(Integer diskNumber) {
		this.diskNumber = diskNumber;
	}

	public void setDiskType(Integer diskType) {
		this.diskType = diskType;
	}

	public void setGdzcSn(String gdzcSn) {
		this.gdzcSn = gdzcSn;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdc(Integer idc) {
		this.idc = idc;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setRack(Integer rack) {
		this.rack = rack;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	/**
	 * 重新实现toString()函数方便在日志中打印DTO信息.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}