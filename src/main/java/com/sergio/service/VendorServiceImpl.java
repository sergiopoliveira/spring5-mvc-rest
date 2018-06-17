package com.sergio.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sergio.api.v1.mapper.VendorMapper;
import com.sergio.api.v1.model.VendorDTO;
import com.sergio.controllers.v1.VendorController;
import com.sergio.domain.Vendor;
import com.sergio.repositories.VendorRepository;

@Service
public class VendorServiceImpl implements VendorService {

	private final VendorMapper vendorMapper;
	private final VendorRepository vendorRepository;

	public VendorServiceImpl(VendorMapper vendorMapper, VendorRepository vendorRepository) {
		this.vendorMapper = vendorMapper;
		this.vendorRepository = vendorRepository;
	}

	@Override
	public List<VendorDTO> getAllVendors() {

		return vendorRepository
				.findAll()
				.stream()
				.map(vendor -> {
			VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
			vendorDTO.setVendorUrl(getVendorUrl(vendor.getId()));
			return vendorDTO;
		}).collect(Collectors.toList());
	}

	@Override
	public VendorDTO getVendorById(Long id) {
		return vendorRepository.findById(id)
				.map(vendorMapper::vendorToVendorDTO)
				.map(vendorDTO -> {
			// set API URL
			vendorDTO.setVendorUrl(getVendorUrl(id));
			return vendorDTO;
		}).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public VendorDTO createNewVendor(VendorDTO vendorDTO) {

		Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);

		return saveAndReturnDTO(vendor);
	}

	@Override
	public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {

		Vendor vendor = vendorMapper.vendorDTOToVendor(vendorDTO);
		vendor.setId(id);

		return saveAndReturnDTO(vendor);
	}

	@Override
	public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {

		return vendorRepository.findById(id).map(vendor -> {

			if (vendorDTO.getName() != null) {
				vendor.setName(vendorDTO.getName());
			}

			VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));

			returnDTO.setVendorUrl(getVendorUrl(id));

			return returnDTO;
		}).orElseThrow(ResourceNotFoundException::new);
	}

	@Override
	public void deleteVendorById(Long id) {

		vendorRepository.deleteById(id);

	}

	private VendorDTO saveAndReturnDTO(Vendor vendor) {

		Vendor savedVendor = vendorRepository.save(vendor);

		VendorDTO returnDTO = vendorMapper.vendorToVendorDTO(savedVendor);

		returnDTO.setVendorUrl(getVendorUrl(savedVendor.getId()));

		return returnDTO;
	}

	private String getVendorUrl(Long id) {
		return VendorController.BASE_URL + "/" + id;
	}

}
