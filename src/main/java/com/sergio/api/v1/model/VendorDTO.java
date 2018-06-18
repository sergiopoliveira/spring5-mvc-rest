package com.sergio.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VendorDTO {

	@ApiModelProperty(value = "This is the Vendors name", required = true)
	private String name;
	
	@ApiModelProperty(value = "This is the Vendors URL address", required = true)
	@JsonProperty("vendor_url")
	private String vendorUrl;
}
