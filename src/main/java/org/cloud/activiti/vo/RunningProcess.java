package org.cloud.activiti.vo;

public class RunningProcess {
    private String executionid;
    private String processInstanceid;
    private String businesskey;
    private String activityid;

    public String getExecutionid() {
        return executionid;
    }

    public void setExecutionid(String executionid) {
        this.executionid = executionid;
    }

    public String getProcessInstanceid() {
        return processInstanceid;
    }

    public void setProcessInstanceid(String processInstanceid) {
        this.processInstanceid = processInstanceid;
    }

    public String getBusinesskey() {
        return businesskey;
    }

    public void setBusinesskey(String businesskey) {
        this.businesskey = businesskey;
    }

    public String getActivityid() {
        return activityid;
    }

    public void setActivityid(String activityid) {
        this.activityid = activityid;
    }

}
