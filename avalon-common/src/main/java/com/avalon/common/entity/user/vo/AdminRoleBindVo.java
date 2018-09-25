package com.avalon.common.entity.user.vo;

import com.avalon.common.entity.user.bo.AdminRoleBind;

/**
 * 
 * @ClassName: AdminRoleBindVo
 * @Description:
 * @author: carme-generator
 *
 */
public class AdminRoleBindVo extends AdminRoleBind {
	private Integer pageSize;

    private Integer pageNo;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
