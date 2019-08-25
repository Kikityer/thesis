<template>
   <section>
     <div style="margin: 0 0 20px 0;background-color: #f5f7f9; height: 50px">
       <div style="position: relative;top: 10px;left:10px; max-height: 80px">
         <AutoComplete
           v-model="value"
           :data="data1"
           clearable
           :filter-method="filterMethod"
           placeholder="请选择楼栋"
           style="width:10%"
           :transfer="true"
           placement="bottom"
           icon="ios-arrow-down">
         </AutoComplete>
         <Input prefix="ios-search" clearable placeholder="请输入寝室号" style="width: 10%" v-model="value1" @on-enter="searchLocation"/>
         <Button type="primary" @click="searchLocation">查询</Button>
         <Input prefix="ios-search" clearable placeholder="请输入锁ID" style="width: 10%" v-model="value2"@on-enter="searchLockId"/>
         <Button type="primary" @click="searchLockId">查询</Button>
         <DatePicker type="date"  placeholder="请选择日期" style="width: 10%" v-model="value3"@on-enter="searchDate"></DatePicker>
         <Button type="primary" @click="searchDate">查询</Button>
         <Button type="success"  @click="fresh">刷新</Button>
         <Tooltip  content="导出需要更换电池的寝室信息" placement="bottom">
           <Button type="success"  @click="leadOutBattery"><Icon type="md-cloud-download" size="18" /></Button>
         </Tooltip >
         <Tooltip  content="导出当前锁状态信息" placement="bottom">
           <Button type="success"  @click="leadOut"><Icon type="md-cloud-download" size="18" /></Button>
         </Tooltip >
       </div>
     </div>
     <Table border  :columns="columns" :data="pageTable[currentPage-1]"  stripe max-height = 380>
       <template slot-scope="{ row, index }" slot="action">
         <Button type="primary" size="small" @click="openLock(index,row)">开锁</Button>
       </template>
     </Table>
     <div style="margin: 20px 0 0 0; background-color: #f5f7f9; height: 50px">
       <Form style="position: relative; top: 8px;left: 10px">
         <Row>
           <Col span="9" offset="15">
             <FormItem>
               <Page :total="allDataNum"
                     :current="currentPage"
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
   </section>
</template>

<script>
    export default {
      inject: ['reload'],
        name: "lockInfo",
      data(){
        return{
          columns: [
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
              title: '寝室号',
              key: 'dormNum',
              align: 'center'
            },
            {
              title: '锁ID',
              key: 'lockId',
              align: 'center'
            },
            {
              title: '电池余量',
              key: 'lockBattery',
              align: 'center',
              sortable: true,
              render: (h, params) => {

                if (params.row.lockBattery >= 60  && params.row.lockBattery < 100) {
                  return h('div', [
                    h('i', {
                      class: 'iconfont icon-iconset0251',
                      style: {
                        fontSize: '2.5em',
                      },
                      domProps: {
                        title: params.row.lockBattery+'%',
                      },
                    })
                  ]);
                }else if(params.row.lockBattery >= 20  && params.row.lockBattery < 60){
                  return h('div', [
                    h('i', {
                      class: 'iconfont icon-iconset0250',
                      style: {
                        fontSize: '2.5em',
                      },
                      domProps: {
                        title: params.row.lockBattery+'%',
                      },
                    })
                  ]);
                }else if(params.row.lockBattery >= 10  && params.row.lockBattery < 20){
                  return h('div', [
                    h('i', {
                      class: 'iconfont icon-iconset0249',
                      style: {
                        fontSize: '2.5em',
                        color: '#ff9900',
                      },
                      domProps: {
                        title: params.row.lockBattery+'%',
                      },
                    })
                  ]);
                }else if (params.row.lockBattery > 0  && params.row.lockBattery < 10){
                  return h('div', [
                    h('i', {
                      class: 'iconfont icon-iconset0248',
                      style: {
                        fontSize: '2.5em',
                        color: '#ed4014',
                      },
                      domProps: {
                        title: params.row.lockBattery+'%',
                      },
                    })
                  ]);
                }
              }
            },
            {
              title: '信号强度',
              key: 'lockSignal',
              align: 'center',
              sortable: true,
              render: (h, params) => {
                if (params.row.lockSignal === 3) {
                  return h('div', [
                    h('i', {
                      class: 'iconfont icon-WIFIxinhao-ji',
                      style: {
                        fontSize: '2.5em',
                      },
                      domProps: {
                        title: '强',
                      },
                    })
                  ]);
                }else if (params.row.lockSignal === 2){
                  return h('div', [
                    h('i', {
                      class: 'iconfont icon-WIFIxinhao-ji1',
                      style: {
                        fontSize: '2.5em',
                      },
                      domProps: {
                        title: '中',
                      },
                    })
                  ]);
                }else if (params.row.lockSignal === 1){
                  return h('div', [
                    h('i', {
                      class: 'iconfont icon-WIFIxinhao-ji2',
                      style: {
                        fontSize: '2.5em',
                        color: '#ed4014',
                      },
                      domProps: {
                        title: '弱',
                      },
                    })
                  ]);
                }
              }
            },
            {
              title: '具体时间',
              key: 'stateTime',
              align: 'center'
            },
            {
              title: '远程开锁',
              slot: 'action',
              width: 150,
              align: 'center',
            }
          ],
          buildingName: '',
          dormNum: '',
          lockId: '',
          lockBattery: '',
          lockSignal: '',
          stateTime: '',

          data: [],  //后台返回的所有数据
          allDataNum: 0,  //所有数据的个数
          pageTable: [], //分页之后要展示的页数
          changeBattery: [], //需要更换电池的寝室信息
          value: '', //根据楼宇名称绑定的值
          value1:'', //根据寝室号绑定的值
          value2:'', //根据锁ID绑定的值
          value3:'', //根据日期绑定的值
          data1: [],  //楼宇名称返回的所有数据
          flag: false,
          pageSize: 10,
          currentPage: 1,
        }
      },
      created(){
        this.getBuildingName();
      },
      mounted(){
        this.getLockStateInfo();
      },
      methods:{

        /**
         * 获取锁状态信息
         * 24小时以内
         * 去重
         */
        getLockStateInfo: function(){
          this.axios({
            method: 'post',
            url: 'http://localhost:8083/lockState/getDayStateInfo',
          }).then(res => {
            this.changeBattery = [];
            for(let i = 0;i< res.data.length;i++){
              res.data[i].stateTime = new Date(+new Date(new Date(res.data[i].stateTime).toJSON()) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
              if(0 < res.data[i].lockBattery && res.data[i].lockBattery < 10){
                this.changeBattery.push(res.data[i])
              }
            }
            this.data = res.data;
            this.cutPage(this.data);
            this.allDataNum = res.data.length;
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
         * 前端分页
         */
        cutPage(people){
          this.pageTable = this.sliceArray(people, this.pageSize);  //分页函数
        },
        sliceArray(array, size) {
          let result = [];
          for (let x = 0; x < Math.ceil(array.length / size); x++) {
            let start = x * size;
            let end = start + size;
            result.push(array.slice(start, end));
          }
          return result;
        },
        changeSize: function (size) {
          this.pageSize = size;
          this.cutPage(this.data);  //在每次选择每页条数之后刷新一次请求列表参数得以渲染新的列表
        },
        changePage: function(currentPage){
          this.currentPage = currentPage;
        },


        /**
         *
         * ①只根据楼栋名进行查询
         * ②只根据寝室号进行查询
         * ③根据楼栋名称和寝室号实现某个寝室锁状态信息的查询
         * 24小时内
         * 去重
         */
        searchLocation: function () {
          let buildingName = this.value;
          let dormNumber = this.value1;
          if (buildingName !== '' && dormNumber === '') {   //只根据楼宇名称进行查询
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/lockState/findByBuildingName',
              params:{buildingName:buildingName}
            }).then(res => {
              if(res.data.message === 'no name'){
                this.pageTable = [];
                this.value = '';
                this.$Message.error('没有此楼栋对应的锁状态信息');
              }else{
                this.changeBattery = [];
                for(let i = 0;i< res.data.length;i++){
                  res.data[i].stateTime = new Date(+new Date(new Date(res.data[i].stateTime).toJSON()) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
                  if(0 < res.data[i].lockBattery && res.data[i].lockBattery < 10){
                    this.changeBattery.push(res.data[i])
                  }
                }
                this.currentPage = 1;
                this.data = res.data;
                this.cutPage(this.data);
                this.value = '';
                this.allDataNum = res.data.length;
                console.log( res.data.length)
              }
            }).catch(function (error) {
              console.log(error);
            })
          }else if(buildingName === '' && dormNumber !== '') {  //只根据寝室号进行查询
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/lockState/findByDormNum',
              params:{dormNumber:dormNumber}
            }).then(res => {
              if(res.data.message === 'no name'){
                this.pageTable = [];
                this.value1 = '';
                this.$Message.error('没有此寝室号对应的锁状态信息');
              }else{
                for(let i = 0;i< res.data.length;i++){
                  res.data[i].stateTime = new Date(+new Date(new Date(res.data[i].stateTime).toJSON()) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
                }
                this.currentPage = 1;
                this.data = res.data;
                this.cutPage(this.data);
                this.value1 = '';
                this.allDataNum = res.data.length;
                console.log( res.data.length)
              }
            }).catch(function (error) {
              console.log(error);
            })
          }else if(buildingName !== '' && dormNumber !== '') {  //根据楼栋名称和寝室号进行多条件查询
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/lockState/findByLocation',
              params:{buildingName:buildingName,dormNumber:dormNumber}
            }).then(res => {
              if(res.data.message === 'no name'){
                this.pageTable = [];
                this.value = '';
                this.value1 = '';
                this.$Message.error('没有此锁状态信息');
              }else{
                for(let i = 0;i< res.data.length;i++){
                  res.data[i].stateTime = new Date(+new Date(new Date(res.data[i].stateTime).toJSON()) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
                }
                this.currentPage = 1;
                this.data = res.data;
                this.cutPage(this.data);
                this.value = '';
                this.value1 = '';
                this.allDataNum = res.data.length;
                console.log( res.data.length)
              }
            }).catch(function (error) {
              console.log(error);
            })
          }else {
            this.$Message.info('请输入楼栋名或寝室号后再进行查询');
          }
        },


        /**
         * 根据锁id进行查询锁的状态信息
         * 24小时内
         * 去重
         */
        searchLockId: function () {
          let lockId = this.value2;
          if(lockId === ''){
            this.$Message.info('请输入锁ID后再进行查询');
          }else {
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/lockState/findByLockID',
              params: {lockId: lockId}
            }).then(res => {
              if (res.data.message === 'no name') {
                this.pageTable = [];
                this.value2 = '';
                this.$Message.error('没有此锁ID对应的锁状态信息');
              } else {
                for (let i = 0; i < res.data.length; i++) {
                  res.data[i].stateTime = new Date(+new Date(new Date(res.data[i].stateTime).toJSON()) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
                }
                this.currentPage = 1;
                this.data = res.data;
                this.cutPage(this.data);
                this.value2 = '';
                this.allDataNum = res.data.length;
                console.log(res.data.length)
              }
            }).catch(function (error) {
              console.log(error);
            })
          }
        },


        /**
         * 根据日期进行查询
         */
        searchDate: function () {
          let date = this.value3;
          if(date === ''){
            this.$Message.info('请选择日期后再进行查询');
          }else {
            this.axios({
              method: 'post',
              url: 'http://localhost:8083/lockState/findByDate',
              params: {date: date}
            }).then(res => {
              if (res.data.message === 'no name') {
                this.pageTable = [];
                this.value3 = '';
                this.$Message.error('没有此日期对应的锁状态信息');
              } else {
                for (let i = 0; i < res.data.length; i++) {
                  res.data[i].stateTime = new Date(+new Date(new Date(res.data[i].stateTime).toJSON()) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
                }
                this.currentPage = 1;
                this.data = res.data;
                this.cutPage(this.data);
                this.value3 = '';
                this.allDataNum = res.data.length;
                console.log(res.data.length)
              }
            }).catch(function (error) {
              console.log(error);
            })
          }
        },


        /**
         * 点击刷新按钮
         */
        fresh: function(){
          this.reload();
        },


        /**
         * 导出需要更换电池的寝室信息
         */
        leadOutBattery: function () {
          require.ensure([], () => {
            const {export_json_to_excel} = require('@/assets/js/Export2Excel');
            const tHeader = ['楼宇名称','寝室号', '锁ID', '电池余量', '信号强度(1:弱 2:中 3:强)','具体时间'];
            const filterVal = ['buildingName','dormNum', 'lockId', 'lockBattery', 'lockSignal','stateTime'];
            const list = this.changeBattery;
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '需更换电池寝室名单');
          })
        },


        /**
         * 导出当前页信息
         */
        leadOut: function () {
          require.ensure([], () => {
            const {export_json_to_excel} = require('@/assets/js/Export2Excel');
            const tHeader = ['楼宇名称','寝室号', '锁ID', '电池余量', '信号强度(1:弱 2:中 3:强)','具体时间'];
            const filterVal = ['buildingName','dormNum', 'lockId', 'lockBattery', 'lockSignal','stateTime'];
            const list = this.data;
            const data = this.formatJson(filterVal, list);
            export_json_to_excel(tHeader, data, '锁状态信息');
          })
        },
        formatJson(filterVal, jsonData) {
          return jsonData.map(v => filterVal.map(j => v[j]))
        },


        /**
         * 远程开锁
         */
        openLock: function (index, row) {
          this.$Modal.confirm({
            title: '提示',
            content: '是否确认立即打开此门锁？',
            onOk: () => {
              this.$Spin.show({
                render: (h) => {
                  return h('div', [
                    h('Icon', {
                      'class': 'demo-spin-icon-load',
                      props: {
                        type: 'ios-loading',
                        size: 18
                      }
                    }),
                    h('div', '正在开锁中请稍等...')
                  ])
                }
              });
              let lockId = row.lockId;
              this.axios({
                method: 'post',
                url: 'http://localhost:8083/lockState/openImmediately',
                params: {lockId: lockId}
              }).then(res => {
                this.$Spin.hide();
                if(res.data === 'success'){
                  this.$Message.success('开锁成功');
                }else{
                  this.$Message.error('连接服务器失败，请重试');
                }
              }).catch(function (error) {
                console.log(error)
              })
            },
          })
        },

      }

    }
</script>

<style scoped>
  .demo-spin-icon-load {
    animation: ani-demo-spin 1s linear infinite;
  }
</style>
