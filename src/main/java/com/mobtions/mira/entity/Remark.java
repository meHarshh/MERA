package com.mobtions.mira.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Remark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String content;
    private LocalDate date;

    // Who made the remark
    @ManyToOne
    @JoinColumn(name = "commented_by_id")
    @JsonIgnoreProperties({"manager", "assets", "remarks"}) // Avoid nested serialization
    private Employee commentedBy;


    
    @JsonBackReference
    @ManyToOne
    private Task task;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Employee getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(Employee commentedBy) {
		this.commentedBy = commentedBy;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

    
    
}
