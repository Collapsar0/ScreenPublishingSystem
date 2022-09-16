<template>
  <!--  搜索行-->
  <div class="top">
    <!--  公告状态-->
    <div class="condition">
      <div class="condition-text">公告状态:</div>
      <div class="inputBox">
        <el-select v-model="noticeStateValue" clearable class="m-2" placeholder="请选择公告状态">
          <el-option
              v-for="item in noticeStateOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </div>
    </div>
    <!-- 按钮 -->
    <div class="buttonTop">
      <div class="buttonBox">
        <el-button plain @click="cleanAll">重置</el-button>
        <el-button type="primary" @click="unionSearchFunc">查询</el-button>
      </div>
    </div>
  </div>
  <!--  按钮行-->
  <div class="buttonLine">
    <div class="buttonBox">
      <el-button type="primary" @click="openadd">创建公告</el-button>
    </div>
  </div>
  <!--  表格行-->
  <div style="width: 100%">
    <el-table
        ref="multipleTableRef" class="eltable"
        :data="tableData"
        style="width: 100%;"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"/>
      <el-table-column property="noticecontent" label="公告内容" width="430"/>
      <el-table-column property="playbacktime" label="播放时间" width="170"/>
      <el-table-column property="noticestate" label="公告状态" width="120"/>
      <el-table-column property="author" label="作者" width="120"/>
      <el-table-column property="playbacktime" label="创建时间" width="170"/>
      <el-table-column label="操作" width="230">
        <template #default="scope">
          <!--  -->

          <el-button link type="primary" size="small" @click="openEquipmentDetail(scope.$index, scope.row)">
            详细
          </el-button>
          <el-button link type="primary" size="small" @click.prevent="editGroupFormFunc(scope.$index, scope.row)">
            编辑
          </el-button>
          <el-button link type="primary" size="small" @click.prevent="copyNotice(scope.$index, scope.row)">
            复制
          </el-button>
          <el-button link type="primary" size="small" @click="submitFunc(scope.$index, scope.row)">
            发布
          </el-button>

          <el-button link type="primary" size="small" @click.prevent="deleteThisRow(scope.$index, scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <!-- 发布 -->
  <el-dialog v-model="submitVisible" width="30%">
    <span>是否发布</span>
    <template #footer >
        <span class="dialog-footer">
          <el-button @click="submitVisible = false">取消</el-button>
          <el-button type="primary" @click="submit">确认</el-button>
        </span>
    </template>
  </el-dialog>

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
  <!--  设备详情弹窗-->
  <el-dialog v-model="noticeDetailVisible" title="设备详情" width="66%">
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
            标题：{{openRowInfo.noticetitle}}
          </div>
          <div class="infoLine">
            内容：{{openRowInfo.noticeid}}
          </div>
          <div class="infoLine">
            开始时间:{{openRowInfo.starttime}}
          </div>
          <div class="infoLine">
            结束时间：{{openRowInfo.endtime}}
          </div>
        </div>
      </el-tab-pane>
      <el-tab-pane label="设备详细" name="second">
        <el-table
            ref="multipleTableRef"
            :data="nemtableData"
            style="width: 100%;"
        >
          <el-table-column property="equipmentname" label="设备名称" width="200" />
          <el-table-column property="macaddresswireless" label="MAC地址" width="200" />
          <el-table-column property="resolvingpower" label="分辨率" width="200" />
          <el-table-column property="organization" label="所属机构" width="165" />
          <el-table-column property="nowplan" label="当前计划" width="120" />
          <el-table-column property="equipmentstate" label="设备状态" width="80" />
        </el-table>
        <!--  分页部分-->
        <!-- <div class="bottomNav1">
            <el-pagination
                v-model:currentPage="nemcurrentPage"
                v-model:page-size="nempageSize"
                :page-sizes="[10, 20, 50, 100]"
                :small="false"
                :disabled="false"
                :background="false"
                layout="total, sizes, prev, pager, next, jumper"
                :total="emtotalCount"
                @size-change="emhandleSizeChange"
                @current-change="emhandleCurrentChange"
            />
        </div> -->
      </el-tab-pane>
    </el-tabs>
    <!--    底部内容-->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="equipmentDetailVisible = false">返回</el-button>
      </span>
    </template>
  </el-dialog>
  <!--  创建公告弹出框-->
  <el-dialog v-model="addNoticeFormVisible" title="创建公告" width="70%">
    <el-form :model="noticeForm">
      <el-form-item label="文字颜色" :label-width="formLabelWidth">
        <el-input v-model="noticeForm.textColor" style="width: 300px;" autocomplete="off" placeholder=""/>
        <el-color-picker v-model="noticeForm.textColor" />
      </el-form-item>
      <el-form-item label="文字字号" :label-width="formLabelWidth">
        <el-input v-model="noticeForm.textSize" style="width: 300px;" autocomplete="off" placeholder=""/>
        <el-slider v-model="noticeForm.textSize" />
      </el-form-item>
      <el-form-item label="背景颜色" :label-width="formLabelWidth">
        <el-input v-model="noticeForm.backgroundColor" style="width: 300px;" autocomplete="off" placeholder=""/>
        <el-color-picker v-model="noticeForm.backgroundColor" />
      </el-form-item>
      <el-form-item label="背景高度" :label-width="formLabelWidth">
        <el-input v-model="noticeForm.backgroundHeight" style="width: 300px;" autocomplete="off" placeholder=""/>
        <el-slider v-model="noticeForm.backgroundHeight" />
      </el-form-item>
      <el-form-item label="文字位置" :label-width="formLabelWidth">
        <el-select v-model="noticeForm.textPosition" clearable class="m-2" style="width: 300px;" autocomplete="off" placeholder=" ">
          <el-option
              v-for="item in textPositionOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="播放速度" :label-width="formLabelWidth">
        <el-select v-model="noticeForm.playbackSpeed" clearable class="m-2" style="width: 300px;" autocomplete="off" placeholder=" ">
          <el-option
              v-for="item in playbackSpeedOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="公告内容" :label-width="formLabelWidth">
        <el-input v-model="noticeForm.noticeContent" style="width: 300px;" autocomplete="off" placeholder="请输入公告内容"/>
      </el-form-item>
      <el-form-item label="设备选择" :label-width="formLabelWidth">
      </el-form-item>
      <!--  表格行-->
      <el-table
          ref="multipleTableRef"
          :data="emtableData"
          style="width: 100%;"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column property="equipmentname" label="设备名称" width="200"/>
        <el-table-column property="macaddresswireless" label="MAC地址" width="200" />
        <el-table-column property="resolvingpower" label="分辨率" width="200" />
        <el-table-column property="organization" label="所属机构" width="165" />
        <el-table-column property="nowplan" label="当前计划" width="120" />
        <el-table-column property="equipmentstate" label="设备状态" width="80" />
      </el-table>
      <!--  分页部分-->
      <div class="bottomNav1">
        <el-pagination
            v-model:currentPage="emcurrentPage"
            v-model:page-size="empageSize"
            :page-sizes="[10, 20, 50, 100]"
            :small="false"
            :disabled="false"
            :background="false"
            layout="total, sizes, prev, pager, next, jumper"
            :total="emtotalCount"
            @size-change="emhandleSizeChange"
            @current-change="emhandleCurrentChange"
        />
      </div>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addNoticeFormVisible = false">返回</el-button>
        <el-button type="primary" @click="addNewNotice"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
  <!--  编辑公告弹出框-->
  <el-dialog v-model="editNoticeFormVisible" title="编辑公告" width="70%">
    <el-form :model="noticeForm">
      <el-form-item label="文字颜色" :label-width="formLabelWidth">
        <el-input v-model="noticeForm.textColor" style="width: 300px;" autocomplete="off" placeholder=""/>
        <el-color-picker v-model="noticeForm.textColor" />
      </el-form-item>
      <el-form-item label="文字字号" :label-width="formLabelWidth">
        <el-input v-model="noticeForm.textSize" style="width: 300px;" autocomplete="off" placeholder=""/>
        <el-slider v-model="noticeForm.textSize" />
      </el-form-item>
      <el-form-item label="背景颜色" :label-width="formLabelWidth">
        <el-input v-model="noticeForm.backgroundColor" style="width: 300px;" autocomplete="off" placeholder=""/>
        <el-color-picker v-model="noticeForm.backgroundColor" />
      </el-form-item>
      <el-form-item label="背景高度" :label-width="formLabelWidth">
        <el-input v-model="noticeForm.backgroundHeight" style="width: 300px;" autocomplete="off" placeholder=""/>
        <el-slider v-model="noticeForm.backgroundHeight" />
      </el-form-item>
      <el-form-item label="文字位置" :label-width="formLabelWidth">
        <el-select v-model="noticeForm.textPosition" clearable class="m-2" style="width: 300px;" autocomplete="off" placeholder=" ">
          <el-option
              v-for="item in textPositionOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="播放速度" :label-width="formLabelWidth">
        <el-select v-model="noticeForm.playbackSpeed" clearable class="m-2" style="width: 300px;" autocomplete="off" placeholder=" ">
          <el-option
              v-for="item in playbackSpeedOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="公告内容" :label-width="formLabelWidth">
        <el-input v-model="noticeForm.noticeContent" style="width: 300px;" autocomplete="off" placeholder="请输入公告内容"/>
      </el-form-item>
      <el-form-item label="设备选择" :label-width="formLabelWidth">
      </el-form-item>
      <!--  表格行-->
      <el-table
          ref="multipleTableRef"
          :data="emtableData"
          style="width: 100%;"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column property="equipmentname" label="设备名称" width="200"/>
        <el-table-column property="macaddresswireless" label="MAC地址" width="200" />
        <el-table-column property="resolvingpower" label="分辨率" width="200" />
        <el-table-column property="organization" label="所属机构" width="165" />
        <el-table-column property="nowplan" label="当前计划" width="120" />
        <el-table-column property="equipmentstate" label="设备状态" width="80" />
      </el-table>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editNoticeFormVisible = false">返回</el-button>
        <el-button type="primary" @click="editNotice"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
</template>

<script>

import { Search } from '@element-plus/icons-vue';
import { onMounted, reactive, toRefs, getCurrentInstance} from "vue";
import { ElTable,ElMessage  } from 'element-plus';
import {getMaterialList,renameMaterial,addOneMaterial,unionSearch,deletematerial} from "@/api/materiaList";
import {getNoticeList,getem,getEquipmentList,addNotice,updateNotice,deleteNotice,searchNotice} from "@/api/noticeList";
export default {
  setup() {
    let socket;
    const state = reactive({
      socket,
      baseUrl: getCurrentInstance()?.appContext.config.globalProperties.$baseUrl,
      formLabelWidth: '140px',
      multipleSelection:[],
      tableData:[
      ],
      emtableData:[
      ],
      nemtableData:[
      ],
      fileList:[],
      actionUrl: getCurrentInstance()?.appContext.config.globalProperties.$baseUrl + '/file/upload',
      materialIdList:[],
      timeList:[],
      previewurl:'',
      noticeStateValue:'',
      noticeStateOptions:[
        {
          value: '',
          label: '全部',
        },
        {
          value: '待发布',
          label: '待发布',
        },
        {
          value: '发布中',
          label: '发布中',
        },
        {
          value: '播放中',
          label: '播放中',
        },
        {
          value: '部分成功',
          label: '部分成功',
        },
        {
          value: '发布失败',
          label: '发布失败',
        },
        {
          value: '已结束',
          label: '已结束',
        },
      ],
      textPositionOptions:[
        {
          label:'上',
          value:'上',
        },
        {
          label:'中',
          value:'中',
        },
        {
          label:'下',
          value:'下',
        }
      ],
      playbackSpeedOptions:[
        {
          label:'快',
          value:'快',
        },
        {
          label:'中',
          value:'中',
        },
        {
          label:'慢',
          value:'满',
        },
      ],
      noticeDetailVisible:false,
      activeName: 'first',
      openRowInfo: {},
      mtableData: [],
      //重命名
      renameFormVisible:false,
      addNoticeFormVisible:false,
      editNoticeFormVisible:false,
      submitVisible : false,
      currentPage: 1,
      pageSize: 10,
      totalCount: 100,
      emcurrentPage: 1,
      empageSize: 10,
      emtotalCount: 100,
      nemcurrentPage: 1,
      nempageSize: 10,
    })
    //清空搜索列表
    const cleanAll = () =>{
      state.noticeStateValue=''
    }
    //查询素材
    const unionSearchFunc = () => {
      state.currentPage = 1
      searchNotice(state.currentPage,state.noticeStateValue,state.pageSize).then( (res) => {
        console.log(res.data)
        state.totalCount = res.data.data.notice.total
        state.tableData = res.data.data.notice.records
        for (const line of state.tableData) {
          line.starttime = new Date(line.starttime).format("yyyy-MM-dd hh:mm:ss");
          line.playbacktime = new Date(line.playbacktime).format("yyyy-MM-dd hh:mm:ss");
          line.endtime = new Date(line.endtime).format("yyyy-MM-dd hh:mm:ss");
        }
      })

    }
    // 打开详情页内容
    const openEquipmentDetail = (index,row) =>{
      console.log(index,row)
      state.noticeDetailVisible=true
      state.openRowInfo = row
      state.nemcurrentPage = 1
      getem(row.noticeid).then( (res) => {
        console.log(res.data)
        // state.emtotalCount = res.data.data.equipment.total
        state.nemtableData = res.data.data.notice
      })
    }
    const noticeForm = reactive({
      emID:[],
      backgroundColor:'',
      backgroundHeight:'',
      noticeContent:'',
      playbackSpeed:'',
      textColor:'',
      textPosition:'',
      textSize:'',
      noticeID:'',
    })
    //上传
    const submit = (index,row) => {
      state.submitVisible = false
      let message = {from: "PC001", to: "Android001", text: "notice:"+noticeForm.noticeID}
      console.log(message)
      state.socket.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
    }
    // 打开编辑窗口
    const editGroupFormFunc = (index,row) =>{
      console.log(index,row)
      noticeForm.noticeID = row.noticeid
      noticeForm.textColor = row.textcolor
      noticeForm.textSize = row.textsize
      noticeForm.backgroundColor = row.backgroundcolor
      noticeForm.backgroundHeight = row.backgroundheight
      noticeForm.textPosition = row.textposition
      noticeForm.playbackSpeed = row.playbackspeed
      noticeForm.noticeContent = row.noticecontent
      state.editNoticeFormVisible=true
      state.emcurrentPage = 1
      getem(row.noticeid).then( (res) => {
        console.log(res.data)
        // state.emtotalCount = res.data.data.equipment.total
        state.emtableData = res.data.data.notice
      })
    }
    const submitFunc = (index,row) =>{
      console.log(index,row)
      noticeForm.noticeID = row.noticeid
      noticeForm.textColor = row.textcolor
      noticeForm.textSize = row.textsize
      noticeForm.backgroundColor = row.backgroundcolor
      noticeForm.backgroundHeight = row.backgroundheight
      noticeForm.textPosition = row.textposition
      noticeForm.playbackSpeed = row.playbackspeed
      noticeForm.noticeContent = row.noticecontent
      state.submitVisible=true
    }

    // 标签页点击
    const handleClick = (tab, event) => {
      console.log(tab, event)
    }
    //打开新建窗口
    const openadd = () =>{
      noticeForm.noticeID = ''
      noticeForm.textColor = ''
      noticeForm.textSize = ''
      noticeForm.backgroundColor = ''
      noticeForm.backgroundHeight = ''
      noticeForm.textPosition = ''
      noticeForm.playbackSpeed = ''
      noticeForm.noticeContent = ''
      state.addNoticeFormVisible=true
      geteList()
    }
    //添加页面选择
    const handleSelectionChange = (val) => {
      state.multipleSelection.value = val
      noticeForm.emID=[]
      for(const i in state.multipleSelection.value){
        const a = state.multipleSelection.value[i].equipmentid
        noticeForm.emID.push(a)
        console.log(noticeForm.emID)
      }
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
    // 改变设备列表分页单页大小
    const emhandleSizeChange = (val) =>{
      console.log(`${val} items per page`)
      state.nempageSize = val
    }
    // 修改设备列表当前分页
    const emhandleCurrentChange = (val) =>{
      console.log(`current page: ${val}`)
      state.nemcurrentPage = val
    }
    //获取信息
    const getList = () =>{
      getNoticeList(state.currentPage,state.pageSize).then( (res) => {
        console.log(res.data)
        state.totalCount = res.data.data.notice.total
        state.tableData = res.data.data.notice.records
        for (const line of state.tableData) {
          line.starttime = new Date(line.starttime).format("yyyy-MM-dd hh:mm:ss");
          line.playbacktime = new Date(line.playbacktime).format("yyyy-MM-dd hh:mm:ss");
          line.endtime = new Date(line.endtime).format("yyyy-MM-dd hh:mm:ss");
        }
      })
    }
    //获取设备列表
    const geteList = () =>{
      getEquipmentList(state.emcurrentPage,state.empageSize).then( (res) => {
        console.log(res.data)
        state.emtotalCount = res.data.data.equipment.total
        state.emtableData = res.data.data.equipment.records
      })
    }
    // 新建设备表内容
    const addNewNotice= async() =>{
      state.addNoticeFormVisible = false
      addNotice((noticeForm.emID).toString(),'2',noticeForm.backgroundColor,noticeForm.backgroundHeight,'-',noticeForm.noticeContent,
          '持续播放',noticeForm.playbackSpeed,'-',noticeForm.textColor,noticeForm.textPosition,noticeForm.textSize).then( (res) => {
        console.log(res.data)
        if(res.data.code === 200){
          ElMessage({
            message: '新增公告成功!',
            type: 'success',
          })
          state.currentPage = 1
          getList()
        }
      })
    }
    //编辑功能
    const editNotice = () =>{
      state.editNoticeFormVisible = false
      updateNotice((noticeForm.emID).toString(),'2',noticeForm.backgroundColor,noticeForm.backgroundHeight,'-',noticeForm.noticeContent,
          noticeForm.noticeID,'持续播放',noticeForm.playbackSpeed,'-',noticeForm.textColor,noticeForm.textPosition,noticeForm.textSize).then( (res) => {
        console.log(res.data)
        if(res.data.code === 200){
          ElMessage({
            message: '编辑公告成功!',
            type: 'success',
          })
          state.currentPage = 1
          getList()
        }
      })
    }
    //复制
    const copyNotice = (index,row) =>{
      console.log(index,row)
      const equipmentListString = (row.equipmentIDList).toString()
      const equipmentListString2 = equipmentListString.substr(1,equipmentListString.length-2)
      console.log('equipmentListString:'+equipmentListString2)
      addNotice(equipmentListString2,'2',row.backgroundcolor,row.backgroundheight,'-',row.noticecontent,
          '持续播放',row.playbackspeed,'-',row.textcolor,row.textposition,row.textsize).then( (res) => {
        console.log(res.data)
        if(res.data.code === 200){
          ElMessage({
            message: '复制公告成功!',
            type: 'success',
          })
          state.currentPage = 1
          getList()
        }
      })

    }
    // 节目文件添加处理函数
    const handlePreview = (uploadFile, uploadFiles) => {
      console.log(uploadFile, uploadFiles)
      console.log(state.fileList)
    }

    // 节目文件删除处理函数
    const handleRemove = (file) => {
      console.log(file)
    }

    // 上传成功时的钩子
    const uploadSuccess = (res,file,fileList) => {
      if(res.code !== 200){
        // return this.$message.error(res.msg)
        return ElMessage.error(res.msg)
      }
      file.url = res.data.url
      state.fileList =fileList
      console.log(state.fileList)
    }

    // 删除当前行数据
    const deleteThisRow = (index,row) =>{
      console.log(index,row)
      deleteNotice(row.noticeid).then( (res) => {
        console.log(res.data)
        if(res.data.code === 200){
          ElMessage({
            message: '公告删除成功!',
            type: 'success',
          })
          state.currentPage = 1
          getList()
        }
      })
    }
    // socket关闭
    const socketClose = () =>{
      console.log("websocket已关闭");
    }

    // socket错误
    const socketError = () =>{
      console.log("websocket发生了错误");
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
    //搜索计划初始化
    onMounted(()=>{
      getList()
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
          // console.log(data)
        };
        //关闭事件
        state.socket.onclose = socketClose
        //发生了错误事件
        state.socket.onerror = socketError
      }
    })
    return {
      ...toRefs(state),
      Search,
      cleanAll,
      openadd,
      ElTable,
      ElMessage,
      handleCurrentChange,
      handleSizeChange,
      handleSelectionChange,
      emhandleSizeChange,
      emhandleCurrentChange,
      getList,
      handlePreview,
      handleRemove,
      uploadSuccess,
      noticeForm,
      addNewNotice,
      unionSearchFunc,
      openEquipmentDetail,
      handleClick,
      getem,
      geteList,
      editGroupFormFunc,
      editNotice,
      deleteThisRow,
      copyNotice,
      submit,
      submitFunc,
    }

  },
  created(){

  }
}
</script>

<style lang="less" scoped>
.top{
  width: 100%;
  margin: auto;
  background-color: #545c64;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  flex-wrap: wrap;
  .condition{
    margin-left: 2.5rem;
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

  }
  .buttonTop{
    width: 100%;
    background-color: #545c64;
    padding-bottom: 20px;
    .buttonBox{
      margin-left: 85%;
    }
  }

}
img{
  width: 63.5rem;
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
.eltable{
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
.el-color-picker{
  margin-left: 3rem;
}
.el-slider {
  margin-left: 3rem;
  width: 30%;
}
</style>
