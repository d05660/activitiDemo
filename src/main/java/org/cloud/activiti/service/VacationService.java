package org.cloud.activiti.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.cloud.activiti.entity.VacationRequest;

public interface VacationService {
    public void completeReportBack(String taskId, String realStartTime, String realEndTime);

    public List<VacationRequest> getDeptTaskByPage(int firstRow, int rowCount);

    public List<Task> getDeptTasks(String userId);

    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinition,
            List<HistoricActivityInstance> historicActivityInstances);

    public List<VacationRequest> getHrTaskByPage(int firstRow, int rowCount);

    public List<Task> getHrTasks(String userId);

    public VacationRequest getLeaveApply(int id);

    public List<VacationRequest> getUpdateApplyTaskByPage(int firstRow, int rowCount);

    public List<Task> getUpdateApplyTasks(String userId);

    public List<VacationRequest> getXJTaskByPage(int firstRow, int rowCount);

    public List<Task> getXJTasks(String userId);

    public ProcessInstance startWorkflow(VacationRequest apply, String userId,
            Map<String, Object> variables);

    public void updateComplete(String taskId, VacationRequest leave, String reApply);
}
