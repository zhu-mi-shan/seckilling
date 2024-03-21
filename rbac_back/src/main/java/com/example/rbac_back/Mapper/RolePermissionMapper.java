package com.example.rbac_back.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RolePermissionMapper {
    List<Integer> getRolePermissionsById(int role_id);
    void addRolePermission(int role_id, int per_id);
    void deleteRolePermission(int role_id);
    Boolean roleHasPermission(int role_id, int per_id);
}
