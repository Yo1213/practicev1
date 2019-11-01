<template>
  <div id="tab-bar">
    <slot name="tab-bar"></slot>
  </div>
</template> 
<script>
export default {
  name: "TabBar"
};
</script> 
<style scoped>
  #tab-bar {
    /* 水平布局 */
    display: flex;
    background-color: #f4f4f4; 
    position: fixed;
    left:0;
    right:0;
    bottom:0;
    box-shadow: 0px -1px 1px rgba(100, 100, 100,.5);
  }
</style>