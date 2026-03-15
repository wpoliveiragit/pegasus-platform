package br.com.pegasus.web.portal.type;

import br.com.pegasus.web.portal.util.MethodUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseModel {

  private int status;
  private String jsonResponse;
  private String jsonErrorResponse;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }

}
