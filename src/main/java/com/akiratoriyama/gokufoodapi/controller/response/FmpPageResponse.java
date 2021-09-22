package com.akiratoriyama.gokufoodapi.controller.response;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;

import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FmpPageResponse<T> {

	private Integer pageIndex;
	private Integer pageSize;
	private Integer totalRecords;
	private List<T> data;
	
	public FmpPageResponse(Slice<TotalRecordsHolder<T>> slice, Function<? super TotalRecordsHolder<T>, T> mapper) {
		this.pageIndex = slice.getNumber();
		this.pageSize = slice.getPageable().isPaged() ? slice.getPageable().getPageSize() : -1;
		this.totalRecords = slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0);
		this.data = slice.stream().map(mapper).collect(Collectors.toList());
	}
}
