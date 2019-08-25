import Vue from 'vue'
import Router from 'vue-router'
import login from '../pages/login'  //导入登陆页面
import parentFrame from '../pages/parentFrame'
import homepage from '../pages/homepage'
import customInfo from '../pages/customInfo'
import customChart from '../pages/customChart'
import productInfo from '../pages/productInfo'
import productChart from '../pages/productChart'
import register from '../pages/register'
import forgetPassword1 from '../pages/forgetPassword1'
import forgetPassword2 from '../pages/forgetPassword2'
import forgetPassword3 from '../pages/forgetPassword3'
import forgetPassword4 from '../pages/forgetPassword4'
import loginLog from '../pages/loginLog'
import rePhoneNum from '../pages/rePhoneNum'
import rePhoneNum1 from '../pages/rePhoneNum1'
import rePassword from '../pages/rePassword'


Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/parentFrame/homepage',
      hidden: true
    },

    {
      path: '/login',
      name:'login',
      component: login,
      hidden: true
    },
    {
      path: '/register',
      name:'register',
      component: register,
      hidden: true
    },
    //忘记密码第一步：填写手机号
    {
      path: '/forgetPassword1',
      name:'forgetPassword1',
      component: forgetPassword1,
      hidden: true
    },
    //忘记密码第二步：验证身份
    {
      path: '/forgetPassword2',
      name:'forgetPassword2',
      component: forgetPassword2,
      hidden: true
    },
    //忘记密码第三步：设置新密码
    {
      path: '/forgetPassword3',
      name:'forgetPassword3',
      component: forgetPassword3,
      hidden: true
    },
    //忘记密码第四步：设置成功
    {
      path: '/forgetPassword4',
      name:'forgetPassword4',
      component: forgetPassword4,
      hidden: true
    },
    //登陆日志
    {
      path: '/loginLog',
      name:'loginLog',
      component: loginLog,
      hidden: true
    },
    //修改手机 步骤一
    {
      path: '/rePhoneNum',
      name:'rePhoneNum',
      component: rePhoneNum,
      hidden: true
    },
    //修改手机 步骤二
    {
      path: '/rePhoneNum1',
      name:'rePhoneNum1',
      component: rePhoneNum1,
      hidden: true
    },
    //修改密码
    {
      path: '/rePassword',
      name:'rePassword',
      component: rePassword,
      hidden: true
    },
    {
      path: '/parentFrame',
      name:'首页',
      iconCls: 'md-flag',
      component: parentFrame,
      children:[
        { path: 'homepage', component: homepage, name: '首页信息' },
      ]
    },
    {
      path: '/parentFrame1',
      name: '客户',
      iconCls: 'ios-people',
      component: parentFrame,
      children:[
        { path: 'customInfo', component: customInfo, name: '客户信息' },
        { path: 'customChart', component: customChart, name: '统计分析' },
      ]
    },
    {
      path: '/parentFrame2',
      name: '产品',
      iconCls: 'md-briefcase',
      component: parentFrame,
      children:[
        { path: 'productInfo', component: productInfo, name: '产品信息' },
        { path: 'productChart', component: productChart, name: '统计分析' },
      ]
    },


  ]
})
