package com.akiratoriyama.gokufoodapi.controller.mapper;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.controller.response.PersonAddressResponse;
import com.akiratoriyama.gokufoodapi.model.PersonAddress;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;

@Component
public class PersonAddressControllerMapper extends BaseControllerMapper {
	
	@Autowired
	private PersonControllerMapper personMapper;
	
	public FmpPageResponse<PersonAddressResponse> to(Slice<TotalRecordsHolder<PersonAddress>> list) {
		return 
				FmpPageResponse.<PersonAddressResponse>builder()
					.pageIndex(list.getNumber())
					.pageSize(list.getPageable().getPageSize())
					.totalRecords(list.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0))
					.data(list.stream().map(this::to).collect(Collectors.toList()))
				.build();
	}
		
	public PersonAddressResponse to(TotalRecordsHolder<PersonAddress> holder) {
		return this.to(holder.getData());
	}
	
	public PersonAddressResponse to(PersonAddress ped) {
		return PersonAddressResponse.builder()
				.id(ped.getId())
				.ts(ped.getConvertedTs())
				.line1(ped.getLine1())
				.line2(ped.getLine2())
				.neighborhood(ped.getNeighborhood())
				.zipCode(ped.getZipCode())
				.type(this.fromEnum(ped.getType()))
				.city(ped.getCity())
				.person(personMapper.to(ped.getPerson()))
				.build();
	}
}
