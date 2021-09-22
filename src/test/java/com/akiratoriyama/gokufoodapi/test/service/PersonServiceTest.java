package com.akiratoriyama.gokufoodapi.test.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.akiratoriyama.gokufoodapi.enums.PersonLegalType;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.PersonNotFoundException;
import com.akiratoriyama.gokufoodapi.model.Person;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.PersonFilter;
import com.akiratoriyama.gokufoodapi.service.PersonService;
import com.akiratoriyama.gokufoodapi.service.impl.PersonServiceImpl;
import com.akiratoriyama.gokufoodapi.service.to.PersonInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.PersonUpdateSTO;
import com.akiratoriyama.gokufoodapi.test.infra.BaseTestConf;
import com.akiratoriyama.gokufoodapi.test.infra.TestDataJpa;

@ExtendWith(SpringExtension.class)
@TestDataJpa
class PersonServiceTest {
	
	@TestConfiguration
	static class PersonServiceTestConfiguration extends BaseTestConf {
		
		@Bean
		public PersonService personService() {
			return new PersonServiceImpl();
		}
	}

	
	@Autowired
	private PersonService personService;
	
	
	@Test
	void filterById() {
		Person pes = personService.insert(this.createPersonSTO());
		Slice<TotalRecordsHolder<Person>> slice = personService.filter(
				PersonFilter.builder()
				.id(pes.getId())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(pes.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	@Test
	void filterByFirstName() {
		Person adr = personService.insert(this.createPersonSTO());
		Slice<TotalRecordsHolder<Person>> slice = personService.filter(
				PersonFilter.builder()
				.quickSearch(adr.getFirstName())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(adr.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	@Test
	void insert() {
		PersonInsertSTO sto = this.createPersonSTO();
		
		Person pes = personService.insert(sto);
		
		assertNotEquals(null, pes);
		assertEquals(true, pes.getActive());
	}
	
	@Test
	void update() throws PersonNotFoundException {
		final String newFirstName = "Pelé";
		Person pes = personService.insert(this.createPersonSTO());
		
		pes = personService.update(
				PersonUpdateSTO.builder()
				.id(pes.getId())
				.firstName(newFirstName)
				.lastName(pes.getLastName())
				.legalType(pes.getLegalType())
				.ts(pes.getConvertedTs())
				.build()
				);
		
		assertNotEquals(null, pes);
		assertEquals(true, pes.getActive());
		assertEquals(newFirstName, pes.getFirstName());
	}

	@Test
	void delete() throws PersonNotFoundException {
		Person pes = personService.insert(this.createPersonSTO());
		
		personService.deleteById(pes.getId());
		
		assertEquals(0, personService.filter(
				                               PersonFilter.builder()
												.id(pes.getId())
												.build()
											)
											.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0)
					);
	}
	
	
	private PersonInsertSTO createPersonSTO() {
		return 
				PersonInsertSTO.builder()
				.firstName("Xuxa")
				.lastName("da Graça")
				.legalType(PersonLegalType.PHYSICAL)
				.user(false)
				.build();
	}
}
