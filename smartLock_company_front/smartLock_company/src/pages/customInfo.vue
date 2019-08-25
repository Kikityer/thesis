<template>
<section>
  <div style="margin: 0 0 20px 0;background-color: #f5f7f9; height: 50px">
    <Form style="position: absolute; top: 8px;left: 10px">
      <FormItem>
        <Row>
          <Col span="12" >
            <AutoComplete
              v-model="value"
              :data="data1"
              :filter-method="filterMethod"
              placeholder="根据学校名称查找"
              style="width:180px"
              :transfer="false"
              placement="bottom"
              icon="ios-search">
            </AutoComplete>
          </Col>
          <Col span="3" offset="1">
            <Button type="primary" @click="searchSchool">查询</Button>
          </Col>
          <Col span="3" offset="1">
            <Button type="success"  @click="modal1 = true">新增</Button>
          </Col>
          <Col span="3" offset="1">
            <Button type="success"  @click="fresh">刷新</Button>
          </Col>
        </Row>
      </FormItem>
    </Form>
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
    title="新增高校购买信息"
    @on-ok="addOne"
    @on-cancel="cancel">
    <Form
      ref="formValidate"
      :model="formValidate"
      :rules="ruleValidate"
      :label-width="90"
      style="padding: 20px 40px"
      label-position="right">
      <Form-item label="高校名称" prop="schoolName">
        <Input
          v-model="formValidate.schoolName"
          placeholder="请输入高校名称"
          />
      </Form-item>
      <Form-item label="高校地点" prop="schoolAddress">
        <Input
          v-model="formValidate.schoolAddress"
          placeholder="请输入高校地址"
        />
      </Form-item>
      <Form-item label="购买日期" prop="purchaseDate">
        <DatePicker
          type="date"
          placeholder="请选择购买日期"
          style="width: 200px"
          v-model="formValidate.purchaseDate">
        </DatePicker>
      </Form-item>
      <Form-item label="购买批次" prop="purchaseBatch">
        <InputNumber :max="10" :min="1" v-model="formValidate.purchaseBatch"></InputNumber>
      </Form-item>
      <Form-item label="每批次数量" prop="batchAmount">
        <Input
          v-model="formValidate.batchAmount"
          placeholder="请输入各批次数量且用'/'分割，例100/350"
        />
      </Form-item>
      <Form-item label="总数量" prop="allAmount">
        <Input
          v-model="formValidate.allAmount"
          placeholder="请输入购买的总数量"
        />
      </Form-item>
    </Form>
  </Modal>
  <Modal
    v-model="modal2"
    title="编辑高校购买信息"
    @on-ok="changeInfo()">
    <Form
      ref="editForm"
      :model="editForm"
      :rules="editFormRules"
      :label-width="90"
      style="padding: 20px 40px"
      label-position="right">
      <Form-item label="高校名称" prop="schoolName">
        <Input
          v-model="editForm.schoolName"
          placeholder="请输入高校名称"
        />
      </Form-item>
      <Form-item label="高校地点" prop="schoolAddress">
        <Input
          v-model="editForm.schoolAddress"
          placeholder="请输入高校地址"
        />
      </Form-item>
      <Form-item label="购买日期" prop="purchaseDate">
        <DatePicker
          type="date"
          placeholder="请选择购买日期"
          style="width: 200px"
          v-model="editForm.purchaseDate">
        </DatePicker>
      </Form-item>
      <Form-item label="购买批次" prop="purchaseBatch">
        <InputNumber :max="10" :min="1" v-model="editForm.purchaseBatch"></InputNumber>
      </Form-item>
      <Form-item label="每批次数量" prop="batchAmount">
        <Input
          v-model="editForm.batchAmount"
          placeholder="请输入各批次数量且用'/'分割，例100/350"
        />
      </Form-item>
      <Form-item label="总数量" prop="allAmount">
        <Input
          v-model="editForm.allAmount"
          placeholder="请输入购买的总数量"
        />
      </Form-item>
    </Form>
  </Modal>
</section>
</template>

<script>
    export default {
      inject: ['reload'],
        name: "custom",
      data(){
          return{
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
                title: '学校名称',
                key: 'schoolName',
                align: 'center'
              },
              {
                title: '学校地址',
                key: 'schoolAddress',
                align: 'center'
              },
              {
                title: '购买日期',
                key: 'purchaseDate',
                align: 'center'
              },
              {
                title: '购买批次',
                key: 'purchaseBatch',
                align: 'center'
              },
              {
                title: '各批次数量（套）',
                key: 'batchAmount',
                align: 'center'
              },
              {
                title: '总数量（套）',
                key: 'allAmount',
                align: 'center'
              },
              {
                title: '操作',
                slot: 'action',
                width: 150,
                align: 'center',
              }
            ],
            data: [],  //表格拿到的将要展示的数据（只有某一页的数据量）
            allDataNum: 0,  //所有数据的个数
            value: '',
            data1: [],  //搜索框自动完成返回的数据
            flag: false,
            sels: [],//列表选中列
            modal1: false,
            formValidate: {
              schoolName: '',
              schoolAddress: '',
              purchaseDate: '',
              purchaseBatch: 1,
              batchAmount: '',
              allAmount: ''
            },
            ruleValidate: {
              schoolName: [
                {
                  required: true,
                  message: '学校名称不能为空',
                }
              ],
              schoolAddress: [
                {
                  required: true,
                  message: '学校地址不能为空',
                }
              ],
              purchaseDate: [
                {
                  required: true,
                  message: '购买日期不能为空',
                }
              ],
              purchaseBatch: [
                {
                  required: true,
                  message: '购买批次不能为空',
                }
              ],
              batchAmount: [
                {
                  required: true,
                  message: '每批次购买数量不能为空',
                }
              ],
              allAmount: [
                {
                  required: true,
                  message: '购买总量不能为空',
                }
              ],
            },
            modal2: false,
            editForm: {
              id: 0,
              schoolName: '',
              schoolAddress: '',
              purchaseDate: '',
              purchaseBatch: 1,
              batchAmount: '',
              allAmount: ''
            },
            editFormRules: {
              schoolName: [
                {
                  required: true,
                  message: '学校名称不能为空',
                }
              ],
              schoolAddress: [
                {
                  required: true,
                  message: '学校地址不能为空',
                }
              ],
              purchaseDate: [
                {
                  required: true,
                  message: '购买日期不能为空',
                }
              ],
              purchaseBatch: [
                {
                  required: true,
                  message: '购买批次不能为空',
                }
              ],
              batchAmount: [
                {
                  required: true,
                  message: '每批次购买数量不能为空',
                }
              ],
              allAmount: [
                {
                  required: true,
                  message: '购买总量不能为空',
                }
              ],
            },
            id: 0,   //单行删除时，存放被选择行的id
            ids: [], //批量删除时，存放所有被选择行的id
            pageSize: 10,
            currentPageNum: 1,
          }
      },

      created(){
          this.getSchoolName();
      },

      mounted(){
       this.getSchoolInfo();
    },

      methods:{

        /**
         * 获取所有的表格中的客户信息
         */
        getSchoolInfo: function () {
          let pageSize = this.pageSize;
          let currentPageNum = this.currentPageNum;
          this.axios({
            method: 'post',
            url: 'http://localhost:8082/customerInfo/getSchoolInfo',
            params: {pageSize: pageSize, currentPageNum:currentPageNum}
          }).then(res => {
            console.log(res.data);
            for(let i = 0;i< res.data.content.length;i++){
              let d = new Date(res.data.content[i].purchaseDate);
              res.data.content[i].purchaseDate= d.getFullYear() + '-' + (d.getMonth() + 1 < 10 ? "0" + (d.getMonth() + 1):d.getMonth() + 1) + '-' + (d.getDate()< 10 ? "0" + d.getDate():d.getDate())
              // res.data[i].purchaseDate = new Date(+new Date(new Date(res.data[i].purchaseDate).toJSON()) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
            }
            this.data = res.data.content;
            this.allDataNum = res.data.totalElements;
          }).catch(function (error) {
            console.log(error);
          })
        },

        /**
         * 自动完成搜索框 在渲染前拿到所有学校的名字
         */
        getSchoolName: function () {
          this.axios({
            method: 'post',
            url: 'http://localhost:8082/customerInfo/getSchoolName',
          }).then(res => {
            this.data1 = res.data;
          }).catch(function (error) {
            console.log(error);
          })
        },
        /**
         * 根据输入的名字给出建议
         * @param value
         * @param option
         * @returns {boolean}
         */
        filterMethod (value, option) {
          return option.indexOf(value) !== -1;
        },

        /**
         * 点击查询按钮
         */
        searchSchool: function () {
          let value = this.value;
          if(value === ''){
            this.$Message.info('请输入学校名称后再查找');
          }else{
            this.axios({
              method: 'post',
              url: 'http://localhost:8082/customerInfo/findBySchoolName',
              params:{schoolName:value}
            }).then(res => {
             if(res.data.message === 'no name'){
               this.data = [];
               this.value = ''
             }else{
               for(let i = 0;i< res.data.length;i++){
                 let d = new Date(res.data[i].purchaseDate);
                 res.data[i].purchaseDate= d.getFullYear() + '-' + (d.getMonth() + 1 < 10 ? "0" + (d.getMonth() + 1):d.getMonth() + 1) + '-' + (d.getDate()< 10 ? "0" + d.getDate():d.getDate())
               }
               this.data = res.data;
               this.value = '';
               this.allDataNum = 1;
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
          let schoolName = this.formValidate.schoolName;
          let schoolAddress = this.formValidate.schoolAddress;
          let purchaseDate =this.formValidate.purchaseDate.toString();
          let purchaseBatch = this.formValidate.purchaseBatch.toString();
          let batchAmount = this.formValidate.batchAmount;
          let allAmount = this.formValidate.allAmount;
          this.$refs.formValidate.validate(async valid => {
            if (valid) {  //表单验证通过
              this.axios({
                method: 'post',
                url: 'http://localhost:8082/customerInfo/addCustomer',
                params:{schoolName:schoolName,schoolAddress:schoolAddress,purchaseDate:purchaseDate,purchaseBatch:purchaseBatch,batchAmount:batchAmount,allAmount:allAmount}
              }).then(res => {
                if (res.data.code ===200){
                  this.$Message.success('新增客户信息成功');
                  this.reload();
                }else {
                  this.$Message.error('新增客户信息失败');
                }
              }).catch(function (error) {
                console.log(error);
              })
            }
          })
        },
        cancel() {
          if ( this.formValidate.schoolName !== '' || this.formValidate.schoolAddress !== '' || this.formValidate.purchaseDate !== ''||
          this.formValidate.batchAmount !== '' || this.formValidate.allAmount !==''){
            this.reload();
          }
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
          let schoolName = this.editForm.schoolName;
          let schoolAddress = this.editForm.schoolAddress;
          let purchaseDate =this.editForm.purchaseDate.toString();
          let purchaseBatch = this.editForm.purchaseBatch.toString();
          let batchAmount = this.editForm.batchAmount;
          let allAmount = this.editForm.allAmount;
          this.$refs.editForm.validate(async valid => {
            if (valid) {  //表单验证通过
              this.axios({
                method: 'post',
                url: 'http://localhost:8082/customerInfo/editCustomer',
                params:{id:id,schoolName:schoolName,schoolAddress:schoolAddress,purchaseDate:purchaseDate,purchaseBatch:purchaseBatch,batchAmount:batchAmount,allAmount:allAmount}
              }).then(res => {
                if (res.data.code ===200){
                  this.$Message.success('编辑客户信息成功');
                  this.reload();
                }else {
                  this.$Message.error('新增客户信息失败');
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
                 url: 'http://localhost:8083/building/deleteOne',
                 params: {id: id}
               }).then(res => {
                 if(res.data.code === 200){
                   this.$Message.success('删除客户信息成功');
                   this.reload();
                 }else{
                   this.$Message.error('删除客户信息失败');
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
                url: 'http://localhost:8082/customerInfo/deleteMany',
                params: {ids:ids}
              }).then(res => {
                if(res.data.code === 200){
                  this.$Message.success('批量删除客户信息成功');
                  this.reload();
                }else{
                  this.$Message.error('批量除客户信息失败');
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
            url: 'http://localhost:8082/customerInfo/getSchoolInfo',
            params: {pageSize: pageSize, currentPageNum:currentPageNum}
          }).then(res => {
            console.log(res.data)
            for(let i = 0;i< res.data.content.length;i++){
              let d = new Date(res.data.content[i].purchaseDate);
              res.data.content[i].purchaseDate= d.getFullYear() + '-' + (d.getMonth() + 1 < 10 ? "0" + (d.getMonth() + 1):d.getMonth() + 1) + '-' + (d.getDate()< 10 ? "0" + d.getDate():d.getDate())
              // res.data[i].purchaseDate = new Date(+new Date(new Date(res.data[i].purchaseDate).toJSON()) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
            }
            this.data = res.data.content;
            this.allDataNum = res.data.totalElements;
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
            url: 'http://localhost:8082/customerInfo/getSchoolInfo',
            params: {pageSize: pageSize, currentPageNum:currentPageNum1}
          }).then(res => {
            console.log(res.data)
            for(let i = 0;i< res.data.content.length;i++){
              let d = new Date(res.data.content[i].purchaseDate);
              res.data.content[i].purchaseDate= d.getFullYear() + '-' + (d.getMonth() + 1 < 10 ? "0" + (d.getMonth() + 1):d.getMonth() + 1) + '-' + (d.getDate()< 10 ? "0" + d.getDate():d.getDate())
              // res.data[i].purchaseDate = new Date(+new Date(new Date(res.data[i].purchaseDate).toJSON()) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
            }
            this.data = res.data.content;
            this.allDataNum = res.data.totalElements;
          }).catch(function (error) {
            console.log(error);
          })
        }


      }
    }
</script>

<style scoped>

</style>
