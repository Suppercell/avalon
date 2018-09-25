package com.avalon.common.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.avalon.common.dao.BaseDao;
import com.avalon.common.dao.user.AdminDao;
import com.avalon.common.entity.user.bo.Admin;
import com.avalon.common.entity.user.vo.AdminVo;
import com.avalon.common.service.BaseServiceImpl;
import com.avalon.common.service.user.AdminService;

import java.util.List;

import com.github.pagehelper.PageInfo;


@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin,Long> implements AdminService {

	@Autowired
    private AdminDao adminDao;
	    
    @Override
    protected BaseDao<Admin,Long> getDao() {        
        return adminDao;
    }
    
    @Override
    public List<AdminVo> queryVo(AdminVo vo) {        
        return adminDao.queryVo(vo);
    }
    
    @Override
    public PageInfo<AdminVo> queryPage(AdminVo vo) {
        preparePage(vo.getPageNo(), vo.getPageSize());
        List<AdminVo> list = queryVo(vo);
        PageInfo<AdminVo> page = new PageInfo<AdminVo>(list);
        return page;
    }

}
