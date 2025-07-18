package com.mobtions.mira.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobtions.mira.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
