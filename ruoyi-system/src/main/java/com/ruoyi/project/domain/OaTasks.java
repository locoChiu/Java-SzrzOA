package com.ruoyi.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务包对象 oa_tasks
 * 
 * @author azim
 * @date 2023-02-20
 */
public class OaTasks extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务包ID */
    private Long tasksId;

    /** 任务包名称 */
    @Excel(name = "任务包名称")
    private String tasksName;

    /** 项目ID */
    @Excel(name = "项目ID")
    private Long proId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String proName;

    /** 项目经理ID */
    @Excel(name = "项目经理ID")
    private Long proUserId;

    /** 项目经理名称 */
    @Excel(name = "项目经理名称")
    private String proUserName;

    /** 负责人ID */
    @Excel(name = "负责人ID")
    private Long userId;

    /** 负责人名称 */
    @Excel(name = "负责人名称")
    private String userName;

    /** 截止时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "截止时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deadline;

    /** 任务数量 */
    @Excel(name = "任务数量")
    private Integer taskCount;

    /** 任务完成数量 */
    @Excel(name = "任务完成数量")
    private Integer taskFinished;

    /** 预计工时（天） */
    @Excel(name = "预计工时", readConverterExp = "天=")
    private Integer tasksDays;

    /** 已用工时(天) */
    @Excel(name = "已用工时(天)")
    private Integer tasksUsedays;

    /** 任务包状态（0 未开始 1 进行中 2 暂停 3关闭） */
    @Excel(name = "任务包状态", readConverterExp = "0=,未=开始,1=,进=行中,2=,暂=停,3=关闭")
    private Integer tasksStatus;

    /** 任务包优先级(0 轻微 1 正常 2紧急 3特急) */
    @Excel(name = "任务包优先级(0 轻微 1 正常 2紧急 3特急)")
    private Integer tasksNum;

    /** 状态（0正常 1暂停） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=暂停")
    private String status;

    public void setTasksId(Long tasksId) 
    {
        this.tasksId = tasksId;
    }

    public Long getTasksId() 
    {
        return tasksId;
    }
    public void setTasksName(String tasksName) 
    {
        this.tasksName = tasksName;
    }

    public String getTasksName() 
    {
        return tasksName;
    }
    public void setProId(Long proId) 
    {
        this.proId = proId;
    }

    public Long getProId() 
    {
        return proId;
    }
    public void setProName(String proName) 
    {
        this.proName = proName;
    }

    public String getProName() 
    {
        return proName;
    }
    public void setProUserId(Long proUserId) 
    {
        this.proUserId = proUserId;
    }

    public Long getProUserId() 
    {
        return proUserId;
    }
    public void setProUserName(String proUserName) 
    {
        this.proUserName = proUserName;
    }

    public String getProUserName() 
    {
        return proUserName;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setDeadline(Date deadline) 
    {
        this.deadline = deadline;
    }

    public Date getDeadline() 
    {
        return deadline;
    }
    public void setTaskCount(Integer taskCount) 
    {
        this.taskCount = taskCount;
    }

    public Integer getTaskCount() 
    {
        return taskCount;
    }
    public void setTaskFinished(Integer taskFinished) 
    {
        this.taskFinished = taskFinished;
    }

    public Integer getTaskFinished() 
    {
        return taskFinished;
    }
    public void setTasksDays(Integer tasksDays) 
    {
        this.tasksDays = tasksDays;
    }

    public Integer getTasksDays() 
    {
        return tasksDays;
    }
    public void setTasksUsedays(Integer tasksUsedays) 
    {
        this.tasksUsedays = tasksUsedays;
    }

    public Integer getTasksUsedays() 
    {
        return tasksUsedays;
    }
    public void setTasksStatus(Integer tasksStatus) 
    {
        this.tasksStatus = tasksStatus;
    }

    public Integer getTasksStatus() 
    {
        return tasksStatus;
    }
    public void setTasksNum(Integer tasksNum) 
    {
        this.tasksNum = tasksNum;
    }

    public Integer getTasksNum() 
    {
        return tasksNum;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("tasksId", getTasksId())
            .append("tasksName", getTasksName())
            .append("proId", getProId())
            .append("proName", getProName())
            .append("proUserId", getProUserId())
            .append("proUserName", getProUserName())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("deadline", getDeadline())
            .append("taskCount", getTaskCount())
            .append("taskFinished", getTaskFinished())
            .append("tasksDays", getTasksDays())
            .append("tasksUsedays", getTasksUsedays())
            .append("tasksStatus", getTasksStatus())
            .append("tasksNum", getTasksNum())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
