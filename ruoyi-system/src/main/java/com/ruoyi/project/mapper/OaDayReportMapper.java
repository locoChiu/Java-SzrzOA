package com.ruoyi.project.mapper;

import com.ruoyi.project.domain.OaDayHours;
import com.ruoyi.project.domain.OaDayReport;

import java.util.List;

/**
 * 日报Mapper接口
 * 
 * @author azim
 * @date 2023-02-21
 */
public interface OaDayReportMapper 
{
    /**
     * 查询日报
     * 
     * @param dayrId 日报主键
     * @return 日报
     */
    public OaDayReport selectOaDayReportByDayrId(Long dayrId);

    /**
     * 查询日报列表
     * 
     * @param oaDayReport 日报
     * @return 日报集合
     */
    public List<OaDayReport> selectOaDayReportList(OaDayReport oaDayReport);

    /**
     * 新增日报
     * 
     * @param oaDayReport 日报
     * @return 结果
     */
    public int insertOaDayReport(OaDayReport oaDayReport);

    /**
     * 修改日报
     * 
     * @param oaDayReport 日报
     * @return 结果
     */
    public int updateOaDayReport(OaDayReport oaDayReport);

    /**
     * 删除日报
     * 
     * @param dayrId 日报主键
     * @return 结果
     */
    public int deleteOaDayReportByDayrId(Long dayrId);

    /**
     * 批量删除日报
     * 
     * @param dayrIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOaDayReportByDayrIds(Long[] dayrIds);

    int insertDayHours(OaDayHours data);
}
