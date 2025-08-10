package com.mobtions.mira.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobtions.mira.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{

}
