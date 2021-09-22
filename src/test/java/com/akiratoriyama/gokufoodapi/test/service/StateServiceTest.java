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

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.StateNotFoundException;
import com.akiratoriyama.gokufoodapi.model.State;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.StateFilter;
import com.akiratoriyama.gokufoodapi.service.CountryService;
import com.akiratoriyama.gokufoodapi.service.StateService;
import com.akiratoriyama.gokufoodapi.service.impl.CountryServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.StateServiceImpl;
import com.akiratoriyama.gokufoodapi.service.to.CountryInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.StateInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.StateUpdateSTO;
import com.akiratoriyama.gokufoodapi.test.infra.BaseTestConf;
import com.akiratoriyama.gokufoodapi.test.infra.TestDataJpa;

@ExtendWith(SpringExtension.class)
@TestDataJpa
class StateServiceTest {
	
	@TestConfiguration
	static class StateServiceTestConfiguration extends BaseTestConf {
		
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
	private StateService stateService;
	
	@Autowired
	private CountryService countryService;
	
	
	@PostConstruct
	public void init() {
		this.countryService.insert(this.createCountrySTO());
	}
	
	@Test
	void filterById() {
		State sta = stateService.insert(this.createStateSTO());
		Slice<TotalRecordsHolder<State>> slice = stateService.filter(
				StateFilter.builder()
				.id(sta.getId())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(sta.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	@Test
	void filterByName() {
		State sta = stateService.insert(this.createStateSTO());
		Slice<TotalRecordsHolder<State>> slice = stateService.filter(
				StateFilter.builder()
				.quickSearch(sta.getName())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(sta.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	
	@Test
	void filterByCountryId() {
		State cou = stateService.insert(this.createStateSTO());
		Slice<TotalRecordsHolder<State>> slice = stateService.filter(
				StateFilter.builder()
				.countryId(BigInteger.ONE)
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(cou.getCountryId(), slice.stream().findFirst().get().getData().getCountryId());
	}
	
	@Test
	void insert() {
		StateInsertSTO sto = this.createStateSTO();
		
		State sta = stateService.insert(sto);
		
		assertNotEquals(null, sta);
		assertEquals(true, sta.getActive());
	}
	
	@Test
	void update() throws StateNotFoundException {
		final String newName = "Alagoas";
		State sta = stateService.insert(this.createStateSTO());
		
		sta = stateService.update(
				StateUpdateSTO.builder()
				.id(sta.getId())
				.name(newName)
				.countryId(BigInteger.ZERO)
				.ts(sta.getTs())
				.build()
				);
		
		assertNotEquals(null, sta);
		assertEquals(true, sta.getActive());
		assertEquals(newName, sta.getName());
	}

	@Test
	void delete() throws StateNotFoundException {
		State sta = stateService.insert(this.createStateSTO());
		
		stateService.deleteById(sta.getId());
		
		assertEquals(0, stateService.filter(
												StateFilter.builder()
												.id(sta.getId())
												.build()
											)
											.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0)
					);
	}
	
	private StateInsertSTO createStateSTO() {
		return 
				StateInsertSTO.builder()
				.name("ZZZZzdfsdf")
				.countryId(BigInteger.ONE)
				.build();
	}
	
	private CountryInsertSTO createCountrySTO() {
		return 
				CountryInsertSTO.builder()
				.name("Brasdasdas")
				.isoCode("BR")
				.build();
	}
}
