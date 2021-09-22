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

import com.akiratoriyama.gokufoodapi.controller.mapper.PersonControllerMapper;
import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.controller.response.PersonResponse;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.PersonNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.infra.util.Const.Resources;
import com.akiratoriyama.gokufoodapi.repository.filter.PersonFilter;
import com.akiratoriyama.gokufoodapi.service.PersonService;
import com.akiratoriyama.gokufoodapi.service.to.PersonInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.PersonUpdateSTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private PersonControllerMapper mapper;

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.PERSON_FILTER})
	@PostMapping(Endpoint.Person.V1.URL_FILTER)
    public FmpPageResponse<PersonResponse> filter(@Valid @RequestBody PersonFilter filter) {
		return mapper.to(personService.filter(filter));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.PERSON_INSERT})
	@PostMapping(Endpoint.Person.V1.URL_INSERT)
	public PersonResponse createPerson(@RequestBody @Valid PersonInsertSTO sto) {
		return this.mapper.to(this.personService.insert(sto));
	}

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.PERSON_UPDATE})
	@PutMapping(Endpoint.Person.V1.URL_UPDATE)
	public PersonResponse updatePerson(@RequestBody @Valid PersonUpdateSTO sto) throws PersonNotFoundException {
    	return this.mapper.to(this.personService.update(sto));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.PERSON_DELETE})
	@DeleteMapping(Endpoint.Person.V1.URL_DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deletePersonById(@RequestParam @NotBlank(message = Messages.PERSON_ID_NOT_NULL) BigInteger personId) throws PersonNotFoundException {
		this.personService.deleteById(personId);
	}
}
