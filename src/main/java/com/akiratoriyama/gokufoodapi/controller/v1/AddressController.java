package com.akiratoriyama.gokufoodapi.controller.v1;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.akiratoriyama.gokufoodapi.controller.mapper.AddressControllerMapper;
import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.AddressNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.infra.util.Const.Resources;
import com.akiratoriyama.gokufoodapi.model.Address;
import com.akiratoriyama.gokufoodapi.repository.filter.AddressByZipCodeFilter;
import com.akiratoriyama.gokufoodapi.repository.filter.AddressFilter;
import com.akiratoriyama.gokufoodapi.service.AddressService;
import com.akiratoriyama.gokufoodapi.service.to.AddressInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.AddressUpdateSTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class AddressController {
	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private AddressControllerMapper mapper;

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.ADDRESS_FILTER})
	@PostMapping(Endpoint.Address.V1.URL_FILTER)
    public FmpPageResponse<Address> filter(@Valid @RequestBody AddressFilter filter) {
		return mapper.to(addressService.filter(filter));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.ADDRESS_FILTER_BY_ZIPCODE})
	@PostMapping(Endpoint.Address.V1.URL_FILTER_BY_ZIPCODE)
	public FmpPageResponse<Address> filter(@Valid @RequestBody AddressByZipCodeFilter filter) {
		return mapper.to(addressService.filterByCachedZipCode(filter));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.ADDRESS_INSERT})
	@PostMapping(Endpoint.Address.V1.URL_INSERT)
	public Address createAddress(@RequestBody @Valid AddressInsertSTO sto) {
		return this.addressService.insert(sto);
	}

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.ADDRESS_UPDATE})
	@PutMapping(Endpoint.Address.V1.URL_UPDATE)
	public Address updateAddress(@RequestBody @Valid AddressUpdateSTO sto) throws AddressNotFoundException {
    	return this.addressService.update(sto);
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.ADDRESS_DELETE})
	@DeleteMapping(Endpoint.Address.V1.URL_DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteAddressById(@RequestParam @NotBlank(message = Messages.ADDRESS_ID_NOT_NULL) BigInteger addressId) throws AddressNotFoundException {
		this.addressService.deleteById(addressId);
	}
}
