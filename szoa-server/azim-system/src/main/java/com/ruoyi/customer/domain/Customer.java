package com.ruoyi.customer.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户管理对象 oa_customer
 * 
 * @author azim
 * @date 2023-02-22
 */
public class Customer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户ID */
    private Long customerId;

    /** 客户姓名 */
    @Excel(name = "客户姓名")
    private String customerName;

    /** 客户性别 */
    @Excel(name = "客户性别")
    private Integer sex;

    /** 客户年龄 */
    @Excel(name = "客户年龄")
    private Integer age;

    @Excel(name = "介绍人")
    private String userName;

    /** 创建用户 */
    @Excel(name = "创建用户")
    private Long createUser;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phonenumber;

    /** 客户来源（0 重点客户 1 不同客户 2 非优选客户） */
    @Excel(name = "客户来源", readConverterExp = "0=,重=点客户,1=,不=同客户,2=,非=优选客户")
    private Integer customerSource;

    /** 行业 */
    @Excel(name = "行业")
    private Integer industry;

    /** 客户级别 */
    @Excel(name = "客户级别")
    private Integer level;

    /** 所在省 */
    @Excel(name = "所在省")
    private String province;

    /** 所在市 */
    @Excel(name = "所在市")
    private String city;

    /** 所在区 */
    @Excel(name = "所在区")
    private String region;

    /** 所在省 */
    @Excel(name = "工作所在省")
    private String workProvince;

    /** 所在市 */
    @Excel(name = "工作所在市")
    private String workCity;

    /** 所在区 */
    @Excel(name = "工作所在区")
    private String workRegion;

    /** 办公地址 */
    @Excel(name = "办公地址")
    private String workLocation;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String detailLocation;

    /** 客户状态（0正常 1停用） */
    @Excel(name = "客户状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getWorkProvince() {
        return workProvince;
    }

    public void setWorkProvince(String workProvince) {
        this.workProvince = workProvince;
    }

    public String getWorkCity() {
        return workCity;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getWorkRegion() {
        return workRegion;
    }

    public void setWorkRegion(String workRegion) {
        this.workRegion = workRegion;
    }

    public void setCustomerId(Long customerId)
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }
    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }
    public void setSex(Integer sex) 
    {
        this.sex = sex;
    }

    public Integer getSex() 
    {
        return sex;
    }
    public void setAge(Integer age) 
    {
        this.age = age;
    }

    public Integer getAge() 
    {
        return age;
    }
    public void setCreateUser(Long createUser) 
    {
        this.createUser = createUser;
    }

    public Long getCreateUser() 
    {
        return createUser;
    }
    public void setPhonenumber(String phonenumber) 
    {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber() 
    {
        return phonenumber;
    }
    public void setCustomerSource(Integer customerSource) 
    {
        this.customerSource = customerSource;
    }

    public Integer getCustomerSource() 
    {
        return customerSource;
    }
    public void setIndustry(Integer industry) 
    {
        this.industry = industry;
    }

    public Integer getIndustry() 
    {
        return industry;
    }
    public void setLevel(Integer level) 
    {
        this.level = level;
    }

    public Integer getLevel() 
    {
        return level;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }
    public void setWorkLocation(String workLocation) 
    {
        this.workLocation = workLocation;
    }

    public String getWorkLocation() 
    {
        return workLocation;
    }
    public void setDetailLocation(String detailLocation) 
    {
        this.detailLocation = detailLocation;
    }

    public String getDetailLocation() 
    {
        return detailLocation;
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
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("sex", getSex())
            .append("age", getAge())
            .append("createUser", getCreateUser())
            .append("phonenumber", getPhonenumber())
            .append("customerSource", getCustomerSource())
            .append("industry", getIndustry())
            .append("level", getLevel())
            .append("province", getProvince())
            .append("city", getCity())
            .append("region", getRegion())
            .append("workLocation", getWorkLocation())
            .append("detailLocation", getDetailLocation())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
