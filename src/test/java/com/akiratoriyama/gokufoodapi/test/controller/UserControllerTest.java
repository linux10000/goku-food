package com.akiratoriyama.gokufoodapi.test.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.akiratoriyama.gokufoodapi.controller.mapper.UserControllerMapper;
import com.akiratoriyama.gokufoodapi.controller.v1.UserController;
import com.akiratoriyama.gokufoodapi.enums.PersonLegalType;
import com.akiratoriyama.gokufoodapi.infra.config.CacheConfig;
import com.akiratoriyama.gokufoodapi.infra.config.LocaleConfig;
import com.akiratoriyama.gokufoodapi.infra.config.MethodSecurityConfig;
import com.akiratoriyama.gokufoodapi.infra.config.SecurityConfig;
import com.akiratoriyama.gokufoodapi.infra.config.ValidatorMessageConfig;
import com.akiratoriyama.gokufoodapi.infra.util.Endpoint;
import com.akiratoriyama.gokufoodapi.infra.util.I18nUtil;
import com.akiratoriyama.gokufoodapi.infra.util.ObjectMapperUtil;
import com.akiratoriyama.gokufoodapi.infra.util.UserUtil;
import com.akiratoriyama.gokufoodapi.model.Person;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.User;
import com.akiratoriyama.gokufoodapi.repository.filter.UserFilter;
import com.akiratoriyama.gokufoodapi.service.UserService;
import com.akiratoriyama.gokufoodapi.test.infra.BaseControllerTestConf;
import com.akiratoriyama.gokufoodapi.test.infra.UserUtilsTest;

@ExtendWith(SpringExtension.class)
@WebMvcTest({UserController.class, LocaleConfig.class, ValidatorMessageConfig.class, CacheConfig.class, SecurityConfig.class, MethodSecurityConfig.class})
class UserControllerTest {
	private static final String ADMIN_USERNAME = "admin";
	
	@TestConfiguration
	static class UserControllerTestConfiguration extends BaseControllerTestConf {
		
		@Bean
		public UserControllerMapper userControllerMapper() {
			return new UserControllerMapper();
		}
		
		@Bean
		public I18nUtil i18nUtil() {
			return new I18nUtil();
		}
		
		@Bean
		public UserUtil userUtil() {
			return new UserUtil();
		}
	}

	@MockBean
	private UserService userService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() {
		UserUtilsTest.injectAdminUserOnContext();
	}

	
	@Test
	void filter() throws Exception {	
		UserFilter filterRequest = UserFilter.builder().id(BigInteger.valueOf(-1)).build();
		TotalRecordsHolder<User> holderUser = new TotalRecordsHolder<User>() {
			@Override
			public Integer getTotalRecords() {
				return 1;
			}
			@Override
			public User getData() {
				return User.builder()
						.id(BigInteger.valueOf(-1))
						.login(ADMIN_USERNAME)
						.person(Person.builder()
								.firstName(ADMIN_USERNAME)
								.lastName(ADMIN_USERNAME)
								.legalType(PersonLegalType.NOT_AVAILIBLE)
								.build()
								)
						.build();
			}
		};
		
		Mockito.when(userService.filter(filterRequest)).thenReturn(new SliceImpl<TotalRecordsHolder<User>>(List.of(holderUser), Pageable.ofSize(1), false));
		
	    mockMvc.perform(
	    		post(Endpoint.User.V1.URL_FILTER)
		        .contentType(MediaType.APPLICATION_JSON_VALUE)
		        .content(ObjectMapperUtil.getObjectMapper().writeValueAsString(filterRequest))
		        )
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.data[0].login").value(ADMIN_USERNAME));
	}
}
