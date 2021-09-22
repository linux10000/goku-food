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

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.CountryNotFoundException;
import com.akiratoriyama.gokufoodapi.model.Country;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.CountryRepository;
import com.akiratoriyama.gokufoodapi.repository.filter.CountryFilter;
import com.akiratoriyama.gokufoodapi.service.CountryService;
import com.akiratoriyama.gokufoodapi.service.to.CountryInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.CountryUpdateSTO;

@Service
public class CountryServiceImpl implements CountryService {
	
	@Autowired
	private CountryRepository countryRepository;

	@Override
	public Slice<TotalRecordsHolder<Country>> filter(CountryFilter filter) {
		return countryRepository.filter(
				filter,
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(),
						Direction.fromString(Objects.requireNonNullElse(filter.getSortDirection(), Sort.Direction.ASC.name())),
						Objects.requireNonNullElse(filter.getSortField(), Country.Fields.id)
						)
				);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Country insert(CountryInsertSTO sto) {
		return countryRepository.save(
					Country.builder()
					.name(sto.getName())
					.isoCode(sto.getIsoCode())
					.active(true)
					.build()
				);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Country update(CountryUpdateSTO sto) throws CountryNotFoundException {
		Country country = countryRepository.findByIdAndActive(sto.getId(), true).orElseThrow(CountryNotFoundException::new);
		
		country.setName(sto.getName());
		country.setIsoCode(sto.getIsoCode());
		country.setConvertedTs(sto.getTs());
		
		return countryRepository.save(country);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void deleteById(BigInteger id) throws CountryNotFoundException {
		Country country = countryRepository.findByIdAndActive(id, true).orElseThrow(CountryNotFoundException::new);
		
		country.setActive(false);
		
		countryRepository.save(country);
	}
}
