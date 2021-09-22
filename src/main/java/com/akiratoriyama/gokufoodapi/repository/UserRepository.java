package com.akiratoriyama.gokufoodapi.repository;

import java.math.BigInteger;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import com.akiratoriyama.gokufoodapi.infra.util.Messages;
import com.akiratoriyama.gokufoodapi.model.TotalRecordsHolder;
import com.akiratoriyama.gokufoodapi.model.User;
import com.akiratoriyama.gokufoodapi.repository.filter.UserFilter;

@Repository
@Validated
public interface UserRepository extends CrudRepository<User, BigInteger> {

	@Query(QRY_FILTER)
	Slice<TotalRecordsHolder<User>> filter(@Valid @NotNull UserFilter filter, Pageable pag);
	
	@Query(QRY_ACTIVE_BY_LOGIN)
	Optional<User> findActiveByLogin(@Valid @NotNull(message = Messages.USER_LOGIN_NOT_NULL) String login);
	
	Optional<User> findByLoginAndPassword(@NotBlank String login, @NotBlank String password);
	
	static final String QRY_FILTER = """
			select function('totalRecords') as totalRecords, usr as data
			  from User usr
			inner join fetch usr.person pes
			where string_regex((usr.login, pes.firstName, pes.lastName), :#{#filter.quickSearch}) = true
			  and ( usr.id = :#{#filter.id} or coalesce(:#{#filter.id}, null) = null )
			  and usr.active = true
			  and pes.active = true
		""";
	
	static final String QRY_ACTIVE_BY_LOGIN = """
			select usr
			from User usr
			inner join fetch usr.person pes
			where usr.login = :login
			and usr.active = true
			and pes.active = true
			""";
}
