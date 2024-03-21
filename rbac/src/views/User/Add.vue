<template>
  <el-card>
    <div slot="header">
      <span style="font-weight: bold">添加用户</span>
    </div>
    <el-form ref="form" :model="user" :rules="rules" label-width="100px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="user.username"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="user_realname">
        <el-input v-model="user.user_realname"></el-input>
      </el-form-item>
      <el-form-item label="性别" prop="user_sex">
        <el-radio-group v-model="user.user_sex">
          <el-radio label="男">男</el-radio>
          <el-radio label="女">女</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="用户角色" prop="user_role">
        <el-checkbox-group v-model = "roleSelectGroup">
          <el-checkbox v-for = "item in roleGroup" :key="item.role_id" :label="item.role_name"
                       :disabled="item.role_name === '普通用户'" border>
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="手机号" prop="user_phone_number">
        <el-input v-model="user.user_phone_number"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="user_pwd">
        <el-input v-model="user.user_pwd"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { addUser, addUser2 } from '@/api/userAdmin';
import { AllRoles } from '@/api/roleAdmin';
export default {
name: "AddUser",
data() {
    return {
    user: {
        username: "",
        user_realname: "",
        user_sex: "",
        user_role:[],
        user_phone_number: "",
        user_pwd: '123'  //默认密码
    },
    roleSelectGroup:["普通用户"],
    roleGroup:[],
    rules: {
        username: [
        { required: true, message: "请输入用户名", trigger: "blur" },
        ],
        user_realname: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        user_sex: [{ required: true, message: "请选择性别", trigger: "change" }],
        user_phone_number: [{ required: true, message: "请输入手机号", trigger: "blur" }],
    },
    };
},
mounted(){
    AllRoles().then((res)=>{
        this.roleGroup = res.data;
    }).catch(error=>{
        console.log(error)
    })
},
methods: {
    submitForm() {
    this.$refs.form.validate(valid => {
        if (valid) {
            this.user.user_role = this.roleSelectGroup;
            addUser2(this.user).then(res =>{
                this.$message.success("添加成功！")
                this.resetForm()
            }).catch(()=>{
                this.$message.error("添加失败！")
            })
        } else {
        console.log("form validation failed");
        }
    });
    },
    resetForm() {
    this.$refs.form.resetFields();
    },
},
};
</script>

<style>
.el-form-item__label {
  padding-right: 15px;
  font-weight: bold;
  text-align: right;
}
.el-checkbox.is-bordered+.el-checkbox.is-bordered {
  margin-left: 0;
}
.el-checkbox.is-bordered {
  margin-bottom: 10px;
}
</style>
  