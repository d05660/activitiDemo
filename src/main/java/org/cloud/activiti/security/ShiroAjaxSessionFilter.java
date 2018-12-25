package org.cloud.activiti.security;

import org.apache.shiro.web.util.WebUtils;
import org.cloud.activiti.util.StringUtils;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShiroAjaxSessionFilter extends UserFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = WebUtils.toHttp(request);
        String xmlHttpRequest = req.getHeader("X-Requested-With");
        if (StringUtils.isNotBlank(xmlHttpRequest)) {
            if (xmlHttpRequest.equalsIgnoreCase("XMLHttpRequest")) {
                HttpServletResponse res = WebUtils.toHttp(response);
                // 采用res.sendError(401);在Easyui中会处理掉error，$.ajaxSetup中监听不到
                res.setHeader("oauthstatus", "401");
                return false;
            }
        }
        return super.onAccessDenied(request, response);
    }
}
