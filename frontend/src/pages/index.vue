<template>
  <div class="page">
    <!--------------------所有房间界面-------------------->
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
    <!--------------------所有房间界面-------------------->
    <!--------------------当前房间，未开始游戏界面-------------------->
    <div v-else-if="$store.getters.getStatus === '1'">
      <van-nav-bar :title="(parseInt($store.getters.getRoom) + 1) + '号桌'">
        <template #right>
        </template>
      </van-nav-bar>
      <van-notice-bar :text="'欢迎，' + name" color="#1989fa" background="#ecf9ff" left-icon="info-o"></van-notice-bar>
      <van-divider>本局玩家</van-divider>
      <van-grid class="personInfo" :column-num="2" gutter="5">
        <van-grid-item :class="name === item ? 'self' : ''" v-for="(item, index) in playList" :key="index">
          <van-tag mark :type="name === item ? 'primary' : 'default'">{{index + 1}}</van-tag>
          <div>{{item}}</div>
        </van-grid-item>
      </van-grid>
      <div v-if="roleList.length > 0">
        <van-divider>本局角色</van-divider>
        <div class="roleList">
          <van-tag size="medium" v-for="(item, index) in roleList" :key="index" class="roleItem">{{item}}</van-tag>
        </div>
      </div>
      <div style="margin: 16px;">
        <van-button round block type="info" @click="start()" :disabled="playList.length < 5">开始</van-button>
      </div>
      <div style="margin: 16px;">
        <van-button round block type="info" @click="leave()" plain>离开</van-button>
      </div>
    </div>
    <!--------------------当前房间，未开始游戏界面-------------------->
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
      name: this.$store.getters.getAccount, // 用户名
      subSingleRoom: '', // 当前房间信息的订阅id，用于退订时识别
      subRule: '', // 当前房间规则的订阅id，用于退订时识别
      roomList: [], // 房间列表
      playList: [], // 当前房间内玩家列表
      roleList: [], // 当前房间内角色列表
      subStart: '',
      subRole: '',
      subCandidates: '',
      subVote: '',
      subResult: ''
    }
  },
  methods: {
    // 用户登出
    logout () {
      this.$store.dispatch('asynClean')
      this.$router.push({ path: '/login' })
      // this.$store.state.wsRoom.send('/app/host/clearAllRooms', {}, JSON.stringify({})) // 清空所有房间玩家信息
    },
    // 加入房间
    joinGame (room, players) {
      if (players.indexOf(this.name) === -1) {
        this.$store.dispatch('asynSetRoom', room)
        this.subscribeSingleRoom()
        this.subscribeSingleGame()
        this.$store.state.wsRoom.send('/app/host/enterRoom', {}, JSON.stringify({'room': room, 'playerNames': [this.name]})) // 加入房间
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
    // 开始游戏
    start () {
      this.$store.state.wsRoom.send('/app/rule/setRoles/' + this.$store.getters.getRoom, {}, JSON.stringify({})) // 分配角色
    },
    // 离开房间
    leave () {
      this.$store.dispatch('asynSetStatus', '0')
      this.$store.state.wsRoom.send('/app/host/leaveRoom/' + this.$store.getters.getRoom, {}, JSON.stringify({'name': this.name})) // 离开房间
      this.$store.dispatch('asynSetRoom', '')
      this.$store.state.wsRoom.unsubscribe(this.subSingleRoom)
      this.subSingleRoom = ''
      this.$store.state.wsRoom.unsubscribe(this.subRule)
      this.subRule = ''

      this.$store.state.wsRoom.unsubscribe(this.subStart)
      this.subStart = ''
      this.$store.state.wsRoom.unsubscribe(this.subRole)
      this.subRole = ''
      this.$store.state.wsRoom.unsubscribe(this.subCandidates)
      this.subCandidates = ''
      this.$store.state.wsRoom.unsubscribe(this.subVote)
      this.subVote = ''
      this.$store.state.wsRoom.unsubscribe(this.subResult)
      this.subResult = ''
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
            this.subscribeSingleGame()
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
      this.$store.state.wsRoom.subscribe('/topic/allRoomsInfo', msg => { // 所有房间信息
        let message = JSON.parse(msg.body)
        this.roomList = message
        if (this.$store.getters.getRoom !== '' && this.playList.length === 0) {
          this.playList = message.find(item => item.room === this.$store.getters.getRoom).playerNames
          this.$store.state.wsRoom.send('/app/rule/getRule/' + this.$store.getters.getRoom, {}, JSON.stringify({})) // 房间中有哪些角色
        }
      })
      this.$store.state.wsRoom.send('/app/host/getAllRooms', {}, JSON.stringify({})) // 获取所有房间信息
    },
    subscribeEnterStatus () {
      this.$store.state.wsRoom.subscribe('/user/topic/enterRoomStatus', msg => { // 进入状态：是否已经加入了房间，用于关闭页面后重连
        let message = JSON.parse(msg.body)
        if (message) {
          this.$store.dispatch('asynSetStatus', '1')
        } else {
          this.$store.dispatch('asynSetRoom', '')
          this.$store.state.wsRoom.unsubscribe(this.subSingleRoom)
          this.subSingleRoom = ''
          this.$store.state.wsRoom.unsubscribe(this.subRule)
          this.subRule = ''

          this.$store.state.wsRoom.unsubscribe(this.subStart)
          this.subStart = ''
          this.$store.state.wsRoom.unsubscribe(this.subRole)
          this.subRole = ''
          this.$store.state.wsRoom.unsubscribe(this.subCandidates)
          this.subCandidates = ''
          this.$store.state.wsRoom.unsubscribe(this.subVote)
          this.subVote = ''
          this.$store.state.wsRoom.unsubscribe(this.subResult)
          this.subResult = ''
          Toast.fail({
            message: '加入失败'
          })
        }
      })
    },
    subscribeSingleRoom () {
      this.$store.state.wsRoom.subscribe('/topic/roomInfo/' + this.$store.getters.getRoom, msg => { // 单个房间信息
        this.subSingleRoom = msg.headers.subscription
        let message = JSON.parse(msg.body)
        this.playList = message.playerNames
        // this.$store.state.wsRoom.send('/app/rule/getRule/' + this.$store.getters.getRoom, {}, JSON.stringify({}))
      })
      this.$store.state.wsRoom.subscribe('/topic/rule/' + this.$store.getters.getRoom, msg => {
        this.subRule = msg.headers.subscription
        let message = JSON.parse(msg.body)
        if (message !== null) {
          this.roleList = message.roles
        }
      })
    },
    subscribeSingleGame () {
      this.$store.state.wsRoom.subscribe('/topic/setRoles/' + this.$store.getters.getRoom, msg => { // 是否已经分配角色
        this.subStart = msg.headers.subscription
        let message = JSON.parse(msg.body)
        if (message) {
          this.$store.state.wsRoom.send('/app/rule/getPlayerInfo/' + this.$store.getters.getRoom + '/' + this.name, {}, JSON.stringify({})) // 获取自己的身份
        }
      })
      this.$store.state.wsRoom.subscribe('/user/topic/playerInfo', msg => { // 自己和其他能被自己获知身份的玩家的身份
        this.subRole = msg.headers.subscription
      })
      this.$store.state.wsRoom.subscribe('/topic/candidateList/' + this.$store.getters.getRoom, msg => { // 选择做任务玩家
        this.subCandidates = msg.headers.subscription
      })
      this.$store.state.wsRoom.subscribe('/topic/missionEnableVoteResult/' + this.$store.getters.getRoom, msg => { // 做任务玩家人选投票
        this.subVote = msg.headers.subscription
      })
      this.$store.state.wsRoom.subscribe('/topic/missionSuccessVoteResult/' + this.$store.getters.getRoom, msg => { // 任务成败投票
        this.subResult = msg.headers.subscription
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
    // 禁用浏览器的返回
    history.pushState(null, null, document.URL)
    window.addEventListener('popstate', function () {
      history.pushState(null, null, document.URL)
    })
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
