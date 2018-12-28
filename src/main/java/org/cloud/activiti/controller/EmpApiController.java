package org.cloud.activiti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloud.activiti.common.BaseController;
import org.cloud.activiti.entity.Emp;
import org.cloud.activiti.entity.QueryResult;
import org.cloud.activiti.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class EmpApiController extends BaseController {
    @Autowired
    IEmpService empService;

    /**
     * 查询
     */
    @GetMapping("/emps")
    @ResponseBody
    public Map<String, Object> listEmp(@RequestParam String draw, @RequestParam int startIndex,
            @RequestParam int pageSize,
            @RequestParam(value = "orderColumn", required = false, defaultValue = "id") String orderColumn,
            @RequestParam(value = "orderDir", required = false, defaultValue = "asc") String orderDir) {
        Map<String, Object> info = new HashMap<>();
        info.put("pageData", empService.getAllByOrder(orderColumn, orderDir, startIndex, pageSize));
        info.put("total", empService.getCount(new Emp()));
        info.put("draw", draw);
        return info;
    }

    /**
     * 新增
     */
    @PostMapping("/emps")
    @ResponseBody
    public QueryResult addEmp(@RequestBody Emp emp) {
        return renderSuccess(empService.save(emp));
    }

    /**
     * 更新
     */
    @PutMapping("/emps/{empId}")
    @ResponseBody
    public QueryResult updateEmp(@PathVariable String empId, @RequestBody Emp emp) {
        emp.setId(Long.parseLong(empId));
        return renderSuccess(empService.save(emp));
    }

    /** 删除 */
    @DeleteMapping("/emps/{id}")
    @ResponseBody
    public QueryResult removeEmp(@PathVariable String id) {
        return renderSuccess(empService.deleteById(Long.parseLong(id)));
    }

    /**
     * 批量删除
     */
    @PostMapping("/emps/batch")
    @ResponseBody
    public QueryResult removeEmps(@RequestBody List<String> ids) {
        int sum = 0;
        for (String id : ids) {
            sum += empService.deleteById(Long.parseLong(id));
        }
        return renderSuccess(sum);
    }
}
