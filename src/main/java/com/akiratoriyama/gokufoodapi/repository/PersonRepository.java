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

import com.akiratoriyama.gokufoodapi.model.Person;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.PersonFilter;

@Repository
public interface PersonRepository extends CrudRepository<Person, BigInteger> {

	@Query(QRY_FILTER)
	Slice<TotalRecordsHolder<Person>> filter(@Valid @NotNull PersonFilter filter, Pageable pag);
	
	Optional<Person> findByIdAndActive(@NotNull BigInteger id, @NotNull Boolean active);
	
	static final String QRY_FILTER = """
			select function('totalRecords') as totalRecords, pes as data
			  from Person pes
			where string_regex((pes.firstName, pes.lastName), :#{#filter.quickSearch}) = true
			  and ( pes.id = :#{#filter.id}               or coalesce(:#{#filter.id}, null) = null )
			  and ( pes.user = :#{#filter.user}           or coalesce(:#{#filter.user}, null) = null )
			  and ( pes.legalType = :#{#filter.legalType} or coalesce(:#{#filter.legalType}, null) = null )
			  and pes.active = true
		""";
}
