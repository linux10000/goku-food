package com.akiratoriyama.gokufoodapi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.ConstraintViolation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.akiratoriyama.gokufoodapi.infra.exception.GokuException;
import com.akiratoriyama.gokufoodapi.infra.exception.GokuRuntimeException;
import com.akiratoriyama.gokufoodapi.infra.util.I18nUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

@Configuration
@ControllerAdvice
public class GokuExceptionHandlerController {

	@Autowired
	private I18nUtil i18nUtil;
	
	
	@ExceptionHandler({Throwable.class})
	public ResponseEntity<SimplesErrorResponse> handleDefaultException(Throwable ex) {
		return new ResponseEntity<>(
					new SimplesErrorResponse(i18nUtil.get(ex.getMessage()), ex.getClass().getSimpleName()), 
					HttpStatus.INTERNAL_SERVER_ERROR
				);
	}
	
	@ExceptionHandler({AccessDeniedException.class})
	public ResponseEntity<SimplesErrorResponse> handleDefaultException(AccessDeniedException ex) {
		return new ResponseEntity<>(
				new SimplesErrorResponse(ex.getMessage(), ex.getClass().getSimpleName()), 
				HttpStatus.FORBIDDEN
				);
	}
	
	@ExceptionHandler({BadCredentialsException.class})
	public ResponseEntity<SimplesErrorResponse> handleDefaultException(BadCredentialsException ex) {
		return new ResponseEntity<>(
				new SimplesErrorResponse(ex.getMessage(), ex.getClass().getSimpleName()), 
				HttpStatus.UNAUTHORIZED
				);
	}
	
	@ExceptionHandler({GokuException.class})
	public ResponseEntity<SimplesErrorResponse> handleDefaultHcfException(GokuException ex) {
		return new ResponseEntity<>(
					new SimplesErrorResponse(i18nUtil.get(ex.getMessage(), ex.getArgs()), ex.getClass().getSimpleName()), 
					HttpStatus.BAD_REQUEST
				);
	}
	
	@ExceptionHandler({GokuRuntimeException.class})
	public ResponseEntity<SimplesErrorResponse> handleDefaultHcfException(GokuRuntimeException ex) {
		return new ResponseEntity<>(
				new SimplesErrorResponse(i18nUtil.get(ex.getMessage(), ex.getArgs()), ex.getClass().getSimpleName()), 
				HttpStatus.BAD_REQUEST
				);
	}
	
	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<ConstraintErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
		Function<ObjectError, InvalidConstraint> toCampoInvalido = obj -> {
			boolean isField = obj instanceof FieldError;
			return new InvalidConstraint((isField ? ((FieldError) obj).getField() : obj.getObjectName()), i18nUtil.get(obj.getDefaultMessage(), obj.getArguments()), isField);
		};
		
		return new ResponseEntity<>(
					new ConstraintErrorResponse(
							ex.getMessage(),
							ex.getClass().getSimpleName(),
							Stream.concat(
									Optional.ofNullable(ex.getBindingResult().getGlobalErrors()).orElse(new ArrayList<>()).stream().map(toCampoInvalido), 
									Optional.ofNullable(ex.getBindingResult().getFieldErrors()).orElse(new ArrayList<>()).stream().map(toCampoInvalido)
									).collect(Collectors.toList())
							), 
					HttpStatus.BAD_REQUEST
				);
	}
	
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	public ResponseEntity<ConstraintErrorResponse> handleValidationError(javax.validation.ConstraintViolationException ex) {
		Function<ConstraintViolation<?>, InvalidConstraint> toCampoInvalido = obj -> 
			new InvalidConstraint((obj.getRootBeanClass().getName()), i18nUtil.get(obj.getMessage(), obj.getExecutableParameters()), false);

		return new ResponseEntity<>(
				new ConstraintErrorResponse(
						ex.getMessage(),
						ex.getClass().getSimpleName(),
						ex.getConstraintViolations().stream().map(toCampoInvalido).collect(Collectors.toList())
					), 
				HttpStatus.BAD_REQUEST
			);
	}
	
	@ExceptionHandler({ ObjectOptimisticLockingFailureException.class })
	public ResponseEntity<OptimisticLockingResponse> handleOptimisticLockingException(ObjectOptimisticLockingFailureException ex) {
		return new ResponseEntity<>(
				new OptimisticLockingResponse(ex.getMessage(), ex.getClass().getSimpleName(), ex.getPersistentClassName()), 
				HttpStatus.CONFLICT
				);
	}
	
	
	
	//TODO clean here
	
//	@ExceptionHandler({DataAccessException.class, PersistenceException.class, JpaSystemException.class, UncategorizedSQLException.class, HcfDBException.class, PSQLException.class, RegistroAlteradoPorOutroUsuarioException.class})
//	public ResponseEntity<Map<String, Object>> handleDBError(Throwable ex, WebRequest request) {
//		ex.printStackTrace();
//		
//		HcfDBException hcfDBException = null;
//		if ( ex instanceof HcfPgException ) //esse if existe por causa de herenca, sendo essa classe a super classe, ou seja, esse metodo eh chamado como super.handle...
//			hcfDBException = (HcfDBException) ex;
//		else
//			hcfDBException = PGExceptionResolver.resolve(ex);
//		
//		if ( ex instanceof RegistroAlteradoPorOutroUsuarioException )
//			return handleRegistroAlteradoPorOutroUsuarioException((RegistroAlteradoPorOutroUsuarioException) ex);
//		
//		if ( hcfDBException instanceof RegistroAlteradoPorOutroUsuarioException )
//			return handleRegistroAlteradoPorOutroUsuarioException((RegistroAlteradoPorOutroUsuarioException) hcfDBException);
//		
//		if ( hcfDBException instanceof HcfPgConstraintViolationException ) 
//			return handlePgConstraintValidationException((HcfPgConstraintViolationException) hcfDBException);
//		
//		Map<String, Object> r = new HashMap<String, Object>();
//		
//		if ( hcfDBException instanceof HcfPgException ) {
//			r.put("sqlState", ((HcfPgException)hcfDBException).getSqlState());
//			r.put("hint", ((HcfPgException)hcfDBException).getHint());
//		}
//			
//		r.put("message", hcfDBException.getMessage());
//		r.put("exception", hcfDBException.getClass().getSimpleName());
//		r.put("code", "003");
//		
//		return new ResponseEntity<Map<String,Object>>(r, getHeadersPadrao(), HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	
//	private ResponseEntity<Map<String,Object>> handlePgConstraintValidationException(HcfPgConstraintViolationException e) {
//		Map<String, Object> r = new HashMap<String, Object>();
//		
//		r.put("sqlState", e.getSqlState());
//		r.put("table", e.getTable());
//		r.put("column", e.getColumn());
//		r.put("constraint", e.getConstraint());
//		r.put("tipo", e.getTipo().map(PgConstraintViolationKind::name).orElse(""));
//		r.put("message", e.getMessage());
//		r.put("detail", e.getDetail());
//		r.put("exception", e.getClass().getSimpleName());
//		r.put("code", "001");
//		
//		return new ResponseEntity<Map<String,Object>>(r, getHeadersPadrao(), HttpStatus.INTERNAL_SERVER_ERROR);
//	}
	
	
	@Data
	@AllArgsConstructor
	public static class InvalidConstraint {
		private String name;
		private String message;
		private boolean field;
	}
	
	@Data
	@AllArgsConstructor
	public static class SimplesErrorResponse {
		private String message;
		private String exception;
	}
	
	@Data
	@AllArgsConstructor
	public static class OptimisticLockingResponse {
		private String message;
		private String exception;
		private String persistentClass;
	}
	
	@Data
	@AllArgsConstructor
	public static class ConstraintErrorResponse {
		private String message;
		private String exception;
		private List<InvalidConstraint> constraints;
	}
}
