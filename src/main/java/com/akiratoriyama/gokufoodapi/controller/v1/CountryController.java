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

import com.akiratoriyama.gokufoodapi.controller.mapper.CountryControllerMapper;
import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.CountryNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.infra.util.Const.Resources;
import com.akiratoriyama.gokufoodapi.model.Country;
import com.akiratoriyama.gokufoodapi.repository.filter.CountryFilter;
import com.akiratoriyama.gokufoodapi.service.CountryService;
import com.akiratoriyama.gokufoodapi.service.to.CountryInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.CountryUpdateSTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class CountryController {
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private CountryControllerMapper mapper;

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.COUNTRY_FILTER})
	@PostMapping(Endpoint.Country.V1.URL_FILTER)
    public FmpPageResponse<Country> filter(@Valid @RequestBody CountryFilter filter) {
		return mapper.to(countryService.filter(filter));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.COUNTRY_INSERT})
	@PostMapping(Endpoint.Country.V1.URL_INSERT)
	public Country createCountry(@RequestBody @Valid CountryInsertSTO sto) {
		return this.countryService.insert(sto);
	}

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.COUNTRY_UPDATE})
	@PutMapping(Endpoint.Country.V1.URL_UPDATE)
	public Country updateCountry(@RequestBody @Valid CountryUpdateSTO sto) throws CountryNotFoundException {
    	return this.countryService.update(sto);
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.COUNTRY_DELETE})
	@DeleteMapping(Endpoint.Country.V1.URL_DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteCountryById(@RequestParam @NotBlank(message = Messages.COUNTRY_ID_NOT_NULL) BigInteger countryId) throws CountryNotFoundException {
		this.countryService.deleteById(countryId);
	}
}
