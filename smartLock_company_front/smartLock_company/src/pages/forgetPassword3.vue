<template>
   <div>
     <Card class="stepStyle">
       <Steps :current="2" style="position: relative; left: 10%">
         <Step title="填写手机号" icon="ios-phone-portrait"></Step>
         <Step title="验证身份" icon="md-finger-print"></Step>
         <Step title="设置新密码" icon="ios-lock"></Step>
         <Step title="完成" icon="ios-checkmark-circle"></Step>
       </Steps>
     </Card>
     <Card class="rePass">
       <p slot="title" style="text-align: center; font-size: 16px">设置新密码</p>
       <Form
         ref="formValidate"
         :model="formValidate"
         :rules="ruleValidate"
         :label-width="70"
         label-position="right">
         <Form-item label="密码" prop="password" >
           <Input
             v-model="formValidate.password"
             size="large"
             type="password"
             placeholder="请输入新密码（不少于六位）"/>
         </Form-item>
         <Form-item label="重输密码" prop="repassword" >
           <Input
             v-model="formValidate.repassword"
             size="large"
             type="password"
             placeholder="请重复输入新密码（不少于六位）"/>
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
        name: "forgetPassword3",
      data(){
          return{
            formValidate: {
              password: '',
              repassword: ''
            },
            ruleValidate: {
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
            phoneNum: ''
          }
      },
      created() {
        this.getPhoneNum()
      },

      methods:{
        getPhoneNum:function () {
          // 取到路由带过来的参数
          const routerParams = this.$route.query.phoneNum;
          // 将数据放在当前组件的数据内
          this.phoneNum = routerParams;
        },
        nextStep: function () {
          this.$refs.formValidate.validate(async valid => {
            if (valid) {  //表单验证通过
              let phoneNum = this.phoneNum;
              let password = this.formValidate.password;
              let repassword = this.formValidate.repassword;
              if (password === repassword) {  //两次密码输入相同
                this.axios({
                  method: 'post',
                  url: 'http://localhost:8082/forgetPass/repass',
                  params: {phoneNum: phoneNum, password: password},
                }).then(res => {
                  if (res.data.code === 200) {
                    this.$router.push({path:'/forgetPassword4'})
                  }
                }).catch(function (error) {
                  console.log(error);
                })
              }
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
  .rePass{
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -80%);
    width: 420px;
    padding: 0 20px;
  }
</style>
