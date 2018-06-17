package com.sergio.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sergio.api.v1.mapper.VendorMapper;
import com.sergio.api.v1.model.VendorDTO;
import com.sergio.controllers.v1.VendorController;
import com.sergio.domain.Vendor;
import com.sergio.repositories.VendorRepository;
import com.sergio.service.VendorService;
import com.sergio.service.VendorServiceImpl;

public class VendorServiceTest {

	private static final Long ID = 1L;
	private static final String NAME = "Factory Inc";
	
	VendorService vendorService;
	
	@Mock
	VendorRepository vendorRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		vendorService = new VendorServiceImpl(VendorMapper.INSTANCE, vendorRepository);
	}
	
	@Test
	public void getAllVendors() throws Exception {

		// given
		List<Vendor> vendors = Arrays.asList(new Vendor(), new Vendor(), new Vendor());
		
		when(vendorRepository.findAll()).thenReturn(vendors);
		
		// when
		List<VendorDTO> vendorDTOs = vendorService.getAllVendors();
		
		// then
		assertEquals(3, vendorDTOs.size());
	}
	
	@Test
	public void getVendorById() throws Exception {
		
		// given
		Vendor vendor = new Vendor();
		vendor.setId(ID);
		vendor.setName(NAME);

		 Optional<Vendor> vendorOptional = Optional.of(vendor);
		
		when(vendorRepository.findById(anyLong())).thenReturn(vendorOptional);

		// when
		VendorDTO vendorDTO = vendorService.getVendorById(ID);
		
		// then
		assertEquals(NAME, vendorDTO.getName());
	}
	
	@Test
	public void createNewVendor() throws Exception {
		
		// given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName("Factory Inc");
		
		Vendor savedVendor = new Vendor();
		savedVendor.setName(vendorDTO.getName());
		savedVendor.setId(1L);
		
		when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);
		
		// when
		VendorDTO savedDto = vendorService.createNewVendor(vendorDTO);
		
		// then
		assertEquals(vendorDTO.getName(), savedDto.getName());
		assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());
	}
	
	@Test
	public void saveVendorByDTO() throws Exception {
		
		// given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName("Factory Inc");
		
		Vendor savedVendor = new Vendor();
		savedVendor.setName(vendorDTO.getName());
		savedVendor.setId(1L);
		
		when(vendorRepository.save(any(Vendor.class))).thenReturn(savedVendor);

		// when
		VendorDTO savedDto = vendorService.saveVendorByDTO(ID, vendorDTO);
		
		// then
		assertEquals(vendorDTO.getName(), savedDto.getName());
		assertEquals(VendorController.BASE_URL + "/1", savedDto.getVendorUrl());
	}
	
	@Test
	public void deleteVendorById() throws Exception {
		
		vendorService.deleteVendorById(ID);
		
		verify(vendorRepository, times(1)).deleteById(anyLong());
	}
}
