package com.akiratoriyama.gokufoodapi.controller.v1;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.akiratoriyama.gokufoodapi.controller.mapper.ResourceControllerMapper;
import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.infra.util.Const.Resources;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;
import com.akiratoriyama.gokufoodapi.model.Resource;
import com.akiratoriyama.gokufoodapi.repository.filter.ResourceFilter;
import com.akiratoriyama.gokufoodapi.repository.filter.ResourceNotContainedInUserFilter;
import com.akiratoriyama.gokufoodapi.service.ResourceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class ResourceController {
	
	@Autowired
	private ResourceService resourceService;
	
	@Autowired
	private ResourceControllerMapper mapper;

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.RESOURCE_FILTER})
	@PostMapping(Endpoint.Resource.V1.URL_FILTER)
    public FmpPageResponse<Resource> filter(@Valid @RequestBody ResourceFilter filter) {
		return mapper.to(resourceService.filter(filter));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.RESOURCE_FILTER_NOT_CONTAINED_IN_USER})
	@PostMapping(Endpoint.Resource.V1.URL_FILTER_NOT_CONTAINED_IN_USER)
	public FmpPageResponse<Resource> filter(@Valid @RequestBody ResourceNotContainedInUserFilter filter) {
		return mapper.to(resourceService.filterNotContainedInUser(filter));
	}
}
