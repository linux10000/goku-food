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

import com.akiratoriyama.gokufoodapi.controller.mapper.StateControllerMapper;
import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.StateNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.infra.util.Const.Resources;
import com.akiratoriyama.gokufoodapi.model.State;
import com.akiratoriyama.gokufoodapi.repository.filter.StateFilter;
import com.akiratoriyama.gokufoodapi.service.StateService;
import com.akiratoriyama.gokufoodapi.service.to.StateInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.StateUpdateSTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class StateController {
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private StateControllerMapper mapper;

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.STATE_FILTER})
	@PostMapping(Endpoint.State.V1.URL_FILTER)
    public FmpPageResponse<State> filter(@Valid @RequestBody StateFilter filter) {
		return mapper.to(stateService.filter(filter));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.STATE_INSERT})
	@PostMapping(Endpoint.State.V1.URL_INSERT)
	public State createState(@RequestBody @Valid StateInsertSTO sto) {
		return this.stateService.insert(sto);
	}

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.STATE_UPDATE})
	@PutMapping(Endpoint.State.V1.URL_UPDATE)
	public State updateState(@RequestBody @Valid StateUpdateSTO sto) throws StateNotFoundException  {
    	return this.stateService.update(sto);
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.STATE_DELETE})
	@DeleteMapping(Endpoint.State.V1.URL_DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteStateById(@RequestParam @NotBlank(message = Messages.STATE_ID_NOT_NULL) BigInteger stateId) throws StateNotFoundException {
		this.stateService.deleteById(stateId);
	}
}
