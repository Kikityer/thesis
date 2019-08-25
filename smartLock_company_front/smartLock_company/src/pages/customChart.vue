<template>
 <section >
   <div>
     <Row style="margin: 0 5%">
       <Col span="11">
         <Card style= "background-color: #fcfdfc">
           <div id="areaColumn" :style="{width:'100%', height:'230px'}"></div>
         </Card>
       </Col>
       <Col span="11" offset="2">
         <Card style= "background-color: #fcfdfc">
           <div id="dateLine" :style="{width:'100%', height:'230px'}"></div>
         </Card>
       </Col>
     </Row>
     <Row style="margin: 2% 5% 0 5%">
       <Col span="11">
         <Card style= "background-color: #fcfdfc">
           <div id="lockNumColumn" :style="{width:'100%', height:'230px'}"></div>
         </Card>
       </Col>
       <Col span="11" offset="2">
         <Card style= "background-color: #fcfdfc">
           <div id="pieData" :style="{width:'100%', height:'230px'}"></div>
         </Card>
       </Col>
     </Row>
   </div>
 </section>
</template>

<script>
  import echarts from 'echarts'
    export default {
        name: "customChart",
      data() {
        return {
          areaColumn: null,
          dateLine: null,
          lockNumColumn: null,
          pieData: null,
          xArea: [],
          yArea:[],
          xYearData:[],
          yYearData:[],
          xLockNum:[],
          yLockNum:[],
          opinion:[],  //饼状图对应的各项
          opinionData:[]  //饼状图中的数据
        }
      },
      mounted(){
        this.$nextTick(function() {
          this.getAllData();  //渲染之前获取所有的数据
          this.drawAreaColumn(); //绘制地区柱状图
          this.drawDteLine();  //绘制销售日期折线图
          this.drawLockNumColumn();  //绘制锁的销售数量图
          this.drawPieData();  //绘制购买批次的饼状图占比
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
              url: 'http://localhost:8082/customerInfo/getEchartData',
            }).then(res => {
              console.log(res.data[3]);
              for (let key in  res.data[0]) {
                this.xArea.push(key);
                this.yArea.push(res.data[0][key])
              }
              for (let key in  res.data[1]) {
                this.xYearData.push(key);
                this.yYearData.push(res.data[1][key])
              }
              for (let key in  res.data[2]) {
                this.xLockNum.push(key);
                this.yLockNum.push(res.data[2][key])
              }
              for (let key in res.data[3]){
                this.opinion.push(key)
                this.opinionData.push({value:res.data[3][key],name:key});
              }
              this.areaColumn.setOption({
                title: {
                  text: '产品销售地区分布',
                  subtext: '各地区购买产品的高校数量'
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
                },
                yAxis: {},
                series: [{
                  name: '本地区销售高校数量',
                  type: 'bar',
                  data: this.yArea
                }]
              });
              this.dateLine.setOption({
                title: {
                  text: '产品销售日期分布',
                  subtext: '各年份购买产品的高校数量'
                },
                tooltip: {
                  trigger: 'axis'
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
                  type: 'category',
                  data: this.xYearData,
                },
                yAxis: { type: 'value'},
                series: [{
                  name: '本年份销售数量',
                  type: 'line',
                  data: this.yYearData
                }]
              });
              this.lockNumColumn.setOption({
                title: {
                  text: '各高校销售总量',
                  subtext: '各高校购买锁的套数'
                },
                tooltip: {},
                legend: {
                  data:['地址分布'],
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
                grid:{y2:120},
                xAxis:{
                  data: this.xLockNum,
                  nameTextStyle: {fontSize :8},
                  axisLabel:{
                    interval:0,
                    rotate:40,
                    textStyle:{
                      fontSize:10
                    }
                  }
                },
                yAxis: {
                  splitNumber:3,
                },
                series: [{
                  name: '本校销售总量',
                  type: 'bar',
                  data: this.yLockNum
                }]
              });
              this.pieData.setOption({
                title: {
                  text: '各高校购买批次统计', // 标题文本
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
                  textStyle:{
                    color:'#c8c8d0'
                  },
                  data:this.option  //注意要和数据的name相对应
                },
                //工具箱
                toolbox:{
                  show:true,//显示工具箱
                  feature:{
                    dataView:{show:true}, //以文字形式显示数据
                    restore:{show:true},   //还原
                    // dataZoom:{show:true}, //区域缩放
                    saveAsImage:{show:true},  //保存图片
                    //magicType:{type:['line','bar']}//动态数据切换，数据显示可以在该规定内容中切换显示方式，
                  }
                },
                //视觉映射组件，将数据映射到视觉元素上
                visualMap: {
                  show: false,
                  min: 1,
                  max: 28,
                  dimension: 0, //选取数据的维度，如人数据：[身高，体重]，则1代表将体重进行映射，默认值为数组的最后一位
                  // seriesIndex: 4, //选取数据集合中的哪个数组，如{一班}，{二班}，默认选取data中的所有数据集
                  inRange: {
                    //选定了要映射的对象，用inRange详细写要渲染的具体细节，[x，y]中x指最小值对应的量（亮度，饱和度等），y指最大值对应的量，其余的按各自value线性渲染
                    color:['red'],
                    colorLightness: [0,1],
                    colorSaturation:[0,1]
                  }
                },
                //数据
                series : [
                  {
                    name: '各购买批次占比',
                    type: 'pie',
                    radius:['50%','70%'],
                    avoidLabelOverlap: false,
                    data: this.opinionData.sort(function (a, b) {
                      return a.value - b.value;
                    }),
                    roseType: 'radius',//角度和半径展现百分比，'area'只用半径展现
                    label: { //饼图图形的文本标签
                      normal: {  //下同，normal指在普通情况下样式，而非高亮时样式
                        textStyle: {
                          color: 'rgba(255, 255, 255, 0.3)'
                        }
                      }
                    },
                    labelLine: {  //引导线样式
                      normal: {
                        lineStyle: {
                          color: 'rgba(255, 255, 255, 0.3)'
                        },
                        smooth: 0.5, //0-1，越大越平滑弯曲
                        length: 10,  //从块到文字的第一段长
                        length2: 20  //拐弯到文字的段长
                      }
                    },
                    itemStyle: { //图例样式
                      normal: {
                        color: '#97413c',
                        shadowBlur: 50,//阴影模糊程度
                        shadowColor: 'rgba(0, 0, 0, 0.5)'//阴影颜色，一般黑
                      }
                    },

                    animationType: 'scale', //初始动画效果，scale是缩放，expansion是展开
                    animationEasing: 'elasticOut', //初始动画缓动效果
                    animationDelay: function (idx) {  //数据更新动画时长，idx限定了每个数据块从无到有的速度
                      return Math.random() * 200;
                    }
                  }
              ]
              });
              window.addEventListener("resize",function(){
                let areaColumn = echarts.init(document.getElementById('areaColumn'));
                let dateLine = echarts.init(document.getElementById('dateLine'));
                let lockNumColumn = echarts.init(document.getElementById('lockNumColumn'));
                let pieData = echarts.init(document.getElementById('pieData'));
                areaColumn.resize();
                dateLine.resize();
                lockNumColumn.resize();
                pieData.resize();
              });
            }).catch(function (error) {
              console.log(error);
            })
          },
        /**
         * 绘制产品销售的地区分布柱状图 ，地区分布 x轴：省份/直辖市名称  y轴：对应的数量
         */
        drawAreaColumn(){
          this.areaColumn = echarts.init(document.getElementById('areaColumn'));
        },
        /**
         * 绘制年份的折线图 ，x对应年份 ，y轴对应数量
         */
        drawDteLine(){
          this.dateLine = echarts.init(document.getElementById('dateLine'));
        },
        /**
         * 绘制产品销售在每个学校对应的数量 ，x轴高校名称 ，y轴对应锁的数量
         */
        drawLockNumColumn(){
          this.lockNumColumn = echarts.init(document.getElementById('lockNumColumn'));
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
