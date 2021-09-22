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

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.StateNotFoundException;
import com.akiratoriyama.gokufoodapi.model.Country;
import com.akiratoriyama.gokufoodapi.model.State;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.StateRepository;
import com.akiratoriyama.gokufoodapi.repository.filter.StateFilter;
import com.akiratoriyama.gokufoodapi.service.StateService;
import com.akiratoriyama.gokufoodapi.service.to.StateInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.StateUpdateSTO;

@Service
public class StateServiceImpl implements StateService {
	
	@Autowired
	private StateRepository stateRepository;

	
	@Override
	public Slice<TotalRecordsHolder<State>> filter(StateFilter filter) {
		return stateRepository.filter(
				filter,
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(),
						Direction.fromString(Objects.requireNonNullElse(filter.getSortDirection(), Sort.Direction.ASC.name())),
						Objects.requireNonNullElse(filter.getSortField(), State.Fields.id)
						)
				);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public State insert(StateInsertSTO sto) {
		return stateRepository.save(
					State.builder()
					.name(sto.getName())
					.active(true)
					.country(new Country(sto.getCountryId()))
					.build()
				);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public State update(StateUpdateSTO sto) throws StateNotFoundException {
		State state = stateRepository.findByIdAndActive(sto.getId(), true).orElseThrow(StateNotFoundException::new);
		
		state.setName(sto.getName());
		state.setConvertedTs(sto.getTs());
		state.setCountry(new Country(sto.getCountryId()));
		
		return stateRepository.save(state);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void deleteById(BigInteger id) throws StateNotFoundException {
		State state = stateRepository.findByIdAndActive(id, true).orElseThrow(StateNotFoundException::new);
		
		state.setActive(false);
		
		stateRepository.save(state);
	}
}
