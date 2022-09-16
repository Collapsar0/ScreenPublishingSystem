<template>
  <!--  搜索行-->
  <div class="top">
    <!--    素材名称-->
    <div class="condition">
      <div class="condition-text">素材名称:</div>
      <div class="inputBox">
        <el-input v-model="inputName" placeholder="请输入素材名称" class="input-with-select">
          <template #append>
            <el-button :icon="Search" />
          </template>
        </el-input>
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
      <el-button type="primary" @click="openadd">上传素材</el-button>
    </div>
  </div>
<!--  表格行-->
  <div style="width: 100%;">
    <el-table
        ref="multipleTableRef" class="eltable"
        :data="tableData"
        style="width: 100%;"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="40"/>
      <el-table-column label="缩略图" width="120">
        <template #default="scope">
          <el-image style="width: 50px; height: 50px" :src="scope.row.url" fit="fill"/>
        </template>
      </el-table-column>
      <el-table-column property="materialname" label="素材名称" width="150"/>
      <el-table-column property="resolvingpower" label="分辨率" width="150"/>
      <el-table-column property="materialsize" label="大小" width="150"/>
      <el-table-column property="programcount" label="引用节目" width="150"/>
      <el-table-column property="author" label="作者" width="150"/>
      <el-table-column property="updatetime" label="更新时间" width="170"/>
      <el-table-column label="操作" width="216.5">
        <template #default="scope">
          <!--  -->
          <!-- @click.prevent="openEquipmentDetail(scope.$index, scope.row)" -->
          <el-button link type="primary" size="small" @click.prevent="preview(scope.$index, scope.row)">
            预览
          </el-button>
          <el-button link type="primary" size="small" @click.prevent="rename(scope.$index, scope.row)">
            重命名
          </el-button>
          <el-button link type="primary" size="small">
            下载
          </el-button>
          <el-button link type="primary" size="small" @click.prevent="deleteThisRow(scope.$index, scope.row)">
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
  <!--  预览弹出框-->
  <el-dialog v-model="previewVisible" title="预览图" width="70%">
    <img :src="previewurl"  >
  </el-dialog>
<!--  重命名弹出框-->
  <el-dialog v-model="renameFormVisible" title="重命名" width="35%">
    <el-form :model="renameForm">
      <el-form-item label="素材名称" :label-width="formLabelWidth">
        <el-input v-model="renameForm.name" style="width: 200px;" autocomplete="off" placeholder=""/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="renameFormVisible = false">返回</el-button>
        <el-button type="primary" @click="renametrue"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
  <!--  添加节目弹出框-->
  <el-dialog v-model="addmaterialFormVisible" title="新建节目" width="35%">
    <el-form :model="equipmentForm">
<!--      图片处理-->
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
            提示：支持格式为jpg，jpeg，png，gif， bmp，mkv，mp4，mov，mp3
          </div>
        </template>
      </el-upload>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="addmaterialFormVisible = false">返回</el-button>
        <el-button type="primary" @click="addNewMaterial"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>

</template>

<script>

import { Search } from '@element-plus/icons-vue';
import { onMounted, reactive, toRefs,getCurrentInstance} from "vue";
import { ElTable,ElMessage  } from 'element-plus';
import {getMaterialList,renameMaterial,addOneMaterial,unionSearch,deletematerial} from "@/api/materiaList";
export default {
  setup() {
    const state = reactive({
      baseUrl: getCurrentInstance()?.appContext.config.globalProperties.$baseUrl,
      formLabelWidth: '140px',
      inputName: '',
      multipleSelection:'',
      tableData:[
      ],
      fileList:[],
      actionUrl: getCurrentInstance()?.appContext.config.globalProperties.$baseUrl + '/file/upload',
      materialIdList:[],
      timeList:[],
      previewurl:'',
      //重命名
      renameFormVisible:false,
      addmaterialFormVisible:false,
      previewVisible:false,
      currentPage: 1,
      pageSize: 10,
      totalCount: 100,
    })
    //清空搜索列表
    const cleanAll = () =>{
      state.inputName=''
    }
    //查询素材
    const unionSearchFunc = () => {
      state.currentPage = 1
      unionSearch(state.currentPage,"",state.inputName,state.pageSize).then( (res) => {
        console.log(res.data)
        state.totalCount = res.data.data.material.total
        state.tableData = res.data.data.material.records
        for (const line of state.tableData) {
          line.updatetime = new Date(line.updatetime).format("yyyy-MM-dd hh:mm:ss");
          line.materialsize = dataSizeChangeFunc(line.materialsize)
        }
      })

    }
    const openadd = () =>{
      state.addmaterialFormVisible=true
    }
    const handleSelectionChange = (val) => {
      state.multipleSelection.value = val
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
    //获取信息
    const getList = () =>{
      getMaterialList(state.currentPage,state.pageSize).then( (res) => {
        console.log(res.data)
        state.totalCount = res.data.data.material.total
        state.tableData = res.data.data.material.records
        for (const line of state.tableData) {
          line.updatetime = new Date(line.updatetime).format("yyyy-MM-dd hh:mm:ss");
          line.materialsize = dataSizeChangeFunc(line.materialsize)
        }
      })
    }
    // 新建设备表内容
    const addNewMaterial= async() =>{
      state.addmaterialFormVisible = false

      console.log(state.fileList)
      const programContent = []
      let totalData = 0
      let index = 0

      for (const file of state.fileList) {
        await addOneMaterial(8,1,file.name,file.size,"pic","1920×1080",file.url).then( (res) => {
            // console.log(res.data)
            if(res.data.code === 200){
              ElMessage({
                message: '素材添加成功!',
                type: 'success',
              })
              state.materialIdList.push(res.data.data.material.materialid)

              const content = {}
              content["materialID"] = res.data.data.material.materialid
              content["playbackTime"] = state.timeList[index]

              programContent.push(content)
              totalData += file.size
            }
          })
        index++
      }
      state.fileList = []
          equipmentForm.name = ""
          state.timeList = []
          getList()
    }
    const equipmentForm = reactive({
      name: '',
      MACAddress: '',
      organizationID: '',
      SNCode: '',
    })
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
    //预览素材
    const preview = (index,row) =>{
      state.previewVisible = true
      console.log(index,row)
      state.previewurl = row.url
    }
    const renameForm = reactive({
      id:'',
      name: '',
    })
    // 重命名
    const rename= (index,row) =>{
      state.renameFormVisible=true;
      console.log(index,row)
      renameForm.id=row.materialid
      renameForm.name = row.materialname
    }
    //确认重命名
    const renametrue = () =>{
      state.renameFormVisible = false
      renameMaterial(renameForm.id,renameForm.name).then( (res) => {
        console.log(res.data)
        if(res.data.code === 200){
          ElMessage({
            message: '重命名成功!',
            type: 'success',
          })
          state.currentPage = 1
          getList()
        }
      })
    }
    // 删除当前行数据
    const deleteThisRow = (index,row) =>{
      console.log(index,row)
      deletematerial(row.materialid).then( (res) => {
        console.log(res.data)
        if(res.data.code === 200){
          ElMessage({
            message: '素材删除成功!',
            type: 'success',
          })
          state.currentPage = 1
          getList()
        }
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
    })
    return {
      ...toRefs(state),
      Search,
      cleanAll,
      openadd,
      ElTable,
      ElMessage,
      handleSelectionChange,
      handleSizeChange,
      handleCurrentChange,
      getList,
      preview,
      rename,
      renameForm,
      renametrue,
      handlePreview,
      handleRemove,
      uploadSuccess,
      equipmentForm,
      addNewMaterial,
      unionSearchFunc,
      deleteThisRow,
      dataSizeChangeFunc,
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
</style>
