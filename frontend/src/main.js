// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import router from './router'
import store from './store'
import Vant from 'vant'
import 'vant/lib/index.css'
import App from './App'
import 'babel-polyfill'
Vue.use(Vant)
Vue.config.productionTip = false

// 路由拦截
router.beforeEach((to, from, next) => {
  if (to.path !== '/login') {
    if (store.getters.getAccount !== '' && store.getters.getAccount !== null) {
      next()
    } else { // token为空
      next({ path: '/login' })
    }
  } else {
    next()
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})
