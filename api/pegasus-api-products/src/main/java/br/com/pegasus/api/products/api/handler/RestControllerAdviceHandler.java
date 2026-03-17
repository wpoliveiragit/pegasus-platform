package br.com.pegasus.api.products.api.handler;

import br.com.pegasus.api.products.api.type.ExceptionResponseType;
import br.com.pegasus.api.products.domain.exception.BusinessException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Log4j2
@RequiredArgsConstructor
@RestControllerAdvice
public class RestControllerAdviceHandler {

  private static final String CLASS_NAME = RestControllerAdviceHandler.class.getSimpleName();

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionResponseType> fallback(Exception ex) {
    log.error("{}::fallback::message: {}", CLASS_NAME, ex.getMessage());
    HttpStatus hs = HttpStatus.INTERNAL_SERVER_ERROR;
    var resp = new ExceptionResponseType(hs.value(), hs.getReasonPhrase(), hs.getReasonPhrase());
    log.error("{}::fallback::response: {}", CLASS_NAME, resp);
    return new ResponseEntity<>(resp, hs);
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ExceptionResponseType> domain(BusinessException ex) {
    log.warn("{}::domain: message:{}", CLASS_NAME, ex.getMessage());
    return createResponse(ex.getHttpStatus(), ex.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> validationHandler(ConstraintViolationException ex) {
    log.error("{}::validationHandler::constraintViolationException::message: {}", CLASS_NAME, ex.getMessage());
    String detail = ex.getConstraintViolations()
        .stream()
        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
        .reduce((a, b) -> a + " | " + b)
        .orElse("Validation error");
    return createResponse(HttpStatus.BAD_REQUEST, detail);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> validationHandler(MethodArgumentNotValidException ex) {
    log.error("{}::validationHandler::methodArgumentNotValidException::message: {}", CLASS_NAME, ex.getMessage());
    String detail = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(e -> e.getField() + ": " + e.getDefaultMessage())
        .reduce((a, b) -> a + " | " + b)
        .orElse("Body inválido");
    return createResponse(HttpStatus.BAD_REQUEST, detail);
  }

  @ExceptionHandler(HttpMessageNotReadableException .class)
  public ResponseEntity<?> validationHandler(HttpMessageNotReadableException ex) {
    log.error("{}::validationHandler::HttpMessageNotReadableException::message: {}", CLASS_NAME, ex.getMessage());
    String detail = ex.getMostSpecificCause().getMessage();
    return createResponse(HttpStatus.BAD_REQUEST, detail);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<?> validationHandler(MethodArgumentTypeMismatchException ex) {
    log.error("{}::validationHandler::MethodArgumentTypeMismatchException::message: {}", CLASS_NAME, ex.getMessage());
    String detail = String.format(
        "Parâmetro '%s' inválido. Valor '%s' não é do tipo %s",
        ex.getName(),
        ex.getValue(),
        ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido"
    );
    return createResponse(HttpStatus.BAD_REQUEST, detail);
  }

  private ResponseEntity<ExceptionResponseType> createResponse(HttpStatus hs, String detail) {
    var resp = new ExceptionResponseType(hs.value(), hs.getReasonPhrase(), detail);
    log.warn("{}::createResponse::response: {}", CLASS_NAME, resp);
    return new ResponseEntity<>(resp, hs);
  }

}