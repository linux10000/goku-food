package com.akiratoriyama.gokufoodapi.test.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.akiratoriyama.gokufoodapi.enums.PersonLegalType;
import com.akiratoriyama.gokufoodapi.infra.exception.UserResourceUniqueViolationException;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.UserResourceNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.Const.Resources;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.User;
import com.akiratoriyama.gokufoodapi.model.UserResource;
import com.akiratoriyama.gokufoodapi.repository.filter.UserResourceFilter;
import com.akiratoriyama.gokufoodapi.service.PersonService;
import com.akiratoriyama.gokufoodapi.service.TokenCachedService;
import com.akiratoriyama.gokufoodapi.service.UserResourceService;
import com.akiratoriyama.gokufoodapi.service.UserService;
import com.akiratoriyama.gokufoodapi.service.impl.PersonServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.TokenCachedServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.UserResourceServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.UserServiceImpl;
import com.akiratoriyama.gokufoodapi.service.mapper.UserServiceMapper;
import com.akiratoriyama.gokufoodapi.service.to.PersonInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.UserInsertSTO;
import com.akiratoriyama.gokufoodapi.service.to.UserResourceInsertSTO;
import com.akiratoriyama.gokufoodapi.test.infra.BaseTestConf;
import com.akiratoriyama.gokufoodapi.test.infra.TestDataJpa;

@ExtendWith(SpringExtension.class)
@TestDataJpa
@EnableCaching
class UserResourceServiceTest {
	
	@TestConfiguration
	static class UserResourceServiceTestConfiguration extends BaseTestConf {
		
		@Bean
		public UserResourceService userResourceService() {
			return new UserResourceServiceImpl();
		}

		@Bean
		public PersonService personService() {
			return new PersonServiceImpl();
		}
		
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
		
		@Bean
		public UserServiceMapper userServiceMapper() {
			return new UserServiceMapper();
		}
		
		@Bean
		public TokenCachedService tokenCachedService(CacheManager cacheManager) {
			return new TokenCachedServiceImpl(cacheManager);
		}
	}

	
	@Autowired
	private UserResourceService userResourceService;
	
	@Autowired
	private UserService userService;
	
	
	@Test
	void filterByUserId() throws UserResourceUniqueViolationException {
		User user = this.userService.insert(this.createUserInsert());
		UserResource uce = userResourceService.insert(this.createUserResourceSTO(user.getId()));
		Slice<TotalRecordsHolder<UserResource>> slice = userResourceService.filter(
				UserResourceFilter.builder()
				.userId(uce.getUser().getId())
				.build()
				);
		
		assertNotEquals(null, slice.getContent().get(0));
		assertTrue(slice.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0) > 0);
		assertEquals(uce.getUser().getId(), slice.stream().findFirst().get().getData().getUser().getId());
	}

	
	@Test
	void insert() throws UserResourceUniqueViolationException {
		User user = this.userService.insert(this.createUserInsert());
		UserResourceInsertSTO sto = this.createUserResourceSTO(user.getId());
		
		UserResource uce = userResourceService.insert(sto);
		
		assertNotEquals(null, uce);
		assertEquals(true, uce.getActive());
	}
	
	@Test
	void delete() throws UserResourceUniqueViolationException, UserResourceNotFoundException {
		User user = this.userService.insert(this.createUserInsert());
		UserResource uce = userResourceService.insert(this.createUserResourceSTO(user.getId()));
		
		userResourceService.deleteById(uce.getId());
		
		assertEquals(0, userResourceService.filter(
				                               UserResourceFilter.builder()
												.userId(uce.getUser().getId())
												.build()
											)
											.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0)
					);
	}
	
	private UserInsertSTO createUserInsert() {
		PersonInsertSTO person = this.createPersonSTO();
		
		return UserInsertSTO.builder()
				.login("angelica@globo.com")
				.firstName(person.getFirstName())
				.lastName(person.getLastName())
				.legalType(person.getLegalType())
				.password("12345678")
				.build();
	}
	
	private UserResourceInsertSTO createUserResourceSTO(BigInteger userId) {
		return 
				UserResourceInsertSTO.builder()
				.resourceId(new BigInteger(Resources.USERRESOURCE_INSERT))
				.userId(userId)
				.build();
	}
	
	private PersonInsertSTO createPersonSTO() {
		return 
				PersonInsertSTO.builder()
				.firstName("Angelica")
				.lastName("da Graça")
				.legalType(PersonLegalType.PHYSICAL)
				.user(false)
				.build();
	}
}
