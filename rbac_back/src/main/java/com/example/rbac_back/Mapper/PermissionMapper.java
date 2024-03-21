package com.example.rbac_back.Mapper;


import com.example.rbac_back.POJO.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface PermissionMapper {
    public List<Permission> getAllPermissions();
    public String getPermissionNameById(int per_id);
    public int getPermissionIdByName(String per_name);
    public Permission getPermissionByName(String per_name);
    public void addPermission (Permission permission);
    public void deletePermission(int per_id);
}
