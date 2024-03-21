<template>
  <div class="success-page">
    <el-main>
      <div class="content">
        <h1>欢迎登录My-tickets抢票系统!</h1>
      </div>
    </el-main>
  </div>
</template>

<script>
import { getInfo } from "@/api/login";

export default {
  name: "SuccessPage",
  data() {
    return {
      user:{},
    };
  },
  mounted() {
    this.user =JSON.parse(localStorage.getItem("userInfo")) 
    console.log(this.user)
  },
};
</script>

<style scoped>
.success-page {
  height: 100%;
}

.content {
  margin: 20px;
  padding: 20px;
  background-color: #ffffff;
  border-radius: 4px;
  min-height: 500px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
</style>