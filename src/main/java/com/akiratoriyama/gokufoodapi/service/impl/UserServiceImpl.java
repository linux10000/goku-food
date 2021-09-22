package com.akiratoriyama.gokufoodapi.service.impl;

import java.math.BigInteger;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.akiratoriyama.gokufoodapi.infra.exception.AdminDisableForbiddenException;
import com.akiratoriyama.gokufoodapi.infra.exception.InvalidCredentialsException;
import com.akiratoriyama.gokufoodapi.infra.exception.notfound.UserNotFoundException;
import com.akiratoriyama.gokufoodapi.infra.security.TokenSession;
import com.akiratoriyama.gokufoodapi.infra.util.CryptUtils;
import com.akiratoriyama.gokufoodapi.model.Person;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.User;
import com.akiratoriyama.gokufoodapi.repository.UserRepository;
import com.akiratoriyama.gokufoodapi.repository.filter.UserFilter;
import com.akiratoriyama.gokufoodapi.service.PersonService;
import com.akiratoriyama.gokufoodapi.service.TokenCachedService;
import com.akiratoriyama.gokufoodapi.service.UserService;
import com.akiratoriyama.gokufoodapi.service.mapper.UserServiceMapper;
import com.akiratoriyama.gokufoodapi.service.to.UserChangePasswordSTO;
import com.akiratoriyama.gokufoodapi.service.to.UserInsertSTO;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserServiceMapper userServiceMapper;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private TokenCachedService tokenCachedService;

	@Override
	public Slice<TotalRecordsHolder<User>> filter(UserFilter filter) {
		return userRepository.filter(
				filter,
				PageRequest.of(
						filter.getPageIndex(), 
						filter.getPageSize(),
						Direction.fromString(Objects.requireNonNullElse(filter.getSortDirection(), Sort.Direction.ASC.name())),
						Objects.requireNonNullElse(filter.getSortField(), User.Fields.id)
						)
				);
	}
	
	@Override
	public User findByRawToken(String rawToken) throws UserNotFoundException {
		TokenSession session = tokenCachedService.getByRawToken(rawToken);
		if ( session == null )
			throw new BadCredentialsException("");
		
		return userRepository.findActiveByLogin(session.getLogin()).orElseThrow(UserNotFoundException::new);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public User insert(@Valid @NotNull UserInsertSTO sto) {
		User usr = userServiceMapper.from(sto);
		Person pes = personService.insert(usr.getPerson());
		
		usr.setId(pes.getId());
		usr.setActive(true);
		
		return userRepository.save(usr);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void changePassword(UserChangePasswordSTO sto) throws UserNotFoundException, InvalidCredentialsException {
		User user = userRepository.findActiveByLogin(sto.getLogin()).orElseThrow(UserNotFoundException::new);
		
		if ( !sto.getAuthenticatedUser().equals("admin") && !sto.getAuthenticatedUser().equals(sto.getLogin()) )
			throw new AccessDeniedException("");
		
		if ( !user.getPassword().equals(CryptUtils.stringToSHA256(sto.getOldPassword())) )
			throw new InvalidCredentialsException();
		
		user.setPassword(CryptUtils.stringToSHA256(sto.getNewPassword()));
		userRepository.save(user);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void disableByLogin(String login) throws UserNotFoundException, AdminDisableForbiddenException {
		User user = userRepository.findActiveByLogin(login).orElseThrow(UserNotFoundException::new);
		
		if ( this.isUserAdmin(user.getId()) )
			throw new AdminDisableForbiddenException();
		
		user.setActive(false);
		userRepository.save(user);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	@Override
	public void enableByLogin(String login) throws UserNotFoundException {
		User user = userRepository.findActiveByLogin(login).orElseThrow(UserNotFoundException::new);
		
		user.setActive(true);
		userRepository.save(user);
	}
	
	@Override
	public User findByLoginAndPassword(String login, String password) throws UserNotFoundException {
		return this.userRepository.findByLoginAndPassword(login, CryptUtils.stringToSHA256(password)).orElseThrow(UserNotFoundException::new);
	}
	
	private boolean isUserAdmin(BigInteger userId) {
		return userId != null && userId.equals(BigInteger.valueOf(-1));
	}
}
