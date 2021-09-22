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

import com.akiratoriyama.gokufoodapi.model.Address;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.AddressByZipCodeFilter;
import com.akiratoriyama.gokufoodapi.repository.filter.AddressFilter;

@Repository
@Validated
public interface AddressRepository extends CrudRepository<Address, BigInteger> {

	@Query(QRY_FILTER)
	Slice<TotalRecordsHolder<Address>> filter(@Valid @NotNull AddressFilter filter, Pageable pag);
	
	Optional<Address> findByIdAndActive(@NotNull BigInteger id, @NotNull Boolean active);
	
	@Query(QRY_FILTER_BY_ZIPCODE)
	Slice<TotalRecordsHolder<Address>> filterByZipCode(@Valid @NotNull AddressByZipCodeFilter filter, Pageable pag);
	
	static final String QRY_FILTER = """
			select function('totalRecords') as totalRecords, adr as data
			  from Address adr
			 inner join fetch adr.city cit
			 inner join fetch cit.state sta
			 inner join fetch sta.country cou
			where string_regex((adr.line1, adr.line2, adr.neighborhood, adr.zipCode), :#{#filter.quickSearch}) = true
			  and ( adr.id = :#{#filter.id}         or coalesce(:#{#filter.id}, null) = null )
			  and ( adr.cityId = :#{#filter.cityId} or coalesce(:#{#filter.cityId}, null) = null )
			  and adr.active = true
		""";
	
	static final String QRY_FILTER_BY_ZIPCODE = """
			Select function('totalRecords') as totalRecords, adr as data
			  from Address adr
			 inner join fetch adr.city cit
			 inner join fetch cit.state sta
			 inner join fetch sta.country cou
			 where adr.active = true
			   and string_regex((adr.zipCode), :#{#filter.zipCode}) = true
			""";
}
