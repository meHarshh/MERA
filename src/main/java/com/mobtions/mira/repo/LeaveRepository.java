package com.mobtions.mira.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobtions.mira.entity.Employee;
import com.mobtions.mira.entity.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Integer> {

	List<Leave> findByEmployee(Employee employee);

}
