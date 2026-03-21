package br.com.pegasus.api.products.api.type;

import br.com.pegasus.api.products.infra.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponseType {

  private int status;
  private String message;
  private String detail;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
