package br.com.pegasus.api.products.infra.util;

import br.com.pegasus.api.products.api.type.ResponseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

public final class HttpUtil {

  public static ResponseEntity<ResponseType> responseOk(Object body) {
    return ResponseEntity.ok(new ResponseType(HttpStatus.OK.value(), body));
  }

  public static ResponseEntity<ResponseType> responseCreate(Object body, URI uri) {
    return ResponseEntity.created(uri)//
        .body(new ResponseType(HttpStatus.CREATED.value(), body));
  }

  public static ResponseEntity<ResponseType> responseInternalServerError(Object body) {
    return ResponseEntity.internalServerError()//
        .body(new ResponseType(HttpStatus.INTERNAL_SERVER_ERROR.value(), body));
  }

  public static ResponseEntity<ResponseType> responseChoice(Object body, HttpStatus httpStatus ) {
    return ResponseEntity.status(httpStatus)
        .body(new ResponseType(httpStatus.value(), body));
  }

}
