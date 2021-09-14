import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from "@/views/Login";

Vue.use(VueRouter)

// const routes = [
//   {
//     path: '/',
//     name: 'HouseProperty',
//     component: HouseProperty
//   }
// ]

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/FangChanYaoHao',
    name: 'FangChanYaoHao',
    component: () => import(/* webpackChunkName: "about" */ '../views/fcyh/FangChanYaoHao.vue')
  },
  {
    path: '/FgwMsgByProjectName',
    name: 'FgwMsgByProjectName',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/fcyh/FgwMsgByProjectName.vue')
  }
]

const router = new VueRouter({
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({ y: 0 }),
  routes
})

export default router

