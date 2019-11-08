import Toast from './Toast' 
const obj = {}
obj.install = function (Vue) {
  // console.log("执行了obj的install函数",Vue);
  // document.body.appendChild(Toast.$el)
 
// 通过组价构造器将组件Toast =》‘传给’了toast =》 toast挂载到生成的div元素上(生成元素) =》将元素插入到页面
// 因为通过组价构造器将组件Toast =》‘传给’了toast，所以可以直接将 toast组件 挂载到Vue.prototype.$toast

  // 1、创建组件构造器
  const toastContrustor = Vue.extend(Toast)
  // 2、new的方式，根据组件的构造器，可以创建出来一个组件对象
  const toast = new toastContrustor()
  // 3、将组件对象，手动挂载到某一元素上
  toast.$mount(document.createElement('div'))
  // 4、Toast.$el对应的就是div
  document.body.appendChild(toast.$el)
  Vue.prototype.$toast = toast;
}
export default obj