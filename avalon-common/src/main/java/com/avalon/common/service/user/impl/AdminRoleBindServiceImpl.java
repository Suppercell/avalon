package com.avalon.common.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avalon.common.dao.BaseDao;
import com.avalon.common.dao.user.AdminRoleBindDao;
import com.avalon.common.entity.user.bo.AdminRoleBind;
import com.avalon.common.entity.user.vo.AdminRoleBindVo;
import com.avalon.common.service.BaseServiceImpl;
import com.avalon.common.service.user.AdminRoleBindService;

import java.util.List;

import com.github.pagehelper.PageInfo;


@Service
public class AdminRoleBindServiceImpl extends BaseServiceImpl<AdminRoleBind,Long> implements AdminRoleBindService {

	@Autowired
    private AdminRoleBindDao adminRoleBindDao;
	    
    @Override
    protected BaseDao<AdminRoleBind,Long> getDao() {        
        return adminRoleBindDao;
    }
    
    @Override
    public List<AdminRoleBindVo> queryVo(AdminRoleBindVo vo) {        
        return adminRoleBindDao.queryVo(vo);
    }
    
    @Override
    public PageInfo<AdminRoleBindVo> queryPage(AdminRoleBindVo vo) {
        preparePage(vo.getPageNo(), vo.getPageSize());
        List<AdminRoleBindVo> list = queryVo(vo);
        PageInfo<AdminRoleBindVo> page = new PageInfo<AdminRoleBindVo>(list);
        return page;
    }

}
