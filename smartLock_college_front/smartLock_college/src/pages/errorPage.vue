<template>
  <div class="bgImg">
    <a href="http://localhost:8080/" style="position: absolute; top: 80%; left: 48%; font-size: 20px">返回到首页</a>
  </div>

</template>

<script>
    export default {
        name: "errorPage"
    }
</script>

<style scoped>
  .bgImg {
    /*background: #1b6d85;*/
    background: url("../assets/error.jpg") no-repeat ;
    background-size: cover;
    position: fixed;
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
  }
</style>
