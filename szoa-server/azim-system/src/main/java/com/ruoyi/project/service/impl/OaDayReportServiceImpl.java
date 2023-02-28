package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.domain.OaDayHours;
import com.ruoyi.project.domain.OaDayReport;
import com.ruoyi.project.mapper.OaDayReportMapper;
import com.ruoyi.project.service.IOaDayReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * 日报Service业务层处理
 * 
 * @author azim
 * @date 2023-02-21
 */
@Service
public class OaDayReportServiceImpl implements IOaDayReportService
{
    @Autowired
    private OaDayReportMapper oaDayReportMapper;

    /**
     * 查询日报
     * 
     * @param dayrId 日报主键
     * @return 日报
     */
    @Override
    public OaDayReport selectOaDayReportByDayrId(Long dayrId)
    {
        return oaDayReportMapper.selectOaDayReportByDayrId(dayrId);
    }

    /**
     * 查询日报列表
     * 
     * @param oaDayReport 日报
     * @return 日报
     */
    @Override
    public List<OaDayReport> selectOaDayReportList(OaDayReport oaDayReport)
    {
        return oaDayReportMapper.selectOaDayReportList(oaDayReport);
    }

    /**
     * 新增日报
     * 
     * @param oaDayReport 日报
     * @return 结果
     */
    @Override
    public int insertOaDayReport(OaDayReport oaDayReport)
    {
        oaDayReport.setCreateTime(DateUtils.getNowDate());
        return oaDayReportMapper.insertOaDayReport(oaDayReport);
    }

    /**
     * 修改日报
     * 
     * @param oaDayReport 日报
     * @return 结果
     */
    @Override
    public int updateOaDayReport(OaDayReport oaDayReport)
    {
        oaDayReport.setUpdateTime(DateUtils.getNowDate());
        return oaDayReportMapper.updateOaDayReport(oaDayReport);
    }

    /**
     * 批量删除日报
     * 
     * @param dayrIds 需要删除的日报主键
     * @return 结果
     */
    @Override
    public int deleteOaDayReportByDayrIds(Long[] dayrIds)
    {
        return oaDayReportMapper.deleteOaDayReportByDayrIds(dayrIds);
    }

    /**
     * 删除日报信息
     * 
     * @param dayrId 日报主键
     * @return 结果
     */
    @Override
    public int deleteOaDayReportByDayrId(Long dayrId)
    {
        return oaDayReportMapper.deleteOaDayReportByDayrId(dayrId);
    }

    @Override
    public int insertDayHours(OaDayHours data) {
        return oaDayReportMapper.insertDayHours(data);
    }
}
