import axios from 'axios'
import {Notification, MessageBox, Message} from 'element-ui'
import store from '@/store'
import {getToken} from '@/utils/auth'
import errorCode from '@/utils/errorCode'

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'

const service = axios.create({
    baseURL: '/api',
    timeout: 5000
})

service.interceptors.request.use(config =>{
    // TODO 如果token存在，每次请求header中携带token

    // get请求映射params参数
    if (config.method === "get" && config.params) {
        let url = config.url + '?';
        for (const propName of Object.keys(config.params)) {
            const value = config.params[propName];
            var part = encodeURIComponent(propName) + "=";
            if (value !== null && typeof(value) !== "undefined") {
                if (typeof value === 'object') {
                    for (const key of Object.keys(value)) {
                        let params = propName + '[' + key + ']';
                        var subPart = encodeURIComponent(params) + "=";
                        url += subPart + encodeURIComponent(value[key]) + "&";
                    }
                } else {
                    url += part + encodeURIComponent(value) + "&";
                }
            }
        }
        url = url.slice(0, -1);
        config.params = {};
        config.url = url;
    }
    return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

service.interceptors.response.use(res =>{
    // 未设置状态码则默认成功状态
    const code = res.data.code || 200;
    // 获取错误信息
    const msg = errorCode[code] || res.data.msg || errorCode['default']
    if (code === 401) {
        MessageBox.confirm('登录状态已过期，您可以继续留在该页面，或者重新登录', '系统提示', {
            confirmButtonText: '重新登录',
            cancelButtonText: '取消',
            type: 'warning'
        }).then(()=>{
            // TODO 退出重新登录

        })
    }else if (code === 500) {
        Message({
            message:msg,
            type: 'error'
        })
        return Promise.reject(new Error(msg))
    }else if (code !== 200) {
        Notification.error({
            title: msg
        })
        return Promise.reject('error')
    }else {
        return  res.data
    }
},error => {
    console.log('error'+ error)
    let { message } = error;
    if (message === 'Network Error') {
        message = "后端接口异常";
    }else if (message.includes('timeout')){
        message = "系统接口请求超时";
    }else if (message.includes('Request failed with status code')) {
        message = "系统接口" + message.substr(message.length - 3) + "异常";
    }
    Message({
        message: message,
        type: 'error',
        duration: 5 * 1000
    })
    return Promise.reject(error)
})

export default service
