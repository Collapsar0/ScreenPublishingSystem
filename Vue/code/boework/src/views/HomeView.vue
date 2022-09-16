<template>
  <div class="homeTop">
    <div class="topLeft">
      <el-icon ><Monitor /></el-icon>
      <div class="number">
        <div>设备数量</div>
        <div class="nub">{{equipmentnub}}</div>
      </div>
    </div>
    <div class="topMiddle">
      <el-icon><VideoPlay /></el-icon>
      <div class="number">
        <div>节目数量</div>
        <div class="nub">{{programnub}}</div>
      </div>
    </div>
    <div class="topRight">
      <el-icon ><AlarmClock /></el-icon>
      <div class="number">
        <div>计划数量</div>
        <div class="nub">{{plannub}}</div>
      </div>
    </div>
  </div>
  <div class="homeMiddle">
    <div class="middleLeft">
      <div>设备状态</div>
      <div class="e">
        <div class="echart" id="main" style="width:10rem;height:12rem"/>
        <div class="nub">
          <div class="sp" style="color:#337ecc">离线   {{linmb}} 台</div>
          <div class="sp" style="color:#529b2e">播放   {{zainmb}} 台</div>
          <div class="sp" style="color:#e6a23c">空闲   {{kongnmb}} 台</div>
        </div>
      </div>
      

    </div>
    <div class="middleMiddle">
      <div>素材资源</div>
      <div class="e">
        <div class="echart" id="main2" style="width:10rem;height:12rem"/>
        <div class="nub">
          <div class="sp" style="color:#529b2e">图片   {{picnmb}}</div>
          <div class="sp" style="color:#337ecc">视频   {{videonmb}}</div>
          <div class="sp" style="color:#e6a23c">音频   {{voicenmb}}</div>
        </div>
      </div>
    </div>
    <div class="middleRight">
      <div>设备分布</div>
      <div class="e">
        <div class="echart" id="main3" style="width:35rem;height:14rem" />
      </div>
    </div>
  </div>
  <div class="homeBotton">
    <div class="bottonLeft">
      <div>计划审核提醒</div>
      <el-table
          ref="multipleTableRef" :data="tableData" style="width: 100%;">
        <el-table-column property="equipmentid" label="序号" width="80"/>
        <el-table-column property="organization" label="计划名称" width="150" />
        <el-table-column property="groupname" label="计划状态" width="120" />
        <el-table-column property="macaddresswireless" label="提交时间" width="150" />
        <el-table-column property="resolvingpower" label="提交人" width="150" />
        <el-table-column property="equipmentstate" label="操作" width="80" />
      </el-table>
    </div>
    <div class="bottonRight">
      <div>事件记录</div>
      <div class="e">
        <div class="item">
          <div class="itemtop">
            2022-7-5 18:39:22
          </div>
          <div class="itembutton">
            [素材]new素材添加成功
          </div>
        </div>
        <div class="item">
          <div class="itemtop">
            2022-7-4 15:20:01
          </div>
          <div class="itembutton">
            [节目]节目text添加成功
          </div>
        </div>
        <div class="item">
          <div class="itemtop">
            2022-7-4 07:03:24
          </div>
          <div class="itembutton">
            [素材]素材text添加成功
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {AlarmClock,VideoPlay,Monitor}from '@element-plus/icons-vue'
import {onMounted, reactive, toRefs} from "vue";
import {getEquipmentList,getProgramList,unionSearch,getMaterialSize,getEquipmentgroupList} from "@/api/home.js";
 import * as echarts from 'echarts';
export default {
  components:{AlarmClock,Monitor,VideoPlay},
  setup() {
    const state = reactive({
      equipmentnub:'0',
      programnub:'0',
      plannub:'0',
      linmb:'0',
      zainmb:'0',
      kongnmb:'0',
      picnmb:'0',
      videonmb:'0',
      voicenmb:'0',
      //图
      option: {
        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            type: 'pie',

            radius: ['60%', '80%'],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: 'center',
              fontSize: '20',
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '20',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: 7, name: '离线' },
              { value: 1, name: '播放' },
              { value: 1, name: '空闲' }
            ]
          }
        ]

      },
      option2: {
        tooltip: {
          trigger: 'item'
        },
        series: [
          {
            type: 'pie',

            radius: ['60%', '80%'],
            avoidLabelOverlap: false,
            label: {
              show: true,
              position: 'center',
              fontSize: '20',
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '20',
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: 0, name: '视频' },
              { value: 7, name: '图片'},
              { value: 0, name: '音频' }
            ]
          }
        ]

      },
      option3 :{

        xAxis: {
          type: 'category',
          data: [],
          axisLine:{ lineStyle:{color:'white'}},
          axisLabel : {interval:0,rotate:30 },
        
        },
        yAxis: {
          type: 'value',
          axisLine:{ lineStyle:{color:'white'}},
        },
        series: [
          {
            data: [],
            type: 'bar'
          }
        ]
      },
      tableData:[],
    })
    //获取数量
    const getNmb = () =>{
      getEquipmentList(1,10).then( (res) => {
        console.log(res.data)
        state.equipmentnub = res.data.data.equipment.total
      })
      getProgramList(1,10).then( (res) => {
        console.log(res.data)
        state.programnub = res.data.data.program.total
      })
      const myChart = echarts.init(document.querySelector('#main'))
      const myChart2 = echarts.init(document.querySelector('#main2'))
      const myChart3 = echarts.init(document.querySelector('#main3'))
      unionSearch('','1','','离线','','','','','','10','').then( (res) => {
        // console.log(res.data)
        state.linmb = res.data.data.equipment.total
        state.option.series[0].data[0].value = res.data.data.equipment.total        
        myChart.setOption(state.option)
      })
      unionSearch('','1','','在线','','','','','','10','').then( (res) => {
        // console.log(res.data)
        state.zainmb = res.data.data.equipment.total
        state.option.series[0].data[1].value = res.data.data.equipment.total
        myChart.setOption(state.option)
      })
      unionSearch('','1','','空闲','','','','','','10','').then( (res) => {
        // console.log(res.data)
        state.kongnmb = res.data.data.equipment.total
        state.option.series[0].data[2].value = res.data.data.equipment.total
        myChart.setOption(state.option)
      })
      getMaterialSize('1','100').then( (res) => {
        state.picnmb = res.data.data.material
        state.option2.series[0].data[1].value = state.picnmb
        myChart2.setOption(state.option2)
        state.picnmb = dataSizeChangeFunc(state.picnmb)
      })
      getEquipmentgroupList('1','100').then( (res) => {
        for(const i in res.data.data.equipmentGroups.records){
          const gname = res.data.data.equipmentGroups.records[i].groupname
          state.option3.xAxis.data[i] = gname
          state.option3.series[0].data[i] = res.data.data.equipmentGroups.records[i].equipmentcount
        }
        myChart3.setOption(state.option3)

      })
    }
    // 大小转化工具函数
    const dataSizeChangeFunc = (size) => {//把字节转换成正常文件大小
      if (!size) return "";
      const num = 1024.00; //byte
      if (size < num)
        return size + "B";
      if (size < Math.pow(num, 2))
        return (size / num).toFixed(2) + "KB"; //kb
      if (size < Math.pow(num, 3))
        return (size / Math.pow(num, 2)).toFixed(2) + "MB"; //M
      if (size < Math.pow(num, 4))
        return (size / Math.pow(num, 3)).toFixed(2) + "G"; //G
      return (size / Math.pow(num, 4)).toFixed(2) + "T"; //T
    }
    //初始化
    onMounted(()=>{
      getNmb()
      
      
      
    })
     return{
      ...toRefs(state),
      AlarmClock,
      Monitor,
      VideoPlay,
      getNmb,
      dataSizeChangeFunc,
    }
  },
}
</script>

<style lang="less" scoped>
.el-icon{
  font-size:2rem;
  height: 3rem;
  width: 3rem;
  border-radius: 50%;
  background-color:#007aff;
  color: white;
}
.homeTop{
  color: white;
  display: flex;
  .number{
    margin-left: 1rem;
    .nub{
      font-weight: 900;
      font-size: 1.2rem;
      margin-left: 0.1rem;
    }
  }
  .topLeft{
    padding: 1rem;
    display: flex;
    align-items: center;
    margin: 1rem;
    margin-right: 2rem;
    background-color:  #545c64;
    height: 5rem;
    width: 35%;
  }
  .topMiddle{
    padding: 1rem;
    display: flex;
    align-items: center;
    margin-top: 1rem;
    margin-bottom: 1.5rem;
    margin-right: 2rem;
    background-color:  #545c64;
    height: 5rem;
    width: 35%;
  }
  .topRight{
    padding: 1rem;
    display: flex;
    align-items: center;
    margin-top: 1rem;
    margin-bottom: 1.5rem;
    margin-right: 1rem;
    background-color:  #545c64;
    height: 5rem;
    width: 35%;
  }
}
.homeMiddle{
  display: flex;
  color: white;
  .middleLeft{
    padding: 1rem;
    align-items: center;
    margin-left: 1rem;
    margin-bottom: 2rem;
    margin-right: 2rem;
    background-color:  #545c64;
    height: 15rem;
    width: 30%;
    .e{
      display: flex;
      .nub{
        margin-top: 2.5rem;
        margin-left: 1rem;
        .sp{
          margin-bottom: 1.5rem;
        }
      }
    }
  }
  .middleMiddle{
    padding: 1rem;
    align-items: center;
    margin-bottom: 2rem;
    margin-right: 2rem;
    background-color:  #545c64;
    height: 15rem;
    width: 30%;
    .e{
      display: flex;
      .nub{
        margin-top: 2.5rem;
        margin-left: 1rem;
        .sp{
          margin-bottom: 1.5rem;
        }
      }
    }
  }
  .middleRight{
    padding: 1rem;
    align-items: center;
    margin-bottom: 2rem;
    margin-right: 1rem;
    background-color:  #545c64;
    height: 15rem;
    width: 45%;
  }
}
.homeBotton{
  display: flex;
  .bottonLeft{
    color: #fff;
    padding: 1rem;
    align-items: center;
    margin-left: 1rem;
    margin-bottom: 1rem;
    margin-right: 2rem;
    background-color:  #545c64;
    height: 15rem;
    width: 63.5%;
    .el-table{
      margin-top: 1rem;
      --el-table-row-hover-bg-color:#545c64;
      color:white;
      --el-table-header-text-color: white;
      --el-table-header-bg-color: #434950;
      --el-table-tr-bg-color: #545c64;
      --el-table-border-color: #6e6e6e;
      --el-table-border: 1px solid#6e6e6e;
      --el-fill-color-blank: #545c64;
      --el-table-border-color:#545c64;
    }
  }
  .bottonRight{
    color: #fff;
    padding: 1rem;
    align-items: center;
    margin-bottom: 1rem;
    margin-right: 1rem;
    background-color:  #545c64;
    height: 15rem;
    width: 45%;
    .e{
      margin-left: 10px;
      margin-top: 1rem;
      .item{
        border-bottom:1px solid #7c7c7c;
        .itemtop{
          margin-top: 0.5rem;
          font-size: 0.5rem;
          color: #909090;
        }
        .itembutton{
          font-size: 0.9rem;
          margin-bottom: 0.5rem;
        }
      }
    }
  }
}
</style>