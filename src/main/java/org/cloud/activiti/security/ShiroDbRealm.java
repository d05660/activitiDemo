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
        
        //从数据库查询用户信息
        User user = userService.getUserByName(token.getUsername());

        // 账号不存在
        if (user == null) {
            LOGGER.error("username or password error");
            throw new UnknownAccountException();//没找到帐号
        }
        
        //调用 CredentialsMatcher校验 还需要创建一个类继承CredentialsMatcher  如果在上面校验了,这个就不需要了
        return new SimpleAuthenticationInfo(userName, user.getPassword().toCharArray(),
                ShiroByteSource.of(user.getUsername()), getName());
    }

    /**
     * 授权用户权限
     * 授权的方法是在碰到<shiro:hasPermission name=''></shiro:hasPermission>标签的时候调用的
     * 它会去检测shiro框架中的权限(这里的permissions)是否包含有该标签的name值,如果有,里面的内容显示
     * 如果没有,里面的内容不予显示(这就完成了对于权限的认证.)
     *
     * shiro的权限授权是通过继承AuthorizingRealm抽象类，重载doGetAuthorizationInfo();
     * 当访问到页面的时候，链接配置了相应的权限或者shiro标签才会执行此方法否则不会执行
     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可。
     *
     * 在这个方法中主要是使用类：SimpleAuthorizationInfo 进行角色的添加和权限的添加。
     * authorizationInfo.addRole(role.getRole()); authorizationInfo.addStringPermission(p.getPermission());
     *
     * 当然也可以添加set集合：roles是从数据库查询的当前用户的角色，stringPermissions是从数据库查询的当前用户对应的权限
     * authorizationInfo.setRoles(roles); authorizationInfo.setStringPermissions(stringPermissions);
     *
     * 就是说如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "perms[权限添加]");
     * 就说明访问/add这个链接必须要有“权限添加”这个权限才可以访问
     *
     * 如果在shiro配置文件中添加了filterChainDefinitionMap.put("/add", "roles[100002]，perms[权限添加]");
     * 就说明访问/add这个链接必须要有 "权限添加" 这个权限和具有 "100002" 这个角色才可以访问
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
 
    /**
     * 清除所有授权缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }
 
    /**
     * 清除所有认证缓存
     */
    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }
 
    /**
     * 清除所有的  认证缓存和授权缓存
     */
    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
}
