package org.cloud.activiti.service.impl;

import org.cloud.activiti.entity.Role;
import org.cloud.activiti.entity.User;
import org.cloud.activiti.service.SystemService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemServiceImplTest {

    @Autowired
    private SystemService systemService;

    @Test
    public void testAddPermission() {
        systemService.addPermission("permission1");
        systemService.addPermission("permission2");
    }

    @Test
    public void testAddRole() {
        String[] permissionNames = new String[] {
                "permission1", "permission2"
        };
        systemService.addRole(new Role(10, "role01"), permissionNames);
        systemService.addRole(new Role(11, "role02"), permissionNames);
    }

    @Test
    public void testAddUserUser() {
        systemService.addUser(new User(10, "user01", "1234", "135", 40));
    }

    @Test
    public void testAddUserUserStringArray() {
        String[] roleNames = new String[] {
                "role01", "role02"
        };
        systemService.addUser(new User(11, "user02", "1234", "134", 41), roleNames);
    }

    @Test
    public void testDeletePermission() {
        systemService.deletePermission(7);
    }

    @Test
    public void testDeleteRole() {
        systemService.deleteRole(10);
    }

    @Test
    public void testDeleteRolePermission() {
        systemService.deleteRolePermission(9);
    }

    @Test
    public void testDeleteUser() {
        systemService.deleteUser(8);
    }

    @Test
    public void testGetAllUsers() {
        System.out.println(systemService.getAllUsers());
    }

    @Test
    public void testGetPagedPermissions() {
        systemService.getPagedPermissions(0, 5);
    }

    @Test
    public void testGetPagedRoleInfo() {
        systemService.getPagedRoleInfo(0, 5);
    }

    @Test
    public void testGetPagedUsers() {
        systemService.getPagedUsers(0, 5);
    }

    @Test
    public void testGetPermissions() {
        systemService.getPermissions();
    }

    @Test
    public void testGetRoleById() {
        systemService.getRoleById(3);
    }

    @Test
    public void testGetRoleInfo() {
        systemService.getRoleInfo();
    }

    @Test
    public void testGetRoles() {
        systemService.getRoles();
    }

    @Test
    public void testGetUidByUserName() {
        systemService.getUidByUserName("lisi");
    }

    @Test
    public void testGetUserById() {
        systemService.getUserById(1);
    }

    @Test
    public void testUpdateRole() {
        String[] permissionNames = new String[] {
                "permission1", "permission2"
        };
        systemService.updateRole(9, permissionNames);
    }

    @Test
    public void testUpdateUser() {
        String[] roleNames = new String[] {
                "role01"
        };
        systemService.updateUser(5, new User("user02", "1234", "134", 44), roleNames);
    }

}
