package com.mobtions.mira.dto;

import java.time.LocalDate;

import com.mobtions.mira.enums.TaskStatus;

public class TaskRequestDTO {

    private String title;
    private String description;
    private LocalDate assignedDate;
    private LocalDate deadline;

    private TaskStatus status;         

    private int assignedById;          
    private int assignedToId;          
    private Integer reassignedToId;    
    
    
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAssignedDate() {
        return assignedDate;
    }
    public void setAssignedDate(LocalDate assignedDate) {
        this.assignedDate = assignedDate;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public TaskStatus getStatus() {
        return status;
    }
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public int getAssignedById() {
        return assignedById;
    }
    public void setAssignedById(int assignedById) {
        this.assignedById = assignedById;
    }

    public int getAssignedToId() {
        return assignedToId;
    }
    public void setAssignedToId(int assignedToId) {
        this.assignedToId = assignedToId;
    }

    public Integer getReassignedToId() {
        return reassignedToId;
    }
    public void setReassignedToId(Integer reassignedToId) {
        this.reassignedToId = reassignedToId;
    }
}
