<template>
  <div>
    <Row style="margin: 5% 5%">
      <Col span="11">
        <Card style= "background-color: #fcfdfc">
          <div id="areaColumn" :style="{width:'100%', height:'270px'}"></div>
        </Card>
      </Col>
      <Col span="11" offset="2">
        <Card style= "background-color: #fcfdfc">
          <div id="pieData" :style="{width:'100%', height:'270px'}"></div>
        </Card>
      </Col>
    </Row>
  </div>
</template>

<script>
  import echarts from 'echarts'
    export default {
        name: "buildingChart",
      data(){
          return {
            lockNumColumn: null,
            pieData: null,
            xArea: [],
            yArea:[],
            opinion:[],  //饼状图对应的各项
            opinionData:[]  //饼状图中的数据

          }
      },
      mounted(){
        this.$nextTick(function() {
          this.getAllData();  //渲染之前获取所有的数据
          this.drawLockNumColumn(); //绘制每栋楼对应锁的数量的柱状图
          this.drawPieData();  //男女生宿舍数量对比饼图
        })
      },
      methods: {
        /**
         * 获取所有的数据
         */
        getAllData() {
          this.axios({
            method: 'get',
            async: false,
            dataType: 'json',
            url: 'http://localhost:8083/building/getEchartData',
          }).then(res => {
            console.log(res.data)
            for (let key in  res.data[0]) {
              this.xArea.push(key);
              this.yArea.push(res.data[0][key])
            }
            for (let key in res.data[1]){
              this.opinion.push(key);
              this.opinionData.push({value:res.data[1][key],name:key});
            }
            this.lockNumColumn.setOption({
                title: {
                  text: '智能锁数量统计',
                  subtext: '各楼栋已安装锁的套数',
                  x:'center'
                },
                tooltip: {},
                legend: {
                  data:['地址分布']
                },
                //工具箱
                toolbox:{
                  show:true,//显示工具箱
                  feature:{
                    dataView:{show:true}, //以文字形式显示数据
                    restore:{show:true},   //还原
                    //dataZoom:{show:true}, //区域缩放
                    saveAsImage:{show:true},  //保存图片
                    //magicType:{type:['line','bar']}//动态数据切换，数据显示可以在该规定内容中切换显示方式，
                  }
                },
                xAxis:{
                  data: this.xArea,
                  nameTextStyle: {fontSize :8},
                  axisLabel:{
                    interval:0,
                    rotate:40,
                    textStyle:{
                      fontSize:12
                    }
                  }
                },
                yAxis: {},
                series: [{
                  name: '本楼栋中已安装智能锁的数量',
                  type: 'bar',
                  data: this.yArea
                }]
            });
            this.pieData.setOption({
              title: {
                text: '男女生楼栋数量统计', // 标题文本
                subtext: '所有楼栋数据',
                left: 'center',
              },
              tooltip : {
                trigger: 'item',//以具体项目触发弹窗
                formatter: "{a} <br/>{b} : {c} ({d}%)"
              },
              //图例，选择要显示的项目
              legend:{
                orient:'vertical',
                left:'left',
                data:this.option  //注意要和数据的name相对应
              },
              //工具箱
              toolbox:{
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
              //数据
              series : [
                {
                  name: '男女生楼宇数量占比',
                  type: 'pie',
                  radius : '55%',
                  center: ['50%', '60%'],
                  avoidLabelOverlap: false,
                  data: this.opinionData.sort(function (a, b) {
                    return a.value - b.value;
                  }),
                },
              ]
            });
            window.addEventListener("resize",function(){
              let areaColumn = echarts.init(document.getElementById('areaColumn'));
              let pieData = echarts.init(document.getElementById('pieData'));
              areaColumn.resize();
              pieData.resize();
            });
          }).catch(function (error) {
            console.log(error);
          })
        },
        /**
         * 绘制每栋楼对应锁的数量的柱状图，地区分布 x轴：楼宇名称  y轴：锁对应的数量
         */
        drawLockNumColumn(){
          this.lockNumColumn = echarts.init(document.getElementById('areaColumn'));
        },
        /**
         * 绘制饼状图，批次的数量占比
         */
        drawPieData(){
          this.pieData = echarts.init(document.getElementById('pieData'));
        }
      }

    }
</script>

<style scoped>

</style>
