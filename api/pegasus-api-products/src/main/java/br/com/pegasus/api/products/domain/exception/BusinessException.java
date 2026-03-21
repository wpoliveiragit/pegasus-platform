package br.com.pegasus.api.products.domain.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException {

  private final HttpStatus httpStatus;

  public BusinessException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public static void conflictName(String name) {
    throw new BusinessException("Existing name '" + name + "'", HttpStatus.CONFLICT);
  }

  public static BusinessException notFoundId(Long id) {
    return new BusinessException("Product Not Found by id=" + id, HttpStatus.NOT_FOUND);
  }

}
