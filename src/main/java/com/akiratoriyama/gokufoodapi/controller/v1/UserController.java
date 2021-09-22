package com.akiratoriyama.gokufoodapi.controller.v1;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.akiratoriyama.gokufoodapi.controller.mapper.UserControllerMapper;
import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.controller.response.UserFilterResponse;
import com.akiratoriyama.gokufoodapi.controller.response.UserResponse;
import com.akiratoriyama.gokufoodapi.infra.exception.AdminDisableForbiddenException;
import com.akiratoriyama.gokufoodapi.infra.exception.InvalidCredentialsException;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.UserNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.security.AuthUser;
import com.akiratoriyama.gokufoodapi.infra.util.Const.Resources;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.infra.util.UserUtil;
import com.akiratoriyama.gokufoodapi.repository.filter.UserFilter;
import com.akiratoriyama.gokufoodapi.service.UserService;
import com.akiratoriyama.gokufoodapi.service.to.UserChangePasswordSTO;
import com.akiratoriyama.gokufoodapi.service.to.UserInsertSTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserControllerMapper mapper;
	
	@Autowired
	private UserUtil userUtil;

	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@Secured({Resources.USER_FILTER})
	@PostMapping(Endpoint.User.V1.URL_FILTER)
	public FmpPageResponse<UserFilterResponse> withLeadersFilter(@Valid @RequestBody UserFilter filter) {
		return mapper.to(userService.filter(filter));
	}
	
	@Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
	@GetMapping(Endpoint.User.V1.URL_INFO)
	public UserResponse getInfo() throws BadCredentialsException, UserNotFoundException {
		return mapper.from(userService.findByRawToken(userUtil.getCurrent().map(AuthUser::getToken).orElseThrow(() -> new BadCredentialsException(""))));
	}
	
    @PostMapping(Endpoint.User.V1.URL_INSERT)
    public UserResponse insert(@Valid @RequestBody UserInsertSTO sto) {
    	return mapper.from(userService.insert(sto));
    }
    
    @Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
    @PutMapping(Endpoint.User.V1.URL_CHANGE_PASSWORD)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void changePassword(@RequestBody UserChangePasswordSTO sto) throws UserNotFoundException, InvalidCredentialsException {
    	sto.setAuthenticatedUser(userUtil.getCurrent().map(AuthUser::getLogin).orElse(null));
    	userService.changePassword(sto);
    }
    
    @Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
    @Secured({Resources.USER_ENABLE})
    @PutMapping(Endpoint.User.V1.URL_ENABLE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void enable(@RequestParam @NotBlank(message = Messages.USER_LOGIN_NOT_NULL) String login) throws UserNotFoundException {
    	userService.enableByLogin(login);
    }
    
    @Operation(security = @SecurityRequirement(name = Const.Misc.OPENAPI_AUTH))
    @Secured({Resources.USER_DISABLE})
    @PutMapping(Endpoint.User.V1.URL_DISABLE)
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void disable(@RequestParam @NotBlank(message = Messages.USER_LOGIN_NOT_NULL) String login) throws UserNotFoundException, AdminDisableForbiddenException {
    	userService.disableByLogin(login);
    }
}
