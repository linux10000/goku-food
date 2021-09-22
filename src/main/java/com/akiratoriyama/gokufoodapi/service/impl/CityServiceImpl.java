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

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.CityNotFoundException;
import com.akiratoriyama.gokufoodapi.model.City;
import com.akiratoriyama.gokufoodapi.model.State;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.CityRepository;
import com.akiratoriyama.gokufoodapi.repository.filter.CityFilter;
import com.akiratoriyama.gokufoodapi.service.CityService;
import com.akiratoriyama.gokufoodapi.service.to.CityInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.CityUpdateSTO;

@Service
public class CityServiceImpl implements CityService {
	
	@Autowired
	private CityRepository cityRepository;

	@Override
	public Slice<TotalRecordsHolder<City>> filter(CityFilter filter) {
		return cityRepository.filter(
				filter,
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(),
						Direction.fromString(Objects.requireNonNullElse(filter.getSortDirection(), Sort.Direction.ASC.name())),
						Objects.requireNonNullElse(filter.getSortField(), City.Fields.id)
						)
				);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public City insert(CityInsertSTO sto) {
		return cityRepository.save(
					City.builder()
					.name(sto.getName())
					.active(true)
					.state(new State(sto.getStateId()))
					.build()
				);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public City update(CityUpdateSTO sto) throws CityNotFoundException {
		City city = cityRepository.findByIdAndActive(sto.getId(), true).orElseThrow(CityNotFoundException::new);
		
		city.setName(sto.getName());
		city.setConvertedTs(sto.getTs());
		city.setState(new State(sto.getStateId()));
		
		return cityRepository.save(city);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void deleteById(BigInteger id) throws CityNotFoundException {
		City city = cityRepository.findByIdAndActive(id, true).orElseThrow(CityNotFoundException::new);
		
		city.setActive(false);
		
		cityRepository.save(city);
	}
}
