package com.ruoyi.project.service;

import java.util.List;
import java.util.Map;

import com.ruoyi.project.domain.Project;
import com.ruoyi.project.domain.ProjectFile;
import com.ruoyi.project.domain.ProjectHoursCount;

/**
 * 项目集Service接口
 * 
 * @author azim
 * @date 2023-02-17
 */
public interface IProjectService 
{
    /**
     * 查询项目集
     * 
     * @param proId 项目集主键
     * @return 项目集
     */
    public Project selectProjectByProId(Long proId);

    /**
     * 根据用户id查询用户名
     *
     * @param proId 项目集主键
     * @return 项目集
     */
    public String selectProUserName(Long userId);


    /**
     * 获取项目成员信息
     * @param proId
     * @return
     */
    public Map<String,Object> selectProPersons(Long proId);

    /**
     * 变更任务包完成数
     * @param proId
     * @return
     */
    public void updateDemand(Long proId,Boolean flag);

    /**
     * 变更完成任务数
     * @param tasksId
     * @param flag 0为+ 1为-
     */
    public void updateFinishTask(Long tasksId,Boolean flag);

    /**
     * 获取用户参与的项目
     * @param userId
     * @param status
     * @return
     */
    public List<Project> getUserProject(Long userId, Integer status);

    /**
     * 查询项目集列表
     * 
     * @param project 项目集
     * @return 项目集集合
     */
    public List<Project> selectProjectList(Project project);

    /**
     * 新增项目集
     * 
     * @param project 项目集
     * @return 结果
     */
    public int insertProject(Project project);

    /**
     * 修改项目集
     * 
     * @param project 项目集
     * @return 结果
     */
    public int updateProject(Project project);

    /**
     * 批量删除项目集
     * 
     * @param proIds 需要删除的项目集主键集合
     * @return 结果
     */
    public int deleteProjectByProIds(Long[] proIds);

    /**
     * 删除项目集信息
     * 
     * @param proId 项目集主键
     * @return 结果
     */
    public int deleteProjectByProId(Long proId);

    /**
     * 添加项目上传路径
     * @param proId
     * @param pathList
     * @return
     */
    int addProFiles(Long proId, String path);

    /**
     * 删除项目上传路径
     * @param proId
     * @param pathList
     * @return
     */
    int deletProFiles(Long proId, String path);

    /**
     * 获取项目附件路径
     * @param id
     * @return
     */
    List<ProjectFile> getProjectFile(Long id);

    // 获取项目工时统计
    List<ProjectHoursCount> selectProHoursCount(Long id);
}
