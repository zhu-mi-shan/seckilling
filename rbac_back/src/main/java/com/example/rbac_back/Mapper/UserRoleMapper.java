package com.example.rbac_back.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserRoleMapper {
    List<Integer> getUserRoles(int userid);
    void addUserRole(int userid, int role_id);
    void deleteUserRole(int userid);
    void deleteRoleUser(int role_id);
}
