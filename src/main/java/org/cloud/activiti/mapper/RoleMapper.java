package org.cloud.activiti.mapper;

import java.util.List;

import org.cloud.activiti.entity.Role;
import org.cloud.activiti.entity.RolePermission;
import org.cloud.activiti.entity.UserRole;

public interface RoleMapper {

    Role getRoleById(int rid);

    void addRolePermission(RolePermission rp);

    List<Role> getRoleInfo();

    void deleteRole(int rid);

    void deleteRolePermission(int rid);

    void deleteUserRole(int rid);

    Role getRoleByName(String roleName);

    void addUserRole(UserRole userRole);

    void addRole(Role role);

    List<Role> getRoles();

}
