package com.ruoyi.project.service;

import com.ruoyi.project.domain.OaTasks;

import java.util.List;
/**
 * 任务包Service接口
 * 
 * @author azim
 * @date 2023-02-20
 */
public interface IOaTasksService 
{
    /**
     * 查询任务包
     * 
     * @param tasksId 任务包主键
     * @return 任务包
     */
    public OaTasks selectOaTasksByTasksId(Long tasksId);

    public List<OaTasks> getUserAllTasks(Long[] ids);

    /**
     * 查询任务包列表
     * 
     * @param oaTasks 任务包
     * @return 任务包集合
     */
    public List<OaTasks> selectOaTasksList(OaTasks oaTasks);

    /**
     * 新增任务包
     * 
     * @param oaTasks 任务包
     * @return 结果
     */
    public int insertOaTasks(OaTasks oaTasks);

    /**
     * 修改任务包
     * 
     * @param oaTasks 任务包
     * @return 结果
     */
    public int updateOaTasks(OaTasks oaTasks);

    /**
     * 变更总任务数
     * @param tasksId
     * @param flag 0为+ 1为-
     */
    public void updateAllTask(Long tasksId,Boolean flag);

    /**
     * 变更完成任务数
     * @param tasksId
     * @param flag 0为+ 1为-
     */
    public void updateFinishTask(Long tasksId,Boolean flag);

    /**
     * 批量删除任务包
     * 
     * @param tasksIds 需要删除的任务包主键集合
     * @return 结果
     */
    public int deleteOaTasksByTasksIds(Long[] tasksIds);

    /**
     * 删除任务包信息
     * 
     * @param tasksId 任务包主键
     * @return 结果
     */
    public int deleteOaTasksByTasksId(Long tasksId);

    int assginTasks(Long userId, Long tasksIds);
}
