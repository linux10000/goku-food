package com.akiratoriyama.gokufoodapi.service;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Slice;

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.StateNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.model.State;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.StateFilter;
import com.akiratoriyama.gokufoodapi.service.to.StateInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.StateUpdateSTO;

public interface StateService {

	Slice<TotalRecordsHolder<State>> filter(@Valid @NotNull StateFilter filter);

	State insert(@Valid @NotNull StateInsertSTO sto);

	State update(@Valid @NotNull StateUpdateSTO sto) throws StateNotFoundException;

	void deleteById(@Valid @NotNull(message = Messages.STATE_ID_NOT_NULL) BigInteger id) throws StateNotFoundException;

}
