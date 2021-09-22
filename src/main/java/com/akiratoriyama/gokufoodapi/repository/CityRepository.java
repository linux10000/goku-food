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

import com.akiratoriyama.gokufoodapi.model.City;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.CityFilter;

@Repository
@Validated
public interface CityRepository extends CrudRepository<City, BigInteger> {

	@Query(QRY_FILTER)
	Slice<TotalRecordsHolder<City>> filter(@Valid @NotNull CityFilter filter, Pageable pag);
	
	Optional<City> findByIdAndActive(@NotNull BigInteger id, @NotNull Boolean active);
	
	static final String QRY_FILTER = """
			select function('totalRecords') as totalRecords, cit as data
			  from City cit
			 inner join fetch cit.state sta
			 inner join fetch sta.country cou			  
			where string_regex((cit.name), :#{#filter.quickSearch}) = true
			  and ( cit.id = :#{#filter.id}           or coalesce(:#{#filter.id}, null) = null )
			  and ( cit.stateId = :#{#filter.stateId} or coalesce(:#{#filter.stateId}, null) = null )
			  and cit.active = true
		""";
}
