package com.akiratoriyama.gokufoodapi.repository;

import java.math.BigInteger;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import com.akiratoriyama.gokufoodapi.model.State;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.StateFilter;

@Repository
@Validated
public interface StateRepository extends CrudRepository<State, BigInteger> {

	@Query(QRY_FILTER)
	Slice<TotalRecordsHolder<State>> filter(@Valid @NotNull StateFilter filter, Pageable pag);
	
	Optional<State> findByIdAndActive(@NotNull BigInteger id, @NotNull Boolean active);
	
	static final String QRY_FILTER = """
			select function('totalRecords') as totalRecords, sta as data
			  from State sta
			 inner join fetch sta.country cou			  
			where string_regex((sta.name), :#{#filter.quickSearch}) = true
			  and ( sta.id = :#{#filter.id}               or coalesce(:#{#filter.id}, null) = null )
			  and ( sta.countryId = :#{#filter.countryId} or coalesce(:#{#filter.countryId}, null) = null )
			  and sta.active = true
		""";
}
