<template>
  <el-row type="flex" class="profile-wrapper" :gutter="20">
    <el-col :span="12" :xs="12" :sm="12" :md="10" :lg="8" :xl="6">
      <el-card>
        <div slot="header">
          <span>个人信息</span>
        </div>
        <div class="user-info">
          <el-row type="flex" class="row-item">
            <el-col :span="8">
              <i class="el-icon-user" /> 用户名称
            </el-col>
            <el-col :span="15" style="text-align: right">
              {{ user.username }}
            </el-col>
          </el-row>
          <el-row type="flex" class="row-item">
            <el-col :span="8">
              <i class="el-icon-s-custom" /> 真实姓名
            </el-col>
            <el-col :span="15" style="text-align: right">
              {{ user.user_realname }}
            </el-col>
          </el-row>
          <el-row type="flex" class="row-item">
            <el-col :span="8">
              <i v-if="user.user_sex === '男'" class="el-icon-male" />
              <i v-else class="el-icon-female"/>
              性别
            </el-col>
            <el-col :span="15" style="text-align: right">
              {{ user.user_sex }}
            </el-col>
          </el-row>
          <el-row type="flex" class="row-item">
            <el-col :span="8">
              <i class="el-icon-mobile-phone" /> 手机号
            </el-col>
            <el-col :span="15" style="text-align: right">
              {{ user.user_phone_number }}
            </el-col>
          </el-row>
        </div>
      </el-card>
    </el-col>
    <el-col :span="12" :xs="12" :sm="12" :md="14" :lg="16" :xl="18">
      <el-card class="profile-operation">
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane label="基本信息" name="first">
            <el-form ref="message_form"
                     :model="user"
                     :rules="message_rules"
                     label-position="left"
                     label-width="70px"
                     hide-required-asterisk="true">
              <el-form-item label="用户名称" prop="username">
                <el-input v-model="user.username" placeholder="请输入用户名"></el-input>
              </el-form-item>
              <el-form-item label="姓名" prop="user_realname">
                <el-input v-model="user.user_realname" placeholder="请输入真实姓名"></el-input>
              </el-form-item>
              <el-form-item label="性别" prop="user_sex">
                <el-radio-group v-model="user.user_sex">
                  <el-radio label="男">男</el-radio>
                  <el-radio label="女">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-form>
            <el-button type="primary">保存</el-button>
            <el-divider/>
            <el-row>
              <el-col :span="24" class="role-title">角色</el-col>
              <el-tag
                  v-for="item in user.user_role"
                  :key="item"
                  style="margin-right: 10px">
                {{ item }}
              </el-tag>
            </el-row>
            <el-divider/>
            <el-row>
              <el-col :span="24" class="role-title">权限</el-col>
              <el-tag
                  v-for="item in user.user_permission"
                  :key="item"
                  style="margin-right: 10px">
                {{ item }}
              </el-tag>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="修改手机号" name="second">
            <el-form ref="phone_form"
                     :model="user"
                     :rules="phone_rules"
                     hide-required-asterisk="true">
              <div class="phone-form-label">新手机号</div>
              <el-form-item prop="phone">
                <el-input placeholder="请输入手机号"></el-input>
              </el-form-item>
              <div class="phone-form-label">验证码</div>
              <el-form-item prop="code">
                <el-row type="flex">
                  <el-input style="padding-right: 40px" placeholder="请输入验证码"></el-input>
                  <el-button type="primary">获取验证码</el-button>
                </el-row>
              </el-form-item>
            </el-form>
            <el-button type="primary">保存</el-button>
          </el-tab-pane>
          <el-tab-pane label="修改密码" name="third">
            <el-form ref="message_form"
                     :model="user"
                     :rules="password_rules"
                     hide-required-asterisk="true">
              <el-form-item label="旧密码" prop="old_password">
                <el-input placeholder="请输入旧密码"></el-input>
              </el-form-item>
              <el-form-item label="新密码" prop="new_password">
                <el-input placeholder="请输入新密码"></el-input>
              </el-form-item>
              <el-form-item label="确认密码" prop="check_password">
                <el-input placeholder="请确认密码"></el-input>
              </el-form-item>
            </el-form>
            <el-button type="primary">保存</el-button>
          </el-tab-pane>
        </el-tabs>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
export default {
  data() {
    return {
      activeName: 'first',
      user: {},
      message_rules: {
        username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        user_realname: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        user_sex: [{ required: true, message: "请选择性别", trigger: "change" }],
      },
      phone_rules: {
        phone: [{required: true, message: '请输入手机号', trigger: 'blur'}],
        code: [{required: true, message: '请输入验证码', trigger: 'blur'}]
      },
      password_rules: {
        old_password: [{required: true, message: '请输入旧密码', trigger: 'blur'}],
        new_password: [{required: true, message: '请输入新密码', trigger: 'blur'}],
        check_password: [{required: true, message: '请确认密码', trigger: 'blur'}],
      }
    };
  },
  mounted() {
    this.user = JSON.parse(localStorage.getItem("userInfo"))
  },
}
</script>


<style>
.profile-wrapper {
  padding: 10px 15px;
}
.el-card {
  box-shadow: none;
  margin: 8px;
}

.user-info {
  margin: 30px 0;
}

.row-item {
  border-bottom: 1px solid #dfe4ed;
  padding: 15px 0;
  font-size: 15px;
  font-family: serif;
}
.profile-operation {

}
.el-form-item__label {
  padding-right: 0;
  font-weight: bold;
  text-align: right;
}
.role-title, .permission-title {
  font-weight: bold;
  padding-bottom: 20px;
}
.phone-form-label {
  font-weight: bold;
  font-size: 14px;
  color: #606266;
  //line-height: 14px;
  margin-bottom: 14px;
}
</style>