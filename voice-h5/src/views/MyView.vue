<template>
  <div class="container">
    <div class="user_box">
      <div class="user_img_background_box"></div>

      <div class="user_img_box">
        <van-icon name="user-circle-o" size="4rem" />
      </div>

      <div class="user_info_box">
        <div class="user_info_text">{{ cnName }}</div>
      </div>
    </div>

    <div class="option_box">
      <van-row>
        <van-col span="6">
          <div class="option_item_box" @click="chooseChecker()">
            <div class="option_item_icon">
              <van-icon name="friends-o" color="#17202A" size="2.3rem" />
            </div>

            <div class="option_item_text">检查员</div>
          </div>
        </van-col>

        <van-col span="6">
          <div class="option_item_box" @click="checkRecord()">
            <div class="option_item_icon">
              <van-icon name="sign" color="#17202A" size="2.3rem" />
            </div>

            <div class="option_item_text">审核</div>
          </div>
        </van-col>
      </van-row>
    </div>

    <div class="logout_box">
      <van-button class="logout_bt" round type="danger" @click="logout()">退出登录</van-button>
    </div>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      cnName: "",
    };
  },
  created() {
    let that = this;

    that.cnName = localStorage.getItem("cnName");
  },
  methods: {
    chooseChecker() {
      this.$router.replace("/chooseChecker");
    },
    checkRecord() {
      this.$router.replace("/checkUser");
    },
    logout() {
      localStorage.clear();
      this.$router.replace("/home");
    },
  },
};
</script>

<style scoped lang="less">
.container {
  padding: 2rem;

  .user_box {
    height: 6rem;
    margin-top: 2rem;
    background-color: #ebedef;
    border-radius: 1rem;
    position: relative;
    display: flex;
    flex-direction: row;

    .user_img_background_box {
      width: 5rem;
      height: 5rem;
      border-radius: 3rem;
      background-color: white;
      position: absolute;
      left: 50%;
      top: 0;
      transform: translate(-50%, -50%);
    }

    .user_img_box {
      position: absolute;
      left: 50%;
      top: 0;
      transform: translate(-50%, -50%);
    }

    .user_info_box {
      display: flex;
      width: 100%;
      flex-direction: column;
      justify-content: center;
      margin-top: 2rem;

      .user_info_text {
        display: flex;
        justify-content: center;
        align-items: center;
        font-size: 1.2rem;
        font-weight: 800;
      }
    }
  }

  .option_box {
    margin-top: 1rem;
    background-color: #ebedef;
    border-radius: 1rem;

    .option_item_box {
      padding: 1rem;

      .option_item_icon {
        text-align: center;
      }

      .option_item_text {
        text-align: center;
        color: #566573;
      }
    }
  }

  .logout_box {
    text-align: center;
    font-size: 3rem;
    color: #dc7633;

    .logout_bt {
      width: 10rem;
      font-size: 1rem;
    }
  }
}
</style>