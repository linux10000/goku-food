package com.akiratoriyama.gokufoodapi.service;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Slice;

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.CountryNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.model.Country;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.CountryFilter;
import com.akiratoriyama.gokufoodapi.service.to.CountryInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.CountryUpdateSTO;

public interface CountryService {

	Slice<TotalRecordsHolder<Country>> filter(@Valid @NotNull CountryFilter filter);

	Country insert(@Valid @NotNull CountryInsertSTO sto);

	Country update(@Valid @NotNull  CountryUpdateSTO sto) throws CountryNotFoundException;

	void deleteById(@NotNull(message = Messages.COUNTRY_ID_NOT_NULL) BigInteger id) throws CountryNotFoundException;
}
