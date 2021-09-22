package com.akiratoriyama.gokufoodapi.test.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import com.akiratoriyama.gokufoodapi.infra.exception.AdminDisableForbiddenException;
import com.akiratoriyama.gokufoodapi.infra.exception.InvalidCredentialsException;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.UserNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.util.CryptUtils;
import com.akiratoriyama.gokufoodapi.model.Person;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.User;
import com.akiratoriyama.gokufoodapi.repository.UserRepository;
import com.akiratoriyama.gokufoodapi.repository.filter.UserFilter;
import com.akiratoriyama.gokufoodapi.service.PersonService;
import com.akiratoriyama.gokufoodapi.service.TokenCachedService;
import com.akiratoriyama.gokufoodapi.service.UserService;
import com.akiratoriyama.gokufoodapi.service.impl.PersonServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.TokenCachedServiceImpl;
import com.akiratoriyama.gokufoodapi.service.impl.UserServiceImpl;
import com.akiratoriyama.gokufoodapi.service.mapper.UserServiceMapper;
import com.akiratoriyama.gokufoodapi.service.to.UserChangePasswordSTO;
import com.akiratoriyama.gokufoodapi.service.to.UserInsertSTO;
import com.akiratoriyama.gokufoodapi.test.infra.BaseTestConf;
import com.akiratoriyama.gokufoodapi.test.infra.TestDataJpa;

@ExtendWith(SpringExtension.class)
@TestDataJpa
@EnableCaching
class UserServiceTest {
	
	@TestConfiguration
	static class UserServiceTestConfiguration extends BaseTestConf {
		
		@Bean
		public UserServiceMapper userServiceMapper() {
			return new UserServiceMapper();
		}
		
		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
		
		@Bean
		public PersonService personService() {
			return new PersonServiceImpl();
		}
		
		@Bean
		public TokenCachedService tokenCachedService(CacheManager cacheManager) {
			return new TokenCachedServiceImpl(cacheManager);
		}
	}

	
	@Autowired
	private UserServiceImpl userService;
	
	@Autowired
	private UserRepository userRepository;

	@Test
	void filter() {
		final BigInteger adminId = BigInteger.valueOf(-1);
		Slice<TotalRecordsHolder<User>> usr = userService.filter(UserFilter.builder().id(adminId).build());
		
		assertNotEquals(null, usr);
		assertEquals(1, usr.get().findFirst().map(TotalRecordsHolder::getTotalRecords).orElse(0));
		assertEquals(adminId, usr.stream().findFirst().get().getData().getId());
	}
	
	@Test
	void insert() {
		UserInsertSTO sto = this.createUserInsert();
		
		User userFinal = userService.insert(sto);
		
		assertNotEquals(null, userFinal.getPerson());
		assertEquals(BigInteger.ONE, userFinal.getId());
		assertEquals(true, userFinal.getPerson().getActive());
		assertEquals(true, userFinal.getActive());
		assertEquals(sto.getLogin(), userFinal.getLogin());
		assertEquals(CryptUtils.stringToSHA256(sto.getPassword()), userFinal.getPassword());
	}
	
	@Test
	void changePassword() throws UserNotFoundException, InvalidCredentialsException {
		final String newPassword = "87654321";
		final UserInsertSTO newUserSTO = this.createUserInsert();
		
		userService.insert(newUserSTO);
		userService.changePassword(
				UserChangePasswordSTO.builder()
				.login(newUserSTO.getLogin())
				.oldPassword(newUserSTO.getPassword())
				.newPassword(newPassword)
				.authenticatedUser(newUserSTO.getLogin())
				.build()
				);
		
		assertEquals(CryptUtils.stringToSHA256(newPassword), userRepository.findActiveByLogin(newUserSTO.getLogin()).get().getPassword());
	}
	
	@Test
	void disableByLoginNotPossibleForAdmin() throws UserNotFoundException, AdminDisableForbiddenException {
		assertThrows(AdminDisableForbiddenException.class, () -> userService.disableByLogin("admin"));
	}
	
	@Test
	void disableByLogin() throws UserNotFoundException, AdminDisableForbiddenException {
		final UserInsertSTO newUserSTO = this.createUserInsert();
		
		userService.insert(newUserSTO);
		userService.disableByLogin(newUserSTO.getLogin());
		
		assertEquals(false, userRepository.findActiveByLogin(newUserSTO.getLogin()).isPresent());
	}
	
	@Test
	void enableByLogin() throws UserNotFoundException {
		final String login = "admin";
		userService.enableByLogin(login);
		
		assertEquals(true, userRepository.findActiveByLogin(login).isPresent());
	}
	
	private UserInsertSTO createUserInsert() {
		Person person = this.createPerson();
		
		return UserInsertSTO.builder()
				.login("xuxa@globo.com")
				.firstName(person.getFirstName())
				.lastName(person.getLastName())
				.legalType(person.getLegalType())
				.password("12345678")
				.build();
	}
	
	private Person createPerson() {
		return Person.builder()
				.firstName("Maria")
				.lastName("da Xuxa")
				.legalType(PersonLegalType.PHYSICAL)
				.build();
	}
}
