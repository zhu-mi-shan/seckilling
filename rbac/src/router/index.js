import Vue from 'vue'
import VueRouter from 'vue-router'

import Layout from '@/layout'
Vue.use(VueRouter)

const constantRoutes = [
  {
    path: '/',
    component: Layout, // 修改为 Layout 组件
    // name: "首页",
    children: [
      {
        path: '',
        name: '首页',
        component: () => import( '../views/HomeView.vue'),
      },
      {
        path: '/profile',
        component: () => import('../views/Profile.vue'),
        name: "个人中心"
      },
    ]
  },
  // {
  //   path: '/rbac',
  //   component: Layout,
  //   children: [{
  //       path: 'user/list',
  //       component:() => import( '../views/User/List.vue')
  //     },
  //     {
  //       path: 'user/add',
  //       component:() => import( '../views/User/Add.vue')
  //     },
  //     {
  //       path: 'role/list',
  //       component:() => import( '../views/Role/List.vue')
  //     },
  //     {
  //       path: 'role/add',
  //       component:() => import( '../views/Role/Add.vue')
  //     },
  //     {
  //       path: 'permission/list',
  //       component:() => import( '../views/Permission/List.vue')
  //     }
  //   ]
  // },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/about',
    name: 'about',
    component: () => import('../views/AboutView.vue')
  },
  {
    path: '/debug',
    component: () => import('../views/debug.vue')
  }
]

// 防止连续点击多次路由报错
let routerPush = VueRouter.prototype.push;
VueRouter.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: constantRoutes
})

export default router
