package com.akiratoriyama.gokufoodapi.service;

import org.springframework.data.domain.Slice;

import com.akiratoriyama.gokufoodapi.model.Resource;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.ResourceFilter;
import com.akiratoriyama.gokufoodapi.repository.filter.ResourceNotContainedInUserFilter;

public interface ResourceService {

	Slice<TotalRecordsHolder<Resource>> filter(ResourceFilter filter);

	Slice<TotalRecordsHolder<Resource>> filterNotContainedInUser(ResourceNotContainedInUserFilter filter);

}
