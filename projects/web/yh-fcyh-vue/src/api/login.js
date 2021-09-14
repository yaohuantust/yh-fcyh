import request from '@/utils/http'

// 获取验证码
export function getCodeImg() {
    return request({
        url: '/captchaImage',
        method: 'get'
    })
}

// 登录方法
export function login(username, password, code, uuid) {
    const data = {
        username,
        password,
        code,
        uuid
    }
    return request({
        url: '/login',
        method: 'post',
        data: data
    })
}
