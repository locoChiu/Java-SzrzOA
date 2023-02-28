<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="填报日期" prop="writeDate">
        <el-date-picker clearable v-model="queryParams.writeDate" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择填报日期">
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
          v-hasPermi="['project:report:add']">新增</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['project:report:edit']">修改</el-button>
      </el-col> -->
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['project:report:remove']">删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="reportList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="填报日期" align="center" prop="writeDate" width="300">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.writeDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <!-- <el-table-column label="本周工作内容" align="center" prop="weekContent" />
      <el-table-column label="下周工作计划" align="center" prop="nextweekPlan" />
      <el-table-column label="目前存在问题" align="center" prop="problems" /> -->
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <!-- <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['project:report:edit']">修改</el-button> -->
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleSee(scope.row)"
            v-hasPermi="['project:report:query']">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

      <el-dialog :title="title" :visible.sync="opensee" width="800px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
          <el-form-item label="填报日期" prop="writeDate">
            <el-date-picker clearable v-model="form.writeDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择填报日期">
            </el-date-picker>
          </el-form-item>
  
          <el-form-item label="本周工作内容">
            <el-table :data="contentTable" style="width: 100%">
  
              <!-- <el-table-column label="项目" align="center" width="170">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.proName" clearable></el-input>
                </template>
              </el-table-column> -->
  
              <el-table-column prop="workContent" label="工作内容" align="center" width="600">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.content" clearable></el-input>
                </template>
              </el-table-column>
  
              <!-- <el-table-column prop="percent" align="center" label="工作占比">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.percent" clearable></el-input>
                </template>
              </el-table-column> -->
  
              
  
            </el-table>
  
          </el-form-item>
  
          <el-form-item label="下周工作计划">
            <el-table :data="planTable" style="width: 100%">
              <el-table-column prop="workContent" label="工作计划" align="center" width="600">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.content" clearable></el-input>
                </template>
              </el-table-column>
      
  
            </el-table>
          </el-form-item>
  
  
          <el-form-item label="目前存在问题">
            <el-table :data="problemTable" style="width: 100%">
              <el-table-column prop="workContent" label="存在问题" align="center" width="600">
                <template slot-scope="scope">
                  <el-input v-model="scope.row.content" clearable></el-input>
                </template>
              </el-table-column>
    
  
            </el-table>
          </el-form-item>
  
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

    <!-- 添加或修改周报填写对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="填报日期" prop="writeDate">
          <el-date-picker clearable v-model="form.writeDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择填报日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="本周工作内容">
          <el-table :data="contentTable" style="width: 100%">

            <!-- <el-table-column label="项目" align="center" width="170">
              <template slot-scope="scope">
                <el-input v-model="scope.row.proName" clearable></el-input>
              </template>
            </el-table-column> -->

            <el-table-column prop="workContent" label="工作内容" align="center" width="650">
              <template slot-scope="scope">
                <el-input v-model="scope.row.content" clearable @keyup.enter.native="handleContentAdd" style="width:100%"></el-input>
              </template>
            </el-table-column>
            <!-- <el-table-column align="right"  width="50">
              <template slot="header" slot-scope="scope">
                <el-button size="mini" @click="handleContentAdd">+</el-button>
              </template>
            </el-table-column> -->

          </el-table>

        </el-form-item>

        <el-form-item label="下周工作计划">
          <el-table :data="planTable" style="width: 100%">
            <el-table-column prop="workContent" label="工作计划" align="center"  width="650">
              <template slot-scope="scope">
                <el-input v-model="scope.row.content" @keyup.enter.native="handlePlanAdd" clearable></el-input>
              </template>
            </el-table-column>
            <!-- <el-table-column align="right" width="50">
              <template slot="header" slot-scope="scope">
                <el-button size="mini" @click="handlePlanAdd">+</el-button>
              </template>
            </el-table-column> -->
          </el-table>
        </el-form-item>


        <el-form-item label="目前存在问题">
          <el-table :data="problemTable" style="width: 100%">
            <el-table-column prop="workContent" label="存在问题" align="center"  width="650">
              <template slot-scope="scope">
                <el-input v-model="scope.row.content"  @keyup.enter.native="handleProblemAdd"  clearable></el-input>
              </template>
            </el-table-column>
            <!-- <el-table-column align="right" width="50">
              <template slot="header" slot-scope="scope">
                <el-button size="mini" @click="handleProblemAdd">+</el-button>
              </template>
            </el-table-column> -->
          </el-table>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
  
<script>
import { listReport, getReport, delReport, addReport, updateReport } from "@/api/project/report";
import { getInfo } from '@/api/login'

export default {
  name: "Report",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      opensee:false,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 用户信息
      userInfo: {},
      // 总条数
      total: 0,
      // 周报填写表格数据
      reportList: [],
      // 工作内容表格
      contentTable: [{
        proName: undefined,
        content: undefined,
        percent: undefined
      }],
      // 存在问题表格
      problemTable: [{
        content: undefined
      }],
      // 下周计划表格
      planTable: [{
        content: undefined
      }],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        writeDate: null,
        weekContent: null,
        nextweekPlan: null,
        problems: null,
      },
      // 表单参数
      form: {

      },
      // 表单校验
      rules: {
        writeDate: [
          { required: true, message: "填报日期不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    var _this = this;
    getInfo().then(res => {
      _this.userInfo = res.user;
      _this.getList();
    })
  },
  methods: {
    contentChange(){
      console.log(123);
    },
    handlePlanAdd() {
      this.planTable.push({});
    },
    handleContentAdd() {
      this.contentTable.push({});
    },
    handleProblemAdd() {
      this.problemTable.push({});
    },
    handleSee(row) {
        var that = this;
        this.problemTable = [];
        this.contentTable = [];
        this.planTable = [];
        this.reset();
        const weekrId = row.weekrId || this.ids
        getReport(weekrId).then(response => {

          this.form = response.data;

          if( this.form.problems != null && this.form.problems != undefined) {
            let str1 = this.form.problems.split("^");
            str1.forEach(element => {
              that.problemTable.push({content:element});
            });
          }

          if( this.form.nextweekPlan != null && this.form.nextweekPlan != undefined) {
            let str1 = this.form.nextweekPlan.split("^");
            str1.forEach(element => {
              that.planTable.push({content:element});
            });
          }

          if( this.form.weekContent != null && this.form.weekContent != undefined) {
            let str1 = this.form.weekContent.split("^");
            // let str2 = this.form.weekPercent.split("^");
            // let str3 = this.form.weekProname.split("^");
            var param = {};
            for(var i = 0;i<str1.length;i++) {
              // param.proName = str1[i];
              param.content = str1[i];
              // param.percent = str2[i]
              that.contentTable.push(param);
            }
          }
          
          this.opensee = true;
          this.title = "周报详情";
        });
      },
    /** 查询周报填写列表 */
    getList() {
      // let roles = this.userInfo.roles;
      // let flag = false;
      // roles.forEach(element => {
      //   if (element.roleKey == "manager" || element.roleKey == "assistant") {
      //     flag = true;
      //   }
      // });
      // if (!flag) {
      //   this.queryParams.userId = this.userInfo.userId;
      // }
      this.queryParams.userId = this.userInfo.userId;
      this.loading = true;
      listReport(this.queryParams).then(response => {
        this.reportList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.opensee = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        weekrId: null,
        userId: null,
        writeDate: null,
        weekContent: null,
        nextweekPlan: null,
        problems: null,
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
      this.ids = selection.map(item => item.weekrId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.planTable = [{}];
      this.contentTable = [{}];
      this.problemTable = [{}];
      this.open = true;
      this.title = "周报填写";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const weekrId = row.weekrId || this.ids
      getReport(weekrId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改周报填写";
      });
    },
    /** 提交按钮 */
    submitForm() {
      let contentStr = "";
      // let contentPercentStr = "";
      // let contentProStr = "";
      let problemStr = "";
      let planStr = "";
      this.contentTable.forEach(element => {
        contentStr += element.content + "^";
        // contentPercentStr += element.percent + "^";
        // contentProStr += element.proName + "^";
      });
      this.problemTable.forEach(element => {
        problemStr += element.content + "^";
      });
      this.planTable.forEach(element => {
        planStr += element.content + "^";
      });
      contentStr = contentStr.slice(0, contentStr.length - 1);
      // contentPercentStr = contentPercentStr.slice(0, contentPercentStr.length - 1);
      // contentProStr = contentProStr.slice(0, contentProStr.length - 1);
      problemStr = problemStr.slice(0, problemStr.length - 1);
      planStr = planStr.slice(0, planStr.length - 1);
      this.form.weekContent = contentStr;
      this.form.problems = problemStr;
      this.form.nextweekPlan = planStr;
      // this.form.weekPercent = contentPercentStr;
      // this.form.weekProname = contentProStr;
      this.form.userId = this.userInfo.userId;
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.weekrId != null) {
            updateReport(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReport(this.form).then(response => {
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
      const weekrIds = row.weekrId || this.ids;
      this.$modal.confirm('是否确认删除周报填写编号为"' + weekrIds + '"的数据项？').then(function () {
        return delReport(weekrIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/report/export', {
        ...this.queryParams
      }, `report_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
  