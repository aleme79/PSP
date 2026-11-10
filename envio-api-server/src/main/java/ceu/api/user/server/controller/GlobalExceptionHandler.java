package ceu.api.user.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ceu.api.user.server.service.EnvioNoEncontradoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(EnvioNoEncontradoException.class)
	public ResponseEntity<String> handleNotFound(EnvioNoEncontradoException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(e.getMessage());
	}
	
	public ResponseEntity<String> handleBadRequestExceptions(Exception e){
		return ResponseEntity.badRequest().body(null);
	}

}
