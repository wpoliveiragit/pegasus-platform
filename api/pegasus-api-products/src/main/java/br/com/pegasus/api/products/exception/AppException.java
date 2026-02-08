package br.com.pegasus.api.products.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {
  private final HttpStatus httpStatus;

  public AppException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public static AppException conflictName(String name) {
    return new AppException("Existing name '" + name + "'", HttpStatus.CONFLICT);
  }

  public static AppException notFoundId(Long id){
    return new AppException("Product Not Found by id=" + id, HttpStatus.NOT_FOUND);
  }
}
