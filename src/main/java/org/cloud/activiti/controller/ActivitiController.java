package org.cloud.activiti.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.cloud.activiti.entity.VacationRequest;
import org.cloud.activiti.service.UserService;
import org.cloud.activiti.service.VacationService;
import org.cloud.activiti.util.CollectionUtil;
import org.cloud.activiti.vo.DataGrid;
import org.cloud.activiti.vo.ProcessInfo;
import org.cloud.activiti.vo.VacationTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ActivitiController {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runService;
    @Autowired
    private FormService formService;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private VacationService vacationService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private UserService userService;

    @RequestMapping("/processList")
    String processList() {
        return "activiti/processList";
    }

    @RequestMapping("/uploadWorkflow")
    public String fileUpload(@RequestParam MultipartFile uploadFile, HttpServletRequest request) {
        try {
            String filename = uploadFile.getOriginalFilename();
            InputStream inputStream = uploadFile.getInputStream();
            repositoryService.createDeployment().addInputStream(filename, inputStream).deploy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping(value = "/getProcessLists")
    @ResponseBody
    public DataGrid<ProcessInfo> getList(@RequestParam("current") int current, @RequestParam("rowCount") int rowCount) {
        int firstRow = (current - 1) * rowCount;
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().listPage(firstRow, rowCount);
        int total = repositoryService.createProcessDefinitionQuery().list().size();
        List<ProcessInfo> mylist = new ArrayList<ProcessInfo>();
        for (ProcessDefinition processDefinition : list) {
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setId(processDefinition.getId());
            processInfo.setDeploymentId(processDefinition.getDeploymentId());
            processInfo.setKey(processDefinition.getKey());
            processInfo.setName(processDefinition.getName());
            processInfo.setResourceName(processDefinition.getResourceName());
            processInfo.setDiagramResourceName(processDefinition.getDiagramResourceName());
            mylist.add(processInfo);
        }

        DataGrid<ProcessInfo> grid = new DataGrid<ProcessInfo>();
        grid.setCurrent(current);
        grid.setRowCount(rowCount);
        grid.setRows(mylist);
        grid.setTotal(total);
        return grid;
    }

    @RequestMapping("/showResource")
    public void export(@RequestParam("pdid") String pdid, @RequestParam("resource") String resource,
            HttpServletResponse response) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(pdid)
                .singleResult();
        InputStream inputStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resource);
        ServletOutputStream output = response.getOutputStream();
        IOUtils.copy(inputStream, output);
    }

    @RequestMapping("/deleteDeploy")
    public String deletedeploy(@RequestParam("deployid") String deployid) throws Exception {
        repositoryService.deleteDeployment(deployid, true);
        return "activiti/processList";
    }

    @RequestMapping("/runningProcess")
    public String task() {
        return "activiti/runningProcess";
    }

    @RequestMapping("/deptLeaderAudit")
    public String mytask() {
        return "activiti/deptLeaderAudit";
    }

    @RequestMapping("/hrAudit")
    public String hr() {
        return "activiti/hrAudit";
    }

    @RequestMapping("/index")
    public String my() {
        return "index";
    }

    @RequestMapping("/leaveApply")
    public String leave() {
        return "activiti/leaveApply";
    }

    @RequestMapping("/reportBack")
    public String reprotback() {
        return "activiti/reportBack";
    }

    @RequestMapping("/modifyApply")
    public String modifyapply() {
        return "activiti/modifyApply";
    }

    @RequestMapping(value = "/startLeave", method = RequestMethod.POST)
    @ResponseBody
    public String startLeave(VacationRequest apply, HttpSession session) {
        String userid = (String) session.getAttribute("username");
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("applyuserid", userid);
        ProcessInstance ins = vacationService.startWorkflow(apply, userid, variables);
        System.out.println("流程id" + ins.getId() + "已??");
        return "success";
    }

    @RequestMapping(value = "/deptTaskList")
    @RequiresPermissions(value = { "deptManager" })
    @ResponseBody
    public DataGrid<VacationTask> getDeptTaskList(HttpSession session, @RequestParam("current") int current,
            @RequestParam("rowCount") int rowCount) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        List<VacationRequest> results = vacationService.getDeptTaskByPage((current - 1) * rowCount, rowCount);
        long totalSize = vacationService.getDeptTaskCount(userName);
        return CollectionUtil.convertReuestToTask(results, current, rowCount, totalSize);
    }

    @RequiresPermissions(value = { "deptManager" })
    @RequestMapping(value = "/hrtasklist")
    @ResponseBody
    public DataGrid<VacationTask> gethrtasklist(HttpSession session, @RequestParam("current") int current,
            @RequestParam("rowCount") int rowCount) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        List<VacationRequest> results = vacationService.getHrTaskByPage((current - 1) * rowCount, rowCount);
        long totalSize = vacationService.getHrTaskCount(userName);
        return CollectionUtil.convertReuestToTask(results, current, rowCount, totalSize);
    }

    @RequiresPermissions(value = { "deptManager" })
    @RequestMapping(value = "/xjtasklist")
    @ResponseBody
    public DataGrid<VacationTask> getXJtasklist(HttpSession session, @RequestParam("current") int current,
            @RequestParam("rowCount") int rowCount) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        List<VacationRequest> results = vacationService.getXJTaskByPage((current - 1) * rowCount, rowCount);
        long totalSize = vacationService.getXJTaskCount(userName);
        return CollectionUtil.convertReuestToTask(results, current, rowCount, totalSize);
    }

    @RequiresPermissions(value = { "deptManager" })
    @RequestMapping(value = "/updateTaskList")
    @ResponseBody
    public DataGrid<VacationTask> getupdatetasklist(HttpSession session, @RequestParam("current") int current,
            @RequestParam("rowCount") int rowCount) {
        String userName = (String) SecurityUtils.getSubject().getPrincipal();
        List<VacationRequest> results = vacationService.getUpdateApplyTaskByPage((current - 1) * rowCount, rowCount);
        long totalSize = vacationService.getUpdateApplyTaskCount(userName);
        return CollectionUtil.convertReuestToTask(results, current, rowCount, totalSize);
    }

    // @RequestMapping(value = "/dealtask")
    // @ResponseBody
    // public String taskdeal(@RequestParam("taskid") String taskid,
    // HttpServletResponse response) {
    // Task task = taskService.createTaskQuery().taskId(taskid).singleResult();
    // ProcessInstance process = runService.createProcessInstanceQuery()
    // .processInstanceId(task.getProcessInstanceId()).singleResult();
    // LeaveApply leave = leaveService.getLeave(new
    // Integer(process.getBusinessKey()));
    // return leave;
    // }
    //
    // @RequestMapping(value = "/activiti/task-deptleaderaudit")
    // String url() {
    // return "/activiti/task-deptleaderaudit";
    // }
    //
    // @RequestMapping(value = "/task/deptcomplete/{taskid}")
    // @ResponseBody
    // public String deptcomplete(HttpSession session, @PathVariable("taskid")
    // String taskid,
    // HttpServletRequest req) {
    // String userid = (String) session.getAttribute("username");
    // Map<String, Object> variables = new HashMap<String, Object>();
    // String approve = req.getParameter("deptleaderapprove");
    // variables.put("deptleaderapprove", approve);
    // taskService.claim(taskid, userid);
    // taskService.complete(taskid, variables);
    // return JSON.toJSONString("success");
    // }
    //
    // @RequestMapping(value = "/task/hrcomplete/{taskid}")
    // @ResponseBody
    // public String hrcomplete(HttpSession session, @PathVariable("taskid")
    // String taskid,
    // HttpServletRequest req) {
    // String userid = (String) session.getAttribute("username");
    // Map<String, Object> variables = new HashMap<String, Object>();
    // String approve = req.getParameter("hrapprove");
    // variables.put("hrapprove", approve);
    // taskservice.claim(taskid, userid);
    // taskservice.complete(taskid, variables);
    // return JSON.toJSONString("success");
    // }
    //
    // @RequestMapping(value = "/task/reportcomplete/{taskid}")
    // @ResponseBody
    // public String reportbackcomplete(@PathVariable("taskid") String taskid,
    // HttpServletRequest req) {
    // String realstart_time = req.getParameter("realstart_time");
    // String realend_time = req.getParameter("realend_time");
    // leaveService.completereportback(taskid, realstart_time, realend_time);
    // return JSON.toJSONString("success");
    // }
    //
    // @RequestMapping(value = "/task/updatecomplete/{taskid}")
    // @ResponseBody
    // public String updatecomplete(@PathVariable("taskid") String taskid,
    // @ModelAttribute("leave") LeaveApply leave, @RequestParam("reapply")
    // String reapply) {
    // leaveService.updatecomplete(taskid, leave, reapply);
    // return JSON.toJSONString("success");
    // }
    //
    // @RequestMapping("involvedprocess") // 参与的正在?行的?假流程
    // @ResponseBody
    // public DataGrid<RunningProcess> allexeution(HttpSession session,
    // @RequestParam("current") int current, @RequestParam("rowCount") int
    // rowCount) {
    // int firstrow = (current - 1) * rowCount;
    // String userid = (String) session.getAttribute("username");
    // ProcessInstanceQuery query = runService.createProcessInstanceQuery();
    // int total = (int) query.count();
    // List<ProcessInstance> a =
    // query.processDefinitionKey("leave").involvedUser(userid)
    // .listPage(firstrow, rowCount);
    // List<RunningProcess> list = new ArrayList<RunningProcess>();
    // for (ProcessInstance p : a) {
    // RunningProcess process = new RunningProcess();
    // process.setActivityid(p.getActivityId());
    // process.setBusinesskey(p.getBusinessKey());
    // process.setExecutionid(p.getId());
    // process.setProcessInstanceid(p.getProcessInstanceId());
    // list.add(process);
    // }
    // DataGrid<RunningProcess> grid = new DataGrid<RunningProcess>();
    // grid.setCurrent(current);
    // grid.setRowCount(rowCount);
    // grid.setTotal(total);
    // grid.setRows(list);
    // return grid;
    // }
    //
    // @RequestMapping("/getfinishprocess")
    // @ResponseBody
    // public DataGrid<HistoryProcess> getHistory(HttpSession session,
    // @RequestParam("current") int current, @RequestParam("rowCount") int
    // rowCount) {
    // String userid = (String) session.getAttribute("username");
    // HistoricProcessInstanceQuery process = historyService
    // .createHistoricProcessInstanceQuery()
    // .processDefinitionKey("leave")
    // .startedBy(userid)
    // .finished();
    // int total = (int) process.count();
    // int firstrow = (current - 1) * rowCount;
    // List<HistoricProcessInstance> info = process.listPage(firstrow,
    // rowCount);
    // List<HistoryProcess> list = new ArrayList<HistoryProcess>();
    // for (HistoricProcessInstance history : info) {
    // HistoryProcess his = new HistoryProcess();
    // String bussinesskey = history.getBusinessKey();
    // LeaveApply apply = leaveService.getLeave(Integer.parseInt(bussinesskey));
    // his.setLeaveapply(apply);
    // his.setBusinessKey(bussinesskey);
    // his.setProcessDefinitionId(history.getProcessDefinitionId());
    // list.add(his);
    // }
    // DataGrid<HistoryProcess> grid = new DataGrid<HistoryProcess>();
    // grid.setCurrent(current);
    // grid.setRowCount(rowCount);
    // grid.setTotal(total);
    // grid.setRows(list);
    // return grid;
    // }
    //
    // @RequestMapping("/historyprocess")
    // public String history() {
    // return "activiti/historyprocess";
    // }
    //
    // @RequestMapping("/processinfo")
    // @ResponseBody
    // public List<HistoricActivityInstance> processinfo(
    // @RequestParam("instanceid") String instanceid) {
    // List<HistoricActivityInstance> his = historyService
    // .createHistoricActivityInstanceQuery()
    // .processInstanceId(instanceid)
    // .orderByHistoricActivityInstanceStartTime()
    // .asc()
    // .list();
    // return his;
    // }
    //
    // @RequestMapping("/processhis")
    // @ResponseBody
    // public List<HistoricActivityInstance> processhis(@RequestParam("ywh")
    // String ywh) {
    // String instanceid = historyService
    // .createHistoricProcessInstanceQuery()
    // .processDefinitionKey("purchase")
    // .processInstanceBusinessKey(ywh)
    // .singleResult()
    // .getId();
    // System.out.println(instanceid);
    // List<HistoricActivityInstance> his = historyService
    // .createHistoricActivityInstanceQuery()
    // .processInstanceId(instanceid)
    // .orderByHistoricActivityInstanceStartTime()
    // .asc()
    // .list();
    // return his;
    // }
    //
    // @RequestMapping("myleaveprocess")
    // String myleaveprocess() {
    // return "activiti/myleaveprocess";
    // }
    //
    // @RequestMapping("traceprocess/{executionid}")
    // public void traceprocess(@PathVariable("executionid") String executionid,
    // HttpServletResponse response) throws Exception {
    // ProcessInstance process = runService.createProcessInstanceQuery()
    // .processInstanceId(executionid).singleResult();
    // BpmnModel bpmnmodel =
    // repositoryService.getBpmnModel(process.getProcessDefinitionId());
    // List<String> activeActivityIds =
    // runService.getActiveActivityIds(executionid);
    // DefaultProcessDiagramGenerator gen = new
    // DefaultProcessDiagramGenerator();
    // // ?得?史活????体（通?????正序排序，不然有的?可以?制不出来）
    // List<HistoricActivityInstance> historicActivityInstances = historyService
    // .createHistoricActivityInstanceQuery().executionId(executionid)
    // .orderByHistoricActivityInstanceStartTime().asc().list();
    // // ?算活??
    // List<String> highLightedFlows = leaveService.getHighLightedFlows(
    // (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
    // .getDeployedProcessDefinition(process.getProcessDefinitionId()),
    // historicActivityInstances);
    //
    // InputStream in = gen.generateDiagram(bpmnmodel, "png", activeActivityIds,
    // highLightedFlows,
    // "宋体", "宋体", null, 1.0);
    // // InputStream in=gen.generateDiagram(bpmnmodel, "png",
    // activeActivityIds);
    // ServletOutputStream output = response.getOutputStream();
    // IOUtils.copy(in, output);
    // }
    //
    // @RequestMapping("myleaves")
    // String myleaves() {
    // return "activiti/myleaves";
    // }
    //
    // @RequestMapping("setupprocess")
    // @ResponseBody
    // public DataGrid<RunningProcess> setupprocess(HttpSession session,
    // @RequestParam("current") int current, @RequestParam("rowCount") int
    // rowCount) {
    // int firstrow = (current - 1) * rowCount;
    // String userid = (String) session.getAttribute("username");
    // ProcessInstanceQuery query = runService.createProcessInstanceQuery();
    // int total = (int) query.count();
    // List<ProcessInstance> a =
    // query.processDefinitionKey("leave").involvedUser(userid)
    // .listPage(firstrow, rowCount);
    // List<RunningProcess> list = new ArrayList<RunningProcess>();
    // for (ProcessInstance p : a) {
    // RunningProcess process = new RunningProcess();
    // process.setActivityid(p.getActivityId());
    // process.setBusinesskey(p.getBusinessKey());
    // process.setExecutionid(p.getId());
    // process.setProcessInstanceid(p.getProcessInstanceId());
    // LeaveApply l =
    // leaveService.getLeave(Integer.parseInt(p.getBusinessKey()));
    // if (l.getUserId().equals(userid))
    // list.add(process);
    // else
    // continue;
    // }
    // DataGrid<RunningProcess> grid = new DataGrid<RunningProcess>();
    // grid.setCurrent(current);
    // grid.setRowCount(rowCount);
    // grid.setTotal(total);
    // grid.setRows(list);
    // return grid;
    // }

}
