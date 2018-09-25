package com.avalon.shiro;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.avalon.common.entity.user.bo.Admin;
import com.avalon.common.entity.user.bo.AdminRole;
import com.avalon.common.entity.user.bo.AdminRoleBind;
import com.avalon.common.service.user.AdminRoleBindService;
import com.avalon.common.service.user.AdminRoleService;
import com.avalon.common.service.user.AdminService;
import com.avalon.common.util.StringUtil;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private AdminService         adminService;

    @Autowired
    private AdminRoleBindService adminRoleBindService;

    @Autowired
    private AdminRoleService     adminRoleService;

    /**  
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.  
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String loginName = (String) super.getAvailablePrincipal(principals);
        //到数据库查是否有此对象
        Admin a = new Admin();
        a.setLoginName(loginName);
        List<Admin> admins = adminService.query(a);
        if (!admins.isEmpty()) {
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            Set<String> roles = new HashSet<String>();
            AdminRoleBind arb = new AdminRoleBind();
            arb.setAdminId(admins.get(0).getId());
            List<AdminRoleBind> binds = adminRoleBindService.query(arb);
            Set<String> permissions = new HashSet<String>();
            if (!binds.isEmpty()) {
                Long[] ids = new Long[binds.size()];
                for (int i = 0; i < ids.length; i++) {
                    ids[i] = binds.get(i).getAdminRoleId();
                }

                List<AdminRole> adminRoles = adminRoleService.getByIds(ids);
                for (AdminRole adminRole : adminRoles) {
                    roles.add(adminRole.getRoleValue());
                    if (StringUtil.isNotBlank(adminRole.getMenu())) {
                        permissions.addAll(Arrays.asList(adminRole.getMenu().split(",")));
                    }
                    if (StringUtil.isNotBlank(adminRole.getButton())) {
                        permissions.addAll(Arrays.asList(adminRole.getButton().split(",")));
                    }
                }
            }
            //用户的角色集合
            info.setRoles(roles);

            info.setStringPermissions(permissions);
            return info;
        }
        // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }

    /**  
     * 认证回调函数,登录时调用.  
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
                                                                                        throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Admin a = new Admin();
        a.setLoginName(token.getUsername());
        List<Admin> admins = adminService.query(a);
        if (!admins.isEmpty()) {
            return new SimpleAuthenticationInfo(admins.get(0).getLoginName(), admins.get(0)
                .getPassword(), getName());
        } else {
            throw new UnknownAccountException();
        }
    }

}
