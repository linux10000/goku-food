package com.akiratoriyama.gokufoodapi.test.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.akiratoriyama.gokufoodapi.enums.AddressType;
import com.akiratoriyama.gokufoodapi.enums.PersonLegalType;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.PersonAddressNotFoundException;
import com.akiratoriyama.gokufoodapi.model.PersonAddress;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.PersonAddressFilter;
import com.akiratoriyama.gokufoodapi.service.CityService;
import com.akiratoriyama.gokufoodapi.service.CountryService;
import com.akiratoriyama.gokufoodapi.service.PersonAddressService;
import com.akiratoriyama.gokufoodapi.service.PersonService;
import com.akiratoriyama.gokufoodapi.service.StateService;
import com.akiratoriyama.gokufoodapi.service.impl.CityServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.CountryServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.PersonAddressServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.PersonServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.StateServiceImpl;
import com.akiratoriyama.gokufoodapi.service.to.CityInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.CountryInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.PersonAddressInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.PersonAddressUpdateSTO;
import com.akiratoriyama.gokufoodapi.service.to.PersonInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.StateInsertSTO;
import com.akiratoriyama.gokufoodapi.test.infra.BaseTestConf;
import com.akiratoriyama.gokufoodapi.test.infra.TestDataJpa;

@ExtendWith(SpringExtension.class)
@TestDataJpa
class PersonAddressServiceTest {
	
	@TestConfiguration
	static class PersonAddressServiceTestConfiguration extends BaseTestConf {
			
		@Bean
		public PersonAddressService personAddressService() {
			return new PersonAddressServiceImpl();
		}
		
		@Bean
		public CityService cityService() {
			return new CityServiceImpl();
		}
		
		@Bean
		public StateService stateService() {
			return new StateServiceImpl();
		}
		
		@Bean
		public CountryService countryService() {
			return new CountryServiceImpl();
		}
		
		@Bean
		public PersonService personService() {
			return new PersonServiceImpl();
		}
	}

	
	@Autowired
	private PersonAddressService personAddressService;
	
	@Autowired
	private CityService cityService;

	@Autowired
	private StateService stateService;
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private PersonService personService;
	
	
	@PostConstruct
	public void init() {
		this.countryService.insert(this.createCountrySTO());
		this.stateService.insert(this.createStateSTO());
		this.cityService.insert(this.createCitySTO());
		this.personService.insert(this.createPersonSTO());
	}
	
	@Test
	void filterById() {
		PersonAddress ped = personAddressService.insert(this.createPersonAddressSTO());
		Slice<TotalRecordsHolder<PersonAddress>> slice = personAddressService.filter(
				PersonAddressFilter.builder()
				.id(ped.getId())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(ped.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	@Test
	void filterByStreet() {
		PersonAddress ped = personAddressService.insert(this.createPersonAddressSTO());
		Slice<TotalRecordsHolder<PersonAddress>> slice = personAddressService.filter(
				PersonAddressFilter.builder()
				.quickSearch(ped.getLine1())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(ped.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	@Test
	void insert() {
		PersonAddressInsertSTO sto = this.createPersonAddressSTO();
		
		PersonAddress ped = personAddressService.insert(sto);
		
		assertNotEquals(null, ped);
		assertEquals(true, ped.getActive());
	}
	
	@Test
	void update() throws PersonAddressNotFoundException {
		final String newStreetName = "Rua da esquerda e direita 80";
		PersonAddress ped = personAddressService.insert(this.createPersonAddressSTO());
		
		ped = personAddressService.update(
				PersonAddressUpdateSTO.builder()
				.id(ped.getId())
				.line1(newStreetName)
				.cityId(BigInteger.ONE)
				.cityId(BigInteger.ONE)
				.ts(ped.getConvertedTs())
				.build()
				);
		
		assertNotEquals(null, ped);
		assertEquals(true, ped.getActive());
		assertEquals(newStreetName, ped.getLine1());
	}

	@Test
	void delete() throws PersonAddressNotFoundException {
		PersonAddress sta = personAddressService.insert(this.createPersonAddressSTO());
		
		personAddressService.deleteById(sta.getId());
		
		assertEquals(0, personAddressService.filter(
				                               PersonAddressFilter.builder()
												.id(sta.getId())
												.build()
											)
											.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0)
					);
	}
	
	private PersonAddressInsertSTO createPersonAddressSTO() {
		return 
				PersonAddressInsertSTO.builder()
				.line1("Rua sobe e desce 123")
				.line2("Sem mais")
				.neighborhood("Meu Bairro")
				.zipCode("11070020")
				.cityId(BigInteger.ONE)
				.personId(BigInteger.ONE)
				.type(AddressType.DELIVERY)
				.build();
	}
	
	private CityInsertSTO createCitySTO() {
		return 
				CityInsertSTO.builder()
				.name("Santos")
				.stateId(BigInteger.ONE)
				.build();
	}
	
	private StateInsertSTO createStateSTO() {
		return 
				StateInsertSTO.builder()
				.name("Sao Paulo")
				.countryId(BigInteger.ONE)
				.build();
	}
	
	private CountryInsertSTO createCountrySTO() {
		return 
				CountryInsertSTO.builder()
				.name("Brazil")
				.isoCode("BR")
				.build();
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
