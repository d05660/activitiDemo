package org.cloud.activiti.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.cloud.activiti.entity.Role;
import org.cloud.activiti.entity.RolePermission;
import org.cloud.activiti.entity.User;
import org.cloud.activiti.entity.UserRole;
import org.cloud.activiti.mapper.RoleMapper;
import org.cloud.activiti.mapper.UserMapper;
import org.cloud.activiti.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public void addUser(User user, String[] roleNames) {
        userMapper.addUser(user);
        for (String roleName : roleNames) {
            Role role = roleMapper.getRoleByName(roleName);
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            roleMapper.addUserRole(userRole);
        }
    }

    @Override
    public void deleteUser(int uid) {
        userMapper.deleteUser(uid);
        userMapper.deleteUserRole(uid);
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
    public int getUidByUserName(String userName) {
        return userMapper.getUidByUserName(userName);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByName(String userName) {
        return userMapper.getUserByName(userName);
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
                Role role = roleMapper.getRoleByName(roleName);
                UserRole ur = new UserRole();
                ur.setRole(role);
                ur.setUser(user);
                roleMapper.addUserRole(ur);
            }
        }
    }

    @Override
    public Set<String> getRolesByUserName(String username) {
        Set<String> rolesSet = new HashSet<>();
        User user = userMapper.getUserByName(username);
        for (UserRole userRole : user.getUserRoles()) {
            String roleName = userRole.getRole().getRoleName();
            rolesSet.add(roleName);
        }
        return rolesSet;
    }

    @Override
    public Set<String> getPermissionsByUserName(String username) {
        Set<String> permissions = new HashSet<>();
        User user = userMapper.getUserByName(username);
        for (UserRole ur : user.getUserRoles()) {
            Role role = roleMapper.getRoleById(ur.getRole().getRid());
            List<RolePermission> rps = role.getRolePermissions();
            for (RolePermission rp : rps) {
                String permission = rp.getPermission().getPermissionName();
                permissions.add(permission);
            }
        }
        return permissions;
    }
}