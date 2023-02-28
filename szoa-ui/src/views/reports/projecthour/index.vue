<template>
  <div class="app-container">
    <el-form :model="form" ref="form" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="项目" prop="proId">
        <el-select v-model="form.proId" placeholder="请选择项目">
          <el-option v-for="dict in selectList" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">查看</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-descriptions  title="" :column="3"  border>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-user"></i>
          项目名
        </template>
        {{proInfo.proName}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          项目经理
        </template>
        {{proInfo.userName}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          项目状态
        </template>
        <el-tag size="small">{{proInfo.statusStr}}</el-tag>
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-mobile-phone"></i>
          合同号
        </template>
        {{proInfo.contractNo}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-location-outline"></i>
          开始时间
        </template>
        {{proInfo.startTime}}
      </el-descriptions-item>

      <el-descriptions-item>
        <template slot="label">
          <i class="el-icon-tickets"></i>
          总任务包数量
        </template>
        <el-tag size="small">{{proInfo.allDemand}}</el-tag>
      </el-descriptions-item>


    </el-descriptions>


    <el-table :data="hourList" border show-summary style="width: 100%">

      <!-- <el-table-column prop="index" label="ID" width="180">
      </el-table-column> -->
      <!-- <el-table-column
        type="index">
      </el-table-column> -->

      <el-table-column prop="userName" label="成员">
      </el-table-column>

      <el-table-column prop="workHours" label="项目工时">
      </el-table-column>

      <el-table-column prop="workDays" label="项目人天">
      </el-table-column>


    </el-table>



  </div>
</template>
    
<script>
import { selectProHoursCount, getProset, listProset } from "@/api/project/proset";

export default {
  name: "Projecthour",
  data() {
    return {
      hourList:[],
      selectList: [],
      proInfo:{},
      form: {},
      showSearch: true,
      tableData: [{
        id: '12987122',
        name: '王小虎',
        amount1: '234',
        amount2: '3.2',
        amount3: 10
      }, {
        id: '12987123',
        name: '王小虎',
        amount1: '165',
        amount2: '4.43',
        amount3: 12
      }, {
        id: '12987124',
        name: '王小虎',
        amount1: '324',
        amount2: '1.9',
        amount3: 9
      }, {
        id: '12987125',
        name: '王小虎',
        amount1: '621',
        amount2: '2.2',
        amount3: 17
      }, {
        id: '12987126',
        name: '王小虎',
        amount1: '539',
        amount2: '4.1',
        amount3: 15
      }]
    };
  },
  created() {
    listProset().then(res => {
      res.rows.forEach(element => {
        let param = {};
        param.label = element.proName;
        param.value = element.proId;
        this.selectList.push(param);
      });
    })
  },
  methods: {
    /** 搜索按钮操作 */
    handleQuery() {
      if (!this.form.proId) {
        this.$modal.msgWarning("请先选择项目");
        return;
      }
      getProset(this.form.proId).then(res=>{
        this.proInfo = res.data;
        switch(this.proInfo.status) {
          case '0':
            this.proInfo.statusStr = "未开始";
            break;
          case '1':
            this.proInfo.statusStr = "进行中";
            break;
          case '2':
            this.proInfo.statusStr = "已暂停";
            break;
          case '3':
            this.proInfo.statusStr = "已结束";
            break;
          default:
            this.proInfo.statusStr = "状态异常";
        }
      }) 
      selectProHoursCount(this.form.proId).then(res => {
        this.hourList = res.data;
        console.log(res.data);
        this.hourList.forEach(ele=>{
          ele.workDays = (ele.workHours / 8 ).toFixed(1);
        })
      })
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("form");
    },
  }
}
</script>
    