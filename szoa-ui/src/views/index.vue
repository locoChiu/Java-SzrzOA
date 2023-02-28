<template>
  <div class="app-container home">
    <el-row :gutter="20">

      <h1 style="color:rgba(0, 0, 0, .7);margin-left: 40px;margin-bottom: 50px;font-size: 24px;">该模块暂未开发</h1> 

      <el-divider />

      <el-col :xs="24" :sm="24" :md="8" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span style="color:lightblue;">快捷入口</span>
          </div>
          <el-tag style="cursor: pointer;" @click="goDetail('/project/dayreport')">工时填报</el-tag>&nbsp;
          <el-tag style="cursor: pointer;" type="success" @click="goDetail('/project/tasks')">任务管理</el-tag>&nbsp;
          <el-tag style="cursor: pointer;" type="info"  @click="goDetail('/customer/customer')">客户管理</el-tag>&nbsp;
          <el-tag style="cursor: pointer;" type="warning"  @click="goDetail('/worktime/report')">周报填写</el-tag>
        </el-card>

      </el-col>

      <el-col :xs="24" :sm="24" :md="16" :lg="16">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>待办提醒</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="v3.8.5 - 2023-01-01">
              <ol>
                <li>定时任务违规的字符</li>
                <li>重置时取消部门选中</li>
                <li>新增返回警告消息提示</li>
                <li>修复代码生成图片/文件/单选时选择必填无法校验问题</li>
                <li>修复某些特性的情况用户编辑对话框中角色和部门无法修改问题</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>

    </el-row>
  </div>
</template>

<script>
import { getProset } from "@/api/project/proset";

export default {
  name: "Index",
  data() {
    return {
      baseUrl: process.env.VUE_APP_BASE_API,  //  基础路径
      fileList: [], // 附件列表
      uploadShow: false,  // 上传弹出框
      proId:undefined,  // 项目Id
      proInfo:{}, // 项目信息
    };
  },
  created(){
  },
  methods: {
    // 快捷入口
    goDetail(data) {
      this.$router.push(data);
    },
    // 获取文件名称
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1);
      } else {
        return "";
      }
    },
    // 删除文件
    handleDelete(index) {
      this.fileList.splice(index, 1);
      this.$emit("input", this.listToString(this.fileList));
    },
    // 显示附件上传弹出框
    showUpload() {
      this.uploadShow = true;
    },
  }
};
</script>

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
  font-size: 13px;
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

