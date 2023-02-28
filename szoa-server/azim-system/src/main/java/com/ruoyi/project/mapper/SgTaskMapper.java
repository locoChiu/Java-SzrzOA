package com.ruoyi.project.mapper;

import com.ruoyi.project.domain.ProjectFile;
import com.ruoyi.project.domain.SgTask;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * 任务Mapper接口
 * 
 * @author azim
 * @date 2023-02-21
 */
public interface SgTaskMapper 
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
     * 删除任务
     * 
     * @param taskId 任务主键
     * @return 结果
     */
    public int deleteSgTaskByTaskId(Long taskId);

    /**
     * 批量删除任务
     * 
     * @param taskIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSgTaskByTaskIds(Long[] taskIds);

    int addProFiles(@Param("id") Long taskId, @Param("path") String s);

    List<ProjectFile> getProjectFile(@Param("id") Long taskId);

    int deletProFiles(@Param("id") Long taskId, @Param("path") String path);
}
