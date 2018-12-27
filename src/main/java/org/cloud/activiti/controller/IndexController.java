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
    
    @RequiresPermissions(value = { "deptManager" })
    @GetMapping(value = "/manager/details")
    @ResponseBody
    public String getManager() {
        return "manager";
    }

}
