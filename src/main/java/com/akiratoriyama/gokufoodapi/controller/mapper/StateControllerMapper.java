package com.akiratoriyama.gokufoodapi.controller.mapper;

import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.model.State;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;

@Component
public class StateControllerMapper extends BaseControllerMapper {

	public FmpPageResponse<State> to(Slice<TotalRecordsHolder<State>> list) {
		return 
				FmpPageResponse.<State>builder()
					.pageIndex(list.getNumber())
					.pageSize(list.getPageable().getPageSize())
					.totalRecords(list.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0))
					.data(list.stream().map(this::to).collect(Collectors.toList()))
				.build();
	}
	
	public State to(TotalRecordsHolder<State> holder) {
		return holder.getData();
	}
}
