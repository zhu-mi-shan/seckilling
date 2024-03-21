import request from '@/utils/request'

// 用户登录
export function register(username, user_pwd, user_realname, user_sex, user_phone_number){
  const data = {
    username,
    user_pwd,
    user_realname,
    user_sex,
    user_phone_number
  }
  return request({
    url: '/user/register',
    method: 'post',
    data: data
  })
}