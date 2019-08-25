<template>
   <section>
     <div style="margin: 0 0 20px 0;background-color: #f5f7f9; height: 50px">
       <div style="position: relative;top: 10px;left:10px; max-height: 80px">
         <Input prefix="ios-search" clearable placeholder="请输入学生姓名" style="width: 10%" v-model="value" @on-enter="searchStudentName"/>
         <Button type="primary" @click="searchStudentName">查询</Button>
         <Input prefix="ios-search" clearable placeholder="请输入学生学号" style="width: 10%" v-model="value1"@on-enter="searchStudentNum"/>
         <Button type="primary" @click="searchStudentNum">查询</Button>
         <AutoComplete
           v-model="value2"
           :data="data1"
           clearable
           :filter-method="filterMethod"
           placeholder="请选择楼栋"
           style="width:10%"
           :transfer="true"
           placement="bottom"
           icon="ios-arrow-down">
         </AutoComplete>
         <Input prefix="ios-search" clearable placeholder="请输入寝室号" style="width: 10%" v-model="value3" @on-enter="searchLocation"/>
         <Button type="primary" @click="searchLocation">查询</Button>
         <Button type="success"  @click="modal1 = true">新增</Button>
         <Button type="success"  @click="fresh">刷新</Button>
         <Tooltip  content="批量导入学生信息" placement="bottom">
           <Button type="success"  @click="leadIn"><Icon type="md-cloud-upload" size="18" /></Button>
         </Tooltip >
         <Tooltip  content="导出当前页学生信息" placement="bottom">
           <Button type="success"  @click="leadOut"><Icon type="md-cloud-download" size="18" /></Button>
         </Tooltip >
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
       title="新增学生信息"
       @on-ok="addOne"
       @on-cancel="cancel">
       <Form
         ref="formValidate"
         :model="formValidate"
         :rules="ruleValidate"
         :label-width="100"
         style="padding: 0 40px"
         label-position="right">
         <Form-item label="学生姓名" prop="studentName">
           <Input
             v-model="formValidate.studentName"
             placeholder="请输入学生的姓名"
           />
         </Form-item>
         <Form-item label="学生学号" prop="studentNum">
           <Input
             v-model="formValidate.studentNum"
             placeholder="请输入学生的学号"
           />
         </Form-item>
         <Form-item label="学生卡ID" prop="studentCardId">
           <Input
             v-model="formValidate.studentCardId"
             placeholder="请输入学生卡ID"
           />
         </Form-item>
         <Form-item label="身份证ID" prop="identityCardId">
           <Input
             v-model="formValidate.identityCardId"
             placeholder="请输入学生的身份证ID"
           />
         </Form-item>
         <Form-item label="学生所在楼栋" prop="buildingName">
           <AutoComplete
             v-model="formValidate.buildingName"
             :data="data1"
             :filter-method="filterMethod"
             placeholder="请选择楼栋名称"
             style="width:180px"
             :transfer="true"
             placement="bottom"
             icon="ios-arrow-down">
           </AutoComplete>
         </Form-item>
         <Form-item label="学生所在寝室" prop="dormNum">
           <Input
             v-model="formValidate.dormNum"
             placeholder="请输入寝室号"
           />
         </Form-item>
         <Form-item label="智能锁ID" prop="lockId">
           <Input
             v-model="formValidate.lockId"
             placeholder="请输入学生所在寝室对应的智能锁ID"
           />
         </Form-item>
         <Form-item label="学生编号" prop="definedNum">
           <AutoComplete
             v-model="formValidate.definedNum"
             :data="data2"
             :filter-method="filterMethod"
             placeholder="请给学生分配自定义编号"
             style="width:180px"
             :transfer="true"
             placement="bottom"
             @on-focus="getDefinedNum"
             icon="ios-arrow-down">
           </AutoComplete>
         </Form-item>
       </Form>
     </Modal>
     <Modal
       v-model="modal2"
       title="编辑学生信息"
       @on-ok="changeInfo()">
       <Form
         ref="editForm"
         :model="editForm"
         :rules="editFormRules"
         :label-width="100"
         style="padding: 0 40px"
         label-position="right">
         <Form-item label="学生姓名" prop="studentName">
           <Input
             disabled
             v-model="editForm.studentName"
             placeholder="请输入学生的姓名"
           />
         </Form-item>
         <Form-item label="学生学号" prop="studentNum">
           <Input
             disabled
             v-model="editForm.studentNum"
             placeholder="请输入学生的学号"
           />
         </Form-item>
         <Form-item label="学生卡ID" prop="studentCardId">
           <Input
             v-model="editForm.studentCardId"
             placeholder="请输入学生卡ID"
           />
         </Form-item>
         <Form-item label="身份证ID" prop="identityCardId">
           <Input
             v-model="editForm.identityCardId"
             placeholder="请输入学生的身份证ID"
           />
         </Form-item>
         <Form-item label="学生所在楼栋" prop="buildingName">
           <AutoComplete
             disabled
             v-model="editForm.buildingName"
             :data="data1"
             :filter-method="filterMethod"
             placeholder="请选择楼栋名称"
             style="width:180px"
             :transfer="true"
             placement="bottom"
             icon="ios-arrow-down">
           </AutoComplete>
         </Form-item>
         <Form-item label="学生所在寝室" prop="dormNum">
           <Input
             disabled
             v-model="editForm.dormNum"
             placeholder="请输入寝室号"
           />
         </Form-item>
         <Form-item label="智能锁ID" prop="lockId">
           <Input
             disabled
             v-model="editForm.lockId"
             placeholder="请输入学生所在寝室对应的智能锁ID"
           />
         </Form-item>
         <Form-item label="学生编号" prop="definedNum">
           <AutoComplete
             disabled
             v-model="editForm.definedNum"
             :data="data2"
             :filter-method="filterMethod"
             placeholder="请给学生分配自定义编号"
             style="width:180px"
             :transfer="true"
             placement="bottom"
             @on-focus="getDefinedNum"
             icon="ios-arrow-down">
           </AutoComplete>
         </Form-item>
       </Form>
     </Modal>
     <Modal v-model="modal3" width="110" :closable = "false">
       <p slot="header" style="color:#2d8cf0;text-align:center">
         <span>导入</span>
       </p>
       <div style="text-align:center">
         <Upload
           ref="uploadFile"
           action="http://localhost:8083/student/getExcelInfo"
           :with-credentials="true"
           :format ="['xlsx','xls']"
           :show-upload-list="true"
           :on-success="handleSuccess"
           :on-format-error="handleFormatError"
           :on-error="handleError">
           <Button icon="ios-cloud-upload-outline">导  入</Button>
         </Upload>
         <Button @click="loadTemplate">下载模板</Button>
       </div>
       <div slot="footer">
         <Button type="primary" size="small" long  @click="back">返回</Button>
       </div>
     </Modal>
   </section>
</template>

<script>
    export default {
      inject: ['reload'],
        name: "studentInfo",
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
              title: '学生姓名',
              key: 'studentName',
              align: 'center'
            },
            {
              title: '学生学号',
              key: 'studentNum',
              align: 'center'
            },
            {
              title: '学生卡ID',
              key: 'studentCardId',
              align: 'center'
            },
            {
              title: '身份证ID',
              key: 'identityCardId',
              align: 'center'
            },
            {
              title: '楼栋名称',
              key: 'buildingName',
              align: 'center'
            },
            {
              title: '寝室号',
              key: 'dormNum',
              align: 'center'
            },
            {
              title: '智能锁ID',
              key: 'lockId',
              align: 'center'
            },
            {
              title: '学生编号',
              key: 'definedNum',
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
            studentName: '',
            studentNum: '',
            studentCardId: '',
            identityCardId: '',
            buildingName: '',
            dormNum: '',
            lockId: '',
            definedNum: ''
          },
          ruleValidate: {
            studentName: [
              {
                required: true,
                message: '学生名字不能为空',
              }
            ],
            studentNum: [
              {
                required: true,
                message: '学生卡号不能为空',
              }
            ],
            studentCardId: [
              {
                required: true,
                message: '学生卡ID不能为空',
              }
            ],
            identityCardId: [
              {
                required: true,
                message: '身份证ID不能为空',
              }
            ],
            buildingName: [
              {
                required: true,
                message: '学生寝室所在楼栋名不能为空',
              }
            ],
            dormNum: [
              {
                required: true,
                message: '学生所在寝室号不能为空',
              }
            ],
            lockId: [
              {
                required: true,
                message: '智能锁的ID不能为空',
              }
            ],
            definedNum: [
              {
                required: true,
                message: '自定义的学生编号不能空',
              }
            ],
          },
          editForm: {
            id: 0,
            studentName: '',
            studentNum: '',
            studentCardId: '',
            identityCardId: '',
            buildingName: '',
            dormNum: '',
            lockId: '',
            definedNum: ''
          },
          editFormRules: {
            studentName: [
              {
                required: true,
                message: '学生名字不能为空',
              }
            ],
            studentNum: [
              {
                required: true,
                message: '学生卡号不能为空',
              }
            ],
            studentCardId: [
              {
                required: true,
                message: '学生卡ID不能为空',
              }
            ],
            identityCardId: [
              {
                required: true,
                message: '身份证ID不能为空',
              }
            ],
            buildingName: [
              {
                required: true,
                message: '学生寝室所在楼栋名不能为空',
              }
            ],
            dormNum: [
              {
                required: true,
                message: '学生所在寝室号不能为空',
              }
            ],
            lockId: [
              {
                required: true,
                message: '智能锁的ID不能为空',
              }
            ],
            definedNum: [
              {
                required: true,
                message: '自定义的学生编号不能空',
              }
            ],
          },
          data: [],  //表格拿到的将要展示的数据（只有某一页的数据量）
          allDataNum: 0,  //所有数据的个数
          value: '', //根据学生名字绑定的值
          value1:'', //根据学生卡号绑定的值
          value2:'', //根据楼宇名称绑定的值
          data1: [],  //楼宇名称返回的所有数据
          data2: [],  //推荐编号返回的所有数据
          value3:'', //根据寝室号绑定的值
          flag: false,
          sels: [],//列表选中列
          modal1: false,
          modal2: false,
          modal3: false,
          id: 0,   //单行删除时，存放被选择行的id
          ids: [], //批量删除时，存放所有被选择行的id
          pageSize: 10,
          currentPageNum: 1,
        }
      },
      created(){
        this.getBuildingName();
      },
      mounted(){
        this.getStudentInfo();
      },
      methods:{

        /**
         * 获取表格中的学生的信息
         */
        getStudentInfo: function () {
          let pageSize = this.pageSize;
          let currentPageNum = this.currentPageNum;
          this.axios({
            method: 'post',
            url: 'http://localhost:8083/student/getStudentInfo',
            params: {pageSize: pageSize, currentPageNum:currentPageNum}
          }).then(res => {
            console.log(res.data);
            this.data = res.data.content;
            this.allDataNum = res.data.totalElements;
          }).catch(function (error) {
            console.log(error);
          })
        },

        /**
         * 自动完成搜索框 在渲染前拿到所有楼宇的名字
         */
        getBuildingName: function () {
          this.axios({
            method: 'post',
            url: 'http://localhost:8083/building/getBuildingName',
          }).then(res => {
            this.data1 = res.data;
          }).catch(function (error) {
            console.log(error);
          })
        },
        /**
         * 根据输入的楼宇名称给出建议
         * @param value
         * @param option
         * @returns {boolean}
         */
        filterMethod (value, option) {
          return option.indexOf(value) !== -1;
        },


        /**
         * 根据学生的姓名实现模糊查找
         */
        searchStudentName: function () {
          let value = this.value;
          if(value === ''){
            this.$Message.info('请输入学生名字后再查询');
          }else{
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/student/findByStudentName',
              params:{studentName:value}
            }).then(res => {
              if(res.data.message === 'no name'){
                this.data = [];
                this.value = '';
                this.$Message.error('没有此学生信息');
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
         * 根据学号实现模糊查询
         */
        searchStudentNum: function () {
          let value = this.value1;
          if(value === ''){
            this.$Message.info('请输入学生学号后再查询');
          }else{
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/student/findByStudentNum',
              params:{studentNum:value}
            }).then(res => {
              if(res.data.message === 'no name'){
                this.data = [];
                this.value = '';
                this.$Message.error('没有此学生信息');
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
         * ①只根据楼栋名进行查询
         * ②只根据寝室号进行查询
         * ③根据楼栋名称和寝室号实现某个寝室学生信息的查询
         */
        searchLocation: function () {
          let buildingName = this.value2;
          let dormNumber = this.value3;
          if (buildingName !== '' && dormNumber === ''){   //只根据楼宇名称进行查询
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/student/findByBuildingName',
              params:{buildingName:buildingName}
            }).then(res => {
              if(res.data.message === 'no name'){
                this.data = [];
                this.value2 = '';
                this.$Message.error('没有此楼栋中对应的学生信息');
              }else{
                this.data = res.data;
                this.value2 = '';
                this.allDataNum = res.data.length;
                console.log( res.data.length)
              }
            }).catch(function (error) {
              console.log(error);
            })
          }else if(buildingName === '' && dormNumber !== ''){  //只根据寝室号进行查询
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/student/findByDormNum',
              params:{dormNumber:dormNumber}
            }).then(res => {
              if(res.data.message === 'no name'){
                this.data = [];
                this.value3 = '';
                this.$Message.error('没有此寝室号对应的学生信息');
              }else{
                this.data = res.data;
                this.value3 = '';
                this.allDataNum = res.data.length;
                console.log( res.data.length)
              }
            }).catch(function (error) {
              console.log(error);
            })
          }else if(buildingName !== '' && dormNumber !== ''){  //根据楼栋名称和寝室号进行多条件查询
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/student/findByLocation',
              params:{buildingName:buildingName,dormNumber:dormNumber}
            }).then(res => {
              if(res.data.message === 'no name'){
                this.data = [];
                this.value2 = '';
                this.value3 = '';
                this.$Message.error('没有此学生信息');
              }else{
                this.data = res.data;
                this.value2 = '';
                this.value3 = '';
                this.allDataNum = res.data.length;
                console.log( res.data.length)
              }
            }).catch(function (error) {
              console.log(error);
            })
          }else {
            this.$Message.info('请输入学生所在楼栋名或寝室号后再查询');
          }
        },

        /**
         * 实现学生信息的批量导入（Excel表格）
         */
        leadIn: function(){
          this.modal3 = true;
          this.$Notice.open({
            title: '提示',
            desc: '请按照模板填写学生信息，获取模板请点击<a>下载模板</a>按钮',
            duration: 8
          });
        },
        handleFormatError: function (file) {
          this.$Notice.warning({
            title: '文件格式不正确',
            desc: '文件 《' + file.name + ' 》格式不正确，请上传.xls,.xlsx文件。'
          })
        },
        handleSuccess(response,file,fileList){
            this.$Message.success("数据导入成功！");
        },
        handleError(error,file,fileList){
          this.$Message.error("数据导入失败！")
        },
        back: function(){
          this.modal3 = false;
          this.$refs.uploadFile.clearFiles()
        },
        loadTemplate: function(){
          require.ensure([], () => {
            const {export_json_to_excel} = require('@/assets/js/Export2Excel');
            const tHeader = ['姓名','学号', '学生卡ID', '身份证ID', '楼栋名称','寝室号', '锁ID', '编号'];
            const data = [];
            export_json_to_excel(tHeader, data, '上传学生信息模板');
          })
        },




        /**
         * 实现导出当前页学生的信息（Excel表格）
         */
        leadOut: function () {
          require.ensure([], () => {
            const {export_json_to_excel} = require('@/assets/js/Export2Excel');
            const tHeader = ['姓名','学号', '学生卡ID', '身份证ID', '楼栋名称','寝室号', '锁ID', '编号'];
            const filterVal = ['studentName','studentNum', 'studentCardId', 'identityCardId', 'buildingName','dormNum', 'lockId', 'definedNum'];
            const list = this.data;
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '学生信息');
          })
        },
        formatJson(filterVal, jsonData) {
          return jsonData.map(v => filterVal.map(j => v[j]))
        },



        /**
         * 新增表单中自定义编号，点击自动完成输入框获取建议数据
         */
        getDefinedNum: function(){
          let buildingName = this.formValidate.buildingName;
          let dormNumber = this.formValidate.dormNum;
          if(buildingName !== '' && dormNumber !=='' ){
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/student/getDefinedNum',
              params:{buildingName:buildingName,dormNumber:dormNumber}
            }).then(res => {
              this.data2 = res.data;
            }).catch(function (error) {
              console.log(error);
            })
          }else {
            this.$Message.info('选择学生所在楼栋和输入寝室号后重试');
          }
        },
        /**
         * 新增按钮弹出对话框中的确定和取消按钮
         */
        addOne () {
          let studentName = this.formValidate.studentName;
          let studentNum = this.formValidate.studentNum;
          let studentCardId = this.formValidate.studentCardId;
          let identityCardId = this.formValidate.identityCardId;
          let buildingName = this.formValidate.buildingName;
          let dormNum = this.formValidate.dormNum;
          let lockId = this.formValidate.lockId;
          let definedNum = this.formValidate.definedNum;
          this.$refs.formValidate.validate(async valid => {
            if (valid) {  //表单验证通过
              this.axios({
                method: 'post',
                url: 'http://localhost:8083/student/addStudentInfo',
                params:{studentName:studentName,studentNum:studentNum,studentCardId:studentCardId,identityCardId:identityCardId,buildingName:buildingName,dormNum:dormNum,lockId:lockId,definedNum:definedNum}
              }).then(res => {
                if (res.data.code ===200){
                  this.$Message.success('新增学生信息成功');
                  this.reload();
                }else {
                  this.$Message.error('新增学生信息失败');
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
          this.editForm.id = row.id;
        },
        /**
         * 点击编辑对话框的确定按钮
         */
        changeInfo(){
          let id = this.editForm.id;
          let studentCardId = this.editForm.studentCardId;
          let identityCardId = this.editForm.identityCardId;
          this.$refs.editForm.validate(async valid => {
            if (valid) {  //表单验证通过
              this.axios({
                method: 'post',
                url: 'http://localhost:8083/student/editStudentInfo',
                params:{id:id,studentCardId:studentCardId,identityCardId:identityCardId}
              }).then(res => {
                if (res.data.code ===200){
                  this.$Message.success('编辑客户信息成功');
                  this.reload();
                }else {
                  this.$Message.error('编辑客户信息失败');
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
                url: 'http://localhost:8083/student/deleteOne',
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
                url: 'http://localhost:8083/student/deleteMany',
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
            url: 'http://localhost:8083/student/getStudentInfo',
            params: {pageSize: pageSize, currentPageNum:currentPageNum}
          }).then(res => {
            console.log(res.data);
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
            url: 'http://localhost:8083/student/getStudentInfo',
            params: {pageSize: pageSize, currentPageNum:currentPageNum1}
          }).then(res => {
            console.log(res.data);
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
