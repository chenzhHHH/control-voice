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
        url: `../course/course`
      });
    }
  }
};
if (!Array) {
  const _easycom_uni_search_bar2 = common_vendor.resolveComponent("uni-search-bar");
  const _easycom_uni_icons2 = common_vendor.resolveComponent("uni-icons");
  const _easycom_uni_col2 = common_vendor.resolveComponent("uni-col");
  const _easycom_uni_row2 = common_vendor.resolveComponent("uni-row");
  const _easycom_uni_section2 = common_vendor.resolveComponent("uni-section");
  (_easycom_uni_search_bar2 + _easycom_uni_icons2 + _easycom_uni_col2 + _easycom_uni_row2 + _easycom_uni_section2)();
}
const _easycom_uni_search_bar = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-search-bar/uni-search-bar.js";
const _easycom_uni_icons = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-icons/uni-icons.js";
const _easycom_uni_col = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-col/uni-col.js";
const _easycom_uni_row = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-row/uni-row.js";
const _easycom_uni_section = () => "../../node-modules/@dcloudio/uni-ui/lib/uni-section/uni-section.js";
if (!Math) {
  (_easycom_uni_search_bar + _easycom_uni_icons + _easycom_uni_col + _easycom_uni_row + _easycom_uni_section)();
}
function _sfc_render(_ctx, _cache, $props, $setup, $data, $options) {
  return {
    a: common_vendor.p({
      placeholder: "\u8BF7\u8F93\u5165\u641C\u7D22\u5173\u952E\u8BCD",
      radius: 30
    }),
    b: common_vendor.p({
      type: "image",
      size: 30
    }),
    c: common_vendor.o(($event) => $options.goTo()),
    d: common_vendor.p({
      span: 8
    }),
    e: common_vendor.p({
      type: "image",
      size: 30
    }),
    f: common_vendor.p({
      span: 8
    }),
    g: common_vendor.p({
      type: "image",
      size: 30
    }),
    h: common_vendor.p({
      span: 8
    }),
    i: common_vendor.p({
      type: "image",
      size: 30
    }),
    j: common_vendor.p({
      span: 8
    }),
    k: common_vendor.p({
      type: "image",
      size: 30
    }),
    l: common_vendor.p({
      span: 8
    }),
    m: common_vendor.p({
      type: "image",
      size: 30
    }),
    n: common_vendor.p({
      span: 8
    }),
    o: common_vendor.p({
      type: "plus",
      size: 30
    }),
    p: common_vendor.p({
      span: 8
    }),
    q: common_vendor.p({
      title: "\u8F7B\u677E\u7EC3",
      type: "line"
    })
  };
}
var MiniProgramPage = /* @__PURE__ */ common_vendor._export_sfc(_sfc_main, [["render", _sfc_render], ["__file", "/Users/chenzhihang/Desktop/project/voice-uni/voice-uni/pages/index/index.vue"]]);
wx.createPage(MiniProgramPage);
