import request from '@/utils/request'

//获取Users()
export function Allusers(){
    return request({
      url: '/user/Allusers',
      method: 'get'
    })
  }
//获取Users
export function Allusers2(){
  return request.get('/user/Allusers')
}
//添加新用户
export function addUser(user){
    const data = user
    return request({
      url: '/user/addUser',
      method: 'post',
      data: data
    })
  }
//添加新用户
export function addUser2(user){
  return request.post('/user/addUser', user)
}

//更改用户
export function UpdateUser(user){
  return request({
    url: '/user/updateUser',
    method: 'put',
    data: user
  })
}

//删除用户(传AxiosRequestConfig)
export function DeleteUser(userid){
  return request({
    url: '/user/deleteUser',
    method: 'delete',
    params:{
      userid: userid
    }
  })
}
//删除用户(直接调用delete)
export function DeleteUser2(userid){
  return request.delete('/user/deleteUser', 
  {
    params:{
      userid: userid
    }
  })
}
