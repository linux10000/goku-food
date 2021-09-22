package com.akiratoriyama.gokufoodapi.controller.v1;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.akiratoriyama.gokufoodapi.controller.mapper.UserResourceControllerMapper;
import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.controller.response.UserResourceResponse;
import com.akiratoriyama.gokufoodapi.infra.exception.UserResourceUniqueViolationException;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.UserResourceNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.infra.util.Const.Resources;
import com.akiratoriyama.gokufoodapi.repository.filter.UserResourceFilter;
import com.akiratoriyama.gokufoodapi.service.UserResourceService;
import com.akiratoriyama.gokufoodapi.service.to.UserResourceInsertSTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class UserResourceController {
	
	@Autowired
	private UserResourceService userResourceService;
	
	@Autowired
	private UserResourceControllerMapper mapper;

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.USERRESOURCE_FILTER})
	@PostMapping(Endpoint.UserResource.V1.URL_FILTER)
    public FmpPageResponse<UserResourceResponse> filter(@Valid @RequestBody UserResourceFilter filter) {
		return mapper.to(userResourceService.filter(filter));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.USERRESOURCE_INSERT})
	@PostMapping(Endpoint.UserResource.V1.URL_INSERT)
	public UserResourceResponse create(@RequestBody @Valid UserResourceInsertSTO sto) throws UserResourceUniqueViolationException {
		return mapper.to(this.userResourceService.insert(sto));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.USERRESOURCE_DELETE})
	@DeleteMapping(Endpoint.UserResource.V1.URL_DELETE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteById(@RequestParam @NotBlank(message = Messages.USERRESOURCE_ID_NOT_NULL) BigInteger userResourceId) throws UserResourceNotFoundException {
		this.userResourceService.deleteById(userResourceId);
	}
}
