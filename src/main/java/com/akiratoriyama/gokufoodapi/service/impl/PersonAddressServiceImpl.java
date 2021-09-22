package com.akiratoriyama.gokufoodapi.service.impl;

import java.math.BigInteger;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.PersonAddressNotFoundException;
import com.akiratoriyama.gokufoodapi.model.City;
import com.akiratoriyama.gokufoodapi.model.Person;
import com.akiratoriyama.gokufoodapi.model.PersonAddress;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.PersonAddressRepository;
import com.akiratoriyama.gokufoodapi.repository.filter.PersonAddressFilter;
import com.akiratoriyama.gokufoodapi.service.PersonAddressService;
import com.akiratoriyama.gokufoodapi.service.to.PersonAddressInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.PersonAddressUpdateSTO;

@Service
public class PersonAddressServiceImpl implements PersonAddressService {
	
	@Autowired
	private PersonAddressRepository personAddressRepository;

	@Override
	public Slice<TotalRecordsHolder<PersonAddress>> filter(PersonAddressFilter filter) {
		return personAddressRepository.filter(
				filter,
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(),
						Direction.fromString(Objects.requireNonNullElse(filter.getSortDirection(), Sort.Direction.ASC.name())),
						Objects.requireNonNullElse(filter.getSortField(), PersonAddress.Fields.id)
						)
				);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public PersonAddress insert(PersonAddressInsertSTO sto) {
		return personAddressRepository.save(
					PersonAddress.builder()
					.line1(sto.getLine1())
					.line2(sto.getLine2())
					.neighborhood(sto.getNeighborhood())
					.zipCode(sto.getZipCode())
					.active(true)
					.city(new City(sto.getCityId()))
					.person(new Person(sto.getPersonId()))
					.type(sto.getType())
					.build()
				);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public PersonAddress update(PersonAddressUpdateSTO sto) throws PersonAddressNotFoundException {
		PersonAddress personAddress = personAddressRepository.findByIdAndActive(sto.getId(), true).orElseThrow(PersonAddressNotFoundException::new);
		
		personAddress.setLine1(sto.getLine1());
		personAddress.setLine2(sto.getLine2());
		personAddress.setNeighborhood(sto.getNeighborhood());
		personAddress.setZipCode(sto.getZipCode());
		personAddress.setConvertedTs(sto.getTs());
		personAddress.setCity(new City(sto.getCityId()));
		personAddress.setConvertedTs(sto.getTs());
		personAddress.setType(sto.getType());
		
		return personAddressRepository.save(personAddress);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void deleteById(BigInteger id) throws PersonAddressNotFoundException {
		PersonAddress personAddress = personAddressRepository.findByIdAndActive(id, true).orElseThrow(PersonAddressNotFoundException::new);
		
		personAddress.setActive(false);
		
		personAddressRepository.save(personAddress);
	}
}
