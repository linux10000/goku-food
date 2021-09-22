package com.akiratoriyama.gokufoodapi.service;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Slice;

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.PersonAddressNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.model.PersonAddress;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.PersonAddressFilter;
import com.akiratoriyama.gokufoodapi.service.to.PersonAddressInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.PersonAddressUpdateSTO;

public interface PersonAddressService {

	Slice<TotalRecordsHolder<PersonAddress>> filter(@Valid @NotNull PersonAddressFilter filter);

	PersonAddress insert(@Valid @NotNull PersonAddressInsertSTO sto);

	PersonAddress update(@Valid @NotNull PersonAddressUpdateSTO sto) throws PersonAddressNotFoundException;

	void deleteById(@Valid @NotNull(message = Messages.PERSONADDRESS_ID_NOT_NULL) BigInteger id) throws PersonAddressNotFoundException;

}
