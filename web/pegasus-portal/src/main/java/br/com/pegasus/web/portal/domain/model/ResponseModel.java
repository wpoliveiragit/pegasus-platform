package br.com.pegasus.web.portal.domain.model;

import br.com.pegasus.web.portal.infra.util.MethodUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseModel<R, E> {
  private R response;
  private E errorResponse;
  private String errorMessage;
  private int status;

  public ResponseModel(int status, R response) {
    this.status = status;
    this.response = response;
  }

  public ResponseModel(int status, String errorMessage, E errorResult) {
    this.status = status;
    this.errorMessage = errorMessage;
    this.errorResponse = errorResult;
  }

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
