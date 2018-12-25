package org.cloud.activiti.service.impl;

import java.util.List;

import org.cloud.activiti.entity.Permission;
import org.cloud.activiti.entity.Role;
import org.cloud.activiti.entity.RolePermission;
import org.cloud.activiti.entity.User;
import org.cloud.activiti.entity.UserRole;
import org.cloud.activiti.mapper.PermissionMapper;
import org.cloud.activiti.mapper.RoleMapper;
import org.cloud.activiti.mapper.UserMapper;
import org.cloud.activiti.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void addUser(User user, String[] roleNames) {
        userMapper.addUser(user);
        for (String roleName : roleNames) {
            Role role = roleMapper.getRoleIdByName(roleName);
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            roleMapper.addUserRole(userRole);
        }
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public void deleteUser(int uid) {
        userMapper.deleteUser(uid);
        userMapper.deleteUserRole(uid);
    }

    @Override
    public void updateUser(int uid, User user, String[] roleNames) {
        if (roleNames == null) {
            user.setUid(uid);
            userMapper.updateUser(user);
            userMapper.deleteUserRole(uid);
        } else {
            user.setUid(uid);
            userMapper.updateUser(user);
            userMapper.deleteUserRole(uid);
            for (String roleName : roleNames) {
                Role role = roleMapper.getRoleIdByName(roleName);
                UserRole ur = new UserRole();
                ur.setRole(role);
                ur.setUser(user);
                roleMapper.addUserRole(ur);
            }
        }
    }
    
    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }
    
    @Override
    public int getUidByUserName(String userName) {
        return userMapper.getUidByUserName(userName);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userMapper.getUsers();
    }

    @Override
    public List<User> getPagedUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userMapper.getUsers();
    }

    @Override
    public void addRole(Role role, String[] permissionNames) {
        roleMapper.addRole(role);
        for (String permissionName : permissionNames) {
            Permission p = permissionMapper.getPermissionByName(permissionName);
            RolePermission rp = new RolePermission();
            rp.setRole(role);
            rp.setPermission(p);
            roleMapper.addRolePermission(rp);
        }
    }

    @Override
    public void deleteRole(int rid) {
        roleMapper.deleteRole(rid);
        roleMapper.deleteRolePermission(rid);
        roleMapper.deleteUserRole(rid);
    }

    @Override
    public void updateRole(int rid, String[] permissionNames) {
        Role role = roleMapper.getRoleById(rid);
        for (String permissionName : permissionNames) {
            Permission p = permissionMapper.getPermissionByName(permissionName);
            RolePermission rp = new RolePermission();
            rp.setRole(role);
            rp.setPermission(p);
            roleMapper.addRolePermission(rp);
        }
    }

    @Override
    public List<Role> getPagedRoleInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return roleMapper.getRoleInfo();
    }

    @Override
    public List<Role> getRoleInfo() {
        return roleMapper.getRoleInfo();
    }
    
    @Override
    public List<Role> getRoles() {
        return roleMapper.getRoles();
    }

    @Override
    public Role getRoleById(int rid) {
        return roleMapper.getRoleById(rid);
    }
    
    @Override
    public void addPermission(String permissionName) {
        permissionMapper.addPermission(permissionName);
    }
    
    @Override
    public List<Permission> getPermissions() {
        return permissionMapper.getPermissions();
    }

    @Override
    public void deleteRolePermission(int rid) {
        roleMapper.deleteRolePermission(rid);
    }

    @Override
    public List<Permission> getPagedPermissions(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return permissionMapper.getPermissions();
    }

    @Override
    public void deletePermission(int pid) {
        permissionMapper.deletePermission(pid);
        permissionMapper.deleteRolePermission(pid);
    }

    @Override
    public User getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }
}
