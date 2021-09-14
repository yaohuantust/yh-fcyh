import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import Cookies from 'js-cookie'

// 导入element-ui
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

// 导入全局样式
import './assets/css/global.css'

Vue.config.productionTip = false
Vue.use(ElementUI, {
    size: Cookies.get('size') || 'medium' // set element-ui default size
})


new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
