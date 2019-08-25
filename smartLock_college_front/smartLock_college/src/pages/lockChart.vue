<template>
  <div>
    <Row style="margin: 0 5%">
      <Col span="11">
        <Card style= "background-color: #fcfdfc">
          <div id="BatteryColumn" :style="{width:'100%', height:'230px'}"></div>
        </Card>
      </Col>
      <Col span="11" offset="2">
        <Card style= "background-color: #fcfdfc">
          <div id="signalLine" :style="{width:'100%', height:'230px'}"></div>
        </Card>
      </Col>
    </Row>
    <Row style="margin: 2% 5% 0 5%">
      <Col span="11">
        <Card style= "background-color: #fcfdfc">
          <div id="unlockLine" :style="{width:'100%', height:'230px'}"></div>
        </Card>
      </Col>
      <Col span="11" offset="2">
        <Card style= "background-color: #fcfdfc">
          <div id="pieData" :style="{width:'100%', height:'230px'}"></div>
        </Card>
      </Col>
    </Row>
  </div>
</template>

<script>
  import echarts from 'echarts'
    export default {
        name: "lockChart",
      data(){
          return{
            BatteryColumn: null,
            signalLine: null,
            unlockLine: null,
            pieData: null,
            xBattery: [],
            yBattery:[],
            xSignal:[],
            ySignal:[],
            xUnlock:[],
            yUnlock:[],
            opinion:[],  //饼状图对应的各项
            opinionData:[]  //饼状图中的数据
          }
      },
      mounted(){
        this.$nextTick(function() {
          this.getAllData();  //渲染之前获取所有的数据
          this.drawBatteryColumn();
          this.drawSignalLine();
          this.drawUnlockLine();
          this.drawPieData();
        })
      },
      methods: {
        /**
         * 获取所有的数据
         */
        getAllData(){
          this.axios({
            method: 'get',
            async: false,
            dataType: 'json',
            url: 'http://localhost:8083/lockRecord/getChartData',
          }).then(res => {
            console.log(res.data[2]);
            for (let key in  res.data[0]) {
              this.yBattery.push(key);
              this.xBattery.push(res.data[0][key])
            }
            for (let key in  res.data[1]) {
              this.xSignal.push(key);
              this.ySignal.push(res.data[1][key])
            }
            for (let key in  res.data[2]) {
              this.xUnlock.push(key);
              this.yUnlock.push(res.data[2][key])
            }
            for (let key in  res.data[3]) {
              this.opinion.push(key);
              this.opinionData.push({value:res.data[3][key],name:key});
            }
            this.BatteryColumn.setOption({
              title : {
                text: '今日需更换电池数量统计',
                subtext: '各楼栋数据',
                x:'center'
              },
              tooltip : {
                trigger: 'axis'
              },
              toolbox: {
                show : true,
                feature : {
                  mark : {show: true},
                  dataView : {show: true, readOnly: false},
                  restore : {show: true},
                  saveAsImage : {show: true}
                }
              },
              xAxis : [
                {
                  type : 'value',
                  minInterval: 1, //设置成1保证坐标轴分割刻度显示成整数。
                }
              ],
              yAxis : [
                {
                  type : 'category',
                  data : this.yBattery
                }
              ],
              series: [{
                name: '本楼栋需更换电池数量',
                type: 'bar',
                data: this.xBattery
              }]
            });
            this.signalLine.setOption({
              title: {
                text: '门锁信号强度统计',
                subtext: '各楼栋信号差的门锁数量',
                x:'center'
              },
              tooltip: {
                trigger: 'axis'
              },
              toolbox: {
                show : true,
                feature : {
                  mark : {show: true},
                  dataView : {show: true, readOnly: false},
                  restore : {show: true},
                  saveAsImage : {show: true}
                }
              },
              xAxis:{
                type: 'category',
                data: this.xSignal,
              },
              yAxis: { type: 'value', minInterval: 1},
              series: [{
                name: '本楼栋信号较差门锁数量',
                type: 'line',
                data: this.ySignal
              }]
            });
            this.unlockLine.setOption({
              title: {
                text: '开锁时间点习惯统计',
                subtext: '24小时内数据',
                x:'center'
              },
              legend: {
                show: true,
                bottom: 0
              },
              toolbox: {
                show : true,
                feature : {
                  mark : {show: true},
                  dataView : {show: true, readOnly: false},
                  restore : {show: true},
                  saveAsImage : {show: true}
                }
              },
              tooltip: {
                show: true
              },
              xAxis: {
                type: "category",
                boundaryGap: false,
                data:["00:00","01:00","02:00","03:00","04:00","05:00","06:00","07:00","08:00","09:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00","19:00","20:00","21:00","22:00","23:00"],
                axisLabel: {
                  interval: 0,
                  rotate: 50,
                  align: "center",
                  margin: 18
                },
                axisTick: {
                  show: false
                }
              },
              yAxis: {
                type: "value",
                minInterval: 1,
                axisTick: {
                  show: false
                }
              },
              series: [{
                  name:'本时间区域内开锁数量',
                  data: this.yUnlock,
                  type: "line",
                  areaStyle: {}
                }]
            });
            this.pieData.setOption({
              title : {
                text: '开锁方式偏好统计',
                subtext: '一周内数据',
                x:'center'
              },
              tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
              },
              legend: {
                orient : 'vertical',
                x : 'left',
                data: this.opinion,
              },
              toolbox: {
                show : true,
                feature : {
                  mark : {show: true},
                  dataView : {show: true, readOnly: false},
                  magicType : {
                    show: true,
                    type: ['pie', 'funnel'],
                    option: {
                      funnel: {
                        x: '25%',
                        width: '50%',
                        funnelAlign: 'left',
                        max: 1548
                      }
                    }
                  },
                  restore : {show: true},
                  saveAsImage : {show: true}
                }
              },
              calculable : true,
              series : [
                {
                  name:'开锁方式占比',
                  type:'pie',
                  radius : '55%',
                  center: ['50%', '60%'],
                  data: this.opinionData.sort(function (a, b) {
                    return a.value - b.value;
                  }),
                }
              ]
            });
            window.addEventListener("resize",function(){
              let BatteryColumn = echarts.init(document.getElementById('BatteryColumn'));
              let signalLine = echarts.init(document.getElementById('signalLine'));
              let unlockLine = echarts.init(document.getElementById('unlockLine'));
              let pieData = echarts.init(document.getElementById('pieData'));
              BatteryColumn.resize();
              signalLine.resize();
              unlockLine.resize();
              pieData.resize();
            });
          }).catch(function (error) {
            console.log(error);
          })
        },
        /**
         * 今日需要更换电池数量
         */
        drawBatteryColumn: function () {
          this.BatteryColumn = echarts.init(document.getElementById('BatteryColumn'));
        },


        /**
         * 各个楼栋信号轻度较差的楼栋数据统计signalLine
         */
        drawSignalLine: function () {
          this.signalLine = echarts.init(document.getElementById('signalLine'));
        },


        /**
         * 一天中开锁的时间散点
         */
        drawUnlockLine: function () {
          this.unlockLine = echarts.init(document.getElementById('unlockLine'));
        },


        /**
         * 饼状图
         */
        drawPieData: function () {
          this.pieData = echarts.init(document.getElementById('pieData'));
        }
      }
    }
</script>

<style scoped>

</style>
