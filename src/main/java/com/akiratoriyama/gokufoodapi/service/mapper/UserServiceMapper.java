package com.akiratoriyama.gokufoodapi.service.mapper;

import org.springframework.stereotype.Component;

import com.akiratoriyama.gokufoodapi.infra.util.CryptUtils;
import com.akiratoriyama.gokufoodapi.model.Person;
import com.akiratoriyama.gokufoodapi.model.User;
import com.akiratoriyama.gokufoodapi.service.to.UserInsertSTO;

@Component
public class UserServiceMapper {

	public User from(UserInsertSTO sto) {
		return User.builder()
				.login(sto.getLogin())
				.password(CryptUtils.stringToSHA256(sto.getPassword()))
				.active(true)
				.person(
						Person.builder()
						.firstName(sto.getFirstName())
						.lastName(sto.getLastName())
						.legalType(sto.getLegalType())
						.user(true)
						.build()
						)
				.build();
	}
}
