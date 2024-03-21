import router from './router'
import { getAccessToken } from '@/utils/auth'
import store from './store'
import { getInfo} from '@/api/login.js'

router.beforeEach((to, from, next) => {
  if (getAccessToken()) { // 如果有token
    if (to.path === '/login') {
      next({ path: '/' }) // 如果是去登录页，重定向到首页
    } else {
        // console.log(store.getters.routes.length)
        if(store.getters.hasGeneratedRoutes == false){
          console.log("begin generate routes!")
            let menus =JSON.parse(localStorage.getItem("userInfo")).user_menu
            // console.log(menus)
            store.dispatch('GenerateRoutes', menus).then(
                accessroutes=>{
                    // console.log("new routes:")
                    // console.log(accessroutes)
                    router.addRoutes(accessroutes)
                    next({ ...to, replace: true }) // hack方法 确保addRoutes已完成, 否则第一次进需要点击两次
                }
            )
            store.commit('SET_HAS_ROUTES', true)
        }
        else{
          next() // 正常跳转
        }
    }
  } else { // 没有token
    if (to.path === '/login') {
      next() // 如果是去登录页，正常跳转
    } else {
      next('/login') // 否则重定向到登录页
    }
  }
})

router.afterEach(() => {
  // 这里可以添加一些在路由跳转后需要执行的代码
})
