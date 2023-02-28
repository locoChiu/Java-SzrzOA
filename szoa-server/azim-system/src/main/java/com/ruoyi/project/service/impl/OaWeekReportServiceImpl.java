package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.domain.OaWeekReport;
import com.ruoyi.project.mapper.OaWeekReportMapper;
import com.ruoyi.project.service.IOaWeekReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 周报填写Service业务层处理
 * 
 * @author azim
 * @date 2023-02-21
 */
@Service
public class OaWeekReportServiceImpl implements IOaWeekReportService
{
    @Autowired
    private OaWeekReportMapper oaWeekReportMapper;

    /**
     * 查询周报填写
     * 
     * @param weekrId 周报填写主键
     * @return 周报填写
     */
    @Override
    public OaWeekReport selectOaWeekReportByWeekrId(Long weekrId)
    {
        return oaWeekReportMapper.selectOaWeekReportByWeekrId(weekrId);
    }

    /**
     * 查询周报填写列表
     * 
     * @param oaWeekReport 周报填写
     * @return 周报填写
     */
    @Override
    public List<OaWeekReport> selectOaWeekReportList(OaWeekReport oaWeekReport)
    {
        return oaWeekReportMapper.selectOaWeekReportList(oaWeekReport);
    }

    /**
     * 新增周报填写
     * 
     * @param oaWeekReport 周报填写
     * @return 结果
     */
    @Override
    public int insertOaWeekReport(OaWeekReport oaWeekReport)
    {
        oaWeekReport.setCreateTime(DateUtils.getNowDate());
        return oaWeekReportMapper.insertOaWeekReport(oaWeekReport);
    }

    /**
     * 修改周报填写
     * 
     * @param oaWeekReport 周报填写
     * @return 结果
     */
    @Override
    public int updateOaWeekReport(OaWeekReport oaWeekReport)
    {
        oaWeekReport.setUpdateTime(DateUtils.getNowDate());
        return oaWeekReportMapper.updateOaWeekReport(oaWeekReport);
    }

    /**
     * 批量删除周报填写
     * 
     * @param weekrIds 需要删除的周报填写主键
     * @return 结果
     */
    @Override
    public int deleteOaWeekReportByWeekrIds(Long[] weekrIds)
    {
        return oaWeekReportMapper.deleteOaWeekReportByWeekrIds(weekrIds);
    }

    /**
     * 删除周报填写信息
     * 
     * @param weekrId 周报填写主键
     * @return 结果
     */
    @Override
    public int deleteOaWeekReportByWeekrId(Long weekrId)
    {
        return oaWeekReportMapper.deleteOaWeekReportByWeekrId(weekrId);
    }
}
