package br.com.pegasus.api.products.api.handler;

import br.com.pegasus.api.products.api.type.ExceptionResponseType;
import br.com.pegasus.api.products.api.type.ResponseType;
import br.com.pegasus.api.products.domain.exception.BusinessException;
import br.com.pegasus.api.products.infra.logger.AppLogger;
import br.com.pegasus.api.products.infra.util.HttpUtil;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.message.ParameterizedMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RequiredArgsConstructor
@RestControllerAdvice
public class RestControllerAdviceHandler {

  private static final String FMT_VH_MTD_ARG_TYPE_MISMA_EX = "Parâmetro '%s' inválido. Valor '%s' não é do tipo %s";
  private final AppLogger log = new AppLogger(RestControllerAdviceHandler.class);

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseType> fallback(Exception ex) {
    log.infoPattern("fallback", "params: message:{}", ex.getMessage());
    HttpStatus hs = HttpStatus.INTERNAL_SERVER_ERROR;
    var respType = new ExceptionResponseType(hs.value(), hs.getReasonPhrase(), hs.getReasonPhrase());
    ResponseEntity<ResponseType> resp = HttpUtil.responseInternalServerError(respType);
    log.errorPattern("fallback", "response: ", resp.getBody());
    return resp;
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ResponseType> domain(BusinessException ex) {
    ResponseEntity<ResponseType> resp = createResponse(ex.getHttpStatus(), ex.getMessage());
    log.warnPattern("BusinessException", "params: message:{}, response:{}", ex.getMessage(), resp.getBody());
    return resp;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> validationHandler(ConstraintViolationException ex) {
    String detail = ex.getConstraintViolations().stream()//
        .map(v -> v.getPropertyPath() + ": " + v.getMessage())//
        .reduce((a, b) -> a + " | " + b)//
        .orElse("Validation error");
    ResponseEntity<ResponseType> resp = createResponse(HttpStatus.BAD_REQUEST, detail);
    log.warnPattern("ConstraintViolationException", "params: message:{}, detail:{}, response:{}", ex.getMessage(), detail, resp.getBody());
    return resp;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> validationHandler(MethodArgumentNotValidException ex) {
    String detail = ex.getBindingResult().getFieldErrors().stream()//
        .map(e -> e.getField() + ": " + e.getDefaultMessage())//
        .reduce((a, b) -> a + " | " + b)//
        .orElse("Body inválido");
    ResponseEntity<ResponseType> resp = createResponse(HttpStatus.BAD_REQUEST, detail);
    log.warnPattern("MethodArgumentNotValidException", "params: message:{}, detail:{}, response:{}", ex.getMessage(), detail, resp.getBody());
    return resp;
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> validationHandler(HttpMessageNotReadableException ex) {
    String detail = ex.getMostSpecificCause().getMessage();
    ResponseEntity<ResponseType> resp = createResponse(HttpStatus.BAD_REQUEST, detail);
    log.warnPattern("HttpMessageNotReadableException", "params: message:{}, detail:{}, response:{}", ex.getMessage(), detail, resp.getBody());
    return resp;
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<?> validationHandler(MethodArgumentTypeMismatchException ex) {

    String detail = ParameterizedMessage.format("Parâmetro '{}' inválido. Valor '{}' não é do tipo {}",
        new Object[]{ex.getName(), ex.getValue(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido"});
    ResponseEntity<ResponseType> resp = createResponse(HttpStatus.BAD_REQUEST, detail);
    log.warnPattern("MethodArgumentTypeMismatchException", "params: message:{}, detail:{}, response:{}", ex.getMessage(), detail, resp.getBody());
    return resp;
  }

  private ResponseEntity<ResponseType> createResponse(HttpStatus hs, String detail) {
    return HttpUtil.responseChoice(new ExceptionResponseType(hs.value(), hs.getReasonPhrase(), detail), hs);
  }

}