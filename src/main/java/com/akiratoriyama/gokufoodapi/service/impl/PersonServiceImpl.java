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

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.PersonNotFoundException;
import com.akiratoriyama.gokufoodapi.model.Person;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.PersonRepository;
import com.akiratoriyama.gokufoodapi.repository.filter.PersonFilter;
import com.akiratoriyama.gokufoodapi.service.PersonService;
import com.akiratoriyama.gokufoodapi.service.to.PersonInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.PersonUpdateSTO;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public Slice<TotalRecordsHolder<Person>> filter(PersonFilter filter) {
		return personRepository.filter(
				filter,
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(),
						Direction.fromString(Objects.requireNonNullElse(filter.getSortDirection(), Sort.Direction.ASC.name())),
						Objects.requireNonNullElse(filter.getSortField(), Person.Fields.id)
						)
				);
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Person insert(Person pes) {
		pes.setActive(true);
		pes.setId(null);
		
		return personRepository.save(pes);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Person insert(PersonInsertSTO sto) {
		return personRepository.save(
					Person.builder()
					.firstName(sto.getFirstName())
					.lastName(sto.getLastName())
					.legalType(sto.getLegalType())
					.user(false)
					.active(true)
					.build()
				);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Person update(PersonUpdateSTO sto) throws PersonNotFoundException {
		Person person = personRepository.findByIdAndActive(sto.getId(), true).orElseThrow(PersonNotFoundException::new);
		
		person.setFirstName(sto.getFirstName());
		person.setLastName(sto.getLastName());
		person.setLegalType(sto.getLegalType());
		person.setConvertedTs(sto.getTs());
		
		return personRepository.save(person);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void deleteById(BigInteger id) throws PersonNotFoundException {
		Person person = personRepository.findByIdAndActive(id, true).orElseThrow(PersonNotFoundException::new);
		
		person.setActive(false);
		
		personRepository.save(person);
	}
}
