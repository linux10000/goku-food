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

import com.akiratoriyama.gokufoodapi.controller.mapper.PersonAddressControllerMapper;
import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.controller.response.PersonAddressResponse;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.PersonAddressNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.infra.util.Const.Resources;
import com.akiratoriyama.gokufoodapi.repository.filter.PersonAddressFilter;
import com.akiratoriyama.gokufoodapi.service.PersonAddressService;
import com.akiratoriyama.gokufoodapi.service.to.PersonAddressInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.PersonAddressUpdateSTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class PersonAddressController {
	
	@Autowired
	private PersonAddressService personAddressService;
	
	@Autowired
	private PersonAddressControllerMapper mapper;

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.PERSONADDRESS_FILTER})
	@PostMapping(Endpoint.PersonAddress.V1.URL_FILTER)
    public FmpPageResponse<PersonAddressResponse> filter(@Valid @RequestBody PersonAddressFilter filter) {
		return mapper.to(personAddressService.filter(filter));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.PERSONADDRESS_INSERT})
	@PostMapping(Endpoint.PersonAddress.V1.URL_INSERT)
	public PersonAddressResponse createPersonAddress(@RequestBody @Valid PersonAddressInsertSTO sto) {
		return this.mapper.to(this.personAddressService.insert(sto));
	}

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.PERSONADDRESS_UPDATE})
	@PutMapping(Endpoint.PersonAddress.V1.URL_UPDATE)
	public PersonAddressResponse updatePersonAddress(@RequestBody @Valid PersonAddressUpdateSTO sto) throws PersonAddressNotFoundException {
    	return this.mapper.to(this.personAddressService.update(sto));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.PERSONADDRESS_DELETE})
	@DeleteMapping(Endpoint.PersonAddress.V1.URL_DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletePersonAddressById(@RequestParam @NotBlank(message = Messages.PERSONADDRESS_ID_NOT_NULL) BigInteger personAddressId) throws PersonAddressNotFoundException {
		this.personAddressService.deleteById(personAddressId);
	}
}
