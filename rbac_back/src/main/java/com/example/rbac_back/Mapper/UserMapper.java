package com.example.rbac_back.Mapper;

import com.example.rbac_back.POJO.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    User getUserByName(String username);
    User getUserById(int userid);
    List<User> getAllUsers();
    int addUser(User user);
    void deleteUser(int userid);
    void updateUser(User user);
}
