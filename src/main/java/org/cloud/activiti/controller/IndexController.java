package org.cloud.activiti.controller;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    @Resource
    private SessionDAO sessionDAO;
    
    @GetMapping(value = "/index")
    public String getIndex(Model model) {
        model.addAttribute("name", "world");
        return "index";
    }
    
    @GetMapping("/empList")
    public String getEmpPage(Model model) {
        return "emps";
    }
    
    @RequiresPermissions(value = { "L1" })
    @GetMapping(value = "/manager/details")
    @ResponseBody
    public String getManager() {
        return "manager";
    }
    
    @GetMapping(value = "/index1")
    public String getManager(Model model) {
        model.addAttribute("name", "world");
        return "manager";
    }

}
