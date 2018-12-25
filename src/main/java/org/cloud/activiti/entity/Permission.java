package org.cloud.activiti.entity;

import java.util.List;

public class Permission {
    private int pid;
    private String permissionName;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private List<RolePermission> rp;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public List<RolePermission> getRp() {
        return rp;
    }

    public void setRp(List<RolePermission> rp) {
        this.rp = rp;
    }
}