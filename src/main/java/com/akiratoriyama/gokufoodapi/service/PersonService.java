package com.akiratoriyama.gokufoodapi.service;

import java.math.BigInteger;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Slice;

import com.akiratoriyama.gokufoodapi.infra.exception.notfound.PersonNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.model.Person;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.repository.filter.PersonFilter;
import com.akiratoriyama.gokufoodapi.service.to.PersonInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.PersonUpdateSTO;

public interface PersonService {

	Person insert(@NotNull Person pes);

	Slice<TotalRecordsHolder<Person>> filter(@Valid @NotNull PersonFilter filter);

	Person insert(@Valid @NotNull PersonInsertSTO sto);

	Person update(@Valid @NotNull PersonUpdateSTO sto) throws PersonNotFoundException;

	void deleteById(@Valid @NotNull(message = Messages.PERSON_ID_NOT_NULL) BigInteger id) throws PersonNotFoundException;

}
