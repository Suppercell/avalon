package com.avalon.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

    public static void addCookie(HttpServletResponse response, String domain, String name,
                                 String value, int age) {
        Cookie cookies = new Cookie(name, value);
        cookies.setPath("/");
        //设置cookie经过多长秒后被删除。如果0，就说明立即删除。如果是负数就表明当浏览器关闭时自动删除。  
        cookies.setMaxAge(age);
        cookies.setDomain(domain);
        response.addCookie(cookies);
    }

    public static String getCookieValue(HttpServletRequest request, String domain, String cookieName) {
        if (cookieName != null) {
            Cookie cookie = getCookie(request, cookieName);
            if (cookie != null) {
                return cookie.getValue();
            }
        }
        return "";
    }

    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        try {
            if (cookies != null && cookies.length > 0) {
                for (int i = 0; i < cookies.length; i++) {
                    cookie = cookies[i];
                    if (cookie.getName().equals(cookieName)) {
                        return cookie;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Map<String, String> getCookies(HttpServletRequest request) {
        Map<String, String> result = new HashMap<String, String>();
        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if (cookies != null && cookies.length > 0) {
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                result.put(cookie.getName(), cookie.getValue());
            }
        }
        return result;
    }

    public static boolean deleteCookie(HttpServletRequest request, HttpServletResponse response,
                                       String cookieName) {
        if (cookieName != null) {
            Cookie cookie = getCookie(request, cookieName);
            if (cookie != null) {
                cookie.setMaxAge(0);//如果0，就说明立即删除  
                cookie.setPath("/");//不要漏掉  
                response.addCookie(cookie);
                return true;
            }
        }
        return false;
    }

}
