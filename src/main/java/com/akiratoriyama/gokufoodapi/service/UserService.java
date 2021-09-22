package com.akiratoriyama.gokufoodapi.service;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Slice;

import com.akiratoriyama.gokufoodapi.infra.exception.AdminDisableForbiddenException;
import com.akiratoriyama.gokufoodapi.infra.exception.InvalidCredentialsException;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.UserNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.User;
import com.akiratoriyama.gokufoodapi.repository.filter.UserFilter;
import com.akiratoriyama.gokufoodapi.service.to.UserChangePasswordSTO;
import com.akiratoriyama.gokufoodapi.service.to.UserInsertSTO;

public interface UserService {

	Slice<TotalRecordsHolder<User>> filter(@Valid @NotNull UserFilter filter);

	void enableByLogin(@NotBlank(message = Messages.USER_LOGIN_NOT_NULL)  String login) throws UserNotFoundException;

	void disableByLogin(@NotBlank(message = Messages.USER_LOGIN_NOT_NULL) String login) throws UserNotFoundException, AdminDisableForbiddenException;

	void changePassword(@Valid @NotNull UserChangePasswordSTO sto) throws UserNotFoundException, InvalidCredentialsException;

	User insert(@Valid @NotNull UserInsertSTO sto);

	User findByLoginAndPassword(@NotBlank(message = Messages.USER_LOGIN_NOT_NULL) String login, @NotBlank(message = Messages.USER_PASSWORD_NOT_NULL) String password) throws UserNotFoundException;

	User findByRawToken(@NotBlank(message = Messages.USER_LOGIN_NOT_NULL) String rawToken) throws UserNotFoundException;

}
