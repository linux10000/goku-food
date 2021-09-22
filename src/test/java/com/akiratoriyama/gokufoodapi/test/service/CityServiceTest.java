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

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.CityNotFoundException;
import com.akiratoriyama.gokufoodapi.model.City;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.CityFilter;
import com.akiratoriyama.gokufoodapi.service.CityService;
import com.akiratoriyama.gokufoodapi.service.CountryService;
import com.akiratoriyama.gokufoodapi.service.StateService;
import com.akiratoriyama.gokufoodapi.service.impl.CityServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.CountryServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.StateServiceImpl;
import com.akiratoriyama.gokufoodapi.service.to.CityInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.CityUpdateSTO;
import com.akiratoriyama.gokufoodapi.service.to.CountryInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.StateInsertSTO;
import com.akiratoriyama.gokufoodapi.test.infra.BaseTestConf;
import com.akiratoriyama.gokufoodapi.test.infra.TestDataJpa;

@ExtendWith(SpringExtension.class)
@TestDataJpa
class CityServiceTest {
	
	@TestConfiguration
	static class CityServiceTestConfiguration extends BaseTestConf {
		
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
	private CityService cityService;
	
	@Autowired
	private StateService stateService;
	
	@Autowired
	private CountryService countryService;
	
	
	@PostConstruct
	public void init() {
		this.countryService.insert(this.createCountrySTO());
		this.stateService.insert(this.createStateSTO());
	}
	
	@Test
	void filterById() {
		City cit = cityService.insert(this.createCitySTO());
		Slice<TotalRecordsHolder<City>> slice = cityService.filter(
				CityFilter.builder()
				.id(cit.getId())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(cit.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	@Test
	void filterByName() {
		City cit = cityService.insert(this.createCitySTO());
		Slice<TotalRecordsHolder<City>> slice = cityService.filter(
				CityFilter.builder()
				.quickSearch(cit.getName())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(cit.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	
	@Test
	void filterByStateId() {
		City cit = cityService.insert(this.createCitySTO());
		Slice<TotalRecordsHolder<City>> slice = cityService.filter(
				CityFilter.builder()
				.stateId(BigInteger.ONE)
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(cit.getStateId(), slice.stream().findFirst().get().getData().getStateId());
	}
	
	@Test
	void insert() {
		CityInsertSTO sto = this.createCitySTO();
		
		City cit = cityService.insert(sto);
		
		assertNotEquals(null, cit);
		assertEquals(true, cit.getActive());
	}
	
	@Test
	void update() throws CityNotFoundException {
		final String newName = "Cubatao";
		City cit = cityService.insert(this.createCitySTO());
		
		cit = cityService.update(
				CityUpdateSTO.builder()
				.id(cit.getId())
				.name(newName)
				.stateId(BigInteger.ONE)
				.ts(cit.getTs())
				.build()
				);
		
		assertNotEquals(null, cit);
		assertEquals(true, cit.getActive());
		assertEquals(newName, cit.getName());
	}

	@Test
	void delete() throws CityNotFoundException {
		City sta = cityService.insert(this.createCitySTO());
		
		cityService.deleteById(sta.getId());
		
		assertEquals(0, cityService.filter(
                                				CityFilter.builder()
												.id(sta.getId())
												.build()
											)
											.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0)
					);
	}
	
	private CityInsertSTO createCitySTO() {
		return 
				CityInsertSTO.builder()
				.name("Axxxasdds")
				.stateId(BigInteger.ONE)
				.build();
	}
	
	private StateInsertSTO createStateSTO() {
		return 
				StateInsertSTO.builder()
				.name("SSassoosdsddss")
				.countryId(BigInteger.ONE)
				.build();
	}
	
	private CountryInsertSTO createCountrySTO() {
		return 
				CountryInsertSTO.builder()
				.name("Brasdasdas")
				.isoCode("BZ")
				.build();
	}
}
