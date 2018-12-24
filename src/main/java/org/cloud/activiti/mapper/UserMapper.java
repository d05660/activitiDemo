package org.cloud.activiti.mapper;

import java.util.List;

import org.cloud.activiti.entity.User;

public interface UserMapper {
    List<User> getUsers();

    User getUserById(int id);

    void deleteUser(int uid);

    void deleteUserRole(int uid);

    void addUser(User user);

    void updateUser(User user);

    int getUidByUserName(String userName);
}
