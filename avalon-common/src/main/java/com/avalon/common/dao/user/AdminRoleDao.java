package com.avalon.common.dao.user;

import com.avalon.common.dao.BaseDao;
import com.avalon.common.entity.user.bo.AdminRole;
import com.avalon.common.entity.user.vo.AdminRoleVo;

import java.util.List;

public interface AdminRoleDao extends BaseDao<AdminRole,Long>{

	List<AdminRoleVo> queryVo(AdminRoleVo vo);

}