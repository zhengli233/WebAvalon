import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

export default new Router({
  routes: [
    {
      'path': '/',
      'name': 'Home',
      'component': resolve => require(['@/pages/index'], resolve)
    }, {
      'path': '/inGame',
      'name': 'InGame',
      'component': resolve => require(['@/pages/inGame'], resolve)
    }
  ]
})
