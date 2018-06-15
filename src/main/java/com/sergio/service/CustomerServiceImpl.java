package com.sergio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sergio.api.v1.mapper.CustomerMapper;
import com.sergio.api.v1.model.CustomerDTO;
import com.sergio.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{

	private final CustomerMapper customerMapper;
	private final CustomerRepository customerRepository;
	
	public CustomerServiceImpl(CustomerMapper customerMapper, CustomerRepository customerRepository) {
		this.customerMapper = customerMapper;
		this.customerRepository = customerRepository;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() {
		
		return customerRepository.findAll()
				.stream()
				.map(customerMapper::customerToCustomerDTO)
				.collect(Collectors.toList());
	}

	@Override
	public CustomerDTO getCustomerById(Long id) {
		return customerMapper.customerToCustomerDTO(customerRepository.findById(id).get());
	}

}