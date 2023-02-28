package com.ruoyi.project.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 日报对象 oa_day_report
 * 
 * @author azim
 * @date 2023-02-21
 */
public class OaDayReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 日报ID */
    private Long dayrId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 工时标签 */
    @Excel(name = "工时标签")
    private String workType;

    private String content;

    /** 今日工时 */
    @Excel(name = "今日工时")
    private Integer workHours;

    /** 填报日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "填报日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workDate;

    /** 部门状态（0正常 1停用） */
    @Excel(name = "部门状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setDayrId(Long dayrId) 
    {
        this.dayrId = dayrId;
    }

    public Long getDayrId() 
    {
        return dayrId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setWorkType(String workType) 
    {
        this.workType = workType;
    }

    public String getWorkType() 
    {
        return workType;
    }
    public void setWorkHours(Integer workHours) 
    {
        this.workHours = workHours;
    }

    public Integer getWorkHours() 
    {
        return workHours;
    }
    public void setWorkDate(Date workDate) 
    {
        this.workDate = workDate;
    }

    public Date getWorkDate() 
    {
        return workDate;
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("dayrId", getDayrId())
            .append("userId", getUserId())
            .append("workType", getWorkType())
            .append("workHours", getWorkHours())
            .append("workDate", getWorkDate())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
