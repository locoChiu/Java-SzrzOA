package com.ruoyi.project.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.project.domain.Project;
import com.ruoyi.project.domain.ProjectFile;
import com.ruoyi.project.domain.ProjectHoursCount;
import org.apache.ibatis.annotations.Param;

/**
 * 项目集Mapper接口
 * 
 * @author azim
 * @date 2023-02-17
 */
public interface ProjectMapper 
{
    /**
     * 查询项目集
     * 
     * @param proId 项目集主键
     * @return 项目集
     */
    public Project selectProjectByProId(Long proId);

    /**
     * 查询项目成员
     * @param proId 项目ID
     * @return
     */
    public List<SysUser> selectProPersons(Long proId);

    /**
     * 查询其他成员
     * @param proId
     * @return
     */
    public List<SysUser> selectOtherPersons(Long proId);

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
     * 删除项目集
     * 
     * @param proId 项目集主键
     * @return 结果
     */
    public int deleteProjectByProId(Long proId);

    /**
     * 删除项目与项目成员关系
     *
     * @param proId 项目主键
     * @return 结果
     */
    public int deleteProAndUserRelation(Long proId);

    /**
     * 批量删除项目集
     * 
     * @param proIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteProjectByProIds(Long[] proIds);

    void setProAndUserRelation(@Param("proid") Long proId, @Param("userid") Long userid);

    /**
     * 添加项目附件路径
     * @param proId
     * @param path
     * @return
     */

    int addProFiles(@Param("id") Long proId, @Param("path") String path);
    /**
     * 删除项目附件路径
     * @param proId
     * @param path
     * @return
     */
    int deletProFiles(@Param("id") Long proId,@Param("path") String path);

    /**
     * 获取项目附件路径
     * @param id
     * @return
     */
    List<ProjectFile> getProjectFile(Long id);

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

    List<Project> getUserProject(@Param("userId")Long userId,@Param("status") Integer status);

    String selectProUserName(Long userId);

    List<ProjectHoursCount> selectProHoursCount(Long id);
}
