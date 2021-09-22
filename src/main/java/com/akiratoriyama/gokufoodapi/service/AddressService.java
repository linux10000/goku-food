package com.akiratoriyama.gokufoodapi.service;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Slice;

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.AddressNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.model.Address;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.AddressByZipCodeFilter;
import com.akiratoriyama.gokufoodapi.repository.filter.AddressFilter;
import com.akiratoriyama.gokufoodapi.service.to.AddressInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.AddressUpdateSTO;

public interface AddressService {

	Slice<TotalRecordsHolder<Address>> filter(@Valid @NotNull AddressFilter filter);

	Slice<TotalRecordsHolder<Address>> filterByCachedZipCode(@Valid @NotNull AddressByZipCodeFilter filter);

	Address insert(@Valid @NotNull AddressInsertSTO sto);

	Address update(@Valid @NotNull AddressUpdateSTO sto) throws AddressNotFoundException;

	void deleteById(@Valid @NotNull(message = Messages.ADDRESS_ID_NOT_NULL) BigInteger id) throws AddressNotFoundException;


}
