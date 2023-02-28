<template>
  <div class="app-container">
    
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="70px">
      <el-form-item label="项目名称" prop="proName">
        <el-input v-model="queryParams.proName" placeholder="请输入项目名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="合同编号" prop="contractNo">
        <el-input v-model="queryParams.contractNo" placeholder="请输入合同编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['project:proset:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['project:proset:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['project:proset:remove']">删除</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['project:proset:export']">导出</el-button>
      </el-col> -->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="prosetList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="项目名称" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <router-link :to="'/project/proId/' + scope.row.proId" class="link-type">
            <span>{{ scope.row.proName }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="项目经理" align="center" prop="userName" />

      <el-table-column label="项目开始时间" align="center" prop="startTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="项目截止时间" align="center" prop="endTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="项目进度" align="center" >
        <template slot-scope="scope">
          <p v-if="scope.row.proSpeed > 70" style="border-radius: 10px;margin:0 auto;width:80px;font-weight:700;background-color: #02DF82;">{{scope.row.proSpeed}}%</p>
          <p v-else-if="scope.row.proSpeed > 50" style="border-radius: 10px;margin:0 auto;width:80px;font-weight:700;background-color:	#FF8000;">{{scope.row.proSpeed}}%</p>
          <p v-else style="border-radius: 10px;margin:0 auto;width:80px;font-weight:700;background-color: #FF2D2D;">{{scope.row.proSpeed}}%</p>
        </template>
      </el-table-column>

      <el-table-column label="合同编号" align="center" prop="contractNo" />
      <el-table-column label="状态" align="center" prop="status" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.oa_project_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="200px">
        <template slot-scope="scope">
          <!-- <el-button size="mini" type="text" icon="el-icon-view" @click="goDetail(scope.row)"
            v-hasPermi="['project:proset:edit']">进入</el-button> -->
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['project:proset:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['project:proset:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改项目集对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="所属部门" prop="deptId">
          <treeselect v-model="form.deptId" :options="deptOptions" placeholder="请选择归属部门"/>
        </el-form-item>
        <el-form-item label="项目名称" prop="proName">
          <el-input v-model="form.proName" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="项目经理" prop="userId">
          <el-select v-model="form.userId"  placeholder="请输入项目经理">
                <el-option
                  v-for="item in allUserList"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId"
                ></el-option>
              </el-select>
        </el-form-item>

        <el-form-item label="项目状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择项目状态">
                <el-option
                  v-for="dict in dict.type.oa_project_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
        </el-form-item>
        
        <el-form-item label="合同编号" prop="contractNo">
          <el-input v-model="form.contractNo" placeholder="请输入合同编号" />
        </el-form-item>
        <el-form-item label="项目简介" prop="proIntro">
          <el-input v-model="form.proIntro" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="项目描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="项目开始时间" prop="startTime">
          <el-date-picker clearable v-model="form.startTime" type="date" value-format="yyyy-MM-dd"
            placeholder="请选择项目开始时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="项目截止时间" prop="endTime">
          <el-date-picker clearable v-model="form.endTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择项目截止时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="项目成员">
          <el-transfer v-model="proPersons" :titles="['所有成员', '项目成员']" :data="personData"></el-transfer>
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
import { listProset, getProset, getProPerson,delProset, addProset, updateProset } from "@/api/project/proset";
import { listUser, deptTreeSelect } from "@/api/system/user";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Proset",
  components: { Treeselect },
  dicts: ['oa_project_status'],
  data() {
    return {
      // 所有成员原数据
      allUserList:[],
      // 项目成员
      proPersons:[],
      // 所有成员
      personData: [],
      // 部门树选项
      deptOptions: [],
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
      // 项目集表格数据
      prosetList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        proName: null,
        userId: null,
        proIntro: null,
        description: null,
        startTime: null,
        endTime: null,
        proSpeed: null,
        allDemand: null,
        finishDemand: null,
        allTasks: null,
        finishTasks: null,
        contractNo: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 项目经理id
      oldManagerId:undefined,
      // 表单校验
      rules: {
        deptId: [
          { required: true, message: "所属部门不能为空", trigger: "blur" }
        ],
        proName: [
          { required: true, message: "项目名称不能为空", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "项目经理不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getPersons();
  },
  methods: {
    // 前往详情页面
    goDetail(row){
      this.$router.push({ path: "/project/proId/" + row.proId });
    },
    // 获取所有成员
    getPersons() {
      const data = [];
      listUser().then(response => {
        this.allUserList = response.rows;
        response.rows.forEach(element => {
          data.push({
            key: element.userId,
            label: element.nickName,
          });
        });
      });
      this.personData = data;
    },
    /** 查询菜单下拉树结构 */
    getTreeselect() {
      var _this = this;
      deptTreeSelect().then(response => {
        _this.deptOptions = response.data;
      });
    },
    /** 查询项目集列表 */
    getList() {
      this.loading = true;
      listProset(this.queryParams).then(response => {
        this.prosetList = response.rows;
        this.total = response.total;
        this.prosetList.forEach(element => {
          if(element.finishDemand == 0) {
            element.proSpeed = 0;
          } else {
            element.proSpeed = Math.floor(element.finishDemand / element.allDemand * 100);
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
        proId: null,
        deptId: null,
        proName: null,
        orderNum: null,
        userId: null,
        proIntro: null,
        description: null,
        startTime: null,
        endTime: null,
        proSpeed: null,
        allDemand: null,
        finishDemand: null,
        allTasks: null,
        finishTasks: null,
        contractNo: null,
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
      this.ids = selection.map(item => item.proId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.getTreeselect();
      this.proPersons = [];
      this.getPersons();
      this.reset();
      this.open = true;
      this.title = "添加项目";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      var _this = this;
      const proId = row.proId || this.ids
      this.personData = []; 
      this.proPersons = [];
      getProPerson(proId).then(res=>{
        const data = [];
        res.data.otherUser.forEach(element => {
          data.push({
            key: element.userId,
            label: element.nickName,
          });
        });
        _this.personData = data;
        let proData = [];
        res.data.proUser.forEach(element => {
          proData.push(element.userId)
        });
        _this.proPersons = proData;
      })
      getProset(proId).then(response => {
        this.form = response.data;
        this.oldManagerId = this.form.userId;
        this.oldManageName = response.data.userName;
        this.form.userId = response.data.userName;
        this.open = true;
        this.title = "修改项目";
      });
    },
    /** 提交按钮 */
    submitForm() {
      if(this.form.userId == this.oldManageName) {
        this.form.userId = this.oldManagerId;
      }
      this.form.proPersons = this.proPersons;
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.proId != null) {
            updateProset(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProset(this.form).then(response => {
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
      const proIds = row.proId || this.ids;
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        return delProset(proIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('project/proset/export', {
        ...this.queryParams
      }, `proset_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
  