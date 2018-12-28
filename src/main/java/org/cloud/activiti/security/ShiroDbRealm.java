package org.cloud.activiti.security;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.cloud.activiti.entity.User;
import org.cloud.activiti.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm 继承AuthorizingRealm
 * 重写  AuthorizationInfo（授权） 和  AuthenticationInfo（认证）
 * 
 * @author sinocom
 */
public class ShiroDbRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroDbRealm.class);

    @Autowired
    private IUserService userService;

    public ShiroDbRealm(CacheManager cacheManager, CredentialsMatcher matcher) {
        super(cacheManager, matcher);
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
            throws AuthenticationException {
        LOGGER.info("Shiro start to Authentication...");
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        // 根据用户名查询数据库
        String userName = token.getUsername();
        User user = userService.getUserByName(token.getUsername());

        // 账号不存在
        if (user == null) {
            LOGGER.error("username or password error");
            throw new UnknownAccountException();//没找到帐号
        }
        
        // 认证缓存信息
        return new SimpleAuthenticationInfo(userName, user.getPassword().toCharArray(),
                ShiroByteSource.of(user.getUsername()), getName());
    }

    /**
     * Shiro权限认证 授权
     * 
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String) principals.getPrimaryPrincipal();

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roleSet = userService.getRolesByUserName(username);
        info.setRoles(roleSet);
        LOGGER.info(roleSet.stream().reduce((x, y) -> x + "," + y).orElse(""));
        
        Set<String> permissionSet = userService.getPermissionsByUserName(username);
        info.setStringPermissions(permissionSet);
        LOGGER.info(permissionSet.stream().reduce((x, y) -> x + "," + y).orElse(""));
        return info;
    }

    @Override
    protected String getAuthenticationCacheKey(PrincipalCollection principals) {
        String shiroUser = (String) super.getAvailablePrincipal(principals);
        return shiroUser;
    }

    @Override
    protected String getAuthorizationCacheKey(PrincipalCollection principals) {
        String shiroUser = (String) super.getAvailablePrincipal(principals);
        return shiroUser;
    }
    
    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }
 
    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }
 
    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }
 
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }
 
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }
 
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
