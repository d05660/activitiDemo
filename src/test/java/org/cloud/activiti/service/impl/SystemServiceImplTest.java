package org.cloud.activiti.service.impl;

import static org.junit.Assert.*;

import org.cloud.activiti.entity.Role;
import org.cloud.activiti.entity.User;
import org.cloud.activiti.service.IRoleService;
import org.cloud.activiti.service.IUserService;
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
    private IUserService userService;
    
    @Autowired
    private IRoleService roleService;

    @Test
    public void testAddPermission() {
        roleService.addPermission("permission1");
        roleService.addPermission("permission2");
    }

    @Test
    public void testAddRole() {
        String[] permissionNames = new String[] {
                "permission1", "permission2"
        };
        roleService.addRole(new Role(10, "role01"), permissionNames);
        roleService.addRole(new Role(11, "role02"), permissionNames);
    }

    @Test
    public void testAddUserUser() {
        userService.addUser(new User(10, "user01", "1234", "135", 40));
    }

    @Test
    public void testAddUserUserStringArray() {
        String[] roleNames = new String[] {
                "role01", "role02"
        };
        userService.addUser(new User(11, "user02", "1234", "134", 41), roleNames);
    }

    @Test
    public void testDeletePermission() {
        roleService.deletePermission(7);
    }

    @Test
    public void testDeleteRole() {
        roleService.deleteRole(10);
    }

    @Test
    public void testDeleteRolePermission() {
        roleService.deleteRolePermission(9);
    }

    @Test
    public void testDeleteUser() {
        userService.deleteUser(8);
    }

    @Test
    public void testGetAllUsers() {
        assertNotNull(userService.getAllUsers());
    }

    @Test
    public void testGetPagedPermissions() {
        roleService.getPagedPermissions(0, 5);
    }

    @Test
    public void testGetPagedRoleInfo() {
        roleService.getPagedRoleInfo(0, 5);
    }

    @Test
    public void testGetPagedUsers() {
        userService.getPagedUsers(0, 5);
    }

    @Test
    public void testGetPermissions() {
        roleService.getPermissions();
    }

    @Test
    public void testGetRoleById() {
        roleService.getRoleById(3);
    }

    @Test
    public void testGetRoleInfo() {
        roleService.getRoleInfo();
    }

    @Test
    public void testGetRoles() {
        roleService.getRoles();
    }

    @Test
    public void testGetUidByUserName() {
        userService.getUidByUserName("lisi");
    }

    @Test
    public void testGetUserById() {
        userService.getUserById(1);
    }

    @Test
    public void testUpdateRole() {
        String[] permissionNames = new String[] {
                "permission1", "permission2"
        };
        roleService.updateRole(9, permissionNames);
    }

    @Test
    public void testUpdateUser() {
        String[] roleNames = new String[] {
                "role01"
        };
        userService.updateUser(5, new User("user02", "1234", "134", 44), roleNames);
    }

}
