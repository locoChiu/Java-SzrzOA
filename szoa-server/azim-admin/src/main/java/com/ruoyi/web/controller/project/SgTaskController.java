package com.ruoyi.web.controller.project;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.project.domain.OaTasks;
import com.ruoyi.project.domain.SgTask;
import com.ruoyi.project.service.IOaTasksService;
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
 * 任务Controller
 * 
 * @author azim
 * @date 2023-02-21
 */
@RestController
@RequestMapping("/project/sgtask")
public class SgTaskController extends BaseController
{
    @Autowired
    private ISgTaskService sgTaskService;

    @Autowired
    private IOaTasksService oaTasksService;

    /**
     * 查询任务列表
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:list')")
    // @Anonymous
    @GetMapping("/list")
    public TableDataInfo list(SgTask sgTask)
    {
        startPage();
        List<SgTask> list = sgTaskService.selectSgTaskList(sgTask);
        return getDataTable(list);
    }

    /**
     * 导出任务列表
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:export')")
    @Log(title = "任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SgTask sgTask)
    {
        List<SgTask> list = sgTaskService.selectSgTaskList(sgTask);
        ExcelUtil<SgTask> util = new ExcelUtil<SgTask>(SgTask.class);
        util.exportExcel(response, list, "任务数据");
    }

    /**
     * 获取任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId)
    {
        return success(sgTaskService.selectSgTaskByTaskId(taskId));
    }

    /**
     * 新增任务
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:add')")
    @Log(title = "任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SgTask sgTask)
    {
        Long tasksId = sgTask.getTasksId();
        oaTasksService.updateAllTask(tasksId,true);
        return toAjax(sgTaskService.insertSgTask(sgTask));
    }

    /**
     * 修改任务
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:edit')")
    @Log(title = "任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SgTask sgTask)
    {
        if(sgTask.getTaskStatus() != null && sgTask.getTaskStatus() == 3)
        {
            oaTasksService.updateFinishTask(sgTask.getTasksId(),true);
        }
        return toAjax(sgTaskService.updateSgTask(sgTask));
    }

    /**
     * 删除任务
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:remove')")
    @Log(title = "任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable Long[] taskIds)
    {
        for (Long taskId : taskIds) {
            SgTask sgTask = sgTaskService.selectSgTaskByTaskId(taskId);
            Long proId = sgTask.getTasksId();
            oaTasksService.updateAllTask(proId,false);
        }
        return toAjax(sgTaskService.deleteSgTaskByTaskIds(taskIds));
    }


    /**
     * 存储附件路径
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:add')")
    @Log(title = "附件路径", businessType = BusinessType.INSERT)
    @PostMapping("/addTaskFiles")
    public AjaxResult addTaskFiles(@RequestBody Map<String,String> data)
    {
        Long taskId = Long.valueOf(data.get("taskId"));
        String path = data.get("path");
        return toAjax(sgTaskService.addTaskFiles(taskId,path));
    }

    /**
     * 获取附件路径
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:query')")
    @GetMapping("/getTaskFiles/{taskId}")
    public AjaxResult getTaskFiles(@PathVariable("taskId") Long taskId)
    {
        return success(sgTaskService.getTaskFiles(taskId));
    }

    /**
     * 删除附件路径
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:delte')")
    @Log(title = "附件路径", businessType = BusinessType.DELETE)
    @PostMapping("/deletTaskFiles")
    public AjaxResult deletTaskFiles(@RequestBody Map<String,String> data)
    {
        Long taskId = Long.valueOf(data.get("taskId"));
        String path = data.get("path");
        String filePath = RuoYiConfig.getProfile() + path.replace("/profile","");
        FileUtils.deleteFile(filePath);
        return toAjax(sgTaskService.deletTaskFiles(taskId, path));
    }
}
