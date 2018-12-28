package org.cloud.activiti.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cloud.activiti.entity.Permission;
import org.cloud.activiti.entity.Role;


public interface IRoleService {
    List<Role> getRoles();

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

    Map<String, Set<String>> getPermissionMapByUserId(int uid);
}
