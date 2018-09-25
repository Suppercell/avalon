package com.avalon.common.entity.user.bo;

import java.util.Date;

/**
*
* @ClassName: Admin
* @Description: 映射p_admin表
* @author: carme-generator
*
*/
public class Admin {

    /**
    * id
    */
    private Long   id;


    /**
    * loginName
    */
    private String loginName;


    /**
    * password
    */
    private String password;


    /**
    * isDelete
    */
    private Integer isDelete;


    /**
    * createdAt
    */
    private Date createdAt;


    /**
    * 用户id
    */
    private String createdBy;


    /**
    * changedAt
    */
    private Date changedAt;


    /**
    * changedBy
    */
    private String changedBy;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public java.util.Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(java.util.Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public java.util.Date getChangedAt() {
		return changedAt;
	}
	public void setChangedAt(java.util.Date changedAt) {
		this.changedAt = changedAt;
	}
	public String getChangedBy() {
		return changedBy;
	}
	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}


}