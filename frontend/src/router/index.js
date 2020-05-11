import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      'path': '/login',
      'name': 'Login',
      'component': resolve => require(['@/pages/login'], resolve)
    }, {
      'path': '/',
      'name': 'Home',
      'component': resolve => require(['@/pages/index'], resolve)
    }
  ]
})
