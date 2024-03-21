import axios from 'axios';
import { getAccessToken, getRefreshToken, setToken, refreshToken, removeToken, removeRefreshToken } from '@/utils/auth'
import router from '@/router'
// 请求队列
let requestList = []
// 是否正在刷新中
let isRefreshToken = false

//创建axios实例
const service = axios.create({
  baseURL: '/proxy',
  timeout: 15000
});

//添加请求拦截器
service.interceptors.request.use(
  config => {
    // 在请求发送之前对请求数据进行处理
    if (getAccessToken()) {
      config.headers['Authorization'] = 'Bearer ' + getAccessToken() // 携带token
    }
    return config;
  },
  error => {
    // 对请求错误做些什么
    console.log(error);
    return Promise.reject(error);
  }
);

//添加响应拦截器
service.interceptors.response.use(
  response => {    
    return response.data
  },
  async error => {
    const code = error.response.status
    //Token失效无感刷新
    if(code === 401){
      //该请求是第一个Token失效的请求
      if(!isRefreshToken){
        isRefreshToken = true
        //1. 无法获取refreshToken
        if(!getRefreshToken()){
          window.alert("无法获取refreshToken")
          logout()
        }
        //2. 有refreshToken，进行token刷新
        try {
          console.log("token刷新")
          const refreshTokenRes = await refreshToken()
          // 2.1 刷新成功，则回放队列的请求 + 当前请求
          if(refreshTokenRes.code != 401){
            setToken(refreshTokenRes.data)
            requestList.forEach(cb => cb())
            return service(error.config)
          }
          else{
            //2.2 refreshToken已失效
            window.alert("refreshToken已失效,请重新登录")
            logout()
          }
        } catch (e) {
          // 2.3 其他错误
          console.log(error);
          window.alert("无法刷新Token,请重新登录")
          logout()
        } finally {
          requestList = []
          isRefreshToken = false
        }
      }
      //该请求之前Token就已经失效
      else{
        // 添加到队列，等待刷新获取到新的令牌
        return new Promise(resolve => {
          requestList.push(() => {
            error.config.headers['Authorization'] = 'Bearer ' + getAccessToken()
            resolve(service(error.config))
          })
        })
      }
    }
    //其他错误
    else{
      console.log(error);
      return Promise.reject(error);
    }
    
  }
);

function logout(){
  removeToken()
  router.push({ path: '/login' }) // 重定向到登录页面
}

export default service;
