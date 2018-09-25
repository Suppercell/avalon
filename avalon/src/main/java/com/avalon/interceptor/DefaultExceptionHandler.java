package com.avalon.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.avalon.common.util.RequestUtil;

public class DefaultExceptionHandler implements HandlerExceptionResolver {
    private static final Logger logger = LogManager.getLogger(DefaultExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object param, Exception e) {
        String reqInfo = RequestUtil.getRequestMsg(request);
        ModelAndView view = new ModelAndView("system/error");
        logger.error(reqInfo, e);
        return view;
    }

}
