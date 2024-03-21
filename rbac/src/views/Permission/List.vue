<template>
    <el-card>
    <el-table :data="permissions">
      <el-table-column prop="per_name" label="权限名" align="center"></el-table-column>
      <el-table-column prop="per_des" label="权限描述" align="center"></el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import { AllPermissions } from '@/api/PermissionAdmin'
export default {
    name: "PermissionList",
    data() {
        return {
            permissions: [  
            ],
        };
    },
    mounted(){
        AllPermissions().then((res)=>{
            this.permissions = res.data
        }).catch(error=>{
            console.log(error)
        })
    },
};
</script>

<style>
</style>
      