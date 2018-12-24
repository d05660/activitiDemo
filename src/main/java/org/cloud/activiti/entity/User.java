package org.cloud.activiti.entity;

import java.util.List;

public class User {
	private int uid;
	private String username;
	private String password;
	private String tel;
	private int age;
	private List<UserRole> userRoles;
	
	public User() {
	    
	}
	
	public User(String username, String password, String tel, int age) {
        super();
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.age = age;
    }
	
	public User(int uid, String username, String password, String tel, int age) {
        super();
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.age = age;
    }

    public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
}
