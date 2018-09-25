package com.avalon.common;

public class AjaxResult {
    public static final String SUCCESS = "1";

    public static final String FAIL    = "0";

    private String             code;

    /**
     * 重定向url
     */
    private String             redirectUrl;

    private String             message;

    private Object             result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
