package com.ruoyi.project.service;

import com.ruoyi.project.domain.ProjectFile;
import com.ruoyi.project.domain.SgTask;

import java.util.List;

/**
 * 任务Service接口
 * 
 * @author azim
 * @date 2023-02-21
 */
public interface ISgTaskService 
{
    /**
     * 查询任务
     * 
     * @param taskId 任务主键
     * @return 任务
     */
    public SgTask selectSgTaskByTaskId(Long taskId);

    /**
     * 查询任务列表
     * 
     * @param sgTask 任务
     * @return 任务集合
     */
    public List<SgTask> selectSgTaskList(SgTask sgTask);

    /**
     * 新增任务
     * 
     * @param sgTask 任务
     * @return 结果
     */
    public int insertSgTask(SgTask sgTask);

    /**
     * 修改任务
     * 
     * @param sgTask 任务
     * @return 结果
     */
    public int updateSgTask(SgTask sgTask);

    /**
     * 批量删除任务
     * 
     * @param taskIds 需要删除的任务主键集合
     * @return 结果
     */
    public int deleteSgTaskByTaskIds(Long[] taskIds);

    /**
     * 删除任务信息
     * 
     * @param taskId 任务主键
     * @return 结果
     */
    public int deleteSgTaskByTaskId(Long taskId);

    int addTaskFiles(Long taskId, String path);

    List<ProjectFile> getTaskFiles(Long taskId);

    int deletTaskFiles(Long taskId, String path);
}
