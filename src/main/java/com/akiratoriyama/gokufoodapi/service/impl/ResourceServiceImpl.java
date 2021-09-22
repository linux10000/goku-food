package com.akiratoriyama.gokufoodapi.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.akiratoriyama.gokufoodapi.model.Resource;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.ResourceRepository;
import com.akiratoriyama.gokufoodapi.repository.filter.ResourceFilter;
import com.akiratoriyama.gokufoodapi.repository.filter.ResourceNotContainedInUserFilter;
import com.akiratoriyama.gokufoodapi.service.ResourceService;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepository resourceRepository;
	
	@Override
	public Slice<TotalRecordsHolder<Resource>> filter(ResourceFilter filter) {
		return resourceRepository.filter(
				filter,
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(),
						Direction.fromString(Objects.requireNonNullElse(filter.getSortDirection(), Sort.Direction.ASC.name())),
						Objects.requireNonNullElse(filter.getSortField(), Resource.Fields.id)
						)
				);
	}
	
	@Override
	public Slice<TotalRecordsHolder<Resource>> filterNotContainedInUser(ResourceNotContainedInUserFilter filter) {
		return resourceRepository.filterNotContainedInUser(
				filter,
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(),
						Direction.fromString(Objects.requireNonNullElse(filter.getSortDirection(), Sort.Direction.ASC.name())),
						Objects.requireNonNullElse(filter.getSortField(), Resource.Fields.id)
						)
				);
	}
}
