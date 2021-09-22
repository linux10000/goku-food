package com.akiratoriyama.gokufoodapi.infra.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class I18nUtil {
	
	@Autowired
	private MessageSource messageSource;

    public String get(String key) {
        return messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
    }

    public String get(String key, Object[] arg) {
        return messageSource.getMessage(key, arg, LocaleContextHolder.getLocale());
    }
}
