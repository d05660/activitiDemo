package org.cloud.activiti.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.cloud.activiti.common.BaseServiceImpl;
import org.cloud.activiti.common.PasswordHelper;
import org.cloud.activiti.entity.Emp;
import org.cloud.activiti.mapper.EmpMapper;
import org.cloud.activiti.service.IEmpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;

@Service
public class EmpServiceImpl extends BaseServiceImpl<EmpMapper, Emp> implements IEmpService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EmpMapper empMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Override
    public List<Emp> getAllByOrder(String column, String orderDir, int startIndex, int pageSize) {
        int pageNum = startIndex / pageSize + 1;
        PageHelper.startPage(pageNum, pageSize);
        return empMapper.selectAllOrderBy(column, orderDir);
    }

    @Override
    public Long saveBatch(List<Emp> list) {
        SqlSession batchSqlSession = null;
        try {
            long beginTime = System.currentTimeMillis();
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int batchCount = 500;
            for (int index = 0; index < list.size(); index++) {
                Emp emp = list.get(index);
                emp.setUserid(UUID.randomUUID().toString());
                emp.setPassword(PasswordHelper.createPassWord(32));
                batchSqlSession.getMapper(EmpMapper.class).insert(emp);
                if (index != 0 && index % batchCount == 0) {
                    batchSqlSession.commit();
                }
            }
            batchSqlSession.commit();
            long endTime = System.currentTimeMillis();
            logger.info("插入完成， 耗时 " + (endTime - beginTime) + " 毫秒！");
        } finally {
            if (batchSqlSession != null) {
                batchSqlSession.close();
            }
        }
        return 1L;
    }

    @Override
    public Long updateBatch(List<Emp> list) {
        SqlSession batchSqlSession = null;
        try {
            long beginTime = System.currentTimeMillis();
            batchSqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
            int batchCount = 500;
            for (int index = 0; index < list.size(); index++) {
                Emp workCalendar = list.get(index);
                batchSqlSession.getMapper(EmpMapper.class).updateByPrimaryKey(workCalendar);
                if (index != 0 && index % batchCount == 0) {
                    batchSqlSession.commit();
                }
            }
            batchSqlSession.commit();
            long endTime = System.currentTimeMillis();
            logger.info("更新完成， 耗时 " + (endTime - beginTime) + " 毫秒！");
        } finally {
            if (batchSqlSession != null) {
                batchSqlSession.close();
            }
        }
        return 1L;
    }

}
