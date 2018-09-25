package com.avalon.common.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avalon.common.dao.BaseDao;
import com.avalon.common.dao.user.AdminRoleDao;
import com.avalon.common.entity.user.bo.AdminRole;
import com.avalon.common.entity.user.vo.AdminRoleVo;
import com.avalon.common.service.BaseServiceImpl;
import com.avalon.common.service.user.AdminRoleService;

import java.util.List;

import com.github.pagehelper.PageInfo;


@Service
public class AdminRoleServiceImpl extends BaseServiceImpl<AdminRole,Long> implements AdminRoleService {

	@Autowired
    private AdminRoleDao adminRoleDao;
	    
    @Override
    protected BaseDao<AdminRole,Long> getDao() {        
        return adminRoleDao;
    }
    
    @Override
    public List<AdminRoleVo> queryVo(AdminRoleVo vo) {        
        return adminRoleDao.queryVo(vo);
    }
    
    @Override
    public PageInfo<AdminRoleVo> queryPage(AdminRoleVo vo) {
        preparePage(vo.getPageNo(), vo.getPageSize());
        List<AdminRoleVo> list = queryVo(vo);
        PageInfo<AdminRoleVo> page = new PageInfo<AdminRoleVo>(list);
        return page;
    }

}
