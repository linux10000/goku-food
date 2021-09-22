package com.akiratoriyama.gokufoodapi.controller.mapper;

import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.model.Resource;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;

@Component
public class ResourceControllerMapper extends BaseControllerMapper {

	public FmpPageResponse<Resource> to(Slice<TotalRecordsHolder<Resource>> list) {
		return 
				FmpPageResponse.<Resource>builder()
					.pageIndex(list.getNumber())
					.pageSize(list.getPageable().getPageSize())
					.totalRecords(list.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0))
					.data(list.stream().map(this::to).collect(Collectors.toList()))
				.build();
	}
		
	public Resource to(TotalRecordsHolder<Resource> holder) {
		return holder.getData();
	}
}
