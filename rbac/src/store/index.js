import Vue from 'vue'
import Vuex from 'vuex'

import {routes} from '@/router'
import Layout from '@/layout/index'
Vue.use(Vuex)

//foreach()函数没有返回值，所以用filter进行遍历
function filterRoutes(menus){
    return menus.filter(route =>{
        route.component = Layout
        route.children.forEach(child => {
          child.component = loadView(child.component)
        });
        return true;
    })
}

export const loadView = (view) => { // 路由懒加载
  // console.log(view)
  return () => import(`@/views/${view}`)
    .catch(error => console.error(`Dynamic page loading failed: ${error}`));
}

export default new Vuex.Store({
  state: {
    hasGeneratedRoutes: false,
    routes:[]
  },
  getters: {
    routes:state => state.routes,
    hasGeneratedRoutes:state=>state.hasGeneratedRoutes
  },
  mutations: {
    SET_ROUTES:(state, routes)=>{
      state.routes = routes;
    },
    SET_HAS_ROUTES:(state, flag)=>{
      state.hasGeneratedRoutes = flag
    },
  },
  actions: {
    GenerateRoutes({commit}, menus){
      return new Promise(resolve =>{
        const routes = filterRoutes(menus)
        commit('SET_ROUTES', routes)
        resolve(routes)
      })
    }
  },
  modules: {
  }
})
