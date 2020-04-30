<template>
  <div class="page">
    <van-nav-bar title="Web Avalon"></van-nav-bar>
    <van-form validate-first ref="form" style="margin-top: 16px;">
      <van-field v-model="name" name="name" placeholder="请输入你的名字" label="你的名字" :rules="[{ validator: validatorName, message: '支持1-20位中英文和数字' }]"></van-field>
      <div style="margin: 16px;">
        <van-button round block type="info" @click="createRoom">创建房间</van-button>
      </div>
      <div style="margin: 16px;">
        <van-button round block type="info" @click="joinRoom">加入房间</van-button>
      </div>
    </van-form>
    <van-action-sheet v-model="showCreateRoom" title="创建房间">
      <div class="content">
        <van-form validate-first ref="createForm" style="margin-top: 16px;">
          <van-field name="players" label="游戏人数">
            <template #input>
              <van-stepper v-model="players" min="5" max="10"></van-stepper>
            </template>
          </van-field>
          <div style="margin: 16px;">
            <van-button round block type="info" @click="doCreateRoom">创建</van-button>
          </div>
        </van-form>
      </div>
    </van-action-sheet>
    <van-action-sheet v-model="showJoinRoom" title="加入房间">
      <div class="content">
        <van-form validate-first ref="joinForm" style="margin-top: 16px;">
          <van-field v-model="room" name="room" placeholder="请输入房间号" label="房间号" :rules="[{ validator: validatorRoom, message: '房间号为10位数字' }]"></van-field>
          <div style="margin: 16px;">
            <van-button round block type="info" @click="doJoinRoom">加入</van-button>
          </div>
        </van-form>
      </div>
    </van-action-sheet>
  </div>
</template>

<script>
import { Toast } from 'vant'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'

export default {
  name: 'index',
  data () {
    return {
      name: '',
      players: 5,
      room: '',
      showCreateRoom: false,
      showJoinRoom: false
    }
  },
  methods: {
    // 玩家名字校验
    validatorName (val) {
      return new RegExp('^[\u4e00-\u9fa5_a-zA-Z0-9]{1,20}$').test(val)
    },
    // 房间号校验
    validatorRoom (val) {
      return new RegExp('^[0-9]{10}$').test(val)
    },
    // 显示创建房间
    createRoom () {
      this.$refs['form'].validate().then(() => {
        this.showCreateRoom = true
      }).catch(() => {
      })
    },
    // 显示加入房间
    joinRoom () {
      this.$refs['form'].validate().then(() => {
        this.showJoinRoom = true
      }).catch(() => {
      })
    },
    // 创建房间
    doCreateRoom () {
      this.$refs['createForm'].validate().then(() => {
        this.$store.state.wsRoom.send('/app/host/create', {}, JSON.stringify({'players': this.players}))
        this.subscribe()
        Toast.loading({
          message: '请稍后',
          duration: 0,
          forbidClick: true
        })
      }).catch(() => {
      })
    },
    // 加入房间
    doJoinRoom () {
      this.$refs['joinForm'].validate().then(() => {
        this.$store.state.wsRoom.send('/app/host/enter', {}, JSON.stringify({'room': this.room}))
        this.subscribe()
        Toast.loading({
          message: '请稍后',
          duration: 0,
          forbidClick: true
        })
      }).catch(() => {
      })
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
      this.$store.state.wsRoom.subscribe('/topic/roomInfo', msg => {
        // let message = JSON.parse(msg.body)
        Toast.clear()
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
