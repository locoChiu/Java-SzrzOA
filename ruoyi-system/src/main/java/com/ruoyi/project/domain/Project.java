package com.ruoyi.project.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目集对象 oa_project
 * 
 * @author azim
 * @date 2023-02-17
 */
public class Project extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 项目id */
    private Long proId;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String proName;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Integer orderNum;

    /** 项目经理 */
    @Excel(name = "项目经理")
    private String userId;

    /** 项目经理名字 */
    @Excel(name = "项目经理名字")
    private String userName;

    /** 项目简介 */
    @Excel(name = "项目简介")
    private String proIntro;

    /** 项目描述 */
    @Excel(name = "项目描述")
    private String description;

    /** 项目开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "项目开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 项目截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "项目截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 项目进度 */
    @Excel(name = "项目进度")
    private Integer proSpeed;

    /** 总需求数 */
    @Excel(name = "总需求数")
    private Integer allDemand;

    /** 已完成需求数 */
    @Excel(name = "已完成需求数")
    private Integer finishDemand;


    /** 总任务数 */
    @Excel(name = "总任务数")
    private Integer allTasks;

    /** 已完成任务数 */
    @Excel(name = "已完成任务数")
    private Integer finishTasks;

    /** 合同编号 */
    @Excel(name = "合同编号")
    private String contractNo;

    /** 项目状态（0未开始 1进行中 2代表未关闭 3代表已挂起 4代表已关闭） */
    @Excel(name = "项目状态", readConverterExp = "0=未开始,1=进行中,2=代表未关闭,3=代表已挂起,4=代表已关闭")
    private String status;

    /** 项目成员 */
    @Excel(name = "项目成员")
    private List<SysUser> proPersons;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<SysUser> getProPersons() {
        return proPersons;
    }

    public void setProPersons(List<SysUser> proPersons) {
        this.proPersons = proPersons;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setProId(Long proId) 
    {
        this.proId = proId;
    }

    public Long getProId() 
    {
        return proId;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setProName(String proName) 
    {
        this.proName = proName;
    }

    public String getProName() 
    {
        return proName;
    }
    public void setOrderNum(Integer orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() 
    {
        return orderNum;
    }
    public void setUserId(String userId) 
    {
        this.userId = userId;
    }

    public String getUserId() 
    {
        return userId;
    }
    public void setProIntro(String proIntro) 
    {
        this.proIntro = proIntro;
    }

    public String getProIntro() 
    {
        return proIntro;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setProSpeed(Integer proSpeed) 
    {
        this.proSpeed = proSpeed;
    }

    public Integer getProSpeed() 
    {
        return proSpeed;
    }
    public void setAllDemand(Integer allDemand) 
    {
        this.allDemand = allDemand;
    }

    public Integer getAllDemand() 
    {
        return allDemand;
    }
    public void setFinishDemand(Integer finishDemand) 
    {
        this.finishDemand = finishDemand;
    }

    public Integer getFinishDemand() 
    {
        return finishDemand;
    }
    public void setAllTasks(Integer allTasks) 
    {
        this.allTasks = allTasks;
    }

    public Integer getAllTasks() 
    {
        return allTasks;
    }
    public void setFinishTasks(Integer finishTasks) 
    {
        this.finishTasks = finishTasks;
    }

    public Integer getFinishTasks() 
    {
        return finishTasks;
    }
    public void setContractNo(String contractNo) 
    {
        this.contractNo = contractNo;
    }

    public String getContractNo() 
    {
        return contractNo;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("proId", getProId())
            .append("deptId", getDeptId())
            .append("proName", getProName())
            .append("orderNum", getOrderNum())
            .append("userId", getUserId())
            .append("proIntro", getProIntro())
            .append("description", getDescription())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("proSpeed", getProSpeed())
            .append("allDemand", getAllDemand())
            .append("finishDemand", getFinishDemand())
            .append("allTasks", getAllTasks())
            .append("finishTasks", getFinishTasks())
            .append("contractNo", getContractNo())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
