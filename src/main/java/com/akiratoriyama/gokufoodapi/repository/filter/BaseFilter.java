package com.akiratoriyama.gokufoodapi.repository.filter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
@SuperBuilder
public class BaseFilter {
	
	@Builder.Default
	@JsonProperty("pageIndex")
	@NotNull
	@Min(0)
	private int pageIndex = 0;
	
	@Builder.Default
	@JsonProperty("pageSize")
	@NotNull
	@Min(1)
	@Max(100)
	private int pageSize = 25;
	
	@Builder.Default
	@JsonProperty("quickSearch")
	@Size(min = 0, max = 200)
	private String quickSearch = "";
	
	@JsonProperty("sortField")
	@Size(min = 0, max = 200)
	private String sortField;
	
	@JsonProperty("sortDirection")
	@Pattern(regexp = "ASC|DESC", flags = Flag.CASE_INSENSITIVE)
	private String sortDirection;
}
