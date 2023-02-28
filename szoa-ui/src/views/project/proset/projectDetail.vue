<template>
  <div class="app-container home proDetailBox">
    <el-row :gutter="20">

      <el-col :xs="24" :sm="24" :md="8" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>项目信息</span>
          </div>
          <div class="body" style="line-height:20px">
            <p>
              <i class="el-icon-folder-checked"></i> 项目名称：{{ proInfo.proName }}
              <!-- <el-link href="http://www.ruoyi.vip" target="_blank">http://www.ruoyi.vip</el-link> -->
            </p>
            <p>
              <i class="el-icon-user"></i> 项目经理：<a href="javascript:;">{{ proInfo.userName }}</a>
            </p>
            <p>
              <i class="el-icon-postcard"></i> 项目状态：进行中
            </p>
            <p style="display:flex;align-items: center;margin-top:0px;margin-bottom: 0px;">
              <i class="el-icon-odometer"></i>&nbsp;<span
                style="margin-left:4px;margin-right: 4px;">项目进度：</span>
                <!-- <el-slider v-model="proPercent"
                style="width: 70%;margin-top: 2px;" disabled></el-slider> -->
                <el-progress :percentage="proPercent" style="width: 70%;margin-top: 2px;" :color="customColor"></el-progress>
            </p>
            <p>
              <i class="el-icon-coordinate"></i> 成员人数：{{ personList.length + 1 }}
            </p>
            <p>
              <i class="el-icon-time"></i> 截止日期：{{ parseTime(proInfo.endTime, '{y}-{m}-{d}') }}
            </p>
          </div>
        </el-card>

        <el-divider />

        <el-dialog title="附件上传" @close="closeDialog" style="margin-top:100px" :visible.sync="uploadShow" width="400px"
          append-to-body>
          <FileUpload :value="uploadList" @input="uploadSuccess" @delete="uploadDelete"></FileUpload>
        </el-dialog>

        <el-card class="update-log">
          <!-- 文件列表 -->
          <div slot="header" class="clearfix">
            <span>附件列表</span>
            <el-button style="float: right; padding: 3px 0" type="text" @click="showUpload">上传</el-button>
          </div>
          <div class="body">
            <p style="width: 100%;text-align:center" v-if="fileEmpty">空</p>
            <transition-group v-if="!fileEmpty" class="upload-file-list el-upload-list el-upload-list--text"
              name="el-fade-in-linear" tag="ul">
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
          </div>
        </el-card>
      </el-col>

      <!-- 右边主体 -->
      <el-col :xs="24" :sm="24" :md="16" :lg="16">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>任务包列表</span>
          </div>
          <!-- <div slot="body" class="clearfix"> -->
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                v-hasPermi="['project:tasks:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
              <!-- <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                v-hasPermi="['system:tasks:edit']">修改</el-button> -->
                <el-button type="success" plain icon="el-icon-edit" size="mini"  :disabled="multiple"  @click="handleAssign"
                v-hasPermi="['project:tasks:edit']">指派</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                v-hasPermi="['project:tasks:remove']">删除</el-button>
            </el-col>
            <!-- <el-col :span="1.5">
                  <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
                    v-hasPermi="['system:tasks:export']">导出</el-button>
                </el-col> -->
            <!-- <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar> -->
          </el-row>

          <el-table v-loading="loading" :data="tasksList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="45" align="center" />
            <el-table-column label="任务包" width="140" align="center" prop="tasksName" />
            <el-table-column label="负责人" align="center" prop="userName" />
            <el-table-column label="进度" align="center" prop="percent" />
            <el-table-column label="截止时间" align="center" prop="deadline" width="100">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.deadline, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="已用工时" align="center" prop="tasksUsedays" width="120" />
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
                  v-hasPermi="['system:tasks:remove']">删除</el-button> -->
              </template>
            </el-table-column>
          </el-table>

          <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize" @pagination="getList" />

          <!-- 添加或修改任务包对话框 -->
          <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="120px">
              <el-form-item label="任务包名称" prop="tasksName">
                <el-input v-model="form.tasksName" placeholder="请输入任务包名称" />
              </el-form-item>
              <el-form-item label="负责人" prop="userId">
                <el-select v-model="form.userId" placeholder="请选择负责人">
                  <el-option v-for="item in personList" :key="item.userId" :label="item.nickName"
                    :value="item.userId"></el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="截止时间" prop="deadline">
                <el-date-picker clearable v-model="form.deadline" type="date" value-format="yyyy-MM-dd"
                  placeholder="请选择截止时间">
                </el-date-picker>
              </el-form-item>
              <el-form-item label="预计人天" prop="tasksDays">
                <el-input v-model="form.tasksDays" placeholder="请输入预计人天" />
              </el-form-item>
              <el-form-item label="优先级" prop="tasksNum">
                <el-select v-model="form.tasksNum" placeholder="请选择项目优先级">
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

           <!-- 指派人员选择 -->
           <el-dialog title="任务包指派" :visible.sync="assignopen" width="400px" append-to-body>
            <el-form ref="assginForm" :model="assignForm" label-width="100px">
              <el-form-item label="负责人">
                <el-select v-model="assignUserId" placeholder="请选择负责人">
                  <el-option v-for="item in personList" :key="item.userId" :label="item.nickName"
                    :value="item.userId"></el-option>
                </el-select>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button type="primary" @click="submitAssign">确 定</el-button>
              <el-button @click="assignCancel">取 消</el-button>
            </div>
          </el-dialog>

        </el-card>
      </el-col>

    </el-row>
  </div>
</template>

<script>
import { getProset, getProPerson, deletProFiles, getProjectFile, addProFiles } from "@/api/project/proset";
import { listTasks, getTasks, delTasks, addTasks, updateTasks,assignTasks } from "@/api/project/tasks";

export default {
  name: "ProDetail",
  dicts: ['oa_tasks_tasks_status', 'oa_tasks_tasks_num'],
  data() {
    return {
      // 指派人员
      assignUserId:undefined,
      // 指派弹出层标识
      assignopen:false,
      // 进度条颜色
      customColor: undefined,
      // 项目进度
      proPercent: 0,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 项目成员
      personList: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 任务包表格数据
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
      // 表单参数
      form: {},
      // 表单参数
      assignForm: {
      },
      // 表单校验
      rules: {
        tasksName: [
          { required: true, message: "任务包名称不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "负责人不能为空", trigger: "change" }
        ],
        tasksDays: [
          { required: true, message: '预计人天不能为空'},
          { type: 'number', message: '预计人天应为数字',transform: (value) => Number(value)}
        ],
      },
      baseUrl: process.env.VUE_APP_BASE_API,  //  基础路径
      fileList: [], // 上传附件列表
      uploadList: [], // 项目附件列表
      fileEmpty: true, // 附件为空显示标志
      uploadShow: false,  // 上传弹出框
      proId: undefined,  // 项目Id
      proInfo: {}, // 项目信息
    };
  },
  created() {
    this.proId = this.$route.params && this.$route.params.proId;
    if (this.proId != undefined) {
      this.getInfo();
      this.getList();
      this.getPersons();
      this.getProjectFiles();
    }
  },
  methods: {
    // 获取项目成员
    getPersons() {
      getProPerson(this.proId).then(res => {
        this.personList = res.data.proUser;
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.proId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 获取项目情况
    getInfo() {
      var _this = this;
      getProset(this.proId).then(response => {
        _this.proInfo = response.data;
        if(_this.proInfo.finishDemand == 0) {
          _this.proPercent = 0;
        } else {
          _this.proPercent = Math.floor(_this.proInfo.finishDemand / _this.proInfo.allDemand * 100);

        }
      });
    },
    // 文件上传组件删除
    uploadDelete(data) {
      let _this = this;
      deletProFiles({ proId: this.proId, path: data }).then(res => {
        _this.getProjectFiles();
      })
    },
    // 对象转成指定字符串分隔
    listToString(list, separator) {
      let strs = "";
      separator = separator || ",";
      for (let i in list) {
        strs += list[i].url + separator;
      }
      return strs != '' ? strs.substr(0, strs.length - 1) : '';
    },
    // 获取附件列表
    getProjectFiles() {
      let _this = this;
      getProjectFile(this.proId).then(res => {
        if (res.data.length > 0) {
          _this.fileList = res.data;
          _this.fileEmpty = false;
        }
      })
    },
    // 文件上传组件回传
    uploadSuccess(data) {
      let _this = this;
      addProFiles({ proId: this.proId, path: data }).then(res => {
        _this.getProjectFiles();
      }).catch(err => {
      })
    },
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
    // 删除文件
    handleDelete(index) {
      let data = this.fileList.splice(index, 1);
      this.uploadDelete(data[0].pathName);
    },
    // 显示附件上传弹出框
    showUpload() {
      this.uploadShow = true;
    },
    // 关闭对话框
    closeDialog() {
      this.uploadList = [];
    },

    /** 查询任务包列表 */
    getList() {
      this.queryParams.proId = this.proId;
      this.loading = true;
      listTasks(this.queryParams).then(response => {
        this.tasksList = response.rows;
        this.tasksList.forEach(element => {
          if(element.taskFinished) {
            element.percent = Math.floor(element.taskFinished / element.taskCount * 100) + "%"
          } else {
            element.percent = "0%";
          }
          
        });
        this.total = response.total;
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
      this.reset();
      this.open = true;
      this.title = "添加任务包";
    },
    // 指派
    handleAssign(){
      this.assignUserId = undefined;
      this.assignopen = true;
    },
    // 取消指派
    assignCancel() {
      this.assignUserId = undefined;
      this.assignopen = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const tasksId = row.tasksId || this.ids
      getTasks(tasksId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改任务包";
      });
    },
     /** 指派按钮 */
    submitAssign() {      
      if(this.assignUserId == undefined) {
        this.$modal.msg("请选择分派人员");
        return;
      } 
      let _this = this;
      let tasksIdsStr = this.ids.join(",");
      assignTasks({userId:this.assignUserId,tasksIds:tasksIdsStr}).then(res=>{
        _this.$modal.msgSuccess("指派成功");
        _this.getList();
        _this.assignopen = false;
      })
      
    },
    /** 提交按钮 */
    submitForm() {
      this.form.proId = this.proId;
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
              this.getInfo();
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
        this.getInfo();
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
.proDetailBox .el-slider__runway.disabled .el-slider__bar {
  background-color: #1890ff !important;
}
</style>
<style scoped lang="scss">
.home {
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans",
  "Helvetica Neue",
  Helvetica,
  Arial,
  sans-serif;
  font-size: 14px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}

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

