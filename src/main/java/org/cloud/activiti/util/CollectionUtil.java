package org.cloud.activiti.util;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.cloud.activiti.entity.VacationRequest;
import org.cloud.activiti.vo.DataGrid;
import org.cloud.activiti.vo.ProcessInfo;
import org.cloud.activiti.vo.RunningProcess;
import org.cloud.activiti.vo.VacationTask;

public class CollectionUtil {

    public static DataGrid<VacationTask> vacationRequestToVacationTask(int current, int rowCount,
            long totalSize, List<VacationRequest> requests) {
        List<VacationTask> tasks = new ArrayList<VacationTask>();
        for (VacationRequest apply : requests) {
            VacationTask task = new VacationTask();
            task.setApplyTime(apply.getApplyTime());
            task.setUserId(apply.getUserId());
            task.setEndTime(apply.getEndTime());
            task.setId(apply.getId());
            task.setLeaveType(apply.getLeaveType());
            task.setProcessInstanceId(apply.getProcessInstanceId());
            task.setProcessDefId(apply.getTask().getProcessDefinitionId());
            task.setReason(apply.getReason());
            task.setStartTime(apply.getStartTime());
            task.setTaskCreateTime(apply.getTask().getCreateTime());
            task.setTaskId(apply.getTask().getId());
            task.setTaskName(apply.getTask().getName());
            tasks.add(task);
        }
        return new DataGrid<VacationTask>(current, rowCount, totalSize, tasks);
    }

    public static DataGrid<ProcessInfo> processDefinitionToProcessInfo(int current, int rowCount,
            long totalSize, List<ProcessDefinition> requests) {
        List<ProcessInfo> mInfos = new ArrayList<ProcessInfo>();
        for (ProcessDefinition processDefinition : requests) {
            ProcessInfo processInfo = new ProcessInfo();
            processInfo.setId(processDefinition.getId());
            processInfo.setDeploymentId(processDefinition.getDeploymentId());
            processInfo.setKey(processDefinition.getKey());
            processInfo.setName(processDefinition.getName());
            processInfo.setResourceName(processDefinition.getResourceName());
            processInfo.setDiagramResourceName(processDefinition.getDiagramResourceName());
            mInfos.add(processInfo);
        }
        return new DataGrid<ProcessInfo>(current, rowCount, totalSize, mInfos);
    }

    public static DataGrid<RunningProcess> processInstanceToRunningProcess(int current,
            int rowCount, long totalSize, List<ProcessInstance> requests) {
        List<RunningProcess> mList = new ArrayList<RunningProcess>();
        for (ProcessInstance processDefinition : requests) {
            RunningProcess process = new RunningProcess();
            process.setActivityid(processDefinition.getActivityId());
            process.setBusinesskey(processDefinition.getBusinessKey());
            process.setExecutionid(processDefinition.getId());
            process.setProcessInstanceid(processDefinition.getProcessInstanceId());
            mList.add(process);
        }
        return new DataGrid<RunningProcess>(current, rowCount, totalSize, mList);
    }

}
