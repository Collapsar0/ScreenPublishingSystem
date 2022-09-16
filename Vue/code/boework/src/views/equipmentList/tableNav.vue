<template>
<!--  搜索行-->
  <div class="box">
    <!--    设备名称-->
    <div class="condition">
      <div class="condition-text">
        设备名称:
      </div>
      <div class="inputBox">
        <el-input
            v-model="inputName"
            placeholder="请输入设备名"
            class="input-with-select"
        >
          <template #append>
            <el-button :icon="Search" />
          </template>
        </el-input>
      </div>

    </div>
    <!--    所属机构-->
    <div class="condition">
      <div class="condition-text">
        所属机构:
      </div>
      <el-select v-model="organizationValue" clearable class="m-2" placeholder="请选择所属机构">
        <el-option
            v-for="item in organizationOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>

    </div>
    <!--    所属分组-->
    <div class="condition">
      <div class="condition-text">
        所属分组:
      </div>
      <el-select v-model="groupValue" clearable class="m-2" placeholder="请选择所属分组">
        <el-option
            v-for="item in groupOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>

    </div>
    <!--    MAC地址-->
    <div class="condition">
      <div class="condition-text">
        MAC地址:
      </div>
      <div class="inputBox">
        <el-input
            v-model="inputMACAddress"
            placeholder="请输入MAC地址"
            class="input-with-select"
        >
          <template #append>
            <el-button :icon="Search" />
          </template>
        </el-input>
      </div>

    </div>
    <!--    分辨率-->
    <div class="condition">
      <div class="condition-text">
        分辨率:
      </div>
      <el-select v-model="resolvingPowerValue" clearable class="m-2" placeholder="请选择分辨率">
        <el-option
            v-for="item in resolvingPowerOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>

    </div>
    <!--    设备状态-->
    <div class="condition">
      <div class="condition-text">
        设备状态:
      </div>
      <el-select v-model="equipmentStateValue" clearable class="m-2" placeholder="">
        <el-option
            v-for="item in equipmentStateOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>

    </div>
    <!--    系统版本-->
    <div class="condition">
      <div class="condition-text">
        系统版本:
      </div>
      <el-select v-model="systemUpgradeValue" clearable class="m-2" placeholder="">
        <el-option
            v-for="item in systemUpgradeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>

    </div>
    <!--    当前计划-->
    <div class="condition">
      <div class="condition-text">
        当前计划:
      </div>
      <el-select v-model="nowPlanValue" clearable class="m-2" placeholder="请选择当前计划">
        <el-option
            v-for="item in nowPlanOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>

    </div>
    <!--    SN码-->
    <div class="condition">
      <div class="condition-text">
        SN:
      </div>
      <div class="inputBox">
        <el-input
            v-model="inputSNCode"
            placeholder="请输入SN号"
            class="input-with-select"
        >
          <template #append>
            <el-button :icon="Search" />
          </template>
        </el-input>
      </div>

    </div>
  </div>
  <div class="buttonLineTop">
    <div class="buttonBox">
      <el-button plain @click="cleanAll">重置</el-button>
      <el-button type="primary" @click="unionSearchFunc">查询</el-button>
    </div>
  </div>
<!--  按钮行-->
  <div class="buttonLine">
    <div class="buttonBox">
      <el-button type="primary" @click="addEquipmentFormVisible=true">新增设备</el-button>
      <el-button>批量删除</el-button>
    </div>
  </div>
<!--  表格行-->
  <div style="width: 100%">
    <el-table
        ref="multipleTableRef"
        :data="tableData"
        style="width: 100%;"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"/>
      <el-table-column label="设备名称" width="120">
        <template #default="scope">{{ scope.row.equipmentname }}</template>
      </el-table-column>
      <el-table-column property="organization" label="所属机构" width="150"/>
      <el-table-column property="groupname" label="所属分组" width="120"/>
      <el-table-column property="macaddresswireless" label="MAC地址" width="150"/>
      <el-table-column property="resolvingpower" label="分辨率" width="150"/>
      <el-table-column property="equipmentstate" label="设备状态" width="80"/>
      <el-table-column property="systemupdate" label="系统升级" width="120"/>
      <el-table-column property="nowplan" label="当前计划" width="120"/>
      <el-table-column label="操作" width="231">
        <template #default="scope">
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="openEquipmentDetail(scope.$index, scope.row)"
          >
            详情
          </el-button>
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="remoteControlVisible=true"
          >
            控制
          </el-button>
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="refresh"
          >
            刷新
          </el-button>
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="editEquipmentFormFunc(scope.$index, scope.row)"
          >
            编辑
          </el-button>
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="deleteThisRow(scope.$index, scope.row)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

<!--  分页部分-->
  <div class="bottomNav">
    <el-pagination
        v-model:currentPage="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :small="false"
        :disabled="false"
        :background="false"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalCount"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
    />
  </div>

  <!--  添加设备弹出框-->
  <el-dialog v-model="addEquipmentFormVisible" title="新增设备" width="35%">
    <el-form :model="equipmentForm">
      <el-form-item label="设备名称" :label-width="formLabelWidth">
        <el-input v-model="equipmentForm.name" style="width: 300px;" autocomplete="off" placeholder="例如：设备一"/>
      </el-form-item>
      <el-form-item label="设备MAC地址" :label-width="formLabelWidth">
        <el-input v-model="equipmentForm.MACAddress" style="width: 300px;" autocomplete="off" placeholder="例如：805E4FE5E3996"/>
      </el-form-item>
      <el-form-item label="设备所属机构id" :label-width="formLabelWidth">
        <el-input v-model="equipmentForm.organizationID" style="width: 300px;" autocomplete="off" placeholder="例如：10102"/>
      </el-form-item>
      <el-form-item label="设备SN码" :label-width="formLabelWidth">
        <el-input v-model="equipmentForm.SNCode" style="width: 300px;" autocomplete="off" placeholder="例如：T232BS200721000209"/>
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addEquipmentFormVisible = false">返回</el-button>
        <el-button type="primary" @click="addNewEquipment"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
  <!--  设备详情弹窗-->
  <el-dialog v-model="equipmentDetailVisible" title="设备详情" width="45%">
<!--    标签页-->
    <el-tabs
        v-model="activeName"
        type="card"
        class="demo-tabs"
        @tab-click="handleClick"
    >
      <el-tab-pane label="设备信息" name="first">
        <div class="detailBox">
          <div class="infoLine">
            设备型号：{{openRowInfo.equipmenttype}}
          </div>
          <div class="infoLine">
            系统版本：{{openRowInfo.systemedition}}
          </div>
          <div class="infoLine">
            设备IP:{{openRowInfo.ip}}
          </div>
          <div class="infoLine">
            信发版本：{{openRowInfo.sendingedition}}
          </div>
          <div class="infoLine">
            有线MAC地址：{{openRowInfo.macaddresswired}}
          </div>
          <div class="infoLine">
            无线MAC地址：{{openRowInfo.macaddresswireless}}
          </div>
          <div class="infoLine">
            运行内存：{{openRowInfo.ram}}
          </div>
          <div class="infoLine">
            SN：{{openRowInfo.snnumber}}
          </div>
          <div class="infoLine">
            存储空间： {{openRowInfo.rom}}
          </div>
          <div class="infoLine">
            激活时间：{{openRowInfo.activationtime}}
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="安装信息" name="second">
        <div class="detailBox">
          <div class="infoLine">
            设备名称：{{openRowInfo.equipmentname}}
          </div>
          <div class="infoLine">
            注册时间：{{openRowInfo.runtime}}
          </div>
          <div class="infoLine">
            所属分组: {{openRowInfo.groupname}}
          </div>
          <div class="infoLine">
            分辨率：{{openRowInfo.resolvingpower}}
          </div>
          <div class="infoLine">
            所属机构：{{openRowInfo.organization}}
          </div>
          <div class="infoLine">
            屏显方式：{{openRowInfo.screendisplay}}
          </div>
          <div class="infoLine">
            安装位置：{{openRowInfo.installationposition}}
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="状态信息" name="third">
        <div class="detailBox1">
          <div class="infoLine">
            设备状态：{{openRowInfo.equipmentstate}}
          </div>
          <div class="infoLine">
            当前计划：{{openRowInfo.nowplan}}
          </div>
          <div class="infoLine">
            设备运行时长: --
          </div>
          <div class="infoLine">
            最后心跳时间: {{openRowInfo.runtime}}
          </div>
        </div>
      </el-tab-pane>
    </el-tabs>
<!--    底部内容-->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="equipmentDetailVisible = false">返回</el-button>
      </span>
    </template>
  </el-dialog>
  <!--  远程控制弹窗-->
  <el-dialog v-model="remoteControlVisible" title="远程控制" width="50%">
    <!--    标签页-->
    <el-tabs
        v-model="activeName2"
        type="card"
        class="demo-tabs"
        @tab-click="handleClick"
    >
      <el-tab-pane label="屏幕截图" name="first">
        <div style="width: 90%">
          <div style="display: flex;flex-direction: column;margin: auto;width: 80%;">
            <el-button @click="getScreen" style="width: 100px;margin-bottom: 5px">屏幕截图</el-button>
            <el-image
                style="width: 400px; height: 400px"
                :src="screenUrl"
                :preview-src-list="screenUrlList"
                :initial-index="screenIndex"
                fit="cover"
            >
              <template #placeholder>
                <div class="image-slot">Waiting<span class="dot">...</span></div>
              </template>
            </el-image>
          </div>
        </div>
      </el-tab-pane>
<!--      <el-tab-pane label="定时开关机" name="second">定时开关机</el-tab-pane>-->
      <el-tab-pane label="关闭系统" name="third">
        <el-button @click="rebootVisible=true">关闭系统</el-button>
      </el-tab-pane>
<!--      <el-tab-pane label="音量控制" name="fourth">音量控制</el-tab-pane>-->
<!--      <el-tab-pane label="亮度控制" name="fifth">亮度控制</el-tab-pane>-->
<!--      <el-tab-pane label="系统升级" name="sixth">系统升级</el-tab-pane>-->
      <el-tab-pane label="清理缓存" name="seventh">
        <el-button @click="clearVisible=true">清理缓存</el-button>
      </el-tab-pane>
    </el-tabs>
    <!--    底部内容-->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="remoteControlVisible = false">返回</el-button>
      </span>
    </template>
  </el-dialog>
  <!--  编辑设备弹出框-->
  <el-dialog v-model="editEquipmentFormVisible" title="新增设备" width="35%">
    <el-form :model="equipmentForm">
      <el-form-item label="设备名称" :label-width="formLabelWidth">
        <el-input v-model="editEquipmentForm.name" style="width: 200px;" autocomplete="off" placeholder="例如：设备一"/>
      </el-form-item>
      <el-form-item label="所属分组" :label-width="formLabelWidth">
        <el-select v-model="editEquipmentForm.group" >
          <el-option
              v-for="item in groupOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editEquipmentFormVisible = false">返回</el-button>
        <el-button type="primary" @click="modifyEquipmentInfo"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>

  <!-- 发布 -->
  <el-dialog v-model="rebootVisible" width="30%">
    <span>是否关闭设备系统？</span>
    <template #footer >
        <span class="dialog-footer">
          <el-button @click="rebootVisible = false">取消</el-button>
          <el-button type="primary" @click="rebootFunc">确认</el-button>
        </span>
    </template>
  </el-dialog>

  <!-- 缓存 -->
  <el-dialog v-model="clearVisible" width="30%">
    <span>是否清理缓存？</span>
    <template #footer >
        <span class="dialog-footer">
          <el-button @click="clearVisible = false">取消</el-button>
          <el-button type="primary" @click="clearFunc">确认</el-button>
        </span>
    </template>
  </el-dialog>

</template>

<script>
import {getCurrentInstance, onMounted, reactive, toRefs} from "vue";
import { ElTable,ElMessage  } from 'element-plus';
import { Search } from '@element-plus/icons-vue';
import {addOneEquipment, deleteEquipment, getEquipmentList, modifyInfo, unionSearch, getdList,getEquipmentGroupByID} from "@/api/equipmentList";

export default {
  setup(){
    let socket;
    const state = reactive({
      socket,
      baseUrl: getCurrentInstance()?.appContext.config.globalProperties.$baseUrl,
      rebootVisible:false,
      // 搜索框相关
      clearVisible:false,

      screenUrl:'http://47.99.58.44:8081/src/2022/07/05/c97445636d1d4b05bd3d0e8a842cf58b.jpg',
      screenUrlList:['http://47.99.58.44:8081/src/2022/07/05/c97445636d1d4b05bd3d0e8a842cf58b.jpg'],
      screenIndex:0,

      // 输入框绑定变量
      inputName: '',
      inputMACAddress: '',
      inputSNCode: '',
      // 选择器绑定变量
      organizationValue: '',
      groupValue: '',
      resolvingPowerValue: '',
      equipmentStateValue: '',
      systemUpgradeValue: '',
      nowPlanValue: '',
      // 选择器选项绑定数组
      organizationOptions: [
      ],
      resolvingPowerOptions:[
        {
          value: '',
          label: '全部分辨率',
        },
        {
          value: '1920x1080(横)',
          label: '1920x1080(横)',
        },
        {
          value: '1080x1920(竖)',
          label: '1080x1920(竖)',
        },
        {
          value: '3840x2160(横)',
          label: '3840x2160(横)',
        },
        {
          value: '2160x3840(竖)',
          label: '2160x3840(竖)',
        },
      ],
      equipmentStateOptions:[
        {
          value: '',
          label: '全部',
        },
        {
          value: '播放',
          label: '播放',
        },
        {
          value: '空闲',
          label: '空闲',
        },
        {
          value: '离线',
          label: '离线',
        },
      ],
      systemUpgradeOptions:[
        {
          value: '',
          label: '全部',
        },
        {
          value: '已是最新',
          label: '已是最新',
        },
        {
          value: '升级',
          label: '升级',
        },
        {
          value: '有新版本',
          label: '有新版本',
        },
      ],
      nowPlanOptions: [
      ],


      // 表格相关数据
      multipleSelection:[],
      tableData: [],
      // 添加设备弹出框
      addEquipmentFormVisible: false,
      formLabelWidth: '140px',
      //设备详情
      equipmentDetailVisible: false,
      activeName: 'first',
      openRowInfo: {},
      // 远程控制
      remoteControlVisible: false,
      activeName2: 'first',
      // 编辑设备
      editEquipmentFormVisible: false,
      // 分组信息
      groupOptions:[
      ],
      // 分页信息
      currentPage: 1,
      pageSize: 10,
      totalCount: 100,
    })

    // 新建设备表内容
    const equipmentForm = reactive({
      name: '',
      MACAddress: '',
      organizationID: '',
      SNCode: '',
    })

    // 新建设备
    const addNewEquipment = () =>{
      state.addEquipmentFormVisible = false

      addOneEquipment(equipmentForm.SNCode,equipmentForm.name,equipmentForm.MACAddress,equipmentForm.organizationID).then( (res) => {
        console.log(res.data)

        if(res.data.code === 200){
          ElMessage({
            message: '新增设备成功!',
            type: 'success',
          })
          state.currentPage = 1
          getList()
        }

      }).finally( () => {
        console.log()
      })
    }

    // 编辑设备内容表
    const editEquipmentForm = reactive({
      id: '',
      name: '',
      group:'',
    })
    // 标签页点击
    const handleClick = (tab, event) => {
      console.log(tab, event)
    }
    // 设备详情内容信息
    const equipmentDetailInfo = reactive({
      name: 'HiDPTAndroid Hi3751V553',
      SystemEdition: 'BOE_iGallery32_V13103_V5.2.0',
      IP: '192.168.53.212',
      transEdition: '1.3.1',
      lineMac: 'A0BB3ED3861D',
      wireLessMac: 'A0BB3ED2F376',
      Ram: '0.96 GB',
      SNCode: 'T232BS200721000123',
      ROM: ' 4.65 GB可用（共 5.39 GB）',
      activeTime: '2022-06-23 10:40:12',
    })

    const handleSelectionChange = (val) => {
      state.multipleSelection.value = val
    }

    const refresh = () => {
      getList()
      ElMessage({
        message: '刷新成功',
        type: 'success',
      })
    }

    // 编辑设备信息
    const editEquipmentFormFunc = (index,row) =>{
      state.editEquipmentFormVisible=true;
      console.log(index,row)
      editEquipmentForm.id = row.equipmentid
      editEquipmentForm.name = row.equipmentname
      editEquipmentForm.group = row.groupname
    }

    // 删除当前行数据
    const deleteThisRow = (index,row) =>{
      console.log(index,row)
      deleteEquipment(row.equipmentid).then( (res) => {
        console.log(res.data)
        if(res.data.code === 200){
          ElMessage({
            message: '设备删除成功!',
            type: 'success',
          })
          state.currentPage = 1
          getList()
        }
      })
    }

    // 改变分页单页大小
    const handleSizeChange = (val) =>{
      console.log(`${val} items per page`)
      state.pageSize = val
      getList()
    }

    // 修改当前分页
    const handleCurrentChange = (val) =>{
      console.log(`current page: ${val}`)
      state.currentPage = val
      getList()
    }

    // 获取设备列表
    const getList = () =>{
      getEquipmentList(state.currentPage,state.pageSize).then( (res) => {
        console.log(res.data)
        state.totalCount = res.data.data.equipment.total
        state.tableData = res.data.data.equipment.records
        for (const line of state.tableData) {
          line.runtime = new Date(line.runtime).format("yyyy-MM-dd hh:mm:ss");
        }
        for(const i in state.tableData){
          getEquipmentGroupByID(state.tableData[i].equipmentGroupID).then( (res1) => {
            // console.log(res1.data)
            state.tableData[i].groupname = res1.data.data.equipmentGroup.groupname
          })
        }
      })
    }

    // 打开界面详情页内容
    const openEquipmentDetail = (index,row) =>{
      console.log(index,row)
      state.equipmentDetailVisible=true
      state.openRowInfo = row
    }

    // 修改设备信息
    const modifyEquipmentInfo = () =>{
      state.editEquipmentFormVisible = false
      modifyInfo(editEquipmentForm.group,editEquipmentForm.id,editEquipmentForm.name).then( (res) => {
        console.log(res.data)
        if(res.data.code === 200){
          ElMessage({
            message: '设备信息修改成功!',
            type: 'success',
          })
          state.currentPage = 1
          getList()
        }
      })
    }

    const unionSearchFunc = () => {
      state.currentPage = 1
      unionSearch(state.inputSNCode,state.currentPage,state.inputName,state.equipmentStateValue,
          state.groupValue,state.inputMACAddress,state.nowPlanValue,
          state.organizationValue,state.resolvingPowerValue,state.pageSize,state.systemUpgradeValue).then( (res) => {
        console.log(res.data)
        state.totalCount = res.data.data.equipment.total
        state.tableData = res.data.data.equipment.records
        for(const i in state.tableData){
          getEquipmentGroupByID(state.tableData[i].equipmentGroupID).then( (res1) => {
            console.log(res1.data)
            state.tableData[i].groupname = res1.data.data.equipmentGroup.groupname
          })
        }
      })
    }

    // 清除全部
    const cleanAll = () =>{
      state.inputName=''
      state.inputMACAddress=''
      state.inputSNCode=''
      state.organizationValue=''
      state.groupValue=''
      state.resolvingPowerValue=''
      state.equipmentStateValue=''
      state.systemUpgradeValue=''
      state.nowPlanValue=''
    }
    //搜索分组初始化
    const getSearch = () =>{
      getdList('equipmentGroupID').then( (res) => {
        console.log(res.data)
        for(const i in res.data.data.List){
          getEquipmentGroupByID(res.data.data.List[i].equipmentGroupID).then( (res1) => {
            // console.log(res1.data)
            const a = res1.data.data.equipmentGroup.groupname
            const b = {}
            b.value = a
            b.label = a
            state.groupOptions.push(b)
          })
        }
      })
      getdList('nowPlan').then( (res) => {
        // console.log(res.data)
        for(const i in res.data.data.List){
          const a = res.data.data.List[i].nowplan
          const b = {}
          b.value = a
          b.label = a
          state.nowPlanOptions.push(b)
        }
      })
      getdList('organization').then( (res) => {
        console.log(res.data)
        for(const i in res.data.data.List){
          const a = res.data.data.List[i].organization
          const b = {}
          b.value = a
          b.label = a
          state.organizationOptions.push(b)
        }
      })
    }
    //时间
    Date.prototype.format = function(fmt) {
      var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
      };
      if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
      }
      for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
      }
      return fmt;
    }

    // 重启系统
    const rebootFunc = () =>{
      state.rebootVisible = false
      let message = {from: "PC001", to: "Android001", text: 'reboot'}
      state.socket.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
      console.log(message)
    }

    // 屏幕截图
    const getScreen = () =>{
      let message = {from: "PC001", to: "Android001", text: 'screen'}
      state.socket.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
      console.log(message)
    }

    // 清理缓存
    const clearFunc = () =>{
      state.clearVisible = false
      let message = {from: "PC001", to: "Android001", text: 'clear'}
      state.socket.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
      console.log(message)
    }

    // socket关闭
    const socketClose = () =>{
      console.log("websocket已关闭");
    }

    // socket错误
    const socketError = () =>{
      console.log("websocket发生了错误");
    }

    //搜索计划初始化
    onMounted(()=>{
      getList()
      getSearch()
      // 初始化Socket
      if (typeof (WebSocket) == "undefined") {
        console.log("您的浏览器不支持WebSocket");
      } else {
        console.log("您的浏览器支持WebSocket");
        let socketUrl = "ws://"+state.baseUrl.slice(7)+"/imserver/" + "PC001";
        if (state.socket != null) {
          state.socket.close();
          state.socket = null;
        }
        // 开启一个websocket服务
        state.socket = new WebSocket(socketUrl);
        //打开事件
        state.socket.onopen = function () {
          console.log("websocket已打开");
        };
        //  浏览器端收消息，获得从服务端发送过来的文本消息
        state.socket.onmessage = function (msg) {
          console.log("收到数据====" + msg.data)
          let data = JSON.parse(msg.data)  // 对收到的json数据进行解析， 类似这样的： {"users": [{"username": "zhang"},{ "username": "admin"}]}

          if(data.text){
            console.log(data.text)
            if(state.screenIndex ===0){
              const img = decodeURIComponent(data.text)
              state.screenUrl = img
              state.screenUrlList[0]=img
              console.log(decodeURIComponent(img))
              state.screenIndex++
            }else{
              const img = decodeURIComponent(data.text)
              state.screenUrl = img
              state.screenUrlList.push(img)
              console.log(decodeURIComponent(img))
              state.screenIndex++
            }

          }

          // console.log(data)
        };
        //关闭事件
        state.socket.onclose = socketClose
        //发生了错误事件
        state.socket.onerror = socketError
      }
    })

    return {
      getSearch,
      ...toRefs(state),
      ElTable,
      handleSelectionChange,
      equipmentForm,
      handleClick,
      equipmentDetailInfo,
      ElMessage,
      refresh,
      editEquipmentForm,
      editEquipmentFormFunc,
      deleteThisRow,
      handleSizeChange,
      handleCurrentChange,
      addNewEquipment,
      getList,
      openEquipmentDetail,
      modifyEquipmentInfo,
      Search,
      unionSearchFunc,
      cleanAll,
      rebootFunc,
      getScreen,
      clearFunc,
    }
  },
  created() {

  }

}
</script>
<style lang="less" scoped>
//搜索框
.box{
  width: 100%;
  margin: auto;
  background-color: #545c64;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  flex-wrap: wrap;
  .condition{
    width: 350px;
    display: flex;
    flex-direction: row;
    align-items: center;
    padding-top: 20px;
    padding-bottom: 20px;
    .condition-text{
      color: white;
      margin-right: 10px;
      width: 85px;
      text-align: right;
    }
    .inputBox{

    }
  }
}
.buttonLineTop{
  width: 100%;
  background-color: #545c64;
  padding-bottom: 20px;
  .buttonBox{
    margin-left: 85%;
  }
}
//表格行样式
.buttonLine{
  width: 100%;
  margin-top: 10px;
  padding-top: 20px;
  padding-bottom: 20px;
  display: flex;
  flex-direction: row;
  justify-content: end;
  background-color: #545c64;
  .buttonBox{
    margin-right: 20px;
  }
}
//表格样式设为主题色
.el-table{
  --el-table-row-hover-bg-color:#434950;
  color:white;
  --el-table-header-text-color: white;
  --el-table-header-bg-color: #434950;
  --el-table-tr-bg-color: #545c64;
  --el-table-border-color: #6e6e6e;
  --el-table-border: 1px solid#6e6e6e;
}
::v-deep .el-table{
  width: 100%;
  .el-table__header-wrapper table,.el-table__body-wrapper table{
    width: 100% !important;
  }
  .el-table__body, .el-table__footer, .el-table__header{
    table-layout: auto;
  }
}

.el-table__header{
  width: 100% !important;
}
.el-table__body{
  width: 100% !important;
}
.detailBox{
  width: 100%;
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  .infoLine{
    width: 50%;
    padding: 10px;
  }
}
.detailBox1{
  width: 100%;
  flex-direction: row;
  flex-wrap: wrap;
  .infoLine{
    width: 50%;
    padding: 10px;
  }
}

// 分页部分
.bottomNav {
  width: 100%;
  background-color: #545c64;
  color: white;
  padding: 15px 0 15px;
  display: flex;
  justify-content: end;
  .el-pagination{
    --el-text-color-regular: black;
  }
}

.image-slot {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background: var(--el-fill-color-light);
  color: var(--el-text-color-secondary);
  font-size: 14px;
}
.dot {
  animation: dot 2s infinite steps(3, start);
  overflow: hidden;
}

</style>
