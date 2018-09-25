package com.avalon.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class ShiroHelper {

    public static boolean isPermitted(String permission) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            if (subject.hasRole("super_admin")) {
                return true;
            } else {
                return subject.isPermitted(permission);
            }
        } else {
            return false;
        }

    }
}
