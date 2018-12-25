package org.cloud.activiti.service;

import java.util.List;

import org.cloud.activiti.entity.Permission;
import org.cloud.activiti.entity.Role;
import org.cloud.activiti.entity.User;

public interface SystemService {
    List<User> getAllUsers();

    List<User> getPagedUsers(int pageNum, int pageSize);

    User getUserById(int id);
    
    User getUserByName(String userName);

    List<Role> getRoles();

    void deleteUser(int uid);

    void addUser(User user, String[] roleNames);

    void addUser(User user);

    void updateUser(int uid, User user, String[] roleNames);

    List<Role> getPagedRoleInfo(int pageNum, int pageSize);

    List<Role> getRoleInfo();

    List<Permission> getPermissions();

    void addRole(Role role, String[] permissionNames);

    void deleteRole(int rid);

    Role getRoleById(int rid);

    void deleteRolePermission(int rid);

    void updateRole(int rid, String[] permissionNames);

    List<Permission> getPagedPermissions(int pageNum, int pageSize);

    void addPermission(String permissionName);

    void deletePermission(int pid);

    int getUidByUserName(String userName);
}
