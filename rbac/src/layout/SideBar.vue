<template>
  <div>
    <el-menu
        class="el-menu-vertical-demo menu"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        :default-active="this.$route.path"
    >
      <el-menu-item index="/" @click="$router.push('/')">
        <i class="el-icon-house"></i>
        <span slot="title">首页</span>
      </el-menu-item>
      <el-submenu
          v-for="(route, index) in routes"
          :key="index"
          :index="route.path"
          v-if="route.children && route.children.length > 0"
      >
        <template #title>
          <i :class="route.icon || 'el-icon-menu'"></i>
          <span>{{ route.name }}</span>
        </template>

        <el-menu-item
            v-for="(childRoute, childIndex) in route.children"
            :key="childIndex"
            :index="route.path +'/'+ childRoute.path"
            @click="navigateTo(route.path + '/' + childRoute.path)"
        >
          <i :class="childRoute.icon || 'el-icon-document'"></i>
          <span slot="title">{{ childRoute.name }}</span>
        </el-menu-item>
      </el-submenu>
<!--      <el-menu-item index="/logout" @click="logout">-->
<!--        <i class="el-icon-setting"></i>-->
<!--        <span slot="title">退出</span>-->
<!--      </el-menu-item>-->
    </el-menu>
  </div>
</template>

<script>
import {mapGetters} from "vuex";
import {removeRefreshToken, removeToken} from "@/utils/auth";

export default {
  computed: {
    ...mapGetters(["routes"]),
  },
  methods: {
    navigateTo(path) {
      console.log(path)
      this.$router.push(path);
    },
    logout() {
      this.$confirm('确定注销并退出系统吗？', '提示').then(() => {
        removeToken()
        removeRefreshToken()
        this.$store.commit('SET_ROUTES',[])
        this.$store.commit('SET_HAS_ROUTES', false)
        this.$router.push({ path: '/login' }) // 重定向到登录页面
      }).catch(() => {});
    },
  },
}

</script>