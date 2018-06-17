package com.sergio.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sergio.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long>{

	Optional<Vendor> findById(Long id);
}
