<template>
<!--  搜索行-->
  <div class="box">
    <!--    分组名称-->
    <div class="condition">
      <div class="condition-text">
        分组名称:
      </div>
      <div class="inputBox">
        <el-input
            v-model="inputName"
            placeholder="请输入分组名称"
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
      <el-button type="primary" @click="openadd">新增分组</el-button>
    </div>
  </div>
<!--  表格行-->
  <div class="width=100%">
    <el-table
        ref="multipleTableRef" class="eltable1"
        :data="tableData"
        style="width: 100%;"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"/>
      <el-table-column property="equipmentgroupid" label="分组ID" width="220"/>
      <el-table-column property="groupname" label="分组名称" width="220"/>
      <el-table-column property="organization" label="所属机构" width="220"/>
      <el-table-column property="equipmentcount" label="设备数量" width="150"/>
      <el-table-column property="description" label="描述" width="200"/>
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
              @click.prevent="editGroupFormFunc(scope.$index, scope.row)"
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

  <!--  添加设备分组弹出框-->
  <el-dialog v-model="addEquipmentFormVisible" title="新建分组" width="70%">
    <el-form :model="equipmentForm">
      <el-form-item label="分组名称" :label-width="formLabelWidth">
        <el-input v-model="equipmentForm.gName" style="width: 300px;" autocomplete="off" placeholder="请输入分组名称"/>
      </el-form-item>
      <el-form-item label="所属机构" :label-width="formLabelWidth">
        <el-select v-model="equipmentForm.organization" clearable class="m-2" style="width: 300px;" autocomplete="off" placeholder=" ">
          <el-option
              v-for="item in organizationOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="描述" :label-width="formLabelWidth">
        <el-input v-model="equipmentForm.description" style="width: 300px;" autocomplete="off" placeholder="描述最多200字"/>
      </el-form-item>
      <el-form-item label="设备选择" :label-width="formLabelWidth">
      </el-form-item>
      <div class="searchbox">
        <div class="inputb">
          <!--    设备名称-->
          <div class="condition">
            <div class="condition-text">
              设备名称:
            </div>
            <div class="inputBox">
              <el-input
                  v-model="inputemName"
                  placeholder="请输入设备名"
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
        </div>

        <div class="buttonBox">
          <el-button plain @click="cleanAll2">重置</el-button>
          <el-button type="primary" @click="emunionSearchFunc">查询</el-button>
        </div>
      </div>
      <!--  表格行-->
      <el-table
          ref="multipleTableRef"
          :data="emtableData"
          style="width: 100%;"
          @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column label="设备名称" width="200">
          <template #default="scope">{{ scope.row.equipmentname }}</template>
        </el-table-column>
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
        <el-button @click="addEquipmentFormVisible = false">返回</el-button>
        <el-button type="primary" @click="addNewEquipment"
        >确认</el-button
        >
      </span>
    </template>
  </el-dialog>
  <!--  设备详情弹窗-->
  <el-dialog v-model="equipmentDetailVisible" title="分组详情" width="70%">
    <el-form :model="equipmentForm">
      <el-form-item label="分组名称:" :label-width="formLabelWidth">
        {{editGroupForm.groupname}}
      </el-form-item>
      <el-form-item label="所属机构:" :label-width="formLabelWidth">
        {{editGroupForm.organization}}
      </el-form-item>
      <el-form-item label="描述:" :label-width="formLabelWidth">
        {{editGroupForm.description}}
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
        <el-table-column label="设备名称" width="200">
          <template #default="scope">{{ scope.row.equipmentname }}</template>
        </el-table-column>
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
            @size-change="emhandleSizeChange2"
            @current-change="emhandleCurrentChange2"
        />
      </div>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="equipmentDetailVisible = false">返回</el-button>
      </span>
    </template>
  </el-dialog>
  <!--  编辑设备弹出框-->
  <el-dialog v-model="editEquipmentFormVisible" title="新增设备" width="70%">
    <el-form :model="equipmentForm">
      <el-form-item label="分组名称" :label-width="formLabelWidth">
        <el-input v-model="editGroupForm.groupname" style="width: 300px;" autocomplete="off" placeholder="请输入分组名称"/>
      </el-form-item>
      <el-form-item label="所属机构" :label-width="formLabelWidth">
        <el-select v-model="editGroupForm.organization" clearable class="m-2" style="width: 300px;" autocomplete="off" placeholder=" ">
          <el-option
              v-for="item in organizationOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="描述" :label-width="formLabelWidth">
        <el-input v-model="editGroupForm.description" style="width: 300px;" autocomplete="off" placeholder="描述最多200字"/>
      </el-form-item>
      <el-form-item label="设备选择" :label-width="formLabelWidth">
      </el-form-item>
      <div class="searchbox">
        <div class="inputb">
          <!--    设备名称-->
          <div class="condition">
            <div class="condition-text">
              设备名称:
            </div>
            <div class="inputBox">
              <el-input
                  v-model="inputemName2"
                  placeholder="请输入设备名"
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
            <el-select v-model="resolvingPowerValue2" clearable class="m-2" placeholder="请选择分辨率">
              <el-option
                  v-for="item in resolvingPowerOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </div>
        </div>

        <div class="buttonBox">
          <el-button plain @click="cleanAll2">重置</el-button>
          <el-button type="primary" @click="emunionSearchFunc2">查询</el-button>
        </div>
      </div>
      <!--  表格行-->
      <el-table
          ref="multipleTableRef"
          :data="emtableData"
          style="width: 100%;"
          @selection-change="handleSelectionChange2"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column label="设备名称" width="200">
          <template #default="scope">{{ scope.row.equipmentname }}</template>
        </el-table-column>
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
            @size-change="emhandleSizeChange2"
            @current-change="emhandleCurrentChange2"
        />
      </div>
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

</template>

<script>
import {onMounted, reactive, toRefs} from "vue";
import { ElTable,ElMessage  } from 'element-plus';
import { Search } from '@element-plus/icons-vue';
import {getEquipmentgroupList,getEquipmentList,deleteEquipmentgroup,getdList,addOneEquipmentgroup,unionSearch,emunionSearch,
modifyInfo} from "@/api/equipmentgroupList";
// import { tr } from 'element-plus/es/locale';

export default {
  setup(){
    const state = reactive({
      // 搜索框相关

      // 输入框绑定变量
      inputName: '',
      inputemName: '',
      inputemName2: '',
      // 选择器绑定变量
      organizationValue: '',
      resolvingPowerValue: '',
      resolvingPowerValue2: '',
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
      // 表格相关数据
      multipleSelection:[],
      multipleSelection2:[],
      tableData: [],
      emtableData: [],
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
      emcurrentPage: 1,
      empageSize: 10,
      emtotalCount: 100,
    })

    // 新建设备表内容
    const equipmentForm = reactive({
      emID:[],
      gName:'',
      organization:'',
      description:''
    })
    //打开新增分组
    const openadd = () =>{
      equipmentForm.emID=[]
      equipmentForm.gName=''
      equipmentForm.organization=''
      equipmentForm.description=''
      state.addEquipmentFormVisible = true
      emunionSearchFunc()
    }
    // 新建设备
    const addNewEquipment = () =>{
      state.addEquipmentFormVisible = false
      addOneEquipmentgroup((equipmentForm.emID).toString(),equipmentForm.gName,equipmentForm.organization,equipmentForm.description).then( (res) => {
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
    const editGroupForm = reactive({
      emID:[],
      groupid:'',
      groupname: '',
      organization: '',
      description:'',
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
      equipmentForm.emID=[]
      for(const i in state.multipleSelection.value){
          const a = state.multipleSelection.value[i].equipmentid
          equipmentForm.emID.push(a)
          console.log(equipmentForm.emID)
        }
      // equipmentForm.emID
      // console.log(state.multipleSelection.value[0].equipmentid)
    }
    const handleSelectionChange2 = (val) => {
      state.multipleSelection2.value = val
      editGroupForm.emID=[]
      for(const i in state.multipleSelection2.value){
          const a = state.multipleSelection2.value[i].equipmentid
          editGroupForm.emID.push(a)
          console.log(editGroupForm.emID)
        }
      // equipmentForm.emID
      // console.log(state.multipleSelection.value[0].equipmentid)
    }

    const refresh = () => {
      getList()
      ElMessage({
        message: '刷新成功',
        type: 'success',
      })
    }

    // 编辑设备信息
    const editGroupFormFunc = (index,row) =>{
      state.editEquipmentFormVisible=true;
      console.log(index,row)
      editGroupForm.groupid = row.equipmentgroupid
      editGroupForm.groupname = row.groupname
      editGroupForm.organization = row.organization
      editGroupForm.description = row.description
      emunionSearchFunc2()
      console.log(editGroupForm.groupid,editGroupForm.groupname,editGroupForm.organization,editGroupForm.description )
    }

    //删除当前行数据
    const deleteThisRow = (index,row) =>{
      console.log(index,row)
      deleteEquipmentgroup(row.equipmentgroupid).then( (res) => {
        console.log(res.data)
        if(res.data.code === 200){
          ElMessage({
            message: '分组删除成功!',
            type: 'success',
          })
          state.currentPage = 1
          getList()
        }
        // if(res.data.code === 201){
        //   ElMessage({
        //     message: '该设备组中有设备，无法删除!',
        //   })

        // }
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
    // 改变设备列表分页单页大小
    const emhandleSizeChange = (val) =>{
      console.log(`${val} items per page`)
      state.empageSize = val
      geteList()
    }
    const emhandleSizeChange2 = (val) =>{
      console.log(`${val} items per page`)
      state.empageSize = val
      emunionSearchFunc2()
    }
    // 修改设备列表当前分页
    const emhandleCurrentChange = (val) =>{
      console.log(`current page: ${val}`)
      state.emcurrentPage = val
      geteList()
    }
    const emhandleCurrentChange2 = (val) =>{
      console.log(`current page: ${val}`)
      state.emcurrentPage = val
      emunionSearchFunc2()
    }
    //获取设备列表
    const geteList = () =>{
      getEquipmentList(state.emcurrentPage,state.empageSize).then( (res) => {
        console.log(res.data)
        state.emtotalCount = res.data.data.equipment.total
        state.emtableData = res.data.data.equipment.records
      })
    }
    // 获取设备分组列表
    const getList = () =>{
      getEquipmentgroupList(state.currentPage,state.pageSize).then( (res) => {
        console.log(res.data)
        state.totalCount = res.data.data.equipmentGroups.total
        state.tableData = res.data.data.equipmentGroups.records
      })
    }

    // 打开界面详情页内容
    const openEquipmentDetail = (index,row) =>{
      console.log(index,row)
      state.equipmentDetailVisible=true
      editGroupForm.groupname = row.groupname
      editGroupForm.organization = row.organization
      editGroupForm.description = row.description
      emunionSearchFunc2()
    }

    // 修改设备信息
    const modifyEquipmentInfo = () =>{
      state.editEquipmentFormVisible = false
      console.log((editGroupForm.emID).toString(),editGroupForm.groupid,editGroupForm.groupname,editGroupForm.organization,editGroupForm.description )
      modifyInfo((editGroupForm.emID).toString(),editGroupForm.description,editGroupForm.groupid,editGroupForm.groupname,
      editGroupForm.organization,
      ).then( (res) => {
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
    //分组联合查询
    const unionSearchFunc = () => {
      state.currentPage = 1
      unionSearch(state.currentPage,state.inputName,state.organizationValue,state.pageSize).then( (res) => {
        console.log(res.data)
        state.totalCount = res.data.data.equipmentGroup.total
        state.tableData = res.data.data.equipmentGroup.records
      })
    }
    //设备联合查询
    const emunionSearchFunc = () => {
      state.emcurrentPage = 1
      emunionSearch('',state.emcurrentPage,state.inputemName,'','','','','',state.resolvingPowerValue,state.empageSize,'').then( (res) => {
        console.log(res.data)
        state.emtotalCount = res.data.data.equipment.total
        state.emtableData = res.data.data.equipment.records
      })
    }
    //编辑设备联合查询
    const emunionSearchFunc2 = () => {
      state.emcurrentPage = 1
      emunionSearch('',state.emcurrentPage,state.inputemName2,'',editGroupForm.groupname,'','','',state.resolvingPowerValue2,state.empageSize,'').then( (res) => {
        console.log(res.data)
        state.emtotalCount = res.data.data.equipment.total
        state.emtableData = res.data.data.equipment.records
      })
    }
    // 清除全部
    const cleanAll = () =>{
      state.inputName=''
      state.organizationValue=''
    }
    const cleanAll2 = () =>{
      state.inputemName=''
      state.resolvingPowerValue=''
      state.inputemName2=''
      state.resolvingPowerValue2=''
    }
    //搜索分组初始化
    const getSearch = () =>{
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
    //搜索计划初始化
    onMounted(()=>{
      getList(),
      getSearch(),
      geteList()
    })

    return {
      getSearch,
      ...toRefs(state),
      ElTable,
      handleSelectionChange,
      handleSelectionChange2,
      equipmentForm,
      handleClick,
      equipmentDetailInfo,
      ElMessage,
      refresh,
      editGroupForm,
      editGroupFormFunc,
      deleteThisRow,
      handleSizeChange,
      handleCurrentChange,
      emhandleSizeChange,
      emhandleCurrentChange,
      emhandleSizeChange2,
      emhandleCurrentChange2,
      openadd,
      addNewEquipment,
      getList,
      openEquipmentDetail,
      modifyEquipmentInfo,
      Search,
      unionSearchFunc,
      emunionSearchFunc,
      emunionSearchFunc2,
      cleanAll,
      cleanAll2,
      geteList
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
.searchbox{
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  .inputb{
    display: flex;
    justify-content: space-around;
    .condition{
      padding:0 1rem;
      display: flex;
      align-items: center;
	    justify-content: center;
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
.eltable1{
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
.bottomNav1{
  width: 100%;
  display: flex;
  justify-content: end;
}
</style>
