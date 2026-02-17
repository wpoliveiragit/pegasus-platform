package br.com.pegasus.web.portal.domain.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class RestTemplateExceptionType extends Exception {

  private final HttpStatusCode status;

  public RestTemplateExceptionType(Throwable cause, HttpStatusCode status){
    super(cause);
    this.status = status;
  }

}
