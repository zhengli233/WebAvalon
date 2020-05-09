<template>
  <div class="page">
    <van-nav-bar title="Web Avalon" @click-right="logout">
      <template #right>
        <van-icon name="cross" color="#fff" size="18"></van-icon>
      </template>
    </van-nav-bar>
    <div v-if="!onGoing">
      <van-notice-bar :text="'欢迎，' + name" color="#1989fa" background="#ecf9ff" left-icon="info-o"></van-notice-bar>
      <van-grid square :column-num="3">
        <van-grid-item
          v-for="(item, index) in roomList"
          :key="index"
          :icon="item.onGoing ? 'fire-o' : 'underway-o'"
          :text="index + 1 + '号桌 ' + (item.onGoing ? item.playerNames.length + '/' + item.playerNames.length : item.playerNames.length + '/10')"
          @click="joinGame(item.room, item.playerNames)">
        </van-grid-item>
      </van-grid>
    </div>
    <div v-else>
      已经加入
    </div>
  </div>
</template>

<script>
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import { Dialog } from 'vant'

export default {
  name: 'index',
  data () {
    return {
      name: this.$store.getters.getAccount,
      onGoing: false,
      roomList: [
        {room: '0', onGoing: true, playerNames: ['a', 'b', 'c', 'd']},
        {room: '1', onGoing: true, playerNames: ['aa', 'bb', 'cc', 'dd', 'ee']},
        {room: '2', onGoing: false, playerNames: ['aaa', 'bbb']},
        {room: '3', onGoing: false, playerNames: ['ccc']},
        {room: '4', onGoing: false, playerNames: []},
        {room: '5', onGoing: false, playerNames: []},
        {room: '6', onGoing: false, playerNames: []},
        {room: '7', onGoing: false, playerNames: []},
        {room: '8', onGoing: false, playerNames: []},
        {room: '9', onGoing: false, playerNames: []}
      ]
    }
  },
  methods: {
    logout () {
      this.$store.dispatch('asynClean')
      this.$router.push({ path: '/login' })
    },
    joinGame (room, players) {
      if (players.indexOf(this.name) === -1) {
        this.$store.state.wsRoom.send('/app/host/enterRoom', {}, JSON.stringify({'room': room, 'players': [this.name]}))
      } else {
        Dialog.confirm({
          message: '该房间中已有与您的名字相同玩家，请重新创建名字或加入其它房间'
        }).then(() => {
          this.$router.push({ path: '/login' })
        }).catch(() => {
        })
      }
    },
    // 连接
    connection () {
      this.disconnect()
      let socket = new SockJS('http://www.houtiao.club')
      this.$store.state.wsRoom = Stomp.over(socket)
      let headers = {}
      this.$store.state.wsRoom.connect(
        headers,
        frame => {
          if (this.connectLock) {
            console.log('锁线程锁已还原')
            window.clearInterval(this.$store.state.wsRoomTime)
            this.connectLock = false // 还原锁
          }
          this.subscribe()
        },
        err => {
          // 连接发生错误时的处理函数
          console.log('连接失败', err)
          if (!this.connectLock) {
            this.reconnect()
          }
        }
      )
    },
    // 订阅
    subscribe () {
      this.$store.state.wsRoom.subscribe('/app/host/getAllRoomsInfo', msg => {
        let message = JSON.parse(msg.body)
        this.roomList = message
      })
    },
    // 断开连接
    disconnect () {
      if (this.$store.state.wsRoom !== null) {
        this.$store.state.wsRoom.disconnect()
        this.$store.state.wsRoom = null
      }
    },
    // 重连
    reconnect () {
      if (this.connectLock) return
      this.connectLock = true
      // 断开重连机制,尝试发送消息,捕获异常发生时重连
      this.$store.state.wsRoomTime = window.setInterval(() => {
        this.connection()
      }, 10000)
    }
  },
  mounted () {
    this.connection()
  },
  beforeDestroy: function () {
    this.disconnect()
    window.clearInterval(this.$store.state.wsRoomTime)
    this.$store.state.wsRoomTime = null
  }
}
</script>

<style scoped>

</style>
