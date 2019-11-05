<template>
  <div class="detail">
    <!-- 导航栏 -->
    <detail-nav-bar></detail-nav-bar>
    <scroll class="content" ref="scroll">
      <detail-swiper :top-images="topImages"/>
      <detail-base-info :goods="goodsInfo"/>
      <detail-shop-info :shop="shopInfo"/>
      <detail-image-info :detail-info="detailInfo" @detailImageLoad="detailImageLoad"/>
      <detail-params-info :param-info="itemParams"/>
      <detail-comment-info :comment-info="commentInfo"/>
      <goods-list :goods="recommends"/></goods-list>
    </scroll>
  </div>
</template>
<script>
import DetailNavBar from "./childcomponents/DetailNavBar";
import DetailSwiper from "./childcomponents/DetailSwiper";
import DetailBaseInfo from "./childcomponents/DetailBaseInfo";
import DetailShopInfo from './childcomponents/DetailShopInfo'
import DetailImageInfo from './childcomponents/DetailImageInfo'
import DetailParamsInfo from './childcomponents/DetailParamsInfo'
import DetailCommentInfo from './childcomponents/DetailCommentInfo'

import Scroll from "components/common/scroll/Scroll";
import GoodsList from 'components/content/goods/GoodsList'

import { getDetail, Goods, getRecommend } from "network/detail";
import {debounce} from 'common/utils'
import {ItemListenerMixin} from 'common/mixin'
export default {
  name: "Detail",
  components: {
    DetailNavBar,
    DetailSwiper,
    DetailBaseInfo,
    DetailShopInfo,
    DetailImageInfo,
    DetailParamsInfo,
    DetailCommentInfo,
    Scroll,
    GoodsList
  },
  mixins:[ItemListenerMixin],
  data() {
    return {
      iid: null,
      topImages: [],
      goodsInfo: {},
      shopInfo: {},
      detailInfo: {},
      itemParams: {},
      commentInfo: {},
      recommends: []
    };
  },
  created() {
    // 1.保存存入的id
    this.iid = this.$route.params.iid;
    // 2.根据iid请求详情数据
    getDetail(this.iid).then(res => {
      // 1.获取数据
      const data = res.result;
      // console.log(data);
      // 2.获取顶部的轮播数据
      this.topImages = res.result.itemInfo.topImages;
      // 3.创建商品的对象
      this.goodsInfo = new Goods(
        data.itemInfo,
        data.columns,
        data.shopInfo.services
      );
      // 4.取出店铺信息
      this.shopInfo = data.shopInfo;
      // 5.取出详情信息
      this.detailInfo = data.detailInfo;
      // 6.取出参数信息
      this.itemParams = data.itemParams;
      // 7.取出评论信息
      if(data.rate.cRate !== 0) {
        this.commentInfo = data.rate.list[0]
      }
    });
    // 3.请求推荐数据
    getRecommend().then(res => {
      this.recommends = res.data.list 
    })
  },
  mounted() {
   console.log("mounted");
   
  },
  destroyed() {
    this.$bus.$off('itemImgLoad', this.itemImgListener)
  },
  methods:{
     // 监听详情的图片加载完成,就调用此函数
    detailImageLoad() {
      // mixin，refresh是mounted里面的函数
      this.refresh()
    }
  }
};
</script>
<style scoped>
.detail {
  height: 100vh;
  background-color: #fff;
  position: relative;
  z-index: 1;
}
.content {
  /* 100%就是继承父级的height */
  background-color: #fff;
  height: calc(100% - 44px);
}
</style>