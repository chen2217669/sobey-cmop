package com.sobey.cmdbuild.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Basic Entity 基本类 所有的entity皆继承该抽象类.
 * 
 * <br>
 * 
 * <b>注意:postgreSQL数据库对表明和表字段大小写敏感. </b>
 * 
 * 
 * @author Administrator
 * 
 */
@MappedSuperclass
public abstract class BasicEntity {

	protected Integer id;
	protected String idClass;
	protected String code;
	protected String description;
	protected Character status;
	protected String user;
	protected Date beginDate;
	protected String notes;

	@Id
	@Column(name = "\"Id\"", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "\"IdClass\"", nullable = false)
	public String getIdClass() {
		return idClass;
	}

	public void setIdClass(String idClass) {
		this.idClass = idClass;
	}

	@NotBlank
	@Column(name = "\"Code\"", length = 100)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotBlank
	@Column(name = "\"Description\"", length = 250)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "\"Status\"", length = 1)
	public Character getStatus() {
		return status;
	}

	public void setStatus(Character status) {
		this.status = status;
	}

	@Column(name = "\"User\"", length = 40)
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"BeginDate\"", nullable = false, length = 29)
	public Date getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	@Column(name = "\"Notes\"")
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

}
