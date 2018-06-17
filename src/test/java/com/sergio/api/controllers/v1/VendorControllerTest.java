package com.sergio.api.controllers.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.sergio.api.v1.model.VendorDTO;
import com.sergio.controllers.v1.RestResponseEntityExceptionHandler;
import com.sergio.controllers.v1.VendorController;
import com.sergio.service.ResourceNotFoundException;
import com.sergio.service.VendorService;

public class VendorControllerTest {

	private static final Long ID = 1L;
	private static final String NAME = "Nuts for Nuts Company";
	
	@Mock
	VendorService vendorService;
	
	@InjectMocks
	VendorController vendorController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(vendorController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}
	
	@Test
	public void testListVendors() throws Exception {
		VendorDTO vendor1 = new VendorDTO();
		vendor1.setName(NAME);
		
		VendorDTO vendor2 = new VendorDTO();
		vendor2.setName("Factory Inc");
		
		List<VendorDTO> vendors = Arrays.asList(vendor1, vendor2);
		
		when(vendorService.getAllVendors()).thenReturn(vendors);
		
		mockMvc.perform(get(VendorController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.vendors", hasSize(2)));
	}
	
	@Test
	public void testGetByIdVendors() throws Exception {
		VendorDTO vendor1 = new VendorDTO();
		vendor1.setName(NAME);
		
		when(vendorService.getVendorById(ID)).thenReturn(vendor1);
		
		mockMvc.perform(get(VendorController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", equalTo(NAME)));
	}
	
	@Test
	public void createNewVendor() throws Exception {
		// given
		VendorDTO vendor1 = new VendorDTO();
		vendor1.setName(NAME);
		
		VendorDTO returnDTO = new VendorDTO();
		returnDTO.setName(vendor1.getName());
		returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");
		
		when(vendorService.createNewVendor(vendor1)).thenReturn(returnDTO);
		
		mockMvc.perform(post(VendorController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(vendor1)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name", equalTo(NAME)))
		.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
	}
	
	@Test
	public void testUpdateVendor() throws Exception {
		// given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		
		VendorDTO returnDTO = new VendorDTO();
		returnDTO.setName(vendorDTO.getName());
		returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");
		
		when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);
		
		mockMvc.perform(put(VendorController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(vendorDTO)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", equalTo(NAME)))
		.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
	}
	
	@Test
	public void testPatchVendor() throws Exception {
		// given
		VendorDTO vendorDTO = new VendorDTO();
		vendorDTO.setName(NAME);
		
		VendorDTO returnDTO = new VendorDTO();
		returnDTO.setName(vendorDTO.getName());
		returnDTO.setVendorUrl(VendorController.BASE_URL + "/1");
		
		when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(returnDTO);
		
		mockMvc.perform(patch(VendorController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(vendorDTO)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.name", equalTo(NAME)))
		.andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
	}
	
	@Test
	public void testDeleteVendor() throws Exception {
		
		mockMvc.perform(delete(VendorController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		verify(vendorService).deleteVendorById(anyLong());
	}
	
	@Test
	public void testGetByNameNotFound() throws Exception {
		
		when(vendorController.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get(VendorController.BASE_URL + "/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
