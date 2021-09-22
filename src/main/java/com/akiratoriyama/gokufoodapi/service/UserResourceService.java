package com.akiratoriyama.gokufoodapi.service;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Slice;

import com.akiratoriyama.gokufoodapi.infra.exception.UserResourceUniqueViolationException;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.UserResourceNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.UserResource;
import com.akiratoriyama.gokufoodapi.repository.filter.UserResourceFilter;
import com.akiratoriyama.gokufoodapi.service.to.UserResourceInsertSTO;

public interface UserResourceService {

	Slice<TotalRecordsHolder<UserResource>> filter(@Valid @NotNull UserResourceFilter filter);

	UserResource insert(@Valid @NotNull UserResourceInsertSTO sto) throws UserResourceUniqueViolationException;

	void deleteById(@Valid @NotNull(message = Messages.USERRESOURCE_ID_NOT_NULL) BigInteger id) throws UserResourceNotFoundException;

	List<UserResource> findByUserId(@NotNull(message = Messages.USER_ID_NOT_NULL) BigInteger userId);

}
