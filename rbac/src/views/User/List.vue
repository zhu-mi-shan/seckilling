<template>
  <div>
    <el-card>
      <el-table :data="users">
        <el-table-column fixed type="index" align="center" min-width="10%"></el-table-column>
        <el-table-column prop="username" label="用户名" align="center" min-width="20%"></el-table-column>
        <el-table-column prop="user_realname" label="姓名" align="center" min-width="20%"></el-table-column>
        <el-table-column prop="user_sex" label="性别" align="center" min-width="15%"></el-table-column>
        <el-table-column prop="user_phone_number" label="手机号" align="center" min-width="25%"></el-table-column>
        <el-table-column
            prop="user_role"
            label="用户角色"
            header-align="center"
            align="left"
            min-width="45%">
          <template #default="scope">
            <el-tag
                v-for="item in scope.row.user_role"
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
              @click="editUser(scope.$index, scope.row)">编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="deleteUser(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <el-dialog
        title="编辑用户"
        :visible.sync="dialogVisible"
        width="50%">
      <span>用户信息</span>
      <el-form ref="form" :model="dialogUser" label-width="100px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="dialogUser.username" :disabled = true></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="user_realname">
          <el-input v-model="dialogUser.user_realname"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="user_sex">
          <el-radio-group v-model="dialogUser.user_sex">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="用户角色" prop="user_role">
          <el-checkbox-group v-model = "dialogUser.user_role" :min = 1>
            <el-checkbox v-for = "item in roleGroup" :key="item.role_id" :label="item.role_name" border
                         :disabled="item.role_name === '普通用户'">
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="手机号" prop="user_phone_number">
          <el-input v-model="dialogUser.user_phone_number"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="user_pwd">
          <el-input v-model="dialogUser.user_pwd"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="updateUser()">确 定</el-button>
  </span>
    </el-dialog>
  </div>

</template>

<script>
import { Allusers, Allusers2, UpdateUser, DeleteUser, DeleteUser2 } from '@/api/userAdmin';
import { AllRoles } from '@/api/roleAdmin';
import { getUserPermission } from '@/api/PermissionAdmin'
export default {
    name: "UserList",
    data() {
        return {
            users: [],
            dialogUser:[],
            dialogVisible: false,
            roleGroup:[],
            editPermission: false
        };
    },
    mounted(){
        Allusers2().then((res)=>{
            this.users = res.data
        }).catch(error=>{
            console.log(error)
        })
        AllRoles().then(res=>{
            this.roleGroup = res.data
        }).catch(error=>{
            console.log(error)
        })
        if(getUserPermission().includes("编辑用户")){
            this.editPermission = true
        }
    },
    methods:{
        editUser(index, row){
            this.dialogVisible = true
            this.dialogUser = row
        },
        updateUser(){
            const user = this.dialogUser
            UpdateUser(user).then((res)=>{
                if(res.code == 200){
                    this.$message.success("编辑成功")
                    this.dialogVisible = false
                    Allusers().then((res)=>{
                        this.users = res.data
                    })
                }
            })
        },
        deleteUser(index, row){
            const userid = row.userid
            const username = row.username
            if(username === 'www'){
                window.alert("不可删除该角色！")
            }
            else{
                DeleteUser2(userid).then((res)=>{
                    if(res.code == 200){
                        this.$message.success("删除成功")
                        Allusers().then((res)=>{
                            this.users = res.data
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
  