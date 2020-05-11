import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
  account: '', // 当前登录的账号信息
  wsRoom: null, // websocket-room
  wsRoomTime: null // websocket-room定时器
}

const getters = {
  getAccount (state) {
    if (state.account === '') {
      state.account = localStorage.getItem('account')
    }
    return state.account
  }
}

const mutations = {
  setAccount (state, account) {
    state.account = account
    localStorage.setItem('account', account)
  }
}

const actions = {
  asynSetAccount (context, account) {
    context.commit('setAccount', account)
  },
  asynClean (context) {
    context.commit('setAccount', '')
    localStorage.setItem('account', '')
  }
}

export default new Vuex.Store({
  state,
  getters,
  mutations,
  actions
})
