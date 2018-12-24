package org.cloud.activiti.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.cloud.activiti.entity.LeaveApply;
import org.cloud.activiti.mapper.LeaveApplyMapper;
import org.cloud.activiti.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveApplyMapper leaveMapper;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Override
    public ProcessInstance startWorkflow(LeaveApply apply, String userId,
            Map<String, Object> variables) {
        apply.setApplyTime(new Date().toString());
        apply.setUserId(userId);
        leaveMapper.save(apply);
        String businessKey = String.valueOf(apply.getId());
        identityService.setAuthenticatedUserId(userId);
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("leave", businessKey,
                variables);
        System.out.println(businessKey);
        String instanceid = instance.getId();
        apply.setProcessInstanceId(instanceid);
        leaveMapper.update(apply);
        return instance;
    }

    private List<LeaveApply> getPagedTaskList(String userId, String candidateGroup, int firstRow,
            int rowCount) {
        List<LeaveApply> results = new ArrayList<LeaveApply>();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(candidateGroup)
                .listPage(firstRow, rowCount);
        for (Task task : tasks) {
            String instanceid = task.getProcessInstanceId();
            ProcessInstance ins = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(instanceid).singleResult();
            String businesskey = ins.getBusinessKey();
            LeaveApply a = leaveMapper.get(Integer.parseInt(businesskey));
            a.setTask(task);
            results.add(a);
        }
        return results;
    }

    @Override
    public List<LeaveApply> getPageDeptTask(String userId, int firstRow, int rowCount) {
        return getPagedTaskList(userId, "部??理", firstRow, rowCount);
    }

    @Override
    public List<LeaveApply> getPageHrTask(String userId, int firstRow, int rowCount) {
        return getPagedTaskList(userId, "人事", firstRow, rowCount);
    }

    @Override
    public List<LeaveApply> getPageXJTask(String userId, int firstRow, int rowCount) {
        return getPagedTaskList(userId, "?假", firstRow, rowCount);
    }

    @Override
    public List<LeaveApply> getPageUpdateApplyTask(String userId, int firstRow, int rowCount) {
        return getPagedTaskList(userId, "?整申?", firstRow, rowCount);
    }

    @Override
    public LeaveApply getLeave(int id) {
        LeaveApply leave = leaveMapper.get(id);
        return leave;
    }

    @Override
    public int getAllDeptTask(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("部??理").list();
        return tasks.size();
    }

    @Override
    public int getAllHrTask(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("人事").list();
        return tasks.size();
    }

    @Override
    public int getAllXJTask(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(userId)
                .taskName("?假").list();
        return tasks.size();
    }

    @Override
    public int getAllUpdateApplyTask(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(userId)
                .taskName("?整申?").list();
        return tasks.size();
    }

    @Override
    public void completeReportBack(String taskId, String realStartTime, String realEndTime) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String instanceid = task.getProcessInstanceId();
        ProcessInstance ins = runtimeService.createProcessInstanceQuery()
                .processInstanceId(instanceid).singleResult();
        String businesskey = ins.getBusinessKey();
        LeaveApply a = leaveMapper.get(Integer.parseInt(businesskey));
        a.setRealityStartTime(realStartTime);
        a.setRealityEndTime(realEndTime);
        leaveMapper.update(a);
        taskService.complete(taskId);
    }

    @Override
    public void updateComplete(String taskId, LeaveApply leave, String reApply) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String instanceid = task.getProcessInstanceId();
        ProcessInstance ins = runtimeService.createProcessInstanceQuery()
                .processInstanceId(instanceid).singleResult();
        String businesskey = ins.getBusinessKey();
        LeaveApply a = leaveMapper.get(Integer.parseInt(businesskey));
        a.setLeaveType(leave.getLeaveType());
        a.setStartTime(leave.getStartTime());
        a.setEndTime(leave.getEndTime());
        a.setReason(leave.getReason());
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("reapply", reApply);
        if ("true".equals(reApply)) {
            leaveMapper.update(a);
            taskService.complete(taskId, variables);
        } else
            taskService.complete(taskId, variables);

    }

    @Override
    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinition,
            List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<String>();// 用以保存高亮的?flowId
        for (int i = 0; i < historicActivityInstances.size(); i++) {// ??史流程?点?行遍?
            ActivityImpl activityImpl = processDefinition
                    .findActivity(historicActivityInstances.get(i).getActivityId());
            List<ActivityImpl> sameStartTimeNodes = new ArrayList<ActivityImpl>();// 用以保存后需?始??相同的?点
            if ((i + 1) >= historicActivityInstances.size()) {
                break;
            }
            ActivityImpl sameActivityImpl1 = processDefinition
                    .findActivity(historicActivityInstances.get(i + 1).getActivityId());// 将后面第一个?点放在??相同?点的集合里
            sameStartTimeNodes.add(sameActivityImpl1);
            for (int j = i + 1; j < historicActivityInstances.size() - 1; j++) {
                HistoricActivityInstance activityImpl1 = historicActivityInstances.get(j);// 后?第一个?点
                HistoricActivityInstance activityImpl2 = historicActivityInstances.get(j + 1);// 后?第二个?点
                if (activityImpl1.getStartTime().equals(activityImpl2.getStartTime())) {// 如果第一个?点和第二个?点?始??相同保存
                    ActivityImpl sameActivityImpl2 = processDefinition
                            .findActivity(activityImpl2.getActivityId());
                    sameStartTimeNodes.add(sameActivityImpl2);
                } else {// 有不相同跳出循?
                    break;
                }
            }
            List<PvmTransition> pvmTransitions = activityImpl.getOutgoingTransitions();// 取出?点的所有出去的?
            for (PvmTransition pvmTransition : pvmTransitions) {// ?所有的??行遍?
                ActivityImpl pvmActivityImpl = (ActivityImpl) pvmTransition.getDestination();// 如果取出的?的目??点存在??相同的?点里，保存??的id，?行高亮?示
                if (sameStartTimeNodes.contains(pvmActivityImpl)) {
                    highFlows.add(pvmTransition.getId());
                }
            }
        }
        return highFlows;
    }

}
