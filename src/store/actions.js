import {
  ADD_COUNTER,
  ADD_TO_CART
} from './mutation-types'
export default  {
  addCart(context, payload) {
    // payload新添加的商品
    // let oldProduct = null;
    // for(let item of state.cartList) {
    //   if( item.iid === payload.iid) {
    //     oldProduct = item
    //   }
    // }

    // 如果是相同商品，就数量加1，不需要再生成新的div
    // 如果是新商品，count为1
    
// 方法返回值：返回符合测试条件的第一个数组元素值，如果没有符合条件的则返回 undefined
    return new Promise((resolve, reject) => {
      let oldProduct = context.state.cartList.find(item => item.iid === payload.iid);
    if(oldProduct) {
     context.commit(ADD_COUNTER, oldProduct)
     resolve('当前商品数量+1')
    }else {
      payload.count = 1;
      // context.state.cartList.push(payload)
      context.commit(ADD_TO_CART, payload)
      resolve('添加了新的商品')
    }
    })
    //把购物车数据一传过来，肯定cartList没有值，因此oldProduct为空
    // 第一步：进入else，在cartList对象里添加新的属性，初始值为 1
    // 以后，都先进入if，通过iid检查之前有没有加入到购物车
    // 有？ count++ 
    // 无：push
  }
}