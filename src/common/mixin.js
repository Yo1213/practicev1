import {debounce} from './utils'
import BackTop from 'components/content/backtop/BackTop'
// 看代码时，把这里的代码统统放进引用的组件里
export const ItemListenerMixin = {
  data() {
    return {
      itemImgListener: null,
      refresh: null
    }
  },
  mounted() {
    this.refresh = debounce(this.$refs.scroll.refresh, 100)
    this.itemImgListener = () => {
      this.refresh();
    }
    this.$bus.$on('itemImgLoad', this.itemImgListener)
    // console.log("我是mixin内容");  
  }
}
export const backTopMixin = {
  components: {
    BackTop
  },
  data() {
    return {
      isShowBackTop: false
    }
  },
  methods: {
    backTop() {
      this.$refs.scroll.scrollTo(0, 0, 100)
    },
    listenShowBackTop(position) {
      this.isShowBackTop = (-position.y) > 500
    }
  }
}