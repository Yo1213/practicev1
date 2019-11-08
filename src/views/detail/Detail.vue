<template>
  <div class="detail">
    <!-- 导航栏 -->
    <detail-nav-bar @titleClick="titleClick" ref="nav"></detail-nav-bar>
    <scroll class="content" ref="scroll":probe-type="3" @scroll="contentScroll">
      <detail-swiper :top-images="topImages"/>
      <detail-base-info :goods="goodsInfo"/>
      <detail-shop-info :shop="shopInfo"/>
      <detail-image-info :detail-info="detailInfo" @detailImageLoad="detailImageLoad"/>
      <detail-params-info :param-info="itemParams" ref="params"/>
      <detail-comment-info :comment-info="commentInfo" ref="comment"/>
      <goods-list :goods="recommends" ref="recommend"/></goods-list>
    </scroll>
    <detail-bottom-bar @addCart="addToCart"/>
    <back-top @click.native="backTop" v-show="isShowBackTop" />
    <!-- <toast :show="show" :message="message"/> -->
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
import DetailBottomBar from './childcomponents/DetailBottomBar'

import Scroll from "components/common/scroll/Scroll";
import GoodsList from 'components/content/goods/GoodsList'
// import Toast from 'components/common/toast/Toast'

import { getDetail, Goods, getRecommend } from "network/detail";
import {debounce} from 'common/utils'
import {ItemListenerMixin, backTopMixin} from 'common/mixin'

import {mapActions} from 'vuex'
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
    DetailBottomBar,
    Scroll,
    GoodsList,
    // Toast
  },
  mixins:[ItemListenerMixin, backTopMixin],
  data() {
    return {
      iid: null,
      topImages: [],
      goodsInfo: {},
      shopInfo: {},
      detailInfo: {},
      itemParams: {},
      commentInfo: {},
      recommends: [],
      themeTopYs: [],
      getThemeTopY: null,
      currentIndex: 0,
      // message: '',
      // show: false
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
    }),
    this.getThemeTopY = debounce(() => {
      this.themeTopYs = []
      this.themeTopYs.push(0)
      // this.$refs.params组件
      // this.$refs.params.$el元素
      this.themeTopYs.push(this.$refs.params.$el.offsetTop)
      this.themeTopYs.push(this.$refs.comment.$el.offsetTop)
      this.themeTopYs.push(this.$refs.recommend.$el.offsetTop)
      this.themeTopYs.push(Number.MAX_VALUE)
    }, 100)
  },
  mounted() {
  },
  destroyed() {
    this.$bus.$off('itemImgLoad', this.itemImgListener)
  },
  methods:{
    // ...mapActions(['addCart']),
    ...mapActions({
      add: 'addCart'
    }),
     // 监听详情的图片加载完成,就调用此函数
    detailImageLoad() {
      // mixin，refresh是mounted里面的函数
      this.refresh()
      this.getThemeTopY()
    },
    titleClick(index) {
      this.$refs.scroll.scrollTo(0, -this.themeTopYs[index], 100)
    },
    contentScroll(position) {
      // 1、获取y值
      const positionY = -position.y
      // 2、positionY和主题中的值进行对比      
      let length = this.themeTopYs.length
      for(let i = 0; i < length - 1; i++) {
        // this.themeTopYs[i+1]越界，在数组最后面加上计算机自带的允许的最大值Number.MAX_VALUE
        // 所以不需要遍历数组最后一位
        if(this.currentIndex !== i && (i < length - 1 && positionY >= this.themeTopYs[i] && positionY < this.themeTopYs[i+1])){
         this.currentIndex = i;
         this.$refs.nav.currentIndex = this.currentIndex;
        }   
      }
      // 3、判断BackTop是否显示
      this.listenShowBackTop(position)  
    },
    addToCart() {
      // 1、获取购物车需要展示的信息
      const product = {}
      product.picture = this.topImages[0];
      product.title = this.goodsInfo.title;
      product.desc = this.goodsInfo.desc;
      product.price = this.goodsInfo.realPrice;
      product.iid = this.iid;
      // 2.将商品添加到购物车里面
      // this.$store.dispatch('addCart', product).then(res => {
      //   console.log(res);       
      // })

      //  this.addCart(product).then(res => {
      //   console.log(res);       
      // })

       this.add(product).then(res => {
        // this.show = true;
        // this.message = res;
        // setTimeout(() => {
        //    this.show = false;
        //    this.message = '';
        // }, 1500)       
        this.$toast.Show(res, 1500)
      })
      // 3.toast弹窗，添加到购物车成功

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
  height: calc(100% - 44px - 58px - 40px);
}
</style>