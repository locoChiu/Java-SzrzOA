package com.ruoyi.web.controller.project;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.project.domain.OaTasks;
import com.ruoyi.project.domain.Project;
import com.ruoyi.project.service.IOaTasksService;
import com.ruoyi.project.service.IProjectService;
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
 * 任务包Controller
 *
 * @author azim
 * @date 2023-02-20
 */
@RestController
@RequestMapping("/project/tasks")
public class OaTasksController extends BaseController
{
    @Autowired
    private IOaTasksService oaTasksService;

    @Autowired
    private IProjectService projectService;

    /**
     * 查询任务包列表
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:list')")
    @GetMapping("/list")
    public TableDataInfo list(OaTasks oaTasks)
    {
        startPage();
        List<OaTasks> list = oaTasksService.selectOaTasksList(oaTasks);
        for (OaTasks tasks : list) {
            tasks.setProUserName(projectService.selectProUserName(tasks.getProUserId()));
            if(tasks.getUserId() != null) {
                tasks.setUserName(projectService.selectProUserName(tasks.getUserId()));
            }
        }
        return getDataTable(list);
    }

    /**
     * 查询任务包列表
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:list')")
    @GetMapping("/getUserAllList/{proIds}")
    public AjaxResult getUserAllList(@PathVariable Long[] proIds)
    {
        List<OaTasks> list = oaTasksService.getUserAllTasks(proIds);
        return success(list);
    }

    /**
     * 导出任务包列表
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:export')")
    @Log(title = "任务包", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OaTasks oaTasks)
    {
        List<OaTasks> list = oaTasksService.selectOaTasksList(oaTasks);
        ExcelUtil<OaTasks> util = new ExcelUtil<OaTasks>(OaTasks.class);
        util.exportExcel(response, list, "任务包数据");
    }


    /**
     * 获取任务包详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:query')")
    @GetMapping(value = "/{tasksId}")
    public AjaxResult getInfo(@PathVariable("tasksId") Long tasksId)
    {
        OaTasks oaTasks = oaTasksService.selectOaTasksByTasksId(tasksId);
        if(oaTasks.getUserId()!=null) {
            oaTasks.setUserName(projectService.selectProUserName(oaTasks.getUserId()));
        }
        oaTasks.setProUserName(projectService.selectProUserName(oaTasks.getProUserId()));
        return success(oaTasks);
    }

    /**
     * 新增任务包
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:add')")
    @Log(title = "任务包", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OaTasks oaTasks)
    {
        Long proId = oaTasks.getProId();
        projectService.updateDemand(proId,true);
        Project project = projectService.selectProjectByProId(proId);
        // oaTasks.setProName(project.getProName());
        oaTasks.setProUserId(Long.valueOf(project.getUserId()));
        // oaTasks.setProUserName(project.getUserName());
        // if(oaTasks.getUserId() != null) {
        //     String name = projectService.selectProUserName(oaTasks.getUserId());
        //     oaTasks.setUserName(name);
        // }
        return toAjax(oaTasksService.insertOaTasks(oaTasks));
    }


    /**
     * 分派任务
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:add')")
    @Log(title = "任务包", businessType = BusinessType.INSERT)
    @PostMapping("/assignTasks")
    public AjaxResult assignTasks(@RequestBody Map<String,String> data)
    {
        Long userId = Long.valueOf(data.get("userId"));
        String tasksIds = data.get("tasksIds");
        String[] ids = tasksIds.split(",");
        for (String id : ids) {
            Long taksid = Long.valueOf(id);
            oaTasksService.assginTasks(userId,taksid);
        }
        return success();
    }

    /**
     * 修改任务包
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:edit')")
    @Log(title = "任务包", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OaTasks oaTasks)
    {
        return toAjax(oaTasksService.updateOaTasks(oaTasks));
    }

    /**
     * 删除任务包
     */
    @PreAuthorize("@ss.hasPermi('project:tasks:remove')")
    @Log(title = "任务包", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tasksIds}")
    public AjaxResult remove(@PathVariable Long[] tasksIds)
    {
        for (Long tasksId : tasksIds) {
            OaTasks oaTasks = oaTasksService.selectOaTasksByTasksId(tasksId);
            Long proId = oaTasks.getProId();
            projectService.updateDemand(proId,false);
        }
        return toAjax(oaTasksService.deleteOaTasksByTasksIds(tasksIds));
    }
}
