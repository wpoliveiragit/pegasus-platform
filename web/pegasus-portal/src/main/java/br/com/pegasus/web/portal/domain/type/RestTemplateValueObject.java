package br.com.pegasus.web.portal.domain.type;

import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class RestTemplateValueObject<S, F> {

  private String url;
  private Object[] uriVariables;

  public S getResponseSuccess() {
    return responseSuccess;
  }

  private boolean success;
  private S responseSuccess;
  private F responseFail;
}
