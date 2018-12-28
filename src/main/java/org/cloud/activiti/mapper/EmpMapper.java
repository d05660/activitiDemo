package org.cloud.activiti.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.cloud.activiti.common.BaseMapper;
import org.cloud.activiti.entity.Emp;

public interface EmpMapper extends BaseMapper<Emp> {
    List<Emp> selectAllOrderBy(@Param("column") String column, @Param("orderDir") String orderDir);
}
