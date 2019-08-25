<template>
  <div>
    <Card class="p-register">
      <p slot="title" style="text-align: center; font-size: 16px">新用户注册</p>
      <Form
        ref="formValidate"
        :model="formValidate"
        :rules="ruleValidate"
        :label-width="70"
        label-position="right">
        <Form-item label="用户名称" prop="username">
          <Input
            v-model="formValidate.username"
            size="large"
            placeholder="请输入用户名"
            @on-enter="subRegister"/>
        </Form-item>
        <Form-item label="手机号码" prop="phoneNum">
          <Row>
            <Col span="9">
              <Select v-model="value1">
                <Option :value="selectList.value">{{selectList.label}}</Option>
              </Select>
            </Col>
            <Col span="14" offset="1">
              <Input
                v-model="formValidate.phoneNum"
                size="large"
                placeholder="请输入手机号码"
                @on-enter="subRegister"/>
            </Col>
          </Row>
        </Form-item>
        <Form-item label="验证码"  prop="verifyCode">
          <Row>
            <Col span="12">
              <Input
                v-model="formValidate.verifyCode"
                size="large"
                placeholder="请输入验证码"
                @on-enter="subRegister"/>
            </Col>
            <Col span="11" offset="1">
              <Button type="primary" size="large"  :disabled="disabled" @click="sendcode">{{btntxt}}</Button>
            </Col>
          </Row>
        </Form-item>
        <Form-item label="密码" prop="password" >
          <Input
            v-model="formValidate.password"
            size="large"
            type="password"
            placeholder="请输入密码（不少于六位）"
            @on-enter="subRegister"/>
        </Form-item>
        <Form-item label="重输密码" prop="repassword" >
          <Input
            v-model="formValidate.repassword"
            size="large"
            type="password"
            placeholder="请重复输入密码（不少于六位）"
            @on-enter="subRegister"/>
        </Form-item>
      </Form>
      <Form>
        <Form-item>
          <Button
            type="primary"
            size="large"
            long
            @click="subRegister">
            立即注册
          </Button>
        </Form-item>
      </Form>
    </Card>
    <Divider style="position: absolute; top: 85%"/>
    <p style="text-align: center;  font-size: 14px; position: absolute; top: 93%;left: 43%">Copyright &copy; 2018-2019 Kikityer</p>
  </div>

</template>

<script>
    export default {
        name: "register",
      data(){
          return{
            formValidate: {
              username: '',
              phoneNum: '',
              verifyCode: '',
              password: '',
              repassword: ''
            },
            ruleValidate: {
              username: [
                {
                  required: true,
                  message: '用户名不能为空',
                }
              ],
              phoneNum:[
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
                }
              ],
              password: [
                {
                  required: true,
                  message: '密码不能为空'
                },
                {
                  required: true,
                  type:'string',
                  min: 6,
                  message: '密码不能少于6位',
                  trigger: 'blur'
                }
              ],
              repassword: [
                {
                  required: true,
                  message: '重输密码不能为空'
                },
                {
                  required: true,
                  type:'string',
                  min: 6,
                  message: '密码不能少于6位',
                  trigger: 'blur'
                }
              ],
            },
            selectList: {
              value: '1',
              label: '+ 86'
            },
            value1: '1',
            btntxt:"获取验证码",
            disabled:false,
            time:0,
          }
      },
      //获取手机验证码
      methods:{

          /**
           *点击"获取验证码"按钮事件
           * */
        sendcode:function(){
          let phoneNum = this.formValidate.phoneNum;
          if (phoneNum === ''){ //手机号输入框为空
            this.$Message.warning('请输入手机号码后再试！');
          }else if (phoneNum.length === 11){  //十一位手机号 发起axios请求
            this.axios({
              method: 'post',
              url: 'http://localhost:8082/register/number',
              params: {phoneNum: phoneNum},
            }).then(res => {
              if(res.data.msg === "phoneNum used"){
                this.$Message.warning('此手机号已经注册，请直接登陆！');
              }else if (res.data.code === 200){
                this.$Message.success('短信发送成功，请注意查收！');
                this.time=60;
                this.disabled=true;
                this.timer();
              }
            }).catch(function (error) {
              console.log(error);
            })
          }
        },
        timer:function () {
          if (this.time > 0) {
            this.time--;
            this.btntxt=this.time+"s后重获";
            setTimeout(this.timer, 1000);
          } else{
            this.time=0;
            this.btntxt="获取验证码";
            this.disabled=false;
          }
        },

        /**
         * 点击”立即注册“按钮事件
         */
        subRegister:function () {
          this.$refs.formValidate.validate(async valid => {
            if (valid) {  //表单验证通过
              let username = this.formValidate.username;
              let phoneNum = this.formValidate.phoneNum;
              let verifyCode = this.formValidate.verifyCode;
              let password = this.formValidate.password;
              let repassword = this.formValidate.repassword;
              if (password === repassword){  //两次密码输入相同
                this.$Spin.show();
                this.axios({
                  method: 'post',
                  url: 'http://localhost:8082/register/user',
                  params: {username: username,phoneNum: phoneNum,verifyCode: verifyCode,password: password},
                }).then(res => {
                  this.$Spin.hide();
                  if (res.data.code === 200) {
                    this.$Message.success('注册成功！');
                    this.$router.push({path:'/login'});
                  }else if (res.data.msg === "username used"){
                    this.$Message.warning('此用户名已被使用，请更换！');
                  }else if(res.data.msg === "code expire"){
                    this.$Message.warning('验证码已过期，请重新获取！');
                  }else if(res.data.msg === "code not equal"){
                    this.$Message.warning('验证码输入错误！');
                  }
                  console.log(res.data);
                }).catch(function (error) {
                  console.log(error);
                })
              }else {
                this.$Message.warning('两次密码不一致，请重新输入！');
              }
            }
          })
        },
      }

    }
</script>

<style scoped>
  .p-register {
    position: absolute !important;
    left: 50%;
    top: 55%;
    transform: translate(-50%, -80%);
    background-color: white;
    width: 340px;
    padding: 0 7px;
  }
</style>
