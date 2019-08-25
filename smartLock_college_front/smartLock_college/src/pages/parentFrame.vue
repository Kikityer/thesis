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
            <p style="font-size: 16px; color: #2d8cf0">智能门锁管理平台-高校端</p>
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
              <a  style="text-align: center;font-size: 16px; color: #2d8cf0; line-height: 24px;display: block;margin-bottom: 16px">本软件系统相关说明</a>
              <Divider />
              <p :style="pStyle" ><Badge status="processing"/>基础说明</p>
              <div class="demo-drawer-profile">
                <Row>
                  <Col span="6">
                    软件名称：
                  </Col>
                  <Col span="18">
                    智能门锁管理系统-高校端
                  </Col>
                </Row>
                <Row>
                  <Col span="6">
                    使用人员：
                  </Col>
                  <Col span="18">
                    面向所有购买了此套产品的高校中的宿舍管理人员
                  </Col>
                </Row>
                <Row>
                  <Col span="6">
                    软件功能：
                  </Col>
                  <Col span="18">
                    ①：实时查看门锁状态信息，开锁记录；<br/>
                    ②：管理每个寝室中对应的学生的信息；<br/>
                    ③：远程开锁
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
                    ①：登陆账号为注册时填写的宿管员工工号或手机号，如果忘记登陆密码可在登陆界面点击<a>忘记密码</a>实现密码找回。<br/>
                    ②：遇到其他无法解决的问题时请及时联系后勤管理部门。<br/> 联系电话 <a>0551-63861118</a>
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
      <Layout :style="{padding: '0 20px'}">
        <div :style="activeColor"  @mouseover="MouseOver" @mouseout="MouseOut">
        <Breadcrumb class="bread">
          <Icon type="ios-pin" style="padding: 0 10px"/>
          <BreadcrumbItem v-for="item in $route.matched" :key="item.path" style="margin: 0 10px">
            {{ item.name }}
          </BreadcrumbItem>
        </Breadcrumb>
        </div>
        <Content :style="{padding: '10px 0', background: '#fff'}">
          <Layout>
            <Sider hide-trigger :style="{background: '#515a6e'}">
              <Menu :active-name="menuName" :open-names="[mainName]"  ref="menu" theme="dark" width="auto" accordion>
                <template v-for="(item,index) in $router.options.routes" v-if="!item.hidden">
                  <submenu :name="item.path">
                    <template slot="title" ><Icon :type="item.iconCls"></Icon>{{item.name}}</template>
                    <MenuItem v-for="child in item.children" :to="item.path+'/'+child.path" :name="child.path">{{child.name}}</MenuItem>
                  </submenu>
                </template>
              </Menu>
            </Sider>
            <Content :style="{padding: '0 20px',height: '550px', overflow: 'auto',  background: '#fff'}">
              <Row span="24">
                <transition name="fade" mode="out-in">
                  <router-view></router-view>
                </transition>
              </Row>
            </Content>
          </Layout>
        </Content>
      </Layout>
      <Footer class="layout-footer-center">Copyright &copy; 2018-2019 Kikityer</Footer>
    </Layout>
  </div>
</template>

<script>
    export default {
      inject: ['reload'],
        name: "homepage",
      data () {
        return {
          menuName:'homepage',
          mainName:'/parentFrame',
          value:4,
          value1: false,
          pStyle: {
            fontSize: '16px',
            color: 'rgba(0,0,0,0.85)',
            lineHeight: '24px',
            display: 'block',
            marginBottom: '16px'
          },
          modal2: false,
          activeColor: '',
        }
      },

      /*初始化页面完成后之后执行*/
      mounted(){
        this.getAllUser();
        this.menuName = this.$route.path.split('/')[2];
        this.mainName = '/'+this.$route.path.split('/')[1];
        this.$nextTick(()=>{
          this.$refs.menu.updateOpened();
          this.$refs.menu.updateActiveName();
        })

      },
      methods: {
        getAllUser() {
          this.axios({
            method: 'get',
            url: 'http://localhost:8082/getAll',
          }).then(res => {
            if(res.data.message === "no login"){
              this.$Message.error('未登陆/登陆超时，请登录后访问！');
              this.$router.push({path:'/login'});
            }else {
              console.log(res.data)
            }
          }).catch(function (error) {
            console.log(error)
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
         * 点击登陆日志，路由到登录日志界面
         */
        loginLog:function () {
          this.$router.push({path:'/loginLog'});
        },

        /**
         * 点击主页，重新加载本界面
         */
        backhome: function () {
          this.reload();
        },

        /**
         * 点击帮助，出现对话框，点击知道了按钮重新加载本页面
         */
        iknow: function () {
          this.reload();
        },

        /**
         * 点击更改手机，路由到更改手机号界面
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
        },

        /**
         *导航面包屑鼠标移入事件
         */
        MouseOver: function () {
          this.activeColor = 'background: #dcdee2;';
        },

        /**
         *导航面包屑鼠标移出事件
         * @constructor
         */
        MouseOut :function () {
          this.activeColor = 'background: white';
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
  .bread{
    padding: 13px 0;
    margin: 3px 0;
    background-color: white;
    border: 1px solid #dcdee2;
  }
</style>
