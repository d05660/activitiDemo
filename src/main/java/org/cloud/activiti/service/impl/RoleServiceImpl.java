package org.cloud.activiti.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cloud.activiti.entity.Permission;
import org.cloud.activiti.entity.Role;
import org.cloud.activiti.entity.RolePermission;
import org.cloud.activiti.entity.User;
import org.cloud.activiti.entity.UserRole;
import org.cloud.activiti.mapper.PermissionMapper;
import org.cloud.activiti.mapper.RoleMapper;
import org.cloud.activiti.mapper.UserMapper;
import org.cloud.activiti.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service
public class RoleServiceImpl implements RoleService {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void addPermission(String permissionName) {
        permissionMapper.addPermission(permissionName);
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
    public void deletePermission(int pid) {
        permissionMapper.deletePermission(pid);
        permissionMapper.deleteRolePermission(pid);
    }

    @Override
    public void deleteRole(int rid) {
        roleMapper.deleteRole(rid);
        roleMapper.deleteRolePermission(rid);
        roleMapper.deleteUserRole(rid);
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
    public List<Role> getPagedRoleInfo(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return roleMapper.getRoleInfo();
    }

    @Override
    public Map<String, Set<String>> getPermissionMapByUserId(int uid) {
        Map<String, Set<String>> resourceMap = new HashMap<>();
        User user = userMapper.getUserById(uid);
        Set<String> roles = new HashSet<>();
        Set<String> urlSet = new HashSet<>();
        for (UserRole userRole : user.getUserRoles()) {
            String roleName = userRole.getRole().getRoleName();
            roles.add(roleName);
            Role role = roleMapper.getRoleByName(roleName);
            if (role != null) {
                logger.debug(role.toString());
                for (RolePermission rolePermission : role.getRolePermissions()) {
                    urlSet.add(rolePermission.getPermission().getUrl());
                }
            }
        }
        resourceMap.put("urls", urlSet);
        resourceMap.put("roles", roles);
        return resourceMap;
    }

    @Override
    public List<Permission> getPermissions() {
        return permissionMapper.getPermissions();
    }

    @Override
    public Role getRoleById(int rid) {
        return roleMapper.getRoleById(rid);
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

}