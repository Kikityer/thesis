<template>
  <section>
    <div style="margin: 0 0 20px 0;background-color: #f5f7f9; height: 50px">
      <div style="position: relative;top: 10px;left:10px; max-height: 80px">
        <AutoComplete
          v-model="value"
          :data="data1"
          :filter-method="filterMethod"
          placeholder="请选择楼栋"
          style="width:180px"
          :transfer="true"
          placement="bottom"
          icon="ios-arrow-down">
        </AutoComplete>
        <Button type="primary" @click="searchBuilding">查询</Button>
        <Input prefix="ios-search" placeholder="请输入宿管姓名" style="width: auto" v-model="value1" @on-enter="searchManagerName"/>
        <Button type="primary" @click="searchManagerName">查询</Button>
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
      title="新增楼宇信息"
      @on-ok="addOne"
      @on-cancel="cancel">
      <Form
        ref="formValidate"
        :model="formValidate"
        :rules="ruleValidate"
        :label-width="90"
        style="padding: 20px 40px"
        label-position="right">
        <Form-item label="楼宇名称" prop="buildingName">
          <Input
            v-model="formValidate.buildingName"
            placeholder="请输入楼宇名称"
          />
        </Form-item>
        <Form-item label="门锁总数" prop="lockNum">
          <Input
            v-model="formValidate.lockNum"
            placeholder="请输入当前楼宇内门锁的总数量"
          />
        </Form-item>
        <Form-item label="居住人群" prop="forWhom">
          <Select v-model="formValidate.forWhom" style="width:200px">
            <Option v-for="item in buildingPeople" :value="item.value" :key="item.value">{{ item.label }}</Option>
          </Select>
        </Form-item>
        <Form-item label="寝室容量" prop="dormCapacity">
          <InputNumber :max="8" :min="1" v-model="formValidate.dormCapacity"></InputNumber>
        </Form-item>
        <Form-item label="宿管姓名" prop="managerName">
          <Input
            v-model="formValidate.managerName"
            placeholder="请输入宿管的姓名"
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
        <Form-item label="楼宇名称" prop="buildingName">
          <Input
            v-model="editForm.buildingName"
            placeholder="请输入楼宇名称"
          />
        </Form-item>
        <Form-item label="门锁总数" prop="lockNum">
          <Input
            v-model="editForm.lockNum"
            placeholder="请输入当前楼宇内门锁的总数量"
          />
        </Form-item>
        <Form-item label="居住人群" prop="forWhom">
          <Select v-model="editForm.forWhom" style="width:200px">
            <Option v-for="item in buildingPeople" :value="item.value" :key="item.value">{{ item.label }}</Option>
          </Select>
        </Form-item>
        <Form-item label="寝室容量" prop="dormCapacity">
          <InputNumber :max="8" :min="1" v-model="editForm.dormCapacity"></InputNumber>
        </Form-item>
        <Form-item label="宿管姓名" prop="managerName">
          <Input
            v-model="editForm.managerName"
            placeholder="请输入宿管的姓名"
          />
        </Form-item>
      </Form>
    </Modal>
  </section>
</template>

<script>
    export default {
      inject: ['reload'],
        name: "buildingInfo",
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
              title: '楼宇名称',
              key: 'buildingName',
              align: 'center'
            },
            {
              title: '门锁总数',
              key: 'lockNum',
              align: 'center'
            },
            {
              title: '居住人群',
              key: 'forWhom',
              align: 'center'
            },
            {
              title: '寝室容量',
              key: 'dormCapacity',
              align: 'center'
            },
            {
              title: '宿管姓名',
              key: 'managerName',
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
            buildingName: '',
            lockNum: '',
            forWhom: '',
            dormCapacity: 4,
            managerName: ''
          },
          ruleValidate: {
            buildingName: [
              {
                required: true,
                message: '楼宇名称名称不能为空',
              }
            ],
            lockNum: [
              {
                required: true,
                message: '锁的数量不能为空',
              }
            ],
            forWhom: [
              {
                required: true,
                message: '居住人群类别不能为空',
              }
            ],
            dormCapacity: [
              {
                required: true,
                message: '寝室容量不能为空',
              }
            ],
            managerName: [
              {
                required: true,
                message: '宿管人员不能为空',
              }
            ],
          },
          modal2: false,
          editForm: {
            id: 0,
            buildingName: '',
            lockNum: '',
            forWhom: '',
            dormCapacity: 4,
            managerName: ''
          },
          editFormRules: {
            buildingName: [
              {
                required: true,
                message: '楼宇名称名称不能为空',
              }
            ],
            lockNum: [
              {
                required: true,
                message: '锁的数量不能为空',
              }
            ],
            forWhom: [
              {
                required: true,
                message: '居住人群类别不能为空',
              }
            ],
            dormCapacity: [
              {
                required: true,
                message: '寝室容量不能为空',
              }
            ],
            managerName: [
              {
                required: true,
                message: '宿管人员不能为空',
              }
            ],
          },
          data: [],  //表格拿到的将要展示的数据（只有某一页的数据量）
          allDataNum: 0,  //所有数据的个数
          value: '', //根据楼宇名称绑定的值
          data1: [],  //搜索框自动完成返回的数据
          value1:'', //根据宿管名字绑定的值
          flag: false,
          sels: [],//列表选中列
          modal1: false,
          id: 0,   //单行删除时，存放被选择行的id
          ids: [], //批量删除时，存放所有被选择行的id
          pageSize: 10,
          currentPageNum: 1,
          buildingPeople: [
            {
              value: '男',
              label: '男'
            },
            {
              value: '女',
              label: '女'
            },
            {
              value: '男/女',
              label: '男/女'
            },
          ]
        }
      },
      created(){
        this.getBuildingName();
      },
      mounted(){
        this.getBuildingInfo();
      },

      methods:{
        /**
         * 获取所有的表格中的楼宇信息
         */
        getBuildingInfo: function () {
          let pageSize = this.pageSize;
          let currentPageNum = this.currentPageNum;
          this.axios({
            method: 'post',
            url: 'http://localhost:8083/building/getBuildingInfo',
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
         * 点击查询按钮(根据楼宇名称查找)
         */
        searchBuilding: function () {
          let value = this.value;
          if(value === ''){
            this.$Message.info('请输入楼宇名称后再查询');
          }else{
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/building/findByBuildingName',
              params:{buildingName:value}
            }).then(res => {
              if(res.data.message === 'no name'){
                this.data = [];
                this.value = ''
              }else{
                this.data = res.data;
                this.value = '';
                this.allDataNum = res.data.length;
              }
            }).catch(function (error) {
              console.log(error);
            })
          }
        },

        /**
         * 点击查询按钮(根据宿管名字查找)
         */
        searchManagerName: function(){
          let value = this.value1;
          if(value === ''){
            this.$Message.info('请输入宿管名字后再查询');
          }else{
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/building/findByManagerName',
              params:{managerName:value}
            }).then(res => {
              if(res.data.message === 'no name'){
                this.data = [];
                this.value1 = ''
              }else{
                this.data = res.data;
                this.value1 = '';
                this.allDataNum = res.data.length;
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
          let buildingName = this.formValidate.buildingName;
          let lockNum = this.formValidate.lockNum;
          let forWhom = this.formValidate.forWhom;
          let dormCapacity = this.dormCapacity;
          let managerName = this.formValidate.managerName;
          this.$refs.formValidate.validate(async valid => {
            if (valid) {  //表单验证通过
              this.axios({
                method: 'post',
                url: 'http://localhost:8083/building/addBuildingInfo',
                params:{buildingName:buildingName,lockNum:lockNum,forWhom:forWhom,dormCapacity:dormCapacity,managerName:managerName}
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
          if ( this.formValidate.buildingName !== '' || this.formValidate.lockNum !== '' || this.formValidate.forWhom !== ''||
            this.formValidate.managerName !== '' || this.formValidate.dormCapacity !== 0){
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
          let buildingName = this.editForm.buildingName;
          let lockNum = this.editForm.lockNum;
          let forWhom = this.editForm.forWhom;
          let dormCapacity = this.dormCapacity;
          let managerName = this.editForm.managerName;
          this.$refs.editForm.validate(async valid => {
            if (valid) {  //表单验证通过
              this.axios({
                method: 'post',
                url: 'http://localhost:8083/building/editBuildingInfo',
                params:{id:id,buildingName:buildingName,lockNum:lockNum,forWhom:forWhom,dormCapacity:dormCapacity,managerName:managerName}
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
                url: 'http://localhost:8083/building/deleteMany',
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
            url: 'http://localhost:8083/building/getBuildingInfo',
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
            url: 'http://localhost:8083/building/getBuildingInfo',
            params: {pageSize: pageSize, currentPageNum:currentPageNum1}
          }).then(res => {
            console.log(res.data);
            this.data = res.data.content;
            this.allDataNum = res.data.totalElements;
          }).catch(function (error) {
            console.log(error);
          })
        },
      }
    }
</script>

<style scoped>

</style>
