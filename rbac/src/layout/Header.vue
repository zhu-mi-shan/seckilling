<template>
  <div class="header">
<!--    <div class="navbar">-->
      <Breadcrumb class="breadcrumb"></Breadcrumb>
      <div class="right-menu">
        <el-dropdown>
          <div class="user-name"> {{ username }} </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click.native="routerProfile">个人中心</el-dropdown-item>
              <el-dropdown-item @click.native="routerHome">首页</el-dropdown-item>
              <el-dropdown-item divided @click.native="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
<!--    </div>-->
  </div>
</template>

<script>
import {mapGetters} from "vuex";
import {removeRefreshToken, removeToken} from "@/utils/auth";

export default {
  data() {
    return {
      username:"",
    };
  },
  mounted() {
    this.username = JSON.parse(localStorage.getItem("userInfo")).username
  },
  computed: {
    ...mapGetters(["routes"]),
  },
  methods: {
    routerProfile() {
      this.$router.push('/profile')
    },
    routerHome () {
      this.$router.push('/')
    },
    logout() {
      this.$confirm('确定注销并退出系统吗？', '提示').then(() => {
        removeToken()
        removeRefreshToken()
        this.$store.commit('SET_ROUTES',[])
        this.$store.commit('SET_HAS_ROUTES', false)
        this.$router.push({ path: '/login' }) // 重定向到登录页面
        this.$message.info('退出登录成功')
      }).catch(() => {});
    },
  }
}
</script>

<style lang="scss" scoped>
.header {
  height: 50px;
  position: fixed;
  top: 0;
  right: 0;
  width: calc(100% - 200px);
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);
}
.breadcrumb {
  float: left;
  font-size: 14px;
  line-height: 50px;
  margin-left: 20px;
}


.right-menu {
  float: right;
  margin-right: 40px;
  .user-name{
    float: right;
    cursor: pointer;
    padding: 0 20px;
    line-height: 50px;
    font-size: medium;
  }
  .user-name:hover {
    background: #f6f6f6;
  }
  //.dropdown {
  //
  //}
}
.el-dropdown-menu {
  //right: 20px;
}
</style>
<script setup>
import Breadcrumb from "@/layout/Breadcrumb.vue";
</script>