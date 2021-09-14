import request from '@/utils/http'

// 查询角色列表
export function getMsg(query) {
    return request({
        url: '/fcyh/getMsg',
        method: 'get',
        params: query
    })
}

// 查询角色列表
export function queryFgw(query) {
    return request({
        url: '/fcyh/getFgwByProjectName',
        method: 'get',
        params: query
    })
}
