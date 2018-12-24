package org.cloud.activiti.mapper;

import java.util.List;

import org.cloud.activiti.entity.Permission;

public interface PermissionMapper {

    List<Permission> getPermissions();

    void addPermission(String permissionName);

    void deleteRolePermission(int pid);

    void deletePermission(int pid);

    Permission getPermissionByName(String permissionName);

}
