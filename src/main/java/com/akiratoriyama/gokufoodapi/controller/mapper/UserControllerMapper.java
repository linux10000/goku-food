package com.akiratoriyama.gokufoodapi.controller.mapper;

import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.controller.response.FmpPageResponse;
import com.akiratoriyama.gokufoodapi.controller.response.UserFilterResponse;
import com.akiratoriyama.gokufoodapi.controller.response.UserResponse;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.User;

@Component
public class UserControllerMapper extends BaseControllerMapper {

	public FmpPageResponse<UserFilterResponse> to(Slice<TotalRecordsHolder<User>> list) {
		return 
				FmpPageResponse.<UserFilterResponse>builder()
					.pageIndex(list.getNumber())
					.pageSize(list.getPageable().getPageSize())
					.totalRecords(list.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0))
					.data(list.stream().map(this::to).collect(Collectors.toList()))
				.build();
	}
	
	public UserFilterResponse to(TotalRecordsHolder<User> holder) {
		User data = holder.getData();
		
		return 
				UserFilterResponse.builder()
					.id(data.getId())
					.login(data.getLogin())
					.firstName(data.getPerson().getFirstName())
					.lastName(data.getPerson().getLastName())
					.legalType(this.fromEnum(data.getPerson().getLegalType()))
					.ts(data.getConvertedTs())
				.build();
	}
	
	public UserResponse from(User usr) {
		return UserResponse.builder()
				.id(usr.getId())
				.login(usr.getLogin())
				.ts(usr.getConvertedTs())
				.firstName(usr.getPerson().getFirstName())
				.lastName(usr.getPerson().getLastName())
				.legalType(this.fromEnum(usr.getPerson().getLegalType()))
				.build();
	}
}
