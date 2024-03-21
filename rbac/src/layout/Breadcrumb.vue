<template>
  <el-breadcrumb separator-icon="ArrowRight">
    <el-breadcrumb-item
        v-for="item in list"
        :key="item.path"
        :to="item.path">
      {{item.name}}
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script>
// import {computed, onMounted, reactive, toRefs, watch} from "vue";
// import {useRoute} from 'vue-router';
//
//
// const route = useRoute();
//
// const list = computed(()=>{
//   return route.matched
// })

import {mapGetters} from "vuex";
export default {
  computed: {
    list() {
      console.log(this.$route.matched)
      return this.$route.matched;
    }
  }
}


</script>

<style scoped>

</style>