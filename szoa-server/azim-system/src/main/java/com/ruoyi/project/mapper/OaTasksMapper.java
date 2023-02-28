package com.ruoyi.project.mapper;

import com.ruoyi.project.domain.OaTasks;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * 任务包Mapper接口
 * 
 * @author azim
 * @date 2023-02-20
 */
public interface OaTasksMapper 
{
    /**
     * 查询任务包
     * 
     * @param tasksId 任务包主键
     * @return 任务包
     */
    public OaTasks selectOaTasksByTasksId(Long tasksId);

    /**
     * 查询任务包列表
     * 
     * @param oaTasks 任务包
     * @return 任务包集合
     */
    public List<OaTasks> selectOaTasksList(OaTasks oaTasks);


    public List<OaTasks> getUserAllTasks(@Param("ids") Long[] ids);

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
     * 删除任务包
     * 
     * @param tasksId 任务包主键
     * @return 结果
     */
    public int deleteOaTasksByTasksId(Long tasksId);

    /**
     * 批量删除任务包
     * 
     * @param tasksIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOaTasksByTasksIds(Long[] tasksIds);

    /**
     * 分派任务包
     * @param userId
     * @param tasksId
     * @return
     */
    int assignTasks(@Param("userId") Long userId,@Param("tasksId") Long tasksId);

    /**
     * 增加任务数
     * @param proId
     */
    void addAllDemands(Long proId);

    /**
     * 减少任务数
     * @param proId
     */
    void delAllDemands(Long proId);

    /**
     * 增加完成任务数
     * @param proId
     */
    void addFinishDemands(Long proId);

    /**
     * 减少完成任务数
     * @param proId
     */
    void delFinishDemands(Long proId);


}
