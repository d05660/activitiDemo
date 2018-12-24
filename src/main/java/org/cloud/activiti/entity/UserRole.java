package org.cloud.activiti.entity;

public class UserRole {
	private int urid;
	private User user;
	private Role role;

	public int getUrid() {
		return urid;
	}

	public void setUrid(int urid) {
		this.urid = urid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
