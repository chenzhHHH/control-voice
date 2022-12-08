"use strict";
var common_vendor = require("../../common/vendor.js");
const _sfc_main = {
  data() {
    return {};
  },
  onLoad() {
  },
  methods: {
    goTo() {
      common_vendor.index.navigateTo({
        url: `../learn/learn`
      });
    }
  }
};
if (!Array) {
  const _easycom_uni_col2 = common_vendor.resolveComponent("uni-col");
  const _easycom_uni_row2 = common_vendor.resolveComponent("uni-row");
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  const _easycom_uni_section2 = common_vendor.resolveComponent("uni-section");
  (_easycom_uni_col2 + _easycom_uni_row2 + _easycom_uni_icons2 + _easycom_uni_section2)();
}
const _easycom_uni_col = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-col/uni-col.js";
const _easycom_uni_row = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-row/uni-row.js";
const _easycom_uni_icons = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-icons/uni-icons.js";
const _easycom_uni_section = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-section/uni-section.js";
if (!Math) {
  (_easycom_uni_col + _easycom_uni_row + _easycom_uni_icons + _easycom_uni_section)();
}
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.p({
      span: 10
    }),
    b: common_vendor.p({
      span: 14
    }),
    c: common_vendor.p({
      span: 4
    }),
    d: common_vendor.p({
      span: 17
    }),
    e: common_vendor.p({
      type: "right",
      size: 20
    }),
    f: common_vendor.p({
      span: 3
    }),
    g: common_vendor.o(($event) => $options.goTo("word")),
    h: common_vendor.p({
      span: 4
    }),
    i: common_vendor.p({
      span: 17
    }),
    j: common_vendor.p({
      type: "right",
      size: 20
    }),
    k: common_vendor.p({
      span: 3
    }),
    l: common_vendor.o(($event) => $options.goTo("sentence")),
    m: common_vendor.p({
      title: "\u5B66\u4E60\u4EFB\u52A1",
      type: "line"
    }),
    n: common_vendor.o(($event) => $options.goTo())
  };
}
var MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "/Users/chenzhihang/Desktop/project/voice-uni/voice-uni/pages/course/course.vue"]]);
wx.createPage(MiniProgramPage);
