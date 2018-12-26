package org.cloud.activiti.vo;

import org.cloud.activiti.entity.PurchaseRequest;
import org.cloud.activiti.entity.VacationRequest;

public class HistoryProcess {
    private String processDefinitionId;
    private String businessKey;
    private VacationRequest vacationRequest;
    private PurchaseRequest purchaseRequest;

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public VacationRequest getVacationRequest() {
        return vacationRequest;
    }

    public void setVacationRequest(VacationRequest vacationRequest) {
        this.vacationRequest = vacationRequest;
    }

    public PurchaseRequest getPurchaseRequest() {
        return purchaseRequest;
    }

    public void setPurchaseRequest(PurchaseRequest purchaseRequest) {
        this.purchaseRequest = purchaseRequest;
    }

}
