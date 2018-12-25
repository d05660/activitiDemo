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
import org.cloud.activiti.entity.VacationRequest;
import org.cloud.activiti.mapper.VacationMapper;
import org.cloud.activiti.service.VacationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationServiceImpl implements VacationService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private VacationMapper vacationRequestMapper;
    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Override
    public void completeReportBack(String taskId, String realStartTime, String realEndTime) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String instanceid = task.getProcessInstanceId();
        ProcessInstance ins = runtimeService.createProcessInstanceQuery()
                .processInstanceId(instanceid).singleResult();
        String businesskey = ins.getBusinessKey();
        VacationRequest a = vacationRequestMapper.get(Integer.parseInt(businesskey));
        a.setRealityStartTime(realStartTime);
        a.setRealityEndTime(realEndTime);
        vacationRequestMapper.update(a);
        taskService.complete(taskId);
    }

    @Override
    public List<VacationRequest> getDeptTaskByPage(int firstRow, int rowCount) {
        return getTaskListByPage("部門経理", firstRow, rowCount);
    }

    @Override
    public long getDeptTaskCount(String userId) {
        return taskService.createTaskQuery().taskCandidateGroup("部門経理").count();
    }

    @Override
    public List<Task> getDeptTasks(String userId) {
        return taskService.createTaskQuery().taskCandidateGroup("部門経理").list();
    }

    @Override
    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinition,
            List<HistoricActivityInstance> historicActivityInstances) {
        List<String> highFlows = new ArrayList<String>();
        for (int i = 0; i < historicActivityInstances.size(); i++) {
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

    @Override
    public List<VacationRequest> getHrTaskByPage(int firstRow, int rowCount) {
        return getTaskListByPage("人事", firstRow, rowCount);
    }

    @Override
    public long getHrTaskCount(String userId) {
        return taskService.createTaskQuery().taskCandidateGroup("人事").count();
    }

    @Override
    public List<Task> getHrTasks(String userId) {
        return taskService.createTaskQuery().taskCandidateGroup("人事").list();
    }

    @Override
    public VacationRequest getLeaveApply(int id) {
        return vacationRequestMapper.get(id);
    }

    private List<VacationRequest> getTaskListByPage(String candidateGroup, int firstRow,
            int rowCount) {
        List<VacationRequest> results = new ArrayList<VacationRequest>();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(candidateGroup)
                .listPage(firstRow, rowCount);
        for (Task task : tasks) {
            String instanceid = task.getProcessInstanceId();
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(instanceid).singleResult();
            String businesskey = processInstance.getBusinessKey();
            VacationRequest a = vacationRequestMapper.get(Integer.parseInt(businesskey));
            a.setTask(task);
            results.add(a);
        }
        return results;
    }

    @Override
    public List<VacationRequest> getUpdateApplyTaskByPage(int firstRow, int rowCount) {
        return getTaskListByPage("調整申?", firstRow, rowCount);
    }

    @Override
    public long getUpdateApplyTaskCount(String userId) {
        return taskService.createTaskQuery().taskCandidateOrAssigned(userId).taskName("?整申?").count();
    }

    @Override
    public List<Task> getUpdateApplyTasks(String userId) {
        return taskService.createTaskQuery().taskCandidateOrAssigned(userId).taskName("?整申?").list();
    }

    @Override
    public List<VacationRequest> getXJTaskByPage(int firstRow, int rowCount) {
        return getTaskListByPage("?假", firstRow, rowCount);
    }

    @Override
    public long getXJTaskCount(String userId) {
        return taskService.createTaskQuery().taskCandidateOrAssigned(userId).taskName("?假").count();
    }

    @Override
    public List<Task> getXJTasks(String userId) {
        return taskService.createTaskQuery().taskCandidateOrAssigned(userId).taskName("?假").list();
    }

    @Override
    public ProcessInstance startWorkflow(VacationRequest apply, String userId,
            Map<String, Object> variables) {
        apply.setApplyTime(new Date().toString());
        apply.setUserId(userId);
        vacationRequestMapper.save(apply);
        String businessKey = String.valueOf(apply.getId());
        identityService.setAuthenticatedUserId(userId);
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("leave", businessKey,
                variables);
        String instanceId = instance.getId();
        logger.debug("ProcessInstance businessKey: " + businessKey + " instanceId: " + instanceId);
        apply.setProcessInstanceId(instanceId);
        vacationRequestMapper.update(apply);
        return instance;
    }

    @Override
    public void updateComplete(String taskId, VacationRequest leave, String reApply) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        String instanceid = task.getProcessInstanceId();
        ProcessInstance ins = runtimeService.createProcessInstanceQuery()
                .processInstanceId(instanceid).singleResult();
        String businesskey = ins.getBusinessKey();
        VacationRequest a = vacationRequestMapper.get(Integer.parseInt(businesskey));
        a.setLeaveType(leave.getLeaveType());
        a.setStartTime(leave.getStartTime());
        a.setEndTime(leave.getEndTime());
        a.setReason(leave.getReason());
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("reapply", reApply);
        if ("true".equals(reApply)) {
            vacationRequestMapper.update(a);
            taskService.complete(taskId, variables);
        } else
            taskService.complete(taskId, variables);

    }

}
