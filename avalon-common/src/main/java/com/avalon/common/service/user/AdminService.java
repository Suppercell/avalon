package com.avalon.common.service.user;

import com.avalon.common.entity.user.bo.Admin;
import com.avalon.common.entity.user.vo.AdminVo;
import com.avalon.common.service.BaseService;

import java.util.List;

import com.github.pagehelper.PageInfo;

public interface AdminService extends BaseService<Admin,Long>{
	
	List<AdminVo> queryVo(AdminVo vo);
	
	PageInfo<AdminVo> queryPage(AdminVo vo);
	
}