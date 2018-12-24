package org.cloud.activiti.service.impl;

import static org.junit.Assert.*;

import org.cloud.activiti.service.SystemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml" })
public class SystemServiceImplTest {
    
    @Autowired
    private SystemService systemService;

    @Test
    public void testAddUserUserStringArray() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddUserUser() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteUser() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateUser() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetUserById() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetUidByUserName() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetAllUsers() {
        systemService.getAllUsers();
    }

    @Test
    public void testGetPagedUsers() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddRole() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteRole() {
        fail("Not yet implemented");
    }

    @Test
    public void testUpdateRole() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetPagedRoleInfo() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetRoleInfo() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetRoles() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetRoleById() {
        fail("Not yet implemented");
    }

    @Test
    public void testAddPermission() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetPermissions() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeleteRolePermission() {
        fail("Not yet implemented");
    }

    @Test
    public void testGetPagedPermissions() {
        fail("Not yet implemented");
    }

    @Test
    public void testDeletePermission() {
        fail("Not yet implemented");
    }

}
