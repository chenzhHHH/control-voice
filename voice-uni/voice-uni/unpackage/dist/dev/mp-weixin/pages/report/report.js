"use strict";
var common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  data() {
    return {};
  },
  onLoad() {
  },
  onUnload() {
    common_vendor.index.switchTab({
      url: "../index/index"
    });
  },
  methods: {
    nextLearn() {
      common_vendor.index.navigateTo({
        url: `../course/course`
      });
    }
  }
};
if (!Array) {
  const _easycom_uni_col2 = common_vendor.resolveComponent("uni-col");
  const _easycom_uni_rate2 = common_vendor.resolveComponent("uni-rate");
  const _easycom_uni_row2 = common_vendor.resolveComponent("uni-row");
  (_easycom_uni_col2 + _easycom_uni_rate2 + _easycom_uni_row2)();
}
const _easycom_uni_col = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-col/uni-col.js";
const _easycom_uni_rate = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-rate/uni-rate.js";
const _easycom_uni_row = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-row/uni-row.js";
if (!Math) {
  (_easycom_uni_col + _easycom_uni_rate + _easycom_uni_row)();
}
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.p({
      span: 6
    }),
    b: common_vendor.p({
      disabled: true,
      disabledColor: "#ffc247",
      value: 4.3
    }),
    c: common_vendor.p({
      span: 16
    }),
    d: common_vendor.p({
      span: 6
    }),
    e: common_vendor.p({
      disabled: true,
      disabledColor: "#ffc247",
      value: 4
    }),
    f: common_vendor.p({
      span: 16
    })
  };
}
var MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "/Users/chenzhihang/Desktop/project/voice-uni/voice-uni/pages/report/report.vue"]]);
wx.createPage(MiniProgramPage);
