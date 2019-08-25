// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import axios from 'axios'   //全局引入axios
import VueAxios from 'vue-axios'
import iView from 'iview';
import 'iview/dist/styles/iview.css';
import './assets/iconfont/iconfont.css'


Vue.config.productionTip = false;
Vue.prototype.axios = axios ; //将axios方法设定为Vue对象的方法（注意：时axios是Vue实例中的方法，方法必须为对象调用）
Vue.use(VueAxios, axios);
axios.defaults.withCredentials = true;  //axios默认发送时不带上cookie。设置为true *****这点非常重要**验证码等功能依靠此实现
Vue.use(iView);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
