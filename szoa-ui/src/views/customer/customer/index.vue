<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="客户姓名" prop="customerName">
                <el-input v-model="queryParams.customerName" placeholder="请输入客户姓名" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                    v-hasPermi="['customer:customer:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
                    v-hasPermi="['customer:customer:edit']">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
                    v-hasPermi="['customer:customer:remove']">删除</el-button>
            </el-col>
            <!-- <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['customer:customer:export']"
          >导出</el-button>
        </el-col> -->
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="customerList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="客户姓名" align="center" prop="customerName" />
            <el-table-column label="性别" align="center" prop="sex">
                <template slot-scope="scope">
                    <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.sex" />
                </template>
            </el-table-column>
            <el-table-column label="年龄" align="center" prop="age" />
            <el-table-column label="介绍人" align="center" prop="userName" />
            <el-table-column label="手机号" align="center" prop="phonenumber" width="100"/>
            <el-table-column label="客户来源" align="center" prop="customerSource">
                <template slot-scope="scope">
                    <dict-tag :options="dict.type.oa_customer_source" :value="scope.row.customerSource" />
                </template>
            </el-table-column>
            <el-table-column label="行业" align="center" prop="industry" >
                <template slot-scope="scope">
                    <dict-tag :options="dict.type.oa_customer_industry" :value="scope.row.industry" />
                </template>
            </el-table-column>
            <el-table-column label="客户级别" align="center" prop="level">
                <template slot-scope="scope">
                    <dict-tag :options="dict.type.oa_customer_level" :value="scope.row.level" />
                </template>
            </el-table-column>
            <el-table-column label="地址" align="center" prop="usersLocation" :show-overflow-tooltip="true" />
            <el-table-column label="办公地址" align="center" prop="worksLocation" :show-overflow-tooltip="true"  />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                        v-hasPermi="['customer:customer:edit']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                        v-hasPermi="['customer:customer:remove']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
            @pagination="getList" />

        <!-- 添加或修改客户管理对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="姓名" prop="customerName">
                    <el-input v-model="form.customerName" placeholder="请输入客户姓名" />
                </el-form-item>
                <el-form-item label="性别" prop="sex">
                    <el-select v-model="form.sex" placeholder="请选择客户性别">
                        <el-option v-for="dict in dict.type.sys_user_sex" :key="dict.value" :label="dict.label"
                            :value="parseInt(dict.value)"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="年龄" prop="age">
                    <el-input v-model="form.age" placeholder="请输入客户年龄" />
                </el-form-item>
                <el-form-item label="手机号" prop="phonenumber">
                    <el-input v-model="form.phonenumber" placeholder="请输入手机号" />
                </el-form-item>
                <el-form-item label="来源" prop="customerSource">
                    <el-select v-model="form.customerSource" placeholder="请选择客户来源">
                        <el-option v-for="dict in dict.type.oa_customer_source" :key="dict.value" :label="dict.label"
                            :value="parseInt(dict.value)"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="介绍人" prop="createUser">
                    <el-select v-model="form.createUser"  placeholder="请选择介绍人">
                            <el-option
                            v-for="item in allUserList"
                            :key="item.userId"
                            :label="item.nickName"
                            :value="item.userId"
                            ></el-option>
                        </el-select>
                    </el-form-item>

                <el-form-item label="行业" prop="industry">
                    <el-select v-model="form.industry" placeholder="请选择客户行业">
                        <el-option v-for="dict in dict.type.oa_customer_industry" :key="dict.value" :label="dict.label"
                            :value="parseInt(dict.value)"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="级别" prop="level">
                    <el-select v-model="form.level" placeholder="请选择客户级别">
                        <el-option v-for="dict in dict.type.oa_customer_level" :key="dict.value" :label="dict.label"
                            :value="parseInt(dict.value)"></el-option>
                    </el-select>
                </el-form-item>

                <el-form-item label="所属地区">
                    <cascader :selProvince="form.province" :selCity="form.city" :selCounty="form.region"
                        @changeName="lockValue" @changeNames="lock" @changeRegion="Region"></cascader>
                </el-form-item>
                <el-form-item label="详细地址" prop="detailLocation">
                    <el-input v-model="form.detailLocation" placeholder="请输入详细地址" />
                </el-form-item>

                <el-form-item label="办公地址" prop="workLocation">
                    <el-input placeholder="请选择办公地址" v-model="addressInfo.address" disabled>
                    <template slot="prepend">{{addressInfo.province}}{{addressInfo.city}}{{addressInfo.district}}</template>
                    <el-button slot="append" icon="el-icon-map" @click="showMap" type="primary">选择地址</el-button>
                    </el-input>
                </el-form-item>

            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>

        <!-- 百度地图位置选择 -->
        <BMapAddressSelect ref="bmapAddressSelect" @confirmMapAddress="confirmMapAddress"></BMapAddressSelect>

    </div>
</template>
  
<script>
import { listCustomer, getCustomer, delCustomer, addCustomer, updateCustomer } from "@/api/customer/customer";
import { listUser, deptTreeSelect } from "@/api/system/user";
import {  getInfo } from '@/api/login'
import cascader from "../components/address.vue";
import BMapAddressSelect from "../components/location.vue";

export default {
    name: "Customer",
    dicts: ['oa_customer_level', 'oa_customer_source','oa_customer_industry','sys_user_sex'],
    components: {
        cascader,
        BMapAddressSelect
    },
    data() {
        return {
            // 介绍人列表
            allUserList:[],
            // 位置信息
            addressInfo: {},
            // 遮罩层
            loading: true,
            // 选中数组
            ids: [],
            // 用户信息
            userInfo:{},
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 显示搜索条件
            showSearch: true,
            // 总条数
            total: 0,
            // 客户管理表格数据
            customerList: [],
            // 弹出层标题
            title: "",
            // 是否显示弹出层
            open: false,
            // 查询参数
            queryParams: {
                pageNum: 1,
                pageSize: 10,
                customerName: null,
                sex: null,
                age: null,
                createUser: null,
                phonenumber: null,
                customerSource: null,
                industry: null,
                level: null,
                province: null,
                city: null,
                region: null,
                workLocation: null,
                detailLocation: null,
                status: null,
            },
            // 表单参数
            form: {},
            // 表单校验
            rules: {
                customerName: [
                    { required: true, message: "客户姓名不能为空", trigger: "blur" }
                ],
                sex: [
                    { required: true, message: "客户性别不能为空", trigger: "blur" }
                ],
                age: [
                    { required: true, message: "客户年龄不能为空", trigger: "blur" }
                ],
                phonenumber: [
                    { required: true, message: "客户手机号不能为空", trigger: "blur" }
                ],
                industry: [
                    { required: true, message: "客户行业龄不能为空", trigger: "blur" }
                ],
                createUser: [
                    { required: true, message: "介绍人不能为空", trigger: "blur" }
                ],
            }
        };
    },
    created() {
        let _this = this;
        getInfo().then(res=>{
            _this.userInfo = res.user;
            _this.getList();
        })
       
    },
    methods: {
        /** 显示地图 */
        showMap() {
            this.$refs.bmapAddressSelect.show();
        },

        /** 确认地图地址 */
        confirmMapAddress(addressInfo) {
            console.log(addressInfo);
            this.addressInfo = addressInfo;
        },
        lockValue(province) {
            this.form.province = province
        },
        lock(city) {
            this.form.city = city
        },
        Region(region) {
            this.form.region = region
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
        },
        /** 查询客户管理列表 */
        getList() {
            let roles = this.userInfo.roles;
            let flag = false;
            roles.forEach(element => {
                if(element.roleKey == "manager" || element.roleKey == "assistant") {
                    flag = true;
                }
            });
            if(!flag) {
                this.queryParams.createUser = this.userInfo.userId;
            }
            this.loading = true;
            listCustomer(this.queryParams).then(response => {
                this.customerList = response.rows;
                this.customerList.forEach(element => {
                    element.usersLocation = element.province + element.city + element.region + element.detailLocation;
                    element.worksLocation = element.workProvince + element.workCity + element.workRegion + element.workLocation;
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
                customerId: null,
                customerName: null,
                sex: null,
                age: null,
                createUser: null,
                phonenumber: null,
                customerSource: null,
                industry: null,
                level: null,
                province: null,
                city: null,
                region: null,
                workLocation: null,
                detailLocation: null,
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
            this.ids = selection.map(item => item.customerId)
            this.single = selection.length !== 1
            this.multiple = !selection.length
        },
        /** 新增按钮操作 */
        handleAdd() {
            this.reset();
            this.getPersons();
            this.open = true;
            this.title = "添加客户管理";
        },
        /** 修改按钮操作 */
        handleUpdate(row) {
            this.reset();
            this.getPersons();
            const customerId = row.customerId || this.ids
            getCustomer(customerId).then(response => {
                this.form = response.data;
                this.addressInfo.address= this.form.workLocation 
                this.addressInfo.province =this.form.workProvince
                this.addressInfo.city = this.form.workCity 
                this.addressInfo.district =this.form.workRegion 
                this.open = true;
                this.title = "修改客户管理";
            });
        },
        /** 提交按钮 */
        submitForm() {
            this.form.workLocation = this.addressInfo.address;
            this.form.workProvince = this.addressInfo.province;
            this.form.workCity = this.addressInfo.city;
            this.form.workRegion = this.addressInfo.district;
            if(this.form.createUser == null) {
                this.form.createUser = this.userInfo.userId;
            }
            this.$refs["form"].validate(valid => {
                if (valid) {
                    if (this.form.customerId != null) {
                        updateCustomer(this.form).then(response => {
                            this.$modal.msgSuccess("修改成功");
                            this.open = false;
                            this.getList();
                        });
                    } else {
                        addCustomer(this.form).then(response => {
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
            const customerIds = row.customerId || this.ids;
            this.$modal.confirm('是否确认删除客户管理编号为"' + customerIds + '"的数据项？').then(function () {
                return delCustomer(customerIds);
            }).then(() => {
                this.getList();
                this.$modal.msgSuccess("删除成功");
            }).catch(() => { });
        },
        /** 导出按钮操作 */
        handleExport() {
            this.download('customer/customer/export', {
                ...this.queryParams
            }, `customer_${new Date().getTime()}.xlsx`)
        }
    }
};
</script>
  