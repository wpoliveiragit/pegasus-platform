package br.com.pegasus.api.products.api.handler;

import br.com.pegasus.api.products.api.type.ExceptionResponseType;
import br.com.pegasus.api.products.api.type.ResponseType;
import br.com.pegasus.api.products.domain.exception.BusinessException;
import br.com.pegasus.api.products.infra.util.HttpUtil;
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

  private static class Const {

    private static final String CLASS_NAME = RestControllerAdviceHandler.class.getSimpleName();

    private static final String LOG_FALL_BACK_MESSAGE = CLASS_NAME + "::fallback::message: {}";
    private static final String LOG_FALL_BACK_RESPONSE = CLASS_NAME + "::fallback::response: {}";

    private static final String LOG_DOMAIN_MESSAGE = CLASS_NAME + "::domain::message: {}";
    private static final String LOG_VH_CONST_VIOLA_EX_MSG = CLASS_NAME + "::validationHandler::constraintViolationException::message: {}";
    private static final String LOG_VH_MTO_ARG_N_VALID_EX_MSG = CLASS_NAME + "::validationHandler::methodArgumentNotValidException::message: {}";
    private static final String LOG_VH_HTTP_MSG_N_READA_EX_MSG = CLASS_NAME + "::validationHandler::HttpMessageNotReadableException::message: {}";
    private static final String LOG_VH_MTD_ARG_TYPE_MISMA_EX_MSG = CLASS_NAME + "::validationHandler::MethodArgumentTypeMismatchException::message: {}";
    private static final String LOG_VH_CREAT_RESPO = CLASS_NAME + "::createResponse::response: {}";

    private static final String FMT_VH_MTD_ARG_TYPE_MISMA_EX = "Parâmetro '%s' inválido. Valor '%s' não é do tipo %s";
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseType> fallback(Exception ex) {
    log.error(Const.LOG_FALL_BACK_MESSAGE, ex.getMessage());
    HttpStatus hs = HttpStatus.INTERNAL_SERVER_ERROR;
    var respType = new ExceptionResponseType(hs.value(), hs.getReasonPhrase(), hs.getReasonPhrase());
    ResponseEntity<ResponseType> resp = HttpUtil.responseInternalServerError(respType);
    log.error(Const.LOG_FALL_BACK_RESPONSE, resp.getBody());
    return resp;
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ResponseType> domain(BusinessException ex) {
    log.warn(Const.LOG_DOMAIN_MESSAGE, ex.getMessage());
    return createResponse(ex.getHttpStatus(), ex.getMessage());
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> validationHandler(ConstraintViolationException ex) {
    log.error(Const.LOG_VH_CONST_VIOLA_EX_MSG, ex.getMessage());
    String detail = ex.getConstraintViolations().stream()//
        .map(v -> v.getPropertyPath() + ": " + v.getMessage())//
        .reduce((a, b) -> a + " | " + b)//
        .orElse("Validation error");
    return createResponse(HttpStatus.BAD_REQUEST, detail);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> validationHandler(MethodArgumentNotValidException ex) {
    log.error(Const.LOG_VH_MTO_ARG_N_VALID_EX_MSG, ex.getMessage());
    String detail = ex.getBindingResult().getFieldErrors().stream()//
        .map(e -> e.getField() + ": " + e.getDefaultMessage())//
        .reduce((a, b) -> a + " | " + b)//
        .orElse("Body inválido");
    return createResponse(HttpStatus.BAD_REQUEST, detail);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<?> validationHandler(HttpMessageNotReadableException ex) {
    log.error(Const.LOG_VH_HTTP_MSG_N_READA_EX_MSG, ex.getMessage());
    String detail = ex.getMostSpecificCause().getMessage();
    return createResponse(HttpStatus.BAD_REQUEST, detail);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<?> validationHandler(MethodArgumentTypeMismatchException ex) {
    log.error(Const.LOG_VH_MTD_ARG_TYPE_MISMA_EX_MSG, ex.getMessage());
    String detail = String.format(Const.FMT_VH_MTD_ARG_TYPE_MISMA_EX,//
        ex.getName(), ex.getValue(), //
        ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "desconhecido");
    return createResponse(HttpStatus.BAD_REQUEST, detail);
  }

  private ResponseEntity<ResponseType> createResponse(HttpStatus hs, String detail) {
    var respType = new ExceptionResponseType(hs.value(), hs.getReasonPhrase(), detail);
    ResponseEntity<ResponseType> resp = HttpUtil.responseChoice(respType, hs);
    log.warn(Const.LOG_VH_CREAT_RESPO, respType);
    return resp;
  }

}