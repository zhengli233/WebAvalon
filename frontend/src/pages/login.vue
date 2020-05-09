<template>
  <div class="page">
    <van-nav-bar title="Web Avalon"></van-nav-bar>
    <van-form validate-first ref="form" style="margin-top: 16px;">
      <van-field v-model="name" name="name" placeholder="请输入你的名字" label="你的名字" :rules="[{ validator: validatorName, message: '支持1-20位中英文和数字' }]"></van-field>
      <div style="margin: 16px;">
        <van-button round block type="info" @click="startGame()">开始游戏</van-button>
      </div>
    </van-form>
  </div>
</template>

<script>
export default {
  name: 'login',
  data () {
    return {
      name: ''
    }
  },
  methods: {
    // 玩家名字校验
    validatorName (val) {
      return new RegExp('^[\u4e00-\u9fa5_a-zA-Z0-9]{1,20}$').test(val)
    },
    // 显示创建房间
    startGame () {
      this.$refs['form'].validate().then(() => {
        this.$store.dispatch('asynSetAccount', this.name)
        this.$router.push({ path: '/' })
      }).catch(() => {
      })
    }
  },
  mounted () {
  }
}
</script>

<style scoped>

</style>
