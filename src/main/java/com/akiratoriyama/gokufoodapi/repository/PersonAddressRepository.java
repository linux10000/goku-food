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

import com.akiratoriyama.gokufoodapi.model.PersonAddress;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.PersonAddressFilter;

@Repository
@Validated
public interface PersonAddressRepository extends CrudRepository<PersonAddress, BigInteger> {

	@Query(QRY_FILTER)
	Slice<TotalRecordsHolder<PersonAddress>> filter(@Valid @NotNull PersonAddressFilter filter, Pageable pag);
	
	Optional<PersonAddress> findByIdAndActive(@NotNull BigInteger id, @NotNull Boolean active);
	
	static final String QRY_FILTER = """
			select function('totalRecords') as totalRecords, ped as data
			  from PersonAddress ped
			 inner join fetch ped.person pes
			 inner join fetch ped.city cit
			 inner join fetch cit.state sta
			 inner join fetch sta.country cou			
			where string_regex((ped.line1, ped.line2, ped.neighborhood, ped.zipCode), :#{#filter.quickSearch}) = true
			  and ( ped.id = :#{#filter.id}             or coalesce(:#{#filter.id}, null) = null )
			  and ( ped.cityId = :#{#filter.cityId}     or coalesce(:#{#filter.cityId}, null) = null )
			  and ( ped.personId = :#{#filter.personId} or coalesce(:#{#filter.personId}, null) = null )
			  and ped.active = true
		""";
}
