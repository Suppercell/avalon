package com.avalon.common.dao.user;

import com.avalon.common.dao.BaseDao;
import com.avalon.common.entity.user.bo.Admin;
import com.avalon.common.entity.user.vo.AdminVo;

import java.util.List;

public interface AdminDao extends BaseDao<Admin,Long>{

	List<AdminVo> queryVo(AdminVo vo);

}