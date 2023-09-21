<template>
    <div class="disposal_card_container_box">
      <div class="title_box">处置卡</div>
  
      <div class="disposal_card_box">
        
      </div>
    </div>
  </template>
  <script>
  export default {};
  </script>
  <style lang="scss" scoped>
  .disposal_card_container_box {
    height: 50vh;
    .title_box {
      font-size: 2rem;
    }
    .disposal_card_box {
      height: calc(100% - 2rem - 2rem);
      padding: 1rem;
      overflow-y: scroll;
      box-sizing: border-box;
      background-color: #3b4253;
  
      &::-webkit-scrollbar {
        width: 0; /* Safari,Chrome 隐藏滚动条 */
        height: 0; /* Safari,Chrome 隐藏滚动条 */
        display: none; /* 移动端、pad 上Safari，Chrome，隐藏滚动条 */
      }
    }
  }
  </style>