package com.avalon.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class RequestUtil {

    public static String getRequest(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value == null) {
            return "";
        }
        value = value.trim();
        return value;
    }

    public static String getRequestMsg(HttpServletRequest request) {
        return getRequestMsg(request, null, null);
    }

    public static String getRequestMsg(HttpServletRequest request, String message) {
        return getRequestMsg(request, null, message);
    }

    public static String getRequestMsg(HttpServletRequest request, Exception ex, String message) {
        StringBuilder result = new StringBuilder();
        result.append("\nurl:").append(request.getRequestURL().toString());
        result.append("\nmethod:").append(request.getMethod());
        result.append("\nparam:").append(getLogRequestParam(request));
        String cookieJson = JsonUtil.toJson(CookieUtil.getCookies(request));
        result.append("\ncookie:").append(cookieJson);

        if (ex != null) {
            result.append("\nexMsg=").append(ex.toString());
        }
        if (StringUtils.isNotBlank(message)) {
            result.append("\nmessage=").append(message);
        }
        return result.toString();
    }

    private static String getLogRequestParam(HttpServletRequest request) {
        StringBuilder result = new StringBuilder();
        Map<String, String[]> paramMap = request.getParameterMap();
        for (String key : paramMap.keySet()) {
            if (result.length() != 0) {
                result.append("&");
            }
            result.append(key).append("=");
            String value = StringUtil.array2String(paramMap.get(key));
            if (value.indexOf("?") != -1 || value.indexOf("&") != -1) {
                value = "[" + value + "]";
            }
            result.append(value);
        }
        return result.toString();
    }

    public static String getRequestParam(HttpServletRequest request, boolean hasEncode) {
        Map<String, String[]> paramMap = request.getParameterMap();
        Map<String, String> targetMap = new HashMap<String, String>();
        for (String targetKey : paramMap.keySet()) {
            targetMap.put(targetKey, StringUtil.array2String(paramMap.get(targetKey)));
        }
        return getRequestParam(targetMap, hasEncode);
    }

    public static String getRequestParam(Map<String, String> params, boolean hasEncode) {
        StringBuilder result = new StringBuilder();
        for (String key : params.keySet()) {
            if (result.length() != 0) {
                result.append("&");
            }
            String value = params.get(key);
            if (hasEncode) {
                try {
                    value = URLEncoder.encode(value, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            result.append(key).append("=").append(value);
        }
        return result.toString();
    }

}
