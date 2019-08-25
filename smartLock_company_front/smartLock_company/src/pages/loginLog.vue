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
        <Content :style="{padding: '24px 40px', margin: '35px 0',Height: 'auto', background: '#fff'}">
          <Card>
            <p slot="title" style="text-align: center; font-size: 20px; margin: 10px 0">登录日志</p>
            <Form style="margin: 0 38px">
              <FormItem>
                <p style="font-size: 18px"><icon type="ios-warning" size="24"></icon>  若发现异常登陆，请尽快修改密码！</p>
              </FormItem>
            </Form>
            <CellGroup style="margin: 20px 20px 60px 20px;">
              <Cell style="margin: 10px 0;">
                <p slot="" style="font-size: 16px">{{time1}}</p>
                <p slot="extra" style="font-size: 16px">{{ip1}}</p>
              </Cell>
              <Cell style="margin: 10px 0;background-color: #f5f7f9">
                <p slot="" style="font-size: 16px">{{time2}}</p>
                <p slot="extra" style="font-size: 16px">{{ip2}}</p>
              </Cell>
              <Cell style="margin: 10px 0;">
                <p slot="" style="font-size: 16px">{{time3}}</p>
                <p slot="extra" style="font-size: 16px">{{ip3}}</p>
              </Cell>
              <Cell style="margin: 10px 0;background-color: #f5f7f9">
                <p slot="" style="font-size: 16px">{{time4}}</p>
                <p slot="extra" style="font-size: 16px">{{ip4}}</p>
              </Cell>
              <Cell style="margin: 10px 0;">
                <p slot="" style="font-size: 16px">{{time5}}</p>
                <p slot="extra" style="font-size: 16px">{{ip5}}</p>
              </Cell>
              <Cell style="margin: 10px 0;background-color: #f5f7f9">
                <p slot="" style="font-size: 16px">{{time6}}</p>
                <p slot="extra" style="font-size: 16px">{{ip6}}</p>
              </Cell>
            </CellGroup>
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
        name: "loginLog",
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
            time1: '',
            ip1: '',
            time2: '',
            ip2: '',
            time3: '',
            ip3: '',
            time4: '',
            ip4: '',
            time5: '',
            ip5: '',
            time6: '',
            ip6: '',
            modal2: false,
          }
      },

      //页面未渲染之前获取日志信息
      mounted(){
         this.getLog();
      },
      methods:{

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
         * 点击登陆日志，刷新本页面
         */
        loginLog:function () {
          this.reload();
        },

        /**
         * 获取日志信息
         */
        getLog: function () {
          let username = localStorage.getItem('username');
          this.axios({
            method: 'post',
            url: 'http://localhost:8082/loginLog/getLog',
            params: {username:username},
          }).then( res => {
            if (res.data.message === "no login"){
              this.$Message.error('登陆超时，请登录后访问！');
              this.$router.push({path:'/login'});
            }else {
              console.log(res.data[0].loginTime);
              this.time1 = res.data[0].loginTime;
              this.ip1 = res.data[0].loginLocation;
              this.time2 = res.data[1].loginTime;
              this.ip2 = res.data[1].loginLocation;
              this.time3 = res.data[2].loginTime;
              this.ip3 = res.data[2].loginLocation;
              this.time4 = res.data[3].loginTime;
              this.ip4 = res.data[3].loginLocation;
              this.time5 = res.data[4].loginTime;
              this.ip5 = res.data[4].loginLocation;
              this.time6 = res.data[5].loginTime;
              this.ip6 = res.data[5].loginLocation;
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
         * 点击修改手机号，路由到修改手机号界面
         */
        rephoneNum: function () {
          this.$router.push({path:'/rePhoneNum'});
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
