package com.mobtions.mira.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.mobtions.mira.entity.Asset;
import com.mobtions.mira.util.ResponseStructure;

public interface AssetService {

	ResponseEntity<ResponseStructure<Asset>> grantAsset(Asset asset, int employeeId);

	ResponseEntity<ResponseStructure<List<Asset>>> fetchAssetBasedOnEmployee(int employeeId);

	ResponseEntity<ResponseStructure<List<Asset>>> fetchAllAssets();

	ResponseEntity<ResponseStructure<List<Asset>>> deleteAllAssetBasedOnEmployee(int employeeId);

	ResponseEntity<ResponseStructure<Asset>> editAssetBasedOnAssetId(Asset asset);

}
