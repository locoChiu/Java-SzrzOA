package com.ruoyi.web.controller.project;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.project.domain.OaWeekReport;
import com.ruoyi.project.service.IOaWeekReportService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 周报填写Controller
 * 
 * @author azim
 * @date 2023-02-21
 */
@RestController
@RequestMapping("/project/report")
public class OaWeekReportController extends BaseController
{
    @Autowired
    private IOaWeekReportService oaWeekReportService;

    /**
     * 查询周报填写列表
     */
    @PreAuthorize("@ss.hasPermi('project:report:list')")
    @GetMapping("/list")
    public TableDataInfo list(OaWeekReport oaWeekReport)
    {
        startPage();
        List<OaWeekReport> list = oaWeekReportService.selectOaWeekReportList(oaWeekReport);
        return getDataTable(list);
    }

    /**
     * 导出周报填写列表
     */
    @PreAuthorize("@ss.hasPermi('project:report:export')")
    @Log(title = "周报填写", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OaWeekReport oaWeekReport)
    {
        List<OaWeekReport> list = oaWeekReportService.selectOaWeekReportList(oaWeekReport);
        ExcelUtil<OaWeekReport> util = new ExcelUtil<OaWeekReport>(OaWeekReport.class);
        util.exportExcel(response, list, "周报填写数据");
    }

    /**
     * 获取周报填写详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:report:query')")
    @GetMapping(value = "/{weekrId}")
    public AjaxResult getInfo(@PathVariable("weekrId") Long weekrId)
    {
        return success(oaWeekReportService.selectOaWeekReportByWeekrId(weekrId));
    }

    /**
     * 新增周报填写
     */
    @PreAuthorize("@ss.hasPermi('project:report:add')")
    @Log(title = "周报填写", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OaWeekReport oaWeekReport)
    {
        return toAjax(oaWeekReportService.insertOaWeekReport(oaWeekReport));
    }

    /**
     * 修改周报填写
     */
    @PreAuthorize("@ss.hasPermi('project:report:edit')")
    @Log(title = "周报填写", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OaWeekReport oaWeekReport)
    {
        return toAjax(oaWeekReportService.updateOaWeekReport(oaWeekReport));
    }

    /**
     * 删除周报填写
     */
    @PreAuthorize("@ss.hasPermi('project:report:remove')")
    @Log(title = "周报填写", businessType = BusinessType.DELETE)
	@DeleteMapping("/{weekrIds}")
    public AjaxResult remove(@PathVariable Long[] weekrIds)
    {
        return toAjax(oaWeekReportService.deleteOaWeekReportByWeekrIds(weekrIds));
    }
}
