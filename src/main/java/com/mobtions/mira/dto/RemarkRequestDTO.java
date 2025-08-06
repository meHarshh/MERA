package com.mobtions.mira.dto;

public class RemarkRequestDTO {

    private String content;
    private int commentedById;
    private int taskId;

    // Getters and Setters
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCommentedById() {
        return commentedById;
    }

    public void setCommentedById(int commentedById) {
        this.commentedById = commentedById;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
