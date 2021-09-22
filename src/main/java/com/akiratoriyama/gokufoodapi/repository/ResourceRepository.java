package com.akiratoriyama.gokufoodapi.repository;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import com.akiratoriyama.gokufoodapi.model.Resource;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.UserResource;
import com.akiratoriyama.gokufoodapi.repository.filter.ResourceFilter;
import com.akiratoriyama.gokufoodapi.repository.filter.ResourceNotContainedInUserFilter;

@Repository
@Validated
public interface ResourceRepository extends CrudRepository<UserResource, BigInteger> {

	@Query(QRY_FILTER)
	Slice<TotalRecordsHolder<Resource>> filter(@Valid @NotNull ResourceFilter filter, Pageable pag);
	
	@Query(QRY_FILTER_NOT_CONTAINED_IN_USER)
	Slice<TotalRecordsHolder<Resource>> filterNotContainedInUser(@Valid @NotNull ResourceNotContainedInUserFilter filter, Pageable pag);
	
	
	static final String QRY_FILTER = """
			select function('totalRecords') as totalRecords, rsc as data
			  from Resource rsc
			where string_regex((rsc.description), :#{#filter.quickSearch}) = true
			  and ( rsc.id = :#{#filter.id} or coalesce(:#{#filter.id}, null) = null )
			  and rsc.active = true
		""";
	
	static final String QRY_FILTER_NOT_CONTAINED_IN_USER = """
			select function('totalRecords') as totalRecords, rsc as data
			from Resource rsc
			where string_regex((rsc.description), :#{#filter.quickSearch}) = true
			and ( rsc.id = :#{#filter.id} or coalesce(:#{#filter.id}, null) = null )
			and rsc.active = true
			and not exists (
					Select uce
					  from UserResource uce
					 where uce.active = true
					   and uce.userId = coalesce(:#{#filter.userId}, null)
					   and uce.resourceId = rsc.id
			 ) 
			""";
}
