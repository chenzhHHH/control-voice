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

  <div>
    <van-row justify="center" style="margin: 1rem 0 0 0">
      <van-col span="10" >
        <category-box :icon-src="require('../assets/trophy.png')" active-background-color="#E74C3C"></category-box>
      </van-col>

      <van-col span="10" offset="1">
        <category-box :icon-src="require('../assets/trophy.png')" active-background-color="#AF7AC5"></category-box>
      </van-col>
    </van-row>

    <van-row justify="center" style="margin: 1rem 0 0 0">
      <van-col span="10" >
        <category-box :icon-src="require('../assets/trophy.png')" active-background-color="#85C1E9"></category-box>
      </van-col>

      <van-col span="10" offset="1">
        <category-box :icon-src="require('../assets/trophy.png')" active-background-color="#F5B041"></category-box>
      </van-col>
    </van-row>

    <van-row justify="center" style="margin: 1rem 0 0 0">
      <van-col span="10" >
        <category-box :icon-src="require('../assets/trophy.png')" active-background-color="#C0392B"></category-box>
      </van-col>

      <van-col span="10" offset="1">
        <category-box :icon-src="require('../assets/trophy.png')" active-background-color="#229954"></category-box>
      </van-col>
    </van-row>
  </div>
</template>

<script>
  import CategoryBox from '@/components/CategoryBox.vue'
  import { Swipe, SwipeItem, Search } from 'vant';

  export default {
    components: {
      CategoryBox,
      [Swipe.name]: Swipe,
      [SwipeItem.name]: SwipeItem,
      [Search.name]: Search
    },
    data() {
      return {
        
      }
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