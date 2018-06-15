package com.sergio.api.v1.mapper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.sergio.api.v1.model.CustomerDTO;
import com.sergio.domain.Customer;

public class CustomerMapperTest {

	private static final String FIRSTNAME = "Freddy";
	private static final String LASTNAME = "Meyers";
	
	CustomerMapper customerMapper = CustomerMapper.INSTANCE;
	
	@Test
	public void customerToCustomerDTOTest() throws Exception {
		
		// given
		Customer customer = new Customer();
		customer.setFirstname(FIRSTNAME);
		customer.setLastname(LASTNAME);

		// when 
		CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
		
		// then
		assertEquals(FIRSTNAME, customerDTO.getFirstname());
		assertEquals(LASTNAME, customerDTO.getLastname());
	}

}
