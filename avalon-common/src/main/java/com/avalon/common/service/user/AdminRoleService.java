package com.avalon.common.service.user;

import com.avalon.common.entity.user.bo.AdminRole;
import com.avalon.common.entity.user.vo.AdminRoleVo;
import com.avalon.common.service.BaseService;

import java.util.List;

import com.github.pagehelper.PageInfo;

public interface AdminRoleService extends BaseService<AdminRole,Long>{
	
	List<AdminRoleVo> queryVo(AdminRoleVo vo);
	
	PageInfo<AdminRoleVo> queryPage(AdminRoleVo vo);
	
}