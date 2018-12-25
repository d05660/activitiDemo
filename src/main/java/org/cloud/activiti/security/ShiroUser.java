package org.cloud.activiti.security;

import java.io.Serializable;
import java.util.Set;

public class ShiroUser implements Serializable {

    private static final long serialVersionUID = -1373760761780840081L;

    private int id;
    private final String loginName;
    private String userId;
    private int level;
    private Set<String> urlSet;
    private Set<String> roles;

    public ShiroUser(String loginName) {
        this.loginName = loginName;
    }

    public ShiroUser(int id, String loginName, String userId, int level, Set<String> urlSet) {
        this.id = id;
        this.loginName = loginName;
        this.userId = userId;
        this.level = level;
        this.urlSet = urlSet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUserId() {
        return userId;
    }

    public Set<String> getUrlSet() {
        return urlSet;
    }

    public void setUrlSet(Set<String> urlSet) {
        this.urlSet = urlSet;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getLoginName() {
        return loginName;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return loginName;
    }

}
