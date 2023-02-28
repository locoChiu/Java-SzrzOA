package com.ruoyi.project.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.domain.OaTasks;
import com.ruoyi.project.mapper.OaTasksMapper;
import com.ruoyi.project.service.IOaTasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 任务包Service业务层处理
 * 
 * @author azim
 * @date 2023-02-20
 */
@Service
public class OaTasksServiceImpl implements IOaTasksService
{
    @Autowired
    private OaTasksMapper oaTasksMapper;

    /**
     * 查询任务包
     * 
     * @param tasksId 任务包主键
     * @return 任务包
     */
    @Override
    public OaTasks selectOaTasksByTasksId(Long tasksId)
    {
        return oaTasksMapper.selectOaTasksByTasksId(tasksId);
    }

    @Override
    public List<OaTasks> getUserAllTasks(Long[] ids) {
        return oaTasksMapper.getUserAllTasks(ids);
    }

    /**
     * 查询任务包列表
     * 
     * @param oaTasks 任务包
     * @return 任务包
     */
    @Override
    public List<OaTasks> selectOaTasksList(OaTasks oaTasks)
    {
        return oaTasksMapper.selectOaTasksList(oaTasks);
    }

    /**
     * 新增任务包
     * 
     * @param oaTasks 任务包
     * @return 结果
     */
    @Override
    public int insertOaTasks(OaTasks oaTasks)
    {
        oaTasks.setCreateTime(DateUtils.getNowDate());
        return oaTasksMapper.insertOaTasks(oaTasks);
    }

    // 变更任务数量
    @Override
    public void updateAllTask(Long proId, Boolean flag) {
        if(flag) {
            oaTasksMapper.addAllDemands(proId) ;
        } else {
            oaTasksMapper.delAllDemands(proId) ;
        }
    }

    // 变更完成任务数量
    @Override
    public void updateFinishTask(Long proId, Boolean flag) {
        if(flag) {
            oaTasksMapper.addFinishDemands(proId) ;
        } else {
            oaTasksMapper.delFinishDemands(proId) ;
        }
    }

    /**
     * 修改任务包
     * 
     * @param oaTasks 任务包
     * @return 结果
     */
    @Override
    public int updateOaTasks(OaTasks oaTasks)
    {
        oaTasks.setUpdateTime(DateUtils.getNowDate());
        return oaTasksMapper.updateOaTasks(oaTasks);
    }

    /**
     * 批量删除任务包
     * 
     * @param tasksIds 需要删除的任务包主键
     * @return 结果
     */
    @Override
    public int deleteOaTasksByTasksIds(Long[] tasksIds)
    {
        return oaTasksMapper.deleteOaTasksByTasksIds(tasksIds);
    }

    /**
     * 删除任务包信息
     * 
     * @param tasksId 任务包主键
     * @return 结果
     */
    @Override
    public int deleteOaTasksByTasksId(Long tasksId)
    {
        return oaTasksMapper.deleteOaTasksByTasksId(tasksId);
    }

    /**
     * 指派任务
     * @param userId
     * @param tasksIds
     * @return
     */
    @Override
    public int assginTasks(Long userId, Long tasksId) {
        return oaTasksMapper.assignTasks(userId,tasksId);
    }
}
