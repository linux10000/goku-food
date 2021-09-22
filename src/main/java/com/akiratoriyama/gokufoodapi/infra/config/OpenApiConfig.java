package com.akiratoriyama.gokufoodapi.infra.config;

import org.springframework.context.annotation.Configuration;

import com.akiratoriyama.gokufoodapi.infra.util.Const;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Goku Food API", version = "v1"))
@SecurityScheme(
	    name = Const.Misc.OPENAPI_AUTH,
	    type = SecuritySchemeType.HTTP,
	    bearerFormat = "JWT",
	    scheme = "bearer"
	)
public class OpenApiConfig {

}
