<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['project:dayreport:add']">新增</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="dayreportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="填报日期" align="center" prop="workDate" width="180" required>
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.workDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="工时标签" align="center" prop="workType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.oa_tasks_work_type" :value="scope.row.workType" />
        </template>
      </el-table-column>

      <el-table-column label="当日工时" align="center" prop="workHours">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.workHours }} h</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="工作内容" align="center" prop="content" />
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />


    <!-- 添加或修改日报对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <div v-for="item in detailTask">
          <el-row>
            <el-col :span="8">
              <el-form-item label="任务名称">
                {{ item.taskName }}
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="总工时">
                <el-tag type="success">{{ item.tasksHours }} h</el-tag>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="剩余工时">
                <el-tag type="success">{{ item.taskLeaveHours }} h</el-tag>
              </el-form-item>
            </el-col>

          </el-row>

          <el-row>

            <el-col :span="8">
              <el-form-item label="截止时间" diabled>
                <span>{{ parseTime(item.deadline, '{y}-{m}-{d}') }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="实际工时">
                <el-input-number :min="0" :max="24" v-model="item.workHours" placeholder="实际工时">
                </el-input-number>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="20">
              <el-form-item label="任务进度">
                <el-slider :max='100' :step='1' v-model="item.taskSpeed"></el-slider>
              </el-form-item>
            </el-col>
          </el-row>
          <hr>
        </div>

        <el-row>
          <el-col :span="12">
            <el-form-item label="工时标签" prop="workType">
              <el-select v-model="form.workType" placeholder="工时标签">
                <el-option v-for="dict in dict.type.oa_tasks_work_type" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="日期" prop="workDate">
              <el-date-picker clearable v-model="form.workDate" type="date" value-format="yyyy-MM-dd"
                placeholder="请选择填报日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="18">
            <el-form-item label="备注" prop="content">
              <el-input v-model="form.content" type="textarea" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>

    </el-dialog>

    <!-- 选择任务对话框 -->
    <el-dialog title="请选择任务包" :visible.sync="taskChooseOpen" width="900px" append-to-body>
      <el-table @row-dblclick="chooseTask" :data="userTasks" style="width: 100%">
        <el-table-column prop="tasksName" align="center" label="任务包名">
        </el-table-column>
        <el-table-column prop="proName" align="center" label="所属项目" width="180">
        </el-table-column>
        <el-table-column prop="taskSpeed" align="center" label="进度" width="100">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.taskSpeed }}</el-tag>

          </template>
        </el-table-column>
        <el-table-column prop="taskLeaveHours" align="center" label="剩余人天" width="100">
          <template slot-scope="scope">
            <el-tag type="success">{{ scope.row.taskLeaveDays }} </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="taskLeaveHours" align="center" label="剩余工时" width="100">
          <template slot-scope="scope">
            <el-tag type="success">{{ scope.row.taskLeaveHours }} </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="截止时间" align="center" prop="deadline" width="140">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.deadline, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
      </el-table>

    </el-dialog>
  </div>
</template>
  
<script>
import { getUserProject } from "@/api/project/proset";
import { getAllUserTask } from "@/api/project/tasks";
import { listDayreport, getDayreport, delDayreport, addDayreport, updateDayreportaddDayHour, addDayHour } from "@/api/project/dayreport";
import { listSgtask, updateSgtask } from "@/api/project/sgtask";
import { getInfo } from '@/api/login'
import { isRejected } from "q";

export default {
  name: "Dayreport",
  dicts: ['oa_tasks_work_type'],
  data() {
    return {
      // 选择的任务
      chooseTaskInfo: {},
      // 任务对话框
      taskChooseOpen: false,
      // 任务列表
      taskList: [],
      // 用户信息
      userInfo: {},
      // 遮罩层
      loading: true,
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
      // 用户项目
      detailTask: [],
      // 日报表格数据
      dayreportList: [],
      // 弹出层标题
      title: "",
      // 用户任务包列表
      userTasks: [],
      // 是否显示弹出层
      open: false,
      // 当前选中项目ID
      currentProId: undefined,
      // 当前选中任务包ID
      currentTasksId: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        workType: null,
        workHours: null,
        workDate: null,
        status: null,
      },
      // 用户参与的项目
      userPro: [],
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workType: [
          { required: true, message: "工时标签不能为空", trigger: "blur" }
        ],
        workHours: [
          { required: true, message: "实际工时不能为空", trigger: "blur" }
        ],
        workDate: [
          { required: true, message: "填报日期不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "工作内容不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    var _this = this;
    getInfo().then(res => {
      _this.userInfo = res.user;
      _this.getUserPro();
      _this.getList();
    })
  },
  methods: {
    // 获取用户参与的项目
    getUserPro() {
      getUserProject(this.userInfo.userId).then(res => {
        this.userPro = res.data.started;
        if (this.userPro.length > 0) {
          this.getTasks();

        }
      })
    },
    // 任务选择
    chooseTask(data) {
      this.currentProId = data.proId;
      this.currentTasksId = data.tasksId;
      let query = {};
      query.tasksId = data.tasksId;
      query.taskStatus = 1;

      listSgtask(query).then(res => {
        this.detailTask = res.rows;
        if (this.detailTask.length > 0) {
          this.detailTask.forEach(element => {
            element.taskLeaveHours = element.tasksHours - element.tasksUsehours;
          })
          this.taskChooseOpen = false;
          this.open = true;
          this.title = "任务工时填报";
        } else {
          this.$modal.alertWarning("该任务包暂无任务可填报");
        }
      })
    },
    // 获取正在进行的任务列表
    getTask() {
      let queryForm = {};
      queryForm.userId = this.userInfo.userId;
      queryForm.taskStatus = 1;
      listSgtask(queryForm).then(res => {
        this.taskList = res.rows;
        this.taskList.forEach(element => {
          element.taskLeaveHours = element.tasksHours - element.tasksUsehours;
        });
      });
    },
    // 获取正在进行的任务包列表
    getTasks() {
      let ids = [];
      this.userPro.forEach(element => {
        ids.push(element.proId);
      });
      getAllUserTask(ids).then(res => {
        this.userTasks = res.data;
        this.userTasks.forEach(ele => {
          if (ele.taskFinished) {
            ele.taskSpeed = Math.floor(ele.taskFinished / ele.taskCount * 100) + "%"
          } else {
            ele.taskSpeed = "0%";
          }
          ele.taskLeaveHours = ele.tasksDays * 8 - ele.tasksUsedays;
          ele.taskLeaveDays = Math.floor(ele.taskLeaveHours / 8);
        })
      })
    },
    /** 查询日报列表 */
    getList() {
      this.queryParams.userId = this.userInfo.userId;
      this.loading = true;
      listDayreport(this.queryParams).then(response => {
        this.dayreportList = response.rows;
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
        dayrId: null,
        userId: null,
        workType: null,
        workHours: null,
        workDate: null,
        status: null,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.ids = selection.map(item => item.dayrId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.taskChooseOpen = true;
      // this.title = "添加日报";
    },
    handleUpdate(row) {
      this.reset();
      const dayrId = row.dayrId || this.ids
      getDayreport(dayrId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改日报";
      });
    },
    /** 提交按钮 */
    submitForm() {
      let _this = this;
      let hourscount = 0;
      this.detailTask.forEach(ele => {
        hourscount += ele.workHours || 0
      })
      if (hourscount == 0) {
        this.$modal.msgWarning("实际工时不能为0");
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.detailTask.forEach(ele => {
            if (ele.workHours) {
              let param = {};
              param.proId = this.currentProId;
              param.tasksId = ele.tasksId;
              param.workHours = ele.workHours;
              param.workSpeed = ele.taskSpeed;
              param.userId = this.userInfo.userId;
              param.content = this.form.content;
              param.workDate = this.form.workDate;
              param.taskId = ele.taskId;
              addDayHour(param).then(res => {
                _this.addToReport(hourscount);
                _this.open = false;
                _this.$modal.msgSuccess("填报成功");
              }).catch(err => {
                _this.$modal.msgWarning("填写日报出错，请重试");
                _this.open = false;
                _this.reset();
              })
            }

          })
        }
      })
    },
    addToReport(hours) {
      this.form.userId = this.userInfo.userId;
      this.form.workHours = hours;
      if (this.form.dayrId != null) {
        updateDayreport(this.form).then(response => {
          this.getList();
          this.reset();
        });
      } else {
        addDayreport(this.form).then(response => {
          this.getList();
          this.reset();
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const dayrIds = row.dayrId || this.ids;
      this.$modal.confirm('是否确认删除日报编号为"' + dayrIds + '"的数据项？').then(function () {
        return delDayreport(dayrIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/dayreport/export', {
        ...this.queryParams
      }, `dayreport_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
  