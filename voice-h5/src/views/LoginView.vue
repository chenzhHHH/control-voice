<template>
  <div class="container">
    <van-form @submit="onSubmit">
      <van-cell-group inset>
        <van-field v-model="username" name="username" label="用户名" placeholder="用户名" :rules="[{ required: true, message: '请填写用户名' }]" />
        <van-field v-model="phone" name="phone" label="手机号" placeholder="手机号" :rules="[{ required: true, message: '请填写手机号' }]" />
      </van-cell-group>

      <div style="margin: 2rem 1.5rem 0 1.5rem">
        <van-button round block type="primary" native-type="submit"> 提交 </van-button>
      </div>
    </van-form>
  </div>
</template>
  
  <script>
export default {
  components: {},
  data() {
    return {
      username: "",
      phone: "",
    };
  },
  methods: {
    onSubmit() {
      let that = this;

      let formData = {
        username: that.username,
        phone: that.phone,
      };

      let config = {
        headers: {
          "Content-Type": "application/json;charset=utf-8",
        },
      };

      that.$http.post("/user/login", formData, config).then((response) => {
        if (response.data.code === '2000') {
          let data = response.data.data;

          localStorage.setItem("token", data.token);
          localStorage.setItem("userId", data.id);

          that.$notify({ type: "success", message: "登陆成功" });

          this.$router.replace("/home");
        } else {
          that.$notify({ type: "warning", message: response.data.msg });
        }
      });
    },
  },
};
</script>
  
  <style scoped lang="less">
.container {
  width: 20rem;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -70%);
}
</style>