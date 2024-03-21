<template>
  <div class="login-container">
    <div class="login-box">
    <!-- <div class="login-logo">
        <a href="#">
        <img src="../assets/logo.png" alt="logo">
        </a>
    </div> -->
    <div class="form">
      <h3>My-tickets</h3>
      <div v-if="isLogin" class="login-form">
        <el-form ref="form" :model="loginForm" :rules="rules" label-position="left" label-width="0">
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" placeholder="请输入用户名" prefix-icon="el-icon-user"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input type="password" v-model="loginForm.password" placeholder="请输入密码" prefix-icon="el-icon-lock"></el-input>
          </el-form-item>
          <el-form-item>
            <el-checkbox class="remember_password" v-model="loginForm.remember">记住密码</el-checkbox>
            <a class="forgot-password">忘记密码？</a>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="do_login" :loading="loading">登录</el-button>
          </el-form-item>
        </el-form>
        <span class="bottom-msg">没有账号？ <a @click="isLogin = false" href="#">点此注册</a></span>
      </div>
      <div v-else class="register-form">
        <el-form ref="register_form"
                 :model="user"
                 :rules="register_rules"
                 label-position="left"
                 label-width="70px"
                 hide-required-asterisk="true">
          <el-form-item label="用户名：" prop="username">
            <el-input v-model="user.username" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="密码：" prop="user_pwd">
            <el-input type="password" v-model="user.user_pwd" placeholder="请输入密码"></el-input>
          </el-form-item>
          <el-form-item label="确认密码：" prop="confirmPassword">
            <el-input type="password" v-model="user.confirmPassword" placeholder="请再次输入密码"></el-input>
          </el-form-item>
          <el-form-item label="姓名：" prop="user_realname">
              <el-input v-model="user.user_realname" placeholder="请输入真实姓名"></el-input>
          </el-form-item>
          <el-form-item label="性别：" prop="user_sex">
              <el-radio-group v-model="user.user_sex">
              <el-radio label="男">男</el-radio>
              <el-radio label="女">女</el-radio>
              </el-radio-group>
          </el-form-item>
          <el-form-item label="手机号：" prop="user_phone_number">
              <el-input v-model="user.user_phone_number" placeholder="请输入手机号"></el-input>
          </el-form-item>
        </el-form>
        <div class="register-bottom-button">
          <el-button class="register-submit-button" type="primary" @click="registerUser" :loading="register_loading">注册</el-button>
          <el-button class="register-reset-button" @click="resetForm">重置</el-button>
        </div>
        <span class="bottom-msg">已有账号？ <a @click="isLogin = true" href="#">点此登录</a></span>
      </div>
    </div>
    </div>
  </div>
</template>
    
<script>
import { login, getInfo} from '@/api/login.js'
import { setToken, setRefreshToken } from '@/utils/auth'
import router from '@/router'
import {register } from '@/api/register.js'
export default {
  mounted(){
      localStorage.clear()
  },
  data() {
    return {
      isLogin: true,
      user: {
        username: "",
        user_pwd: "",
        confirmPassword: "",
        user_realname: "",
        user_sex: "",
        user_phone_number: "",
      },
      register_rules: {
          username: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          ],
          user_realname: [{ required: true, message: "请输入姓名", trigger: "blur" }],
          user_sex: [{ required: true, message: "请选择性别", trigger: "change" }],
          user_phone_number: [{ required: true, message: "请输入手机号", trigger: "blur" },
          { pattern: /^1\d{10}$/, message: '格式不正确', trigger: 'blur' },],
          user_pwd: [{ required: true, message: '请输入密码', trigger: 'blur' }],
          confirmPassword: [
            { required: true, message: '请确认密码', trigger: 'blur' },
            {
              validator: this.validateConfirmPassword,
              trigger: 'blur',
            },
          ],
      },
      loginForm: {
          username: '',
          password: ''
      },
      rules: {
          username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
          password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      loading: false,
      register_loading: false,
    }
  },
  methods: {
      validateConfirmPassword(rule, value, callback) {
        if (value !== this.user.user_pwd) {
          callback(new Error('两次输入密码不一致'));
        } else {
          callback();
        }
      },
      resetForm() {
        this.$refs.register_form.resetFields();
      },
      registerUser(){
          this.$refs.register_form.validate(valid => {
              if (valid) {
                  this.register_loading = true
                  register(this.user.username,this.user.user_pwd, this.user.user_realname, this.user.user_sex,this.user.user_phone_number ).then(res =>{
                      if(res.code === 200){
                          this.$message.success("注册成功！")
                          this.isLogin = true
                      }
                      else
                          this.$message.error(res.msg)
                  }).catch(()=>{
                      this.$message.error("注册失败！")
                  })
                  this.register_loading = false
              }
          })
      },
      do_login() {
        this.$refs.form.validate(valid => {
          if (valid) {
          this.loading = true
              // 登录处理逻辑中增加网络请求
            login(this.loginForm.username,this.loginForm.password).then(res =>{
                  this.loading = false
                  if(res.code === 200){
                      setToken(res.data.accessToken)
                      setRefreshToken(res.data.refreshToken)
                      getInfo().then(res =>{
                          localStorage.setItem("userInfo",JSON.stringify(res.data))
                          this.$router.push({ path: '/' })
                          this.$message.success('登陆成功')
                      })
                  }
                  else
                      this.$message.error(res.msg)
              }).catch(() => {
                // 登录失败，显示错误提示
                this.$message.error('登陆失败')
                this.loading = false
              })
          }
        })
      }
  }
}
</script>

<style lang="scss">
$teal: rgb(0,124,137);
.login-container {
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;

  .login-box {
      width: 500px;
      border-radius: 5px;
      box-shadow: 0px 0px 10px #ccc;

      .login-logo {
        height: 100px;
        display: flex;
        justify-content: center;
        align-items: center;

        img {
            height: 80%;
        }
      }

      .form {
        padding: 30px;
        .login-form {
          .el-button--primary{
            width: 90%;
            background: $teal;
            border-color: $teal;
            &:hover,
            &.active,
            &:focus {
              background: lighten($teal, 7);
              border-color: lighten($teal, 7);
            }
          }
          .login_button{
            width: 30%;
            background: #ff9900;
            &:hover,
            &.active,
            &:focus {
              background: lighten($teal, 7);
              border-color: lighten($teal, 7);
            }
          }
          .remember_password{
            float: left;
            margin-left: 20px;
          }
        }
        .register-form >>> .el-form-item__content {
          margin-left: 0px !important;
        }
        .el-form-item__label {
          padding-right: 0;
          font-weight: bold;
        }
        h3 {
            text-align: center;
            font-size: 24px;
            margin-bottom: 20px;
            padding-bottom: 20px;
        }
        a {
            color: $teal;
            text-decoration: none;
            &:hover,
            &:active,
            &:focus {
            color: lighten($teal, 7);
            }
        }
        .el-checkbox__label{
            color: $teal;

        }
        .el-checkbox__input.is-checked .el-checkbox__inner {
          border-color: $teal; /* 选中状态下的颜色 */
          background-color: $teal; /* 选中状态下的背景色（可选） */
        }
        .forgot-password{
            float: right;
            margin-right: 20px;
        }
        .bottom-msg {
          padding-bottom: 15px;
          display:inline-block;
          font-size:0.6em;
          font-weight:normal;
          float:right;
          vertical-align: middle;
        }
      }
  }

  .register-bottom-button {
    display: flex;
    justify-content: space-between;
    margin-bottom: 22px;
    .register-submit-button {
        width: 47%;
        background: $teal;
        border-color: $teal;
        &:hover,
        &.active,
        &:focus {
            background: lighten($teal, 7);
            border-color: lighten($teal, 7);
        }
    }
    .register-reset-button {
      float: right;
      width: 47%;
    }
  }

}
</style>