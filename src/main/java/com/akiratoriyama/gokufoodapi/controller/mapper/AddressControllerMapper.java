package com.akiratoriyama.gokufoodapi.controller.mapper;

import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.model.Address;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;

@Component
public class AddressControllerMapper extends BaseControllerMapper {

	public FmpPageResponse<Address> to(Slice<TotalRecordsHolder<Address>> list) {
		return 
				FmpPageResponse.<Address>builder()
					.pageIndex(list.getNumber())
					.pageSize(list.getPageable().getPageSize())
					.totalRecords(list.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0))
					.data(list.stream().map(this::to).collect(Collectors.toList()))
				.build();
	}
		
	public Address to(TotalRecordsHolder<Address> holder) {
		return holder.getData();
	}
}
