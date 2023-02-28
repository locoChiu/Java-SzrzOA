package com.ruoyi.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 周报填写对象 oa_week_report
 * 
 * @author azim
 * @date 2023-02-21
 */
public class OaWeekReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 周报id */
    private Long weekrId;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 填报日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "填报日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date writeDate;

    /** 本周工作内容 */
    @Excel(name = "本周工作内容")
    private String weekContent;

    /** 下周工作计划 */
    @Excel(name = "下周工作计划")
    private String nextweekPlan;

    /** 目前存在问题 */
    @Excel(name = "目前存在问题")
    private String problems;

    private String userName;

    private String weekProname;


    private String weekPercent;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWeekProname() {
        return weekProname;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setWeekProname(String weekProname) {
        this.weekProname = weekProname;
    }

    public String getWeekPercent() {
        return weekPercent;
    }

    public void setWeekPercent(String weekPercent) {
        this.weekPercent = weekPercent;
    }

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setWeekrId(Long weekrId)
    {
        this.weekrId = weekrId;
    }

    public Long getWeekrId() 
    {
        return weekrId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setWriteDate(Date writeDate) 
    {
        this.writeDate = writeDate;
    }

    public Date getWriteDate() 
    {
        return writeDate;
    }
    public void setWeekContent(String weekContent) 
    {
        this.weekContent = weekContent;
    }

    public String getWeekContent() 
    {
        return weekContent;
    }
    public void setNextweekPlan(String nextweekPlan) 
    {
        this.nextweekPlan = nextweekPlan;
    }

    public String getNextweekPlan() 
    {
        return nextweekPlan;
    }
    public void setProblems(String problems) 
    {
        this.problems = problems;
    }

    public String getProblems() 
    {
        return problems;
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
            .append("weekrId", getWeekrId())
            .append("userId", getUserId())
            .append("writeDate", getWriteDate())
            .append("weekContent", getWeekContent())
            .append("nextweekPlan", getNextweekPlan())
            .append("problems", getProblems())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
