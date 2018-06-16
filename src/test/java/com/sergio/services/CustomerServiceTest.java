package com.sergio.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sergio.api.v1.mapper.CustomerMapper;
import com.sergio.api.v1.model.CustomerDTO;
import com.sergio.controllers.v1.CustomerController;
import com.sergio.domain.Customer;
import com.sergio.repositories.CustomerRepository;
import com.sergio.service.CustomerService;
import com.sergio.service.CustomerServiceImpl;

public class CustomerServiceTest {

	private static final Long ID = 1L;
	private static final String FIRSTNAME = "Joe";
	private static final String LASTNAME = "Adams";
	
	CustomerService customerService;
	
	@Mock
	CustomerRepository customerRepository;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE, customerRepository);
	}

	@Test
	public void getAllCustomers() throws Exception {

		// given
		List<Customer> customers = Arrays.asList(new Customer(), new Customer(), new Customer());
		
		when(customerRepository.findAll()).thenReturn(customers);
		
		// when
		List<CustomerDTO> customerDTOs = customerService.getAllCustomers();
		
		// then
		assertEquals(3, customerDTOs.size());
	}
	
	@Test
	public void getCustomerById() throws Exception {
		
		// given
		Customer customer = new Customer();
		customer.setId(ID);
		customer.setFirstname(FIRSTNAME);
		customer.setLastname(LASTNAME);

		 Optional<Customer> customerOptional = Optional.of(customer);
		
		when(customerRepository.findById(anyLong())).thenReturn(customerOptional);

		// when
		CustomerDTO customerDTO = customerService.getCustomerById(ID);
		
		// then
		assertEquals(FIRSTNAME, customerDTO.getFirstname());
		assertEquals(LASTNAME, customerDTO.getLastname());
	}
	
	@Test
	public void createNewCustomer() throws Exception {
		
		// given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname("Jim");
		
		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname(customerDTO.getFirstname());
		savedCustomer.setLastname(customerDTO.getLastname());
		savedCustomer.setId(1L);
		
		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
		
		// when
		CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);
		
		// then
		assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
		assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
	}
	
	@Test
	public void saveCustomerByDTO() throws Exception {
		
		// given
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setFirstname("Jim");
		
		Customer savedCustomer = new Customer();
		savedCustomer.setFirstname(customerDTO.getFirstname());
		savedCustomer.setLastname(customerDTO.getLastname());
		savedCustomer.setId(1L);
		
		when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

		// when
		CustomerDTO savedDto = customerService.saveCustomerByDTO(ID, customerDTO);
		
		// then
		assertEquals(customerDTO.getFirstname(), savedDto.getFirstname());
		assertEquals(CustomerController.BASE_URL + "/1", savedDto.getCustomerUrl());
	}
	
	@Test
	public void deleteCustomerById() throws Exception {
		
		customerRepository.deleteById(ID);
		
		verify(customerRepository, times(1)).deleteById(anyLong());
	}
}
