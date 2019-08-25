<template>
    <section>
      <div style="margin: 0 0 20px 0;background-color: #f5f7f9; height: 50px">
        <div style="position: relative;top: 10px;left:10px; max-height: 80px">
          <Input prefix="ios-search" placeholder="请输入宿管名字" style="width: auto" v-model="value" @on-enter="searchManagerName"/>
          <Button type="primary" @click="searchManagerName">查询</Button>
          <Input prefix="ios-search" placeholder="请输入宿管工号" style="width: auto" v-model="value1" @on-enter="searchManagerNum"/>
          <Button type="primary" @click="searchManagerNum">查询</Button>
          <Button type="success"  @click="modal1 = true">新增</Button>
          <Button type="success"  @click="fresh">刷新</Button>
        </div>
      </div>
      <Table border ref="selection"  :columns="columns" :data="data" @on-selection-change="selsChange" stripe max-height = 380>
        <template slot-scope="{ row, index }" slot="action">
          <Button type="primary" size="small" style="margin-right: 5px" @click="handleEdit(index, row)">编辑</Button>
          <Button type="error" size="small" @click="removeOne(index,row)">删除</Button>
        </template>
      </Table>
      <div style="margin: 20px 0 0 0; background-color: #f5f7f9; height: 50px">
        <Form style="position: relative; top: 8px;left: 10px">
          <Row>
            <Col span="3">
              <FormItem>
                <Button type="error" size="small" v-model="flag" :disabled= flag?false:true @click="removeAll">批量删除</Button>
              </FormItem>
            </Col>
            <Col span="9" offset="12">
              <FormItem>
                <Page :total="allDataNum"
                      :current="currentPageNum"
                      size="small"
                      show-elevator
                      show-sizer
                      show-total
                      :page-size="pageSize"
                      @on-change="changePage"
                      @on-page-size-change="changeSize"></Page>
              </FormItem>
            </Col>
          </Row>
        </Form>
      </div>
      <Modal
        v-model="modal1"
        title="新增宿管信息"
        @on-ok="addOne"
        @on-cancel="cancel">
        <Form
          ref="formValidate"
          :model="formValidate"
          :rules="ruleValidate"
          :label-width="90"
          style="padding: 0 40px"
          label-position="right">
          <Form-item label="宿管名字" prop="name">
            <Input
              v-model="formValidate.name"
              placeholder="请输入宿管的名字"
            />
          </Form-item>
          <Form-item label="宿管年龄" prop="age">
            <Input
              v-model="formValidate.age"
              placeholder="请输入宿管的年龄"
            />
          </Form-item>
          <Form-item label="宿管性别" prop="sexual">
            <Select v-model="formValidate.sexual" style="width:200px">
              <Option v-for="item in sexual" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </Form-item>
          <Form-item label="宿管工号" prop="jobNum">
            <Input
              v-model="formValidate.jobNum"
              placeholder="请输入宿管的工号"
            />
          </Form-item>
          <Form-item label="工号密码" prop="jobNumPass">
            <Input
              v-model="formValidate.jobNumPass"
              type="password"
              placeholder="请输入宿管工号对应的密码"
            />
          </Form-item>
          <Form-item label="宿管电话" prop="phoneNum">
            <Input
              v-model="formValidate.phoneNum"
              placeholder="请输入宿管的电话"
            />
          </Form-item>
          <Form-item label="管辖范围" prop="buildingName">
            <Input
              v-model="formValidate.buildingName"
              placeholder="请输入宿管管辖的楼宇名称"
            />
          </Form-item>
          <Form-item label="权限角色" prop="role">
            <Select v-model="formValidate.role" style="width:200px">
              <Option v-for="item in role" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </Form-item>
          <Form-item label="是否已注册" prop="registered">
            <Select v-model="formValidate.registered" style="width:200px">
              <Option v-for="item in registered" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </Form-item>
        </Form>
      </Modal>
      <Modal
        v-model="modal2"
        title="编辑宿管信息"
        @on-ok="changeInfo()">
        <Form
          ref="editForm"
          :model="editForm"
          :rules="editFormRules"
          :label-width="90"
          style="padding: 0 40px"
          label-position="right">
          <Form-item label="宿管名字" prop="name">
            <Input
              v-model="editForm.name"
              placeholder="请输入宿管的名字"
            />
          </Form-item>
          <Form-item label="宿管年龄" prop="age">
            <Input
              v-model="editForm.age"
              placeholder="请输入宿管的年龄"
            />
          </Form-item>
          <Form-item label="宿管性别" prop="sexual">
            <Select v-model="editForm.sexual" style="width:200px">
              <Option v-for="item in sexual" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </Form-item>
          <Form-item label="宿管工号" prop="jobNum">
            <Input
              v-model="editForm.jobNum"
              placeholder="请输入宿管的工号"
            />
          </Form-item>
          <Form-item label="宿管电话" prop="phoneNum">
            <Input
              v-model="editForm.phoneNum"
              placeholder="请输入宿管的电话"
            />
          </Form-item>
          <Form-item label="管辖范围" prop="buildingName">
            <Input
              v-model="editForm.buildingName"
              placeholder="请输入宿管管辖的楼宇名称"
            />
          </Form-item>
          <Form-item label="权限角色" prop="role">
            <Select v-model="editForm.role" style="width:200px">
              <Option v-for="item in role" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </Form-item>
          <Form-item label="是否已注册" prop="registered">
            <Select v-model="editForm.registered" style="width:200px" disabled>
              <Option v-for="item in registered" :value="item.value" :key="item.value">{{ item.label }}</Option>
            </Select>
          </Form-item>
        </Form>
      </Modal>
    </section>
</template>

<script>
    export default {
      inject: ['reload'],
        name: "dormManagerInfo",
      data(){
        return {
          columns: [
            {
              type: 'selection',
              width: 60,
              align: 'center'
            },
            {
              title: '编号',
              type: 'index',
              width: 80,
              align: 'center'
            },
            {
              title: '宿管姓名',
              key: 'name',
              align: 'center'
            },
            {
              title: '宿管年龄',
              key: 'age',
              align: 'center'
            },
            {
              title: '宿管性别',
              key: 'sexual',
              align: 'center'
            },
            {
              title: '宿管工号',
              key: 'jobNum',
              align: 'center'
            },
            {
              title: '宿管电话',
              key: 'phoneNum',
              align: 'center'
            },
            {
              title: '管辖范围',
              key: 'buildingName',
              align: 'center'
            },
            {
              title: '权限角色',
              key: 'role',
              align: 'center'
            },
            {
              title: '是否注册',
              key: 'registered',
              align: 'center'
            },
            {
              title: '操作',
              slot: 'action',
              width: 150,
              align: 'center',
            }
          ],
          formValidate: {
            name: '',
            age: '',
            sexual: '',
            jobNum: '',
            jobNumPass: '',
            phoneNum: '',
            buildingName: '',
            role: '',
            registered: ''
          },
          ruleValidate: {
            name: [
              {
                required: true,
                message: '宿管名字不能为空',
              }
            ],
            age: [
              {
                required: true,
                message: '宿管年龄不能为空',
              }
            ],
            sexual: [
              {
                required: true,
                message: '宿管性别不能为空',
              }
            ],
            jobNum: [
              {
                required: true,
                message: '宿管工号不能为空',
              }
            ],
            jobNumPass: [
              {
                required: true,
                message: '宿管工号对应的密码不能为空',
              }
            ],
            phoneNum: [
              {
                required: true,
                message: '宿管手机号不能为空',
              }
            ],
            buildingName: [
              {
                required: true,
                message: '管辖的楼宇名称不能为空',
              }
            ],
            role: [
              {
                required: true,
                message: '角色信息不能为空',
              }
            ],
            registered: [
              {
                required: true,
                message: '是否注册情况不能为空',
              }
            ],
          },
          editForm: {
            id: 0,
            name: '',
            age: '',
            sexual: '',
            jobNum: '',
            phoneNum: '',
            buildingName: '',
            role: '',
            registered: ''
          },
          editFormRules: {
            name: [
              {
                required: true,
                message: '宿管名字不能为空',
              }
            ],
            age: [
              {
                required: true,
                message: '宿管年龄不能为空',
              }
            ],
            sexual: [
              {
                required: true,
                message: '宿管性别不能为空',
              }
            ],
            jobNum: [
              {
                required: true,
                message: '宿管工号不能为空',
              }
            ],
            phoneNum: [
              {
                required: true,
                message: '宿管手机号不能为空',
              }
            ],
            buildingName: [
              {
                required: true,
                message: '管辖的楼宇名称不能为空',
              }
            ],
            role: [
              {
                required: true,
                message: '角色信息不能为空',
              }
            ],
            registered: [
              {
                required: true,
                message: '是否注册情况不能为空',
              }
            ],
          },
          data: [],  //表格拿到的将要展示的数据（只有某一页的数据量）
          allDataNum: 0,  //所有数据的个数
          value: '', //根据宿管名字绑定的值
          value1:'', //根据宿管工号绑定的值
          flag: false,
          sels: [],//列表选中列
          modal1: false,
          modal2: false,
          id: 0,   //单行删除时，存放被选择行的id
          ids: [], //批量删除时，存放所有被选择行的id
          pageSize: 10,
          currentPageNum: 1,
          sexual: [
            {
              value: '男',
              label: '男'
            },
            {
              value: '女',
              label: '女'
            }
          ],
          role: [
            {
              value: '超级管理员',
              label: '超级管理员'
            },
            {
              value: '管理员',
              label: '管理员'
            }
          ],
          registered: [
            {
              value: '否',
              label: '否'
            }
          ]
        }
      },
      mounted(){
        this.getManagerInfo();
      },
      methods:{
        /**
         * 获取表格中的宿管信息
         */
        getManagerInfo: function () {
          let pageSize = this.pageSize;
          let currentPageNum = this.currentPageNum;
          this.axios({
            method: 'post',
            url: 'http://localhost:8083/dormManager/getManagerInfo',
            params: {pageSize: pageSize, currentPageNum:currentPageNum}
          }).then(res => {
            console.log(res.data);
            this.data = res.data.content;
            this.allDataNum = res.data.totalElements[0];
          }).catch(function (error) {
            console.log(error);
          })
        },

        /**
         * 根据宿管的名字查找
         */
        searchManagerName: function () {
          let value = this.value;
          if(value === ''){
            this.$Message.info('请输入宿管名字后再查询');
          }else{
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/dormManager/findByManagerName',
              params:{managerName:value}
            }).then(res => {
              if(res.data.message === 'no name'){
                this.data = [];
                this.value = '';
                this.$Message.error('没有此员工');
              }else{
                this.data = res.data;
                this.value = '';
                this.allDataNum = res.data.length;
                console.log( res.data.length)
              }
            }).catch(function (error) {
              console.log(error);
            })
          }
        },

        /**
         * 根据宿管的工号查找
         */
        searchManagerNum: function () {
          let value = this.value1;
          if(value === ''){
            this.$Message.info('请输入宿管工号后再查询');
          }else{
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/dormManager/findByManagerJobNum',
              params:{managerJobNum:value}
            }).then(res => {
              if(res.data.message === 'no number'){
                this.data = [];
                this.value = '';
                this.$Message.error('没有此员工');
              }else{
                this.data = res.data;
                this.value = '';
                this.allDataNum = res.data.length;
                console.log( res.data.length)
              }
            }).catch(function (error) {
              console.log(error);
            })
          }
        },

        /**
         * 新增按钮弹出对话框中的确定和取消按钮
         */
        addOne () {
          let name = this.formValidate.name;
          let age = this.formValidate.age;
          let sexual = this.formValidate.sexual;
          let jobNum = this.formValidate.jobNum;
          let jobNumPass = this.formValidate.jobNumPass;
          let phoneNum = this.formValidate.phoneNum;
          let buildingName =this.formValidate.buildingName;
          let role = this.formValidate.role;
          let registered = this.formValidate.registered;
          this.$refs.formValidate.validate(async valid => {
            if (valid) {  //表单验证通过
              this.axios({
                method: 'post',
                url: 'http://localhost:8083/dormManager/addManagerInfo',
                params:{name:name,age:age,sexual:sexual,jobNum:jobNum,jobNumPass:jobNumPass,phoneNum:phoneNum,buildingName:buildingName,role:role,registered:registered}
              }).then(res => {
                if (res.data.code ===200){
                  this.$Message.success('新增宿管信息成功');
                  this.reload();
                }else {
                  this.$Message.error('新增宿管信息失败');
                }
              }).catch(function (error) {
                console.log(error);
              })
            }
          })
        },
        cancel() {
            this.reload();
        },

        /**
         * 点击刷新按钮
         */
        fresh: function(){
          this.reload();
        },

        /**
         * 编辑按钮弹出对话框的确定和取消按钮
         */
        handleEdit(index, row){
          this.modal2 = true;
          this.editForm = Object.assign({}, row);
          this.editForm.purchaseBatch = parseInt(row.purchaseBatch);
          this.id = row.id;
        },
        /**
         * 点击编辑对话框的确定按钮
         */
        changeInfo(){
          let id = this.id;
          let name = this.editForm.name;
          let age = this.editForm.age;
          let sexual = this.editForm.sexual;
          let jobNum = this.editForm.jobNum;
          let phoneNum = this.editForm.phoneNum;
          let buildingName =this.editForm.buildingName;
          let role = this.editForm.role;
          this.$refs.editForm.validate(async valid => {
            if (valid) {  //表单验证通过
              this.axios({
                method: 'post',
                url: 'http://localhost:8083/dormManager/editManagerInfo',
                params:{id:id,name:name,age:age,sexual:sexual,jobNum:jobNum,phoneNum:phoneNum,buildingName:buildingName,role:role}
              }).then(res => {
                if (res.data.code ===200){
                  this.$Message.success('编辑宿管信息成功');
                  this.reload();
                }else {
                  this.$Message.error('编辑宿管信息失败');
                }
              }).catch(function (error) {
                console.log(error);
              })
            }
          })
        },

        /**
         * 单行删除操作
         */
        removeOne(index,row){
          this.$Modal.confirm({
            title: '提示',
            content: '是否确认删除此条记录？',
            onOk: () => {
              let id = row.id;
              this.axios({
                method: 'post',
                url: 'http://localhost:8083/dormManager/deleteOne',
                params: {id: id}
              }).then(res => {
                if(res.data.code === 200){
                  this.$Message.success('删除宿管信息成功');
                  this.reload();
                }else{
                  this.$Message.error('删除宿管信息失败');
                }
              }).catch(function (error) {
                console.log(error)
              })
            },
          })
        },

        /**
         * 选中项发生变化时触发
         * @param sels
         */
        selsChange: function (sels) {
          this.sels = sels;
          if(this.sels.length !== 0){
            this.flag = true;
          }else {
            this.flag = false;
          }
        },

        /**
         * 批量删除操作
         */
        removeAll: function () {
          this.$Modal.confirm({
            title: '提示',
            content: '是否确认删除多条记录？',
            onOk: () => {
              let ids = this.sels.map(item => item.id).toString();
              this.axios({
                method: 'post',
                url: 'http://localhost:8083/dormManager/deleteMany',
                params: {ids:ids}
              }).then(res => {
                if(res.data.code === 200){
                  this.$Message.success('批量删除宿管信息成功');
                  this.reload();
                }else{
                  this.$Message.error('批量除宿管信息失败');
                }
              }).catch(function (error) {
                console.log(error)
              })
            },
          })
        },

        /**
         * 改变每页显示条数
         */
        changeSize: function (size) {
          this.pageSize = size;
          console.log(this.pageSize);
          let pageSize = this.pageSize;
          let currentPageNum = this.currentPageNum;
          this.axios({
            method: 'post',
            url: 'http://localhost:8083/dormManager/getManagerInfo',
            params: {pageSize: pageSize, currentPageNum:currentPageNum}
          }).then(res => {
            console.log(res.data);
            this.data = res.data.content;
            this.allDataNum = res.data.totalElements[0];
          }).catch(function (error) {
            console.log(error);
          })
        },

        /**
         * 改变页数
         */
        changePage: function (currentPageNum) {
          this.currentPageNum = currentPageNum;
          console.log(this.currentPageNum);
          let pageSize = this.pageSize;
          let currentPageNum1 = this.currentPageNum;
          this.axios({
            method: 'post',
            url: 'http://localhost:8083/dormManager/getManagerInfo',
            params: {pageSize: pageSize, currentPageNum:currentPageNum1}
          }).then(res => {
            console.log(res.data);
            this.data = res.data.content;
            this.allDataNum = res.data.totalElements[0];
          }).catch(function (error) {
            console.log(error);
          })
        },
      }
    }
</script>

<style scoped>

</style>
