<template>
  <div class="bottom-bar">
    <div class="check-content">
      <check-button :is-checked="isSelectAll" class="check-button" @click.native="checkClick"/>
      <span>全选</span>
    </div>
    <div class="price">
      合计{{totalPrice}}
    </div>
    <div class="calculate" @click="calculate">
      去计算({{checkLength}})
    </div>
  </div>
</template>
<script>
import CheckButton from 'components/content/checkButton/CheckButton'
import {mapGetters} from 'vuex'
  export default {
    name: "CartBottomBar",
    components: {
      CheckButton
    },
    computed: {
      ...mapGetters(['cartList']),
      totalPrice() {
        return '￥' + this.cartList.filter(item => {
          return item.checked
        }).reduce((preVal, item) => {
          return preVal + item.count * item.price
        }, 0).toFixed(2)
      },
      checkLength() {
        return this.cartList.filter(item => item.checked).length
      },
      isSelectAll() {
        // 1.使用filter
        // !num
        // return !(this.cartList.filter(item => !item.checked).length)
        // 2.使用find
        if(this.cartList.length === 0) return false;
        // return !(this.cartList.find(item => !item.checked))
        // 3.普通遍历
        for(let item of this.cartList) {
          if(!item.checked) {
            return false
          }
        }
        return true;
      }
    },
    methods:{
      checkClick() {
        if(this.isSelectAll) {
          this.cartList.forEach(item => item.checked = false)
        } else {
          this.cartList.forEach(item => item.checked = true)
        }
      },
      calculate() {
        if(!this.isSelectAll) {
          this.$toast.Show('请选择购买的商品', 2000)
        }
      }
    }
  }
</script>
<style scoped>
  .bottom-bar {
    display:flex;
    position: relative;
    bottom:40px;
    height: 40px;
    line-height: 40px;
    background-color: #eee;
    font-size: 14px;
  }
  .check-content {
    display: flex;
    align-items: center;
    margin-left:10px;
    width: 60px;
  }
  .check-button {
    width: 20px;
    height: 20px;
    line-height: 20px;
    margin-right:5px;
  }
  .price{
    margin:0 30px;
    flex:1;
  }
  .calculate {
    width: 90px;
    background-color: red;
    color:#fff;
    text-align: center;
  }
</style>