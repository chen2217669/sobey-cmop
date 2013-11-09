package com.sobey.cmdbuild.webservice.response.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sobey.cmdbuild.constants.WsConstants;

@XmlRootElement
@XmlType(name = "EcsDTO", namespace = WsConstants.NS)
public class EcsDTO {

	private Date beginDate;
	private String code;
	private String description;
	private Integer ecsAgent;
	private Integer ecsStatus;

	// Tag Integer 标签ID
	// Ipaddress Integer Ipaddress ID
	// ServiceSpec Integer 服务规格ID
	// Tenants Integer 租户ID
	// Server Integer 宿主机ID
	// Image Integer 镜像ID（操作系统+位数）
	// ECSAgent Integer 虚拟机所属agent
	// ECSStatus Integer ① Pending ： 等待被创建
	// ② Running ： 运行中
	// ③ Stopped ： 已关机
	// ④ Suspended ： 由于欠费, 暂停使用
	// ⑤ Deleted： 已被删除
	// 需要考虑到不同ECS Agent提供的状态可能不同。

	private Integer id;
	private Integer image;
	private Integer ipaddress;
	private String remark;
	private Integer server;
	private Integer serviceSpec;
	private Integer tag;
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

	public Integer getEcsAgent() {
		return ecsAgent;
	}

	public Integer getEcsStatus() {
		return ecsStatus;
	}

	public Integer getId() {
		return id;
	}

	public Integer getImage() {
		return image;
	}

	public Integer getIpaddress() {
		return ipaddress;
	}

	public String getRemark() {
		return remark;
	}

	public Integer getServer() {
		return server;
	}

	public Integer getServiceSpec() {
		return serviceSpec;
	}

	public Integer getTag() {
		return tag;
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

	public void setEcsAgent(Integer ecsAgent) {
		this.ecsAgent = ecsAgent;
	}

	public void setEcsStatus(Integer ecsStatus) {
		this.ecsStatus = ecsStatus;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setImage(Integer image) {
		this.image = image;
	}

	public void setIpaddress(Integer ipaddress) {
		this.ipaddress = ipaddress;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setServer(Integer server) {
		this.server = server;
	}

	public void setServiceSpec(Integer serviceSpec) {
		this.serviceSpec = serviceSpec;
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