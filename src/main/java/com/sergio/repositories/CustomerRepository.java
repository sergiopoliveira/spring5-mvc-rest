package com.sergio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergio.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

	Optional<Customer> findById(Long id);
}
