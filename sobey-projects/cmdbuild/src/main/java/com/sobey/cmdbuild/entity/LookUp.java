package com.sobey.cmdbuild.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "\"LookUp\"", schema = "public")
public class LookUp extends BasicEntity {

	private String type;
	private String parentType;
	private Integer parentId;
	private Integer number;
	private Boolean isDefault;

	@Column(name = "\"Type\"", length = 64)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "\"ParentType\"", length = 64)
	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	@Column(name = "\"ParentId\"")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Column(name = "\"Number\"")
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Column(name = "\"IsDefault\"")
	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

}
