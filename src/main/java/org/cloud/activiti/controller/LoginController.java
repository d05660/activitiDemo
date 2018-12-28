package org.cloud.activiti.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.cloud.activiti.common.BaseController;
import org.cloud.activiti.util.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("loginController")
public class LoginController extends BaseController {

    public static final String USER_SESSION = "USER_SESSION";

    /**
     * GET login
     * 
     * @return {String}
     */
    @GetMapping("/login")
    @CsrfToken(create = true)
    public String login() {
        logger.debug("GET login request!");
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
        
        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // ?置?住密?

        token.setRememberMe(1 == rememberMe);
        try {
            currentUser.login(token);
            logger.info("User [" + currentUser.getPrincipal() + "] logged in successfully." );
            return renderSuccess();
        } catch (UnknownAccountException e) {
            logger.error("User Account Not exist！");
        } catch (DisabledAccountException e) {
            logger.error("User Account is disabled！");
        } catch (IncorrectCredentialsException e) {
            logger.error("User Credentials incorrect");
        } catch (Throwable e) {
            logger.error(e.getMessage());
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
        logger.info("User logout");
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return renderSuccess();
    }
}
