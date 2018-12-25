package org.cloud.activiti.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.PathMatchingFilter;

public class UserFilter extends PathMatchingFilter {

    // @Resource
    // private ISysUserService sysUserService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        // 可以参考http://jinnianshilongnian.iteye.com/blog/2025656
        return true;
    }

}
