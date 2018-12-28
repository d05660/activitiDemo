package org.cloud.activiti.service;

import java.util.List;

import org.cloud.activiti.common.BaseService;
import org.cloud.activiti.entity.Emp;

public interface IEmpService extends BaseService<Emp> {

    List<Emp> getAllByOrder(String column, String orderDir, int pageNum, int pageSize);

    Long saveBatch(List<Emp> list);

    Long updateBatch(List<Emp> list);
}
