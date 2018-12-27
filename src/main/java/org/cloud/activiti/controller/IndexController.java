package org.cloud.activiti.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    
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
