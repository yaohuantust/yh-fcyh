let proxyObj = {}

proxyObj['/api']={
    // websocket
    ws:false,
    // 目标地址
    target: 'http://localhost:8081',
    // 发送请求host会被设置target
    changeOrigin: true,

    // 重写请求地址
    pathRewrite:{
        '^/api':'/'
    }
}

module.exports = {
    devServer:{
        host:'localhost',
        port:8080,
        proxy:proxyObj
    }
}
