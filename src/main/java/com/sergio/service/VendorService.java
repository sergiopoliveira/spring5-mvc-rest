package com.sergio.service;

import java.util.List;

import com.sergio.api.v1.model.VendorDTO;

public interface VendorService {

	List<VendorDTO> getAllVendors();
	
	VendorDTO getVendorById(Long id);
	
	VendorDTO createNewVendor(VendorDTO vendorDTO);
	
	VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO);
	
	VendorDTO patchVendor(Long id, VendorDTO vendorDTO);
	
	void deleteVendorById(Long id);
}
