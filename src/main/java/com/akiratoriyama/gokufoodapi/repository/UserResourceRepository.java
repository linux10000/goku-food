package com.akiratoriyama.gokufoodapi.repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.UserResource;
import com.akiratoriyama.gokufoodapi.repository.filter.UserResourceFilter;

@Repository
@Validated
public interface UserResourceRepository extends CrudRepository<UserResource, BigInteger> {

	@Query(QRY_FILTER)
	Slice<TotalRecordsHolder<UserResource>> filter(@Valid @NotNull UserResourceFilter filter, Pageable pag);
	
	Optional<UserResource> findByUserIdAndResourceIdAndActive(@NotNull BigInteger userId, @NotNull BigInteger resourceId, @NotNull Boolean active);

	Optional<UserResource> findByIdAndActive(@NotNull BigInteger id, @NotNull Boolean active);

	List<UserResource> findByUserIdAndActive(@NotNull BigInteger userId, @NotNull Boolean active);
	
	static final String QRY_FILTER = """
			select function('totalRecords') as totalRecords, uce as data
			  from UserResource uce
			 inner join fetch uce.resource rsc
			 inner join fetch uce.user usr			  
			 inner join fetch usr.person pes			  
			where string_regex((rsc.description), :#{#filter.quickSearch}) = true
			  and ( uce.userId = :#{#filter.userId}         or coalesce(:#{#filter.userId}, null) = null )
			  and ( uce.resourceId = :#{#filter.resourceId} or coalesce(:#{#filter.resourceId}, null) = null )
			  and uce.active = true
		""";
}
