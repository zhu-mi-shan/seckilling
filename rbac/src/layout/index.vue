    <template>
      <div class="app-wrapper">
        <SideBar class="sidebar-container"/>
        <div class="main-container">
          <Header />
          <!--      <el-main>-->
          <el-main class="app-main">
            <router-view/>
          </el-main>
        </div>

    <!--    <div class="main-content">-->
    <!--      <router-view/>-->
    <!--    </div>-->
      </div>
    </template>

<script>
import { removeToken, removeRefreshToken } from '@/utils/auth'
import { getUserPermission } from '@/api/PermissionAdmin';
import { mapGetters } from 'vuex';
import SideBar from "@/layout/SideBar.vue";
import Header from "@/layout/Header.vue";
export default {
  components: {Header, SideBar},
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
};
</script>

<style lang="scss" scoped>
.app-wrapper {
  position: relative;
  height: 100vh;
  width: 100%;
}
.sidebar-container {
  width: 200px;
  height: 100vh;
  position: fixed;
  background: #545c64;
}
.main-container {
  margin-left: 200px;
  min-height: 100%;
  position: relative;
}
.app-main {
  padding-top: 84px;
  min-height: calc(100vh - 84px);

}
</style>
  