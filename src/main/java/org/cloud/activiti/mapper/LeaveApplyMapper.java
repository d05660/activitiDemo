package org.cloud.activiti.mapper;

import org.cloud.activiti.entity.LeaveApply;

public interface LeaveApplyMapper {
	void save(LeaveApply apply);

	LeaveApply get(int id);

	void update(LeaveApply app);
}
