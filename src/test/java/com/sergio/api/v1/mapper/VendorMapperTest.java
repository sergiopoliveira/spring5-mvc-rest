package com.sergio.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sergio.api.v1.model.VendorDTO;
import com.sergio.domain.Vendor;

public class VendorMapperTest {

	public static final String NAME = "Factory Inc";
	
	VendorMapper vendorMapper = VendorMapper.INSTANCE;
	
	@Test
	public void vendorToVendorDTOTest() throws Exception {
		
		// given
		Vendor vendor = new Vendor();
		vendor.setName(NAME);
		
		// when
		VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
		
		// then
		assertEquals(NAME, vendorDTO.getName());
	}
}
