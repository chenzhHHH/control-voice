<template>
  <div class="special_word_container_box">
    <div class="title_box">通信失效相关特情词</div>

    <div class="special_word_box">
      <div class="per_special_word_box">
        <el-row>
          <el-col :span="12"><div class="per_special_word_cn_text">aeronautical communication</div></el-col>
          <el-col :span="12"><div class="per_special_word_en_text">航空通讯</div></el-col>
        </el-row>
      </div>

      <div class="per_special_word_box">
        <el-row>
          <el-col :span="12"><div class="per_special_word_cn_text">airfield control communication</div></el-col>
          <el-col :span="12"> <div class="per_special_word_en_text">机场指挥通信</div></el-col>
        </el-row>
      </div>

      <div class="per_special_word_box">
        <el-row>
          <el-col :span="12"><div class="per_special_word_cn_text">air-ground conmmunication</div></el-col>
          <el-col :span="12"> <div class="per_special_word_en_text">空地通信</div></el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
export default {};
</script>
<style lang="scss" scoped>
.special_word_container_box {
  height: 50vh;
  .title_box {
    font-size: 2rem;
  }
  .special_word_box {
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
    .per_special_word_box {
      font-size: 1.2rem;
      padding-bottom: 0.2rem;
      .per_special_word_cn_text {
      }
      .per_special_word_en_text {
      }
    }
  }
}
</style>