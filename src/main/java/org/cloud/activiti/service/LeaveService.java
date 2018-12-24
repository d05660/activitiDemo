package org.cloud.activiti.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.cloud.activiti.entity.LeaveApply;

public interface LeaveService {
    public ProcessInstance startWorkflow(LeaveApply apply, String userId,
            Map<String, Object> variables);

    public List<LeaveApply> getPageDeptTask(String userId, int firstRow, int rowCount);

    public int getAllDeptTask(String userId);

    public LeaveApply getLeave(int id);

    public List<LeaveApply> getPageHrTask(String userId, int firstRow, int rowCount);

    public int getAllHrTask(String userId);

    public List<LeaveApply> getPageXJTask(String userId, int firstRow, int rowCount);

    public int getAllXJTask(String userId);

    public List<LeaveApply> getPageUpdateApplyTask(String userId, int firstRow, int rowCount);

    public int getAllUpdateApplyTask(String userId);

    public void completeReportBack(String taskId, String realStartTime, String realEndTime);

    public void updateComplete(String taskId, LeaveApply leave, String reApply);

    public List<String> getHighLightedFlows(ProcessDefinitionEntity processDefinition,
            List<HistoricActivityInstance> historicActivityInstances);
}
