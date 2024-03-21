import request from '@/utils/request'

//获取Roles
export function AllRoles(){
    return request({
        url: '/role/getAllRoles',
        method: 'get'
    })
}

//增加角色
export function addRole(role){
    const data = role
    return request({
        url: '/role/addRole',
        method: 'post',
        data: data
        })
    }

//更改Role
export function UpdateRole(role){
    return request({
        url: '/role/updateRole',
        method: 'put',
        data: role
    })
}

//删除Role
export function DeleteRole(role_id){
    return request({
        url: '/role/deleteRole',
        method: 'delete',
        params:{
            role_id: role_id
        }
    })
}