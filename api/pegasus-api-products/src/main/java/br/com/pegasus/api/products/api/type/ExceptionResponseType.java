package br.com.pegasus.api.products.api.type;

import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponseType {

  private int code; // http-status::value
  private String message; // http-status::reason-phrase
  private String detail; // Detalhes do problema

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
