<template>
  <div class="layout">
    <Modal v-model="modal2" :closable="false" :mask-closable="false">
      <p slot="header" style="color:#2d8cf0;text-align:center">
        <Icon type="md-help-buoy"></Icon>
        <span>帮助</span>
      </p>
      <p>Content of dialog</p>
      <p>Content of dialog</p>
      <p>Content of dialog</p>
      <div slot="footer">
        <Button type="primary" size="large" long  @click="iknow">知道了</Button>
      </div>
    </Modal>
    <Layout>
      <Header>
        <Menu mode="horizontal" theme="dark">
          <div class="layout-logo">
            <p style="font-size: 16px">智能门锁管理平台-公司端</p>
          </div>
          <!--<img src="../../assets/lock.png"/>-->
          <div class="layout-nav">
            <Submenu name="4">
              <template slot="title">
                <Avatar shape="circle" src="https://i.loli.net/2017/08/21/599a521472424.jpg" />
              </template>
              <MenuItem name="4-1" @click.native="repass"><Icon type="ios-lock"></Icon>修改密码</MenuItem>
              <MenuItem name="4-2" @click.native="rephoneNum"><Icon type="md-phone-portrait"></Icon>修改手机</MenuItem>
              <MenuItem name="4-3" @click.native="loginLog"><Icon type="ios-information-circle"></Icon>登陆日志</MenuItem>
              <MenuItem name="4-4" @click.native="backhome"><Icon type="ios-home"></Icon>回到首页</MenuItem>
              <MenuItem name="4-5" @click.native="modal2 = true"><Icon type="ios-help-circle"></Icon>帮助</MenuItem>
              <MenuItem name="4-6" @click.native="logOut"><Icon type="md-log-out"></Icon>退出</MenuItem>
            </Submenu>
          </div>
          <div style="cursor:pointer" @click="value1 = true">
            <i class="iconfont icon-et-more3" style="color: white"></i>
            <Drawer :closable="false" width="400" v-model="value1">
              <a style="text-align: center;font-size: 16px; color: #2d8cf0; line-height: 24px;display: block;margin-bottom: 16px">本软件系统相关说明</a>
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
          </div>
        </Menu>
      </Header>
      <Layout :style="{padding: '0 30px'}">
        <Content :style="{padding: '24px 25%', margin: '35px 0',Height: 'auto', background: '#fff'}">
          <Card style="margin: 0 0 70px 0; background-color: #fcfdfc">
            <p slot="title" style="text-align: center; font-size: 20px; margin: 10px 0">修改手机</p>
            <Form>
              <FormItem >
                <p style="font-size: 15px; font-weight: bold">步骤二：更换新号码</p>
              </FormItem>
            </Form>
            <Form style="margin: 0 20% "
                  ref="formValidate"
                  :model="formValidate"
                  :rules="ruleValidate"
                  :label-width="70"
                  label-position="right">
              <FormItem label="新号码" prop="newPhoneNum">
                <Input
                  v-model="formValidate.newPhoneNum"
                  size="large"
                  placeholder="请输入新号码"
                  @on-enter="finish"/>
              </FormItem>
              <FormItem label="验证码"  prop="verifyCode">
                <Row>
                  <Col span="15">
                    <Input
                      v-model="formValidate.verifyCode"
                      size="large"
                      placeholder="请输入验证码"
                      @on-enter="finish"/>
                  </Col>
                  <Col span="8" offset="1">
                    <Button type="primary" size="large"  :disabled="disabled" @click="sendCode">{{btntxt}}</Button>
                  </Col>
                </Row>
              </FormItem>
              <Form>
                <FormItem>
                  <Button
                    type="primary"
                    size="large"
                    long
                    @click="finish">
                    完成
                  </Button>
                </FormItem>
              </Form>
            </Form>
          </Card>
        </Content>
      </Layout>
      <Footer class="layout-footer-center">Copyright &copy; 2018-2019 Kikityer</Footer>
    </Layout>
  </div>
</template>

<script>
    export default {
      inject: ['reload'],
        name: "rePhoneNum1",
      data(){
        return{
          pStyle: {
            fontSize: '16px',
            color: 'rgba(0,0,0,0.85)',
            lineHeight: '24px',
            display: 'block',
            marginBottom: '16px'
          },
          value:4,
          value1: false,
          modal2: false,
          formValidate: {
            newPhoneNum: '',
            verifyCode: '',
          },
          ruleValidate: {
            newPhoneNum: [
              {
                required: true,
                message: '手机号不能为空',
                trigger: 'blur'

              },
              {
                required: true,
                type:'string',
                len: 11,
                message: '请正确输入手机号',
                trigger: 'blur'
              }
            ],
            verifyCode: [
              {
                required: true,
                message: '验证码不能为空',
                trigger: 'blur'
              }
            ]
          },
          btntxt:"获取验证码",
          disabled:false,
          time:0,
        }
      },

      methods:{
        /**
         * 点击获取手机验证码
         */
        sendCode(){
          let phoneNum = this.formValidate.newPhoneNum;
          this.axios({
            method: 'post',
            url: 'http://localhost:8082/revise/getNewCode',
            params: {phoneNum: phoneNum},
          }).then( res => {
            if (res.data.message === "no login"){
              this.$Message.error('登陆超时，请登录后访问！');
              this.$router.push({path:'/login'});
            }else if (res.data.code === 200) {
              this.$Message.success('短信发送成功，请注意查收！');
            }
          }).catch(function (error) {
            console.log(error)
          })
        },

        /**
         * 填写手机验证码，点击完成按钮
         */
        finish(){
          this.$refs.formValidate.validate(async valid => {
            if (valid) {  //表单验证通过
              let oldPhoneNum = localStorage.getItem('oldPhoneNum');
              let newPhoneNum = this.formValidate.newPhoneNum;
              let verifyCode = this.formValidate.verifyCode;
              this.axios({
                method: 'post',
                url: 'http://localhost:8082/revise/verifyNewNum',
                params: {oldPhoneNum: oldPhoneNum, newPhoneNum: newPhoneNum, verifyCode: verifyCode},
              }).then(res => {
                if (res.data.message === "no login"){
                  this.$Message.error('登陆超时，请登录后访问！');
                  this.$router.push({path:'/login'});
                }else{
                  if (res.data.code === 200) {
                    this.$router.push({path:'/'});
                    this.$Message.success('修改手机号码成功！');
                    localStorage.removeItem('oldPhoneNum');
                  }else if(res.data.msg === "code expire"){
                    this.$Message.warning('验证码已过期，请重新获取！');
                  }else if(res.data.msg === "code not equal"){
                    this.$Message.warning('验证码输入错误！');
                  }
                }
              }).catch(function (error) {
                console.log(error)
              })
            }
          })
        },

        /**
         * 点击退出登陆，然后路由到登陆界面
         */
        logOut() {
          this.$Modal.confirm({
            title: '提示',
            content: '是否确认退出登陆？',
            onOk: () => {
              this.axios({
                method: 'get',
                url: 'http://localhost:8082/out/logout',
              }).then(res => {
                // if(res.data.data === "成功退出登陆!"){
                this.$router.push({path:'/login'});
                localStorage.removeItem('username');
                // }
              }).catch(function (error) {
                console.log(error)
              })
            },
          })
        },

        /**
         * 点击提交评分
         */
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

        /**
         * 点击主页，路由到主页界面
         */
        backhome: function () {
          this.$router.push({path:'/'});
        },

        /**
         * 点击帮助，弹出对话框，点击知道了，重新加载本界面
         */
        iknow: function () {
          this.reload();
        },

        /**
         * 点击登陆日志，路由到登陆日志界面
         */
        loginLog:function () {
          this.$router.push({path:'/loginLog'});
        },

        /**
         * 点击修改手机号，重新加载本界面
         */
        rephoneNum: function () {
          this.reload();
        },

        /**
         * 点击更换密码，路由到更换密码界面
         */
        repass: function () {
          this.$router.push({path:'/rePassword'});
        },

        /**
         * 点击账号管理，路由到账号管理界面
         */
        accountManage: function () {
          this.$router.push({path:'/accountManage'});
        }
      }
    }
</script>

<style scoped>
  .layout{
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: absolute;
    width: 100%;
    height: 100%;
    border-radius: 4px;
    overflow: hidden;
  }
  .layout-logo{
    width: 230px;
    height: 30px;
    /*background: #2d8cf0;*/
    border-radius: 3px;
    float: left;
    position: relative;
    top: 0px;
    left: 20px;
    color: white;
  }
  .layout-nav{
    width:100px;
    margin: 0 auto;
    margin-right: 20px;
  }
  .layout-footer-center{
    text-align: center;
  }
</style>
