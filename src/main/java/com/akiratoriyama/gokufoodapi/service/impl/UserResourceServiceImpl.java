package com.akiratoriyama.gokufoodapi.service.impl;

import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.akiratoriyama.gokufoodapi.infra.exception.UserResourceUniqueViolationException;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.UserResourceNotFoundException;
import com.akiratoriyama.gokufoodapi.model.Resource;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.User;
import com.akiratoriyama.gokufoodapi.model.UserResource;
import com.akiratoriyama.gokufoodapi.repository.UserResourceRepository;
import com.akiratoriyama.gokufoodapi.repository.filter.UserResourceFilter;
import com.akiratoriyama.gokufoodapi.service.UserResourceService;
import com.akiratoriyama.gokufoodapi.service.to.UserResourceInsertSTO;

@Service
public class UserResourceServiceImpl implements UserResourceService {
	
	@Autowired
	private UserResourceRepository userResourceRepository;

	@Override
	public Slice<TotalRecordsHolder<UserResource>> filter(UserResourceFilter filter) {
		return userResourceRepository.filter(
				filter,
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(),
						Direction.fromString(Objects.requireNonNullElse(filter.getSortDirection(), Sort.Direction.ASC.name())),
						Objects.requireNonNullElse(filter.getSortField(), UserResource.Fields.id)
						)
				);
	}
	
	@Override
	public List<UserResource> findByUserId(@NotNull BigInteger userId) {
		return this.userResourceRepository.findByUserIdAndActive(userId, true);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public UserResource insert(UserResourceInsertSTO sto) throws UserResourceUniqueViolationException {
		if ( userResourceRepository.findByUserIdAndResourceIdAndActive(sto.getUserId(), sto.getResourceId(), true).isPresent() )
			throw new UserResourceUniqueViolationException();
		
		return userResourceRepository.save(
                    UserResource.builder()
                    .resource(new Resource(sto.getResourceId()))
                    .user(new User(sto.getUserId()))
					.active(true)
					.build()
				);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void deleteById(BigInteger id) throws UserResourceNotFoundException {
		UserResource userResource = userResourceRepository.findByIdAndActive(id, true).orElseThrow(UserResourceNotFoundException::new);
		
		userResource.setActive(false);
		
		userResourceRepository.save(userResource);
	}
}
