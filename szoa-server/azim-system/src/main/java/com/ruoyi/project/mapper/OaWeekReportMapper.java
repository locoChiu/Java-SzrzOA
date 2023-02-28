package com.ruoyi.project.mapper;

import com.ruoyi.project.domain.OaWeekReport;

import java.util.List;

/**
 * 周报填写Mapper接口
 * 
 * @author azim
 * @date 2023-02-21
 */
public interface OaWeekReportMapper 
{
    /**
     * 查询周报填写
     * 
     * @param weekrId 周报填写主键
     * @return 周报填写
     */
    public OaWeekReport selectOaWeekReportByWeekrId(Long weekrId);

    /**
     * 查询周报填写列表
     * 
     * @param oaWeekReport 周报填写
     * @return 周报填写集合
     */
    public List<OaWeekReport> selectOaWeekReportList(OaWeekReport oaWeekReport);

    /**
     * 新增周报填写
     * 
     * @param oaWeekReport 周报填写
     * @return 结果
     */
    public int insertOaWeekReport(OaWeekReport oaWeekReport);

    /**
     * 修改周报填写
     * 
     * @param oaWeekReport 周报填写
     * @return 结果
     */
    public int updateOaWeekReport(OaWeekReport oaWeekReport);

    /**
     * 删除周报填写
     * 
     * @param weekrId 周报填写主键
     * @return 结果
     */
    public int deleteOaWeekReportByWeekrId(Long weekrId);

    /**
     * 批量删除周报填写
     * 
     * @param weekrIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOaWeekReportByWeekrIds(Long[] weekrIds);
}
