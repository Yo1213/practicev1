<template>
  <div id="home">
    <nav-bar class="home-nav">
      <div slot="center">购物街</div>
    </nav-bar>
    <tab-control :titles="['流行', '新款', '精选']"
                   @tabClick="tabClick" 
                   ref="tabControl1" 
                   class="tab-control"
                   v-show="this.isTabFixed">
    </tab-control>
    <scroll class="content" 
            ref="scroll" 
            :probe-type="3"
            @scroll="contentScroll" 
            :pull-up-load="true"
            @pullingUp="loadMore">
      <!-- banner父传子 -->
      <home-swiper :banners="banners" @swiperImageLoad="swiperImageLoad"></home-swiper>
      <recommend-view :recommends="recommends"></recommend-view>
      <feature-view></feature-view>
      <tab-control :titles="['流行', '新款', '精选']"
                   @tabClick="tabClick" 
                   ref="tabControl2" >
      </tab-control>
      <goods-list :goods="showGoods"></goods-list>
    </scroll>
    <back-top @click.native="backTop" v-show="isShowBackTop"/>
  </div>
</template> 
<script>
import HomeSwiper from "./childComps/HomeSwiper";
import RecommendView from "./childComps/RecommendView";
import FeatureView from "./childComps/FeatureView";

import NavBar from "components/common/navbar/NavBar";
import TabControl from "components/content/tabControl/TabControl";
import GoodsList from "components/content/goods/GoodsList";
import Scroll from "components/common/scroll/Scroll";

import { getHomeMultidata, getHomeGoods } from "network/home";
import {debounce} from 'common/utils'
import {ItemListenerMixin, backTopMixin} from 'common/mixin'
export default {
  name: "Home",
  components: {
    HomeSwiper,
    RecommendView,
    FeatureView,
    NavBar,
    TabControl,
    GoodsList,
    Scroll
  },
  data() {
    return {
      banners: [],
      recommends: [],
      goods: {
        pop: { page: 0, list: [] },
        new: { page: 0, list: [] },
        sell: { page: 0, list: [] }
      },
      currentType: "pop",
      tabOffsetTop: 0,
      isTabFixed: false,
      saveY: 0
    };
  },
  mixins: [ItemListenerMixin, backTopMixin],
  computed: {
    showGoods() {
      return this.goods[this.currentType].list;
    }
  },
  created() {
    // 1、请求多个数据
    // 调用methods里面的函数并执行
    this.getHomeMultidata();
    // 2、请求商品数据
    this.getHomeGoods("pop");
    this.getHomeGoods("new");
    this.getHomeGoods("sell");
  },
  mounted() {
    
  },
  destroy() {
    // console.log("destroy");
  },
  activated() {
    this.$refs.scroll.scrollTo(0, this.saveY, 0);
    this.$refs.scroll.refresh();
  },
  deactivated() {
    // 1.保存y值
    this.saveY = this.$refs.scroll.getScrollY();
    // 2.取消全局监听事件
    this.$bus.$off('itemImgLoad', this.itemImgListener)
  },
  methods: {
    // 事件监听相关的方法
    contentScroll(position) {
      // 1.判断BackTop是否显示
      this.listenShowBackTop(position);
      // 2。判断tabControls是否吸顶(position: fixed)
      this.isTabFixed = (-position.y) > this.tabOffsetTop;
    },
    tabClick(index) {  
      switch (index) {
        case 0:
          this.currentType = "pop";
          break;
        case 1:
          this.currentType = "new";
          break;
        case 2:
          this.currentType = "sell";
          break;
      }
      if(this.$refs.tabControl1 !== undefined) {
        this.$refs.tabControl1.currentIndex = index;
        this.$refs.tabControl2.currentIndex = index;
      }
    },
    loadMore() {
      this.getHomeGoods(this.currentType)
    },
    swiperImageLoad() {
      // 所有的组件都有一个属性$el：用于获取组件中的元素
      this.tabOffsetTop = this.$refs.tabControl2.$el.offsetTop;  
    },
    // 网络请求相关的方法
    getHomeMultidata() {
      getHomeMultidata().then(res => {
        // console.log(res);
        this.banners = res.data.banner.list;
        this.recommends = res.data.recommend.list;
      });
    },
    getHomeGoods(type) {
      const page = this.goods[type].page + 1;
      getHomeGoods(type, page).then(res => {
        // type:是传进来的'pop', 'new' ,'sell'
        this.goods[type].list.push(...res.data.list);
        this.goods[type].page += 1;
        // 完成上拉加载更多，这样才能进行下一页的加载
        this.$refs.scroll.finishPullUp();
      });
    }
  }
};
</script> 
<style scoped>
#home {
  /* padding-top: 44px; */
  height: 100vh;
}
.home-nav {
  background-color: var(--color-tint);
  color: white;
  /* 在源盛世，为了不跟随一起滚动 */
  /* position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 9; */
}
/* .content {
  height: 30calc(100% - 93px);
  overflow: hidden;
  margin-top: 44px;
} */
.content {
  overflow:hidden;
  position: absolute;
  top: 44px;
  bottom: 49px;
  left: 0;
  right:0;
}
.tab-control {
  position:relative;
  z-index: 9;
}
</style>