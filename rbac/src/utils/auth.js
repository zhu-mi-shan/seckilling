import request from '@/utils/request'

const AccessTokenKey = 'ACCESS_TOKEN'
const RefreshTokenKey = 'REFRESH_TOKEN'

// 获取 Token
export function getAccessToken() {
  return localStorage.getItem(AccessTokenKey)
}

// 设置 Token
export function setToken(token) {
  localStorage.setItem(AccessTokenKey, token)
}

// 删除 Token
export function removeToken() {
    localStorage.removeItem(AccessTokenKey)
}

//RefreshToken
// 获取 Token
export function getRefreshToken() {
  return localStorage.getItem(RefreshTokenKey)
}

// 设置 Token
export function setRefreshToken(token) {
  localStorage.setItem(RefreshTokenKey, token)
}

// 删除 Token
export function removeRefreshToken() {
    localStorage.removeItem(RefreshTokenKey)
}

export function refreshToken(){
  const refreshToken = getRefreshToken()
  console.log(refreshToken)
  return request({
    url: '/user/refreshToken',
    method: "post",
    data:{
      refreshToken: refreshToken
    }
  })
}