package com.akiratoriyama.gokufoodapi.controller.mapper;

import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.controller.response.UserResourceResponse;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.User;
import com.akiratoriyama.gokufoodapi.model.UserResource;

@Component
public class UserResourceControllerMapper extends BaseControllerMapper {
	
	public FmpPageResponse<UserResourceResponse> to(Slice<TotalRecordsHolder<UserResource>> list) {
		return 
				FmpPageResponse.<UserResourceResponse>builder()
					.pageIndex(list.getNumber())
					.pageSize(list.getPageable().getPageSize())
					.totalRecords(list.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0))
					.data(list.stream().map(this::to).collect(Collectors.toList()))
				.build();
	}
		
	public UserResourceResponse to(TotalRecordsHolder<UserResource> holder) {
		return this.to(holder.getData());
	}
	
	public UserResourceResponse to(UserResource pes) {
		return UserResourceResponse.builder()
				.id(pes.getId())
				.ts(pes.getConvertedTs())
				.resource(pes.getResource())
				.user(to(pes.getUser()))
				.build();
	}
	
	public UserResourceResponse.User to(User usr) {
		return UserResourceResponse.User.builder()
				.id(usr.getId())
				.login(usr.getLogin())
				.firstName(usr.getPerson().getFirstName())
				.lastName(usr.getPerson().getLastName())
				.build();
	}
}
