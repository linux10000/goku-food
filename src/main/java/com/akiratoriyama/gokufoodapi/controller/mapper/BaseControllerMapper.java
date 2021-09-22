package com.akiratoriyama.gokufoodapi.controller.mapper;

import org.springframework.beans.factory.annotation.Autowired;

import com.akiratoriyama.gokufoodapi.controller.response.EnumResponse;
import com.akiratoriyama.gokufoodapi.enums.I18nEnum;
import com.akiratoriyama.gokufoodapi.infra.util.I18nUtil;

public abstract class BaseControllerMapper {
	
	@Autowired
	private I18nUtil i18nUtil;

    public EnumResponse fromEnum(I18nEnum enumm) {
    	return EnumResponse.builder().type(enumm).i18n(i18nUtil.get(enumm.getI18nKey())).build();
    }
}
