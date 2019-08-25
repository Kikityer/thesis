<template>
    <div class="bgImg">
      <Card class="stepStyle">
        <Steps :current="3" style="position: relative; left: 10%">
          <Step title="填写手机号" icon="ios-phone-portrait"></Step>
          <Step title="验证身份" icon="md-finger-print"></Step>
          <Step title="设置新密码" icon="ios-lock"></Step>
          <Step title="完成" icon="ios-checkmark-circle"></Step>
        </Steps>
      </Card>
      <Card class="sucess">
        <p style="text-align: center; font-size: 26px; color:#2d8cf0; position:absolute;top: 100px ">√ 恭喜您，新密码设置成功！</p>
        <Form>
          <Form-item>
            <Button
              style="position:absolute; top: 200px"
              type="primary"
              size="large"
              long
              @click="toLoginLink">
              立即登陆
            </Button>
          </Form-item>
        </Form>
      </Card>
      <p style="text-align: center;  font-size: 14px; position: absolute; top: 90%;left: 43%; color: white">Copyright &copy; 2018-2019 Kikityer</p>
    </div>

</template>

<script>
    export default {
        name: "forgetPassword4",
      data(){
          return{

          }
      },
      methods: {
        toLoginLink: function () {
          this.$router.push({path:'/login'})
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
  .sucess{
    position: absolute;
    left: 50%;
    top: 53%;
    transform: translate(-50%, -80%);
    width: 420px;
    height: 300px;
    padding: 0 20px;
  }
  .bgImg {
    /*background: #1b6d85;*/
    background: url("../assets/background.png") no-repeat ;
    background-size: cover;
    position: fixed;
    height: 100%;
    width: 100%;
    top: 0;
    left: 0;
  }
</style>
