package br.com.cotiinformatica.handlers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import br.com.cotiinformatica.domain.exceptions.PedidoNaoEncontradoException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
	
   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
       Map<String, String> errors = new HashMap<>();
       // Percorre os erros de campo e adiciona ao map
       ex.getBindingResult().getAllErrors().forEach(error -> {
           String field = ((FieldError) error).getField();
           String message = error.getDefaultMessage();
           errors.put(field, message);
       });
       return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body(errors);
   }
   
   @ExceptionHandler(HttpMessageNotReadableException.class)
   public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
       Map<String, String> errors = new HashMap<>();
       errors.put("erro", ex.getMessage());
       return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body(errors);
   }
   
   @ExceptionHandler(MethodArgumentTypeMismatchException.class)
   public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
       Map<String, String> errors = new HashMap<>();
       errors.put("erro", ex.getMessage());
       return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body(errors);
   }
   
   
   @ExceptionHandler(PedidoNaoEncontradoException.class)
   public ResponseEntity<Object> handlePedidoNaoEncontradoException(PedidoNaoEncontradoException ex) {
       Map<String, String> errors = new HashMap<>();
       errors.put("erro", ex.getMessage());
       return ResponseEntity
               .status(HttpStatus.NOT_FOUND)
               .body(errors);
   }
}

