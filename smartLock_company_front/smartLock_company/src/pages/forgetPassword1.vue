<template>
  <div>
    <Card class="stepStyle">
      <Steps :current="0" style="position: relative; left: 10%">
        <Step title="填写手机号" icon="ios-phone-portrait"></Step>
        <Step title="验证身份" icon="md-finger-print"></Step>
        <Step title="设置新密码" icon="ios-lock"></Step>
        <Step title="完成" icon="ios-checkmark-circle"></Step>
      </Steps>
    </Card>
    <Card class="phoneVerify">
      <p slot="title" style="text-align: center; font-size: 16px">填写手机号</p>
      <Form
        ref="formValidate"
        :model="formValidate"
        :rules="ruleValidate"
        :label-width="70"
        label-position="right">
        <Form-item label="手机号码" prop="phoneNum">
          <Input
            v-model="formValidate.phoneNum"
            size="large"
            placeholder="请输入注册时用的手机号"
            @on-enter="nextStep"/>
        </Form-item>
        <Form-item label="验证码" prop="captchaCode">
          <Row>
            <Col span="14">
              <Input
                v-model="formValidate.captchaCode"
                size="large"
                placeholder="请输入验证码"
                @on-enter="nextStep"/>
            </Col>
            <Col span="9" offset="1">
              <img id="imgIdentifyingCode"/>
            </Col>
          </Row>
        </Form-item>
        <Form-item>
          <Row>
            <Col span="9">
              <Button
                type="primary"
                size="large"
                @click="clickVerification">
                获取验证码
              </Button>
            </Col>
            <Col span="8" offset="7">
              <a @click="clickVerification">看不清，换一张</a>
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
        name: "forgetPassword1",
      data(){
          return{
            formValidate: {
              phoneNum: '',
              captchaCode: '',
            },
            ruleValidate: {
              phoneNum: [
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
              captchaCode: [
                {
                  required: true,
                  message: '验证码不能为空',
                }
              ]
            },
          }
      },
      methods: {
        /**
         * 获取验证码（"获取验证码按钮"和"看不清，换一张"）
         */
        clickVerification:function () {
          let identifyCodeSrc = "http://localhost:8082/img/getKaptchaImage?" + Math.random();
          let objs = document.getElementById("imgIdentifyingCode");
          objs.src = identifyCodeSrc;
        },

        /**
         * 下一步按钮点击事件,进入到身份验证
         */
        nextStep: function () {
          this.$refs.formValidate.validate(async valid => {
            if (valid) {  //表单验证通过
              let phoneNum = this.formValidate.phoneNum;
              let captchaCode = this.formValidate.captchaCode;
              this.axios({
                method: 'post',
                url: 'http://localhost:8082/forgetPass/kaptchaVerify',
                params: {phoneNum: phoneNum,captchaCode: captchaCode},
              }).then(res => {
                if (res.data.code === 200) {
                  this.$router.push({
                    path:'/forgetPassword2',
                    query: {
                      phoneNum: phoneNum
                    }
                  });
                }else if (res.data.msg === "wrong code") {
                  this.$Message.warning('验证码有误！');
                }else if(res.data.msg === "unregister phoneNum"){
                  this.$Message.warning('未查询到此号码，请检查！');
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
.phoneVerify{
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -80%);
  width: 420px;
  padding: 0 20px;
}
</style>
