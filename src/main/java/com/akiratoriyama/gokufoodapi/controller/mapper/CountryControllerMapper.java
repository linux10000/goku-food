package com.akiratoriyama.gokufoodapi.controller.mapper;

import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.model.Country;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;

@Component
public class CountryControllerMapper extends BaseControllerMapper {

	public FmpPageResponse<Country> to(Slice<TotalRecordsHolder<Country>> list) {
		return 
				FmpPageResponse.<Country>builder()
					.pageIndex(list.getNumber())
					.pageSize(list.getPageable().getPageSize())
					.totalRecords(list.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0))
					.data(list.stream().map(this::to).collect(Collectors.toList()))
				.build();
	}
	
	public Country to(TotalRecordsHolder<Country> holder) {
		return holder.getData();
	}
}
