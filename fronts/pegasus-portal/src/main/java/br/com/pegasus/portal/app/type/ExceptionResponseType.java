package br.com.pegasus.portal.app.type;

import br.com.pegasus.portal.app.util.MethodUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponseType {

  private int code; // http-status::value
  private String message; // http-status::reason-phrase
  private String detail; // Detalhes do problema

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
