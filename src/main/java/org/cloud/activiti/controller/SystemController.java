package org.cloud.activiti.controller;

import java.util.ArrayList;
import java.util.List;

import org.cloud.activiti.entity.Permission;
import org.cloud.activiti.entity.Role;
import org.cloud.activiti.entity.User;
import org.cloud.activiti.entity.UserRole;
import org.cloud.activiti.service.SystemService;
import org.cloud.activiti.vo.DataGrid;
import org.cloud.activiti.vo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SystemController {
    @Autowired
    SystemService systemService;

    @RequestMapping("/useradmin")
    String useradmin() {
        return "system/useradmin";
    }

    @RequestMapping("/roleadmin")
    String roleadmin() {
        return "system/roleadmin";
    }

    @RequestMapping("/permissionadmin")
    String permissionadmin() {
        return "system/permissionadmin";
    }

    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    @ResponseBody
    DataGrid<UserInfo> userlist(@RequestParam("current") int current,
            @RequestParam("rowCount") int rowCount) {
        int total = systemService.getAllUsers().size();
        List<User> userlist = systemService.getPagedUsers(current, rowCount);
        List<UserInfo> users = new ArrayList<UserInfo>();
        for (User user : userlist) {
            UserInfo u = new UserInfo();
            u.setId(user.getUid());
            u.setAge(user.getAge());
            u.setPassword(user.getPassword());
            u.setTel(user.getTel());
            u.setUsername(user.getUsername());
            String rolename = "";
            List<UserRole> ur = user.getUserRoles();
            if (ur != null) {
                for (UserRole userole : ur) {
                    rolename = rolename + "," + userole.getRole().getRoleName();
                }
                if (rolename.length() > 0)
                    rolename = rolename.substring(1, rolename.length());

                u.setRolelist(rolename);
            }
            users.add(u);
        }
        DataGrid<UserInfo> grid = new DataGrid<UserInfo>();
        grid.setCurrent(current);
        grid.setRows(users);
        grid.setRowCount(rowCount);
        grid.setTotal(total);
        return grid;
    }

    @RequestMapping(value = "/user/{uid}", method = RequestMethod.GET)
    @ResponseBody
    User getuserinfo(@PathVariable("uid") int userid) {
        return systemService.getUserById(userid);
    }

    @RequestMapping(value = "/rolelist", method = RequestMethod.GET)
    @ResponseBody
    List<Role> getroles() {
        return systemService.getRoles();
    }

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @ResponseBody
    DataGrid<Role> getallroles(@RequestParam("current") int current,
            @RequestParam("rowCount") int rowCount) {
        List<Role> roles = systemService.getRoleInfo();
        List<Role> rows = systemService.getPagedRoleInfo(current, rowCount);
        DataGrid<Role> grid = new DataGrid<Role>();
        grid.setCurrent(current);
        grid.setRowCount(rowCount);
        grid.setTotal(roles.size());
        grid.setRows(rows);
        return grid;
    }

    @RequestMapping(value = "/deleteuser/{uid}", method = RequestMethod.GET)
    String deleteuser(@PathVariable("uid") int uid) {
        systemService.deleteUser(uid);
        return "system/useradmin";
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    String adduser(@ModelAttribute("user") User user,
            @RequestParam(value = "rolename[]", required = false) String[] rolename) {
        if (rolename == null)
            systemService.addUser(user);
        else
            systemService.addUser(user, rolename);
        return "forward:/useradmin";
    }

    @RequestMapping(value = "/updateuser/{uid}", method = RequestMethod.POST)
    String updateuser(@PathVariable("uid") int uid, @ModelAttribute("user") User user,
            @RequestParam(value = "rolename[]", required = false) String[] rolename) {
        systemService.updateUser(uid, user, rolename);
        return "system/useradmin";
    }

    @RequestMapping(value = "permissionlist", method = RequestMethod.GET)
    @ResponseBody
    List<Permission> getPermisions() {
        return systemService.getPermissions();
    }

    @RequestMapping(value = "addrole", method = RequestMethod.POST)
    String addrole(@RequestParam("rolename") String rolename,
            @RequestParam(value = "permissionname[]") String[] permissionname) {
        Role r = new Role();
        r.setRoleName(rolename);
        systemService.addRole(r, permissionname);
        return "forward:/roleadmin";
    }

    @RequestMapping(value = "/deleteRole/{rid}", method = RequestMethod.GET)
    String deleterole(@PathVariable("rid") int rid) {
        systemService.deleteRole(rid);
        return "system/roleadmin";
    }

    @RequestMapping(value = "roleInfo/{rid}", method = RequestMethod.GET)
    @ResponseBody
    Role getRolebyrid(@PathVariable("rid") int rid) {
        return systemService.getRoleById(rid);
    }

    @RequestMapping(value = "updateRole/{rid}", method = RequestMethod.POST)
    String updaterole(@PathVariable("rid") int rid,
            @RequestParam(value = "permissionname[]") String[] permissionnames) {
        systemService.deleteRolePermission(rid);
        systemService.updateRole(rid, permissionnames);
        return "system/roleadmin";
    }

    @RequestMapping(value = "permissions", method = RequestMethod.GET)
    @ResponseBody
    DataGrid<Permission> getpermissions(@RequestParam("current") int current,
            @RequestParam("rowCount") int rowCount) {
        List<Permission> p = systemService.getPermissions();
        List<Permission> list = systemService.getPagedPermissions(current, rowCount);
        DataGrid<Permission> grid = new DataGrid<Permission>();
        grid.setCurrent(current);
        grid.setRowCount(rowCount);
        grid.setTotal(p.size());
        grid.setRows(list);
        return grid;
    }

    @RequestMapping(value = "addPermission", method = RequestMethod.POST)
    String addpermission(@RequestParam("permissionName") String permissionName) {
        systemService.addPermission(permissionName);
        return "system/permissionadmin";
    }

    @RequestMapping(value = "deletePermission/{pid}", method = RequestMethod.GET)
    String deletepermission(@PathVariable("pid") int pid) {
        systemService.deletePermission(pid);
        return "system/permissionadmin";
    }

}
