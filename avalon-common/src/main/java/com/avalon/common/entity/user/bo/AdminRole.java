package com.avalon.common.entity.user.bo;

import java.util.Date;

/**
*
* @ClassName: AdminRole
* @Description: 映射p_admin_role表
* @author: carme-generator
*
*/
public class AdminRole {

    /**
    * id
    */
    private Long   id;


    /**
    * roleName
    */
    private String roleName;


    /**
    * roleValue
    */
    private String roleValue;


    /**
    * 菜单
    */
    private String  menu;

    /**
    * button
    */
    private String  button;

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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleValue() {
        return roleValue;
    }

    public void setRoleValue(String roleValue) {
        this.roleValue = roleValue;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
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