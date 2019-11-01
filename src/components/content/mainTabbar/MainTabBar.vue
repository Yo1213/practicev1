<template>
    <tab-bar>
      <!-- home本身在props里面定义成了一个字符串变量，它是一个变量，所以不需要动态绑定 -->
      <tab-bar-item slot="tab-bar" path="/home" activeColor="blue">
        <img slot="item-icon" src="~assets/img/tabbar/home.svg" alt="" />
        <img slot="item-icon-active" src="~assets/img/tabbar/home_active.svg" alt="" />
        <div slot="item-text">首页</div>
      </tab-bar-item>
      <tab-bar-item slot="tab-bar" path="/category" activeColor="red">
        <img slot="item-icon" src="~assets/img/tabbar/category.svg" alt="" />
        <img slot="item-icon-active" src="~assets/img/tabbar/category_active.svg" alt="" />
        <div slot="item-text">分类</div>
      </tab-bar-item>
      <tab-bar-item slot="tab-bar" path="/cart" activeColor="green">
        <img slot="item-icon" src="~assets/img/tabbar/cart.svg" alt="" />
        <img slot="item-icon-active" src="~assets/img/tabbar/cart_active.svg" alt="" /> 
        <div slot="item-text">购物车</div>
      </tab-bar-item>
      <tab-bar-item slot="tab-bar" path="/profile" activeColor="purple">
        <img slot="item-icon" src="~assets/img/tabbar/profile.svg" alt="" />
        <img slot="item-icon-active" src="~assets/img/tabbar/profile_active.svg" alt="" /> 
        <div slot="item-text">我的</div>
      </tab-bar-item>
    </tab-bar>
</template>

<script>
import TabBar from 'components/common/tabbar/TabBar'
import TabBarItem from 'components/common/tabbar/TabBarItem'
export default {
  name: 'MainTabBar',
  components: {
    TabBar,
    TabBarItem
  }
}
</script>

<style>
</style>
