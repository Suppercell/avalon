package com.avalon.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.avalon.common.PropComponent;

@Controller
public class LoginController {

    @RequestMapping(value = "login", method = RequestMethod.GET)
    @ApiOperation(value = "登录页")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(PropComponent.getProp().getStaticUrl());
        return "login";
    }

    @RequestMapping(value = "doLogin", method = RequestMethod.POST)
    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType = "form"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "form") })
    public String doLogin(HttpServletRequest request, HttpServletResponse response,
                          String username, String password) {

        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);

        return "redirect:/loginSuccess.do";
    }

    @RequestMapping(value = "loginSuccess", method = RequestMethod.GET)
    public String loginSuccess(HttpServletRequest request, HttpServletResponse response) {
        return "loginSuccess";
    }

    @RequestMapping(value="noAuth", method = RequestMethod.GET)
    public String unAuth(HttpServletRequest request, HttpServletResponse response) {
        return "system/noAuth";
    }

}
