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

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.AddressNotFoundException;
import com.akiratoriyama.gokufoodapi.model.Address;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.AddressByZipCodeFilter;
import com.akiratoriyama.gokufoodapi.repository.filter.AddressFilter;
import com.akiratoriyama.gokufoodapi.service.AddressService;
import com.akiratoriyama.gokufoodapi.service.CityService;
import com.akiratoriyama.gokufoodapi.service.CountryService;
import com.akiratoriyama.gokufoodapi.service.StateService;
import com.akiratoriyama.gokufoodapi.service.impl.AddressServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.CityServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.CountryServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.StateServiceImpl;
import com.akiratoriyama.gokufoodapi.service.to.AddressInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.AddressUpdateSTO;
import com.akiratoriyama.gokufoodapi.service.to.CityInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.CountryInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.StateInsertSTO;
import com.akiratoriyama.gokufoodapi.test.infra.BaseTestConf;
import com.akiratoriyama.gokufoodapi.test.infra.TestDataJpa;

@ExtendWith(SpringExtension.class)
@TestDataJpa
class AddressServiceTest {
	
	@TestConfiguration
	static class AddressServiceTestConfiguration extends BaseTestConf {
		
		@Bean
		public AddressService addressService() {
			return new AddressServiceImpl();
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
	}

	
	@Autowired
	private AddressService addressService;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private CountryService countryService;
	
	
	@PostConstruct
	public void init() {
		this.countryService.insert(this.createCountrySTO());
		this.stateService.insert(this.createStateSTO());
		this.cityService.insert(this.createCitySTO());
	}
	
	@Test
	void filterById() {
		Address adr = addressService.insert(this.createAddressSTO());
		Slice<TotalRecordsHolder<Address>> slice = addressService.filter(
				AddressFilter.builder()
				.id(adr.getId())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(adr.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	@Test
	void filterByStreet() {
		Address adr = addressService.insert(this.createAddressSTO());
		Slice<TotalRecordsHolder<Address>> slice = addressService.filter(
				AddressFilter.builder()
				.quickSearch(adr.getLine1())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(adr.getId(), slice.stream().findFirst().get().getData().getId());
	}

	@Test
	void filterByZipCode() {
		Address adr = addressService.insert(this.createAddressSTO());
		Slice<TotalRecordsHolder<Address>> slice = addressService.filterByCachedZipCode(
				AddressByZipCodeFilter.builder()
				.zipCode(adr.getZipCode())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(adr.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	@Test
	void filterByCityId() {
		Address adr = addressService.insert(this.createAddressSTO());
		Slice<TotalRecordsHolder<Address>> slice = addressService.filter(
				AddressFilter.builder()
				.cityId(BigInteger.ONE)
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(adr.getCityId(), slice.stream().findFirst().get().getData().getCityId());
	}
	
	@Test
	void insert() {
		AddressInsertSTO sto = this.createAddressSTO();
		
		Address adr = addressService.insert(sto);
		
		assertNotEquals(null, adr);
		assertEquals(true, adr.getActive());
	}
	
	@Test
	void update() throws AddressNotFoundException {
		final String newStreetName = "Rua da esquerda e direita 80";
		Address adr = addressService.insert(this.createAddressSTO());
		
		adr = addressService.update(
				AddressUpdateSTO.builder()
				.id(adr.getId())
				.line1(newStreetName)
				.cityId(BigInteger.ONE)
				.ts(adr.getTs())
				.build()
				);
		
		assertNotEquals(null, adr);
		assertEquals(true, adr.getActive());
		assertEquals(newStreetName, adr.getLine1());
	}

	@Test
	void delete() throws AddressNotFoundException {
		Address adr = addressService.insert(this.createAddressSTO());
		
		addressService.deleteById(adr.getId());
		
		assertEquals(0, addressService.filter(
				                                AddressFilter.builder()
												.id(adr.getId())
												.build()
											)
											.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0)
					);
	}
	
	private AddressInsertSTO createAddressSTO() {
		return 
				AddressInsertSTO.builder()
				.line1("Rua sobe e desce 123")
				.line2("Sem mais")
				.neighborhood("Meu Bairro")
				.zipCode("6546545")
				.cityId(BigInteger.ONE)
				.build();
	}
	
	private CityInsertSTO createCitySTO() {
		return 
				CityInsertSTO.builder()
				.name("Asdasdasdas")
				.stateId(BigInteger.ONE)
				.build();
	}
	
	private StateInsertSTO createStateSTO() {
		return 
				StateInsertSTO.builder()
				.name("SSAaasoos")
				.countryId(BigInteger.ONE)
				.build();
	}
	
	private CountryInsertSTO createCountrySTO() {
		return 
				CountryInsertSTO.builder()
				.name("Brrsdfsdfsd")
				.isoCode("BP")
				.build();
	}
}
