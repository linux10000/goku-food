package com.akiratoriyama.gokufoodapi.service;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Slice;

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.CityNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.model.City;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.CityFilter;
import com.akiratoriyama.gokufoodapi.service.to.CityInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.CityUpdateSTO;

public interface CityService {

	Slice<TotalRecordsHolder<City>> filter(@Valid @NotNull CityFilter filter);

	City insert(@Valid @NotNull CityInsertSTO sto);

	City update(@Valid @NotNull CityUpdateSTO sto) throws CityNotFoundException;

	void deleteById(@Valid @NotNull(message = Messages.CITY_ID_NOT_NULL) BigInteger id) throws CityNotFoundException;

}
