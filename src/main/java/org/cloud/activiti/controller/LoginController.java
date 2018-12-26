package org.cloud.activiti.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.cloud.activiti.util.BaseController;
import org.cloud.activiti.util.CsrfToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("loginController")
public class LoginController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    public static final String USER_SESSION = "USER_SESSION";

    /**
     * GET login
     * 
     * @return {String}
     */
    @GetMapping("/login")
    @CsrfToken(create = true)
    public String login() {
        LOGGER.info("GET login request!");
        if (SecurityUtils.getSubject().isAuthenticated()) {
            return "redirect:/index";
        }
        return "login";
    }

    /**
     * POST login
     * 
     * @param username 用?名
     * @param password 密?
     * @return {Object}
     */
    @PostMapping("/login")
    @CsrfToken(remove = true)
    @ResponseBody
    public Object loginPost(HttpServletRequest request, HttpServletResponse response,
            String username, String password,
            @RequestParam(value = "rememberMe", defaultValue = "0") Integer rememberMe) {
        LOGGER.info("GET login request!");
        // 改?全部抛出?常，避免ajax csrf token被刷新
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // ?置?住密?

        token.setRememberMe(1 == rememberMe);
        try {
            user.login(token);
            return renderSuccess();
        } catch (UnknownAccountException e) {
            LOGGER.error("User Account Not exist！");
        } catch (DisabledAccountException e) {
            LOGGER.error("User Account is disabled！");
        } catch (IncorrectCredentialsException e) {
            LOGGER.error("User Credentials incorrect");
        } catch (Throwable e) {
            LOGGER.error(e.getMessage());
        }
        return renderError("User Or Password incorrect!");
    }

    /**
     * 未授?
     * 
     * @return {String}
     */
    @GetMapping("/unauth")
    public String unauth() {
        if (SecurityUtils.getSubject().isAuthenticated() == false) {
            return "redirect:/login";
        }
        return "unauth";
    }

    /**
     * 退出
     * 
     * @return {Result}
     */
    @PostMapping("/logout")
    @ResponseBody
    public Object logout() {
        LOGGER.info("User logout");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return renderSuccess();
    }
}
