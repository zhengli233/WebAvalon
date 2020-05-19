<template>
  <div class="page">
    <div v-if="$store.getters.getStatus === '0'">
      <van-nav-bar title="Web Avalon" @click-right="logout">
        <template #right>
          <van-icon name="cross" color="#fff" size="18"></van-icon>
        </template>
      </van-nav-bar>
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
    <div v-else-if="$store.getters.getStatus === '1'">
      <van-nav-bar :title="(parseInt($store.getters.getRoom) + 1) + '号桌'">
        <template #right>
        </template>
      </van-nav-bar>
      <van-notice-bar :text="'欢迎，' + name" color="#1989fa" background="#ecf9ff" left-icon="info-o"></van-notice-bar>
      <van-grid class="personInfo" :column-num="2" gutter="5">
        <van-grid-item :class="name === item ? 'self' : ''" v-for="(item, index) in playList" :key="index">
          <van-tag mark :type="name === item ? 'primary' : 'default'">{{index + 1}}</van-tag>
          <div>{{item}}</div>
        </van-grid-item>
      </van-grid>
      <div style="margin: 16px;">
        <van-button round block type="info" @click="start()" :disabled="playList.length < 5">开始</van-button>
      </div>
      <div style="margin: 16px;">
        <van-button round block type="info" @click="leave()" plain>离开</van-button>
      </div>
    </div>
  </div>
</template>

<script>
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import { Toast, Dialog } from 'vant'

export default {
  name: 'index',
  data () {
    return {
      name: this.$store.getters.getAccount,
      subSingleRoom: '',
      subRule: '',
      roomList: [
        // {room: '0', onGoing: false, playerNames: []},
        // {room: '1', onGoing: false, playerNames: []},
        // {room: '2', onGoing: false, playerNames: []},
        // {room: '3', onGoing: false, playerNames: []},
        // {room: '4', onGoing: false, playerNames: []},
        // {room: '5', onGoing: false, playerNames: []},
        // {room: '6', onGoing: false, playerNames: []},
        // {room: '7', onGoing: false, playerNames: []},
        // {room: '8', onGoing: false, playerNames: []},
        // {room: '9', onGoing: false, playerNames: []}
      ],
      playList: [
        // 'q', '1234567890', '一二三四五六七八九十', 'Tornorroyv', 'zhengli233'
      ]
    }
  },
  methods: {
    logout () {
      this.$store.dispatch('asynClean')
      this.$router.push({ path: '/login' })
      // this.$store.state.wsRoom.send('/app/host/clearAllRooms', {}, JSON.stringify({}))
    },
    joinGame (room, players) {
      if (players.indexOf(this.name) === -1) {
        this.$store.dispatch('asynSetRoom', room)
        this.subscribeSingleRoom()
        this.$store.state.wsRoom.send('/app/host/enterRoom', {}, JSON.stringify({'room': room, 'playerNames': [this.name]}))
      } else {
        Dialog.confirm({
          message: '该房间中已有与您的名字相同玩家，请重新创建名字或加入其它房间',
          confirmButtonText: '好的，去创建'
        }).then(() => {
          this.$router.push({ path: '/login' })
        }).catch(() => {
        })
      }
    },
    start () {
    },
    leave () {
      this.$store.dispatch('asynSetStatus', '0')
      this.$store.state.wsRoom.send('/app/host/leaveRoom/' + this.$store.getters.getRoom, {}, JSON.stringify({'name': this.name}))
      this.$store.dispatch('asynSetRoom', '')
      this.$store.state.wsRoom.unsubscribe(this.subSingleRoom)
      this.subSingleRoom = ''
      this.$store.state.wsRoom.unsubscribe(this.subRule)
      this.subRule = ''
    },
    // 连接
    connection () {
      Toast.loading({
        message: '加载中',
        forbidClick: true,
        duration: 0
      })
      this.disconnect()
      let socket = new SockJS('https://api.houtiao.club/avalon/web_avalon')
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
          Toast.clear()
          this.subscribe()
          this.subscribeEnterStatus()
          if (this.$store.getters.getRoom !== '') {
            this.subscribeSingleRoom()
          }
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
      this.$store.state.wsRoom.subscribe('/topic/allRoomsInfo', msg => {
        let message = JSON.parse(msg.body)
        this.roomList = message
        if (this.$store.getters.getRoom !== '' && this.playList.length === 0) {
          this.playList = message.find(item => item.room === this.$store.getters.getRoom).playerNames
        }
      })
      this.$store.state.wsRoom.send('/app/host/getAllRooms', {}, JSON.stringify({}))
    },
    subscribeEnterStatus () {
      this.$store.state.wsRoom.subscribe('/user/topic/enterRoomStatus', msg => {
        let message = JSON.parse(msg.body)
        if (message) {
          this.$store.dispatch('asynSetStatus', '1')
        } else {
          this.$store.dispatch('asynSetRoom', '')
          this.$store.state.wsRoom.unsubscribe(this.subSingleRoom)
          this.subSingleRoom = ''
          this.$store.state.wsRoom.unsubscribe(this.subRule)
          this.subRule = ''
          Toast.fail({
            message: '加入失败'
          })
        }
      })
    },
    subscribeSingleRoom () {
      this.$store.state.wsRoom.subscribe('/topic/roomInfo/' + this.$store.getters.getRoom, msg => {
        this.subSingleRoom = msg.headers.subscription
        let message = JSON.parse(msg.body)
        this.playList = message.playerNames
        // this.$store.state.wsRoom.send('/app/rule/getRule/' + this.$store.getters.getRoom, {}, JSON.stringify({}))
      })
      this.$store.state.wsRoom.subscribe('/topic/rule/' + this.$store.getters.getRoom, msg => {
        this.subRule = msg.headers.subscription
        // let message = JSON.parse(msg.body)
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
