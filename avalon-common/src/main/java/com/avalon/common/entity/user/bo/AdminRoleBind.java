package com.avalon.common.entity.user.bo;

import java.util.Date;

/**
*
* @ClassName: AdminRoleBind
* @Description: 映射p_admin_role_bind表
* @author: carme-generator
*
*/
public class AdminRoleBind {

    /**
    * id
    */
    private Long   id;


    /**
    * padminId
    */
    private Long adminId;


    /**
    * padminRoleId
    */
    private Long adminRoleId;


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
	public Long getAdminId() {
		return adminId;
	}
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	public Long getAdminRoleId() {
		return adminRoleId;
	}
	public void setAdminRoleId(Long adminRoleId) {
		this.adminRoleId = adminRoleId;
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