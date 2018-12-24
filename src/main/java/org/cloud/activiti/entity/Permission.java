package org.cloud.activiti.entity;

import java.util.List;

public class Permission {
	private int pid;
	private String permissionname;
	private List<RolePermission> rp;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPermissionname() {
		return permissionname;
	}

	public void setPermissionname(String permissionname) {
		this.permissionname = permissionname;
	}

	public List<RolePermission> getRp() {
		return rp;
	}

	public void setRp(List<RolePermission> rp) {
		this.rp = rp;
	}
}