package com.example.rbac_back.Mapper;

import com.example.rbac_back.POJO.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    List<Role> getAllRoles();
    void addRole(Role role);
    void deleteRole(int role_id);
    void updateRole(Role role);
    String getRoleNameById(int role_id);
    int getRoleIdByName(String role_name);
}
