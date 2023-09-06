<template>
  <div class="container_box" :style="isSelf ? 'flex-direction: row-reverse' : 'flex-direction: row'">
    <div class="profile_img_box">
      <el-avatar :src="require('@/assets/logo.png')" />
    </div>

    <div :class="isSelf ? 'content_box_self' : 'content_box_not_self'">
        
    </div>
  </div>
</template>

<script>
export default {
  name: "PerChat",
  components: {},
  data() {
    return {};
  },
  props: {
    isSelf: Boolean,
  },
};
</script>

<style lang="scss" scoped>
.container_box {
  width: 100%;
  float: left;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: flex-start;
  padding: 10px 0 10px 0;
  .profile_img_box {
  }
  .content_box_self {
    height: 100px;
    margin: 0 20px 0 20px;
    padding: 5px 5px 5px 5px;
    border-radius: 5px;
    background-color: #ccc;
    position: relative;
    
    &::before {
      content: "";
      border-top: 5px solid transparent;
      border-bottom: 10px solid transparent;
      border-left: 10px solid #ccc;
      position: absolute;
      top: 12px;
      right: -10px;
    }
  }

  .content_box_not_self {
    height: 100px;
    margin: 0 20px 0 20px;
    padding: 5px 5px 5px 5px;
    border-radius: 5px;
    background-color: #ccc;
    position: relative;
    
    &::before {
      content: "";
      border-top: 5px solid transparent;
      border-bottom: 10px solid transparent;
      border-right: 10px solid #ccc;
      position: absolute;
      top: 12px;
      left: -10px;
    }
  }
}
</style>