<template>
  <div>
    <el-card>
      <el-table :data="roles">
        <el-table-column fixed type="index" align="center" min-width="10%"></el-table-column>
        <el-table-column prop="role_name" label="角色名" align="center" min-width="20%"></el-table-column>
        <el-table-column prop="role_des" label="角色描述" align="center" min-width="25%"></el-table-column>
        <el-table-column
            prop="role_permission"
            label="角色权限"
            header-align="center"
            align="left"
            min-width="40%">
          <template #default="scope">
            <el-tag
                v-for="item in scope.row.role_permission"
                :key="item"
                style="margin-left: 10px;margin-bottom: 8px"
            >
              {{ item }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column v-if = "editPermission" label="操作" align="center" min-width="20%">
          <template slot-scope="scope">
            <el-button
                size="mini"
                type="primary"
                @click="editRole(scope.$index, scope.row)">编辑</el-button>
            <el-button
                size="mini"
                type="danger"
                @click="deleteRole(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog
      title="编辑角色"
      :visible.sync="dialogVisible"
      width="50%">
      <span>角色信息</span>
          <el-form ref="form" :model="dialogRole" label-width="100px">
          <el-form-item label="角色名" prop="role_name">
              <el-input v-model="dialogRole.role_name"></el-input>
          </el-form-item>
          <el-form-item label="角色描述" prop="role_des">
              <el-input v-model="dialogRole.role_des"></el-input>
          </el-form-item>
          <el-form-item label="角色权限" prop="role_permission">
              <el-checkbox-group v-model = "dialogRole.role_permission">
                  <el-checkbox v-for = "item in permissionGroup" :key="item" :label="item"
                      :disabled="item=='访问主页'" border>
                  </el-checkbox>
              </el-checkbox-group>
          </el-form-item>
          </el-form>
      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateRole()">确 定</el-button>
      </span>
      </el-dialog>
  </div>
</template>

<script>
import { AllRoles, UpdateRole, DeleteRole } from '@/api/roleAdmin';
import { getUserPermission } from '@/api/PermissionAdmin'
export default {
    name: "RoleList",
    data() {
        return {
            roles: [  
            ],
            editPermission: false,
            dialogRole:[
            ],
            dialogVisible: false,
            permissionGroup:['访问主页', '查看用户', '查看角色','查看权限','编辑用户','编辑角色','查看活动','管理活动','活动数据']
        };
    },
    mounted(){
        AllRoles().then((res)=>{
            this.roles = res.data
        }).catch(error=>{
            console.log(error)
        })   
        if(getUserPermission().includes("编辑角色")){
            this.editPermission = true
        }
    },
    methods:{
        editRole(index, row){
            this.dialogVisible = true
            this.dialogRole = row
        },
        updateRole(){
            const role = this.dialogRole
            if(role.role_name === '最高管理员'){
                window.alert("禁止更改最高管理员!")
            }
            else{
                UpdateRole(role).then((res)=>{
                    if(res.code == 200){
                        this.$message.success("编辑成功")
                        this.dialogVisible = false
                        AllRoles().then((res)=>{
                            this.roles = res.data
                        })
                    }
                })
            }
        },
        deleteRole(index, row){
            const role_id = row.role_id
            const role_name = row.role_name
            const default_roles = ['普通用户','VIP用户','管理员','最高管理员']
            if(default_roles.includes(role_name)){
                window.alert("不可删除该角色！")
            }
            else{
                DeleteRole(role_id).then((res)=>{
                    if(res.code == 200){
                        this.$message.success("删除成功")
                        AllRoles().then((res)=>{
                            this.roles = res.data
                        })
                    }
                })
            }
            
        },
    }
};
</script>

<style>
</style>
      