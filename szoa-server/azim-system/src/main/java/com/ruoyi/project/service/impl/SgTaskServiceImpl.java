package com.ruoyi.project.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.domain.ProjectFile;
import com.ruoyi.project.domain.SgTask;
import com.ruoyi.project.mapper.SgTaskMapper;
import com.ruoyi.project.service.ISgTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 任务Service业务层处理
 * 
 * @author azim
 * @date 2023-02-21
 */
@Service
public class SgTaskServiceImpl implements ISgTaskService
{
    @Autowired
    private SgTaskMapper sgTaskMapper;

    /**
     * 查询任务
     * 
     * @param taskId 任务主键
     * @return 任务
     */
    @Override
    public SgTask selectSgTaskByTaskId(Long taskId)
    {
        return sgTaskMapper.selectSgTaskByTaskId(taskId);
    }

    /**
     * 查询任务列表
     * 
     * @param sgTask 任务
     * @return 任务
     */
    @Override
    public List<SgTask> selectSgTaskList(SgTask sgTask)
    {
        return sgTaskMapper.selectSgTaskList(sgTask);
    }

    /**
     * 新增任务
     * 
     * @param sgTask 任务
     * @return 结果
     */
    @Override
    public int insertSgTask(SgTask sgTask)
    {
        sgTask.setCreateTime(DateUtils.getNowDate());
        return sgTaskMapper.insertSgTask(sgTask);
    }

    /**
     * 修改任务
     * 
     * @param sgTask 任务
     * @return 结果
     */
    @Override
    public int updateSgTask(SgTask sgTask)
    {
        sgTask.setUpdateTime(DateUtils.getNowDate());
        return sgTaskMapper.updateSgTask(sgTask);
    }

    /**
     * 批量删除任务
     * 
     * @param taskIds 需要删除的任务主键
     * @return 结果
     */
    @Override
    public int deleteSgTaskByTaskIds(Long[] taskIds)
    {
        return sgTaskMapper.deleteSgTaskByTaskIds(taskIds);
    }

    /**
     * 删除任务信息
     * 
     * @param taskId 任务主键
     * @return 结果
     */
    @Override
    public int deleteSgTaskByTaskId(Long taskId)
    {
        return sgTaskMapper.deleteSgTaskByTaskId(taskId);
    }

    @Override
    public int addTaskFiles(Long taskId, String path) {
        List<String> pathList = new ArrayList<>();
        if(path.indexOf(",") != -1) {
            String[] paths = path.split(",");
            for (String s : paths) {
                pathList.add(s);
            }
        } else {
            pathList.add(path);
        }
        int result = 0;
        for (String s : pathList) {
            result = sgTaskMapper.addProFiles(taskId,s);
        }
        return result;
    }

    @Override
    public List<ProjectFile> getTaskFiles(Long taskId) {
        return sgTaskMapper.getProjectFile(taskId);
    }

    @Override
    public int deletTaskFiles(Long taskId, String path) {
        int result = 0;
        result = sgTaskMapper.deletProFiles(taskId,path);
        return result;
    }
}
