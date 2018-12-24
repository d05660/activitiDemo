package org.cloud.activiti.mapper;

import org.cloud.activiti.entity.User;

public interface LoginMapper {
    User getPwdByName(String name);
}
