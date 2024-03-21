<template>
  <el-card>
    <div slot="header">
      <span style="font-weight: bold">添加角色</span>
    </div>
    <el-form ref="form" :model="role" :rules="rules" label-width="100px">
      <el-form-item label="角色名称" prop="role_name">
        <el-input v-model="role.role_name"></el-input>
      </el-form-item>
      <el-form-item label="角色描述" prop="role_des">
        <el-input v-model="role.role_des"></el-input>
      </el-form-item>
      <el-form-item label="角色权限" prop="role_permission">
        <el-checkbox-group v-model = "role.role_permission">
          <el-checkbox v-for = "item in permissionGroup"
                       :key="item.per_id"
                       :label="item.per_name"
                       :disabled="item.per_name=='访问主页'"
                       border>
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>

</template>

<script>
import { addRole} from '@/api/roleAdmin';
import { AllPermissions } from '@/api/PermissionAdmin';
export default {
name: "Addrole",
mounted(){
    AllPermissions().then(res=>{
        this.permissionGroup = res.data
    }).catch(error=>{
        console.log(error)
    })
},
data() {
    return {
    role: {
        role_name: '',
        role_des: '',
        role_permission:['访问主页'],
    },
    permissionGroup:[],
    rules: {
        role_name: [
        { required: true, message: "请输入角色名", trigger: "blur" },
        ],
        role_des: [{ required: true, message: "请输入角色描述", trigger: "blur" }],
    },
    };
},
methods: {
    submitForm() {
    this.$refs.form.validate(valid => {
        if (valid) {
            addRole(this.role).then(res =>{
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
      