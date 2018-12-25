package org.cloud.activiti.mapper;

import org.cloud.activiti.entity.VacationRequest;

public interface VacationMapper {

	void save(VacationRequest vacationRequest);

	VacationRequest get(int id);

	void update(VacationRequest vacationRequest);
}
