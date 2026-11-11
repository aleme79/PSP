package ceu.dam.psp.examen.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ceu.dam.psp.examen.exceptions.ErrorPagoException;
import ceu.dam.psp.examen.exceptions.NoAutorizadoException;
import ceu.dam.psp.examen.exceptions.NoExisteException;
import ceu.dam.psp.examen.exceptions.UsuarioDuplicadoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(exception = UsuarioDuplicadoException.class)
    public ResponseEntity<String> handle(UsuarioDuplicadoException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
	
	@ExceptionHandler(NoAutorizadoException.class)
    public ResponseEntity<String> handleNoAutorizado(NoAutorizadoException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(e.getMessage());
    }
	
	@ExceptionHandler(NoExisteException.class)
    public ResponseEntity<String> handleNoExiste(NoExisteException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(e.getMessage());
    }
	
	@ExceptionHandler(ErrorPagoException.class)
    public ResponseEntity<String> handleErrorPago(ErrorPagoException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidation(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest()
                .body(e.getFieldError().getField() + ": " + e.getFieldError().getDefaultMessage());
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception e) {
        log.error("Error inesperado en el servidor: ", e);
        return ResponseEntity.internalServerError()
                .body("Error inesperado en el servidor. Consulte el log del servidor si tiene acceso.");
    }

}
