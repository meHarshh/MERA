package com.mobtions.mira.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mobtions.mira.entity.Asset;
import com.mobtions.mira.entity.Employee;
import com.mobtions.mira.repo.AssetRepositiory;
import com.mobtions.mira.repo.EmployeeRepository;
import com.mobtions.mira.service.AssetService;
import com.mobtions.mira.util.ResponseStructure;

@Service
public class AssetServiceImpl implements AssetService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private AssetRepositiory assetRepositiory;

	
	@Override
	public ResponseEntity<ResponseStructure<Asset>> grantAsset(Asset asset, int employeeId) {
		

	    Optional<Employee> byId = employeeRepository.findById(employeeId);
	    if (byId.isEmpty()) {
	        ResponseStructure<Asset> errorResponse = new ResponseStructure<>();
	        errorResponse.setMessage("Employee not found with ID: " + employeeId);
	        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
	        errorResponse.setData(null);
	        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	    }

	    Employee employee = byId.get();

	    asset.setEmployee(employee);
	    List<Asset> assets = new ArrayList<Asset>();
	    assets.add(asset);
	    employee.setAssets(assets);


	    Asset savedAsset = assetRepositiory.save(asset);
	    employeeRepository.save(employee);

	    // Build response
	    ResponseStructure<Asset> response = new ResponseStructure<>();
	    response.setMessage("Asset assigned successfully to employee ID: " + employeeId);
	    response.setStatus(HttpStatus.CREATED.value());
	    response.setData(savedAsset);

	    return new ResponseEntity<>(response, HttpStatus.CREATED);
	}


	@Override
	public ResponseEntity<ResponseStructure<List<Asset>>> fetchAssetBasedOnEmployee(int employeeId) {
	    ResponseStructure<List<Asset>> structure = new ResponseStructure<>();

	    Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
	    
	    if (optionalEmployee.isPresent()) {
	        Employee employee = optionalEmployee.get();
	        List<Asset> assets = employee.getAssets();

	        structure.setStatus(HttpStatus.OK.value());
	        structure.setMessage("Assets fetched successfully for employee ID: " + employeeId);
	        structure.setData(assets);

	        return new ResponseEntity<>(structure, HttpStatus.OK);
	    } else {
	        structure.setStatus(HttpStatus.NOT_FOUND.value());
	        structure.setMessage("Employee with ID " + employeeId + " not found");
	        structure.setData(null);

	        return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
	    }
	}


	@Override
	public ResponseEntity<ResponseStructure<List<Asset>>> fetchAllAssets() {
	    ResponseStructure<List<Asset>> structure = new ResponseStructure<>();

	    List<Asset> allAssets = assetRepositiory.findAll();

	    if (allAssets.isEmpty()) {
	        structure.setStatus(HttpStatus.NO_CONTENT.value());
	        structure.setMessage("No assets found in the database.");
	        structure.setData(allAssets);

	        return new ResponseEntity<>(structure, HttpStatus.NO_CONTENT);
	    } else {
	        structure.setStatus(HttpStatus.OK.value());
	        structure.setMessage("All assets fetched successfully.");
	        structure.setData(allAssets);

	        return new ResponseEntity<>(structure, HttpStatus.OK);
	    }
	}


	@Override
	public ResponseEntity<ResponseStructure<List<Asset>>> deleteAllAssetBasedOnEmployee(int employeeId) {
	    ResponseStructure<List<Asset>> structure = new ResponseStructure<>();

	    Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);

	    if (optionalEmployee.isEmpty()) {
	        structure.setStatus(HttpStatus.NOT_FOUND.value());
	        structure.setMessage("Employee with ID " + employeeId + " not found.");
	        structure.setData(null);
	        return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
	    }

	    Employee employee = optionalEmployee.get();
	    List<Asset> assets = employee.getAssets();

	    if (assets == null || assets.isEmpty()) {
	        structure.setStatus(HttpStatus.NO_CONTENT.value());
	        structure.setMessage("No assets found for employee with ID: " + employeeId);
	        structure.setData(List.of());
	        return new ResponseEntity<>(structure, HttpStatus.NO_CONTENT);
	    }

	    // Unlink and delete all assets
	    for (Asset asset : assets) {
	        asset.setEmployee(null);
	        assetRepositiory.delete(asset);
	    }

	    employee.setAssets(new ArrayList<>()); // Clear reference

	    structure.setStatus(HttpStatus.OK.value());
	    structure.setMessage("All assets for employee ID " + employeeId + " have been deleted.");
	    structure.setData(assets); // returning what was deleted
	    return new ResponseEntity<>(structure, HttpStatus.OK);
	}


	@Override
	public ResponseEntity<ResponseStructure<Asset>> editAssetBasedOnAssetId(Asset updatedAsset) {
	    ResponseStructure<Asset> structure = new ResponseStructure<>();

	    int assetId = updatedAsset.getId(); // or getAssetId() if named that way
	    Optional<Asset> optionalAsset = assetRepositiory.findById(assetId);

	    if (optionalAsset.isEmpty()) {
	        structure.setStatus(HttpStatus.NOT_FOUND.value());
	        structure.setMessage("Asset with ID " + assetId + " not found.");
	        structure.setData(null);
	        return new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);
	    }

	    Asset existingAsset = optionalAsset.get();

	    // Update only non-null/new values from updatedAsset
	    existingAsset.setAssetTag(updatedAsset.getAssetTag());
	    existingAsset.setType(updatedAsset.getType());
	    existingAsset.setBrand(updatedAsset.getBrand());
	    existingAsset.setModel(updatedAsset.getModel());
	    existingAsset.setSerialNumber(updatedAsset.getSerialNumber());
	    existingAsset.setAssignedDate(updatedAsset.getAssignedDate());
	    existingAsset.setReturnDate(updatedAsset.getReturnDate());
	    existingAsset.setAssetsStatus(updatedAsset.getAssetsStatus());
	    existingAsset.setRemarks(updatedAsset.getRemarks());

	    // Update employee if passed (optional)
	    if (updatedAsset.getEmployee() != null) {
	        Optional<Employee> empOpt = employeeRepository.findById(updatedAsset.getEmployee().getEmployeeId());
	        empOpt.ifPresent(existingAsset::setEmployee);
	    }

	    assetRepositiory.save(existingAsset);

	    structure.setStatus(HttpStatus.OK.value());
	    structure.setMessage("Asset updated successfully.");
	    structure.setData(existingAsset);

	    return new ResponseEntity<>(structure, HttpStatus.OK);
	}



	


	

	

}
