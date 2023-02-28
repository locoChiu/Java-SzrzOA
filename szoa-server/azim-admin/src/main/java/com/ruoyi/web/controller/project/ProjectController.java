package com.ruoyi.web.controller.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.project.domain.Project;
import com.ruoyi.project.service.IProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目集Controller
 *
 * @author azim
 * @date 2023-02-17
 */
@RestController
@RequestMapping("/project/proset")
public class ProjectController extends BaseController
{
    @Autowired
    private IProjectService projectService;

    /**
     * 查询项目集列表
     */
    @PreAuthorize("@ss.hasPermi('project:proset:list')")
    @GetMapping("/list")
    public TableDataInfo list(Project project)
    {
        startPage();
        List<Project> list = projectService.selectProjectList(project);
        return getDataTable(list);
    }

    /**
     * 获取用户参与的项目
     * @param userId
     * @param
     * @return
     */
    @PreAuthorize("@ss.hasPermi('project:proset:list')")
    @GetMapping("/getUserProject")
    public AjaxResult getUserProject(Long userId) {
        List<Project> result1 = projectService.getUserProject(userId,null);
        List<Project> result2 = projectService.getUserProject(userId,0);
        List<Project> result3 = projectService.getUserProject(userId,1);
        List<Project> result4 = projectService.getUserProject(userId,2);
        List<Project> result5 = projectService.getUserProject(userId,3);
        Map<String,Object> map = new HashMap<>();
        map.put("all",result1);
        map.put("nostart",result2);
        map.put("started",result3);
        map.put("stop",result4);
        map.put("end",result5);
        return success(map);
    }

    /**
     * 查询项目成员信息
     */
    @PreAuthorize("@ss.hasPermi('project:proset:query')")
    @GetMapping(value = "/properson/{proId}")
    public AjaxResult getProPersons(@PathVariable("proId") Long proId)
    {
        return success(projectService.selectProPersons(proId));
    }

    /**
     * 导出项目集列表
     */
    @PreAuthorize("@ss.hasPermi('project:proset:export')")
    @Log(title = "项目集", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Project project)
    {
        List<Project> list = projectService.selectProjectList(project);
        ExcelUtil<Project> util = new ExcelUtil<Project>(Project.class);
        util.exportExcel(response, list, "项目集数据");
    }

    /**
     * 获取项目集详细信息
     */
    @PreAuthorize("@ss.hasPermi('project:proset:query')")
    @GetMapping(value = "/{proId}")
    public AjaxResult getInfo(@PathVariable("proId") Long proId)
    {
        return success(projectService.selectProjectByProId(proId));
    }


    /**
     * 新增项目集
     */
    @PreAuthorize("@ss.hasPermi('project:proset:add')")
    @Log(title = "项目集", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Project project)
    {
        return toAjax(projectService.insertProject(project));
    }

    /**
     * 修改项目集
     */
    @PreAuthorize("@ss.hasPermi('project:proset:edit')")
    @Log(title = "项目集", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Project project)
    {
        return toAjax(projectService.updateProject(project));
    }

    /**
     * 删除项目集
     */
    @PreAuthorize("@ss.hasPermi('project:proset:remove')")
    @Log(title = "项目集", businessType = BusinessType.DELETE)
    @DeleteMapping("/{proIds}")
    public AjaxResult remove(@PathVariable Long[] proIds)
    {
        return toAjax(projectService.deleteProjectByProIds(proIds));
    }

    /**
     * 存储附件路径
     */
    @PreAuthorize("@ss.hasPermi('project:proset:add')")
    @Log(title = "附件路径", businessType = BusinessType.INSERT)
    @PostMapping("/addProFiles")
    public AjaxResult addProFiles(@RequestBody Map<String,String> data)
    {
        Long proId = Long.valueOf(data.get("proId"));
        String path = data.get("path");
        return toAjax(projectService.addProFiles(proId,path));
    }

    /**
     * 获取附件路径
     */
    @PreAuthorize("@ss.hasPermi('project:proset:query')")
    @GetMapping("/getProjectFile/{proId}")
    public AjaxResult getProjectFile(@PathVariable("proId") Long proId)
    {
        return success(projectService.getProjectFile(proId));
    }

    /**
     * 删除附件路径
     */
    @PreAuthorize("@ss.hasPermi('project:proset:delte')")
    @Log(title = "附件路径", businessType = BusinessType.DELETE)
    @PostMapping("/deletProFiles")
    public AjaxResult deletProFiles(@RequestBody Map<String,String> data)
    {
        Long proId = Long.valueOf(data.get("proId"));
        String path = data.get("path");
        String filePath = RuoYiConfig.getProfile() + path.replace("/profile","");
        FileUtils.deleteFile(filePath);
        return toAjax(projectService.deletProFiles(proId, path));
    }

    /**
     * 获取项目工时统计
     */
    @PreAuthorize("@ss.hasPermi('project:proset:query')")
    @GetMapping("/selectProHoursCount/{proId}")
    public AjaxResult selectProHoursCount(@PathVariable("proId") Long proId)
    {
        return success(projectService.selectProHoursCount(proId));
    }
}
