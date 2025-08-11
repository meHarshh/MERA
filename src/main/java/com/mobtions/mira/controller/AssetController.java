package com.mobtions.mira.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mobtions.mira.entity.Asset;
import com.mobtions.mira.service.AssetService;
import com.mobtions.mira.util.ResponseStructure;


@CrossOrigin(allowCredentials = "true", origins = "http://mera.kingsmenrealty.com/")
@RequestMapping("/assets")
@RestController
public class AssetController {

	@Autowired
	private AssetService assetService;

	@PostMapping(value = "/{employeeId}")
	private ResponseEntity<ResponseStructure<Asset>> grantAsset(@RequestBody Asset asset, @PathVariable int employeeId){
		return assetService.grantAsset(asset, employeeId);
	}

	@GetMapping(value = "/{employeeId}")
	private ResponseEntity<ResponseStructure<List<Asset>>> fetchAssetBasedOnEmployee(@PathVariable int employeeId){
		return assetService.fetchAssetBasedOnEmployee(employeeId);
	}

	@GetMapping
	private ResponseEntity<ResponseStructure<List<Asset>>> fetchAllAssets(){
		return assetService.fetchAllAssets();
	}

	@DeleteMapping(value = "/{employeeId}")
	private ResponseEntity<ResponseStructure<List<Asset>>> deleteAllAssetBasedOnEmployee(@PathVariable int employeeId){
		return assetService.deleteAllAssetBasedOnEmployee(employeeId);		
	}

	@PutMapping
	private ResponseEntity<ResponseStructure<Asset>> editAssetBasedOnAssetId(@RequestBody Asset asset){
		return assetService.editAssetBasedOnAssetId(asset);
	}
}
