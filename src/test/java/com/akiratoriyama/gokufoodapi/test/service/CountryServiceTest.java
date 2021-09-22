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

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.CountryNotFoundException;
import com.akiratoriyama.gokufoodapi.model.Country;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.CountryFilter;
import com.akiratoriyama.gokufoodapi.service.CountryService;
import com.akiratoriyama.gokufoodapi.service.impl.CountryServiceImpl;
import com.akiratoriyama.gokufoodapi.service.to.CountryInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.CountryUpdateSTO;
import com.akiratoriyama.gokufoodapi.test.infra.BaseTestConf;
import com.akiratoriyama.gokufoodapi.test.infra.TestDataJpa;

@ExtendWith(SpringExtension.class)
@TestDataJpa
class CountryServiceTest {
	
	@TestConfiguration
	static class CountryServiceTestConfiguration extends BaseTestConf {
		
		@Bean
		public CountryService countryService() {
			return new CountryServiceImpl();
		}
	}

	
	@Autowired
	private CountryService countryService;
	
	
	@Test
	void filterById() {
		Country cou = countryService.insert(this.createCountrySTO());
		Slice<TotalRecordsHolder<Country>> slice = countryService.filter(
				CountryFilter.builder()
				.id(cou.getId())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(cou.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	@Test
	void filterByName() {
		Country cou = countryService.insert(this.createCountrySTO());
		Slice<TotalRecordsHolder<Country>> slice = countryService.filter(
				CountryFilter.builder()
				.quickSearch(cou.getName())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(cou.getId(), slice.stream().findFirst().get().getData().getId());
	}
	
	
	@Test
	void filterByIsoCode() {
		Country cou = countryService.insert(this.createCountrySTO());
		Slice<TotalRecordsHolder<Country>> slice = countryService.filter(
				CountryFilter.builder()
				.quickSearch(cou.getIsoCode())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(cou.getIsoCode(), slice.stream().findFirst().get().getData().getIsoCode());
	}
	
	@Test
	void insert() {
		CountryInsertSTO sto = this.createCountrySTO();
		
		Country cou = countryService.insert(sto);
		
		assertNotEquals(null, cou);
		assertEquals(true, cou.getActive());
	}
	
	@Test
	void update() throws CountryNotFoundException {
		final String newName = "EUA";
		Country cou = countryService.insert(this.createCountrySTO());
		
		cou = countryService.update(
				CountryUpdateSTO.builder()
				.id(cou.getId())
				.name(newName)
				.isoCode(cou.getIsoCode())
				.ts(cou.getConvertedTs())
				.build()
				);
		
		assertNotEquals(null, cou);
		assertEquals(true, cou.getActive());
		assertEquals(newName, cou.getName());
	}

	@Test
	void delete() throws CountryNotFoundException {
		Country cou = countryService.insert(this.createCountrySTO());
		
		countryService.deleteById(cou.getId());
		
		assertEquals(0, countryService.filter(
												CountryFilter.builder()
												.id(cou.getId())
												.build()
											)
											.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0)
					);
	}
	
	private CountryInsertSTO createCountrySTO() {
		return 
				CountryInsertSTO.builder()
				.name("Brazil")
				.isoCode("BR")
				.build();
	}
}
