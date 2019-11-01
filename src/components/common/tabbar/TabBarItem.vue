<template>
  <div class="tab-bar-item" @click="itemClick">
    <!-- 使用插槽时，整个标签都会被替换掉，包括插槽上的属性。
    所以当给插槽加属性时，在插槽外层加上一个标签，在外层标签上加上属性 -->
    <div v-if="!isActive" ><slot name="item-icon"></slot></div>
    <div v-else><slot name="item-icon-active"></slot></div>
    <div :style="activeStyle"><slot name="item-text"></slot></div>
  </div>
</template> 
<script>
export default {
  name: "TabBarItem",
  props: {
    path:String,
    activeColor: {
      type: String,
      default:'red'
    }
  },
  data() {
    return {
      // isActive: false,
    }
  },
  computed: {
    isActive() {
      // infexof()=-1时，表示数组中没有出现
      // /home->item1(/home) = true
      // /home->item1(/category) = true
      // /home->item1(/cart) = true
      // /home->item1(/profile) = true
      return this.$route.path.indexOf(this.path) !== -1;
    },
    activeStyle() {      
      return this.isActive ? {color: this.activeColor} : {};
    }
  },
  methods: {
    itemClick() {
      this.$router.replace(this.path);   
    }
  }
};
</script> 
<style scoped>
/* 均等分 */
.tab-bar-item {
  flex: 1;
  text-align: center;
  height: 49px;
  font-size: 14px;
}
.tab-bar-item img {
  width:20px;
  height: 18px;
  margin-top: 10px;
  vertical-align: middle;
  margin-bottom:2px;
}
/* .active {
  
} */
</style>