package com.mobtions.mira.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mobtions.mira.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Optional<Employee> findByOfficialEmail(String officialEmail);

	Optional<Employee> findByPersonalEmail(String personalEmail);

	Optional<Employee> findByEmployeeCode(String employeeCode);

}
