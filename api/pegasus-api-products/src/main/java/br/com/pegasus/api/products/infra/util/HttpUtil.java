package br.com.pegasus.api.products.infra.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class HttpUtil {

  public static <T> ResponseEntity<T> responseOK(T obj) {
    return ResponseEntity.ok(obj);
  }

  public static <T> ResponseEntity<T> responseCreate(T obj) {
    return ResponseEntity.status(HttpStatus.CREATED.value()).body(obj);
  }
}
