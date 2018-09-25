package com.avalon.common.dao.user;

import com.avalon.common.dao.BaseDao;
import com.avalon.common.entity.user.bo.AdminRoleBind;
import com.avalon.common.entity.user.vo.AdminRoleBindVo;

import java.util.List;

public interface AdminRoleBindDao extends BaseDao<AdminRoleBind,Long>{

	List<AdminRoleBindVo> queryVo(AdminRoleBindVo vo);

}