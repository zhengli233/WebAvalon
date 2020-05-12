import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
  account: '', // 当前登录的账号信息
  status: '',
  room: '',
  wsRoom: null, // websocket-room
  wsRoomTime: null // websocket-room定时器
}

const getters = {
  getAccount (state) {
    if (state.account === '') {
      state.account = localStorage.getItem('account')
    }
    return state.account
  },
  getStatus (state) {
    if (state.status === '') {
      state.status = localStorage.getItem('status')
    }
    return state.status
  },
  getRoom (state) {
    if (state.room === '') {
      state.room = localStorage.getItem('room')
    }
    return state.room
  }
}

const mutations = {
  setAccount (state, account) {
    state.account = account
    localStorage.setItem('account', account)
  },
  setStatus (state, account) {
    state.status = account
    localStorage.setItem('status', account)
  },
  setRoom (state, account) {
    state.room = account
    localStorage.setItem('room', account)
  }
}

const actions = {
  asynSetAccount (context, account) {
    context.commit('setAccount', account)
  },
  asynSetStatus (context, account) {
    context.commit('setStatus', account)
  },
  asynSetRoom (context, account) {
    context.commit('setRoom', account)
  },
  asynClean (context) {
    context.commit('setAccount', '')
    localStorage.setItem('account', '')
    context.commit('setStatus', '')
    localStorage.setItem('status', '')
    context.commit('setRoom', '')
    localStorage.setItem('room', '')
  }
}

export default new Vuex.Store({
  state,
  getters,
  mutations,
  actions
})
