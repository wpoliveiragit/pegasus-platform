package br.com.pegasus.web.portal.domain.type;

import br.com.pegasus.web.portal.infra.util.MethodUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseModel {

  private int code; // http-status::value
  private String message; // http-status::reason-phrase
  private String detail; // Detalhes do problema

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
