package br.com.pegasus.web.portal.model;

import br.com.pegasus.web.portal.util.MethodUtil;
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
public class RequestModel {

  private ProductModel product;
  private Integer page;
  private Integer size;

  @Override
  public String toString() {
    return MethodUtil.toJson(this);
  }
}
