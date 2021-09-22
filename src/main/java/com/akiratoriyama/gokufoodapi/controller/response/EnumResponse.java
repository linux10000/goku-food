package com.akiratoriyama.gokufoodapi.controller.response;

import com.akiratoriyama.gokufoodapi.enums.I18nEnum;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnumResponse {

	private I18nEnum type;
	private String i18n;
}
