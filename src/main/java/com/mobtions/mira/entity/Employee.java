package com.mobtions.mira.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
=======
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
>>>>>>> origin/master
import com.mobtions.mira.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

<<<<<<< HEAD
@JsonIgnoreProperties({"manager", "assets", "remarks", "hibernateLazyInitializer", "handler"})
=======
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "employeeId")
>>>>>>> origin/master
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employeeId;
	private String fullName;
	private String officialEmail;
	private String personalEmail;
	private String phoneNumber;
	private String employeeCode;
	private Role role;
	private String password;
	private String department;
	private String designation;
	private LocalDate dateOfJoining;
	private LocalDate dateOfBirth;
	private String dateOfLeaving = null;
	private String aadharNumber;
	private String panNumber;
	
	private int leavesTaken ;
	private int wfhTaken ;
	private int totalLeavesAllowed = 24;
	private int totalWFHAllowed = 24;

	
	
	

	public int getLeavesTaken() {
		return leavesTaken;
	}

	public void setLeavesTaken(int leavesTaken) {
		this.leavesTaken = leavesTaken;
	}

	public int getWfhTaken() {
		return wfhTaken;
	}

	public void setWfhTaken(int wfhTaken) {
		this.wfhTaken = wfhTaken;
	}

	public int getTotalLeavesAllowed() {
		return totalLeavesAllowed;
	}

	public int getTotalWFHAllowed() {
		return totalWFHAllowed;
	}

	@ManyToOne
	private Employee manager;

	@JsonIgnore
	@OneToMany(mappedBy = "manager")
	private List<Employee> subordinates;

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public List<Employee> getSubordinates() {
		return subordinates;
	}

	public void setSubordinates(List<Employee> subordinates) {
		this.subordinates = subordinates;
	}
	
//	Assets
	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Asset> assets; 
	
	public List<Asset> getAssets() {
		return assets;
	}
	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getOfficialEmail() {
		return officialEmail;
	}
	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}
	public String getPersonalEmail() {
		return personalEmail;
	}
	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public LocalDate getDateOfJoining() {
		return dateOfJoining;
	}
	public void setDateOfJoining(LocalDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getDateOfLeaving() {
		return dateOfLeaving;
	}
	public void setDateOfLeaving(String dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	
}
