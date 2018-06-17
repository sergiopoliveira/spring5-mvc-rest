package com.sergio.api.controllers.v1;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

import com.sergio.api.v1.model.CustomerDTO;
import com.sergio.controllers.v1.CategoryController;
import com.sergio.controllers.v1.CustomerController;
import com.sergio.controllers.v1.RestResponseEntityExceptionHandler;
import com.sergio.service.CustomerService;
import com.sergio.service.ResourceNotFoundException;

public class CustomerControllerTest {

	private static final Long ID = 1L;
	private static final String FIRSTNAME = "Joe";
	private static final String LASTNAME = "Adams";
	
	@Mock
	CustomerService customerService;
	
	@InjectMocks
	CustomerController customerController;
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(customerController)
				.setControllerAdvice(new RestResponseEntityExceptionHandler())
				.build();
	}
	
	@Test
	public void testListCustomers() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname(FIRSTNAME);
		customer1.setLastname(LASTNAME);
		
		CustomerDTO customer2 = new CustomerDTO();
		customer2.setFirstname("Sergio");
		customer2.setLastname("Oliveira");

		List<CustomerDTO> customers = Arrays.asList(customer1, customer2);
		
		when(customerService.getAllCustomers()).thenReturn(customers);
		
		mockMvc.perform(get(CustomerController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.customers", hasSize(2)));
	}
	
	@Test
	public void testGetByIdCustomers() throws Exception {
		CustomerDTO customer1 = new CustomerDTO();
		customer1.setFirstname(FIRSTNAME);
		customer1.setLastname(LASTNAME);
		
		when(customerService.getCustomerById(ID)).thenReturn(customer1);
		
		mockMvc.perform(get(CustomerController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)));
	}
	
	@Test
	public void createNewCustomer() throws Exception {
		// given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customerDTO.getFirstname());
		returnDTO.setLastname(customerDTO.getLastname());
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
		
		when(customerService.createNewCustomer(customerDTO)).thenReturn(returnDTO);
		
		mockMvc.perform(post(CustomerController.BASE_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(customerDTO)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
		.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
	}
	
	@Test
	public void testUpdateCustomer() throws Exception {
		// given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customerDTO.getFirstname());
		returnDTO.setLastname(customerDTO.getLastname());
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
		
		when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);
		
		mockMvc.perform(put(CustomerController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(customerDTO)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
		.andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
		.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
	}
	
	@Test
	public void testPatchCustomer() throws Exception {
		// given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname(FIRSTNAME);
		customerDTO.setLastname(LASTNAME);
		
		CustomerDTO returnDTO = new CustomerDTO();
		returnDTO.setFirstname(customerDTO.getFirstname());
		returnDTO.setLastname(customerDTO.getLastname());
		returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/1");
		
		when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);
		
		mockMvc.perform(patch(CustomerController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(AbstractRestControllerTest.asJsonString(customerDTO)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.firstname", equalTo(FIRSTNAME)))
		.andExpect(jsonPath("$.lastname", equalTo(LASTNAME)))
		.andExpect(jsonPath("$.customer_url", equalTo(CustomerController.BASE_URL + "/1")));
	}
	
	@Test
	public void testDeleteCustomer() throws Exception {
		
		mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		verify(customerService).deleteCustomerById(anyLong());
	}
	
	@Test
	public void testGetByNameNotFound() throws Exception {
		
		when(customerController.getCustomerById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		mockMvc.perform(get(CategoryController.BASE_URL + "/999")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
}
