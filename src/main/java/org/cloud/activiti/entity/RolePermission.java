package org.cloud.activiti.entity;

public class RolePermission {
	private int rpid;
	private Role role;
	private Permission permission;

	public int getRpid() {
		return rpid;
	}

	public void setRpid(int rpid) {
		this.rpid = rpid;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}
}