<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务名称" prop="taskName">
        <el-input v-model="queryParams.taskName" placeholder="请输入任务名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="截止时间" prop="deadline">
        <el-date-picker clearable v-model="queryParams.deadline" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择截止时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['project:tasks:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['project:tasks:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['project:tasks:remove']">删除</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:sgtask:export']"
        >导出</el-button>
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="sgtaskList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务名称" align="center" prop="taskName" />
      <el-table-column label="负责人" align="center" prop="userName" />
      <el-table-column label="截止时间" align="center" prop="deadline" width="140">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deadline, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="进度" align="center" prop="taskFinished" /> -->
      <el-table-column label="预计工时" align="center" prop="tasksHours" />
      <el-table-column label="已用工时" align="center" prop="tasksUsehours" />
      <!-- <el-table-column label="任务状态" align="center" prop="taskStatus" /> -->

      <el-table-column label="任务状态" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.oa_tasks_tasks_status" :value="scope.row.taskStatus" />
        </template>
      </el-table-column>

      <el-table-column label="优先级" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.oa_tasks_tasks_num" :value="scope.row.taskNum" />
        </template>
      </el-table-column>

      <!-- <el-table-column label="优先级" align="center" prop="taskNum" /> -->

      <!-- <el-table-column label="指派状态 " align="center" prop="taskFlag" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleSee(scope.row)"
            v-hasPermi="['project:tasks:add']">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-folder-opened" @click="handleFile(scope.row)"
            v-hasPermi="['project:tasks:add']">附件上传</el-button>
          <!-- <el-button size="mini" type="text" icon="el-icon-switch-button" @click="handleFinish(scope.row)"
            v-hasPermi="['project:tasks:add']">完成</el-button> -->
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改任务对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="form.taskName" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="负责人" prop="userId">
          <el-select v-model="form.userId" placeholder="请选择负责人">
            <el-option v-for="item in personList" :key="item.userId" :label="item.nickName"
              :value="item.userId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="截止时间" prop="deadline">
          <el-date-picker clearable v-model="form.deadline" type="date" value-format="yyyy-MM-dd" placeholder="请选择截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="任务目标" prop="taskGoal">
          <editor v-model="form.taskGoal" :min-height="192" />
        </el-form-item>
        <el-form-item label="预计工时" prop="tasksHours">
          <el-input v-model="form.tasksHours" placeholder="请输入预计工时" />
        </el-form-item>
        <el-form-item label="优先级" prop="taskNum">
          <el-select v-model="form.taskNum" placeholder="请选择任务优先级">
                  <el-option
                    v-for="dict in dict.type.oa_tasks_tasks_num"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改任务对话框 -->
    <el-dialog title="任务目标" :visible.sync="seeopen" width="900px" append-to-body>
      <el-form ref="form" :model="form" label-width="70px">
        <el-form-item label="任务目标" disabled>
          <!-- <editor v-model="form.taskGoal" :min-height="192"/> -->
          <el-input disabled v-html="form.taskGoal" type="textarea" />
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-html="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="附件列表">
          <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear"
            tag="ul">
            <li :key="file.pathName" class="el-upload-list__item ele-upload-list__item-content"
              v-for="(file, index) in fileList">
              <el-link :href="`${baseUrl}${file.pathName}`" :underline="false" target="_blank">
                <span class="el-icon-document"> {{ getFileName(file.pathName) }} </span>
              </el-link>
              <div class="ele-upload-list__item-content-action">
                <!-- <el-link :underline="false" @click="handleDelete(index)" type="danger">删除</el-link> -->
                <el-link :underline="false" @click="downLoad(index)" type="danger">下载</el-link>
              </div>
            </li>
          </transition-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <!-- <el-button type="primary" @click="submitForm">确 定</el-button> -->
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 附件上传弹出层 -->
    <el-dialog title="附件上传" @close="closeDialog" style="margin-top:100px" :visible.sync="uploadShow" width="400px"
      append-to-body>
      <FileUpload :value="uploadList" @input="uploadSuccess" @delete="uploadDelete"></FileUpload>
    </el-dialog>


  </div>
</template>

<script>
import { listSgtask, getSgtask, delSgtask, addSgtask, updateSgtask, addTaskFiles, getTaskFiles, deletTaskFiles } from "@/api/project/sgtask";
import { getProset, getProPerson, deletProFiles, getProjectFile, addProFiles } from "@/api/project/proset";

export default {
  name: "TasksDetail",
  dicts: ['oa_tasks_tasks_status', 'oa_tasks_tasks_num'],
  data() {
    return {
      seeopen: false, // 查看对话框
      baseUrl: process.env.VUE_APP_BASE_API,  //  基础路径
      fileList: [], // 上传附件列表
      uploadList: [], // 项目附件列表
      fileEmpty: true, // 附件为空显示标志
      uploadShow: false,  // 上传弹出框
      // 项目ID
      proId: undefined,
      tasksId: undefined,
      // 项目成员
      personList: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 任务ID
      taskId: undefined,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 任务表格数据
      sgtaskList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskName: null,
        tasksId: null,
        tasksName: null,
        userId: null,
        userName: null,
        deadline: null,
        taskGoal: null,
        taskFinished: null,
        tasksHours: null,
        tasksUsehours: null,
        taskStatus: null,
        taskNum: null,
        taskFlag: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        taskName: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "负责人不能为空", trigger: "change" }
        ],
        tasksHours: [
          { required: true, message: '预计工时不能为空'},
          { type: 'number', message: '预计工时应为数字',transform: (value) => Number(value)}
        ],
      }
    };
  },
  created() {
    let str = this.$route.params && this.$route.params.tasksId;
    if (str != undefined) {
      str = str.split("&");
      this.tasksId = str[0];
      this.proId = str[1];
      this.getPersons();
    }
    this.getList();
  },
  methods: {
    // 下载文件
    downLoad(index) {
      let data = this.fileList[index].pathName;
      this.$download.resource(data);
    },
    // 获取文件名称
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        let newname = name.slice(name.lastIndexOf("/") + 1);
        let preName = newname.slice(0, newname.indexOf("_"));
        let appName = newname.slice(newname.indexOf("."));
        return preName + appName;
      } else {
        return "";
      }
    },
    // 获取附件列表
    getProjectFiles() {
      let _this = this;
      getTaskFiles(this.taskId).then(res => {
        if (res.data.length > 0) {
          _this.fileList = res.data;
        }
      })
    },
    // 列表附件按钮
    handleFile(scope) {
      this.uploadShow = true;
      this.taskId = scope.taskId;
    },
    // 文件上传组件回传
    uploadSuccess(data) {
      let _this = this;
      addTaskFiles({ taskId: this.taskId, path: data }).then(res => {
      }).catch(err => {
      })
    },
    // 文件上传组件删除
    uploadDelete(data) {
      let _this = this;
      deletTaskFiles({ taskId: this.taskId, path: data }).then(res => {
        _this.getProjectFiles();
      })
    },
    // 关闭对话框
    closeDialog() {
      this.uploadList = [];
    },
    // 获取项目成员
    getPersons() {
      getProPerson(this.proId).then(res => {
        this.personList = res.data.proUser;
      })
    },
    /** 查询任务列表 */
    getList() {
      this.queryParams.tasksId = this.tasksId;
      this.loading = true;
      listSgtask(this.queryParams).then(response => {
        this.sgtaskList = response.rows;
        this.sgtaskList.forEach(element => {
          element.taskFlag = element.taskFlag ? "已指派" : "待指派";
        });
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.seeopen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        taskId: null,
        taskName: null,
        tasksId: null,
        tasksName: null,
        userId: null,
        userName: null,
        deadline: null,
        taskGoal: null,
        taskFinished: null,
        tasksHours: null,
        tasksUsehours: null,
        taskStatus: null,
        taskNum: null,
        taskFlag: null,
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
      this.ids = selection.map(item => item.taskId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加任务";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      let _this = this;
      this.reset();
      const taskId = row.taskId || this.ids
      getSgtask(taskId).then(response => {
        _this.form = response.data;
        _this.open = true;
      });
    },
    /** 查看按钮操作 */
    handleSee(row) {
      let _this = this;
      this.taskId = row.taskId;
      this.reset();
      const taskId = row.taskId || this.ids
      getSgtask(taskId).then(response => {
        _this.form = response.data;
        _this.getProjectFiles();
        _this.seeopen = true;
      });
    },
    // 关闭按钮
    handleFinish(row) {
      this.reset();
      this.form.taskId = row.taskId;
      this.form.tasksId = row.tasksId;
      this.form.taskStatus = 3;
      this.$modal.confirm('是否关闭该任务？').then(res=>{
        return updateSgtask(this.form);
      }).then(function () {
        this.getList();
        this.$modal.msgSuccess("已完成任务 + 1");
      }).catch(() => { });
    },
    /** 提交按钮 */
    submitForm() {
      this.form.tasksId = this.tasksId;
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.taskId != null) {
            updateSgtask(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSgtask(this.form).then(response => {
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
      const taskIds = row.taskId || this.ids;
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        return delSgtask(taskIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/sgtask/export', {
        ...this.queryParams
      }, `sgtask_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
<style scoped lang="scss">
// ===
.upload-file-list .el-upload-list__item {
  border: 1px solid #e4e7ed;
  line-height: 2;
  margin-bottom: 10px;
  position: relative;
}

.upload-file-list .ele-upload-list__item-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: inherit;
}

.ele-upload-list__item-content-action .el-link {
  margin-right: 10px;
}
</style>
