<!-- <template>
  <div class="home">
    <img alt="Vue logo" src="../assets/logo.png">
    <HelloWorld msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue'

export default {
  name: 'HomeView',
  components: {
    HelloWorld
  }
}
</script> -->

<template>
  <van-search v-model="value" shape="round" background="#4fc08d" placeholder="请输入搜索关键词" />

  <div style="background-color: #4fc08d; margin-top: -1px;">
    <div style="text-align: center; margin: auto; width:300px; padding: 10px 0 20px 0;">
      <van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
        <van-swipe-item>1</van-swipe-item>
        <van-swipe-item>2</van-swipe-item>
        <van-swipe-item>3</van-swipe-item>
        <van-swipe-item>4</van-swipe-item>
      </van-swipe>
    </div>
  </div>

  <van-row style="height:50px; line-height: 50px; margin: 10px 0 0 0; border-top: 2px solid #c0c0c0; border-bottom: 1.5px solid #c0c0c0;">
      <van-col span="4" offset="1" style="font-size:10px;font-weight: bold;">轻松练</van-col>

      <van-col span="4" offset="15" style="font-size:10px;">更多</van-col>
  </van-row>

  <van-row>
    <van-col span="8" style="text-align: center; border: 0.5px solid #c0c0c0">
      <div style="margin: 20px 0 10px 0" @click='goTo()'>
        <van-image width="50px" height="50px" it="contain" :src="require('../assets/learnCenter.png')"/>
        <div>
          <span style="font-size: 13px">雷达基础理论</span>
        </div>
        <div>
          <span style="color: #c0c0c0">自定义添加</span>
        </div>
      </div>
    </van-col>

    <van-col span="8" style="text-align: center; border: 0.5px solid #c0c0c0">
      <div style="margin: 20px 0 10px 0">
        <van-image width="50px" height="50px" it="contain" :src="require('../assets/learnCenter.png')"/>
        <div>
          <span style="font-size: 13px">机场管制</span>
        </div>
        <div>
          <span style="color: #c0c0c0">自定义添加</span>
        </div>
      </div>
    </van-col>

    <van-col span="8" style="text-align: center; border: 0.5px solid #c0c0c0">
      <div style="margin: 20px 0 10px 0">
        <van-image width="50px" height="50px" it="contain" :src="require('../assets/learnCenter.png')"/>
        <div>
          <span style="font-size: 13px">进近管制</span>
        </div>
        <div>
          <span style="color: #c0c0c0">自定义添加</span>
        </div>
      </div>
    </van-col>
  </van-row>

  <van-row>
    <van-col span="8" style="text-align: center; border: 0.5px solid #c0c0c0">
      <div style="margin: 20px 0 10px 0">
        <van-image width="50px" height="50px" it="contain" :src="require('../assets/learnCenter.png')"/>
        <div>
          <span style="font-size: 13px">区域管制</span>
        </div>
        <div>
          <span style="color: #c0c0c0">自定义添加</span>
        </div>
      </div>
    </van-col>
    
    <van-col span="8" style="text-align: center; border: 0.5px solid #c0c0c0">
      <div style="margin: 20px 0 10px 0">
        <van-image width="50px" height="50px" it="contain" :src="require('../assets/learnCenter.png')"/>
        <div>
          <span style="font-size: 13px">特情</span>
        </div>
        <div>
          <span style="color: #c0c0c0">自定义添加</span>
        </div>
      </div>
    </van-col>

    <van-col span="8" style="text-align: center; border: 0.5px solid #c0c0c0">
      <div style="margin: 20px 0 10px 0">
        <van-image width="50px" height="50px" it="contain" :src="require('../assets/learnCenter.png')"/>
        <div>
          <span style="font-size: 13px">管制英语</span>
        </div>
        <div>
          <span style="color: #c0c0c0">自定义添加</span>
        </div>
      </div>
    </van-col>
  </van-row>

  <van-row>
    <van-col span="8" style="text-align: center; border: 0.5px solid #c0c0c0">
      <div style="margin: 20px 0 10px 0">
        <van-image width="50px" height="50px" it="contain" :src="require('../assets/add.png')"/>
        <div>
          <span style="font-size: 13px">雷达基础理论</span>
        </div>
        <div>
          <span style="color: #c0c0c0">自定义添加</span>
        </div>
      </div>
    </van-col>
  </van-row>

</template>

<script>
  import { Swipe, SwipeItem, Search } from 'vant';

  export default {
    components: {
      [Swipe.name]: Swipe,
      [SwipeItem.name]: SwipeItem,
      [Search.name]: Search
    },
    methods: {
      goTo() {
        this.$router.replace('/course');
      }
    }
  };
</script>

<style>
  .my-swipe .van-swipe-item {
    color: #fff;
    font-size: 20px;
    line-height: 500px;
    text-align: center;
    background-color: #39a9ed;
  }
</style>