package com.ruoyi.project.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.project.domain.ProjectFile;
import com.ruoyi.project.domain.ProjectHoursCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.mapper.ProjectMapper;
import com.ruoyi.project.domain.Project;
import com.ruoyi.project.service.IProjectService;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 项目集Service业务层处理
 * 
 * @author azim
 * @date 2023-02-17
 */
@Service
public class ProjectServiceImpl implements IProjectService 
{
    @Autowired
    private ProjectMapper projectMapper;

    /**
     * 查询项目集
     * 
     * @param proId 项目集主键
     * @return 项目集
     */
    @Override
    public Project selectProjectByProId(Long proId)
    {

        return projectMapper.selectProjectByProId(proId);
    }

    @Override
    public String selectProUserName(Long userId) {
        return projectMapper.selectProUserName(userId);
    }

    /**
     * 查询项目成员信息
     *
     * @param proId 项目集主键
     * @return 项目集
     */
    @Override
    public Map<String,Object> selectProPersons(Long proId) {
        List<SysUser> proUsers = projectMapper.selectProPersons(proId);
        List<SysUser> otherUsers = projectMapper.selectOtherPersons(proId);
        Map<String,Object> map = new HashMap<>();
        map.put("proUser",proUsers);
        map.put("otherUser",otherUsers);
        return map;
    }

    /**
     * 获取用户参与的项目
     * @param userId
     * @param status
     * @return
     */
    @Override
    public List<Project> getUserProject(Long userId, Integer status) {
        return projectMapper.getUserProject(userId,status);
    }

    // 变更任务包完成数
    @Override
    public void updateDemand(Long proId, Boolean flag) {
        if(flag) {
          projectMapper.addAllDemands(proId) ;
        } else {
            projectMapper.delAllDemands(proId) ;
        }
    }

    // 变更完成任务数量
    @Override
    public void updateFinishTask(Long proId, Boolean flag) {
        if(flag) {
            projectMapper.addFinishDemands(proId) ;
        } else {
            projectMapper.delFinishDemands(proId) ;
        }
    }

    /**
     * 查询项目集列表
     * 
     * @param project 项目集
     * @return 项目集
     */
    @Override
    public List<Project> selectProjectList(Project project)
    {
        return projectMapper.selectProjectList(project);
    }

    /**
     * 新增项目集
     * 
     * @param project 项目集
     * @return 结果
     */
    @Override
    public int insertProject(Project project)
    {
        int result = projectMapper.insertProject(project);
        project.setCreateTime(DateUtils.getNowDate());
        // Long proUserId = Long.valueOf(project.getUserId());
        // projectMapper.setProAndUserRelation(project.getProId(),proUserId);
        if(project.getProPersons().size() > 0) {
            List<SysUser> users = project.getProPersons();
            for (SysUser user : users) {
                projectMapper.setProAndUserRelation(project.getProId(),user.getUserId());
            }
        }
        return result;
    }

    /**
     * 修改项目集
     * 
     * @param project 项目集
     * @return 结果
     */
    @Override
    public int updateProject(Project project)
    {
        project.setUpdateTime(DateUtils.getNowDate());
        projectMapper.deleteProAndUserRelation(project.getProId());
        if(project.getProPersons() != null) {
            if(project.getProPersons().size() > 0) {
                List<SysUser> users = project.getProPersons();
                for (SysUser user : users) {
                    projectMapper.setProAndUserRelation(project.getProId(),user.getUserId());
                }
            }
        }
        return projectMapper.updateProject(project);
    }

    /**
     * 批量删除项目集
     * 
     * @param proIds 需要删除的项目集主键
     * @return 结果
     */
    @Override
    public int deleteProjectByProIds(Long[] proIds)
    {
        if(proIds.length > 0) {
            for (Long proId : proIds) {
                projectMapper.deleteProAndUserRelation(proId);
            }
        }
        return projectMapper.deleteProjectByProIds(proIds);
    }

    /**
     * 删除项目集信息
     * 
     * @param proId 项目集主键
     * @return 结果
     */
    @Override
    public int deleteProjectByProId(Long proId)
    {
        if(proId != null) {
            projectMapper.deleteProAndUserRelation(proId);
        }
        return projectMapper.deleteProjectByProId(proId);
    }

    /**
     * 添加项目附件路径
     * @param proId
     * @param pathList
     * @return
     */
    @Override
    public int addProFiles(Long proId, String path) {
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
            result = projectMapper.addProFiles(proId,s);
        }
        return result;
    }

    /**
     * 删除项目附件路径
     * @param proId
     * @param pathList
     * @return
     */
    @Override
    public int deletProFiles(Long proId, String path) {
        int result = 0;
        result = projectMapper.deletProFiles(proId,path);
        return result;
    }

    @Override
    public List<ProjectFile> getProjectFile(Long id) {
        return projectMapper.getProjectFile(id);
    }

    @Override
    public List<ProjectHoursCount> selectProHoursCount(Long id) {
        return projectMapper.selectProHoursCount(id);
    }
}
