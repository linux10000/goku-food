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

import com.akiratoriyama.gokufoodapi.controller.mapper.CityControllerMapper;
import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.CityNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.infra.util.Const.Resources;
import com.akiratoriyama.gokufoodapi.model.City;
import com.akiratoriyama.gokufoodapi.repository.filter.CityFilter;
import com.akiratoriyama.gokufoodapi.service.CityService;
import com.akiratoriyama.gokufoodapi.service.to.CityInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.CityUpdateSTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class CityController {
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private CityControllerMapper mapper;

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.CITY_FILTER})
	@PostMapping(Endpoint.City.V1.URL_FILTER)
    public FmpPageResponse<City> filter(@Valid @RequestBody CityFilter filter) {
		return mapper.to(cityService.filter(filter));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.CITY_INSERT})
	@PostMapping(Endpoint.City.V1.URL_INSERT)
	public City createCity(@RequestBody @Valid CityInsertSTO sto) {
		return this.cityService.insert(sto);
	}

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.CITY_UPDATE})
	@PutMapping(Endpoint.City.V1.URL_UPDATE)
	public City updateCity(@RequestBody @Valid CityUpdateSTO sto) throws CityNotFoundException {
    	return this.cityService.update(sto);
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.CITY_DELETE})
	@DeleteMapping(Endpoint.City.V1.URL_DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteCityById(@RequestParam @NotBlank(message = Messages.CITY_ID_NOT_NULL) BigInteger cityId) throws CityNotFoundException {
		this.cityService.deleteById(cityId);
	}
}
