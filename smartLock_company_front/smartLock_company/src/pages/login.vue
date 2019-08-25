<template>
  <div>
    <Card class="p-login">
      <p slot="title" style="text-align: center; font-size: 16px">智能门锁管理系统-公司端</p>
      <Form
        ref="formValidate"
        :model="formValidate"
        :rules="ruleValidate"
        :label-width="0"
        label-position="left">
        <Form-item prop="username">
          <Input
            v-model="formValidate.username"
            size="large"
            prefix="md-person"
            placeholder="请输入用户名或手机号"
            @on-enter="handleLogin" />
        </Form-item>
        <Form-item prop="password">
          <Input
            v-model="formValidate.password"
            type="password"
            size="large"
            prefix="md-lock"
            placeholder="请输入密码"
            @on-enter="handleLogin" />
        </Form-item>
        <Form-item v-show="showCode"  prop="captchaCode">
          <Row>
            <Col span="9">
              <Input
                v-model="captchaCode"
                size="large"
                placeholder="请输入验证码"
                @on-enter="handleLogin" />
            </Col>
            <Col span="9" offset="1">
              <img id="imgIdentifyingCode"/>
            </Col>
            <Col span="4" offset="1">
              <a @click="clickVerification">换一张</a>
            </Col>
          </Row>
        </Form-item>
        <Form-item prop="rememberMe" >
          <Row>
            <Col span="18">
              <template>
                <Tooltip max-width="125"  content="非个人电脑登陆时请慎重使用此功能" placement="left" theme="light">
                  <Checkbox v-model="rememberMe"> <span style="color: #2d8cf0" >记住密码，7天内自动登录</span></Checkbox>
                </Tooltip >
              </template>
            </Col>
            <Col span="4" offset="2">
              <!--<Badge status="processing"/>-->
              <icon type="md-help-circle"></icon>
              <a @click="value1 = true">说明</a>
            </Col>
          </Row>
        </Form-item>
        <Form-item>
          <Row>
            <Col span="10">
              <icon type="md-person-add"></icon>
              <a @click="registerUser">注册新账号</a>
            </Col>
            <Col span="6" offset="8">
              <icon type="ios-bug"></icon>
              <a @click="forgetPass">忘记密码</a>
            </Col>
          </Row>

        </Form-item>
        <Form-item>
          <Button
            type="primary"
            size="large"
            long
            @click="handleLogin">
            登陆
          </Button>
        </Form-item>
      </Form>
      <Drawer :closable="false" width="400" v-model="value1">
        <a  style="text-align: center;font-size: 16px; color: #2d8cf0; line-height: 24px;display: block;margin-bottom: 16px">本软件系统相关说明</a>
        <Divider />
        <p :style="pStyle" ><Badge status="processing"/>基础说明</p>
        <div class="demo-drawer-profile">
          <Row>
            <Col span="6">
              软件名称：
            </Col>
            <Col span="18">
              智能门锁管理系统-公司端
            </Col>
          </Row>
          <Row>
            <Col span="6">
              使用人员：
            </Col>
            <Col span="18">
              面向此智能锁研发销售公司所有员工
            </Col>
          </Row>
          <Row>
            <Col span="6">
              软件功能：
            </Col>
            <Col span="18">
              记录、分析和预测本产品的销售情况，为生产和市场提供基础的数据支持
            </Col>
          </Row>
        </div>
        <Divider />
        <p :style="pStyle"><Badge status="processing"/>登陆说明</p>
        <div class="demo-drawer-profile">
          <Row>
            <Col span="6">
              账号密码：
            </Col>
            <Col span="18">
              账号密码的注册需要身份验证，申请新的账号之后便可登陆使用
            </Col>
          </Row>
          <Row>
            <Col span="6">
              记住密码：
            </Col>
            <Col span="18">
              本功能在非个人计算机中实现登陆时请勿使用
            </Col>
          </Row>
        </div>
        <Divider />
        <p :style="pStyle"><Badge status="processing"/>开发人员</p>
        <div class="demo-drawer-profile">
          <Row>
            <Col span="6">
              人员名单：
            </Col>
            <Col span="18">
              Kikityer
            </Col>
          </Row>
          <Row>
            <Col span="6">
              Tel：
            </Col>
            <Col span="18">
              +86 18375335898
            </Col>
          </Row>
          <Row>
            <Col span="6">
              Email：
            </Col>
            <Col span="18">
              hudonghuimail@163.com
            </Col>
          </Row>
          <Row>
            <Col span="6">
              Github：
            </Col>
            <Col span="18">
              <a href="https://github.com/Kikityer" target="_blank">https://github.com/Kikityer</a>
            </Col>
          </Row>
        </div>
        <Divider />
        <Row style="text-align: center">
          <Col span="8" >
            <icon type="md-thumbs-up"></icon>
            <a style="font-size: 14px">喜欢请评分：</a>
          </Col>
          <Col span="10">
            <Rate v-model="value" />
          </Col>
          <Col span="6">
            <Button type="text" @click="submitScore">提交</Button>
          </Col>
        </Row>
      </Drawer>
    </Card>
    <Divider style="position: absolute; top: 85%"/>
    <p style="text-align: center;  font-size: 14px; position: absolute; top: 93%;left: 43%">Copyright &copy; 2018-2019 Kikityer</p>
  </div>

</template>


<script>
  import Vue from 'vue'
export default {
  name: 'login',
  data () {
    return {
      formValidate: {
        username: '',
        password: '',
      },
      ruleValidate: {
        username: [
          {
            required: true,
            message: '账号不能为空',
            trigger: 'blur'
          }
        ],
        password: [
          {
            required: true,
            message: '密码不能为空',
            trigger: 'blur'
          },
          {
            required: true,
            type:'string',
            min: 6,
            message: '密码不能少于6位',
            trigger: 'blur'
          }
        ]
      },
      rememberMe: false,
      VerificationImg:"",//图形验证码链接
      showCode: false,
      captchaCode: '',
      value:4,
      value1: false,
      pStyle: {
        fontSize: '16px',
        color: 'rgba(0,0,0,0.85)',
        lineHeight: '24px',
        display: 'block',
        marginBottom: '16px'
      },
      loginLogInfo: {
        loginIP: '',
        loginLocation: ''
      }
    }
  },

  created:function(){
    this.axios({
      method: 'post',
      url: 'api/cityjson?ie=utf-8',
    }).then( res =>{
      //res.data是搜狐API返回的数据 字符串格式  形如"var returnCitySN = {"cip": "210.45.209.226", "cid": "340100", "cname": "安徽省合肥市"};"
      let arr = res.data.split("=");  //根据 = 将字符串分割
      let str = arr[1]; //取出第二个字段  {"cip": "210.45.209.226", "cid": "340100", "cname": "安徽省合肥市"};
      let s = str.substr(0,str.length -1); //去除最后那个分号 ；
      let obj = JSON.parse(s); //将json格式字符串变成json对象
      let ip = obj.cip.substr(0,obj.cip.length -3)+'*';
      this.loginLogInfo.loginIP = ip;
      this.loginLogInfo.loginLocation = obj.cname;
    }).catch(function (error) {
      console.log(error);
    })
  },

  methods: {
    handleLogin () {
      this.$refs.formValidate.validate(async valid => {
        if (valid) {
          let username = this.formValidate.username;
          let password = this.formValidate.password;
          let rememberMe = this.rememberMe;
          if(this.showCode === false){  //不显示验证码表单
            this.$Spin.show();
            this.axios({
              method: 'post',
              url: 'http://localhost:8082/user/login',
              params: {username:username, password: password,rememberMe: rememberMe},
            }).then(res => {
              if(res.data.code === 200){
                this.$Spin.hide();
                // this.$Message.success('登陆成功！');
                this.$router.push({path:'/parentFrame/homepage'});
                this.loginLog();
                localStorage.setItem('username', username);
              }else {
                this.$Spin.hide();
                this.$Message.warning('用户名或密码错误！');
                this.showCode = true;
                this.clickVerification();
              }
            }).catch(function (error) {
              console.log(error);
            })
          }else if(this.showCode === true){  //显示验证码表单
            if(this.captchaCode.length !==0){
              this.$Spin.show();
              let captchaCode = this.captchaCode;
              this.axios({
                method: 'post',
                url: 'http://localhost:8082/user/login',
                params: {username:username, password: password,rememberMe: rememberMe,captchaCode: captchaCode},
              }).then(res => {
                console.log(res.data);
                if(res.data.code === 200){
                  this.$Spin.hide();
                  // this.$Message.success('登陆成功！');
                  this.$router.push({path:'/parentFrame/homepage'});
                  this.loginLog();
                  localStorage.setItem('username', username);
                }else if(res.data.code === 400 && res.data.msg === "验证码错误"){
                  this.$Spin.hide();
                  this.$Message.warning('验证码错误！');
                  this.clickVerification();
                }else {
                  this.$Spin.hide();
                  this.$Message.warning('用户名或密码错误！');
                  this.clickVerification();
                }
              }).catch(function (error) {
                console.log(error);
              })
            }else {
              this.$Message.warning('验证码不能为空！');
            }
          }
        }
      })
    },
    clickVerification:function () {
      let identifyCodeSrc = "http://localhost:8082/img/getKaptchaImage?" + Math.random();
      let objs = document.getElementById("imgIdentifyingCode");
      objs.src = identifyCodeSrc;
    },
    submitScore:function () {
      let score = this.value;
      this.axios({
        method: 'post',
        url: 'http://localhost:8082/user/score',
        params: {score:score},
      }).then(res => {
        if (res.data.msg === "不满意"){
          this.$Message.success('提交成功，感谢您的评价！如果有不满意的地方请与开发人员联系！');
        }else if (res.data.msg === "满意"){
          this.$Message.success('提交成功，感谢您的评价！我们会继续努力！');
        }else if (res.data.msg === "很满意"){
          this.$Message.success('提交成功，感谢您的评价！我们会继续努力！');
        }else {
          this.$Message.success('提交成功，感谢您的评价！我们会继续努力！');
        }
      }).catch(function (error) {
        console.log(error)
      })
    },
    registerUser:function () {
      this.$router.push({path:'/register'});
    },
    forgetPass:function () {
      this.$router.push({path:'/forgetPassword1'});
    },

    loginLog: function () {
      let loginUsername = this.formValidate.username;
      let loginIP = this.loginLogInfo.loginIP;
      let loginLocation = this.loginLogInfo.loginLocation;
      this.axios({
        method: 'post',
        url: 'http://localhost:8082/loginLog/saveLog',
        //把参数放进data中专用于post请求，后台可以用@RequstBody接收json格式参数
        data: {loginUsername:loginUsername, loginIP:loginIP, loginLocation:loginLocation},
      }).then( res => {
        console.log(res.data)
      }).catch(function (error){
        console.log(error)
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.p-login {
  position: absolute !important;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -80%);
  background-color: white;
  width: 340px;
  padding: 0 20px;
}

.demo-drawer-profile{
  font-size: 14px;
}
.demo-drawer-profile .ivu-col{
  margin-bottom: 12px;
}

/*.pb-form{
margin(10px, null, 10px, null);
}*/
</style>
