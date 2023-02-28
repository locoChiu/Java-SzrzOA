<template>
  <div class="app-container taskBox">
    <el-container>

      <el-aside width="190px" style="border-right: 1px solid #9D9D9D;background-color:#fff;padding-top: 16px;">
        <el-tree :data="data" highlight-current empty-text="无" :props="defaultProps" @node-click="handleNodeClick" :default-expand-all="true"></el-tree>
      </el-aside>

      <el-main>
        <!-- <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
          <el-form-item label="任务包名称" prop="tasksName">
            <el-input v-model="queryParams.tasksName" placeholder="请输入任务包名称" clearable
              @keyup.enter.native="handleQuery" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form> -->

        <!-- <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
              v-hasPermi="['system:tasks:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
              v-hasPermi="['system:tasks:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
              v-hasPermi="['system:tasks:remove']">删除</el-button>
          </el-col>
        </el-row> -->

        <el-table v-loading="loading" :data="tasksList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="45" align="center" />
          <!-- <el-table-column label="任务包" align="center" prop="tasksName" /> -->

          <el-table-column label="任务包" align="center" :show-overflow-tooltip="true">
            <template slot-scope="scope">
              <router-link :to="'/project/tasksId/' + scope.row.tasksId + '&' + currentProId" class="link-type">
                <span>{{ scope.row.tasksName }}</span>
              </router-link>
            </template>
          </el-table-column>

          <el-table-column label="负责人" align="center" prop="userName" />
          <el-table-column label="任务进度" align="center">
            <template slot-scope="scope">
              <p v-if="scope.row.proSpeed > 70" style="border-radius: 10px;margin:0 auto;width:80px;font-weight:700;background-color: #02DF82;">{{scope.row.proSpeed}}%</p>
              <p v-else-if="scope.row.proSpeed > 50" style="border-radius: 10px;margin:0 auto;width:80px;font-weight:700;background-color:	#FF8000;">{{scope.row.proSpeed}}%</p>
              <p v-else style="border-radius: 10px;margin:0 auto;width:80px;font-weight:700;background-color: #FF2D2D;">{{scope.row.proSpeed}}%</p>
            </template>
          </el-table-column>

          <el-table-column label="截止时间" align="center" prop="deadline" width="120">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.deadline, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="已用工时" align="center" prop="tasksUsedays" width="120" >
            <template slot-scope="scope">
              <el-tag>{{scope.row.tasksUsedays}} h</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.oa_tasks_tasks_status" :value="scope.row.tasksStatus" />
            </template>
          </el-table-column>
          <el-table-column label="优先级" align="center">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.oa_tasks_tasks_num" :value="scope.row.tasksNum" />
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['project:tasks:edit']">修改</el-button>
              <!-- <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['system:tasks:remove']">关闭</el-button> -->
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
          @pagination="getList" />

        <!-- 添加或修改任务包对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="任务包名称">
              <el-input v-model="form.tasksName" placeholder="请输入任务包名称" disabled/>
            </el-form-item>
            <el-form-item label="负责人" prop="userId">
              <el-select v-model="form.userId" placeholder="请选择负责人" disabled>
                <el-option v-for="item in personList" :key="item.userId" :label="item.nickName"
                  :value="item.userId"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="截止时间">
              <el-date-picker disabled clearable v-model="form.deadline" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择截止时间">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="预计工时">
              <el-input v-model="form.tasksDays" placeholder="请输入预计工时" disabled/>
            </el-form-item>
            <el-form-item label="优先级">
              <el-input v-model="form.tasksNum" placeholder="请输入任务包优先级" disabled/>
            </el-form-item>
            
            <el-form-item label="项目状态" prop="tasksStatus">
              <el-select v-model="form.tasksStatus" placeholder="请选择项目状态">
                    <el-option
                      v-for="dict in dict.type.oa_tasks_tasks_status"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    ></el-option>
                  </el-select>
            </el-form-item>

            <el-form-item label="备注信息" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" disabled/>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
      </el-main>
    </el-container>


  </div>
</template>
  
<script>
import { getProPerson,getUserProject } from "@/api/project/proset";
import { listTasks, getTasks, delTasks, addTasks, updateTasks } from "@/api/project/tasks";
import { login, logout, getInfo } from '@/api/login'
import { read } from "fs";


export default {
  name: "Tasks",
  dicts: ['oa_tasks_tasks_status', 'oa_tasks_tasks_num'],
  data() {
    return {
      // 菜单
      data: [{
          label: '全部项目',
          children:[],
          key:undefined
        }, {
          label: '未开始项目',
          children:[],
          key:undefined
        }, {
          label: '进行中项目',
          children:[],
          key:undefined
        }, {
          label: '暂停中项目',
          children:[],
          key:undefined
        }, {
          label: '已结束项目',
          children:[],
          key:undefined
        }],
        defaultProps: {
          children: 'children',
          label: 'label',
          key: 'key'
        },
      // 当前选中项目ID
      currentProId:undefined,
      // 负责人
      personList: [],
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 任务管理表格数据
      tasksList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tasksName: null,
        proId: null,
        proName: null,
        proUserId: null,
        proUserName: null,
        userId: null,
        userName: null,
        deadline: null,
        taskCount: null,
        taskFinished: null,
        tasksDays: null,
        tasksUsedays: null,
        tasksStatus: null,
        tasksNum: null,
        status: null,
      },
      // 用户信息
      userInfo:{},
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        tasksName: [
          { required: true, message: "任务包名称不能为空", trigger: "blur" }
        ],
        proId: [
          { required: true, message: "项目ID不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "负责人ID不能为空", trigger: "change" }
        ],
        userName: [
          { required: true, message: "负责人名称不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    var _this = this;
    // this.getList();
    getInfo().then(res=>{
      _this.userInfo = res.user;
      if(_this.userInfo.userId != undefined) {
        _this.getUserPro();
      }
    })
    
  },
  methods: {
    // 获取用户拥有的项目
    getUserPro(){
      let _this = this;
      getUserProject(this.userInfo.userId).then(res=>{
        let data1 = [];
        let data2 = [];
        let data3 = [];
        let data4 = [];
        let data5 = [];

        if(res.data.all.length > 0 ) {
          res.data.all.forEach(element => {
            var item = {};
            item.label = element.proName;
            item.key = element.proId;
            data1.push(item);
          });
        }

        if(res.data.nostart.length > 0 ) {
          res.data.nostart.forEach(element => {
            var item = {};
            item.label = element.proName;
            item.key = element.proId;
            data2.push(item);
          });
        }

        if(res.data.started.length > 0 ) {
          res.data.started.forEach(element => {
            var item = {};
            item.label = element.proName;
            item.key = element.proId;
            data3.push(item);
          });
        }

        if(res.data.stop.length > 0 ) {
          res.data.stop.forEach(element => {
            var item = {};
            item.label = element.proName;
            item.key = element.proId;
            data4.push(item);
          });
        }

        if(res.data.end.length > 0 ) {
          res.data.end.forEach(element => {
            var item = {};
            item.label = element.proName;
            item.key = element.proId;
            data5.push(item);
          });
        }

        _this.data[0].children = data1;
        _this.data[1].children = data2;
        _this.data[2].children = data3;
        _this.data[3].children = data4;
        _this.data[4].children = data5;
        
      })
    },
    // 树形项目点击
    handleNodeClick(data) {
        console.log(data);
        if(data.key != null) {
         this.currentProId = data.key;
         this.getList();
        }
      },
    // 获取项目成员
    getPersons() {
      getProPerson(this.currentProId).then(res => {
        this.personList = res.data.proUser;
      })
    },
    /** 查询任务管理列表 */
    getList() {
      if(this.currentProId != undefined) {
        this.queryParams.proId = this.currentProId;
      }
      // this.queryParams.userId = this.userInfo.userId;
      this.loading = true;
      listTasks(this.queryParams).then(response => {
        this.tasksList = response.rows;
        this.total = response.total;
        this.tasksList.forEach(element => {
          if(element.taskFinished == 0) {
            element.proSpeed = 0;
          } else {
            element.proSpeed = Math.floor(element.taskFinished / element.taskCount * 100);
          }
        });
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        tasksId: null,
        tasksName: null,
        proId: null,
        proName: null,
        proUserId: null,
        proUserName: null,
        userId: null,
        userName: null,
        deadline: null,
        taskCount: null,
        taskFinished: null,
        tasksDays: null,
        tasksUsedays: null,
        tasksStatus: null,
        tasksNum: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tasksId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      if(this.currentProId == undefined) {
        this.$modal.msgError("请先在右侧选择项目");
        return;
      }
      this.reset(); 
      this.getPersons();
      this.open = true;
      this.title = "添加任务管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getPersons();
      const tasksId = row.tasksId || this.ids
      getTasks(tasksId).then(response => {
        console.log(response);
        this.form = response.data;
        this.open = true;
        this.title = "修改任务管理";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if(this.currentProId == undefined) {
        this.$modal.msgError("请先在右侧选择项目");
        return;
      }
      this.form.proId = this.currentProId;
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tasksId != null) {
            updateTasks(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addTasks(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const tasksIds = row.tasksId || this.ids;
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        return delTasks(tasksIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/tasks/export', {
        ...this.queryParams
      }, `tasks_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style>
.taskBox .el-tree-node:focus > .el-tree-node__content {
  /* background-color: #C4E1FF!important; */
}
.taskBox .el-aside {
  padding: 8px 10px;
  /* box-sizing: border-box; */
}
.taskBox .el-tree {
  width: 100%;
  line-height: 34px;

}
.taskBox .el-tree-node__content {
  margin-bottom: 6px;
}
.taskBox .el-tree-node__label {
  box-sizing: border-box;
  /* padding: 4px; */
  font-weight: 500;
  font-size: 16px;
  letter-spacing: 1px;
}
</style>
  