<template>
  <div class="TOP">
    <div class="shuru" ref="loginFormRef">
        <div class="accountName">
        <span>账户名：</span>
        <el-input
            v-model="input_name"
            class="input"
            size="small"
            placeholder="请输入账户名称"
        />
        </div>
        <div class="mechanism">
        <span>所属机构：</span>
        <el-select v-model="value_ji" class="select" placeholder=" " size="small">
            <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
            />
        </el-select>
        </div>
        <div class="role">
        <span>所属角色：</span>
        <el-select v-model="value_jue" class="select" placeholder="请选择所属角色" size="small">
            <el-option
            v-for="item in options2"
            :key="item.value"
            :label="item.label"
            :value="item.value"
            />
        </el-select>
        </div>
        <div class="state">
        <span>状态：</span>
        <el-select v-model="value_zhuang" class="select" placeholder="所有" size="small">
            <el-option
            v-for="item in options3"
            :key="item.value"
            :label="item.label"
            :value="item.value"
            />
        </el-select>
        </div>
    </div>
    <div class="button">
      <el-button @click="reset()">重置</el-button>
      <el-button type="primary" @click="unionSearchU()" >查询</el-button>
    </div>
  </div>
  <div class="second">
    <el-button class="button1" type="primary" @click="dialogFormVisible = true">新建账户</el-button>
    <!-- 添加账户弹出框 -->
    <el-dialog title="添加账户" v-model="dialogFormVisible" @close="dialogclosed">
      <el-form ref="addformRef" :model="form" :rules="addRules">
        <el-form-item label="账户名:" prop="account" :label-width="formLabelWidth">
          <el-input v-model="form.account" autocomplete="off" placeholder="请输入账户名"></el-input>
        </el-form-item>
        <el-form-item label="密码:" prop="password" :label-width="formLabelWidth">
          <el-input v-model="form.password" type="password"
          placeholder="请输入密码"
          show-password autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属机构:" prop="organization" :label-width="formLabelWidth">
          <el-select v-model="form.organization" placeholder=" ">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="所属角色:" prop="role" :label-width="formLabelWidth">
          <el-select v-model="form.role" placeholder="请选择所属角色">
            <el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="账号状态:" prop="accountstate" :label-width="formLabelWidth">
          <el-select v-model="form.accountstate" placeholder="请选择状态">
            <el-option label="启用" value="启用"></el-option>
            <el-option label="停用" value="停用"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="真实姓名:" prop="realname" :label-width="formLabelWidth">
          <el-input v-model="form.realname" autocomplete="off" placeholder="请输入用户真实姓名"></el-input>
        </el-form-item>
        <el-form-item label="邮箱:" prop="email" :label-width="formLabelWidth">
          <el-input v-model="form.email" autocomplete="off" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="手机号:" prop="telephone" :label-width="formLabelWidth">
          <el-input v-model="form.telephone" autocomplete="off" placeholder="请输入手机号"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="adduser()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
  <div class="table">
    <!-- 表格 -->
    <el-table :data="tableData" style="width: 100%" >
      <el-table-column prop="account" label="账户名" width="124" />
      <el-table-column prop="organization" label="所属机构" width="130" />
      <el-table-column prop="role" label="所属角色" width="140" />
      <el-table-column prop="accountstate" label="状态" width="90" />
      <el-table-column prop="realname" label="真实姓名" width="100" />
      <el-table-column prop="telephone" label="手机号" width="120" />
      <el-table-column prop="email" label="邮箱" width="180" />
      <el-table-column prop="updatedate" label="更新时间" width="170" />
      <el-table-column prop="usertype" label="用户类型" width="80" />
      <el-table-column  label="操作" width="162" >
        <template v-slot="scope">
          <el-button class="button1" type="primary" v-if="scope.row.accountstate === '已启用'"  @click="openuse(scope.$index,scope.row)" text link>停用</el-button>
          <el-button class="button1" type="primary" v-if="scope.row.accountstate === '已停用'"  @click="openuse(scope.$index,scope.row)" text link>启用</el-button>
          <el-button class="button2" type="primary" @click="edit(scope.row)" text link>编辑</el-button>
          <el-button class="button3" type="primary" @click="deleteu(scope.$index,scope.row)" text link>删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 停用/启用弹出框 -->
    <el-dialog v-model="dialogVisible" width="30%">
      <span>是否{{usejudge}}</span>
      <template #footer >
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="use()">确认</el-button>
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
    <!-- 编辑用户弹出框 -->
    <el-dialog title="编辑用户" v-model="dialogFormVisible2" width="70%">
      <el-form :model="form2" ref="editformRef" :rules="addRules">
        <el-form-item label="账户名:" prop="account" :label-width="formLabelWidth">
          <el-input v-model="form2.account" autocomplete="off" placeholder="请输入账户名"></el-input>
        </el-form-item>
        <el-form-item label="密码:" prop="password" :label-width="formLabelWidth">
          <el-input v-model="form2.password" type="password"
          placeholder="请输入密码"
          show-password autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="所属机构:" prop="organization" :label-width="formLabelWidth">
          <el-select v-model="form2.organization" placeholder=" ">
            <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="所属角色:" prop="role" :label-width="formLabelWidth">
          <el-select v-model="form2.role" placeholder="请选择所属角色">
            <el-option v-for="item in options2" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="账号状态:" prop="accountstate" :label-width="formLabelWidth">
          <el-select v-model="form2.accountstate" placeholder="请选择状态">
            <el-option label="启用" value="启用"></el-option>
            <el-option label="停用" value="停用"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="真实姓名:" prop="realname" :label-width="formLabelWidth">
          <el-input v-model="form2.realname" autocomplete="off" placeholder="请输入用户真实姓名"></el-input>
        </el-form-item>
        <el-form-item label="邮箱:" prop="email" :label-width="formLabelWidth">
          <el-input v-model="form2.email" autocomplete="off" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item label="手机号:" prop="telephone" :label-width="formLabelWidth">
          <el-input v-model="form2.telephone" autocomplete="off" placeholder="请输入手机号"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogFormVisible2 = false">取 消</el-button>
          <el-button type="primary" @click="edituser()">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>


<script>
import {changeUser,unionSearch,deleteUser,modifyInfo,getuserList,addOneuser,getdList} from "@/api/userList";
import { ElTable,ElMessage  } from 'element-plus';
import { ref } from '@vue/reactivity'
//   import { Search } from '@element-plus/icons-vue'
  export default {
    // components: {
    //   Search
    // },
    setup(){
    //时间转化工具函数
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
    },
    data() {
      return {
        chooseIndex: 0,
        options: [],
        options2: [],
        options3: [
        {
          value: '',
          label: '所有',
        },
        {
          value: '启用',
          label: '启用',
        },
        {
          value: '停用',
          label: '停用',
        }],
        input_name:'',
        value_ji:'',
        value_jue:'',
        value_zhuang:'',
        tableData:[
        ],
        dialogFormVisible: false,
        form: {
          account: '',
          password:'',
          organization: '',
          role: '',
          accountstate: '',
          realname: '',
          telephone: '',
          email: '',
        },
        addRules:{
          account:[{
            required:true,
            message:'请输入账户名',
            trigger:'blur',
          }],
          password:[
            {
              required:true,
              message:'请输入密码',
              trigger:'blur',
            },
            // {
            //   min:8,
            //   max:16,
            //   message:'密码长度为8到16位，且同时包含字母数字和特殊字符(.~!@#$%^&*_-)',
            //   trigger:'blur',
            // }
          ],
          organization:[{
            required:true,
            message:'请选择所属机构',
            trigger:'blur',
          }],
          role:{
            required:true,
            message:'请选择所属角色',
            trigger:'blur',
          },
          accountstate:{
            required:true,
            message:'请选择账号状态',
            trigger:'blur',
          },
          realname:{
            required:true,
            message:'请输入真实姓名',
            trigger:'blur',
          },
          telephone:{
            required:true,
            message:'请输入邮箱',
            trigger:'blur',
          },
          email:{
            required:true,
            message:'请输入正确的手机号',
            trigger:'blur',
          }

        },
        dialogFormVisible2: false,
        form2: {
          userid:'',
          account: '',
          password:'',
          organization: '',
          role: '',
          accountstate: '',
          realname: '',
          telephone: '',
          email: '',
        },
        formLabelWidth: '120px',
        dialogVisible:false,
        // 分页信息
        currentPage: 1,
        pageSize: 10,
        totalCount: 100,
        usejudge:'',
        usedate:{
          id:'',
          state:'',
        },
      }
    },
    created(){
      this.getList()
      this.getSearch()
    },
    methods:{
      getList(){
        getuserList(this.currentPage,this.pageSize).then( (res) => {
        console.log(res.data)
        this.totalCount = res.data.data.users.total
        this.tableData = res.data.data.users.records
        for (const line of this.tableData) {
          line.updatedate = new Date(line.updatedate).format("yyyy-MM-dd hh:mm:ss");
        }

      })
      },
      //获取搜索框
      getSearch(){
        getdList('organization').then( (res) => {
          console.log(res.data)
          for(const i in res.data.data.List){
            const a = res.data.data.List[i].organization
            const b = {}
            b.value = a
            b.label = a
            this.options.push(b)
          }
        })
        getdList('role').then( (res) => {
          console.log(res.data)
          for(const i in res.data.data.List){
            const a = res.data.data.List[i].role
            const b = {}
            b.value = a
            b.label = a
            this.options2.push(b)
          }
        })
      },
      reset(){
         this.input_name=''
         this.value_ji=''
         this.value_jue=''
         this.value_zhuang=''
      },
      //新建用户
      adduser(){
        this.$refs.addformRef.validate(valid=>{
          if(!valid) return
          this.dialogFormVisible = false
          addOneuser(this.form.account,this.form.password,this.form.organization,this.form.role,this.form.accountstate,this.form.realname,this.form.telephone,this.form.email).then( (res) => {
            console.log(res.data)

            if(res.data.code === 200){
              ElMessage({
                message: '新增用户成功!',
                type: 'success',
              })
              this.currentPage = 1
              this.getList()
            }
          }).finally( () => {
            console.log()
          })
        })
      },
      //修改用户
      edituser(){
        this.$refs.editformRef.validate(valid=>{
          if(!valid) return
          this.dialogFormVisible2 = false
          modifyInfo(this.form2.account,this.form2.password,this.form2.organization,this.form2.role,this.form2.accountstate,this.form2.realname,this.form2.telephone,this.form2.email,this.form2.userid).then( (res) => {
            console.log(res.data)
            if(res.data.code === 200){
              ElMessage({
                message: '账户信息修改成功!',
                type: 'success',
              })
              this.currentPage = 1
              this.getList()
            }
          })
        })
      },
      //删除用户信息
      deleteu(index,row){
        console.log(index,row)
        deleteUser(row.userid).then((res) => {
          console.log(res.data)
          if(res.data.code === 200){
            ElMessage({
              message: '账户删除成功!',
              type: 'success',
            })
            this.currentPage = 1
            this.getList()
          }
        })
      },
      //联合查询
      unionSearchU(){
        this.currentPage=1
        unionSearch(this.input_name,this.value_ji,this.value_jue,this.value_zhuang,this.currentPage,this.pageSize).then( (res) =>{
          console.log(res.data)
          this.totalCount = res.data.data.users.total
          this.tableData = res.data.data.users.records
        })
      },
      openuse(index,row){
        console.log(index,row)
        this.chooseIndex = index
        if(row.accountstate === '已启用'){
          this.usejudge='停用'
        }
        else this.usejudge='启用'
        this.usedate.id=row.userid
        this.usedate.state=row.accountstate
        this.dialogVisible = true
      },
      use(index,row){
        this.dialogVisible = false
        console.log(index,row)
        changeUser(this.usedate.state,this.usedate.id).then((res) => {
          console.log(res.data)
          if(res.data.code === 200){
            ElMessage({
              message: '状态修改成功!',
              type: 'success',
            })
            this.currentPage = 1
            this.getList()
          }
        })
      },
      //打开编辑弹出框
      edit(row){
        this.dialogFormVisible2 = true
        this.form2=row
      },
      dialogclosed(){
        this.$refs.addformRef.resetFields()
      },
      //改变分页单页大小
      handleSizeChange(val){
        console.log(`${val} items per page`)
        this.pageSize = val
        this.getList()
      },
      //修改当前分页
      handleCurrentChange(val){
        console.log(`current page: ${val}`)
        this.currentPage = val
        this.getList()
      },
    }
  }
</script>

<style lang="less" scoped>
.TOP{
  padding:2rem;
  background-color: #545c64;
  .shuru{
    margin:0 2rem;
    display: flex;
    color: #FFF;
    justify-content: space-around;
    .accountName{
        display: flex;
        .input{
            width: 10rem;
            height: 1.6rem;
        }
    }
  }
  .button{
    width: 10rem;
    margin:1rem;
    margin-left:65.1rem;
  }
}
.second{
    background-color: #545c64;
    padding:1rem;
    margin-top: 1rem;
    .button1{
       margin-left:69.1rem;
    }
    .el-dialog{
      --el-dialog-width: 40%;
    }
    .el-dialog__body{
      border-top: 1px solid #ccc ;
    }
    .el-input{
      width: 30rem;

    }
}

.table{
  width: 100%;
  margin-top: 1rem;
  .el-table{
    --el-table-row-hover-bg-color:#434950;
    color:white;
    --el-table-header-text-color: white;
    --el-table-header-bg-color: #434950;
    --el-table-tr-bg-color: #545c64;
    --el-table-border-color: #6e6e6e;
    --el-table-border: 1px solid#6e6e6e;
    .el-button{
      width: 2.5rem;
    }
    .button2{
      margin-left: 0.35rem;
    }
    .button3{
      margin-left: 0.35rem;
    }
    /deep/.el-table__inner-wrapper::before{
      z-index: 1;
    }
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
