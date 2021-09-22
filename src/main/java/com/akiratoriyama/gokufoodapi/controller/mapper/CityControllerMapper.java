package com.akiratoriyama.gokufoodapi.controller.mapper;

import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.model.City;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;

@Component
public class CityControllerMapper extends BaseControllerMapper {

	public FmpPageResponse<City> to(Slice<TotalRecordsHolder<City>> list) {
		return 
				FmpPageResponse.<City>builder()
					.pageIndex(list.getNumber())
					.pageSize(list.getPageable().getPageSize())
					.totalRecords(list.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0))
					.data(list.stream().map(this::to).collect(Collectors.toList()))
				.build();
	}
		
	public City to(TotalRecordsHolder<City> holder) {
		return holder.getData();
	}
}
