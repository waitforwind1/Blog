<template>
  <div class="wrapper">
    <div style="margin: 200px auto; width: 350px; height: 280px; padding: 20px; ">
      <el-button style="height:100px;width:300px" type="primary" @click="home" round ><h1>进入主页面</h1><i class="el-icon-caret-right" style="font-size: 20px"></i></el-button>
    </div>
  </div>
</template>

<script>
export default {
  name: "Preindex.vue",
  methods:{
    home(){
      this.$router.push("/front/article")
    }
  }

}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right,#67C23A ,#3F5EFB);
  overflow: hidden;
}
</style>