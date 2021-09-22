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

import com.akiratoriyama.gokufoodapi.model.Country;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.CountryFilter;

@Repository
@Validated
public interface CountryRepository extends CrudRepository<Country, BigInteger> {

	@Query(QRY_FILTER)
	Slice<TotalRecordsHolder<Country>> filter(@Valid @NotNull CountryFilter filter, Pageable pag);
	
	Optional<Country> findByIdAndActive(@NotNull BigInteger id, @NotNull Boolean active);
	
	static final String QRY_FILTER = """
			select function('totalRecords') as totalRecords, cou as data
			  from Country cou
			where string_regex((cou.name, cou.isoCode), :#{#filter.quickSearch}) = true
			  and ( cou.id = :#{#filter.id} or coalesce(:#{#filter.id}, null) = null )
			  and cou.active = true
		""";
}
