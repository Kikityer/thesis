<template>
   <div>
     <Card class="stepStyle">
       <Steps :current="1" style="position: relative; left: 10%">
         <Step title="填写手机号" icon="ios-phone-portrait"></Step>
         <Step title="验证身份" icon="md-finger-print"></Step>
         <Step title="设置新密码" icon="ios-lock"></Step>
         <Step title="完成" icon="ios-checkmark-circle"></Step>
       </Steps>
     </Card>
     <Card class="idVerify">
       <p slot="title" style="text-align: center; font-size: 16px">验证身份</p>
       <Form
         ref="formValidate"
         :model="formValidate"
         :rules="ruleValidate"
         :label-width="70"
         label-position="right">
         <Form-item label="手机号码">
           <Input
             v-model="formValidate.phoneNum"
             size="large"
             disabled />
         </Form-item>
         <Form-item label="验证码"  prop="verifyCode">
           <Row>
             <Col span="12">
               <Input
                 v-model="formValidate.verifyCode"
                 size="large"
                 placeholder="请输入验证码"
                 @on-enter="nextStep"/>
             </Col>
             <Col span="9" offset="3">
               <Button type="primary" size="large"  :disabled="disabled" @click="sendcode">{{btntxt}}</Button>
             </Col>
           </Row>
         </Form-item>
         <Divider />
         <Form>
           <Form-item>
             <Button
               type="primary"
               size="large"
               long
               @click="nextStep">
               下一步
             </Button>
           </Form-item>
         </Form>
       </Form>
     </Card>
     <Divider style="position: absolute; top: 85%"/>
     <p style="text-align: center;  font-size: 14px; position: absolute; top: 93%;left: 43%">Copyright &copy; 2018-2019 Kikityer</p>
   </div>
</template>

<script>
    export default {
        name: "forgetPassword2",
      data(){
          return{
            formValidate: {
              phoneNum: '',
              verifyCode: ''
            },
            ruleValidate: {
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
      created(){
          this.getPhoneNum()
      },
      methods: {
        getPhoneNum:function () {
          // 取到路由带过来的参数
          const routerParams = this.$route.query.phoneNum;
          // 将数据放在当前组件的数据内
          this.formValidate.phoneNum = routerParams;
        },

        /**
         *点击"获取验证码"按钮事件
         * */
        sendcode:function(){
          let phoneNum = this.formValidate.phoneNum;
          this.time = 60;
          this.disabled = true;
          this.timer();
          this.axios({
            method: 'post',
            url: 'http://localhost:8082/forgetPass/getmsg',
            params: {phoneNum: phoneNum},
          }).then(res => {
            if(res.data.code === 200){
              this.$Message.success('短信发送成功，请注意查收！');
            }
            console.log(res.data);
          }).catch(function (error) {
            console.log(error);
          })
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
         * 点击下一步按钮事件，进入重新设置密码界面
         */
        nextStep: function () {
          this.$refs.formValidate.validate(async valid => {
            if (valid) {  //表单验证通过
              let phoneNum = this.formValidate.phoneNum;
              let verifyCode = this.formValidate.verifyCode;
              this.axios({
                method: 'post',
                url: 'http://localhost:8082/forgetPass/verifyID',
                params: {phoneNum: phoneNum,verifyCode: verifyCode},
              }).then(res => {
                if (res.data.code === 200) {
                  this.$router.push({
                    path:'/forgetPassword3',
                    query: {
                      phoneNum: phoneNum
                    }
                  });
                }else if(res.data.msg === "code expire"){
                  this.$Message.warning('验证码已过期，请重新获取！');
                }else if(res.data.msg === "code not equal"){
                  this.$Message.warning('验证码输入错误！');
                }
              }).catch(function (error) {
                console.log(error);
              })
            }
          })
        }

      }
    }
</script>

<style scoped>
  .stepStyle{
    position: absolute;
    left: 50%;
    top: 10%;
    transform: translate(-50%, -80%);
    width: 60%;
    padding: 0 20px;
  }
  .idVerify{
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -80%);
    width: 420px;
    padding: 0 20px;
  }
</style>
