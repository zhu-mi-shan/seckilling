<template>
  <div>
    <div>debug</div>
    <el-button @click="fun">click</el-button>
  </div>
  
</template>

<script>
import { getInfo } from '@/api/login';
export default {
    methods:{
        fun(){
            getInfo().then(
                res=>{
                    let menus = res.data.user_menu
                    this.$store.dispatch('GenerateRoutes', menus);
                }
            )
        }
    }
}
</script>

<style>

</style>