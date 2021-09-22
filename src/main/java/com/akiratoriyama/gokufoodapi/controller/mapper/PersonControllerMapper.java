package com.akiratoriyama.gokufoodapi.controller.mapper;

import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.controller.response.PersonResponse;
import com.akiratoriyama.gokufoodapi.model.Person;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;

@Component
public class PersonControllerMapper extends BaseControllerMapper {

	public FmpPageResponse<PersonResponse> to(Slice<TotalRecordsHolder<Person>> list) {
		return 
				FmpPageResponse.<PersonResponse>builder()
					.pageIndex(list.getNumber())
					.pageSize(list.getPageable().getPageSize())
					.totalRecords(list.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0))
					.data(list.stream().map(this::to).collect(Collectors.toList()))
				.build();
	}
		
	public PersonResponse to(TotalRecordsHolder<Person> holder) {
		return this.to(holder.getData());
	}
	
	public PersonResponse to(Person pes) {
		return PersonResponse.builder()
				.id(pes.getId())
				.ts(pes.getConvertedTs())
				.firstName(pes.getFirstName())
				.lastName(pes.getLastName())
				.legalType(fromEnum(pes.getLegalType()))
				.build();
	}
}
