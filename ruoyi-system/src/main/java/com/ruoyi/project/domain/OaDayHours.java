package com.ruoyi.project.domain;

import java.util.Date;

public class OaDayHours {

    private Long id;
    private Long proId;
    private Long tasksId;
    private Long taskId;
    private Long userId;
    private Integer workHours;
    private Integer workSpeed;
    private String content;
    private Date workDate;



    public Integer getWorkSpeed() {
        return workSpeed;
    }

    public void setWorkSpeed(Integer workSpped) {
        this.workSpeed = workSpped;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public Long getTasksId() {
        return tasksId;
    }

    public void setTasksId(Long tasksId) {
        this.tasksId = tasksId;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Integer workHours) {
        this.workHours = workHours;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }
}
