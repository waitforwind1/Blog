import Vue from 'vue'
import 'element-ui/lib/theme-chalk/index.css';
import ElementUI from 'element-ui';
import App from './App.vue'
import router from './router'
import store from './store'
import './assets/gloable.css'
import request from "@/utils/request";
import './assets/iconfont/iconfont.css'

// main.js全局注册
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// use
Vue.use(mavonEditor)

Vue.config.productionTip = false

Vue.use(ElementUI, { size: "mini" });

Vue.prototype.request=request

new Vue({
  el:'#app',
  router,
  store,
  render: h => h(App)
})
