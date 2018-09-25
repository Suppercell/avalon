package com.avalon.common.service.user;

import com.avalon.common.entity.user.bo.AdminRoleBind;
import com.avalon.common.entity.user.vo.AdminRoleBindVo;
import com.avalon.common.service.BaseService;

import java.util.List;

import com.github.pagehelper.PageInfo;

public interface AdminRoleBindService extends BaseService<AdminRoleBind,Long>{
	
	List<AdminRoleBindVo> queryVo(AdminRoleBindVo vo);
	
	PageInfo<AdminRoleBindVo> queryPage(AdminRoleBindVo vo);
	
}