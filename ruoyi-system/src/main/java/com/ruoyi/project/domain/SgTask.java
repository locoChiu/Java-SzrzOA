package com.ruoyi.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 任务对象 oa_task
 * 
 * @author azim
 * @date 2023-02-21
 */
public class SgTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Long taskId;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务包ID */
    @Excel(name = "任务包ID")
    private Long tasksId;

    /** 任务包名称 */
    @Excel(name = "任务包名称")
    private String tasksName;

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

    /** 任务目标 */
    @Excel(name = "任务目标")
    private String taskGoal;

    /** 任务进度 */
    @Excel(name = "任务进度")
    private Integer taskFinished;

    /** 预计工时（h） */
    @Excel(name = "预计工时", readConverterExp = "h=")
    private Integer tasksHours;

    /** 已用工时(h) */
    @Excel(name = "已用工时(h)")
    private Integer tasksUsehours;

    /** 任务包状态（0 未开始 1 进行中 2 暂停 3关闭） */
    @Excel(name = "任务包状态", readConverterExp = "0=,未=开始,1=,进=行中,2=,暂=停,3=关闭")
    private Integer taskStatus;

    /** 任务包优先级(0 轻微 1 正常 2紧急 3特急) */
    @Excel(name = "任务包优先级(0 轻微 1 正常 2紧急 3特急)")
    private Integer taskNum;

    /** 任务指派状态 （0 待指派 1 已指派） */
    @Excel(name = "任务指派状态 ", readConverterExp = "0=,待=指派,1=,已=指派")
    private Integer taskFlag;

    /** 状态（0正常 1暂停） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=暂停")
    private String status;

    private Integer taskSpeed;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getTaskSpeed() {
        return taskSpeed;
    }

    public void setTaskSpeed(Integer taskSpeed) {
        this.taskSpeed = taskSpeed;
    }

    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }

    public Long getTaskId() 
    {
        return taskId;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
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
    public void setTaskGoal(String taskGoal) 
    {
        this.taskGoal = taskGoal;
    }

    public String getTaskGoal() 
    {
        return taskGoal;
    }
    public void setTaskFinished(Integer taskFinished) 
    {
        this.taskFinished = taskFinished;
    }

    public Integer getTaskFinished() 
    {
        return taskFinished;
    }
    public void setTasksHours(Integer tasksHours) 
    {
        this.tasksHours = tasksHours;
    }

    public Integer getTasksHours() 
    {
        return tasksHours;
    }
    public void setTasksUsehours(Integer tasksUsehours) 
    {
        this.tasksUsehours = tasksUsehours;
    }

    public Integer getTasksUsehours() 
    {
        return tasksUsehours;
    }
    public void setTaskStatus(Integer taskStatus) 
    {
        this.taskStatus = taskStatus;
    }

    public Integer getTaskStatus() 
    {
        return taskStatus;
    }
    public void setTaskNum(Integer taskNum) 
    {
        this.taskNum = taskNum;
    }

    public Integer getTaskNum() 
    {
        return taskNum;
    }
    public void setTaskFlag(Integer taskFlag) 
    {
        this.taskFlag = taskFlag;
    }

    public Integer getTaskFlag() 
    {
        return taskFlag;
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
            .append("taskId", getTaskId())
            .append("taskName", getTaskName())
            .append("tasksId", getTasksId())
            .append("tasksName", getTasksName())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("deadline", getDeadline())
            .append("taskGoal", getTaskGoal())
            .append("taskFinished", getTaskFinished())
            .append("tasksHours", getTasksHours())
            .append("tasksUsehours", getTasksUsehours())
            .append("taskStatus", getTaskStatus())
            .append("taskNum", getTaskNum())
            .append("taskFlag", getTaskFlag())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
