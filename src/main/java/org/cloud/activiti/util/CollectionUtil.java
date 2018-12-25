package org.cloud.activiti.util;

import java.util.ArrayList;
import java.util.List;

import org.cloud.activiti.entity.VacationRequest;
import org.cloud.activiti.vo.DataGrid;
import org.cloud.activiti.vo.VacationTask;

public class CollectionUtil {

    public static DataGrid<VacationTask> convertReuestToTask(List<VacationRequest> requests, int current, 
            int rowCount, long totalSize) {
        DataGrid<VacationTask> grid = new DataGrid<VacationTask>();
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
        grid.setRowCount(rowCount);
        grid.setCurrent(current);
        grid.setTotal(totalSize);
        grid.setRows(tasks);
        return grid;
    }

}
