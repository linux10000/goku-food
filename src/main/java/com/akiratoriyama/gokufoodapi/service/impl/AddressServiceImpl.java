package com.akiratoriyama.gokufoodapi.service.impl;

import java.math.BigInteger;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.AddressNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Const;
import com.akiratoriyama.gokufoodapi.infra.util.FormatUtil;
import com.akiratoriyama.gokufoodapi.model.Address;
import com.akiratoriyama.gokufoodapi.model.City;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.AddressRepository;
import com.akiratoriyama.gokufoodapi.repository.filter.AddressByZipCodeFilter;
import com.akiratoriyama.gokufoodapi.repository.filter.AddressFilter;
import com.akiratoriyama.gokufoodapi.service.AddressService;
import com.akiratoriyama.gokufoodapi.service.to.AddressInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.AddressUpdateSTO;

@Service
public class AddressServiceImpl implements AddressService {
	
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public Slice<TotalRecordsHolder<Address>> filter(AddressFilter filter) {
		return addressRepository.filter(
				filter,
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(),
						Direction.fromString(Objects.requireNonNullElse(filter.getSortDirection(), Sort.Direction.ASC.name())),
						Objects.requireNonNullElse(filter.getSortField(), Address.Fields.id)
						)
				);
	}

	@Cacheable(cacheNames = Const.Cache.ZIP_CODE, key = "#filter.zipCode", unless = "#result == null || #result.getSize() == 0")
	@Override
	public Slice<TotalRecordsHolder<Address>> filterByCachedZipCode(AddressByZipCodeFilter filter) {
		if ( filter.isIgnoreSpecialChars() )
			filter.setZipCode(FormatUtil.getOnlyNumbersAndLetters(filter.getZipCode()));
		
		return addressRepository.filterByZipCode(
				filter,
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(),
						Direction.fromString(Objects.requireNonNullElse(filter.getSortDirection(), Sort.Direction.ASC.name())),
						Objects.requireNonNullElse(filter.getSortField(), Address.Fields.id)
						)
				);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Address insert(AddressInsertSTO sto) {
		return addressRepository.save(
					Address.builder()
					.line1(sto.getLine1())
					.line2(sto.getLine2())
					.neighborhood(sto.getNeighborhood())
					.zipCode(FormatUtil.getOnlyNumbersAndLetters(sto.getZipCode()))
					.active(true)
					.city(new City(sto.getCityId()))
					.build()
				);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public Address update(AddressUpdateSTO sto) throws AddressNotFoundException {
		Address address = addressRepository.findByIdAndActive(sto.getId(), true).orElseThrow(AddressNotFoundException::new);
		
		address.setLine1(sto.getLine1());
		address.setLine2(sto.getLine2());
		address.setNeighborhood(sto.getNeighborhood());
		address.setZipCode(FormatUtil.getOnlyNumbersAndLetters(sto.getZipCode()));
		address.setConvertedTs(sto.getTs());
		address.setCity(new City(sto.getCityId()));
		address.setConvertedTs(sto.getTs());
		
		return addressRepository.save(address);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void deleteById(BigInteger id) throws AddressNotFoundException {
		Address address = addressRepository.findByIdAndActive(id, true).orElseThrow(AddressNotFoundException::new);
		
		address.setActive(false);
		
		addressRepository.save(address);
	}
}
