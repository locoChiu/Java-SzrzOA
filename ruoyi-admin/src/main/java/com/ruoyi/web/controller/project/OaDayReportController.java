package com.ruoyi.web.controller.project;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.project.domain.*;
import com.ruoyi.project.service.IOaDayReportService;
import com.ruoyi.project.service.IOaTasksService;
import com.ruoyi.project.service.IProjectService;
import com.ruoyi.project.service.ISgTaskService;
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
 * 日报Controller
 *
 * @author azim
 * @date 2023-02-21
 */
@RestController
@RequestMapping("/project/dayreport")
public class OaDayReportController extends BaseController {
    @Autowired
    private IOaDayReportService oaDayReportService;

    @Autowired
    private IOaTasksService oaTasksService;

    @Autowired
    private ISgTaskService sgTaskService;

    @Autowired
    private IProjectService projectService;

    /**
     * 查询日报列表
     */
    @PreAuthorize("@ss.hasPermi('project:dayreport:list')")
    @GetMapping("/list")
    public TableDataInfo list(OaDayReport oaDayReport) {
        startPage();
        List<OaDayReport> list = oaDayReportService.selectOaDayReportList(oaDayReport);
        return getDataTable(list);
    }

    /**
     * 导出日报列表
     */
    @PreAuthorize("@ss.hasPermi('project:dayreport:export')")
    @Log(title = "日报", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OaDayReport oaDayReport) {
        List<OaDayReport> list = oaDayReportService.selectOaDayReportList(oaDayReport);
        ExcelUtil<OaDayReport> util = new ExcelUtil<OaDayReport>(OaDayReport.class);
        util.exportExcel(response, list, "日报数据");
    }

    /**
     * 获取日报详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:dayreport:query')")
    @GetMapping(value = "/{dayrId}")
    public AjaxResult getInfo(@PathVariable("dayrId") Long dayrId) {
        return success(oaDayReportService.selectOaDayReportByDayrId(dayrId));
    }

    /**
     * 新增日报
     */
    @PreAuthorize("@ss.hasPermi('project:dayreport:add')")
    @Log(title = "日报", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OaDayReport oaDayReport) {
        return toAjax(oaDayReportService.insertOaDayReport(oaDayReport));
    }

    /**
     * 工时填报
     */
    @PreAuthorize("@ss.hasPermi('project:dayreport:add')")
    @Log(title = "日报", businessType = BusinessType.INSERT)
    @PostMapping("/addDayHour")
    public AjaxResult addDayHour(@RequestBody OaDayHours data) {
        SgTask originalData = sgTaskService.selectSgTaskByTaskId(data.getTaskId());
        OaTasks oldoaTasks = oaTasksService.selectOaTasksByTasksId(data.getTasksId());
        // 变更任务包中的任务使用工时
        OaTasks tasks = new OaTasks();
        tasks.setTasksId(data.getTasksId());
        // 暂时将任务包中的使用人天改为使用工时
        if (oldoaTasks.getTasksUsedays() != null) {
            tasks.setTasksUsedays(oldoaTasks.getTasksUsedays() + data.getWorkHours());
        } else {
            tasks.setTasksUsedays(data.getWorkHours());
        }
        oaTasksService.updateOaTasks(tasks);

        if (data.getWorkSpeed() == 100) {
            oaTasksService.updateFinishTask(data.getTasksId(), true);
            // 变更该任务的状态和进度
            SgTask task = new SgTask();
            Integer workHours = originalData.getTasksUsehours() + data.getWorkHours();
            task.setTasksUsehours(workHours);
            task.setTaskId(data.getTaskId());
            task.setTaskStatus(3);
            task.setTaskSpeed(100);
            sgTaskService.updateSgTask(task);
            // 修改任务包完成状态
            SgTask sgTask = new SgTask();
            sgTask.setTasksId(data.getTasksId());
            List<SgTask> sgTasks = sgTaskService.selectSgTaskList(sgTask);
            Boolean flag = true;
            for (SgTask sgTask1 : sgTasks) {
                if(sgTask1.getTaskStatus() != 3) {
                    flag = false;
                }
            }
            if(flag) {
                OaTasks tasks2 = new OaTasks();
                tasks2.setTasksId(data.getTasksId());
                tasks2.setTasksStatus(3);
                oaTasksService.updateOaTasks(tasks2);
                projectService.updateFinishTask(data.getProId(),true);

                // 修改项目完成状态
                // Boolean flag2 = true;
                // OaTasks oaTasks = new OaTasks();
                // oaTasks.setProId(data.getProId());
                // List<OaTasks> oaTasks1 = oaTasksService.selectOaTasksList(oaTasks);
                // for (OaTasks oaTasks2 : oaTasks1) {
                //     if(oaTasks2.getTasksStatus() != 3) {
                //         flag2 = false;
                //     }
                // }
                // if(flag2) {
                //     Project pro = new Project();
                //     pro.setProId(data.getProId());
                //     pro.setStatus("3");
                //     projectService.updateProject(pro);
                // }
            }
        } else {
            SgTask task = new SgTask();
            Integer workHours = originalData.getTasksUsehours() + data.getWorkHours();
            task.setTasksUsehours(workHours);
            task.setTaskId(data.getTaskId());
            task.setTaskSpeed(data.getWorkSpeed());
            sgTaskService.updateSgTask(task);
        }
        return toAjax(oaDayReportService.insertDayHours(data));

    }

    /**
     * 修改日报
     */
    @PreAuthorize("@ss.hasPermi('project:dayreport:edit')")
    @Log(title = "日报", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OaDayReport oaDayReport) {
        return toAjax(oaDayReportService.updateOaDayReport(oaDayReport));
    }

    /**
     * 删除日报
     */
    @PreAuthorize("@ss.hasPermi('project:dayreport:remove')")
    @Log(title = "日报", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dayrIds}")
    public AjaxResult remove(@PathVariable Long[] dayrIds) {
        return toAjax(oaDayReportService.deleteOaDayReportByDayrIds(dayrIds));
    }
}
