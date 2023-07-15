<template>
  <div class="dashboard-container">
    <div class="dashboard-text" align="center">e学网后台管理系统</div>
    <div><img class="img" src="http://t13.baidu.com/it/u=4158768846,154751069&fm=224&app=112&f=JPEG?w=500&h=500" width="600px"  height="400px" alt="图片找不到"/></div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  cover: '/static/01.png',
  computed: {
    ...mapGetters([
      'name',
      'roles'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.img{
  display: block;
  margin: 0 auto;
}
</style>
