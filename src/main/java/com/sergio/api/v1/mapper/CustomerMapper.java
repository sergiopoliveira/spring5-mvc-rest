package com.sergio.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.sergio.api.v1.model.CustomerDTO;
import com.sergio.domain.Customer;

@Mapper
public interface CustomerMapper {

	CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);
	
	CustomerDTO customerToCustomerDTO(Customer customer);
}
