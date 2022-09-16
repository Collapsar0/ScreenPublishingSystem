<template>
  <!--  搜索行-->
  <div class="box">
    <!--    节目名称-->
    <div class="condition">
      <div class="condition-text">
        节目名称:
      </div>
      <div class="inputBox">
        <el-input
            v-model="inputName"
            placeholder="请输入节目名称"
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
    <!--    节目状态-->
    <div class="condition">
      <div class="condition-text">
        节目状态:
      </div>
      <el-select v-model="programStateValue" clearable class="m-2" placeholder="">
        <el-option
            v-for="item in programStateOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
        />
      </el-select>

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
      <el-button type="primary" @click="addEquipmentFormVisible=true">创建节目</el-button>
      <el-button @click="openDeleteMoreProgramDialog">批量删除</el-button>
    </div>
  </div>
  <!--  表格行-->
  <div class="tableLine">
    <el-table
        ref="multipleTableRef"
        :data="tableData"
        style="width: 100%;"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />
      <el-table-column label="缩略图" width="100">
        <template #default="scope">
          <el-image style="width: 50px; height: 50px" :src="scope.row.programpic" fit="fill" />
        </template>
      </el-table-column>
      <el-table-column label="节目名称" width="120">
        <template #default="scope">{{ scope.row.programname }}</template>
      </el-table-column>
      <el-table-column property="resolvingpower" label="分辨率" width="150" />
      <el-table-column property="programduration" label="节目时长" width="120" />
      <el-table-column property="programdata" label="节目大小" width="150" />
      <el-table-column property="programstate" label="节目状态" width="150" />
      <el-table-column property="author" label="作者" width="130" />
      <el-table-column property="updatedate" label="更新时间" width="110" />
      <el-table-column  label="操作" width="231" >
        <template #default="scope">
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="openEditorDialog(scope.$index, scope.row)"
          >
            编辑
          </el-button>
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="publicProgramDialog(scope.$index, scope.row)"
          >
            发布
          </el-button>
          <el-button
              link
              type="primary"
              size="small"
              @click.prevent="openDeleteProgramDialog(scope.$index, scope.row)"
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

  <!--  添加节目弹出框-->
  <el-dialog v-model="addEquipmentFormVisible" title="新建节目" width="35%">


    <el-form :model="equipmentForm">
      <el-form-item label="名称" :label-width="formLabelWidth">
        <el-input v-model="equipmentForm.name" style="width: 300px;" autocomplete="off" placeholder="例如：节目一"/>
      </el-form-item>
      <el-form-item label="分辨率" :label-width="formLabelWidth">
        <el-select v-model="newResolvingPowerValue" clearable class="m-2" placeholder="请选择分辨率">
          <el-option
              v-for="item in newResolvingPowerOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
<!--      图片处理-->
      <el-button type="primary" style="margin-bottom: 5px" @click="materialVisible=true">选择素材库素材</el-button>

      <el-upload
          v-model:file-list="fileList"
          class="upload-demo"
          :action=actionUrl
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :on-success="uploadSuccess"
          list-type="picture"
      >
        <el-button type="primary">点击上传图片</el-button>
        <template #tip>
          <div class="el-upload__tip">
            jpg/png 文件并且小于 500kb
          </div>
        </template>
      </el-upload>


      <div class="timeBox" v-for="(item,index) in fileList" :key="index">
        <div class="timeText">第{{index+1}}张图:</div>
        <el-input v-model="timeList[index]" style="width: 300px;" placeholder="请输入播放时间（秒）" />
      </div>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addEquipmentFormVisible = false">返回</el-button>
        <el-button type="primary" @click="addNewProgram"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
  <!--  编辑节目弹出框-->
  <el-dialog v-model="editorProgramFormVisible" title="编辑节目" width="35%">
    <el-form :model="editorForm">
      <el-form-item label="名称" :label-width="formLabelWidth">
        <el-input v-model="editorForm.name" style="width: 300px;" autocomplete="off" placeholder="例如：节目一"/>
      </el-form-item>
      <el-form-item label="分辨率" :label-width="formLabelWidth">
        <el-select v-model="editorNewResolvingPowerValue" clearable class="m-2" placeholder="请选择分辨率">
          <el-option
              v-for="item in newResolvingPowerOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <!--      图片处理-->
      <el-button type="primary" style="margin-bottom: 5px" @click="editorMaterialVisible=true">选择素材库素材</el-button>
      <el-upload
          v-model:file-list="editorFileList"
          class="upload-demo"
          :action=actionUrl
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :on-success="editorUploadSuccess"
          list-type="picture"
      >
        <el-button type="primary">点击上传图片</el-button>
        <template #tip>
          <div class="el-upload__tip">
            jpg/png 文件并且小于 500kb
          </div>
        </template>
      </el-upload>


      <div class="timeBox" v-for="(item,index) in editorFileList" :key="index">
        <div class="timeText">第{{index+1}}张图:</div>
        <el-input v-model="editorTimeList[index]" style="width: 300px;" placeholder="请输入播放时间（秒）" />
      </div>

    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="editorProgramFormVisible = false">返回</el-button>
        <el-button type="primary" @click="editorNewProgram"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>

  <!--选择素材弹窗-->
  <el-dialog v-model="materialVisible" title="选择素材" width="87%" >
    <open-material-list :selectIdList="materialIdList" @event="handleChange"></open-material-list>


      <span class="dialog-footer" style="display: flex;justify-content: flex-end">
        <el-button @click="materialVisible = false">返回</el-button>
        <el-button type="primary" @click="selectMaterials"
        >确认</el-button
        >
      </span>

  </el-dialog>

  <!--编辑节目选择素材弹窗-->
  <el-dialog v-model="editorMaterialVisible" title="选择素材" width="87%" >
    <open-material-list :selectIdList="editorMaterialIdList" @event="editorHandleChange"></open-material-list>


    <span class="dialog-footer" style="display: flex;justify-content: flex-end">
        <el-button @click="editorMaterialVisible = false">返回</el-button>
        <el-button type="primary" @click="editorMaterialVisible=false"
        >确认</el-button
        >
      </span>

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
            设备名称：{{openRowInfo.equipmentname}}
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
      <el-tab-pane label="安装信息" name="second">安装信息</el-tab-pane>
      <el-tab-pane label="状态信息" name="third">状态信息</el-tab-pane>
    </el-tabs>
    <!--    底部内容-->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="equipmentDetailVisible = false">返回</el-button>
      </span>
    </template>
  </el-dialog>
  <!--  发布控制弹窗-->
  <el-dialog v-model="remoteControlVisible" title="发布控制" width="35%">
    <div>是否发布</div>
    <!--    底部内容-->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="remoteControlVisible = false">返回</el-button>
        <el-button type="primary" @click="publicProgram"
        >确认</el-button
        >
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

  <!--  删除确认弹窗-->
  <el-dialog v-model="deleteConfirmVisible" title="删除确认" width="35%">
    <div>是否删除</div>
    <!--    底部内容-->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="deleteConfirmVisible = false">返回</el-button>
        <el-button type="primary" @click="deleteThisRow()"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
  <!--  批量删除确认弹窗-->
  <el-dialog v-model="deleteMoreConfirmVisible" title="批量删除确认" width="35%">
    <div>是否删除所选的所有节目</div>
    <!--    底部内容-->
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="deleteMoreConfirmVisible = false">返回</el-button>
        <el-button type="primary" @click="deleteMoreRow()"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>

</template>

<script>
import {onMounted, reactive, toRefs,getCurrentInstance } from "vue";
import { ElTable,ElMessage,UploadProps,UploadUserFile  } from 'element-plus';
import { Search } from '@element-plus/icons-vue';
import { deleteEquipment, getEquipmentList, modifyInfo, unionSearch, getdList} from "@/api/equipmentList";
import {
  addOneMaterial,
  addOneProgram,
  deleteProgramByID, editorProgramContent, getMaterialById,
  getProgramBySearchUnion,
  getProgramList, getUrlById
} from "@/api/programMakeRelease";
import OpenMaterialList from "@/views/releaseProgram/openMaterialList";


export default {
  components: {OpenMaterialList},
  setup(){
    let socket;
    const state = reactive({
      socket,
      baseUrl: getCurrentInstance()?.appContext.config.globalProperties.$baseUrl,
      // 搜索框相关

      // 输入框绑定变量
      inputName: '',
      // 选择器绑定变量
      resolvingPowerValue: '',
      programStateValue: '',
      // 选择器选项绑定数组
      resolvingPowerOptions:[
        {
          value: '',
          label: '全部分辨率',
        },
        {
          value: '1920x1080',
          label: '1920x1080(横)',
        },
        {
          value: '1080x1920',
          label: '1080x1920(竖)',
        },
        {
          value: '3840x2160',
          label: '3840x2160(横)',
        },
        {
          value: '2160x3840',
          label: '2160x3840(竖)',
        },
      ],
      programStateOptions:[
        {
          value: '',
          label: '全部',
        },
        {
          value: '使用中',
          label: '使用中',
        },
        {
          value: '未使用',
          label: '未使用',
        },
        {
          value: '已失效',
          label: '已失效',
        },
      ],


      // 表格相关数据
      multipleSelection:[],
      tableData: [],
      // 添加节目弹出框
      addEquipmentFormVisible: false,
      formLabelWidth: '140px',
      newResolvingPowerValue: '',
      newResolvingPowerOptions:[
        {
          value: '1920x1080',
          label: '1920x1080(横)',
        },
        {
          value: '1080x1920',
          label: '1080x1920(竖)',
        },
        {
          value: '3840x2160',
          label: '3840x2160(横)',
        },
        {
          value: '2160x3840',
          label: '2160x3840(竖)',
        },
      ],
      fileList:[],
      actionUrl: getCurrentInstance()?.appContext.config.globalProperties.$baseUrl + '/file/upload',
      materialIdList:[],
      timeList:[],
      chooseIndex: 0,
      chooseProgramId: 0,

      // 编辑节目弹出框
      editorProgramFormVisible: false,
      editorFileList:[],
      editorNewResolvingPowerValue: '',
      editorMaterialIdList:[],
      editorTimeList:[],
      editRow: {},
      editorMaterialVisible:false,

      // 素材选择弹出框
      materialVisible: false,

      // 删除节目相关
      deleteConfirmVisible: false,
      deleteIndex: 0,
      deleteRow: {},

      deleteMoreConfirmVisible:false,


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

    // 发布节目函数
    const publicProgramDialog = (index,row) =>{
      // console.log(index,row)
      state.chooseIndex = index
      state.chooseProgramId = row.programid
      state.remoteControlVisible=true
    }

    // 节目文件添加处理函数
    const handlePreview = (uploadFile, uploadFiles) => {
      console.log(uploadFile, uploadFiles)
      console.log(state.fileList)
      console.log("add")
    }

    // 节目文件删除处理函数
    const handleRemove = (file) => {
      console.log("删除")
      console.log(file)
    }

    // 上传成功时的钩子
    const uploadSuccess = (res,file,fileList) => {
      if(res.code !== 200){
        // return this.$message.error(res.msg)
        return ElMessage.error(res.msg)
      }
      else if(res.code === 200){
        file.url = res.data.url
        state.fileList =fileList
        console.log(state.fileList)

        addOneMaterial(8,1,file.name,file.size,"pic",state.newResolvingPowerValue,file.url).then( (res) => {
          // console.log(res.data)
          if(res.data.code === 200){
            state.materialIdList.push(res.data.data.material.materialid)
            file["materialID"] = res.data.data.material.materialid
          }
        })
      }
    }

    // 编辑节目上传成功时的钩子
    const editorUploadSuccess = (res,file,fileList) => {
      if(res.code !== 200){
        // return this.$message.error(res.msg)
        return ElMessage.error(res.msg)
      }
      file.url = res.data.url
      state.editorFileList =fileList
      console.log(state.editorFileList)
    }

    // 新建设备表内容
    const equipmentForm = reactive({
      name: '',
      MACAddress: '',
      organizationID: '',
      SNCode: '',
    })

    // 编辑节目表内容
    const editorForm = reactive({
      name: '',
      MACAddress: '',
      organizationID: '',
      SNCode: '',
    })

    // 新建节目
    const addNewProgram = async () =>{
      state.addEquipmentFormVisible = false

      console.log(state.fileList)
      const programContent = []
      let totalData = 0
      let index = 0

      for (const file of state.fileList) {
        // await addOneMaterial(8,1,file.name,file.size,"pic",state.newResolvingPowerValue,file.url).then( (res) => {
        //     // console.log(res.data)
        //     if(res.data.code === 200){
        //       state.materialIdList.push(res.data.data.material.materialid)
        //
        //       const content = {}
        //       content["materialID"] = res.data.data.material.materialid
        //       content["playbackTime"] = state.timeList[index]
        //
        //       programContent.push(content)
        //       totalData += file.size
        //     }
        //   })

        const content = {}
        content["materialID"] = file.materialID
        content["playbackTime"] = state.timeList[index]

        programContent.push(content)
        totalData += file.size
        index++
      }

      let content = ""
      // console.log(qs.stringify(programContent))


      content = encodeURIComponent(JSON.stringify(programContent))
      console.log(content,typeof (content))


      addOneProgram(content,totalData,equipmentForm.name,state.newResolvingPowerValue,8,).then( (res) => {
        if(res.data.code === 200){
          ElMessage({
            message: '节目添加成功!',
            type: 'success',
          })
          state.newResolvingPowerValue = ""
          state.fileList = []
          equipmentForm.name = ""
          state.timeList = []
          getList()

        }
      })

    }

    // todo 打开编辑窗口
    const openEditorDialog = async (index,row) =>{
      state.editorProgramFormVisible = true
      state.editRow = row
      // console.log(index,row)
      state.editorMaterialIdList = []
      state.editorTimeList = []
      state.editorFileList = []

      editorForm.name = row.programname
      state.editorNewResolvingPowerValue = row.resolvingpower
      const content = JSON.parse(decodeURIComponent(row.programcontent))
      for (const item of content) {
        state.editorMaterialIdList.push(item.materialID)
        state.editorTimeList.push(item.playbackTime)

        await getMaterialById(item.materialID).then( (res)=> {
          if(res.data.code === 200) {
            state.editorFileList.push({
              url: res.data.data.equipmentGroup.url,
              name: res.data.data.equipmentGroup.materialname,
              size: res.data.data.equipmentGroup.materialsize
            })
          }
        })
      }
    }

    // todo 编辑节目
    const editorNewProgram = async () =>{
      state.editorProgramFormVisible = false

      // console.log(state.editorFileList)
      const programContent = []
      let totalData = 0
      let index = 0

      for (const file of state.editorFileList) {
        await addOneMaterial(8,1,file.name,file.size,"pic",state.editorNewResolvingPowerValue,file.url).then( (res) => {
          // console.log(res.data)
          if(res.data.code === 200){
            state.editorMaterialIdList.push(res.data.data.material.materialid)

            const content = {}
            content["materialID"] = res.data.data.material.materialid
            content["playbackTime"] = state.editorTimeList[index]

            programContent.push(content)
            totalData += file.size
          }
        })
        index++
      }

      let content = ""
      // console.log(qs.stringify(programContent))


      content = encodeURIComponent(JSON.stringify(programContent))
      // console.log(content,typeof (content))


      editorProgramContent(content,totalData,state.editRow.programid,editorForm.name,state.editorNewResolvingPowerValue).then( (res) => {
        if(res.data.code === 200){
          ElMessage({
            message: '节目编辑成功!',
            type: 'success',
          })
          state.newResolvingPowerValue = ""
          state.fileList = []
          equipmentForm.name = ""
          state.timeList = []
          getList()

        }
      })

    }

    // 发布节目
    const publicProgram = () =>{
      state.remoteControlVisible = false
      let message = {from: "PC001", to: "Android001", text: state.chooseProgramId}
      state.socket.send(JSON.stringify(message));  // 将组装好的json发送给服务端，由服务端进行转发
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
      state.multipleSelection.values = val
      // console.log(state.multipleSelection)
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

    // 打开删除节目弹窗
    const openDeleteProgramDialog = (index,row) => {
      state.deleteConfirmVisible = true
      state.deleteIndex = index
      state.deleteRow = row
    }

    // 删除当前行数据
    const deleteThisRow = () =>{

      deleteProgramByID(state.deleteRow.programid).then( (res) => {
        console.log(res.data)
        if(res.data.code === 200){
          ElMessage({
            message: '节目删除成功!',
            type: 'success',
          })
          state.currentPage = 1
          state.deleteConfirmVisible = false
          getList()
        }
      })
    }

    // 打开批量删除节目弹窗
    const openDeleteMoreProgramDialog = () => {
      state.deleteMoreConfirmVisible = true
    }

    // 删除多行数据
    const  deleteMoreRow = async () =>{

       for (const item of state.multipleSelection.values) {
         // console.log(item.programid)
        await deleteProgramByID(item.programid).then( (res) => {
          // console.log(res.data)
          if(res.data.code !== 200){
          }
        })
      }
      ElMessage({
        message: '节目批量删除成功!',
        type: 'success',
      })
      state.currentPage = 1
      state.deleteMoreConfirmVisible = false
      getList()
    }

    // 改变分页单页大小
    const handleSizeChange = (val) =>{
      console.log(`${val} items per page`)
      state.pageSize = val
      getList()
    }

    // 修改当前分页
    const handleCurrentChange = (val) =>{
      // console.log(`current page: ${val}`)
      state.currentPage = val
      getList()
    }

    // 获取节目列表
    const getList = () =>{
      getProgramList(state.currentPage,state.pageSize).then( (res) => {
        // console.log(res.data)
        state.totalCount = res.data.data.program.total
        state.tableData = res.data.data.program.records
        for (const line of state.tableData) {
          line.updatedate = new Date(line.updatedate).format("yyyy-MM-dd hh:mm:ss");
          line.programdata = dataSizeChangeFunc(line.programdata)
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

    // 联合查询
    const unionSearchFunc = () => {
      state.currentPage = 1
      getProgramBySearchUnion(state.currentPage,state.inputName,state.programStateValue,state.resolvingPowerValue,state.pageSize).then( (res) => {
        console.log(res.data)
        state.totalCount = res.data.data.program.total
        state.tableData = res.data.data.program.records
        for (const line of state.tableData) {
          line.updatedate = new Date(line.updatedate).format("yyyy-MM-dd hh:mm:ss")
          line.programdata = dataSizeChangeFunc(line.programdata)
        }
      })
    }

    // 清除全部
    const cleanAll = () =>{
      state.inputName=''
      state.resolvingPowerValue=''
      state.programStateValue=''
    }

    // socket关闭
    const socketClose = () =>{
      console.log("websocket已关闭");
    }

    // socket错误
    const socketError = () =>{
      console.log("websocket发生了错误");
    }

    // 时间转化工具函数
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

    // 搜索计划初始化
    onMounted(() => {
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

    // 选择素材库素材
    const selectMaterials = () =>{
      state.materialVisible = false
    }

    // 选择素材改变触发函数
    const handleChange = (sonData) =>{
     console.log(sonData)
      state.fileList = []
      for (const data of sonData) {
        const file = {
          'url':  data.url,
          'materialID': data.materialid,
          'size': data.sizeNumber
        }
        state.fileList.push(file)
      }

    }

    // 选择素材改变触发函数
    const editorHandleChange = (sonData) =>{
      console.log(sonData)
      state.editorFileList = []
      for (const data of sonData) {
        const file = {
          'url':  data.url,
          'materialID': data.materialid,
          'size': data.sizeNumber
        }
        state.editorFileList.push(file)
      }

    }

    return {
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
      addNewProgram,
      getList,
      openEquipmentDetail,
      modifyEquipmentInfo,
      Search,
      unionSearchFunc,
      cleanAll,
      UploadProps,
      UploadUserFile,
      handlePreview,
      handleRemove,
      uploadSuccess,
      publicProgram,
      socketClose,
      socketError,
      publicProgramDialog,
      dataSizeChangeFunc,
      editorForm,
      editorUploadSuccess,
      editorNewProgram,
      openEditorDialog,
      openDeleteProgramDialog,
      openDeleteMoreProgramDialog,
      deleteMoreRow,
      selectMaterials,
      handleChange,
      editorHandleChange,
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
.tableLine{
  width: 100%;
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

// 新建节目部分
.timeBox{
  display: flex;
  flex-direction: row;
  align-items: center;
  .timeText{
    font-size: 18px;
  }
}
</style>
